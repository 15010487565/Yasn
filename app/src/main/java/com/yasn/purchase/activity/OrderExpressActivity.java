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
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.fragment.OrderExpressFragment;
import com.yasn.purchase.model.order.OrderQueryExpressModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class OrderExpressActivity extends SimpleTopbarActivity implements
        ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener{

    private ViewPager pager;
    private TextView tvOrderExpressNull;
    private TabLayout tableLayout;
    private OrderExpressPagerAdapter orderExpressPagerAdapter;
    TextView tvTopExpressNum;
    @Override
    protected Object getTopbarTitle() {
        return "物流信息";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_express);
        initView();
    }

    private void initView() {
        //无数据提示语
        tvOrderExpressNull = findViewById(R.id.tv_OrderExpressNull);
        tvOrderExpressNull.setVisibility(View.GONE);
        //有物流信息
        pager = (ViewPager) findViewById(R.id.vp_OrderExpress);
        pager.setVisibility(View.VISIBLE);
        tableLayout = (TabLayout) findViewById(R.id.tab_OrderExpress);
        pager.setOnPageChangeListener(this);
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        tableLayout.setupWithViewPager(pager);
        /**
         * TabLayout.MODE_SCROLLABLE    支持滑动
         * TabLayout.MODE_FIXED     不支持，默认不支持水平滑动
         */
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        tableLayout.setTabTextColors(Color.BLACK, Color.RED);
        orderExpressPagerAdapter = new OrderExpressPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(orderExpressPagerAdapter);
        int tabIndex = getIntent().getIntExtra("tabIndex", 0);
        pager.setCurrentItem(tabIndex);
        //
        tvTopExpressNum = (TextView) findViewById(R.id.tv_topExpressNum);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        int orderId = getIntent().getIntExtra("orderId", 0);
//        orderId = 48907;
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(100, Config.ORDERLOOKDISTR + orderId, params);
    }
    List<OrderQueryExpressModel.DeliverysBean> deliverys;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100://查看物流
                if (returnCode == 200) {
                    OrderQueryExpressModel orderQueryExpressModel = JSON.parseObject(returnData, OrderQueryExpressModel.class);
                    deliverys = orderQueryExpressModel.getDeliverys();

                    if (deliverys !=null && deliverys.size()>0){
                        if(deliverys.size() == 1){
                            orderExpressPagerAdapter.setData(deliverys);
                            tableLayout.setVisibility(View.GONE);
                        }else {
                            orderExpressPagerAdapter.setData(deliverys);
                            tableLayout.setVisibility(View.VISIBLE);
                        }
                        tvOrderExpressNull.setVisibility(View.VISIBLE);
                        pager.setVisibility(View.GONE);
                        for (int i = 0; i < deliverys.size(); i++) {
                            TabLayout.Tab tabAt = tableLayout.getTabAt(i);
                            tabAt.setCustomView(tabIcon(i));
                        }
                        tableLayout.addOnTabSelectedListener(this);
                        String minNumberString = String.format("您的订单被拆分成%s个包裹配送，请注意查收！", deliverys.size());
                        SpannableStringBuilder span = new SpannableStringBuilder(minNumberString);
                        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.black_66)), 0, 8,
                                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.black_66)), minNumberString.length() - 12, minNumberString.length(),
                                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        tvTopExpressNum.setText(span);
                    }else {
                        tableLayout.setVisibility(View.GONE);
                        tvOrderExpressNull.setVisibility(View.VISIBLE);
                        pager.setVisibility(View.GONE);
                    }
                }else {
                    tableLayout.setVisibility(View.GONE);
                    tvOrderExpressNull.setVisibility(View.VISIBLE);
                    pager.setVisibility(View.GONE);
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
    private class OrderExpressPagerAdapter extends FragmentPagerAdapter {

        private List<OrderQueryExpressModel.DeliverysBean> list;

        public OrderExpressPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void setData(List<OrderQueryExpressModel.DeliverysBean> list){
            this.list = list;
            notifyDataSetChanged();
        }
        @Override
        public Fragment getItem(int paramInt) {
            OrderQueryExpressModel.DeliverysBean deliverysBean = list.get(paramInt);
            OrderExpressFragment fragment = new OrderExpressFragment(deliverysBean);
            return fragment;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "包裹"+(position+1);
        }
    }
    private View tabIcon(int position){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.icon_tabview_order,null);
        TextView tv = (TextView) newtab.findViewById(R.id.tv_OrderTab);
        tv.setText("包裹"+(position+1));
        ImageView im = (ImageView)newtab.findViewById(R.id.iv_OrderTab);
        if (position==0){
            im.setVisibility(View.VISIBLE);
            im.setImageResource(R.mipmap.order_express_tab);
        }else {
            im.setVisibility(View.INVISIBLE);
        }
        return newtab;
    }
    private void tabSelectedIcon(TabLayout.Tab tab,boolean isShow){
        View view = tab.getCustomView();
        Log.e("TAG_TAG","view="+(view==null));
        ImageView im = (ImageView)view.findViewById(R.id.iv_OrderTab);
        if (isShow){
            im.setVisibility(View.VISIBLE);
            im.setImageResource(R.mipmap.order_express_tab);
        }else {
            im.setVisibility(View.INVISIBLE);
        }
    }
}
