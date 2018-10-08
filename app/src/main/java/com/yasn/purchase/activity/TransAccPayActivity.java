package com.yasn.purchase.activity;

import android.os.Bundle;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;

import java.io.IOException;
import java.util.Map;

/**
 * 2018年6月6日 09:20:20
 * 转账支付
 */
public class TransAccPayActivity extends BaseYasnActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_acc_pay);
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
