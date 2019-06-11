package vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority.DEFAULT;
import static vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority.HIGH;
import static vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority.IMMEDTATELY;
import static vendingcabinets.dlc.cn.vendingcabinets.base.serialport.blockingqueue.Priority.LOW;

/**
 * @author :      fangbingran
 * @aescription : 等级
 * @date :        2019/06/05  19:34
 */
@IntDef({LOW, DEFAULT, HIGH, IMMEDTATELY})
@Retention(RetentionPolicy.SOURCE)
public @interface Priority {
    /**
     * 最低等级
     */
    int LOW = 0;
    /**
     * 默认等级
     */
    int DEFAULT = 1;
    /**
     * 高等级
     */
    int HIGH = 2;
    /**
     * 立即执行
     */
    int IMMEDTATELY = 3;
}
