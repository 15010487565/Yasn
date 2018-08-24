package com.yasn.purchase.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.model.LoginModel;
import com.yasn.purchase.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static com.yasn.purchase.R.id.tv_RegisterQuickVoiceCode;
import static com.yasn.purchase.R.id.tv_RegisterQuickVoiceHint;

public class RegisterQuickCodeActivity extends SimpleTopbarActivity {

    private EditText etRegisterQuickCode;
    private TextView tvRegisterQuickCodeOk, tvRegisterQuickGetCode, tvRegisterQuickResetCode, tvRegisterQuickVoiceCode, tvRegisterQuickVoiceHint;
    private String mobile;
    private int isSmsLogin;
    private int recLen = 60;

    @Override
    protected Object getTopbarTitle() {
        return "快速注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_quickcode);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        isSmsLogin = intent.getIntExtra("isSmsLogin", 0);
        if (isSmsLogin == 0) {
            resetTopbarTitle("短信登录");
        } else if (isSmsLogin == 1) {
            resetTopbarTitle("快速注册");
        } else if (isSmsLogin == 2) {
            resetTopbarTitle("忘记密码");
        } else if (isSmsLogin == 3) {
            resetTopbarTitle("修改密码");
        }
        getVerificationCode(100, "1");
    }

    /**
     * @param codeType 1：短信；2：语音
     *                 key 普通校验："check",
     *                 登录："login",
     *                 绑定："binding",
     *                 注册："register",
     *                 找回密码："back_password",
     *                 修改密码："update_password"
     */
    private void getVerificationCode(int requestCode, String codeType) {

        String key = null;
        if (isSmsLogin == 0) {//短信登录
            key = "login";
        } else if (isSmsLogin == 1) {//快速注册
            key = "register";
        } else if (isSmsLogin == 2) {//忘记密码
            key = "back_password";
        } else if (isSmsLogin == 3) {//修改密码
            key = "update_password";
        }
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(requestCode, Config.SENDPHONECODE + mobile + "/" + key + "/1/" + codeType, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        etRegisterQuickCode = (EditText) findViewById(R.id.et_RegisterQuickCode);
        //获取验证码
        tvRegisterQuickGetCode = (TextView) findViewById(R.id.tv_RegisterQuickGetCode);
        tvRegisterQuickGetCode.setOnClickListener(this);
        tvRegisterQuickGetCode.setVisibility(View.VISIBLE);
        //验证码倒计时
        tvRegisterQuickResetCode = (TextView) findViewById(R.id.tv_RegisterQuickResetCode);
        tvRegisterQuickResetCode.setVisibility(View.INVISIBLE);
        //语音听取验证码
        tvRegisterQuickVoiceCode = (TextView) findViewById(tv_RegisterQuickVoiceCode);
        tvRegisterQuickVoiceCode.setOnClickListener(this);
        tvRegisterQuickVoiceCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvRegisterQuickVoiceCode.getPaint().setAntiAlias(true);//抗锯齿
        tvRegisterQuickVoiceCode.setVisibility(View.GONE);
        //提示
        tvRegisterQuickVoiceHint = (TextView) findViewById(tv_RegisterQuickVoiceHint);
        tvRegisterQuickVoiceHint.setText("验证码已发送，请注意查收。");
        //下一步
        tvRegisterQuickCodeOk = (TextView) findViewById(R.id.tv_RegisterQuickCodeOk);
        tvRegisterQuickCodeOk.setOnClickListener(this);
    }

    String trimCode;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_RegisterQuickGetCode:
                getVerificationCode(100, "1");
                break;
            case R.id.tv_RegisterQuickCodeOk:
                trimCode = etRegisterQuickCode.getText().toString().trim();
                if (TextUtils.isEmpty(trimCode)) {
                    ToastUtil.showToast("验证码不能为空！");
                    return;
                }
                if (trimCode.length()!=6) {
                    ToastUtil.showToast("验证码不正确！");
                    return;
                }
                if (isSmsLogin == 0) {//短信登录
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobile", mobile);
                    params.put("smsCode", trimCode);
                    params.put("type", "2");
                    okHttpPost(103, Config.LOGIN, params);
                } else if (isSmsLogin == 1) {//快速注册校验手机验证码
                    Map<String, Object> params = new HashMap<String, Object>();
                    okHttpGet(102, Config.REGISTERQUICKDETECTIONPHONECODE + trimCode + "/" + mobile + "/register", params);
                } else if (isSmsLogin == 2) {//忘记密码校验手机验证码
                    Map<String, Object> params = new HashMap<String, Object>();
                    okHttpGet(102, Config.REGISTERQUICKDETECTIONPHONECODE + trimCode + "/" + mobile + "/back_password", params);
                } else if (isSmsLogin == 3) {//修改密码校验手机验证码
                    Map<String, Object> params = new HashMap<String, Object>();
                    okHttpGet(102, Config.REGISTERQUICKDETECTIONPHONECODE + trimCode + "/" + mobile + "/update_password", params);

                }
                break;
            case tv_RegisterQuickVoiceCode:  //语音听取验证码
                getVerificationCode(101, "2");
                break;
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if (recLen > 0) {
                if (recLen >= 10) {
                    tvRegisterQuickResetCode.setText("重新获取" + recLen + "s");
                } else {
                    tvRegisterQuickResetCode.setText("重新获取0" + recLen + "s");
                }
                tvRegisterQuickResetCode.setVisibility(View.VISIBLE);
                tvRegisterQuickGetCode.setVisibility(View.INVISIBLE);
                handler.postDelayed(this, 1000);
            } else {
                tvRegisterQuickVoiceHint.setText("没收到短信？");
                tvRegisterQuickVoiceCode.setVisibility(View.VISIBLE);
                tvRegisterQuickResetCode.setVisibility(View.INVISIBLE);
                tvRegisterQuickGetCode.setVisibility(View.VISIBLE);
                recLen = 60;
                tvRegisterQuickVoiceCode.setEnabled(true);
                tvRegisterQuickGetCode.setEnabled(true);
                tvRegisterQuickVoiceHint.setText("没收到短信?");
                tvRegisterQuickVoiceCode.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                tvRegisterQuickResetCode.setVisibility(View.INVISIBLE);
                tvRegisterQuickGetCode.setVisibility(View.VISIBLE);
                recLen = 60;
                tvRegisterQuickVoiceHint.setText("短信验证码已发送，请注意查收");
                tvRegisterQuickVoiceCode.setVisibility(View.GONE);
                ToastUtil.showToast(returnMsg);
                handler.postDelayed(runnable, 1000);
                tvRegisterQuickVoiceCode.setEnabled(false);
                break;
            case 101:
                tvRegisterQuickVoiceHint.setText("请耐心等待，您将会接到语音验证码电话。");
                tvRegisterQuickVoiceCode.setVisibility(View.GONE);
                tvRegisterQuickResetCode.setVisibility(View.INVISIBLE);
                tvRegisterQuickGetCode.setVisibility(View.VISIBLE);
                recLen = 60;
                handler.postDelayed(runnable, 1000);
                tvRegisterQuickGetCode.setEnabled(false);
                ToastUtil.showToast(returnMsg);
                break;
            case 102:
                if (returnCode == 200) {
                    Intent intent;
                    //0:短信登录 1:快速注册 2:忘记密码 3：修改密码
                    if (isSmsLogin == 3) {
                        intent = new Intent(this, RegisterQuickUpdataPwdActivity.class);
                    } else {
                        intent = new Intent(this, RegisterQuickPasswordActivity.class);
                    }
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("smsCode", trimCode);
                    intent.putExtra("isSmsLogin", isSmsLogin);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 103:
                if (returnCode == 200) {
                    LoginModel login = JSON.parseObject(returnData, LoginModel.class);
                    LoginModel.DataBean data = login.getData();
                    String yasn_shop_token = data.getYasn_shop_token();
                    if (!TextUtils.isEmpty(yasn_shop_token)) {
                        yasn_shop_token = yasn_shop_token.replace("\\", "");
                        try {
                            JSONObject jsonObject = new JSONObject(yasn_shop_token);
                            String access_token = jsonObject.optString("access_token");
                            String refresh_token = jsonObject.optString("refresh_token");
                            SharePrefHelper.getInstance(this).putSpString("token", access_token);
                            SharePrefHelper.getInstance(this).putSpString("resetToken", refresh_token);
//                            SharePrefHelper.getInstance(this).putSpString("resetTokenTime", resetTokenTime);
                            EventBus.getDefault().post(new EventBusMsg("loginSucceed"));
                            startActivity(new Intent(this, MainActivity.class));
//                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
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
