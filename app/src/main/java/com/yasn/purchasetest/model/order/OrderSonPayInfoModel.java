package com.yasn.purchasetest.model.order;

/**
 * OrderMainPayInfoModel 表示大订单的支付信息（金额、订单状态,运费）
 * Created by admin on 2016/11/8.
 */
public class OrderSonPayInfoModel {

    private int orderId;
    private String sn;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
