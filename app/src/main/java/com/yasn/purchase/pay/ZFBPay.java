package com.yasn.purchase.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.yasn.purchase.activity.PayActivity;

import java.net.URLEncoder;
import java.util.Map;

import static com.yasn.purchase.pay.PayConfig.PARTNER;
import static com.yasn.purchase.pay.PayConfig.RSA_PRIVATE;
import static com.yasn.purchase.pay.PayConfig.SELLER;

/**
 * Created by Android on 2017/6/27.
 */

public class ZFBPay {

    private Handler handler;
    public ZFBPay(Handler handler) {
        this.handler = handler;
    }

    public  void buttonAlipay(final Activity activity, String money, String out_trade_no) {

        /**
         * money
         * out_trade_no 商户网站唯一订单号
         */
        String orderInfo = getOrderInfo("订单支付", "订单支付", money,out_trade_no);
        Log.e("TAG_支付宝","orderInfo="+orderInfo);
        String sign = sign(orderInfo);
        Log.e("TAG_支付宝","sign="+sign);
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        Log.e("TAG_支付宝","payInfo="+payInfo);
        // 必须异步调用
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(payInfo, true);
                Message msg = handler.obtainMessage();
                msg.what = PayActivity.SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }.start();
    }
    private String getOrderInfo(String subject, String body, String price,String out_trade_no) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + PayConfig.CERT_NOTIFY + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE, false);
    }
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
