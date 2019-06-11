package vendingcabinets.dlc.cn.vendingcabinets.base.serialport;

import android.serialport.SerialPort;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCException;
import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCExceptionCode;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.AbstractTaskQueue;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  10:13
 */
public class SerialPortMgr {
    private static class InstanceSerialPortMgr {
        private static final SerialPortMgr INSTANCE = new SerialPortMgr();
    }

    public static final boolean BYPASS_METADATA_FILTER = false;

    public static SerialPortMgr get() {
        return InstanceSerialPortMgr.INSTANCE;
    }

    private SerialPort mSerialPort;
    private AbstractTaskQueue mAbstractTaskQueue;
    private boolean isStart = false;
    private BaseDataCallback mBaseDataCallback;
    private OutputStream mOutputStream;
    private BufferedInputStream mBufferedInputStream;

    public void init(String deviceAddress, int baudRate, BaseDataCallback baseDataCallback, SerialPortCallback serialPortCallback) {
        if (TextUtils.isEmpty(deviceAddress)) {
            throw new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "串口地址不为null");
        }
        if (baudRate == 0) {
            throw new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "波特率不为0");
        }
        if (baseDataCallback == null) {
            throw new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "回调不可以为空,校验方法必须实现");
        }
        mBaseDataCallback = baseDataCallback;
        closeSerialPort();
        try {
            File device = new File(deviceAddress);
            mSerialPort = new SerialPort(device, baudRate, 0);
            mOutputStream = mSerialPort.getOutputStream();
            if (mSerialPort.getInputStream() != null) {
                mBufferedInputStream = new BufferedInputStream(mSerialPort.getInputStream());
            }
            serialPortCallback.onOpenSuccess();
            startTaskQueue();
        } catch (Exception e) {
            serialPortCallback.onOpenError(deviceAddress, baudRate, new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "串口打开异常:" + e.toString()));
            mSerialPort = null;
        }
    }

    private void startTaskQueue() {
        if (mAbstractTaskQueue == null) {
            mAbstractTaskQueue = new AbstractTaskQueue(1);
        }
        if (!isStart) {
            mAbstractTaskQueue.start();
            isStart = true;
        }
    }

    /**
     * 停止当前任务,释放线程
     */
    public void stopTaskQueue() {
        if (mAbstractTaskQueue != null && isStart) {
            mAbstractTaskQueue.stop();
            isStart = false;
        }
    }

    public void send(@Priority int priority, CmdPack cmdPack, SendResultCallback sendResultCallback) {
        if (mSerialPort == null) {
            if (sendResultCallback != null) {
                sendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "串口打开失败!"));
            }
            return;
        }

        if (mOutputStream == null) {
            if (sendResultCallback != null) {
                sendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "串口获取,OutputStream为null"));
            }
            return;
        }
        if (mBufferedInputStream == null) {
            if (sendResultCallback != null) {
                sendResultCallback.onFailed(new DLCException(DLCExceptionCode.SERIAL_PORT_ERROR, "串口获取,InputStream为null:"));
                return;
            }
        }
        CmdTask cmdTask = new CmdTask(priority, sendResultCallback, cmdPack, mOutputStream, mBufferedInputStream, mBaseDataCallback);
        if (mAbstractTaskQueue == null) {
            startTaskQueue();
        }
        //加入等待任务里面
        mAbstractTaskQueue.add(cmdTask);
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        if (mSerialPort != null) {
            try {
                if (mSerialPort.getOutputStream() != null) {
                    mSerialPort.getOutputStream().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (mSerialPort.getInputStream() != null) {
                    mSerialPort.getInputStream().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSerialPort.close();
            mSerialPort = null;
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mBufferedInputStream != null) {
            try {
                mBufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
