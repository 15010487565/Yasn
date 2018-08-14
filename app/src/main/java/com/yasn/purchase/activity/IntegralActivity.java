package com.yasn.purchase.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.fragment.IntegralDetailFragment;
import com.yasn.purchase.fragment.IntegralFreezeFragment;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

/**
 * 积分
 */
public class IntegralActivity extends SimpleTopbarActivity implements
        ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener{


    private IntegralPagerAdapter adapter;
    private ViewPager pager;
    private TabLayout tableLayout;
    private TextView tvTopIntegral;

    @Override
    protected Object getTopbarTitle() {
        return R.string.mejifen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        tvTopIntegral = (TextView) findViewById(R.id.tv_TopIntegral);
        pager = (ViewPager) findViewById(R.id.vp_Integral);
        tableLayout = (TabLayout) findViewById(R.id.tab_Integral);

        pager.setOnPageChangeListener(this);
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        tableLayout.setupWithViewPager(pager);
        /**
         * TabLayout.MODE_SCROLLABLE    支持滑动
         * TabLayout.MODE_FIXED     不支持，默认不支持水平滑动
         */
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        tableLayout.setTabTextColors(Color.BLACK, Color.RED);

        adapter = new IntegralPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        int tabIndex = getIntent().getIntExtra("tabIndex", 0);
        pager.setCurrentItem(tabIndex);

        for (int i = 0; i < 2; i++) {
            TabLayout.Tab tabAt = tableLayout.getTabAt(i);
            tabAt.setCustomView(tabIcon(i));
        }
        tableLayout.addOnTabSelectedListener(this);
    }

    private View tabIcon(int position){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.icon_tabview_integral,null);
        TextView tv = (TextView) newtab.findViewById(R.id.tv_IntegralTab);
        if (position == 0){
            tv.setText("积分明细");
            tv.setTextColor(ContextCompat.getColor(this,R.color.white));

        }else {
            tv.setText("冻结积分");
            tv.setTextColor(ContextCompat.getColor(this,R.color.orange_integ));

        }
        ImageView im = (ImageView)newtab.findViewById(R.id.iv_IntegralTab);
        if (position==0){
            im.setImageResource(R.mipmap.jfmx_left);
        }else {
            im.setImageResource(R.mipmap.djjf_right);
        }
        return newtab;
    }

    private void tabSelectedIcon(TabLayout.Tab tab,boolean isShow){
        View view = tab.getCustomView();
//        Log.e("TAG_TAG","view="+(view==null));
        TextView tv = (TextView) view.findViewById(R.id.tv_IntegralTab);
        if (isShow){
            tv.setTextColor(ContextCompat.getColor(this,R.color.white));
        }else {
            tv.setTextColor(ContextCompat.getColor(this,R.color.orange_integ));
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
        tabSelectedIcon(tab,true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tabSelectedIcon(tab,false);
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
    private class IntegralPagerAdapter extends FragmentPagerAdapter {

        public IntegralPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int paramInt) {
            Fragment fragment = null;
            if (paramInt == 0){
                fragment = new IntegralDetailFragment();
            }else {
                fragment = new IntegralFreezeFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0){
                return "积分明细";
            }else {
                return "冻结积分";

            }
        }
    }

    public void getPoint(int point){
        SpannableStringBuilder span = new SpannableStringBuilder("当前可用：" + point );
        span.setSpan(new RelativeSizeSpan(0.6f), 0, 5,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvTopIntegral.setText(span);
    }
}
