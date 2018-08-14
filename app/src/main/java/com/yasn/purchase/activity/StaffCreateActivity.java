package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.AlignedTextUtils;
import com.yasn.purchase.utils.CommonHelper;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class StaffCreateActivity extends SimpleTopbarActivity {

    private CheckBox cbPurchase,cbFinance;
    private TextView tvStaffCreateNumber,tvStaffCreate;
    private EditText etStaffCreateNumber;

    @Override
    protected Object getTopbarTitle() {
        return R.string.staff_create;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_create);

    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //采购部
        cbPurchase = (CheckBox) findViewById(R.id.cb_purchase);
//        cbPurchase.setOnCheckedChangeListener(this);
        //财务部
        cbFinance = (CheckBox) findViewById(R.id.cb_finance);
//        cbFinance.setOnCheckedChangeListener(this);
        tvStaffCreateNumber = (TextView) findViewById(R.id.tv_StaffCreateNumber);
        SpannableStringBuilder retailPriceString = AlignedTextUtils.justifyString("手机号", 4);
        tvStaffCreateNumber.setText(retailPriceString);
        etStaffCreateNumber = (EditText) findViewById(R.id.et_StaffCreateNumber);
        //创建
        tvStaffCreate = (TextView) findViewById(R.id.tv_StaffCreate);
        tvStaffCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_StaffCreate:
                String auth = null;
                //采购部
                boolean checked = cbPurchase.isChecked();
                //财务部
                boolean checked1 = cbFinance.isChecked();
                if (!checked && !checked1){
                    ToastUtil.showToast("请选择员工角色！");
                    return;
                }else {
                    if (checked && checked1) {
                        auth = "1,2";
                    }else  if (checked){
                        auth = "1";
                    } else{
                        auth = "2";
                    }
                }
                String trim = etStaffCreateNumber.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    ToastUtil.showToast("请填写手机号");
                    return;
                }else {
                    boolean b = CommonHelper.with().checkPhone(trim);
                    if (!b){
                        ToastUtil.showToast("请填写正确手机号");
                        return;
                    }
                }
                Map<String, Object> params = new HashMap<String, Object>();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                }
                okHttpGet(100, Config.STAFFCREATE + trim + "/" + auth, params);
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode){
            case 100:
                if (returnCode == 200){
                   if (staffCreateDialog ==null || !staffCreateDialog.isShowing()){
                       showStaffCreateDialog();
                   }
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
    //认证弹窗
    protected AlertDialog staffCreateDialog;
    private void showStaffCreateDialog() {
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_staffcreate, null);
        TextView okbtn = (TextView) serviceView.findViewById(R.id.okbtn);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staffCreateDialog.dismiss();
            }
        });
        Activity activity = StaffCreateActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        staffCreateDialog = builder.create();
        staffCreateDialog.show();
        staffCreateDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }
}
