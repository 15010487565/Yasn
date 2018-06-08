package com.yasn.purchase.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.adapter.OrderExpressAdapter;
import com.yasn.purchase.model.order.OrderQueryExpressModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 全部订单
 * Created by gs on 2018/1/8.
 */

public class OrderExpressFragment extends OrderFragment {

    private OrderQueryExpressModel.DeliverysBean deliverys;
    RecyclerView rcOrderExpress;
    OrderExpressAdapter adapter;
    private TextView loginName;
    private TextView loginNo;
    public OrderExpressFragment(OrderQueryExpressModel.DeliverysBean deliverys) {
        this.deliverys = deliverys;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_orderexpress;
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();

    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        super.initView(inflater, view);
        RelativeLayout title = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        title.setVisibility(View.GONE);
        loginName = (TextView) view.findViewById(R.id.tv_ExpressLogiName);
        loginNo = (TextView) view.findViewById(R.id.tv_ExpressLogiNo);
        initRecyclerView(view);
        initData();
    }

    private void initData() {
        if (deliverys != null) {
            String logiNo = deliverys.getLogiNo();
            String logiName = deliverys.getLogiName();
            loginName.setText("快递公司:"+logiName);
            loginNo.setText("快递单号:"+logiNo);
            OrderQueryExpressModel.DeliverysBean.ExpressDetailBean expressDetail = deliverys.getExpressDetail();
            List<OrderQueryExpressModel.DeliverysBean.ExpressDetailBean.DataBean> data = expressDetail.getData();
            adapter.setData(data);
        }
    }

    private void initRecyclerView(View view) {

        rcOrderExpress = (RecyclerView) view.findViewById(R.id.rc_OrderExpress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(true);
        rcOrderExpress.setLayoutManager(linearLayoutManager);
        //创建Adapter
        adapter = new OrderExpressAdapter(getActivity());
        rcOrderExpress.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
    protected void OkHttpDemand() {

    }
}