package com.yasn.purchase.func;

import android.app.Activity;
import android.view.View;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.OftenShopActivity;
import com.yasn.purchase.utils.ToastUtil;

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
		ToastUtil.showToast("点击联系客服");
        ( (OftenShopActivity)getActivity()).callService();
    }
}
