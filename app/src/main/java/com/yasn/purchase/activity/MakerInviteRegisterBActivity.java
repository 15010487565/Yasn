package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class MakerInviteRegisterBActivity extends SimpleTopbarActivity {

    private TextView tvMakerRegGetCode, tvMakerRegNext;
    private EditText etMakerRegCode, etMakerRegPws, etMakerRegAgainPws;
    private String makerPhone;///手机号
    private int recLen = 60;
    private LinearLayout llMakerSelect;
    private ImageView ivMakerSelect;
    //是否显示密码
    private boolean isVisiblePws = false;
    @Override
    protected Object getTopbarTitle() {
        return "邀请注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_invite_registerb);
        Intent intent = getIntent();
        makerPhone = intent.getStringExtra("MAKERPHONE");
        getVerificationCode(1);
    }

    private void getVerificationCode(int codeType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(100,  Config.SENDPHONECODE + makerPhone + "/register/1/" + codeType, params);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if (recLen>0){
                tvMakerRegGetCode.setText("重新获取" + recLen + "s");
                tvMakerRegGetCode.setEnabled(false);
                tvMakerRegGetCode.setTextSize(12);
                tvMakerRegGetCode.setBackgroundResource(R.drawable.text_black99_blacke0);
                tvMakerRegGetCode.setTextColor(ContextCompat.getColor(MakerInviteRegisterBActivity.this,R.color.white));
                handler.postDelayed(this, 1000);
            }else {
                tvMakerRegGetCode.setText("重新获取");
                tvMakerRegGetCode.setBackgroundResource(R.drawable.text_n_white);
                tvMakerRegGetCode.setTextColor(ContextCompat.getColor(MakerInviteRegisterBActivity.this,R.color.white));
                tvMakerRegGetCode.setTextSize(16);
                tvMakerRegGetCode.setEnabled(true);
                recLen = 60;
            }
        }
    };

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        etMakerRegCode = (EditText) findViewById(R.id.et_MakerRegCode);
        //密码
        etMakerRegPws = (EditText) findViewById(R.id.et_MakerRegPws);
        etMakerRegAgainPws = (EditText) findViewById(R.id.et_MakerRegAgainPws);
        //获取验证码
        tvMakerRegGetCode = (TextView) findViewById(R.id.tv_MakerRegGetCode);
        tvMakerRegGetCode.setOnClickListener(this);
        //下一步
        tvMakerRegNext = (TextView) findViewById(R.id.tv_MakerRegNext);
        tvMakerRegNext.setOnClickListener(this);
        //显示密码
        llMakerSelect = (LinearLayout) findViewById(R.id.ll_MakerSelect);
        llMakerSelect.setOnClickListener(this);
        ivMakerSelect = (ImageView) findViewById(R.id.iv_MakerSelect);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.tv_MakerRegGetCode:
                tvMakerRegGetCode.setEnabled(false);
                recLen = 60;
                getVerificationCode(1);
                break;

            case R.id.ll_MakerSelect:
                if (!isVisiblePws){
                    ivMakerSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                    etMakerRegPws.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etMakerRegAgainPws.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisiblePws = true;
                }else {
                    ivMakerSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                    isVisiblePws = false;
                    etMakerRegPws.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etMakerRegAgainPws.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etMakerRegPws.clearFocus();
                etMakerRegAgainPws.clearFocus();
                break;

            case R.id.tv_MakerRegNext:

                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200){
                    handler.postDelayed(runnable, 1000);
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }
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
