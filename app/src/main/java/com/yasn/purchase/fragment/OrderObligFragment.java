package com.yasn.purchase.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.PayActivity;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.adapter.OrderMainAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnRcOrderItemClickListener;
import com.yasn.purchase.model.order.OrderGoodsContentModel;
import com.yasn.purchase.model.order.OrderHeaderModel;
import com.yasn.purchase.model.order.OrderMainModel;
import com.yasn.purchase.model.order.OrderMainPayInfoModel;
import com.yasn.purchase.model.order.OrderShopNameModel;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.utils.ToastUtil;

/**
 * 待付款
 * Created by gs on 2018/1/8.
 */

public class OrderObligFragment extends OrderFragment implements
        OnRcOrderItemClickListener
        , SwipeRefreshLayout.OnRefreshListener
        , MultiSwipeRefreshLayout.OnLoadListener {

    private MultiSwipeRefreshLayout slOrderOblig;
    private RelativeLayout title;
    private RecyclerView rcOrderOblig;
    private OrderMainAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    int pageNo = 1;//初始化页数
    private List<Object> orderObligList = new ArrayList<>();
    private boolean isDownPull = false;//下拉刷新
    private boolean isUpPull = false;//上拉加载

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_orderobli;
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
        params.put("status", "1");
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
        rcOrderOblig = (RecyclerView) view.findViewById(R.id.rc_OrderOblig);
        linearLayoutManager = new LinearLayoutManager(getFragmentActivity());
        rcOrderOblig.setLayoutManager(linearLayoutManager);
        adapter = new OrderMainAdapter(getActivity(), linearLayoutManager);
        adapter.setOnItemClickListener(this);
        rcOrderOblig.setAdapter(adapter);
        //rc线
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                getFragmentActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.line_gray));
        rcOrderOblig.addItemDecoration(recyclerViewDecoration);

        rcOrderOblig.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isTop = recyclerView.canScrollVertically(-1);//返回false表示不能往下滑动，即代表到顶部了；
                if (isTop) {
                    slOrderOblig.setEnabled(false);
                } else {
                    slOrderOblig.setEnabled(true);
                }
                boolean isBottom = recyclerView.canScrollVertically(1);//返回false表示不能往上滑动，即代表到底部了；
                //屏幕中最后一个可见子项的position
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                //当前屏幕所看到的子项个数
                int visibleItemCount = linearLayoutManager.getChildCount();
                //当前RecyclerView的所有子项个数
                int totalItemCount = linearLayoutManager.getItemCount();
                if (isBottom) {
                    slOrderOblig.setBottom(false);
                } else {
                    if (visibleItemCount == totalItemCount) {
                        slOrderOblig.setBottom(false);
                    } else {
                        slOrderOblig.setBottom(true);
                    }
                }
            }
        });
    }

    private void initMultiSwipeRefresh(View view) {
        //搜索列表
        slOrderOblig = (MultiSwipeRefreshLayout) view.findViewById(R.id.swipe_OrderOblig);
        //下拉刷新监听
        slOrderOblig.setOnRefreshListener(this);
        //上拉加載监听
        slOrderOblig.setOnLoadListener(this);
        //设置样式刷新显示的位置
        slOrderOblig.setProgressViewOffset(true, -20, 100);
        slOrderOblig.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
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
                        slOrderOblig.setRefreshing(false);
                        isDownPull = false;
                    }
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }

    }

    List<OrderMainModel.OrdersBean> ordersList;

    private void initResule(String returnData) {
        try {
            if (pageNo == 1 && orderObligList != null && orderObligList.size() > 0) {
                orderObligList.clear();
            }
            OrderMainModel orderMainModel = JSON.parseObject(returnData, OrderMainModel.class);
            OrderMainModel.MemberBean member = orderMainModel.getMember();
            String employee_auth = member.getEmployee_auth();
            ordersList = orderMainModel.getOrders();
            if (ordersList != null && ordersList.size() > 0) {
                for (int i = 0, j = ordersList.size(); i < j; i++) {
//                    boolean isRemoveList = false;
                    OrderMainModel.OrdersBean ordersBean = ordersList.get(i);
                    //头部信息
                    OrderHeaderModel orderHeaderModel = new OrderHeaderModel();
                    //订单编号
                    String sn = ordersBean.getSn();
                    orderHeaderModel.setOrderCode(sn);
                    //订单id
                    int orderId = ordersBean.getOrderId();
                    orderObligList.add(orderHeaderModel);
                    List<OrderMainModel.OrdersBean.ChildOrderListBean> childOrderList = ordersBean.getChildOrderList();
                    if (childOrderList != null && childOrderList.size() > 0) {
                        for (int k = 0, l = childOrderList.size(); k < l; k++) {
                            OrderMainModel.OrdersBean.ChildOrderListBean childOrderListBean = childOrderList.get(k);
                            //店铺名称
                            OrderShopNameModel orderShopNameModel = new OrderShopNameModel();
                            //主订单取StoreName;子订单ShopName
                            String storeName = childOrderListBean.getStoreName();
                            orderShopNameModel.setShopName(storeName);
                            orderObligList.add(orderShopNameModel);
                            List<OrderMainModel.OrdersBean.ChildOrderListBean.OrderItemBean> orderItem = childOrderListBean.getOrderItem();
                            if (orderItem != null && orderItem.size() > 0) {
                                for (int m = 0, n = orderItem.size(); m < n; m++) {
                                    OrderMainModel.OrdersBean.ChildOrderListBean.OrderItemBean orderItemBean = orderItem.get(m);
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
                                    orderGoodsContentModel.setOrderId(orderId);
                                    //商品信息
                                    orderObligList.add(orderGoodsContentModel);
                                }
                            }
                        }
                        //支付信息
                        OrderMainPayInfoModel orderMainPayInfoModel = new OrderMainPayInfoModel();
                        //支付金额
                        double needPayMoney = ordersBean.getNeedPayMoney();
                        orderMainPayInfoModel.setNeedPayMoney(String.format("%.2f", needPayMoney));
                        //订单状态
                        int status = ordersBean.getStatus();
                        orderMainPayInfoModel.setStatus(status);
                        //运费
                        double shippingTotal = ordersBean.getShippingTotal();
                        orderMainPayInfoModel.setShippingTotal(String.format("%.2f", shippingTotal));
                        orderObligList.add(orderMainPayInfoModel);
                        //是否显示支付布局
                        int isCancel = ordersBean.getIsCancel();
                        int status1 = ordersBean.getStatus();
                        String paymentType = ordersBean.getPaymentType();
                        String parentId = ordersBean.getParentId();
                        Log.e("TAG_支付", "isCancel=" + isCancel + ";status1=" + status1 + ";paymentType=" + paymentType
                                + ";parentId=" + parentId + ";employee_auth=" + employee_auth);
                        if (isCancel == 0 && status1 == 1 && !"offline".equals(paymentType) && parentId == null && !"1".equals(employee_auth)) {
                            orderMainPayInfoModel.setNeedPay(true);
                        } else {
                            orderMainPayInfoModel.setNeedPay(false);
                        }
                        //订单id
                        orderMainPayInfoModel.setOrderId(orderId);
                        //订单号
                        orderMainPayInfoModel.setSn(sn);
                        //订单创建时间
                        long createTime = ordersBean.getCreateTime();
                        orderMainPayInfoModel.setCreateTime(createTime);
                    }
                }
                if (pageNo > 1) {
                    isUpPull = false;
                    slOrderOblig.setLoading(false);
                    adapter.addData(orderObligList);
                } else {
                    adapter.setData(orderObligList);
                }
                if (orderObligList == null || orderObligList.size() == 0) {
                    llError.setVisibility(View.VISIBLE);
                    rcOrderOblig.setVisibility(View.GONE);
                } else {
                    llError.setVisibility(View.GONE);
                    rcOrderOblig.setVisibility(View.VISIBLE);
                }
            } else {
                if (pageNo > 1) {
                    adapter.upFootText();
                    llError.setVisibility(View.GONE);
                    rcOrderOblig.setVisibility(View.VISIBLE);
                } else {
                    llError.setVisibility(View.VISIBLE);
                    rcOrderOblig.setVisibility(View.GONE);
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
            slOrderOblig.setRefreshing(false);
            isDownPull = false;

        }
        if (isUpPull) {
            isUpPull = false;
            slOrderOblig.setLoading(false);
        }
    }

    //查看订单
    @Override
    public void OnLookOrderClick(int position) {
        Object o = orderObligList.get(position);
        if (o instanceof OrderMainPayInfoModel) {
            OrderMainPayInfoModel infoModel = (OrderMainPayInfoModel) o;
            int orderId = infoModel.getOrderId();
            startOrderDetailsActivity(orderId, 1);
        } else if (o instanceof OrderGoodsContentModel) {
            OrderGoodsContentModel goodsModel = (OrderGoodsContentModel) o;
            int orderId = goodsModel.getOrderId();
            startOrderDetailsActivity(orderId, 1);
        }
    }

    //立即支付
    @Override
    public void OnPayMoneyClick(int position) {
        Object o = orderObligList.get(position);
        if (o instanceof OrderMainPayInfoModel) {
            OrderMainPayInfoModel infoModel = (OrderMainPayInfoModel) o;
            if (Config.isWebViewPay) {
                int orderid = infoModel.getOrderId();
                Intent intent = new Intent(getActivity(), WebViewH5Activity.class);
                intent.putExtra("webViewUrl", Config.ORDERPAY + orderid);
                startActivity(intent);
            } else {
                //订单号
                String sn = infoModel.getSn();
                Log.e("TAG_立即支付", "sn=" + sn);
                //支付金额
                String needPayMoney = infoModel.getNeedPayMoney();
                //订单创建时间
                long createTime = infoModel.getCreateTime();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Log.e("TAG_时间", "createTime=" + df.format(new Date(createTime)));
                String format = df.format(new Date(createTime + 2 * 60 * 60 * 1000));
                Intent intent = new Intent(getActivity(), PayActivity.class);
                intent.putExtra("sn", sn);
                intent.putExtra("needPayMoney", needPayMoney);
                intent.putExtra("payTime", format);
                startActivity(intent);
            }
        }
    }

    //查看主订单
    @Override
    public void OnLookMainOrderClick(int position) {

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        pageNo = 1;
        slOrderOblig.setRefreshing(true);
        isDownPull = true;
        OkHttpDemand();
    }

    //上拉加载
    @Override
    public void onLoad() {
//        Log.e("TAG_待付款","onLoad="+(rcOrderOblig !=null)+( rcOrderOblig.getAdapter() != null));
        if (rcOrderOblig != null && rcOrderOblig.getAdapter() != null) {
            isUpPull = true;
            slOrderOblig.setLoading(true);
            pageNo++;
            Log.e("TAG_待付款上拉加载", "pageNo=" + pageNo);
            OkHttpDemand();
        }
    }
}