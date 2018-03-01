package com.yasn.purchase.activityold;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.yasn.purchase.activity.ImgTextDetTXTActivity;
import com.yasn.purchase.superfileview.FileDisplayActivity;
import com.yasn.purchase.utils.SerializableUtil;
import com.yasn.purchase.utils.ToastUtil;

import pub.devrel.easypermissions.EasyPermissions;

public class MyWebViewClient
        extends BridgeWebViewClient
{

    private BridgeWebView webView;
    private Activity activity;
    private LoadWebViewErrListener loadWebViewErrListener;

    public MyWebViewClient(Activity activity, BridgeWebView webView, LoadWebViewErrListener loadWebViewErrListener) {
        super(webView);
        this.webView = webView;
        this.activity = activity;
        this.loadWebViewErrListener = loadWebViewErrListener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url==null){
            ToastUtil.showToast("未获取到有效资源连接！");
            return true;
        }
        //电话
        if (url.startsWith("tel")) {
            Intent telIntent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
            telIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity, "没有拨打电话权限", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            activity.startActivity(telIntent);
            return true;
        }
        else {//跳转原生界面
            Intent intent = null;
            String fileName = null;
            if (url.indexOf("ppt")!=-1){
                String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (!EasyPermissions.hasPermissions(activity, perms)) {
                    EasyPermissions.requestPermissions(activity, "需要访问手机存储权限！", 10086, perms);
                } else {
                    FileDisplayActivity.show(activity, url);
                }
                return true;
            }else if (url.indexOf("pdf")!=-1){
//                fileName = getFileName(url);
                String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (!EasyPermissions.hasPermissions(activity, perms)) {
                    EasyPermissions.requestPermissions(activity, "需要访问手机存储权限！", 10086, perms);
                } else {
                    FileDisplayActivity.show(activity, url);
                }
                return true;
            }else if (url.indexOf("txt")!=-1){
                intent = new Intent(activity, ImgTextDetTXTActivity.class);
            }else if (url.indexOf("doc")!=-1){
                String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (!EasyPermissions.hasPermissions(activity, perms)) {
                    EasyPermissions.requestPermissions(activity, "需要访问手机存储权限！", 10086, perms);
                } else {
                    FileDisplayActivity.show(activity, url);
                }
                return true;
            }else {
                return super.shouldOverrideUrlLoading(view, url);
            }
            intent.putExtra("URL",url);
            intent.putExtra("FILENAME",fileName);
            activity.startActivity(intent);
            return true;
        }
    }
    public String getFileName(String pathandname){

        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1,end);
        }else return null;

    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //保存cookie
        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(url);
        String oldCookie = (String) SerializableUtil.readObject(activity.getFilesDir(), SerializableUtil.COOKIE);
        if (cookies != null && (oldCookie == null || !oldCookie.equals(cookies))) {
//            YasnApplication.getInstance().setUserTag(cookies.substring(cookies.lastIndexOf("=") + 1));
            SerializableUtil.saveObject(cookies, activity.getFilesDir(), SerializableUtil.COOKIE);
        }

        if (!webView.getSettings().getLoadsImagesAutomatically()) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        }

    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Log.e("TAG_加载失败","errorCode="+errorCode+";description="+description+";failingUrl="+failingUrl);
        if (loadWebViewErrListener != null)
            loadWebViewErrListener.onLoadWebviewFail(view, errorCode, description, failingUrl);
    }
    //回调该方法，处理HTTP认证错误
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        Log.e("TAG_加载失败Http","error="+errorResponse.getStatusCode());
    }
    //回调该方法，处理SSL认证错误
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Log.e("TAG_加载失败Ssl","error="+error.getPrimaryError());
        if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
                || error.getPrimaryError() == SslError.SSL_EXPIRED
                || error.getPrimaryError() == SslError.SSL_INVALID
                || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
            handler.proceed();
        } else {
            handler.cancel();
        }
        super.onReceivedSslError(view, handler, error);

    }
    //回调该方法，请求已授权用户自动登录
    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        super.onReceivedLoginRequest(view, realm, account, args);
        Log.e("TAG_加载失败Ssl","realm="+realm+";account="+account+";args="+args);
    }
}
