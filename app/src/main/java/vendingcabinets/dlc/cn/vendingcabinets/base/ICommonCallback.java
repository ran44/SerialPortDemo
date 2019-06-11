package vendingcabinets.dlc.cn.vendingcabinets.base;

import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCException;


/**
 * 接口回调
 *
 * @param <T>
 */
public interface ICommonCallback<T> {
    void onSuccess(T t);

    void onFailed(DLCException dLCException);
}
