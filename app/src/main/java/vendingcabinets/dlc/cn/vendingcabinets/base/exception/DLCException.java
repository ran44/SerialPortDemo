package vendingcabinets.dlc.cn.vendingcabinets.base.exception;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  10:48
 */
public class DLCException extends RuntimeException {
    /**
     * 用String，若是int转下就是
     */
    private final String code;


    public DLCException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 异常码
     */
    public String getCode() {
        return code;
    }


}

