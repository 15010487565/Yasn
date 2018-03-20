package com.yasn.purchase.func;

import android.app.Activity;
import android.view.View;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.OftenShopActivity;

import www.xcd.com.mylibrary.func.BaseTopImageBtnFunc;

/**
 * Created by gs on 2017/12/29.
 */

public class CallService extends BaseTopImageBtnFunc {

    public CallService(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.callservice;
    }

    @Override
    public int getFuncIcon() {
        return R.mipmap.callservice;
    }

    @Override
    public void onclick(View v) {

        ( (OftenShopActivity)getActivity()).callService();
    }
}
