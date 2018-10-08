package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
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
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.help.HelpUtils;


public class PayActivity extends BasePayActivity implements OnRcItemClickListener {

    private RecyclerView rcPay;
    private PayAdapter adapter;
    private List<PayModel> payModels;
    private String paymentCfgType;
    @Override
    protected Object getTopbarTitle() {
        return "选择支付方式";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        //rc线
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.line_gray));
        rcPay = (RecyclerView) findViewById(R.id.rc_Pay);
        rcPay.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PayAdapter(this);
        rcPay.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        rcPay.addItemDecoration(recyclerViewDecoration);
        Intent intent = getIntent();
        String needPayMoney = intent.getStringExtra("needPayMoney");
        //支付金额
        TextView tvPayMoney = (TextView) findViewById(R.id.tv_PayMoney);
        tvPayMoney.setText("￥" + needPayMoney);
        //订单过期时间
        String payTime = intent.getStringExtra("payTime");
        TextView tvPayExpireTime = (TextView) findViewById(R.id.tv_PayExpireTime);
        String minNumberString = String.format(tvPayExpireTime.getText().toString(), payTime);
        SpannableStringBuilder span = new SpannableStringBuilder(minNumberString);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.black_66)), 0, 3,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.black_66)), minNumberString.length() - 15, minNumberString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvPayExpireTime.setText(span);
        //支付金额
        TextView tvOkPay = (TextView) findViewById(R.id.tv_OkPay);
        tvOkPay.setOnClickListener(this);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_OkPay:

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
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    payModels = JSON.parseArray(returnData, PayModel.class);
                    adapter.setData(payModels);
                }
                break;
            case 101:
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
