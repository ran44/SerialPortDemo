package vendingcabinets.dlc.cn.vendingcabinets.base.serialport;

import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCException;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  11:25
 */
public interface SerialPortCallback {
    void onOpenError(String deviceAddress, int baudRate, DLCException dLCException);

    void onOpenSuccess();
}
