package com.yasn.purchasetest.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yasn.purchasetest.R;
import com.yasn.purchasetest.adapter.OrderAdapter;
import com.yasn.purchasetest.model.order.AllOrderBean;
import com.yasn.purchasetest.model.order.GoodsOrderInfo;
import com.yasn.purchasetest.model.order.OrderDataHelper;
import com.yasn.purchasetest.model.order.OrderGoodsItem;
import com.yasn.purchasetest.model.order.OrderSummary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;

/**
 * Created by gs on 2018/1/8.
 */

public class AllOrderFragment extends BaseFragment {

    private RelativeLayout title;
    private RecyclerView rlvAllOrder;
    private OrderAdapter mAllOrderAdapter;
    private List<Object> mAllOrderList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_allorder;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        title = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        title.setVisibility(View.GONE);
        rlvAllOrder = (RecyclerView) view.findViewById(R.id.rlv_allorder);
        rlvAllOrder.setLayoutManager(new LinearLayoutManager(getFragmentActivity()));
        mAllOrderAdapter = new OrderAdapter(getActivity(), mAllOrderList);
        rlvAllOrder.setAdapter(mAllOrderAdapter);
        initData();
        mAllOrderAdapter.notifyDataSetChanged();
    }

    private void initData() {
        AllOrderBean allOrderBean = new AllOrderBean();

        OrderSummary orderSummary1 = new OrderSummary();
        orderSummary1.setId(1);
        orderSummary1.setTotalPrice(10);
        orderSummary1.setStatus("0");
        orderSummary1.setOrderCode("2016111301");

        OrderSummary orderSummary2 = new OrderSummary();
        orderSummary2.setId(2);
        orderSummary2.setTotalPrice(10);
        orderSummary2.setStatus("1");
        orderSummary2.setOrderCode("2016111303");

        OrderGoodsItem orderGoodsItem1 = new OrderGoodsItem();
        OrderGoodsItem orderGoodsItem2 = new OrderGoodsItem();
        OrderGoodsItem orderGoodsItem3 = new OrderGoodsItem();
        OrderGoodsItem orderGoodsItem4 = new OrderGoodsItem();
        GoodsOrderInfo goodsOrderInfo1 = new GoodsOrderInfo();
        GoodsOrderInfo goodsOrderInfo2 = new GoodsOrderInfo();
        GoodsOrderInfo goodsOrderInfo3 = new GoodsOrderInfo();
        GoodsOrderInfo goodsOrderInfo4 = new GoodsOrderInfo();

        goodsOrderInfo1.setOrderCode("2016111301");
        goodsOrderInfo1.setStatus("0");
        goodsOrderInfo1.setShopName("狼牙");
        orderGoodsItem1.setTotalPrice(5);
        orderGoodsItem1.setProductName("狼牙狼蛛键盘");
        orderGoodsItem1.setCount(2);
        orderGoodsItem1.setProductPic("http://img2.3lian.com/2014/c7/25/d/40.jpg");
        orderGoodsItem1.setOrder(goodsOrderInfo1);

        goodsOrderInfo2.setOrderCode("2016111302");
        goodsOrderInfo2.setStatus("0");
        goodsOrderInfo2.setShopName("达尔优");
        orderGoodsItem2.setTotalPrice(5);
        orderGoodsItem2.setProductName("牧马人鼠标");
        orderGoodsItem2.setCount(2);
        orderGoodsItem2.setProductPic("http://img2.3lian.com/2014/c7/25/d/41.jpg");
        orderGoodsItem2.setOrder(goodsOrderInfo2);

        goodsOrderInfo4.setOrderCode("2016111302");
        goodsOrderInfo4.setStatus("0");
        goodsOrderInfo4.setShopName("达尔优");
        orderGoodsItem4.setTotalPrice(5);
        orderGoodsItem4.setProductName("牧马人键盘");
        orderGoodsItem4.setCount(2);
        orderGoodsItem4.setProductPic("http://img2.3lian.com/2014/c7/25/d/41.jpg");
        orderGoodsItem4.setOrder(goodsOrderInfo4);

        List<OrderGoodsItem> orderGoodsItemList = new ArrayList<>();
        orderGoodsItemList.add(orderGoodsItem1);
        orderGoodsItemList.add(orderGoodsItem4);
        orderGoodsItemList.add(orderGoodsItem2);
        orderSummary1.setOrderDetailList(orderGoodsItemList);

        goodsOrderInfo3.setOrderCode("2016111303");
        goodsOrderInfo3.setStatus("0");
        goodsOrderInfo3.setShopName("酷睿");
        orderGoodsItem3.setTotalPrice(5);
        orderGoodsItem3.setProductName("酷睿处理器");
        orderGoodsItem3.setCount(2);
        orderGoodsItem3.setProductPic("http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg");
        orderGoodsItem3.setOrder(goodsOrderInfo3);

        List<OrderGoodsItem> orderGoodsItemList1 = new ArrayList<>();
        orderGoodsItemList1.add(orderGoodsItem3);
        orderSummary2.setOrderDetailList(orderGoodsItemList1);

        List<OrderSummary> orderSummaries = new ArrayList<>();
        orderSummaries.add(orderSummary1);
        orderSummaries.add(orderSummary2);
        allOrderBean.setResultList(orderSummaries);

        mAllOrderList.addAll(OrderDataHelper.getDataAfterHandle(allOrderBean.getResultList()));

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

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
}