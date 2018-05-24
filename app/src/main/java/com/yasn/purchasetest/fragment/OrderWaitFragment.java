package com.yasn.purchasetest.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.yasn.purchasetest.R;
import com.yasn.purchasetest.adapter.OrderSonAdapter;
import com.yasn.purchasetest.common.Config;
import com.yasn.purchasetest.listener.OnRcOrderItemClickListener;
import com.yasn.purchasetest.model.order.OrderGoodsContentModel;
import com.yasn.purchasetest.model.order.OrderHeaderModel;
import com.yasn.purchasetest.model.order.OrderShopNameModel;
import com.yasn.purchasetest.model.order.OrderSonModel;
import com.yasn.purchasetest.model.order.OrderSonPayInfoModel;
import com.yasn.purchasetest.view.MultiSwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.utils.ToastUtil;

/**
 * 待收货
 * Created by gs on 2018/1/8.
 */

public class OrderWaitFragment extends OrderFragment implements
        OnRcOrderItemClickListener
        , SwipeRefreshLayout.OnRefreshListener
        , MultiSwipeRefreshLayout.OnLoadListener {

    private MultiSwipeRefreshLayout slOrderWait;
    private RelativeLayout title;
    private RecyclerView rcOrderWait;
    private OrderSonAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    int pageNo = 1;//初始化页数
    private List<Object> orderWaitList = new ArrayList<>();
    private boolean isDownPull = false;//下拉刷新
    private boolean isUpPull = false;//上拉加载

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_orderwait;
    }

    @Override
    protected void OkHttpDemand() {
        token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
        resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(getActivity()).getSpString("resetTokenTime");
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        params.put("pageNo", String.valueOf(pageNo));
        params.put("status", "3");
        okHttpGet(100, Config.MEORDER, params);
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        super.initView(inflater, view);
        title = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        title.setVisibility(View.GONE);
        initMultiSwipeRefresh(view);
        initRcView(view);
        pageNo = 1;//初始化页数
        OkHttpDemand();
    }

    private void initRcView(View view) {
        rcOrderWait = (RecyclerView) view.findViewById(R.id.rc_OrderWait);
        linearLayoutManager = new LinearLayoutManager(getFragmentActivity());
        rcOrderWait.setLayoutManager(linearLayoutManager);
        adapter = new OrderSonAdapter(getActivity(), linearLayoutManager);
        adapter.setOnItemClickListener(this);
        rcOrderWait.setAdapter(adapter);

        rcOrderWait.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isTop = recyclerView.canScrollVertically(-1);//返回false表示不能往下滑动，即代表到顶部了；
                if (isTop) {
                    slOrderWait.setEnabled(false);
                } else {
                    slOrderWait.setEnabled(true);
                }
                boolean isBottom = recyclerView.canScrollVertically(1);//返回false表示不能往上滑动，即代表到底部了；
                //屏幕中最后一个可见子项的position
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                //当前屏幕所看到的子项个数
                int visibleItemCount = linearLayoutManager.getChildCount();
                //当前RecyclerView的所有子项个数
                int totalItemCount = linearLayoutManager.getItemCount();
                if (isBottom) {
                    slOrderWait.setBottom(false);
                } else {
                    if (visibleItemCount == totalItemCount) {
                        slOrderWait.setBottom(false);
                    } else {
                        slOrderWait.setBottom(true);
                    }
                }
            }
        });
    }

    private void initMultiSwipeRefresh(View view) {
        //搜索列表
        slOrderWait = (MultiSwipeRefreshLayout) view.findViewById(R.id.swipe_OrderWait);
        //下拉刷新监听
        slOrderWait.setOnRefreshListener(this);
        //上拉加載监听
        slOrderWait.setOnLoadListener(this);
        //设置样式刷新显示的位置
        slOrderWait.setProgressViewOffset(true, -20, 100);
        slOrderWait.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    initResule(returnData);
                    if (isDownPull) {
                        slOrderWait.setRefreshing(false);
                        isDownPull = false;
                    }
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }

    }

    List<OrderSonModel.OrdersBean> ordersList;

    private void initResule(String returnData) {
        try {
            if (pageNo == 1 && orderWaitList != null && orderWaitList.size() > 0) {
                orderWaitList.clear();
            }
            OrderSonModel orderSonModel = JSON.parseObject(returnData, OrderSonModel.class);
            ordersList = orderSonModel.getOrders();
            if (ordersList != null && ordersList.size() > 0) {
                for (int i = 0, j = ordersList.size(); i < j; i++) {
                    OrderSonModel.OrdersBean ordersBean = ordersList.get(i);
                    //头部信息
                    OrderHeaderModel orderHeaderModel = new OrderHeaderModel();
                    //订单编号
                    String sn = ordersBean.getSn();
                    orderHeaderModel.setOrderCode(sn);
                    orderWaitList.add(orderHeaderModel);
                    //店铺名称
                    OrderShopNameModel orderShopNameModel = new OrderShopNameModel();
                    String storeName = ordersBean.getStoreName();
                    orderShopNameModel.setShopName(storeName);
                    orderWaitList.add(orderShopNameModel);
                    List<OrderSonModel.OrdersBean.OrderItemBean> orderItem = ordersBean.getOrderItem();

                    if (orderItem != null && orderItem.size() > 0) {
                        for (int m = 0, n = orderItem.size(); m < n; m++) {
                            OrderSonModel.OrdersBean.OrderItemBean orderItemBean = orderItem.get(m);
                            OrderGoodsContentModel orderGoodsContentModel = new OrderGoodsContentModel();
                            //商品图片
                            String image = orderItemBean.getImage();
                            orderGoodsContentModel.setImage(image);
                            //商品名称
                            String name = orderItemBean.getName();
                            orderGoodsContentModel.setName(name);
                            //商品数量
                            int num = orderItemBean.getNum();
                            orderGoodsContentModel.setNum(num);
                            //商品价格
                            double price = orderItemBean.getPrice();
                            orderGoodsContentModel.setPrice(String.format("%.2f", price));
                            //商品信息
                            orderWaitList.add(orderGoodsContentModel);
                        }
                    }
                    //支付信息
                    OrderSonPayInfoModel orderSonPayInfoModel = new OrderSonPayInfoModel();
                    //订单id
                    int orderId = ordersBean.getOrderId();
                    orderSonPayInfoModel.setOrderId(orderId);
                    //订单号
                    orderSonPayInfoModel.setSn(sn);
                    orderWaitList.add(orderSonPayInfoModel);
                }
                if (pageNo > 1) {
                    isUpPull = false;
                    slOrderWait.setLoading(false);
                    adapter.addData(orderWaitList);
                } else {
                    adapter.setData(orderWaitList);
                }
                if (orderWaitList == null || orderWaitList.size() == 0) {
                    llOrderError.setVisibility(View.VISIBLE);
                    rcOrderWait.setVisibility(View.GONE);
                } else {
                    llOrderError.setVisibility(View.GONE);
                    rcOrderWait.setVisibility(View.VISIBLE);
                }
            } else {
                if (pageNo > 1) {
                    adapter.upFootText();
                    rcOrderWait.setVisibility(View.VISIBLE);
                    llOrderError.setVisibility(View.GONE);
                } else {
                    rcOrderWait.setVisibility(View.GONE);
                    llOrderError.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelResult() {
        cancelUpdate();
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        cancelUpdate();
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        cancelUpdate();
    }

    @Override
    public void onFinishResult() {
        cancelUpdate();
    }

    private void cancelUpdate() {
        if (isDownPull) {
            slOrderWait.setRefreshing(false);
            isDownPull = false;

        }
        if (isUpPull) {
            isUpPull = false;
            slOrderWait.setLoading(false);
        }
    }

    //查看订单
    @Override
    public void OnLookOrderClick(int position) {
        Object o = orderWaitList.get(position);
        if (o instanceof OrderSonPayInfoModel){
            OrderSonPayInfoModel  infoModel = (OrderSonPayInfoModel) o;
            String sn = infoModel.getSn();
            Log.e("TAG_查看待收获主订单","SN="+sn);
            int orderId = infoModel.getOrderId();
            Log.e("TAG_查看待收获主订单","orderId="+orderId);
        }
    }

    //立即支付
    @Override
    public void OnPayMoneyClick(int position) {

    }

    //查看主订单
    @Override
    public void OnLookMainOrderClick(int position) {
        Object o = orderWaitList.get(position);
        if (o instanceof OrderSonPayInfoModel){
            OrderSonPayInfoModel  infoModel = (OrderSonPayInfoModel) o;
            String sn = infoModel.getSn();
            Log.e("TAG_查看待收获主订单","SN="+sn);
            int orderId = infoModel.getOrderId();
            Log.e("TAG_查看待收获主订单","orderId="+orderId);
        }
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        pageNo = 1;
        slOrderWait.setRefreshing(true);
        isDownPull = true;
        OkHttpDemand();
    }

    //上拉加载
    @Override
    public void onLoad() {
        if (rcOrderWait != null && rcOrderWait.getAdapter() != null) {
            isUpPull = true;
            slOrderWait.setLoading(true);
            pageNo++;
            Log.e("TAG_待收获上拉加载","pageNo="+pageNo);
            OkHttpDemand();
        }
    }
}