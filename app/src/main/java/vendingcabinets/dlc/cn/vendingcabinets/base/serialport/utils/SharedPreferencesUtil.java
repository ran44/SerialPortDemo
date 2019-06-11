package vendingcabinets.dlc.cn.vendingcabinets.base.serialport.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCException;
import vendingcabinets.dlc.cn.vendingcabinets.base.exception.DLCExceptionCode;


/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/10  09:48
 */
public class SharedPreferencesUtil {
    private SharedPreferences mPreferences;
    private static SharedPreferencesUtil sDefault;


    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        sDefault = new SharedPreferencesUtil(
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()));
    }

    private SharedPreferencesUtil(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    /**
     * 获取默认的PrefUtil实例
     *
     * @return
     */
    public static SharedPreferencesUtil getDefault() {
        checkInit();
        return sDefault;
    }

    /**
     * 检查是否有初始化过Content
     */
    private static void checkInit() {
        if (sDefault == null) {
            throw new DLCException(DLCExceptionCode.SHARED_PREFERENCES_ERROR,
                    "默认SharedPreferencesUtil为null，请先调用SharedPreferencesUtil.init(context)进行初始化");
        }
    }

    /**
     * 获得SharedPreferences.Editor对象
     *
     * @return
     */
    public SharedPreferences.Editor edit() {
        return mPreferences.edit();
    }

    /**
     * 填充int并返回SharedPreferences.Editor对象，
     * 最后需要调用{@link SharedPreferences.Editor#apply()}才能保存数据
     *
     * @param key
     * @param value
     * @return
     */
    public SharedPreferences.Editor putInt(String key, int value) {
        return edit().putInt(key, value);
    }

    /**
     * 直接保存int
     *
     * @param key
     * @param value
     */
    public void saveInt(String key, int value) {
        putInt(key, value).apply();
    }

    /**
     * 获取int数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }
}
