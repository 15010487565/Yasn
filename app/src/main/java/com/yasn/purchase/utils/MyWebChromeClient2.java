package com.yasn.purchase.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.yasn.purchase.activity.GoodsDetailsActivity;
import com.yasn.purchase.activity.SearchActivity;
import com.yasn.purchase.activityold.MainActivity;
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.help.LoginOut;
import com.yasn.purchase.model.EventBusMsg;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * Created by fanjl on 2016-12-15.
 */
public class MyWebChromeClient2 extends WebChromeClient {

    Activity activity;
    private String imagename;

    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private Uri imageUri;
    File file;
    public MyWebChromeClient2(Activity activity) {
        this.activity = activity;
    }

    //Android 5.0+
    @Override
    public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        mUploadCallbackAboveL = filePathCallback;
        take(imagename);
        return true;
    }
    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        take(imagename);
    }
    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        take(imagename);
    }
    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        Log.e("TAG)WEVVIEW","调用4.1.1");
        take(imagename);
    }


    public void onResult(int requestCode, int resultCode, Intent data,String imagename) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
        }
    }


    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (imageUri!=null){
            ((WebViewActivity)activity).uploadImageAndroid(false,imagename,imageUri.toString());
        }
        return;
    }

    public void take(String imagename) {
        Log.e("TAG_T图片","imagename="+imagename);
        if (imagename==null||"".equals(imagename)||"null".equals(imagename)){
            return;
        }
        ((WebViewActivity)activity).setphotoName(imagename);
        ((WebViewActivity)activity).getChoiceDialog().show();
    }
    /**
     * 选择图片
     *
     */
    @JavascriptInterface
    public void uploadImageSucceed() {
        ((WebViewActivity)activity).uploadImageAndroidSucceed();
    }

    @JavascriptInterface
    public void  getImageName(String imagename) {
        if (imagename==null||"".equals(imagename)||"null".equals(imagename)){
            www.xcd.com.mylibrary.utils.ToastUtil.showToast("获取图片信息失败！");
            return;
        }
        this.imagename = imagename;
        take(imagename);
    }
    @JavascriptInterface
    public void  playVideo(String videoUrl,String videoName) {
        if (videoUrl ==null||"".equals(videoUrl)||"null".equals(videoUrl)){
            www.xcd.com.mylibrary.utils.ToastUtil.showToast("未获取到视频有效链接！");
            return;
        }
        ((WebViewActivity)activity).playVideo(videoUrl,videoName);
    }
    /**
     * 显示分享弹出框
     * @param name 标题
     * @param subname 描述
     * @param imgUrl 图片
     * @param url 链接
     */
    @JavascriptInterface
    public void callNativeShareLogic(String name,String subname,String imgUrl,String url) {
        ((WebViewActivity)activity).onClickShare(name,subname,imgUrl,url);
    }
    @JavascriptInterface
    public void getToken(String token,String resetToken,String resetTokenTime) {
//        ((WebViewActivity)activity).getToken(token,resetToken,resetTokenTime);
        Log.e("TAG_js","token="+token);
        Log.e("TAG_js","resetToken="+resetToken);
        Log.e("TAG_js","resetTokenTime="+resetTokenTime);
        SharePrefHelper.getInstance(activity).putSpString("token", token);
        SharePrefHelper.getInstance(activity).putSpString("resetToken", resetToken);
        SharePrefHelper.getInstance(activity).putSpString("resetTokenTime", resetTokenTime);
        EventBus.getDefault().post(new EventBusMsg("loginSucceed"));
    }
    /**
     * 清楚Cookie
     */
    @JavascriptInterface
    public void clearCookie() {
        Log.e("TAG_Cookie","removeAllCookie");
        LoginOut.startLoginOut(activity);
    }
    @JavascriptInterface
    public void webViewBack(String backString) {
        Log.e("TAG_webViewBack1", "返回按钮backString=" + backString);
    }
    @JavascriptInterface
    public void webViewBack(String backString,String startSourceURL) {
        Log.e("TAG_webViewBack2", "返回按钮backString=" + backString+";startSourceURL="+startSourceURL);
    }
    /**
     * WebView返回键
     * @param backString 要返回的原生页面
     * @param startSourceURL 返回时webview页面
     */
    @JavascriptInterface
    public void webViewBack(String backString,String startSourceURL,String goodsId) {
        Log.e("TAG_webViewBack3","返回按钮backString="+backString+";startSource="+startSourceURL+";goodsI="+goodsId);
        if ("Home".equals(backString)){
            startMainActivity(0);
        }else if ("Category".equals(backString)){
            startMainActivity(1);
        }else if ("Finder".equals(backString)){
            startMainActivity(2);
        }else if ("ShopCar".equals(backString)){
            startMainActivity(3);
        }else if ("PersonCenter".equals(backString)){
            startMainActivity(4);
        }else if ("GoodsDetail".equals(backString)){//商品详情
//            EventBus.getDefault().post(new EventBusMsg("GoodsDetail"));
            Intent intent = new Intent(activity, GoodsDetailsActivity.class);
            intent.putExtra("GOODSID",goodsId);
            activity.startActivity(intent);
        }else if ("SearchResultList".equals(backString)){//搜索列表
//            EventBus.getDefault().post(new EventBusMsg("SearchResultList"));
            Intent intent = new Intent(activity, SearchActivity.class);
            activity.startActivity(intent);
        }else if ("else".equals(backString)){//关闭当前activity
            activity.finish();
        }
        else {
            activity.finish();
//            EventBus.getDefault().post(new EventBusMsg("webViewBack"));
        }
    }
    public void startMainActivity(int currentItem){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("CURRENTITEM",currentItem);
        activity.startActivity(intent);
    }
}
