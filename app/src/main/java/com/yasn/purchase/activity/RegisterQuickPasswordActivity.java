package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class RegisterQuickPasswordActivity extends SimpleTopbarActivity {

    private TextView  tvRegisterNext;
    private EditText  etRegisterPws, etRegisterAgainPws;
    private LinearLayout llRegisterSelect;
    private ImageView ivRegisterSelect;
    private boolean isVisiblePws = false;
    private String mobile;
    private int isSmsLogin;
    @Override
    protected Object getTopbarTitle() {
        return "设置密码";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_quick_password);
        Intent intent = getIntent();
        isSmsLogin = intent.getIntExtra("isSmsLogin", 1);
       if (isSmsLogin == 1) {
            resetTopbarTitle("快速注册");
        } else if (isSmsLogin == 2) {
            resetTopbarTitle("忘记密码");
        }
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //密码
        etRegisterPws = (EditText) findViewById(R.id.et_RegisterPws);
        etRegisterAgainPws = (EditText) findViewById(R.id.et_RegisterAgainPws);
        //下一步
        tvRegisterNext = (TextView) findViewById(R.id.tv_RegisterOk);
        tvRegisterNext.setOnClickListener(this);
        //显示密码
        llRegisterSelect = (LinearLayout) findViewById(R.id.ll_RegisterSelect);
        llRegisterSelect.setOnClickListener(this);
        ivRegisterSelect = (ImageView) findViewById(R.id.iv_RegisterSelect);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_RegisterSelect:
                if (!isVisiblePws){
                    ivRegisterSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                    etRegisterPws.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etRegisterAgainPws.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisiblePws = true;
                }else {
                    ivRegisterSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                    isVisiblePws = false;
                    etRegisterPws.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etRegisterAgainPws.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etRegisterPws.clearFocus();
                etRegisterAgainPws.clearFocus();
                break;
            case R.id.tv_RegisterOk:
                String trimPws = etRegisterPws.getText().toString().trim();
                String trimAgainPws = etRegisterAgainPws.getText().toString().trim();
                if (TextUtils.isEmpty(trimPws)||TextUtils.isEmpty(trimAgainPws)){
                    ToastUtil.showToast("密码不能为空！");
                    return;
                }

                if (!trimPws.equals(trimAgainPws)){
                    ToastUtil.showToast("您两次输入的密码不一致！");
                    return;
                }

                if (trimPws.length()<6){
                    ToastUtil.showToast("密码为6-16位字母、数字组合！");
                    return;
                }

                Intent intent = getIntent();
                mobile = intent.getStringExtra("mobile");
                if (isSmsLogin == 1) {//快速注册
                    String smsCode = intent.getStringExtra("smsCode");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobile", mobile);
                    params.put("password", trimPws);
                    params.put("smsCode", smsCode);
                    params.put("source", "2");
                    okHttpPost(100, Config.REGISTER , params);
                } else if (isSmsLogin == 2) {//忘记密码
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobile", mobile);
                    params.put("password", trimPws);
                    okHttpPost(101, Config.RESETPASSWORD , params);
                }
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode){
            case 100:
                if (returnCode ==200){
                    LoginModel login = JSON.parseObject(returnData,LoginModel.class);
                    LoginModel.DataBean data = login.getData();
                    String yasn_shop_token = data.getYasn_shop_token();
                    if (!TextUtils.isEmpty(yasn_shop_token)){
                        yasn_shop_token = yasn_shop_token.replace("\\","");
                        Log.e("TAG_注冊1","yasn_shop_token="+yasn_shop_token);
                        try {
                            JSONObject jsonObject = new JSONObject(yasn_shop_token);
                            String access_token = jsonObject.optString("access_token");
                            Log.e("TAG_注冊token","access_token="+access_token);
                            String refresh_token = jsonObject.optString("refresh_token");
                            Log.e("TAG_注冊token","refresh_token="+refresh_token);
                            SharePrefHelper.getInstance(this).putSpString("token", access_token);
                            SharePrefHelper.getInstance(this).putSpString("resetToken", refresh_token);
                            EventBus.getDefault().post(new EventBusMsg("loginSucceed"));
                            Intent intent = new Intent(this,AuthorActivity.class);
                            intent.putExtra("mobile",mobile);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101:
                if (returnCode ==200){
                    showAuthDialog();
                }
                break;
        }
    }
    //修改密码弹窗
    protected AlertDialog passwordDialog;

    private void showAuthDialog() {
        if (passwordDialog !=null && passwordDialog.isShowing()){
            return;
        }
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_reason, null);
        TextView tvHint = (TextView) serviceView.findViewById(R.id.tv_Hint);
        tvHint.setText("温馨提示");
        TextView reason = (TextView) serviceView.findViewById(R.id.reason);
        reason.setText("密码已经重置，请使用新密码重新登录！");
        TextView okbtn = (TextView) serviceView.findViewById(R.id.okbtn);
        okbtn.setText("确定");
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterQuickPasswordActivity.this,LoginActivity.class));
                finish();
            }
        });
        Activity activity = RegisterQuickPasswordActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        passwordDialog = builder.create();
        passwordDialog.setCancelable(false);
        passwordDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
        });
        passwordDialog.show();
        passwordDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (passwordDialog !=null && passwordDialog.isShowing())
            passwordDialog.dismiss();
    }
}
