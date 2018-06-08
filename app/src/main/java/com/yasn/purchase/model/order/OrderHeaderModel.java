package com.yasn.purchase.model.order;

/**
 * OrderHeaderModel 表示每个小订单的头部信息（订单号、订单状态、店铺名称）
 * Created by admin on 2016/11/8.
 */
public class OrderHeaderModel {

    private String orderCode;
    /**
     * 订单状态(0为新建订单，
     * 货到付款需确认,1为已确认,2为已支付,3为已发货,4为已收货,5为已完成,
     * 6为订单取消（货到付款审核未通过、新建订单取消、订单发货前取消）,
     * 7为交易成功已申请售后申请,8为待人工推单,9为风控审核,100为已支付待放款,200为放款失败)
     */
    private int status;
    private int payStatus;// 订单支付状态(0未付款,1部分付款,2已全部支付)
    private int shipStatus;// 订单发货状态(0未发货,1已发货,2已收货)
    public String getOrderCode() {
        return orderCode;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(int shipStatus) {
        this.shipStatus = shipStatus;
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
