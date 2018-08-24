package com.yasn.purchase.func;

import android.app.Activity;
import android.view.View;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.CollectActivity;

import www.xcd.com.mylibrary.func.BaseTopTextViewFunc;

/**
 * Created by gs on 2017/12/29.
 */

public class CollectRemoveAllFunc extends BaseTopTextViewFunc {


    public CollectRemoveAllFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.mycollectfunc;
    }

    @Override
    protected int getFuncTextRes() {
        return R.string.collect_removeall;
    }

    @Override
    protected int getFuncBgTextRes() {
        return R.drawable.text_white_black;
    }

    @Override
    public void onclick(View v) {
        ((CollectActivity) getActivity()).removeAllDialog("");
    }
}
