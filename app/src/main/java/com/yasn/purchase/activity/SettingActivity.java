package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.help.LoginOut;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class SettingActivity extends SimpleTopbarActivity {

    private LinearLayout llUpdataPwd;
    private TextView tvSettingExit;
    @Override
    protected Object getTopbarTitle() {
        return R.string.setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        llUpdataPwd = (LinearLayout) findViewById(R.id.ll_UpdataPwd);
        llUpdataPwd.setOnClickListener(this);
        tvSettingExit = (TextView) findViewById(R.id.tv_SettingExit);
        tvSettingExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_UpdataPwd:
                String mobile = getIntent().getStringExtra("mobile");
                Intent intent = new Intent(this,RegisterQuickActivity.class);
                intent.putExtra("mobile",mobile);
                intent.putExtra("isSmsLogin",3);
                startActivity(intent);
                break;
            case R.id.tv_SettingExit:
                LoginOut.startLoginOut(this);
                break;
        }
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
