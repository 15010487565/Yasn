package com.yasn.purchase.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BasePayActivity;
import com.yasn.purchase.adapter.PayAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.PayModel;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.help.HelpUtils;

public class MakerPayActivity extends BasePayActivity implements OnRcItemClickListener {

    private TextView tvMakerPayAgreement, tvMakerPaySelectAgreement, tvMakerPay;
    private LinearLayout llMakerPayAgreement;
    private ImageView ivMakerPaySelectAgreement;
    private RecyclerView rcPay;
    private PayAdapter adapter;
    private List<PayModel> payModels;
    private boolean isSelectAgreement = false;
    private String paymentCfgType;

    @Override
    protected Object getTopbarTitle() {
        return "支付中心";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_pay);
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        params.put("User-Agent", HelpUtils.getUserAgent() + "/android_client");
        okHttpGet(100, Config.PAYTYPE, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        tvMakerPayAgreement = (TextView) findViewById(R.id.tv_MakerPayAgreement);
        tvMakerPayAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvMakerPayAgreement.getPaint().setAntiAlias(true);//抗锯齿
        tvMakerPaySelectAgreement = (TextView) findViewById(R.id.tv_MakerPaySelectAgreement);
        tvMakerPaySelectAgreement.setOnClickListener(this);
        ivMakerPaySelectAgreement = (ImageView) findViewById(R.id.iv_MakerPaySelectAgreement);
        ivMakerPaySelectAgreement.setOnClickListener(this);

        llMakerPayAgreement = (LinearLayout) findViewById(R.id.ll_MakerPayAgreement);
        llMakerPayAgreement.setOnClickListener(this);
        //rc线
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.line_gray));
        rcPay = (RecyclerView) findViewById(R.id.rc_MakerPay);
        rcPay.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PayAdapter(this);
        rcPay.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        rcPay.addItemDecoration(recyclerViewDecoration);
        //确认付款
        tvMakerPay = (TextView) findViewById(R.id.tv_MakerPay);
        tvMakerPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_MakerPayAgreement://查看协议
                startActivityForResult(new Intent(this, MakerAgreementActivity.class), 1000);
                break;
            case R.id.tv_MakerPaySelectAgreement://同意协议
                if (!isSelectAgreement) {
                    ivMakerPaySelectAgreement.setBackgroundResource(R.mipmap.checkbox_checked);
                    isSelectAgreement = true;
                } else {
                    ivMakerPaySelectAgreement.setBackgroundResource(R.mipmap.checkbox_unchecked);
                    isSelectAgreement = false;
                }
                break;
            case R.id.iv_MakerPaySelectAgreement://同意协议
                if (!isSelectAgreement) {
                    ivMakerPaySelectAgreement.setBackgroundResource(R.mipmap.checkbox_checked);
                    isSelectAgreement = true;
                } else {
                    ivMakerPaySelectAgreement.setBackgroundResource(R.mipmap.checkbox_unchecked);
                    isSelectAgreement = false;
                }
                break;
            case R.id.tv_MakerPay:
                String sn = getIntent().getStringExtra("sn");
//                if (!TextUtils.isEmpty(paymentCfgType)){
//                    Map<String, Object> params = new HashMap<String, Object>();
//                    if (token != null && !"".equals(token)) {
//                        params.put("access_token", token);
//                    } else if (resetToken != null && !"".equals(resetToken)) {
//                        params.put("access_token", resetToken);
//                    }
//                    params.put("User-Agent", HelpUtils.getUserAgent()+"/android_client");
//                    params.put("paymentCfgType", paymentCfgType);
//                    params.put("sn", sn);

//                    params.put("orderType", "ORDER");
//                    okHttpGet(101, Config.PAY, params);
//                }else {
//                    ToastUtil.showToast("请选择支付方式！");
//                }
//                Intent intent = new Intent(this, WXPayEntryActivity.class);
//                intent.putExtra("sn",sn);
//                intent.putExtra("paymentCfgType",paymentCfgType);
//                startActivity(intent);
                if ("alipayWapPlugin".equals(paymentCfgType)) {//支付宝
                    payV2();
//                    authV2();
                } else if ("offline".equals(paymentCfgType)) {//转账支付

                } else if ("weixinAppPayPlugin".equals(paymentCfgType)) {//微信支付
                    weixinPay("1", "1");
                }
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    payModels = JSON.parseArray(returnData, PayModel.class);
                    Iterator<PayModel> iterator = payModels.iterator();
                    while (iterator.hasNext()) {
                        PayModel next = iterator.next();
                        String type = next.getType();
                        if ("offline".equals(type)) {//创客不支持转账支付
                            iterator.remove();
                        }
                    }
                    adapter.setData(payModels);
                }
                break;
            case 101:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                Log.e("TAG_同意协议", "resultCode=" + resultCode);
                if (resultCode == 1) {
                    if (!isSelectAgreement) {
                        ivMakerPaySelectAgreement.setBackgroundResource(R.mipmap.checkbox_checked);
                        isSelectAgreement = true;
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

    @Override
    public void OnItemClick(View view, int position) {

        for (int i = 0; i < payModels.size(); i++) {
            PayModel dataBean1 = payModels.get(i);
            if (i == position) {
                dataBean1.setCheck(true);
                paymentCfgType = dataBean1.getType();
            } else {
                dataBean1.setCheck(false);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnItemLongClick(View view, int position) {

    }

    @Override
    public void OnClickTabMore(int listPosition) {

    }

    @Override
    public void OnClickRecyButton(int itemPosition, int listPosition) {

    }
}
