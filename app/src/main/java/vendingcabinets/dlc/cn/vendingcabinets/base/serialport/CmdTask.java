package vendingcabinets.dlc.cn.vendingcabinets.base.serialport;

import android.os.SystemClock;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vendingcabinets.dlc.cn.vendingcabinets.DLCLog;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.BaseQueue;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority;
import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCException;
import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCExceptionCode;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.ByteUtil;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  12:22
 */
public class CmdTask extends BaseQueue {
    private SendResultCallback mSendResultCallback;
    private CmdPack mCmdPack;
    private SerialPort mSerialPort;
    private BaseDataCallback mBaseDataCallback;

    public CmdTask(@Priority int priority, SendResultCallback mSendResultCallback, CmdPack mCmdPack, SerialPort mSerialPort, BaseDataCallback baseDataCallback) {
        this.mSendResultCallback = mSendResultCallback;
        this.mCmdPack = mCmdPack;
        this.mSerialPort = mSerialPort;
        this.mBaseDataCallback = baseDataCallback;
        setPriority(priority);
    }

    @Override
    public void runTask() {
        boolean isSend = sendData();
        if (!isSend) {
            return;
        }
        boolean isRunning = true;

        int size;
        // 记录一下发送后的时间，用来判断接收数据是否超时
        long sendTime = SystemClock.uptimeMillis();
        long waitTime = 0;
        while (isRunning) {
            InputStream inputStream = mSerialPort.getInputStream();
            if (inputStream == null) {
                if (mSendResultCallback != null) {
                    mSendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "串口获取,InputStream为null:"));
                    return;
                }
            }
            BufferedInputStream mInputStream = new BufferedInputStream(mSerialPort.getInputStream());
            try {
                DataPack dataPack = null;
                if (mInputStream.available() > 0) {
                    // 更新一下接收数据时间
                    waitTime = SystemClock.uptimeMillis();
                    byte[] received = new byte[2048];
                    size = mInputStream.read(received);
                    dataPack = mBaseDataCallback.checkData(received, size);
                } else {
                    // 暂停一点时间，免得一直循环造成CPU占用率过高
                    SystemClock.sleep(1);
                }
                // 检查释放超市
                boolean isTimeOut = isTimeOut(sendTime, waitTime);
                if (dataPack != null) {
                    //命令码
                    String command =
                            ByteUtil.bytes2HexStr(new byte[mCmdPack.getCheckCommand()]);
                    String readCommand =
                            ByteUtil.bytes2HexStr(new byte[dataPack.getCommand()]);
                    if (readCommand.equalsIgnoreCase(command)) {
                        if (mSendResultCallback != null) {
                            mSendResultCallback.onSuccess(dataPack);
                        }
                    } else {
                        if (mSendResultCallback != null) {
                            mSendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR,
                                    "命令码不同,获取到结果为:" + dataPack.toString() + "--校验命令码:" + command));
                        }
                    }

                    return;
                }
                if (isTimeOut) {
                    if (mSendResultCallback != null) {
                        mSendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "读取超时"));
                    }
                    return;
                }
            } catch (IOException e) {
                if (mSendResultCallback != null) {
                    mSendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, e.toString()));
                }
                isRunning = false;
            }
        }
    }

    private boolean isTimeOut(long sendTime, long waitTime) {
        // 表示一直没收到数据
        if (waitTime == 0) {
            long sendOffset = SystemClock.uptimeMillis() - sendTime;
            return sendOffset > mCmdPack.getSendOutTime();
        } else {
            // 有接收到过数据，但是距离上一个数据已经超时
            long waitOffset = SystemClock.uptimeMillis() - waitTime;
            return waitOffset > mCmdPack.getWaitOutTime();
        }
    }

    private boolean sendData() {
        try {
            OutputStream outputStream = mSerialPort.getOutputStream();
            if (outputStream == null) {
                if (mSendResultCallback != null) {
                    mSendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "串口获取,OutputStream为null"));
                }
                return false;
            }
            SystemClock.sleep(100);
            outputStream.write(mCmdPack.getSendData());
            DLCLog.d("发送码:" + ByteUtil.bytes2HexStr(mCmdPack.getSendData()));
        } catch (IOException e) {
            if (mSendResultCallback != null) {
                mSendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "硬件错误:" + e.toString()));
            }
            return false;
        }
        return true;
    }

}
