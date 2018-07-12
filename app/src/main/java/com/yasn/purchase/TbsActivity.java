package com.yasn.purchase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import static www.xcd.com.mylibrary.utils.SharePrefHelper.context;

/**
 * Created by xiongwenwei@aliyun.com
 * CreateTime: 2017/1/13
 * Note:腾讯浏览服务
 */
public class TbsActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView webView;
//    String url = "https://mapi.alipay.com/gateway.do?_input_charset=utf-8&subject=%E9%9B%85%E6%A3%AE%E8%BD%A6%E5%93%81%E5%AE%9D%E8%AE%A2%E5%8D%95&sign=9abd3c55a5ad978d92af6af20dbfe2d6&notify_url=http%3A%2F%2Fshoptt.yasn.com%3A10800%2Fpay%2Fcallback%2FORDER_alipayWapPlugin_payment-callback%2Fexecute&body=%E8%AE%A2%E5%8D%95%EF%BC%9A152999973822&payment_type=1&out_trade_no=152999973822&partner=2088801989346310&service=alipay.wap.create.direct.pay.by.user&total_fee=2100000.0&return_url=http%3A%2F%2Fshoptt.yasn.com%3A10800%2Fpay%2Freturn%2FORDER_alipayWapPlugin_payment-return%2Fexecute&sign_type=MD5&seller_id=2088801989346310&show_url=http%3A%2F%2Fshoptt.yasn.com%3A10800%2Fpay%2Freturn%2FORDER_alipayWapPlugin_payment-return%2Fexecute";
            String url = "alipays://platformapi/startApp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        TextView tvApp = (TextView) findViewById(R.id.tv_App);
        tvApp.setOnClickListener(this);
        TextView tvH5 = (TextView) findViewById(R.id.tv_H5);
        tvH5.setOnClickListener(this);
        initView();
        loadUrl(url);
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
    }

    private void loadUrl(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (url.startsWith("alipays:") || url.startsWith("alipay")) {
            try {
                boolean visit = checkAliPayInstalled(context);
                if (visit) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            } catch (Exception e) {

            }
        }else {
            webView.loadUrl(url);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                Log.e("TAG_支付5","url======"+url);
                // 获取上下文, H5PayDemoActivity为当前页面
                return shouldUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("TAG_打印日志","网页加载失败onReceivedError");
                Log.e("TAG_加载失败","errorCode="+errorCode+";description="+description+";failingUrl="+failingUrl);
            }
        });
        //进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    Log.e("TAG_打印日志","加载完成onProgressChanged");
                }
            }
        });
    }
    private boolean shouldUrlLoading(WebView view, String url) {
        final Activity context = TbsActivity.this;
        Log.e("TAG_支付1","url="+url);
        // ------  对alipays:相关的scheme处理 -------
        if (url.startsWith("alipays:") || url.startsWith("alipay")) {
            try {
//                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                Log.e("TAG_支付2","visit=");
                boolean visit = checkAliPayInstalled(context);
                Log.e("TAG_支付3","visit="+visit);
                if (visit) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            } catch (Exception e) {
                new AlertDialog.Builder(context)
                        .setMessage("未检测到支付宝客户端，请安装后重试。")
                        .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                            }
                        }).setNegativeButton("取消", null).show();
            }
            return true;
        }
        // ------- 处理结束 -------
        Log.e("TAG_支付4","======");
        if (!(url.startsWith("http") || url.startsWith("https"))) {
            return true;
        }

        view.loadUrl(url);
        return true;
    }

    //判断是否安装支付宝app
    public static boolean checkAliPayInstalled(Context context) {

        try {
            Uri uri = Uri.parse("alipays://platformapi/startApp");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            return componentName != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_App:
                String url = "alipays://platformapi/startApp";
                loadUrl(url);
                break;
            case R.id.tv_H5:
                String url1 = "https://mapi.alipay.com/gateway.do?_input_charset=utf-8&subject=%E9%9B%85%E6%A3%AE%E8%BD%A6%E5%93%81%E5%AE%9D%E8%AE%A2%E5%8D%95&sign=9abd3c55a5ad978d92af6af20dbfe2d6&notify_url=http%3A%2F%2Fshoptt.yasn.com%3A10800%2Fpay%2Fcallback%2FORDER_alipayWapPlugin_payment-callback%2Fexecute&body=%E8%AE%A2%E5%8D%95%EF%BC%9A152999973822&payment_type=1&out_trade_no=152999973822&partner=2088801989346310&service=alipay.wap.create.direct.pay.by.user&total_fee=2100000.0&return_url=http%3A%2F%2Fshoptt.yasn.com%3A10800%2Fpay%2Freturn%2FORDER_alipayWapPlugin_payment-return%2Fexecute&sign_type=MD5&seller_id=2088801989346310&show_url=http%3A%2F%2Fshoptt.yasn.com%3A10800%2Fpay%2Freturn%2FORDER_alipayWapPlugin_payment-return%2Fexecute";

                loadUrl(url1);
                break;
        }
    }
}
