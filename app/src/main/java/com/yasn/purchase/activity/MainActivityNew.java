package com.yasn.purchase.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.yasn.purchase.activityold.LoadWebViewErrListener;
import com.yasn.purchase.activityold.MyWebViewClient;
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.application.YasnApplication;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.fragment.ClassifyFragment;
import com.yasn.purchase.fragment.FindFragment;
import com.yasn.purchase.fragment.HomeFragment;
import com.yasn.purchase.fragment.ShopCarFragment;
import com.yasn.purchase.fragment.ShopFragment;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.model.PresonaModel;
import com.yasn.purchase.utils.MyWebChromeClient2;
import com.yasn.purchase.utils.SerializableUtil;
import com.yasn.purchase.view.BadgeView;
import com.yasn.purchase.wxapi.WXPay;
import com.yonyou.sns.im.util.common.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.config.HttpConfig;
import www.xcd.com.mylibrary.utils.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.view.NoScrollViewPager;
import www.xcd.com.mylibrary.widget.SnsTabWidget;

public class MainActivityNew extends SimpleTopbarActivity implements LoadWebViewErrListener {

    /**
     * 供应商
     * fragment classes
     */
    public static Class<?> fragmentArray[] = {
            HomeFragment.class,
            ClassifyFragment.class,
            FindFragment.class,
            ShopCarFragment.class,
            ShopFragment.class
    };
    /**
     * tabs text
     */
    private static int[] MAIN_TAB_TEXT = new int[]{
            R.string.home,
            R.string.classify,
            R.string.find,
            R.string.order,
            R.string.shop
    };
    /**
     * tabs image normal
     */
    private static int[] MAIN_TAB_IMAGE = new int[]{
            R.mipmap.icon_tab_home,
            R.mipmap.icon_tab_classify,
            R.mipmap.icon_tab_find,
            R.mipmap.icon_tab_order,
            R.mipmap.main_tab_me
    };
    /**
     * tabs image selected
     */
    private static int[] MAIN_TAB_IMAGEHL = new int[]{
            R.mipmap.icon_tab_home_press,
            R.mipmap.icon_tab_classify_press,
            R.mipmap.icon_tab_find_press,
            R.mipmap.icon_tab_order_press,
            R.mipmap.main_tab_me_hl
    };

//    /**
//     * Topbar功能列表
//     */
//    private static Class<?> rightFuncArray[] = {GoodsDetailsTopBtnFunc.class};
    /**
     * fragment列表
     */
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    private NoScrollViewPager viewPager;
    private SnsTabWidget tabWidget;
    private LinearLayout main_bottom;
    private RelativeLayout main_find_view, main_find_viewhl;
    private FrameLayout main_find_frame;
    /**
     * 第一次返回按钮时间
     */
    private long firstTime;
    private int currentItem = 0;

    @Override
    public boolean isTopbarVisibility() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainnew);
        EventBus.getDefault().register(this);
        currentItem = getIntent().getIntExtra("CURRENTITEM", 0);
        initView();
        // 初始化fragments
        initFragments();
        // 初始化ViewPager
        initPager();
        // 初始化Tab
        initTabWidget();
        //实例化webview
        initWebView();
        initData();
        //实例化红点
        resetRedPoint(0, 0);
        resetRedPoint(1, 0);
        resetRedPoint(2, 0);
        resetRedPoint(3, 0);
        resetRedPoint(4, 0);
    }

    public void setCartNum(int cartnum) {
        Log.e("TAG_cartnum", "cartnum=" + cartnum);
        resetRedPoint(3, cartnum);
    }

    private void initView() {
        viewPager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
        tabWidget = (SnsTabWidget) findViewById(R.id.main_tabwidget);
        main_bottom = (LinearLayout) findViewById(R.id.main_bottom);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        main_find_frame = (FrameLayout) findViewById(R.id.main_find_frame);
        main_find_frame.setOnClickListener(this);
        main_find_view = (RelativeLayout) findViewById(R.id.main_find_view);
        main_find_viewhl = (RelativeLayout) findViewById(R.id.main_find_viewhl);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_find_frame:
                main_find_view.setAlpha(0);
                main_find_viewhl.setAlpha(1);
                viewPager.setCurrentItem(2);
                webView.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    /**
     * 获得所有的FragmentClass
     *
     * @return
     */
    protected Class<?>[] getFragmentClassArray() {
        return fragmentArray;
    }

    /**
     * 初始化fragments
     */
    protected void initFragments() {
        // 初始化fragments
        for (int i = 0; i < fragmentArray.length; i++) {
            BaseFragment fragment = null;
            try {
                fragment = (BaseFragment) fragmentArray[i].newInstance();
                fragment.setActivity(this);
            } catch (Exception e) {
            }
            fragmentList.add(fragment);
        }
    }

    /**
     * 初始化ViewPager
     */
    protected void initPager() {

        // adapter
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        // 默认选中第一个
        viewPager.setCurrentItem(currentItem);
        // page change监听
        viewPager.setOnPageChangeListener(new MainPageChangeListener());
    }

    /**
     * 初始化Tab
     */
    protected void initTabWidget() {
        // LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < fragmentArray.length; i++) {
            // 实例化tabitem
            View view = inflater.inflate(R.layout.view_main_tabitem, null);
            // 为每一个Tab按钮设置图标、文字和内容
            setTextViewStyle(view, i, (i == 0));
            tabWidget.addView(view);
        }
        tabWidget.setCurrentTab(currentItem);
        // 添加监听
        tabWidget.setTabSelectionListener(new MainTabSelectionListener());
    }

    /**
     * 重设TextView的样式
     *
     * @param index
     * @param isCur
     */
    protected void setTextViewStyle(View view, int index, boolean isCur) {
        // TextView
        TextView textView = (TextView) view.findViewById(R.id.main_tabitem_text);
        // 设置文字
        textView.setText(MAIN_TAB_TEXT[index]);

        // TextView
        TextView textViewHl = (TextView) view.findViewById(R.id.main_tabitem_texthl);
        // 设置文字
        textViewHl.setText(MAIN_TAB_TEXT[index]);
        if (index != 2) {
            // ImageView
            ImageView imageView = (ImageView) view.findViewById(R.id.main_tabitem_icon);
            // 非高亮图标
            imageView.setImageResource(MAIN_TAB_IMAGE[index]);

            // ImageView
            ImageView imageViewHl = (ImageView) view.findViewById(R.id.main_tabitem_iconhl);
            // 高亮图标
            imageViewHl.setImageResource(MAIN_TAB_IMAGEHL[index]);

            resetTextViewStyle(view, index, isCur);
        }
    }

    /**
     * 重设TextView的样式
     *
     * @param index
     * @param isCur
     */
    protected void resetTextViewStyle(View view, int index, boolean isCur) {
        // 选中页签
        if (isCur) {
            resetTextViewAlpha(view, 1);
        } else {// 未选中页签
            resetTextViewAlpha(view, 0);
        }
    }

    /**
     * 重设TextView的Alpha值
     *
     * @param view
     * @param f
     */
    private void resetTextViewAlpha(View view, float f) {
        if (view == null) {
            return;
        }
        // ViewHl  通过设置透明度实现切换
        View itemViewHl = (View) view.findViewById(R.id.main_tabitem_viewhl);
        itemViewHl.setAlpha(f);
        //通过布局隐藏实现切换
        View itemViewH = (View) view.findViewById(R.id.main_tabitem_view);
        if (f == 1) {
            itemViewH.setVisibility(View.GONE);
        }
        if (f == 0) {
            itemViewH.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 重设页面的Alpha
     *
     * @param index
     * @param f
     */
    private void resetFragmentAlpha(int index, float f) {
        if (index < 0 || index >= fragmentList.size()) {
            return;
        }
        View view = fragmentList.get(index).getView();
        if (view != null) {
            view.setAlpha(f);
        }
    }

    /**
     * 重设红点
     *
     * @param index
     * @param number
     */
    private void resetRedPoint(int index, int number) {
        View view = tabWidget.getChildAt(index);
        // red number
        BadgeView textRedpoint = (BadgeView) view.findViewById(R.id.main_tabitem_redpoint);
        if (index == 3) {
            if (number > 0) {
                if (String.valueOf(number).length() > 2) {
                    textRedpoint.setText("...");
                } else {
                    textRedpoint.setText(String.valueOf(number));
                }
                //隐藏红点
                textRedpoint.setVisibility(View.VISIBLE);
            } else {
                textRedpoint.setText("");
                textRedpoint.setVisibility(View.GONE);
            }
        } else {
            textRedpoint.setVisibility(View.GONE);
        }

    }

    /**
     * 连续按两次返回键就退出
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstTime < 3000) {
            YasnApplication.getInstance().exitApp();
        } else {
            firstTime = System.currentTimeMillis();
            // 再按一次返回桌面

            ToastUtil.showShort(this, R.string.main_press_again_back);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HttpConfig.SUCCESSCODE:
                    Bundle bundle = msg.getData();
                    String returnData = bundle.getString("returnData");
                    setCartNum(Integer.valueOf(returnData));
                    break;
            }
        }
    };

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                PresonaModel presonaModel = JSON.parseObject(returnData, PresonaModel.class);
                PresonaModel.MemberBean member = presonaModel.getMember();
                if (member != null) {
                    int member_id = member.getMember_id();
                    HelpUtils.getGoodsNum(Config.CARTGOODSNUM + member_id, handler);
                }
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

    @Override
    public void onLoadWebviewFail(WebView view, int errorCode, String description, String failingUrl) {
        Log.e("TAG_activity", "errorCode=" + errorCode);
//        webView.setVisibility(View.GONE);
//        viewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadWebviewPageFinished(WebView view, String url) {

    }

    /**
     * tab change监听
     *
     * @author litfb
     * @version 1.0
     * @date 2014年9月23日
     */
    private class MainTabSelectionListener implements SnsTabWidget.ITabSelectionListener {

        @Override
        public void onTabSelectionChanged(int tabIndex) {

            if (tabIndex == 3 || tabIndex == 4) {
                token = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("token");
                resetToken = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("resetToken");
                resetTokenTime = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("resetTokenTime");
                if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
                    if (tabIndex == 3) {
                        webView.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(MainActivityNew.this, WebViewActivity.class);
                        intent.putExtra("webViewUrl", Config.SHOPPCARWEBVIEW);
                        startActivity(intent);
                    } else {
                        webView.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(tabIndex, false);
                    }
                } else {
                    webView.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.GONE);
                    viewPager.setCurrentItem(tabIndex, false);
                }
            } else {
                webView.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(tabIndex, false);
            }

            main_find_view.setAlpha(1);
            main_find_viewhl.setAlpha(0);
            if (!viewPager.hasFocus()) {
                viewPager.requestFocus();
            }
        }
    }

    /**
     * pager adapter
     *
     * @author litfb
     * @version 1.0
     * @date 2014年9月23日
     */
    private class MainPagerAdapter extends FragmentPagerAdapter {

        private FragmentManager manager;

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
            manager = fm;
        }

        @Override
        public Fragment getItem(int paramInt) {

            return fragmentList.get(paramInt);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }

    /**
     * page change监听
     *
     * @author litfb
     * @version 1.0
     * @date 2014年9月23日
     */
    private class MainPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
            int curIndex = tabWidget.getCurIndex();
            // 向右滑
            if (curIndex == paramInt1) {
                resetTextViewAlpha(tabWidget.getChildAt(curIndex), 1 - paramFloat);
                resetFragmentAlpha(curIndex, 1 - paramFloat);
                resetTextViewAlpha(tabWidget.getChildAt(curIndex + 1), paramFloat);
                resetFragmentAlpha(curIndex + 1, paramFloat);
            } else if (curIndex == paramInt1 + 1) {// 向左划
                resetTextViewAlpha(tabWidget.getChildAt(curIndex), paramFloat);
                resetFragmentAlpha(curIndex, paramFloat);
                resetTextViewAlpha(tabWidget.getChildAt(paramInt1), 1 - paramFloat);
                resetFragmentAlpha(paramInt1, 1 - paramFloat);
            }
        }

        @Override
        public void onPageSelected(int index) {
            // tabWidget焦点策略
            int oldFocusability = tabWidget.getDescendantFocusability();
            // 阻止冒泡
            tabWidget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            // 切换tab
            tabWidget.setCurrentTab(index);
            // 重设title
//            resetTitle(index);
            // 变换tab显示
            for (int i = 0; i < fragmentArray.length; i++) {
                View view = tabWidget.getChildAt(i);
                resetTextViewStyle(view, i, (i == index));
            }
            // 还原焦点策略
            tabWidget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int paramInt) {

        }

    }

    @Override
    protected Object getTopbarTitle() {
        return R.string.home;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private BridgeWebView webView;
    MyWebChromeClient2 myWebChromeClient2 = new MyWebChromeClient2(MainActivityNew.this);

    protected void initWebView() {
        webView = (BridgeWebView) findViewById(R.id.mainWebView);
    }

    private void initData() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setUseWideViewPort(false);
        settings.setGeolocationDatabasePath(MainActivityNew.this.getFilesDir().getPath());
        settings.setSavePassword(false);
        webView.addJavascriptInterface(myWebChromeClient2, "android");
        // 设置WebViewClient
        webView.setWebChromeClient(myWebChromeClient2 = new MyWebChromeClient2(this) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                try {
//                    if (newProgress == 100) {
//                        myProgressBar.setVisibility(View.GONE);
//                    } else {
//                        if (View.VISIBLE != myProgressBar.getVisibility()) {
//                            myProgressBar.setVisibility(View.VISIBLE);
//                        }
//                        myProgressBar.setProgress(newProgress);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                super.onProgressChanged(view, newProgress);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setLoadUrl(String webViewUrl) {
        Log.e("TAG_activity", "webViewUrl=" + webViewUrl);
        //取得保存的cookie
        String oldCookie = (String) SerializableUtil.readObject(getFilesDir(), SerializableUtil.COOKIE);
        setCookie(this, ".yasn.com", oldCookie);
        //设置android_client,web端根据这个判断是哪个客户端
        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + "/android_client");
        webView.setDefaultHandler(new DefaultHandler());
        webView.setWebViewClient(new MyWebViewClient(this, webView, this));
        webView.loadUrl(webViewUrl);
        webView.registerHandler("YasnWebRespond", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                WXPay wxPay = new WXPay();
                wxPay.pay(MainActivityNew.this, data);

                function.onCallBack("微信支付中...");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMsg event) {
        String msg = event.getMsg();
        Log.e("TAG_activity", "Main=" + msg);
        if ("login".equals(msg)) {
            webView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            setLoadUrl(Config.LOGINWEBVIEW);
        } else if ("register".equals(msg)) {
            webView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            setLoadUrl(Config.REGISTERWEBVIEW);
        } else if ("loginSucceed".equals(msg)) {
            webView.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            setLoadUrl(Config.LOGINWEBVIEW);
            token = SharePrefHelper.getInstance(this).getSpString("token");
            resetToken = SharePrefHelper.getInstance(this).getSpString("resetToken");
            if (resetToken != null && !"".equals(resetToken)) {
                Map<String, Object> paramsToken = new HashMap<String, Object>();
                paramsToken.put("access_token", resetToken);
                okHttpGet(100, Config.GETPERSONAGEINFO, paramsToken);
            }
        } else if ("shopcar".equals(msg)) {
            webView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("webViewUrl", Config.SHOPPCARWEBVIEW);
            startActivity(intent);
        } else if ("loginout".equals(msg)) {
            removeCookie(this);
           setCartNum(0);
            webView.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0);
//            setLoadUrl(Config.LOGINWEBVIEW);
        } else if ("yasnbang".equals(msg)) {
            removeCookie(this);
            webView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            setLoadUrl(Config.YASNBANG);
        } else if ("webViewShow".equals(msg)) {
            webView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
        } else if ("webViewHide".equals(msg)) {
            webView.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        } else {
            webView.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        }
    }

    private void removeCookie(Context context) {

        CookieSyncManager.createInstance(context);

        CookieManager cookieManager = CookieManager.getInstance();

        cookieManager.removeAllCookie();

        CookieSyncManager.getInstance().sync();

    }
}
