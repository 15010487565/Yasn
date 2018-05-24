package com.yasn.purchasetest.model.order;

/**
 * OrderMainPayInfoModel 表示大订单的支付信息（金额、订单状态,运费）
 * Created by admin on 2016/11/8.
 */
public class OrderMainPayInfoModel {

    private String needPayMoney;
    private int status;
    private String shippingTotal;
    private boolean isNeedPay;
    private int orderId;
    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isNeedPay() {
        return isNeedPay;
    }

    public void setNeedPay(boolean needPay) {
        isNeedPay = needPay;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(String needPayMoney) {
        this.needPayMoney = needPayMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
