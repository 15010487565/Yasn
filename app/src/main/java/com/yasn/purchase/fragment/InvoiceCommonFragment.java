package com.yasn.purchase.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.InvoiceModel;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gs on 2018/6/14.
 * 普通发票
 */

public class InvoiceCommonFragment extends SimpleTopbarFragment{

    private EditText etTitle,etInvoiceNum;
    private TextView tvInvoiceCommon;
    @Override
    protected void OkHttpDemand() {

    }

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_invoicecommon;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        RelativeLayout topbat_parent = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);
        etTitle = (EditText) view.findViewById(R.id.et_Title);
        etInvoiceNum = (EditText) view.findViewById(R.id.et_InvoiceNum);
        tvInvoiceCommon = (TextView) view.findViewById(R.id.tv_InvoiceCommon);
        tvInvoiceCommon.setOnClickListener(this);
        Map<String, Object> params = new HashMap();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
            return;
        }
        okHttpGet(100, Config.GETINVOICE+"2", params);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_InvoiceCommon:
                Map<String, String> params = new HashMap();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                } else {
                    ToastUtil.showToast("登录过期，请重新登录");
                    return;
                }
                String title = etTitle.getText().toString().trim();
                if (TextUtils.isEmpty(title)){
                    ToastUtil.showToast("抬头不能为空！");
                    return;
                }else {
                    params.put("title", title );
                }
                String invoiceNum = etInvoiceNum.getText().toString().trim();
                if (TextUtils.isEmpty(title)){
                    ToastUtil.showToast("税号不能为空！");
                    return;
                }else {
                    params.put("invoiceNum", invoiceNum );
                }
                params.put("invoiceType", "2" );
                okHttpPost(101, Config.SAVERECEIPT, params);
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:

                    InvoiceModel invoiceModel = JSON.parseObject(returnData, InvoiceModel.class);
                    InvoiceModel.DataBean data = invoiceModel.getData();
                    if (data != null){
                        String title = data.getTitle();
                        if (!TextUtils.isEmpty(title)){
                            etTitle.setText(title);
                        }
                        String invoiceNum = data.getInvoiceNum();
                        if (!TextUtils.isEmpty(invoiceNum)){
                            etInvoiceNum.setText(invoiceNum);
                        }
                    }

                break;
            case 101:
                if (returnCode == 200) {
                    Intent intent = new Intent();
                    intent.putExtra("invoice", "普通发票"); //将计算的值回传回去
                    getFragmentActivity().setResult(1,intent);
                    getFragmentActivity().finish();
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
