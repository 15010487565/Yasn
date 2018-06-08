package com.yasn.purchase.model.order;

/**
 * 订单物流状态
 * Created by licheng on 22/2/17.
 */

public class OrderExpressMsgModel {
    private int itemTyte;
    private String nu;
    private String time;
    private String message;

    public int getItemTyte() {
        return itemTyte;
    }

    public void setItemTyte(int itemTyte) {
        this.itemTyte = itemTyte;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
