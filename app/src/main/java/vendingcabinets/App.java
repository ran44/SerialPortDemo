package vendingcabinets;

import android.app.Application;
import android.os.Handler;
import serialtool.util.PrefHelper;
import vendingcabinets.dlc.cn.vendingcabinets.DLCLog;
import vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils.SharedPreferencesUtil;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class App extends Application {

    private Handler mUiHandler;
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mUiHandler = new Handler();
        initUtils();
        SharedPreferencesUtil.init(this);
        DLCLog.setLog(true);
    }

    private void initUtils() {
        PrefHelper.initDefault(this);
    }

    public static App instance() {
        return sInstance;
    }

    public static Handler getUiHandler() {
        return instance().mUiHandler;
    }
}
