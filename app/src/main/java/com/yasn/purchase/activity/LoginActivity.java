package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.model.LoginModel;
import com.yasn.purchase.utils.Code;
import com.yasn.purchase.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

public class LoginActivity extends SimpleTopbarActivity {

    private TextView tvLoginVisible, tvLoginOk, tvLoginQuickRegister, tvLoginForgetPassword, tvLoginSms;
    private ImageView ivLoginVisible, ivLoginCode;
    private EditText etLoginAccount, etLoginPassword, etLoginCode;
    private boolean isVisiblePws = false;

    @Override
    protected Object getTopbarTitle() {
        return "会员登录";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(100, Config.VERIFICATIONCODE, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        tvLoginVisible = (TextView) findViewById(R.id.tv_LoginVisible);
        tvLoginVisible.setOnClickListener(this);
        ivLoginVisible = (ImageView) findViewById(R.id.iv_LoginVisible);
        ivLoginVisible.setOnClickListener(this);
        ivLoginCode = (ImageView) findViewById(R.id.iv_LoginCode);
        ivLoginCode.setOnClickListener(this);

        etLoginAccount = (EditText) findViewById(R.id.et_LoginAccount);
        etLoginPassword = (EditText) findViewById(R.id.et_LoginPassword);
        etLoginCode = (EditText) findViewById(R.id.et_LoginCode);
        //登录按钮
        tvLoginOk = (TextView) findViewById(R.id.tv_LoginOk);
        tvLoginOk.setOnClickListener(this);
        //忘记密码
        tvLoginForgetPassword = (TextView) findViewById(R.id.tv_LoginForgetPassword);
        tvLoginForgetPassword.setOnClickListener(this);
        //短信登录
        tvLoginSms = (TextView) findViewById(R.id.tv_LoginSms);
        tvLoginSms.setOnClickListener(this);
        //快速注册
        tvLoginQuickRegister = (TextView) findViewById(R.id.tv_LoginQuickRegister);
        tvLoginQuickRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_LoginVisible:
                setEtLoginPasswordVisible(isVisiblePws);
                break;
            case R.id.tv_LoginVisible:
                setEtLoginPasswordVisible(isVisiblePws);
                break;
            case R.id.tv_LoginOk:
                String realCode = Code.getInstance().getCode();
                String trim = etLoginCode.getText().toString().trim();
                if (!realCode.equals(trim)){
                    ToastUtil.showToast("请输入正确验证码!");
                    return;
                }
                String trimAccount = etLoginAccount.getText().toString().trim();
                if (TextUtils.isEmpty(trimAccount)){
                    ToastUtil.showToast("登录账号不能为空!");
                    return;
                }
                String trimPassword = etLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(trimPassword)){
                    ToastUtil.showToast("登录密码不能为空!");
                    return;
                }
                if (trimPassword.length()<6){
                    ToastUtil.showToast("登录密码不正确!");
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", trimAccount);
                params.put("password", trimPassword);
                params.put("type", "1");
                okHttpPost(101, Config.LOGIN , params);
                break;
            case R.id.iv_LoginCode://重新获取校验码
                Map<String, Object> paramsCode = new HashMap<String, Object>();
                okHttpGet(100, Config.VERIFICATIONCODE, paramsCode);
                break;
            case R.id.tv_LoginForgetPassword://忘记密码
                startRegisterQuickActivitiy(2);
                break;
            case R.id.tv_LoginSms://短信登录
                startRegisterQuickActivitiy(0);
                break;
            case R.id.tv_LoginQuickRegister://快速注册
                startRegisterQuickActivitiy(1);
                break;
        }
    }

    private void startRegisterQuickActivitiy(int value) {
        Intent intent0 = new Intent(this, RegisterQuickActivity.class);
        //isSmsLogin 0：短信登录 1：快速注册 2:忘记密码
        intent0.putExtra("isSmsLogin", value);
        startActivity(intent0);
//        finish();
    }

    private void setEtLoginPasswordVisible(boolean isVisible) {
        if (!isVisible) {
            ivLoginVisible.setBackgroundResource(R.mipmap.checkbox_checked);
            etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isVisiblePws = true;
        } else {
            ivLoginVisible.setBackgroundResource(R.mipmap.checkbox_unchecked);
            isVisiblePws = false;
            etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etLoginAccount.clearFocus();
        etLoginPassword.clearFocus();
        etLoginCode.clearFocus();
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(returnData);
                        String data = jsonObject.optString("data");
                        ivLoginCode.setImageBitmap(Code.getInstance().createBitmap(data));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101:
                if (returnCode == 200){
                    LoginModel login = JSON.parseObject(returnData,LoginModel.class);
                    LoginModel.DataBean data = login.getData();
                    String yasn_shop_token = data.getYasn_shop_token();
                    if (!TextUtils.isEmpty(yasn_shop_token)){
                        yasn_shop_token = yasn_shop_token.replace("\\","");
                        Log.e("TAG_登录1","yasn_shop_token="+yasn_shop_token);
                        try {
                            JSONObject jsonObject = new JSONObject(yasn_shop_token);
                            String access_token = jsonObject.optString("access_token");
                            Log.e("TAG_登录token","access_token="+access_token);
                            String refresh_token = jsonObject.optString("refresh_token");
                            Log.e("TAG_登录token","refresh_token="+refresh_token);
                            SharePrefHelper.getInstance(this).putSpString("token", access_token);
                            SharePrefHelper.getInstance(this).putSpString("resetToken", refresh_token);
//                            SharePrefHelper.getInstance(this).putSpString("resetTokenTime", resetTokenTime);
                            startActivity(new Intent(this,MainActivity.class));
                            EventBus.getDefault().post(new EventBusMsg("loginSucceed"));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
