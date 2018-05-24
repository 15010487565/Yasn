package com.yasn.purchasetest.model.order;

/**
 * OrderHeaderModel 表示每个小订单的头部信息（订单号、订单状态、店铺名称）
 * Created by admin on 2016/11/8.
 */
public class OrderHeaderModel {

    private String orderCode;
    private int status;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
