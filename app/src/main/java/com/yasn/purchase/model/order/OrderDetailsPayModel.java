package com.yasn.purchase.model.order;

import java.io.Serializable;

/**
 * Created by gs on 2018/5/25.
 */

public class OrderDetailsPayModel implements Serializable {
    private String shippingTotal;//运费金额
    private String deductMoney;// 积分抵扣金额
    private String goodsAmount;//商品总额
    private String needPayMoney;//消极
    private int status;
    private int orderId;//订单号

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getDeductMoney() {
        return deductMoney;
    }

    public void setDeductMoney(String deductMoney) {
        this.deductMoney = deductMoney;
    }

    public String getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(String needPayMoney) {
        this.needPayMoney = needPayMoney;
    }
}
