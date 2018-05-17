package com.yasn.purchasetest.func;

import android.app.Activity;
import android.view.View;

import com.yasn.purchasetest.R;
import com.yasn.purchasetest.activity.CollectActivity;

import www.xcd.com.mylibrary.func.BaseTopTextViewFunc;

/**
 * Created by gs on 2017/12/29.
 */

public class RemoveAll extends BaseTopTextViewFunc {


    public RemoveAll(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.mycollect;
    }

    @Override
    protected int getFuncTextRes() {
        return R.string.removeall;
    }

    @Override
    protected int getFuncBgTextRes() {
        return R.drawable.text_white_black;
    }

    @Override
    public void onclick(View v) {
        ((CollectActivity) getActivity()).removeAllDialog();
    }
}
