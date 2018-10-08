package com.yasn.purchase.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.activityold.LoadWebViewErrListener;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.application.YasnApplication;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.fragment.ClassifyFragment;
import com.yasn.purchase.fragment.FindTestFragment;
import com.yasn.purchase.fragment.HomeFragment;
import com.yasn.purchase.fragment.ShopCarFragment;
import com.yasn.purchase.fragment.ShopFragment;
import com.yasn.purchase.model.EventBusMsg;
import com.yonyou.sns.im.util.common.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.view.BadgeView;
import www.xcd.com.mylibrary.widget.SnsTabWidget;

public class MainActivity extends CheckPermissionsActivity implements LoadWebViewErrListener {

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
//        Log.e("TAG_MAIN", "onCreate=" + currentItem);
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
        SharePrefHelper.getInstance(MainActivity.this).putSpInt("carNum", cartnum);
    }

    private void initView() {
//        viewPager = (NoScrollPreloadViewPager) findViewById(R.id.main_viewpager);
        tabWidget = (SnsTabWidget) findViewById(R.id.main_tabwidget);
        // 为布局添加fragment,开启事物
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tran = fm.beginTransaction();

        //R.id.relative为布局
        tran.add(R.id.frame_content, fragmentList.get(0), "home").show(fragmentList.get(0))
                .add(R.id.frame_content, fragmentList.get(1), "classify").hide(fragmentList.get(1))
                .add(R.id.frame_content, fragmentList.get(2), "findTest").hide(fragmentList.get(2))
                .add(R.id.frame_content, fragmentList.get(3), "shopCar").hide(fragmentList.get(3))
                .add(R.id.frame_content, fragmentList.get(4), "shop").hide(fragmentList.get(4));

        tran.commit();
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
        if (index == 2) {
            textView.setVisibility(View.INVISIBLE);
            textViewHl.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            imageViewHl.setVisibility(View.INVISIBLE);
        } else {
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
//            Log.e("TAG_MAIN", "选择tabIndex=" + tabIndex);
            if (tabIndex == 3 || tabIndex == 4) {
                token = SharePrefHelper.getInstance(MainActivity.this).getSpString("token");
                resetToken = SharePrefHelper.getInstance(MainActivity.this).getSpString("resetToken");
                resetTokenTime = SharePrefHelper.getInstance(MainActivity.this).getSpString("resetTokenTime");
                if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
                    if (tabIndex == 3) {
//                        startWebViewActivity(Config.SHOPPCARWEBVIEW);
                        //原生进货单
                        startActivity(new Intent(MainActivity.this, ShopCarActivity.class));
                    } else {
                        clickFragmentBtn(tabIndex);
                    }
                } else {
                    startBaseActivity(MainActivity.this,LoginActivity.class);
                }
            } else {
                clickFragmentBtn(tabIndex);
            }
        }
    }

    private void clickFragmentBtn(int tabIndex) {
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();

        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == tabIndex) {
                if (tabIndex == 2) {
                    main_tabitem_find.setAlpha(0);
                    main_tabitem_findH.setAlpha(1);
                } else {
                    main_tabitem_find.setAlpha(1);
                    main_tabitem_findH.setAlpha(0);
                }
                fragmentTransaction.show(fragmentList.get(i));
                resetTextViewAlpha(tabWidget.getChildAt(i), 1);
            } else {
                fragmentTransaction.hide(fragmentList.get(i));
                resetTextViewAlpha(tabWidget.getChildAt(i), 0);
            }
        }
        fragmentTransaction.commit();
    }

    private void startWebViewActivity(String url) {
        Intent intent = new Intent(MainActivity.this, WebViewH5Activity.class);
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

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMsg event) {
        String msg = event.getMsg();
//        Log.e("TAG_Main", "msg=" + msg);
        if ("loginout".equals(msg)) {
            setCartNum(0);
            clickFragmentBtn(0);
        } else if ("carNum".equals(msg)) {
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
     *
     * @param listener
     */
    public void registerTouchListener(MainActivityTouchListener listener) {
        activityTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来注销自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterTouchListener(MainActivityTouchListener listener) {
        activityTouchListeners.remove(listener);
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

    //收藏
    public void startCollectActivity() {
     startActivity(new Intent( MainActivity.this , CollectActivity.class));
//        startWebViewActivity(Config.HOMECOLLECT);
    }

    //统计查看更多
    public void startStatisticsActivity(String memberId) {
        Intent intent = new Intent( MainActivity.this , StatisticsActivity.class);
        intent.putExtra("memberId",memberId);
        startActivity(intent);
//        startWebViewActivity(Config.STATISTICSLOOKALL);
    }
    //常购清单
    public void startOftenShopActivity(String memberId) {
//        startWebViewActivity(Config.SHOPLIST);
        if (TextUtils.isEmpty(token)||TextUtils.isEmpty(resetToken)) {
            startBaseActivity( MainActivity.this ,LoginActivity.class);
        }  else {
            Intent intent = new Intent( MainActivity.this , OftenShopActivity.class);
            intent.putExtra("memberId",memberId);
            startActivity(intent);
        }
    }
    //订单
    public void startOrderActivity(int tabIndex) {
//        switch (tabIndex) {
//            case 0:
//                startWebViewActivity(Config.MEORDERWEB);
//                break;
//            case 1:
//                startWebViewActivity(Config.MEORDERUNPAYMENTWEB);
//                break;
//            case 2:
//                startWebViewActivity(Config.MEORDERUNSHIPMENTSWEB);
//                break;
//            case 3:
//                startWebViewActivity(Config.MEORDERUNSIGNFORWEB);
//                break;
//        }
        Intent intent = new Intent(MainActivity.this, MyOrderActivity.class);
        intent.putExtra("tabIndex", tabIndex);
        startActivity(intent);
    }
    //积分
    public void startIntegralActivity() {
        startActivity(new Intent( MainActivity.this , IntegralActivity.class));
//         startWebViewActivity(Config.SHOPINTEGRAL);
    }
    //电话咨询
    public void startShopPhoneActivity(){
        Intent intent = new Intent(this, ShopPhoneActivity.class);
        startActivity(intent);
    }
    //专票资质
    public void startInvoiceSpecialActivity() {
//                    startWebViewActivity(Config.SHOPAPTITUDE);
        startActivity(new Intent(MainActivity.this, InvoiceSpecialActivity.class));
    }
    //帮助中心
    public void startHelpActivity(){
//        startWebViewActivity(Config.SHOPHELP);
        startActivity(new Intent(MainActivity.this, InvoiceHelpActivity.class));
    }
    /**
     * 雅森帮
     * digital_member = 0  未开通雅森帮
     */
    public void startYasnActivity(int digital_member) {
//        if (digital_member == 0) {//未开通雅森帮
//            startWebViewActivity(Config.DREDGEYASNHELP);
//        }else {
            startWebViewActivity(Config.YASNBANG);
//        }
    }
    public void startOilActivity() {
//        startWebViewActivity(Config.SHOPINOILQUERY);
        startActivity(new Intent(this,OilActivity.class));
    }
    //扫一扫功能
    public void scanAQRCode(){
        Intent intent = new Intent(this, WeChatCaptureActivity.class);
        startActivity(intent);
    }
//    private void checkForUpdates() {
//        try {
//            UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
//            Log.e("TAG_策略升级","upgradeType="+(upgradeInfo != null));
//            if (upgradeInfo != null) {
//                Log.e("TAG_策略升级","upgradeType="+upgradeInfo.upgradeType);
//                try {
//                    if (upgradeInfo.upgradeType == 2){//弹窗类型（1:建议 2:强制 3:手工）
//                        int versionCode = getVersionCode();
//                        if (upgradeInfo.versionCode > versionCode) {
//                            return;
//                        }else if (upgradeInfo.versionCode==0){
//                            isFirstOpen();
//                            return;
//                        }
//                    }
//                    StringBuilder info = new StringBuilder();
//                    info.append("id: ").append(upgradeInfo.id).append("\n");
//                    info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n");
//                    info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }else {
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }
//
//    private int getVersionCode() throws Exception {
//        // 获取packagemanager的实例
//        PackageManager packageManager = getPackageManager();
//        // getPackageName()是你当前类的包名，0代表是获取版本信息
//        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
//        int code = packInfo.versionCode;
//        return code;
//    }
}
