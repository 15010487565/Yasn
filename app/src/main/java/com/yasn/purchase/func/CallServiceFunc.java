package com.yasn.purchase.func;

import android.app.Activity;
import android.view.View;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.OrderDetailsActivity;
import com.yasn.purchase.help.SobotUtil;

import www.xcd.com.mylibrary.func.BaseTopImageBtnFunc;

/**
 * Created by gs on 2017/12/29.
 */

public class CallServiceFunc extends BaseTopImageBtnFunc {

    public CallServiceFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.callservicefunc;
    }

    @Override
    public int getFuncIcon() {
        return R.mipmap.callservice;
    }

    @Override
    public void onclick(View v) {
        if (getActivity() instanceof OrderDetailsActivity){
            OrderDetailsActivity activity = (OrderDetailsActivity)getActivity();
            activity.startSobot();
        }else {
            SobotUtil.startSobot(getActivity(),null);
        }
    }
}
