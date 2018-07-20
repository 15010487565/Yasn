package com.yasn.purchase.pay.wexin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yasn.purchase.pay.PayConfig;
import com.yasn.purchase.utils.ToastUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Created by Android on 2017/6/27.
 */


public class PrePayIdAsyncTask
        extends AsyncTask<String, Void, Map<String, String>>
{
    private ProgressDialog dialog;
    private Context context;
    private  String paymoney;
    private String payorder;
    private StringBuffer sb;
    private PayReq req;
    private Map<String,String> resultunifiedorder;
    private IWXAPI api;
    private String appid;
    public PrePayIdAsyncTask(Context context, String appid
            ,String partnerid,String packageValue,String noncestr
    ,String timestamp,String prepayid,String sign) {
        this.context = context;
        this.appid = appid;
        api = WXAPIFactory.createWXAPI(context, appid);
        api.registerApp(appid);

        sb=new StringBuffer();
        req=new PayReq();
        Log.e("TAG_威信支付","partnerId="+partnerid);
        req.appId = appid;
        req.partnerId = partnerid;
        req.prepayId = prepayid;
        req.packageValue = packageValue;
        req.nonceStr = noncestr;
        req.timeStamp = timestamp;
        req.sign = sign;
        api.sendReq(req);
    }
    public PrePayIdAsyncTask(Context context, String paymoney, String payorder) {
        this.context = context;
        this.paymoney = paymoney;
        this.payorder = payorder;
        api = WXAPIFactory.createWXAPI(context, PayConfig.WEIXIN_APP_ID);
        sb=new StringBuffer();
        req=new PayReq();
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "提示", "正在提交订单");

    }

    @Override
    protected Map<String, String> doInBackground(String... params) {
        // TODO Auto-generated method stub
        String url = String.format(params[0]);
        String entity = getProductArgs(paymoney, payorder);
        Log.e("TAG_", "异步" + entity);
        byte[] buf = Util.httpPost(url, entity);
        String content = new String(buf);
        Log.e("TAG_", "content" + content);
        Map<String, String> xml = decodeXml(content);

        return xml;
    }

    @Override
    protected void onPostExecute(Map<String, String> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (dialog != null) {
            dialog.dismiss();
        }
        sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
        Log.e("TAG_", sb.toString());
        resultunifiedorder = result;
        genPayReq();//得到生成签名参数
        sendPayReq();
    }
    private void genPayReq() {

        req.appId = PayConfig.WEIXIN_APP_ID;
        req.partnerId = PayConfig.MCH_ID;
        if (resultunifiedorder!=null) {
            req.prepayId = resultunifiedorder.get("prepay_id");
            req.packageValue = "prepay_id="+resultunifiedorder.get("prepay_id");
        }
        else {
            ToastUtil.showToast("prepayid为空");
        }
        req.nonceStr = getNonceStr();
        req.timeStamp = String.valueOf( System.currentTimeMillis() / 1000);

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);

        Log.e("TAG_Simon", "----"+signParams.toString());
    }
    /*
	 * 调起微信支付
	 */
    private void sendPayReq() {
        api.registerApp(appid);
        api.sendReq(req);
        Log.e("TAG_", req.partnerId);
    }
    public Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("TAG_","异常"+e.toString());
        }
        return null;

    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(PayConfig.API_KEY_WEXIN);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("Simon","----"+appSign);
        return appSign;
    }
    private String getProductArgs(String money,String order) {
        // TODO Auto-generated method stub
        StringBuffer xml=new StringBuffer();
        try {
            String nonceStr=getNonceStr();
            Log.e("TAG_","nonceStr="+nonceStr);
            xml.append("<xml>");
            List<NameValuePair> packageParams=new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", PayConfig.WEIXIN_APP_ID));//应用ID
            packageParams.add(new BasicNameValuePair("body", "权卡商户端-微信支付"));//商品描述
            packageParams.add(new BasicNameValuePair("mch_id", PayConfig.MCH_ID));//商户号
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));//随机字符串
            packageParams.add(new BasicNameValuePair("notify_url", PayConfig.CERT_NOTIFYWEXIN));//写你们的回调地址
            packageParams.add(new BasicNameValuePair("out_trade_no",order));//商户订单号
            packageParams.add(new BasicNameValuePair("total_fee", money));//总金额
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));//交易类型
            String sign=getPackageSign(packageParams);
//            String sign="6fef2cdbbdbada41d331b400f7aa10ef";
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlString=toXml(packageParams);
            return new String(xmlString.toString().getBytes(), "ISO8859-1");

        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    //生成随机号，防重发
    private String getNonceStr() {
        // TODO Auto-generated method stub
        Random random=new Random();

        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    /**
     生成签名
     */

    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(PayConfig.API_KEY_WEXIN);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("TAG_","签名="+packageSign);
        return packageSign;
    }
    /*
     * 转换成xml
     */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");


            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");

        Log.e("TAG_","转换成xml_"+sb.toString());
        return sb.toString();
    }
}

