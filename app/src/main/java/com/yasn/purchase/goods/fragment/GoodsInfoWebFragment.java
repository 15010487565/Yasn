package com.yasn.purchase.goods.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yasn.purchase.R;
import com.yasn.purchase.goods.view.ItemWebView;


/**
 * 图文详情webview的Fragment
 */
public class GoodsInfoWebFragment extends Fragment {

    ItemWebView detailWebView;
    
    private WebSettings webSettings;
    private LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_item_info_web, null);
        detailWebView = (ItemWebView)rootView.findViewById(R.id.detail_webView);
        init();
        return rootView;
    }

    private void init() {
        initWebView();
        initView();
    }

    private void initView() {
        String url = "http://m.okhqb.com/item/description/1000334264.html?fromApp=true";
        detailWebView.setFocusable(false);
        detailWebView.loadUrl(url);
    }

    private void initWebView() {
        webSettings = detailWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        detailWebView.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
