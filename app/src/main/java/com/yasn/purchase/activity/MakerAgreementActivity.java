package com.yasn.purchase.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;

import java.io.IOException;
import java.util.Map;

public class MakerAgreementActivity extends BaseYasnActivity {

    @Override
    protected Object getTopbarTitle() {
        return "创客协议";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_agreement);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        TextView tvMakerAgreement = (TextView) findViewById(R.id.tv_MakerAgreement);
        tvMakerAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakerAgreementActivity.this.setResult(1);
                finish();
            }
        });
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
