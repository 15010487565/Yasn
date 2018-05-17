package com.yasn.purchasetest.wxapi;

import android.content.Context;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;


public class WXPay {

    public static final String APP_ID = "wxfaf772b01b93fb55";
    public static final String PARTNER_ID = "1390929202";
    public static final String API_KEY = "dd3003ce2293c3e7a26c644b22ad5e05";

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI msgApi = null;//= WXAPIFactory.createWXAPI(this, null)

    public void pay(Context activity, String orderDescript) {
        msgApi = WXAPIFactory.createWXAPI(activity, APP_ID, false);

        if (!msgApi.isWXAppInstalled()) {
            Toast.makeText(activity, "未安装微信,无法使用微信支付", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post("-3");
            return;
        }

        PayReq req = new PayReq();
        try {
            JSONObject jsonObject = new JSONObject(orderDescript);
            req.appId = jsonObject.getString("appid");
            req.partnerId = jsonObject.getString("partnerid");
            req.prepayId = jsonObject.getString("prepayid");
            req.packageValue = jsonObject.getString("package");
            req.nonceStr = jsonObject.getString("noncestr");
            req.timeStamp = jsonObject.getString("timestamp");
            req.sign = jsonObject.getString("sign");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 将该app注册到微信
        msgApi.registerApp(APP_ID);
        msgApi.sendReq(req);
    }
}
