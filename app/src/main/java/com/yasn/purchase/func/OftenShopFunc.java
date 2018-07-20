package com.yasn.purchase.func;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.OftenShopActivity;
import com.yasn.purchase.activity.ShopCarActivity;

import www.xcd.com.mylibrary.func.BaseTopImageRedPointFunc;

/**
 * Created by gs on 2017/12/29.
 */

public class OftenShopFunc extends BaseTopImageRedPointFunc {

    public OftenShopFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.oftendhopfunc;
    }

    @Override
    public int getFuncIcon() {
        return R.mipmap.cartnumber;
    }

    @Override
    public void onclick(View v) {
        if (getActivity() instanceof OftenShopActivity){
            OftenShopActivity activity = (OftenShopActivity)getActivity();
            activity.startActivity(new Intent(activity, ShopCarActivity.class));
        }
    }
}
