package com.yasn.purchase.func;

import android.app.Activity;
import android.view.View;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.func.BaseTopTextViewFunc;


/**
 * Created by Android on 2017/5/15.
 */
public class InvoiceHintTopBtnFunc extends BaseTopTextViewFunc {


    public InvoiceHintTopBtnFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.invoicehint;
    }
    /** 功能文本 */
    protected String getFuncText() {
        return "发票须知";
    }

    protected int getFuncTextRes() {
        return R.string.invoicehint;
    }

    @Override
    public void onclick(View v) {
//        getActivity().startActivity(new Intent(getActivity(), MeAccountSettingActivity.class));
    }
}
