package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vendingcabinets.dlc.cn.vendingcabinets.DLCLog;
import vendingcabinets.dlc.cn.vendingcabinets.base.ICommonCallback;
import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCException;
import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCExceptionCode;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.CmdPack;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.DataPack;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.SendResultCallback;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.SerialPortCallback;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.SerialPortMgr;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.ByteUtil;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.SharedPreferencesUtil;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.SellingGoodsBean;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.SlaveStateBean;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.TemperatureBean;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.TestingCabinetDriveBean;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.TestingSingleBean;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  21:58
 */
public class VendingMachineAAProxy {
    private static class InstanceVendingMachineAAProxy {
        private static final VendingMachineAAProxy INSTANCE = new VendingMachineAAProxy();
    }

    private boolean isOpenSuccess = false;

    public static VendingMachineAAProxy get() {
        return InstanceVendingMachineAAProxy.INSTANCE;
    }

    private boolean checkInit(ICommonCallback iCommonCallback) {
        if (!isOpenSuccess) {
            if (iCommonCallback != null) {
                iCommonCallback.onFailed(
                        new DLCException(DLCExceptionCode.VENDING_MACHINE_AA_PROXY_ERROR, "串口为初始化,请先调用VendingMachineAAProxy.init(context)进行初始化"));
            }
            return false;
        }
        return true;
    }

    public void init(Context context, String deviceAddress, int baudRate, boolean isLog) {
        SharedPreferencesUtil.init(context);
        DLCLog.setLog(isLog);
        SerialPortMgr.get().init(deviceAddress, baudRate, new VendingMachineAACallback(), new SerialPortCallback() {
            @Override
            public void onOpenError(String deviceAddress, int baudRate, DLCException dLCException) {
                isOpenSuccess = false;
                DLCLog.d(dLCException.toString());
            }

            @Override
            public void onOpenSuccess() {
                isOpenSuccess = true;
            }
        });
    }

    private void send(@Priority int priority, byte[] allData, byte command, final ICommonCallback iCommonCallback,
                      SendResultCallback sendResultCallback) {
        if (!checkInit(iCommonCallback)) {
            return;
        }
        CmdPack cmdPack = new CmdPack();
        cmdPack.setSendData(allData);
        cmdPack.setCheckCommand(command);
        SerialPortMgr.get().send(priority, cmdPack, sendResultCallback);
    }

    /**
     * 应答帧  0x81
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x01：从机应答主机  0X81：主机应答从机
     * B5     ——  数据       0x01:ACK, 0x02:BUSY
     * B6     ——  校验码
     */
    public void responseFrame(@Priority int priority, int cabinetAddress, int data, final ICommonCallback<String> iCommonCallback) {
        byte[] allData = VendingMachineAACmdFactory.responseFrame(cabinetAddress, data);
        send(priority, allData, VendingMachineAAProtocol.RESPONSE_FRAME_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(ByteUtil.bytes2HexStr(dataPack.getData()));
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }


    /**
     * 测试单个驱动单元      主 到 从 0x82
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x82
     * B5     ——  电机编号
     * B6     ——  校验码
     */
    public void testingSingleDrivers(@Priority int priority, int cabinetAddress, int motorNumber,
                                     final ICommonCallback<TestingSingleBean> iCommonCallback) {
        byte[] allData = VendingMachineAACmdFactory.testingSingleDrivers(cabinetAddress, motorNumber);
        send(priority, allData, VendingMachineAAProtocol.TESTING_SINGLE_DRIVER_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                TestingSingleBean testingSingleBean = new TestingSingleBean();
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                int dataLength = data.length();
                if (dataLength > 0) {
                    testingSingleBean.setMotorNumber(ByteUtil.hexStr2decimalStr(data.substring(0, 1)));
                }
                if (dataLength > 1) {
                    testingSingleBean.setTestResult(ByteUtil.hexStr2decimalStr(data.substring(1, 2)));
                }
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(testingSingleBean);
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }

    /**
     * 测试整柜驱动单元      主 到 从 0x83
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x05
     * B4     ——  控制码     0x83
     * B5     ——  校验码
     */
    public void testingCabinetDrive(@Priority int priority, int cabinetAddress, final ICommonCallback<String> iCommonCallback) {
        byte[] allData = VendingMachineAACmdFactory.testingCabinetDrive(cabinetAddress);
        send(priority, allData, VendingMachineAAProtocol.TESTING_CABINET_DRIVE_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(data);
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }

    /**
     * 货道扫描              主  到   从 0x85
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x05
     * B4     ——  控制码     0x85
     * B5     ——  校验码
     */
    public void cargoTrackScanning(@Priority int priority, final int cargoWay, int cabinetAddress,
                                   final ICommonCallback<List<TestingCabinetDriveBean>> iCommonCallback) {
        byte[] allData = VendingMachineAACmdFactory.testingCabinetDrive(cabinetAddress);
        send(priority, allData, VendingMachineAAProtocol.CARGO_TRACK_SCANNING_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                List<TestingCabinetDriveBean> testingCabinetDriveList = new ArrayList<>();
                for (int i = 0; i < cargoWay; i++) {
                    if (data.length() < i + 1) {
                        break;
                    }
                    TestingCabinetDriveBean testingCabinetDriveBean = new TestingCabinetDriveBean();
                    List<TestingCabinetDriveBean.TestingCabinetDrive> list =
                            getTestingCabinetDriveList(cargoWay, data.substring(i, i + 1), data.substring(i + 1, i + 2));
                    testingCabinetDriveBean.setCargoWayId(i + 1);
                    testingCabinetDriveBean.setList(list);
                    testingCabinetDriveList.add(testingCabinetDriveBean);
                }
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(testingCabinetDriveList);
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }

    private List<TestingCabinetDriveBean.TestingCabinetDrive> getTestingCabinetDriveList(int cargoWay, String testingCabinetDriveOne,
                                                                                         String testingCabinetDriveTwo) {
        List<TestingCabinetDriveBean.TestingCabinetDrive> list = new ArrayList<>();
        String mTestingCabinetDriveOne = ByteUtil.hexStr2BitArr(testingCabinetDriveOne);
        String mTestingCabinetDriveTwo = ByteUtil.hexStr2BitArr(testingCabinetDriveTwo);
        for (int i = mTestingCabinetDriveOne.length(); i > 0; i++) {
            TestingCabinetDriveBean.TestingCabinetDrive testingCabinetDrive = new TestingCabinetDriveBean.TestingCabinetDrive();
            testingCabinetDrive.setMotorNumber(i + 1);
            testingCabinetDrive.setExist(mTestingCabinetDriveOne.substring(i - 1, i).equals("1"));
            list.add(testingCabinetDrive);
        }
        for (int i = mTestingCabinetDriveTwo.length(); i > 0; i++) {
            if (list.size() > cargoWay) {
                break;
            }
            TestingCabinetDriveBean.TestingCabinetDrive testingCabinetDrive = new TestingCabinetDriveBean.TestingCabinetDrive();
            testingCabinetDrive.setMotorNumber(mTestingCabinetDriveOne.length() + i + 1);
            testingCabinetDrive.setExist(mTestingCabinetDriveTwo.substring(i - 1, i).equals("1"));
            list.add(testingCabinetDrive);
        }
        return list;
    }

    /**
     * 出售商品              主  到  从 0x86
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x86
     * B5     ——  电机编号
     * B6     ——  校验码
     */
    public void sellingGoods(@Priority int priority, int cabinetAddress, int motorNumber,
                             final ICommonCallback<SellingGoodsBean> iCommonCallback) {
        byte[] allData = VendingMachineAACmdFactory.sellingGoods(cabinetAddress, motorNumber);
        send(priority, allData, VendingMachineAAProtocol.SELLING_GOODS_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                SellingGoodsBean sellingGoodsBean = new SellingGoodsBean();
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                int dataLength = data.length();
                if (dataLength > 0) {
                    sellingGoodsBean.setMotorNumber(ByteUtil.hexStr2decimalStr(data.substring(0, 1)));
                }
                if (dataLength > 1) {
                    sellingGoodsBean.setResult(ByteUtil.hexStr2decimalStr(data.substring(1, 2)));
                }
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(sellingGoodsBean);
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }

    /**
     * 从机状态查询          主  到  从 0x87
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x05
     * B4     ——  控制码     0x87
     * B5     ——  校验码
     */
    public void slaveStateQuery(@Priority int priority, int cabinetAddress, final ICommonCallback<SlaveStateBean> iCommonCallback) {
        byte[] allData = VendingMachineAACmdFactory.slaveStateQuery(cabinetAddress);
        send(priority, allData, VendingMachineAAProtocol.SLAVE_STATE_QUERY_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                SlaveStateBean slaveStateBean = new SlaveStateBean();
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                int dataLength = data.length();
                if (dataLength > 0) {
                    slaveStateBean.setRefrigerationArea(ByteUtil.hexStr2decimalStr(data.substring(0, 1)));
                }
                if (dataLength > 1) {

                    slaveStateBean.setHeatingArea(ByteUtil.hexStr2decimalStr(data.substring(1, 2)));
                }
                if (dataLength > 2) {
                    String data3 = ByteUtil.hexStr2BitArr(data.substring(2, 3));
                    if (data3.length() > 0) {
                        slaveStateBean.setPhotographicIsNormal(data3.substring(0, 1).equals("0"));
                    }
                    if (data3.length() > 1) {
                        slaveStateBean.setGateIsStop(data3.substring(1, 2).equals("0"));
                    }
                    if (data3.length() > 2) {
                        slaveStateBean.setVibrateIsNormal(data3.substring(2, 3).equals("0"));
                    }
                    if (data3.length() > 3) {
                        slaveStateBean.setTemperatureControlBoardIsNormal(data3.substring(3, 4).equals("0"));
                    }
                    if (data3.length() > 4) {
                        slaveStateBean.setElectromagneticLockIsNormal(data3.substring(4, 5).equals("0"));
                    }
                    if (data3.length() > 5) {
                        slaveStateBean.setPowerIsNormal(data3.substring(5, 6).equals("0"));
                    }
                    if (data3.length() > 6) {
                        slaveStateBean.setCompressorIsNormal(data3.substring(6, 7).equals("0"));
                    }
                    if (data3.length() > 7) {
                        slaveStateBean.setFanIsNormal(data3.substring(7, 8).equals("0"));
                    }
                }
                if (dataLength > 3) {
                    String data4 = ByteUtil.hexStr2BitArr(data.substring(3, 4));
                    if (data4.length() > 0) {
                        slaveStateBean.setCompressorIsStop(data4.substring(0, 1).equals("0"));
                    }
                    if (data4.length() > 1) {
                        slaveStateBean.setFanIIsStop(data4.substring(1, 2).equals("0"));
                    }
                    if (data4.length() > 2) {
                        slaveStateBean.setWindowGlassStop(data4.substring(2, 3).equals("0"));
                    }
                    if (data4.length() > 3) {
                        slaveStateBean.setHeatingModuleStop(data4.substring(3, 4).equals("0"));
                    }
                    if (data4.length() > 4) {
                        slaveStateBean.setLedLanternStop(data4.substring(4, 5).equals("0"));
                    }
                    if (data4.length() > 5) {
                        slaveStateBean.setCabinetSelection(data4.substring(5, 6));
                    }
                    if (data4.length() > 6) {
                        slaveStateBean.setHelpKeyIsDown(data4.substring(6, 7).equals("0"));
                    }
                    if (data4.length() > 7) {
                        slaveStateBean.setLiftingTableIsNormal(data4.substring(7, 8).equals("0"));
                    }
                }
                if (dataLength > 4) {
                    String data5 = ByteUtil.hexStr2BitArr(data.substring(4, 5));
                    slaveStateBean.setFaultyMotorNumber(data5);
                }
                if (dataLength > 5) {
                    String data6 = ByteUtil.hexStr2BitArr(data.substring(5, 6));
                    slaveStateBean.setPeopleCounting(data6);
                }
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(slaveStateBean);
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }

    /**
     * 温度设置              主  到   从 0x88
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x08
     * B4     ——  控制码     0x88
     * B5     ——  探头编号   1 - 4      1-2：制冷     3-4：制热  0：不加热不制冷
     * B6     ——  最小值
     * B7     ——  最大值
     * B8     ——  校验码
     */
    public void setTemperature(@Priority int priority, int cabinetAddress, int minimum, int maximum, int probeNumber,
                               final ICommonCallback<String> iCommonCallback) {
        byte[] allData = VendingMachineAACmdFactory.setTemperature(cabinetAddress, minimum, maximum, probeNumber);
        send(priority, allData, VendingMachineAAProtocol.TEMPERATURE_SET_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(ByteUtil.hexStr2decimalStr(data.substring(0, 1)));
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }

    /**
     * 温度查询             主  到   从 0x89
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x89
     * B5     ——  探头编号   1 - 4        1-2：制冷     3-4：制热
     * B6     ——  校验码
     */
    public void temperatureQuery(@Priority int priority, int cabinetAddress, int probeNumber,
                                 final ICommonCallback<TemperatureBean> iCommonCallback) {

        byte[] allData = VendingMachineAACmdFactory.temperatureQuery(cabinetAddress, probeNumber);
        send(priority, allData, VendingMachineAAProtocol.TEMPERATURE_QUERY_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                TemperatureBean temperatureBean = new TemperatureBean();
                int dataLength = data.length();
                if (dataLength > 0) {
                    temperatureBean.setProbeNumber(ByteUtil.hexStr2decimalStr(data.substring(0, 1)));
                }
                if (dataLength > 1) {
                    temperatureBean.setActualTemperature(ByteUtil.hexStr2decimalStr(data.substring(1, 2)));
                }
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(temperatureBean);
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }

    /**
     * 固件版本查询        主  到   从0x8B
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x05
     * B4     ——  控制码     0x8B
     * B5     ——  校验码
     */
    public void firmwareVersionQuery(@Priority int priority, int cabinetAddress, final ICommonCallback<String> iCommonCallback) {
        byte[] allData = VendingMachineAACmdFactory.firmwareVersionQuery(cabinetAddress);
        send(priority, allData, VendingMachineAAProtocol.FIRMWARE_VERSION_QUERY_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                if (iCommonCallback != null) {
                    iCommonCallback.onSuccess(ByteUtil.hexStr2decimalStr(data.substring(0, 1)));
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }

    /**
     * 光检控制           主 到 从0x91
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x91
     * B5     ——  控制命令
     * 0：关闭光检
     * 1：开启光检
     * B6     ——  校验码
     */
    public void photometricControl(@Priority int priority, int cabinetAddress, boolean isOpenCandling,
                                   final ICommonCallback<Boolean> iCommonCallback) {
        int candling = isOpenCandling ? 1 : 0;
        byte[] allData = VendingMachineAACmdFactory.photometricControl(cabinetAddress, candling);
        send(priority, allData, VendingMachineAAProtocol.PHOTOMETRIC_CONTROL_STH, iCommonCallback, new SendResultCallback() {

            @Override
            public void onSuccess(DataPack dataPack) {
                DLCLog.d("接收数据:" + dataPack.toString());
                String data = ByteUtil.bytes2HexStr(dataPack.getData());
                if (iCommonCallback != null) {
                    String result = data.substring(0, 1);
                    iCommonCallback.onSuccess(result.equals("1"));
                }
            }

            @Override
            public void onFailed(DLCException dLCException) {
                if (iCommonCallback != null) {
                    iCommonCallback.onFailed(dLCException);
                }
            }
        });
    }
}
