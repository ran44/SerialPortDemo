package vendingcabinets.dlc.cn.vendingcabinets.vendingmachine.vendingmachineaa;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  19:08
 */
public interface VendingMachineAAProtocol {
    /**
     * 最短字节不包含数据域（帧头(1字节)+机柜地址(1字节)+ 序号(1字节)+帧长度(1字节)+ 控制码(1字节)+ 数据域(n字节)+校验码(1字节)+帧尾（1字节））
     */
    int MIN_PACK_LEN = 7;
    /**
     * 帧头
     */
    byte FRAME_HEADER = (byte) 0xAA;
    //第一个位置
    int FRAME_HEADER_INDEX = 1;
    /**
     * 帧头长度
     */
    int FRAME_HEADER_LEN = 1;

    /**
     * 数据域标识,第六位
     */
    int DATA_LEN_INDEX = 5;

    /**
     * 异或长度
     */
    int OXR_LEN = 1;
    /**
     * 帧尾长度
     */
    int FRAME_TAIL_LEN = 1;
    /**
     * 帧尾长度
     */
    byte FRAME_TAIL = 0x0a;

    /**
     * 应答帧 相关命令码Host to slave
     */
    byte RESPONSE_FRAME_HTS = (byte) 0x81;
    byte RESPONSE_FRAME_STH = (byte) 0x01;
    byte EIGHT_LEN = (byte) 0x08;
    byte SIX_LEN = (byte) 0x06;
    byte FIVE_LEN = (byte) 0x05;

    /**
     * 测试单个驱动单元命令码,Testing a single driver,
     */
    byte TESTING_SINGLE_DRIVER_HTS = (byte) 0x82;
    byte TESTING_SINGLE_DRIVER_STH = (byte) 0x02;
    /**
     * 测试整柜驱动单元命令码 ,Test the whole cabinet drive unit
     */
    byte TESTING_CABINET_DRIVE_HTS = (byte) 0x83;
    byte TESTING_CABINET_DRIVE_STH = (byte) 0x03;
    /**
     * 货道扫描命令码 ,Cargo Track Scanning
     */
    byte CARGO_TRACK_SCANNING_HTS = (byte) 0x85;
    byte CARGO_TRACK_SCANNING_STH = (byte) 0x05;
    /**
     * 出售商品命令码 ,selling goods
     */
    byte SELLING_GOODS_HTS = (byte) 0x86;
    byte SELLING_GOODS_STH = (byte) 0x06;
    /**
     * 从机状态命令码 ,Slave state query
     */
    byte SLAVE_STATE_QUERY_HTS = (byte) 0x87;
    byte SLAVE_STATE_QUERY_STH = (byte) 0x07;
    /**
     * 温度设置命令码 ,temperature_set
     */
    byte TEMPERATURE_SET_HTS = (byte) 0x88;
    byte TEMPERATURE_SET_STH = (byte) 0x08;
    /**
     * 温度查询命令码 ,temperature_query
     */
    byte TEMPERATURE_QUERY_HTS = (byte) 0x89;
    byte TEMPERATURE_QUERY_STH = (byte) 0x09;
    /**
     * 固件版本查询命令码 ,Firmware Version Query
     */
    byte FIRMWARE_VERSION_QUERY_HTS = (byte) 0x8B;
    byte FIRMWARE_VERSION_QUERY_STH = (byte) 0x0B;
    /**
     * 光检控制命令码 ,Photometric control
     */
    byte PHOTOMETRIC_CONTROL_HTS = (byte) 0x91;
    byte PHOTOMETRIC_CONTROL_STH = (byte) 0x11;
}
