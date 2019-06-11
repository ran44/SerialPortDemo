package vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  20:00
 */
public class CheckUtil {
    /**
     * 异或检验
     *
     * @param bytes
     * @return
     */
    public static String getXOR(byte[] bytes) {
        //byte[] bytes = stringToHexByte(source);  //把普通字符串转换成十六进制字符串
        byte[] mByte = new byte[1];

        for (int i = 0; i < bytes.length - 1; i++) {
            if (i == 0) {
                mByte[0] = (byte) (bytes[i] ^ bytes[i + 1]);
            } else {
                mByte[0] = (byte) (mByte[0] ^ bytes[i + 1]);
            }
        }

        return ByteUtil.bytes2HexStr(mByte);
    }
}
