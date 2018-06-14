package com.yasn.purchase.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.yasn.purchase.R;

import java.io.IOException;
import java.util.Map;

/**
 * Created by gs on 2018/6/14.
 * 普通发票
 */

public class InvoiceCommonFragment extends SimpleTopbarFragment{

    @Override
    protected void OkHttpDemand() {

    }

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_invoicecommon;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {

    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

    }

    @Override
    public void onCancelResult() {

    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

    }

    @Override
    public void onParseErrorResult(int errorCode) {

    }

    @Override
    public void onFinishResult() {

    }
}
