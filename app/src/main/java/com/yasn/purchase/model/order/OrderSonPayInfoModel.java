package com.yasn.purchase.model.order;

/**
 * OrderMainPayInfoModel 表示大订单的支付信息（金额、订单状态,运费）
 * Created by admin on 2016/11/8.
 */
public class OrderSonPayInfoModel {

    private int orderId;
    private String sn;
    private int parentId;
    private String needPayMoney;
    private String shippingTotal;

    public String getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(String needPayMoney) {
        this.needPayMoney = needPayMoney;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

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
