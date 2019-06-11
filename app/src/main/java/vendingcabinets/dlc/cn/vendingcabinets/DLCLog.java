package vendingcabinets.dlc.cn.vendingcabinets;

import android.util.Log;
import com.orhanobut.logger.Logger;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/06  13:53
 */
public class DLCLog {
    private static boolean isLog;

    public static boolean isIsLog() {
        return isLog;
    }

    public static void setIsLog(boolean isLog) {
        DLCLog.isLog = isLog;
    }

    public static void d(String message) {
        if (isLog) {
            Log.d("DLCLog:", message);
        }
    }

    public static void json(String message) {
        if (isLog) {
            Logger.json(message);
        }
    }

    public static void setLog(boolean log) {
        isLog = log;
    }
}
