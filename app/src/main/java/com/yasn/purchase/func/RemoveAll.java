package com.yasn.purchase.func;

import android.app.Activity;
import android.view.View;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.CollectActivity;
import com.yasn.purchase.utils.ToastUtil;

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
        ToastUtil.showToast("点击删除全部");
        ((CollectActivity) getActivity()).removeAllDialog();
    }
}
