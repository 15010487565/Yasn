package com.yasn.purchase.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.MakerShopOrderAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.MakerShopOrderModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class MakerShopOrderActivity extends SimpleTopbarActivity implements MultiSwipeRefreshLayout.OnLoadListener
      ,SwipeRefreshLayout.OnRefreshListener{

    protected LinearLayout llError;
    protected ImageView ivError;
    protected TextView tvErrorHint;
    private MakerShopOrderAdapter adapter;
    private RecyclerView rcMakerShop;
    private LinearLayoutManager mLinearLayoutManager;
    private int pagNo = 1;
    private MultiSwipeRefreshLayout swipe_layout;
    @Override
    protected Object getTopbarTitle() {
        return "门店订单";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_shop_order);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        llError = (LinearLayout)findViewById(R.id.ll_Error);
        llError.setVisibility(View.GONE);
        ivError = (ImageView) findViewById(R.id.iv_Error);
        tvErrorHint = (TextView) findViewById(R.id.tv_ErrorHint);
        tvErrorHint.setText("暂无门店下单！");
        initSwipeRefreshLayout();
        initRecyclerView();
        initData();
    }

    private void initData() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
//        params.put("pageSize", "10");
        okHttpGet(100, Config.MAKERSHOPORDERAPP+pagNo+"/10", params);
    }

    private void initSwipeRefreshLayout() {
        //搜索列表
        swipe_layout = (MultiSwipeRefreshLayout) findViewById(R.id.swipe_layout);
        //下拉刷新监听
        swipe_layout.setOnRefreshListener(this);
        //禁止下拉刷新
//        swipe_layout.setEnabled(false);
//        swipe_layout.setMultiSwipeRefreshClickListener(this);
        //上拉加載监听
        swipe_layout.setOnLoadListener(this);
        //设置样式刷新显示的位置
        swipe_layout.setProgressViewOffset(true, -20, 100);
        swipe_layout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
    }

    private void initRecyclerView() {

        rcMakerShop = (RecyclerView) findViewById(R.id.rc_MakerShopOrder);
        rcMakerShop.setVisibility(View.VISIBLE);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        rcMakerShop.setLayoutManager(mLinearLayoutManager);
        adapter = new MakerShopOrderAdapter(this,mLinearLayoutManager);
        rcMakerShop.setAdapter(adapter);
        rcMakerShop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isTop = recyclerView.canScrollVertically(-1);//返回false表示不能往下滑动，即代表到顶部了；
                if (isTop){
                    swipe_layout.setEnabled(false);
                }else {
                    swipe_layout.setEnabled(true);
                }
                boolean isBottom = recyclerView.canScrollVertically(1);//返回false表示不能往上滑动，即代表到底部了；
                //屏幕中最后一个可见子项的position
//                int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                //当前屏幕所看到的子项个数
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                //当前RecyclerView的所有子项个数
                int totalItemCount = mLinearLayoutManager.getItemCount();
//                Log.e("TAG_底部","isBottom="+isBottom+"visibleItemCount="+visibleItemCount+";totalItemCount="+totalItemCount);
                if (isBottom ){
                    swipe_layout.setBottom(false);
                }else {
                    if (visibleItemCount == totalItemCount){
                        swipe_layout.setBottom(false);
                        adapter.upFootText();
                    }else {
                        swipe_layout.setBottom(true);
                    }
                }
            }
        });
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200){
                    MakerShopOrderModel makerShopOrderModel = JSON.parseObject(returnData,MakerShopOrderModel.class);
                    List<MakerShopOrderModel.DataBean> data = makerShopOrderModel.getData();
                    if (pagNo >1) {
                        if (data == null || data.size() == 0){
                            adapter.upFootText();
                            ToastUtil.showToast("门店订单已全部显示！");
                            rcMakerShop.setVisibility(View.VISIBLE);
                            llError.setVisibility(View.GONE);
                        }else {
                            adapter.addData(data);
                            rcMakerShop.setVisibility(View.VISIBLE);
                            llError.setVisibility(View.GONE);
                        }
                    } else {
                        if (data == null || data.size() == 0){
//                            adapter.upFootText();
                            rcMakerShop.setVisibility(View.GONE);
                            llError.setVisibility(View.VISIBLE);
                            ToastUtil.showToast("未搜索到门店订单！");
                        }else {
                            adapter.setData(data);
                            rcMakerShop.setVisibility(View.VISIBLE);
                            llError.setVisibility(View.GONE);
                        }
                    }
                    swipe_layout.setLoading(false);

                }else {
                    rcMakerShop.setVisibility(View.GONE);
                    llError.setVisibility(View.VISIBLE);
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
    public void onRefresh() {
        pagNo = 1;
        swipe_layout.setRefreshing(true);
        initData();
    }

    @Override
    public void onLoad() {
        Log.e("TAG_Collect","收藏");
        if (rcMakerShop != null && rcMakerShop.getAdapter() != null) {
            swipe_layout.setLoading(true);
            pagNo++;
            initData();
        }
    }
}
