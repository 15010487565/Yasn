package com.yasn.purchasetest.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yasn.purchasetest.R;
import com.yasn.purchasetest.fragment.OrderAllFragment;
import com.yasn.purchasetest.fragment.OrderObligFragment;
import com.yasn.purchasetest.fragment.OrderOverFragment;
import com.yasn.purchasetest.fragment.OrderWaitFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;

public class MyOrderActivity extends SimpleTopbarActivity implements
        ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    private final int[] TITLE = {R.string.all, R.string.obligation,
            R.string.overhang, R.string.waitreceiving};
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    public static Class<?> fragmentArray[] = {
            OrderAllFragment.class,
            OrderObligFragment.class,
            OrderOverFragment.class,
            OrderWaitFragment.class
    };
    private ViewPager pager;
    private TabLayout tableLayout;

    @Override
    protected Object getTopbarTitle() {
        return R.string.myorder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initFragments();
        pager = (ViewPager) findViewById(R.id.vp_MyOrder);
        tableLayout = (TabLayout) findViewById(R.id.tab_MyOrder);
        pager.setOnPageChangeListener(this);
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        tableLayout.setupWithViewPager(pager);
        /**
         * TabLayout.MODE_SCROLLABLE    支持滑动
         * TabLayout.MODE_FIXED     不支持，默认不支持水平滑动
         */
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        tableLayout.setTabTextColors(Color.BLACK, Color.RED);
        AllorderPagerAdapter allorderPagerAdapter = new AllorderPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(allorderPagerAdapter);
        int tabIndex = getIntent().getIntExtra("tabIndex", 0);
        pager.setCurrentItem(tabIndex);
    }

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

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

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
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class AllorderPagerAdapter extends FragmentPagerAdapter {

        public AllorderPagerAdapter(FragmentManager fm) {
            super(fm);
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
        public CharSequence getPageTitle(int position) {
            return getResources().getString(TITLE[position]);
        }
    }
}
