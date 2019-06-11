package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa;

import vendingcabinets.dlc.cn.vendingcabinets.DLCLog;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.ByteUtil;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.CheckUtil;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.SharedPreferencesUtil;
import vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa.bean.CmdBean;

/**
 * @author :      fangbingran
 * @aescription : 命令工程类
 * @date :        2019/06/10  08:56
 */
public class VendingMachineAACmdFactory {
    private static byte[] allCmd(byte cmd, byte frameLength, int cabinetAddress, String data) {
        byte[] frameHeaderByte = {VendingMachineAAProtocol.FRAME_HEADER};
        byte[] commandByte = {cmd};
        byte[] frameLengthByte = {frameLength};
        byte[] frameTailByte = {VendingMachineAAProtocol.FRAME_TAIL};
        String frameHeader = ByteUtil.bytes2HexStr(frameHeaderByte);
        String command = ByteUtil.bytes2HexStr(commandByte);
        String number = ByteUtil.integer2HexStr(getNumber());
        String strFrameLength = ByteUtil.bytes2HexStr(frameLengthByte);
        String frameTail = ByteUtil.bytes2HexStr(frameTailByte);
        String strCabinetAddress = ByteUtil.integer2HexStr(cabinetAddress);
        String notHeaderAndTailStr = strCabinetAddress + number + strFrameLength + command + data;
        String cRc = CheckUtil.getXOR(ByteUtil.hexStr2bytes(notHeaderAndTailStr));
        CmdBean.Builder builder = CmdBean.newBuilder()
                .setFrameHeader(frameHeader)
                .setCabinetAddress(strCabinetAddress)
                .setCommand(command)
                .setNumber(number)
                .setCRC(cRc)
                .setFrameLength(strFrameLength)
                .setFrameTail(frameTail)
                .setData(data);
        DLCLog.d("发送:" + builder.toString());
        return builder.build();
    }

    private static int number = -1;
    private final static String NUMBER = "Vending_Machine_AA_Number";

    private static int getNumber() {
        if (number == -1) {
            number = SharedPreferencesUtil.getDefault().getInt(NUMBER, -1);
        }
        if (number <= 0 || number >= 255) {
            number = 1;
        } else {
            number++;
        }
        SharedPreferencesUtil.getDefault().saveInt(NUMBER, number);
        return number;
    }

    /**
     * 应答帧命令码  0x81
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x01：从机应答主机  0X81：主机应答从机
     * B5     ——  数据       0x01:ACK, 0x02:BUSY
     * B6     ——  校验码
     */
    public static byte[] responseFrame(int cabinetAddress, int data) {
        String strData = ByteUtil.integer2HexStr(data);
        return allCmd(VendingMachineAAProtocol.RESPONSE_FRAME_HTS, VendingMachineAAProtocol.SIX_LEN, cabinetAddress, strData);
    }

    /**
     * 测试单个驱动单元命令码      主 到 从 0x82
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x82
     * B5     ——  电机编号
     * B6     ——  校验码
     */
    public static byte[] testingSingleDrivers(int cabinetAddress, int motorNumber) {
        String strMotorNumber = ByteUtil.integer2HexStr(motorNumber);
        return allCmd(VendingMachineAAProtocol.TESTING_SINGLE_DRIVER_HTS, VendingMachineAAProtocol.SIX_LEN, cabinetAddress, strMotorNumber);
    }

    /**
     * 测试整柜驱动单元命令码      主 到 从 0x83
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x05
     * B4     ——  控制码     0x83
     * B5     ——  校验码
     */
    public static byte[] testingCabinetDrive(int cabinetAddress) {
        return allCmd(VendingMachineAAProtocol.TESTING_CABINET_DRIVE_HTS, VendingMachineAAProtocol.FIVE_LEN, cabinetAddress, "");
    }

    /**
     * 货道扫描命令码              主  到   从 0x85
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x05
     * B4     ——  控制码     0x85
     * B5     ——  校验码
     */
    public static byte[] cargoTrackScanning(int cabinetAddress) {
        return allCmd(VendingMachineAAProtocol.CARGO_TRACK_SCANNING_HTS, VendingMachineAAProtocol.FIVE_LEN, cabinetAddress, "");
    }

    /**
     * 出售商品命令码              主  到  从 0x86
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x86
     * B5     ——  电机编号
     * B6     ——  校验码
     */
    public static byte[] sellingGoods(int cabinetAddress, int motorNumber) {
        String strMotorNumber = ByteUtil.integer2HexStr(motorNumber);
        return allCmd(VendingMachineAAProtocol.SELLING_GOODS_HTS, VendingMachineAAProtocol.SIX_LEN, cabinetAddress, strMotorNumber);
    }

    /**
     * 从机状态查询命令码          主  到  从 0x87
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x05
     * B4     ——  控制码     0x87
     * B5     ——  校验码
     */
    public static byte[] slaveStateQuery(int cabinetAddress) {
        return allCmd(VendingMachineAAProtocol.SLAVE_STATE_QUERY_HTS, VendingMachineAAProtocol.FIVE_LEN, cabinetAddress, "");
    }

    /**
     * 温度设置命令码              主  到   从 0x88
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x08
     * B4     ——  控制码     0x88
     * B5     ——  探头编号   1 - 4      1-2：制冷     3-4：制热  0：不加热不制冷
     * B6     ——  最小值
     * B7     ——  最大值
     * B8     ——  校验码
     */
    public static byte[] setTemperature(int cabinetAddress, int minimum, int maximum, int probeNumber) {
        String maxi = ByteUtil.integer2HexStr(maximum);
        String mini = ByteUtil.integer2HexStr(minimum);
        String strProbeNumber = ByteUtil.integer2HexStr(probeNumber);
        String data = strProbeNumber + mini + maxi;
        return allCmd(VendingMachineAAProtocol.TEMPERATURE_SET_HTS, VendingMachineAAProtocol.EIGHT_LEN, cabinetAddress, data);
    }

    /**
     * 温度查询命令码              主  到   从 0x89
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x89
     * B5     ——  探头编号   1 - 4        1-2：制冷     3-4：制热
     * B6     ——  校验码
     */
    public static byte[] temperatureQuery(int cabinetAddress, int probeNumber) {
        String data = ByteUtil.integer2HexStr(probeNumber);
        return allCmd(VendingMachineAAProtocol.TEMPERATURE_QUERY_HTS, VendingMachineAAProtocol.SIX_LEN, cabinetAddress, data);
    }

    /**
     * 固件版本查询命令码         主  到   从0x8B
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x05
     * B4     ——  控制码     0x8B
     * B5     ——  校验码
     */
    public static byte[] firmwareVersionQuery(int cabinetAddress) {
        return allCmd(VendingMachineAAProtocol.FIRMWARE_VERSION_QUERY_HTS, VendingMachineAAProtocol.FIVE_LEN, cabinetAddress, "");
    }

    /**
     * 光检控制命令码           主 到 从0x91
     * B1     ——  地址       0x01—0X3F
     * B2     ——  序号       主机发给从机的序号(1-255)
     * B3     ——  长度       0x06
     * B4     ——  控制码     0x91
     * B5     ——  控制命令
     * 0：关闭光检
     * 1：开启光检
     * B6     ——  校验码
     */
    public static byte[] photometricControl(int cabinetAddress, int candling) {
        String data = ByteUtil.integer2HexStr(candling);
        return allCmd(VendingMachineAAProtocol.PHOTOMETRIC_CONTROL_HTS, VendingMachineAAProtocol.SIX_LEN, cabinetAddress, data);
    }
}
