package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yasn.purchase.activityold.LoadWebViewErrListener;
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.application.YasnApplication;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.fragment.ClassifyFragment;
import com.yasn.purchase.fragment.FindTestFragment;
import com.yasn.purchase.fragment.HomeFragment;
import com.yasn.purchase.fragment.ShopCarFragment;
import com.yasn.purchase.fragment.ShopFragment;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.view.BadgeView;
import com.yonyou.sns.im.util.common.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.help.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.view.NoPreloadViewPager;
import www.xcd.com.mylibrary.view.NoScrollPreloadViewPager;
import www.xcd.com.mylibrary.widget.SnsTabWidget;

public class MainActivityNew extends SimpleTopbarActivity implements LoadWebViewErrListener {

    /**
     * 供应商
     * fragment classes
     */
    public static Class<?> fragmentArray[] = {
            HomeFragment.class,
            ClassifyFragment.class,
//            FindFragment.class,
            FindTestFragment.class,
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

    private NoScrollPreloadViewPager viewPager;
    private SnsTabWidget tabWidget;
    private RelativeLayout rlMainError;//错误布局
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
        Log.e("TAG_MAIN", "onCreate=" + currentItem);
        initView();
        // 初始化fragments
        initFragments();
        // 初始化ViewPager
        initPager();
        // 初始化Tab
        initTabWidget();
        //实例化红点
        resetRedPoint(0, 0);
        resetRedPoint(1, 0);
        resetRedPoint(2, 0);
        resetRedPoint(3, 0);
        resetRedPoint(4, 0);
    }

    public void setCartNum(int cartnum) {
        resetRedPoint(3, cartnum);
    }

    private void initView() {
        viewPager = (NoScrollPreloadViewPager) findViewById(R.id.main_viewpager);
        tabWidget = (SnsTabWidget) findViewById(R.id.main_tabwidget);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        rlMainError = (RelativeLayout) findViewById(R.id.rl_mainError);
        rlMainError.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabWidget.getBackground().setAlpha(0);
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
            setTextViewStyle(view, i, (i == currentItem));
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
        // ImageView
        ImageView imageView = (ImageView) view.findViewById(R.id.main_tabitem_icon);
        // ImageView
        ImageView imageViewHl = (ImageView) view.findViewById(R.id.main_tabitem_iconhl);
        // 非高亮图标
        imageView.setImageResource(MAIN_TAB_IMAGE[index]);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        // 高亮图标
        imageViewHl.setImageResource(MAIN_TAB_IMAGEHL[index]);
        RelativeLayout.LayoutParams lpHl = (RelativeLayout.LayoutParams) imageViewHl.getLayoutParams();
        if (index == 2) {
            int dip2px = HelpUtils.imageDip2px(this, 60);
            Log.e("TAG_首页2", "dip2px=" + dip2px);
            lp.width = dip2px;
            lp.height = dip2px;
            lpHl.width = dip2px;
            lpHl.height = dip2px;
        }
        imageView.setLayoutParams(lp);
        imageViewHl.setLayoutParams(lpHl);
        resetTextViewStyle(view, index, isCur);
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


    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {

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
            Log.e("TAG_MAIN", "选择tabIndex=" + tabIndex);
            if (tabIndex == 3 || tabIndex == 4) {
                token = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("token");
                resetToken = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("resetToken");
                resetTokenTime = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("resetTokenTime");
                if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
                    if (tabIndex == 3) {
//                        startWebViewActivity(Config.SHOPPCARWEBVIEW);
                        //原生进货单
                        startActivity(new Intent(MainActivityNew.this, ShopCarActivity.class));
                    } else {
                        viewPager.setCurrentItem(tabIndex, false);
                    }
                } else {
                    startWebViewActivity(Config.LOGINWEBVIEW);
                }
            } else {
                viewPager.setCurrentItem(tabIndex, false);
            }

            if (!viewPager.hasFocus()) {
                viewPager.requestFocus();
            }
        }
    }

    private void startWebViewActivity(String url) {
        Intent intent = new Intent(MainActivityNew.this, WebViewActivity.class);
        intent.putExtra("webViewUrl", url);
        startActivity(intent);
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
    private class MainPageChangeListener implements NoPreloadViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
            int curIndex = tabWidget.getCurIndex();
            Log.e("TAG_Main", "onPageScrolled=" + curIndex);
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
            token = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("token");
            resetToken = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("resetToken");
            resetTokenTime = SharePrefHelper.getInstance(MainActivityNew.this).getSpString("resetTokenTime");
        }

        @Override
        public void onPageSelected(int index) {
            Log.e("TAG_Main", "tabWidgetSelected=" + index);
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
            Log.e("TAG_Main", "onPageScrollStateChanged=" + paramInt);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMsg event) {
        String msg = event.getMsg();
        Log.e("TAG_activity", "Main=" + msg);
        if ("loginout".equals(msg)) {
            setCartNum(0);
            viewPager.setCurrentItem(0);
        } else if ("carNum".equals(msg)) {
            setCartNum(0);

        } else if ("webViewBack".equals(msg)) {//返回页

        } else if ("error".equals(msg)) {
            rlMainError.setVisibility(View.VISIBLE);
        } else if ("Success".equals(msg)) {
            rlMainError.setVisibility(View.GONE);
        }
    }
}
