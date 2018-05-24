package com.yasn.purchasetest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchasetest.activityold.LoadWebViewErrListener;
import com.yasn.purchasetest.activityold.WebViewActivity;
import com.yasn.purchasetest.application.YasnApplication;
import com.yasn.purchasetest.common.Config;
import com.yasn.purchasetest.fragment.ClassifyFragment;
import com.yasn.purchasetest.fragment.FindTestFragment;
import com.yasn.purchasetest.fragment.HomeFragment;
import com.yasn.purchasetest.fragment.ShopCarFragment;
import com.yasn.purchasetest.fragment.ShopFragment;
import com.yasn.purchasetest.model.EventBusMsg;
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
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.view.BadgeView;
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
            R.string.shopcar,
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

//    private NoScrollPreloadViewPager viewPager;
    private SnsTabWidget tabWidget;

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
        // 初始化fragments
        initFragments();
        // 初始化ViewPager
//        initPager();
        // 初始化Tab
        initTabWidget();
        //实例化红点
        resetRedPoint(0, 0);
        resetRedPoint(1, 0);
        resetRedPoint(2, 0);
        resetRedPoint(3, 0);
        resetRedPoint(4, 0);
        clickFragmentBtn(currentItem);
    }

    public void setCartNum(int cartnum) {
        resetRedPoint(3, cartnum);
    }
    private void initView() {
//        viewPager = (NoScrollPreloadViewPager) findViewById(R.id.main_viewpager);
        tabWidget = (SnsTabWidget) findViewById(R.id.main_tabwidget);
        // 为布局添加fragment,开启事物
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tran = fm.beginTransaction();

//        HomeFragment homeFragment = new HomeFragment();
//        ClassifyFragment classifyFragment = new ClassifyFragment();
//        FindTestFragment findTestFragment = new FindTestFragment();
//        ShopCarFragment shopCarFragment = new ShopCarFragment();
//        ShopFragment shopFragment = new ShopFragment();
        //R.id.relative为布局
        tran.add(R.id.frame_content, fragmentList.get(0), "home").show(fragmentList.get(0))
                .add(R.id.frame_content, fragmentList.get(1), "classify").hide(fragmentList.get(1))
                .add(R.id.frame_content, fragmentList.get(2), "findTest").hide(fragmentList.get(2))
                .add(R.id.frame_content, fragmentList.get(3), "shopCar").hide(fragmentList.get(3))
                .add(R.id.frame_content, fragmentList.get(4), "shop").hide(fragmentList.get(4));

        tran.commit();
//        clickFragmentBtn(0);
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        tabWidget.getBackground().setAlpha(0);
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
        initView();
    }

    /**
     * 初始化ViewPager
     */
//    protected void initPager() {
//
//        // adapter
//        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
//        // 默认选中第一个
//        viewPager.setCurrentItem(currentItem);
//        // page change监听
//        viewPager.setOnPageChangeListener(new MainPageChangeListener());
//    }

    /**
     * 初始化Tab
     */
    LinearLayout main_tabitem_find;
    LinearLayout main_tabitem_findH;
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
        //放大版发现
        main_tabitem_find = (LinearLayout) findViewById(R.id.main_tabitem_find);
        main_tabitem_findH = (LinearLayout) findViewById(R.id.main_tabitem_findH);
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
        // 高亮图标
        imageViewHl.setImageResource(MAIN_TAB_IMAGEHL[index]);
        if (index == 2){
            textView.setVisibility(View.INVISIBLE);
            textViewHl.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            imageViewHl.setVisibility(View.INVISIBLE);
        }else {
            textView.setVisibility(View.VISIBLE);
            textViewHl.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            imageViewHl.setVisibility(View.VISIBLE);
        }

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
//                        viewPager.setCurrentItem(tabIndex, false);
                        clickFragmentBtn(tabIndex);
                    }
                } else {
                    startWebViewActivity(Config.LOGINWEBVIEW);
                }
            } else {
//                viewPager.setCurrentItem(tabIndex, false);
                clickFragmentBtn(tabIndex);
            }
        }
    }

    private void clickFragmentBtn(int tabIndex) {
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();

        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == tabIndex){
                if (tabIndex == 2){
                    main_tabitem_find.setAlpha(0);
                    main_tabitem_findH.setAlpha(1);
                }else {
                    main_tabitem_find.setAlpha(1);
                    main_tabitem_findH.setAlpha(0);
                }
                fragmentTransaction.show(fragmentList.get(i));
                resetTextViewAlpha(tabWidget.getChildAt(i),1);
            }else {
                fragmentTransaction.hide(fragmentList.get(i));
                resetTextViewAlpha(tabWidget.getChildAt(i),0);
            }
        }
        fragmentTransaction.commit();
    }

    private void startWebViewActivity(String url) {
        Intent intent = new Intent(MainActivityNew.this, WebViewActivity.class);
        intent.putExtra("webViewUrl", url);
        startActivity(intent);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMsg event) {
        String msg = event.getMsg();
        Log.e("TAG_Main","msg="+msg);
        if ("loginout".equals(msg)) {
            setCartNum(0);
        } else if ("carNum".equals(msg)) {
            Log.e("TAG_Main","CarNum="+event.getCarNum());
            setCartNum(Integer.valueOf(event.getCarNum()));

        } else if ("webViewBack".equals(msg)) {//返回页

        }
    }
    // 保存MyTouchListener接口的列表
    private ArrayList<MainActivityTouchListener> activityTouchListeners = new ArrayList<MainActivityTouchListener>();
    public interface MainActivityTouchListener {
        public void onTouchEvent(MotionEvent event);
    }
    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     * @param listener
     */
    public void registerTouchListener(MainActivityTouchListener listener) {
        activityTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来注销自己的触摸事件的方法
     * @param listener
     */
    public void unRegisterTouchListener(MainActivityTouchListener listener) {
        activityTouchListeners.remove( listener );
    }

    /**
     * 分发触摸事件给所有注册了MainActivityTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MainActivityTouchListener listener : activityTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
