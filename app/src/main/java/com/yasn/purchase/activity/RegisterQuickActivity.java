package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.CommonHelper;
import com.yasn.purchase.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class RegisterQuickActivity extends SimpleTopbarActivity {

    private EditText etRegisterQuickAccount;
    private TextView tvRegisterQuickOk;
    private int isSmsLogin; //isSmsLogin 0：短信登录 1：快速注册
    @Override
    protected Object getTopbarTitle() {
        return "快速注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_quick);
        Intent intent = getIntent();
        isSmsLogin = intent.getIntExtra("isSmsLogin", 0);
        if (isSmsLogin == 0){
            resetTopbarTitle("短信登录");
        }else if (isSmsLogin == 1){
            resetTopbarTitle("快速注册");
        }else if (isSmsLogin == 2){
            resetTopbarTitle("重置密码");
        }else if (isSmsLogin == 3){//修改密码
            String mobile = intent.getStringExtra("mobile");
            etRegisterQuickAccount.setText(mobile == null ? "" :mobile);
            etRegisterQuickAccount.setEnabled(false);
            resetTopbarTitle("修改密码");
        }
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        etRegisterQuickAccount = (EditText) findViewById(R.id.et_RegisterQuickAccount);

        tvRegisterQuickOk = (TextView) findViewById(R.id.tv_RegisterQuickOk);
        tvRegisterQuickOk.setOnClickListener(this);
    }
    private String trimPhone;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_RegisterQuickOk:
                trimPhone = etRegisterQuickAccount.getText().toString().trim();
                if (TextUtils.isEmpty(trimPhone)){
                    ToastUtil.showToast("手机号不能为空！");
                    return;
                }
                if (!CommonHelper.with().checkPhone(trimPhone)){
                    ToastUtil.showToast("请输入正确手机号！");
                    return;
                }
                //检测手机号是否已注册
                Map<String, Object> params = new HashMap<String, Object>();
                okHttpGet(100, Config.DETECTIONPHONE  + trimPhone, params);
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode){
            case 100:
                if (returnCode == 200){
                    try {
                        JSONObject jsonObject = new JSONObject(returnData);
                        //true：已注册；false：未注册
                        boolean data = jsonObject.optBoolean("data");
                        if (isSmsLogin == 0){//短信登录
                            if (data){//已注册
                                //isSmsLogin 0：短信登录 1：快速注册
                                Intent intent = new Intent(this,RegisterQuickCodeActivity.class);
                                intent.putExtra("mobile",trimPhone);
                                intent.putExtra("isSmsLogin",0);
                                startActivity(intent);
//                                finish();
                            }else {
                                showIsRegisterDialog();
                            }
                        }else if (isSmsLogin == 1){//快速注册
                            if (data){
                                ToastUtil.showToast("该手机号已经注册！");
                            }else {
                                //isSmsLogin 0：短信登录 1：快速注册
                                Intent intent = new Intent(this,RegisterQuickCodeActivity.class);
                                intent.putExtra("mobile",trimPhone);
                                intent.putExtra("isSmsLogin",1);
                                startActivity(intent);
//                                finish();
                            }
                        }else if (isSmsLogin == 2){//重置密码
                            if (data){
                                //isSmsLogin 0：短信登录 1：快速注册 2：重置密码
                                Intent intent = new Intent(this,RegisterQuickCodeActivity.class);
                                intent.putExtra("mobile",trimPhone);
                                intent.putExtra("isSmsLogin",2);
                                startActivity(intent);
//                                finish();
                            }else {
                                showIsRegisterDialog();
                            }
                        } else if (isSmsLogin == 3){//修改密码
                            if (data){
                                //isSmsLogin 0：短信登录 1：快速注册 2：重置密码 3.修改密码
                                Intent intent = new Intent(this,RegisterQuickCodeActivity.class);
                                intent.putExtra("mobile",trimPhone);
                                intent.putExtra("isSmsLogin",3);
                                startActivity(intent);
//                                finish();
                            }else {
                                showIsRegisterDialog();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }
    }


    protected AlertDialog registerDialog;
    private void showIsRegisterDialog() {
        LayoutInflater factor = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_loginorregister, null);

        TextView okbtn = (TextView) serviceView.findViewById(R.id.tv_LoginOrRegisterAgree);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //isSmsLogin 0：短信登录 1：快速注册
                Intent intent = new Intent(RegisterQuickActivity.this,RegisterQuickCodeActivity.class);
                intent.putExtra("mobile",trimPhone);
                intent.putExtra("isSmsLogin",1);
                startActivity(intent);
                registerDialog.dismiss();
                finish();
            }
        });
        TextView refuse = (TextView) serviceView.findViewById(R.id.tv_LoginOrRegisterRefuse);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDialog.dismiss();
            }
        });
        Activity activity = RegisterQuickActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        registerDialog = builder.create();
        registerDialog.show();
        registerDialog.setContentView(serviceView);
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
}
