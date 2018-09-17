package com.yasn.purchase.model.order;

import java.io.Serializable;

/**
 * Created by gs on 2018/5/25.
 */

public class OrderDetailsGiftModel implements Serializable{
    private String image;
    private String name;
    private String money;
    private String num;
    private String moreBuyToSend;
    private int itmeType;//布局类型

    public int getItmeType() {
        return itmeType;
    }

    public void setItmeType(int itmeType) {
        this.itmeType = itmeType;
    }

    public String getMoreBuyToSend() {
        return moreBuyToSend;
    }

    public void setMoreBuyToSend(String moreBuyToSend) {
        this.moreBuyToSend = moreBuyToSend;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
