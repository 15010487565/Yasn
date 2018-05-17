package com.yasn.purchasetest.activityold;

import android.webkit.WebView;

/**
 * webview 请求失败回调
 */
public interface LoadWebViewErrListener {

    public void onLoadWebviewFail(WebView view, int errorCode, String description, String failingUrl);

    public void onLoadWebviewPageFinished(WebView view, String url);

}
