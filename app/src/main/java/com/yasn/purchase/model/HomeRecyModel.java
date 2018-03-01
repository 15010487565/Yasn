package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2017/12/26.
 */

public class HomeRecyModel implements Serializable {

    public int subject_id;
    public int itemType;

    private String goodsid;
    private String image;
    private String text;
    private String advert;
    private String money;
    private String totalBuyCount;//已售数量

    private boolean autotrophy;
    private boolean purchase;
    private boolean presell;

    private String button1;
    private String button2;
    private String button3;

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public boolean isAutotrophy() {
        return autotrophy;
    }

    public void setAutotrophy(boolean autotrophy) {
        this.autotrophy = autotrophy;
    }

    public boolean isPurchase() {
        return purchase;
    }

    public void setPurchase(boolean purchase) {
        this.purchase = purchase;
    }

    public boolean isPresell() {
        return presell;
    }

    public void setPresell(boolean presell) {
        this.presell = presell;
    }

    public String getAdvert() {
        return advert;
    }

    public void setAdvert(String advert) {
        this.advert = advert;
    }

    public String getTotalBuyCount() {
        return totalBuyCount;
    }

    public void setTotalBuyCount(String totalBuyCount) {
        this.totalBuyCount = totalBuyCount;
    }

    public String getButton1() {
        return button1;
    }

    public void setButton1(String button1) {
        this.button1 = button1;
    }

    public String getButton2() {
        return button2;
    }

    public void setButton2(String button2) {
        this.button2 = button2;
    }

    public String getButton3() {
        return button3;
    }

    public void setButton3(String button3) {
        this.button3 = button3;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
    public HomeRecyModel() {
    }
    public HomeRecyModel(String text, int itemType, String money) {
        this.text = text;
        this.itemType = itemType;
    }

    public HomeRecyModel(String text, int itemType, String money, String image) {
        this.text = text;
        this.itemType = itemType;
        this.image = image;
        this.money = money;
    }

    @Override
    public String toString() {
        return "HomeRecyModel{" +
                "text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", itemType=" + itemType +
                ", money='" + money + '\'' +
                '}';
    }
}
