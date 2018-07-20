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
import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.PayAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.PayModel;
import com.yasn.purchase.pay.PayConfig;
import com.yasn.purchase.pay.alipay.AuthResult;
import com.yasn.purchase.pay.alipay.OrderInfoUtil2_0;
import com.yasn.purchase.pay.alipay.PayResult;
import com.yasn.purchase.pay.wexin.PrePayIdAsyncTask;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.help.HelpUtils;

import static com.yasn.purchase.pay.PayConfig.RSA2_PRIVATE;
import static com.yasn.purchase.pay.PayConfig.RSA_PRIVATE;


public class PayActivity extends SimpleTopbarActivity implements OnRcItemClickListener {

    RecyclerView rcPay;
    PayAdapter adapter;
    List<PayModel> payModels;
    public static final int SDK_PAY_FLAG = 1000;
    public static final int SDK_AUTH_FLAG = 1001;
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
    /**
     * 支付宝支付业务
     */
    public void payV2() {

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (PayConfig.RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(PayConfig.APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Log.e("TAG_支付宝","orderInfo="+orderInfo);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     */
    public void authV2() {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PayConfig.PID, PayConfig.APPID, PayConfig.TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(PayActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * 微信支付
     *
     * @param payMoney
     * @param payOrder
     */
    private void weixinPay(String payMoney, String payOrder) {

        //微信支付
        payMoney = String.valueOf((int) (Double.valueOf(payMoney) * 100));
        String urlString = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        PrePayIdAsyncTask prePayIdAsyncTask = new PrePayIdAsyncTask(this, payMoney, payOrder);
        prePayIdAsyncTask.execute(urlString);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    payModels = JSON.parseArray(returnData, PayModel.class);
                    Log.e("TAG_支付", "payModels=" + payModels.size());
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
        Log.e("TAG_支付", "position=" + position);
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
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtil.showToast("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtil.showToast( "授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
            }
        }
    };
}
