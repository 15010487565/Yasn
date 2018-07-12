package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.PayAdapter;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.PayModel;
import com.yasn.purchase.pay.PayResult;
import com.yasn.purchase.pay.PrePayIdAsyncTask;
import com.yasn.purchase.pay.ZFBPay;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.help.HelpUtils;

import static com.yasn.purchase.common.Config.PAYTYPE;

public class PayActivity extends SimpleTopbarActivity implements OnRcItemClickListener {

    RecyclerView rcPay;
    PayAdapter adapter;
    List<PayModel> payModels;
    public static final int SDK_PAY_FLAG = 1000;

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
        tvPayMoney.setText("￥"+needPayMoney);
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
        params.put("User-Agent", HelpUtils.getUserAgent()+"/android_client");
        okHttpGet(100, PAYTYPE, params);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
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
                if ("alipayWapPlugin".equals(paymentCfgType)){//支付宝
                    new ZFBPay(handler).buttonAlipay(this, "1",sn);
                }else if ("offline".equals(paymentCfgType)){//转账支付

                }else if ("weixinAppPayPlugin".equals(paymentCfgType)){//微信支付
                    weixinPay("1","1");
                }
                break;
        }
    }

    /**
     * 微信支付
     * @param payMoney
     * @param payOrder
     */
    private void weixinPay(String payMoney,String payOrder) {

        //微信支付
        payMoney = String.valueOf((int)(Double.valueOf(payMoney) * 100));
        String urlString="https://api.mch.weixin.qq.com/pay/unifiedorder";
        PrePayIdAsyncTask prePayIdAsyncTask=new PrePayIdAsyncTask(this,payMoney,payOrder);
        prePayIdAsyncTask.execute(urlString);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    payModels = JSON.parseArray(returnData, PayModel.class);
                    Log.e("TAG_支付","payModels="+payModels.size());
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
    private String paymentCfgType;
    @Override
    public void OnItemClick(View view, int position) {
        Log.e("TAG_支付","position="+position);
        for (int i = 0; i < payModels.size(); i++) {
            PayModel dataBean1 = payModels.get(i);
            if (i == position){
                dataBean1.setCheck(true);
                paymentCfgType = dataBean1.getType();
            }else {
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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    Log.e("TAG_resultStatus", "resultStatus=" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtil.showToast("支付成功");

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtil.showToast("支付结果确认中");
                            finish();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtil.showToast("支付失败");
                        }
                    }
                    break;

                }
            }
        }
    };
}
