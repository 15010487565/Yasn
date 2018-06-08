package com.yasn.purchase.activity;

import android.os.Bundle;

import com.yasn.purchase.R;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

/**
 * 进货单结算
 * 2018年6月6日 09:25:11
 */
public class ShopcarPayActivity extends SimpleTopbarActivity {

    @Override
    protected Object getTopbarTitle() {
        return "确认订单";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar_pay);
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
