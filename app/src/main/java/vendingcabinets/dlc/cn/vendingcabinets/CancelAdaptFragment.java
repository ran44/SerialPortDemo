package vendingcabinets.dlc.cn.vendingcabinets;

import android.support.v4.app.Fragment;

import me.jessyan.autosize.internal.CancelAdapt;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * @author :      fangbingran
 * @aescription : todo(这里用一句话描述这个类的作用)
 * @date :        2019/06/10  21:11
 */
public class CancelAdaptFragment extends Fragment implements CustomAdapt {


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }
}
