package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2017/12/26.
 * 高毛利商品
 */

public class OrderModel implements Serializable {
    private String text;
    private String image;
    private int itemType;
    private String money;
    private String button1;
    private String button2;
    private String button3;

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

    public int getItemType() {
        return itemType;
    }
    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
    public OrderModel() {
    }
    public OrderModel(String text, int itemType, String money) {
        this.text = text;
        this.itemType = itemType;
    }

    public OrderModel(String text, int itemType, String money, String image) {
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
