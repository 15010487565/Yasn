package com.yasn.purchase.model;

/**
 * Created by gs on 2018/2/11.
 */

public class EventBusMsg {

    private String msg;
    public EventBusMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg(){
        return msg;
    }
}
