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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.help.LoginOut;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class RegisterQuickUpdataPwdActivity extends SimpleTopbarActivity {

    private TextView tvRegisterNext;
    private EditText etOldUpdataPws, etRegisterPws, etRegisterAgainPws;
    private LinearLayout llRegisterSelect;
    private ImageView ivRegisterSelect;
    private boolean isVisiblePws = false;
    private String mobile;
    private int isSmsLogin;

    @Override
    protected Object getTopbarTitle() {
        return "修改密码";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_quick_updatapwd);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //旧密码
        etOldUpdataPws = (EditText) findViewById(R.id.et_OldUpdataPws);
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
        switch (v.getId()) {
            case R.id.ll_RegisterSelect:
                if (!isVisiblePws) {
                    ivRegisterSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                    etOldUpdataPws.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etRegisterPws.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etRegisterAgainPws.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisiblePws = true;
                } else {
                    ivRegisterSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                    isVisiblePws = false;
                    etOldUpdataPws.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etRegisterPws.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etRegisterAgainPws.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etOldUpdataPws.clearFocus();
                etRegisterPws.clearFocus();
                etRegisterAgainPws.clearFocus();
                break;
            case R.id.tv_RegisterOk:
                String trimOld = etOldUpdataPws.getText().toString().trim();
                if (TextUtils.isEmpty(trimOld)) {
                    ToastUtil.showToast("原密码不能为空！");
                    return;
                }
                String trimPws = etRegisterPws.getText().toString().trim();
                String trimAgainPws = etRegisterAgainPws.getText().toString().trim();
                if (TextUtils.isEmpty(trimPws) || TextUtils.isEmpty(trimAgainPws)) {
                    ToastUtil.showToast("密码不能为空！");
                    return;
                }
                if (!trimPws.equals(trimAgainPws)) {
                    ToastUtil.showToast("您两次输入的密码不一致！");
                    return;
                }

                if (trimPws.length()<6){
                    ToastUtil.showToast("密码为6-16位字母、数字组合！");
                    return;
                }

                Intent intent = getIntent();
                mobile = intent.getStringExtra("mobile");

                String smsCode = intent.getStringExtra("smsCode");
                Map<String, String> params = new HashMap<String, String>();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                }
//                params.put("mobile", mobile);
                params.put("oldPassword", trimOld);
                params.put("newPassword", trimPws);
//                params.put("smsCode", smsCode);
//                params.put("source", "2");
                okHttpPost(100, Config.UPDATAPASSWORD, params);

                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    showAuthDialog();
                }else {
                    ToastUtil.showToast(returnMsg);
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
                LoginOut.loginOutClean(RegisterQuickUpdataPwdActivity.this);
                startActivity(new Intent(RegisterQuickUpdataPwdActivity.this, MainActivity.class));
                finish();
            }
        });
        Activity activity = RegisterQuickUpdataPwdActivity.this;
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
    public void onBackPressed() {
//        super.onBackPressed();
        if (passwordDialog !=null && passwordDialog.isShowing()){

        }else {
            finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (passwordDialog != null && passwordDialog.isShowing())
            passwordDialog.dismiss();
    }
}
