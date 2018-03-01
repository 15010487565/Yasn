package com.yasn.purchase.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yasn.purchase.R;
import com.yasn.purchase.fragment.AllOrderFragment;
import com.yasn.purchase.fragment.ObligationFragment;
import com.yasn.purchase.fragment.OverhangFragment;
import com.yasn.purchase.fragment.WaitreceivingFragment;
import com.yasn.purchase.func.CallService;
import com.yonyou.sns.im.log.YYIMLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;

public class AllorderActivity extends SimpleTopbarActivity implements
        ViewPager.OnPageChangeListener ,TabLayout.OnTabSelectedListener{

    private final int[] TITLE = { R.string.all, R.string.obligation,
            R.string.overhang,R.string.waitreceiving};
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    public static Class<?> fragmentArray[] = {
            AllOrderFragment.class,
            ObligationFragment.class,
            OverhangFragment.class,
            WaitreceivingFragment.class
    };
    private ViewPager pager;
    private TabLayout tableLayout;

    private static Class<?> rightFuncArray[] = {CallService.class};

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected Object getTopbarTitle() {
        return "订单";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorder);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initFragments();
        pager = (ViewPager)findViewById(R.id.pager);
        tableLayout = (TabLayout) findViewById(R.id.table);
//        FindPagerAdapter adapter = new FindPagerAdapter(getActivity().getSupportFragmentManager());
//        pager.setOffscreenPageLimit(0);//设置ViewPager的缓存界面数,默认缓存为2
//        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(this);
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        tableLayout.setupWithViewPager(pager);
        /**
         * TabLayout.MODE_SCROLLABLE    支持滑动
         * TabLayout.MODE_FIXED     不支持，默认不支持水平滑动
         */
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        tableLayout.setTabTextColors(Color.BLACK,Color.RED);
        pager.setAdapter(new AllorderPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0){
                    return new AllOrderFragment();
                }else if (position == 1){
                    return new ObligationFragment();
                }else if (position == 2){
                    return new OverhangFragment();
                } else{
                    return new WaitreceivingFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getResources().getString(TITLE[position]);
            }

            @Override
            public int getCount() {
                return TITLE.length;
            }
        });
    }
    protected void initFragments() {
        // 初始化fragments
        for (int i = 0; i < fragmentArray.length; i++) {
            BaseFragment fragment = null;
            try {
                fragment = (BaseFragment) fragmentArray[i].newInstance();
                fragment.setActivity(this);
            } catch (Exception e) {
                YYIMLogger.d(e);
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

    }
}
