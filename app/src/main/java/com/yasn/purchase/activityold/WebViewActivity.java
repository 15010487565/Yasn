package com.yasn.purchase.activityold;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.utils.CommonHelper;
import com.yasn.purchase.utils.MyWebChromeClient2;
import com.yasn.purchase.utils.ReadImgToBinary;
import com.yasn.purchase.utils.SerializableUtil;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.video.activity.PlayActivity;
import com.yasn.purchase.wxapi.WXPay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import www.xcd.com.mylibrary.PhotoActivity;

public class WebViewActivity extends PhotoActivity implements View.OnClickListener, LoadWebViewErrListener{
    private BridgeWebView mWebView;
    private ProgressBar myProgressBar;
    private long curr_time;
    private View errorView;
    private TextView errorText;
    private FrameLayout fragment_layout;

    public String payHtmlUrl = null;
    MyWebChromeClient2 myWebChromeClient2 = new MyWebChromeClient2(this);
    private String webViewUrl;
    @Override
    public boolean isTopbarVisibility() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        if (intent == null){
            webViewUrl = Config.LOGINWEBVIEW;
        }else {
            webViewUrl = getIntent().getStringExtra("webViewUrl");
        }
        initView();
        initData();
        if (savedInstanceState != null) {
            photoName = savedInstanceState.getString("photoName");
        }
    }

    private void initView() {
        mWebView = (BridgeWebView) findViewById(R.id.bridge_webView);
        myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
        errorView = findViewById(R.id.error_page);
        errorText = (TextView) findViewById(R.id.load_again_web);
        fragment_layout = (FrameLayout) findViewById(R.id.fragment_layout);
        errorText.setOnClickListener(this);
    }

    private void initData() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setUseWideViewPort(false);
        settings.setGeolocationDatabasePath(getFilesDir().getPath());
        settings.setSavePassword(false);
        mWebView.addJavascriptInterface(myWebChromeClient2, "android");
        // 设置WebViewClient
        mWebView.setWebChromeClient(myWebChromeClient2 = new MyWebChromeClient2(this) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                try {
                    if (newProgress == 100) {
                        myProgressBar.setVisibility(View.GONE);
                    } else {
                        if (View.VISIBLE != myProgressBar.getVisibility()) {
                            myProgressBar.setVisibility(View.VISIBLE);
                        }
                        myProgressBar.setProgress(newProgress);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onProgressChanged(view, newProgress);
            }

        });

        //取得保存的cookie
        String oldCookie = (String) SerializableUtil.readObject(getFilesDir(), SerializableUtil.COOKIE);
        setCookie(this, webViewUrl, oldCookie);
        //设置android_client,web端根据这个判断是哪个客户端
        mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + "/android_client");
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setWebViewClient(new MyWebViewClient(this, mWebView, this));
        mWebView.loadUrl(webViewUrl);
        mWebView.registerHandler("YasnWebRespond", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                WXPay wxPay = new WXPay();
                wxPay.pay(WebViewActivity.this, data);

                function.onCallBack("微信支付中...");
            }
        });
    }

    @Subscribe
    public void onPayComplete(String payState) {//微信支付
        mWebView.callHandler("onPayComplete", payState, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                payHtmlUrl = mWebView.copyBackForwardList().getItemAtIndex(mWebView.copyBackForwardList().getCurrentIndex() - 1).getUrl();
            }
        });
    }

    public void setCookie(Context context, String url, String value) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            if (value != null && value.contains(";")) {
                String[] cookiepart = value.split(";");
                for (int i = 0; i < cookiepart.length; i++) {
                    cookieManager.setCookie(url, cookiepart[i]);
                }
            } else {
                cookieManager.setCookie(url, value);
            }
            CookieSyncManager.getInstance().sync();
            //            Log.e("setCookie()  ", cookieManager.getCookie(url) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadWebviewFail(WebView view, int errorCode, String description, String failingUrl) {
        mWebView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadWebviewPageFinished(WebView view, String url) {
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {
        //        Log.i(TAG, "delete file path=" + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.load_again_web:
                if (CommonHelper.with().checkNetWork(this)) {
                    errorView.setVisibility(View.GONE);
                    mWebView.loadUrl(webViewUrl);
                } else {
                    ToastUtil.show(this, getResources().getString(R.string.data_failure_tip));
                }
                break;

            case R.id.share_qq:
                ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_QQ, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_qzone:
                ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_QZONE, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_wexin:
                ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_wexinfriends:
                ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE, testBean, ShareConstant.REQUEST_CODE);
                break;
        }
        if (mShareDialog != null && mShareDialog.isShowing()) {
            mShareDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        try {
            if (mWebView != null) {
                mWebView.removeAllViews();
                mWebView.destroy();
                fragment_layout.removeAllViews();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 分享回调处理
         */
        if (requestCode == ShareConstant.REQUEST_CODE) {
            if (data != null) {
                int channel = data.getIntExtra(ShareConstant.EXTRA_SHARE_CHANNEL, -1);
                int status = data.getIntExtra(ShareConstant.EXTRA_SHARE_STATUS, -1);
                switch (status) {
                    /** 成功 **/
                    case ShareConstant.SHARE_STATUS_COMPLETE:

                        break;
                    /** 失败 **/
                    case ShareConstant.SHARE_STATUS_FAILED:
                        if (dialogIsActivity()) {
                            initShareDialog();
                        }
                        break;
                    /** 取消 **/
                    case ShareConstant.SHARE_STATUS_CANCEL:
                        if (dialogIsActivity()) {
                            initShareDialog();
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void uploadImage(List<File> list) {
        super.uploadImage(list);
        // 调用上传
        for (File imagepath : list) {
            String imageurl = imagepath.getPath();
            if (imageurl != null) {
                uploadImageAndroid(false, photoName, imageurl);
            }
        }
    }

    public void onClickShare(String name, String subname, String imgUrl, String url) {
        testBean = new ShareEntity(name, subname);
        testBean.setUrl(url); //分享链接
        testBean.setImgUrl(imgUrl);
        if (dialogIsActivity()) {
            initShareDialog();
        }
    }

//    public void removeAllCookie() {
//        Log.e("TAG_Cookie","removeAllCookie");
//        CookieManager.getInstance().removeAllCookie();
//    }

    public void playVideo(String videoUrl, String videoName) {
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        Intent intent = new Intent(WebViewActivity.this, PlayActivity.class);
//        Intent intent = new Intent(MainActivity.this, PlayEmptyControlActivity.class);
        intent.putExtra("VIDEOURL", videoUrl);
        intent.putExtra("VIDEONAME", videoName);
        startActivity(intent);
        if (version > 5) {
            overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
        }
    }

//    public void getToken(String token, String resetToken, String resetTokenTime) {
//        Log.e("TAG_js","token="+token);
//        Log.e("TAG_js","resetToken="+resetToken);
//        Log.e("TAG_js","resetTokenTime="+resetTokenTime);
//        SharePrefHelper.getInstance(this).putSpString("token", token);
//        SharePrefHelper.getInstance(this).putSpString("resetToken", resetToken);
//        SharePrefHelper.getInstance(this).putSpString("resetTokenTime", resetTokenTime);
//        Intent intent = new Intent();
//        intent.putExtra("token", token);
//        intent.putExtra("resetToken", resetToken);
//        intent.putExtra("resetTokenTime", resetTokenTime);
//        this.setResult(Activity.RESULT_OK, intent);
//        finish();
//    }

    public void uploadImageAndroid(boolean pagefinsh, final String imagename, final String imageUri) {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                if (imageUri != null) {
                    //将图片转化为字符串
                    String imagebase64 = ReadImgToBinary.imgToBase64(imageUri);
                    mWebView.loadUrl("javascript:getImg('" + photoName + "','" + imagebase64 + "')");
                }
            }
        });
    }

    public void uploadImageAndroidSucceed() {
        dialogDissmiss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imagename", photoName);
    }


    @Override
    public void setphotoName(String photoName) {
        super.setphotoName(photoName);
        this.photoName = photoName;
    }

    /**
     * 初始化分享弹出框
     */
    Dialog mShareDialog;
    TextView share_wexin, share_wexinfriends, share_qq, share_qzone;
    private ShareEntity testBean;

    private void initShareDialog() {
        mShareDialog = new Dialog(this, R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true);
        mShareDialog.setCancelable(true);
        Window window = mShareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);
        View view = View.inflate(this, R.layout.lay_share, null);
        share_wexin = (TextView) view.findViewById(R.id.share_wexin);
        share_wexin.setOnClickListener(this);
        share_wexinfriends = (TextView) view.findViewById(R.id.share_wexinfriends);
        share_wexinfriends.setOnClickListener(this);
        share_qq = (TextView) view.findViewById(R.id.share_qq);
        share_qq.setOnClickListener(this);
        share_qzone = (TextView) view.findViewById(R.id.share_qzone);
        share_qzone.setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        mShareDialog.show();
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    @Override
    public void onBackPressed() {
        Log.e("TAG_wei返回","Url="+mWebView.getUrl());
        if (mWebView != null && mWebView.canGoBack()) {

            //首页四个按钮(页面)
            String tempUrl = mWebView.copyBackForwardList().getItemAtIndex(mWebView.copyBackForwardList().getCurrentIndex()).getUrl();
            Log.e("TAG_wei返回","tempUrl="+tempUrl);
            if (webViewUrl.equals(tempUrl)) {
                this.finish();
            } else {
                if (mWebView.copyBackForwardList().getCurrentIndex() - 2 > 0) {//支付页面
                    String url = mWebView.copyBackForwardList().getItemAtIndex(mWebView.copyBackForwardList().getCurrentIndex() - 2).getUrl();
                    if (url.equals(payHtmlUrl)) {
                        mWebView.clearHistory();
                        mWebView.loadUrl(webViewUrl);
                    } else {
                        mWebView.goBack();
                    }
                } else {
                    mWebView.goBack();
                }
            }
        } else {
            this.finish();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMsg event) {
        String msg = event.getMsg();
        Log.e("TAG_activity","webview="+msg);
        if ("loginout".equals(msg)){
           finish();
        }
    }
}
