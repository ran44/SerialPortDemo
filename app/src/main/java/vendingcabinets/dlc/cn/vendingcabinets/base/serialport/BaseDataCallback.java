package vendingcabinets.dlc.cn.vendingcabinets.base.serialport;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  11:25
 */
public interface BaseDataCallback {

    DataPack checkData(byte[] received, int size);
}
