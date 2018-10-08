package com.yasn.purchase.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.fragment.MakerShroffAccFirmFragment;
import com.yasn.purchase.fragment.MakerShroffAccIndividualFragment;
import com.yasn.purchase.model.MakerShroffAccModel;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MakerShroffAccountActivity extends BaseYasnActivity
        implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener{

    private IntegralPagerAdapter adapter;
    private ViewPager pager;
    private TabLayout tableLayout;
    private RelativeLayout reMakerFillFinish;
    private LinearLayout llMakerFirmFillFinish, llMakerIvidualFillFinish;
    private TextView nameIvidualFinish, bankIvidualFinish, bankNumIvidualFinish;//个人
    private TextView tvMakerFirmTitle, tvMakerFirmNum, tvMakerFirmAddress
            , tvMakerFirmMobile, tvMakerFirmBank, tvMakerFirmBankNum;//企业

    @Override
    protected Object getTopbarTitle() {
        return "收款账号";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_shroff_account);
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(100, Config.GETMAKERRECEIPTACCOUNT , params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        reMakerFillFinish = (RelativeLayout) findViewById(R.id.re_MakerFillFinish);
        reMakerFillFinish.setVisibility(View.GONE);
        //企业
        llMakerFirmFillFinish = (LinearLayout) findViewById(R.id.ll_MakerFirmFillFinish);
        //单位名称
        tvMakerFirmTitle = (TextView) findViewById(R.id.tv_MakerFirmFillFinishTitle);
        //纳税识别号
        tvMakerFirmNum = (TextView) findViewById(R.id.tv_MakerFirmFillFinishNum);
        //注册地址
        tvMakerFirmAddress = (TextView) findViewById(R.id.tv_MakerFirmFillFinishAddress);
        //注册电话
        tvMakerFirmMobile = (TextView) findViewById(R.id.tv_MakerFirmFillFinishMobile);
        //开户银行
        tvMakerFirmBank = (TextView) findViewById(R.id.tv_MakerFirmFillFinishBank);
        //银行账号
        tvMakerFirmBankNum = (TextView) findViewById(R.id.tv_MakerFirmFillFinishBankNum);
        //个人
        llMakerIvidualFillFinish = (LinearLayout) findViewById(R.id.ll_MakerIvidualFillFinish);
        //开户名称
        nameIvidualFinish = (TextView) findViewById(R.id.tv_MakerIvidualFillFinishName);
        bankIvidualFinish = (TextView) findViewById(R.id.tv_MakerIvidualFillFinishBank);
        bankNumIvidualFinish = (TextView) findViewById(R.id.tv_MakerIvidualFillFinishBankNum);
        initTabAndViewPager();
    }

    private void initTabAndViewPager() {
        pager = (ViewPager) findViewById(R.id.vp_MakerShroffAccount);
        pager.setVisibility(View.VISIBLE);
        tableLayout = (TabLayout) findViewById(R.id.tab_MakerShroffAccount);
        tableLayout.setVisibility(View.VISIBLE);
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

        tableLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {
            case 100:
                if (returnCode == 0){
                    MakerShroffAccModel makerShroffAccModel = JSON.parseObject(returnData, MakerShroffAccModel.class);
                    MakerShroffAccModel.DataBean data = makerShroffAccModel.getData();
                    if (data == null || "".equals(data)){
                        tableLayout.setVisibility(View.VISIBLE);
                        pager.setVisibility(View.VISIBLE);
                        reMakerFillFinish.setVisibility(View.GONE);
                    }else {
                        int type = data.getType();
                        if (type == 1){//1 企业；2 个人
                            tableLayout.setVisibility(View.GONE);
                            pager.setVisibility(View.GONE);
                            reMakerFillFinish.setVisibility(View.VISIBLE);
                            llMakerFirmFillFinish.setVisibility(View.VISIBLE);
                            llMakerIvidualFillFinish.setVisibility(View.GONE);
                            String name = data.getName();
                            tvMakerFirmTitle.setText(name == null ? "" : name);
                            String taxpayerNum = data.getTaxpayerNum();
                            tvMakerFirmNum.setText(taxpayerNum == null ? "" : taxpayerNum);
                            String address = data.getAddress();
                            tvMakerFirmAddress.setText(address == null ? "" : address);
                            String mobile = data.getMobile();
                            tvMakerFirmMobile.setText(mobile == null ? "" : mobile);
                            String bankDeposit = data.getBankDeposit();
                            tvMakerFirmBank.setText(bankDeposit == null ? "" : bankDeposit);
                            String accountNum = data.getAccountNum();
                            tvMakerFirmBankNum.setText(accountNum == null ? "" : accountNum);
                        }else if (type == 2){
                            tableLayout.setVisibility(View.GONE);
                            pager.setVisibility(View.GONE);
                            reMakerFillFinish.setVisibility(View.VISIBLE);
                            llMakerFirmFillFinish.setVisibility(View.GONE);
                            llMakerIvidualFillFinish.setVisibility(View.VISIBLE);
                            String name = data.getName();
                            nameIvidualFinish.setText(name == null ? "" : name);
                            String bankDeposit = data.getBankDeposit();
                            bankIvidualFinish.setText(bankDeposit == null ? "" : bankDeposit);
                            String accountNum = data.getAccountNum();
                            bankNumIvidualFinish.setText(accountNum == null ? "" : accountNum);
                        }else {
                            tableLayout.setVisibility(View.VISIBLE);
                            pager.setVisibility(View.VISIBLE);
                            reMakerFillFinish.setVisibility(View.GONE);
                        }
                    }
                }else {
                    tableLayout.setVisibility(View.VISIBLE);
                    pager.setVisibility(View.VISIBLE);
                    reMakerFillFinish.setVisibility(View.GONE);
                    if (returnCode != 200)
                    ToastUtil.showToast(returnMsg);
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
        tableLayout.setVisibility(View.VISIBLE);
        pager.setVisibility(View.VISIBLE);
        reMakerFillFinish.setVisibility(View.GONE);
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

    private class IntegralPagerAdapter extends FragmentPagerAdapter {

        public IntegralPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int paramInt) {
            Fragment fragment = null;
            if (paramInt == 0){
                fragment = new MakerShroffAccFirmFragment();
            }else {
                fragment = new MakerShroffAccIndividualFragment();
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
                return "企业账号";
            }else {
                return "个人账号";

            }
        }
    }

}
