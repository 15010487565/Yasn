package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.view.View;

import www.xcd.com.mylibrary.R;


/**
 * Created by Android on 2017/5/15.
 */
public class MainAddTextTopBtnFunc extends BaseTopTextViewFunc {


    public MainAddTextTopBtnFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.setting;
    }
    /** 功能文本 */
    protected String getFuncText() {
        return "设置";
    }

    protected int getFuncTextRes() {
        return R.string.setting;
    }

    @Override
    public void onclick(View v) {
//        getActivity().startActivity(new Intent(getActivity(), MeAccountSettingActivity.class));
    }
}
