package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/1/22.
 */

public class ShopCarAdapterModel implements Serializable {

    private int itmeType;//布局类型
    private int id;
    private int store_id;
    private String storeName;//店铺名称
    private String freeShipMoney;//包邮

    private String name;
    private String imageDefault;
    private double price;
    private int num;
    private int isCheck;
    private double storeCheckPrice;
    private int enableStore;
    private int productId;
    private int goodsOff;
    private int isBeforeSale;
    private int isExist;
    private int smallSale;
    private int step;
    private int goodsId;
    private double needPayMoney;

    public double getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(double needPayMoney) {
        this.needPayMoney = needPayMoney;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getSmallSale() {
        return smallSale;
    }

    public void setSmallSale(int smallSale) {
        this.smallSale = smallSale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsExist() {
        return isExist;
    }

    public void setIsExist(int isExist) {
        this.isExist = isExist;
    }

    public int getIsBeforeSale() {
        return isBeforeSale;
    }

    public void setIsBeforeSale(int isBeforeSale) {
        this.isBeforeSale = isBeforeSale;
    }

    public int getGoodsOff() {
        return goodsOff;
    }

    public void setGoodsOff(int goodsOff) {
        this.goodsOff = goodsOff;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getEnableStore() {
        return enableStore;
    }

    public void setEnableStore(int enableStore) {
        this.enableStore = enableStore;
    }

    public double getStoreCheckPrice() {
        return storeCheckPrice;
    }

    public void setStoreCheckPrice(double storeCheckPrice) {
        this.storeCheckPrice = storeCheckPrice;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageDefault() {
        return imageDefault;
    }

    public void setImageDefault(String imageDefault) {
        this.imageDefault = imageDefault;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFreeShipMoney() {
        return freeShipMoney;
    }

    public void setFreeShipMoney(String freeShipMoney) {
        this.freeShipMoney = freeShipMoney;
    }

    public int getItmeType() {
        return itmeType;
    }

    public void setItmeType(int itmeType) {
        this.itmeType = itmeType;
    }


    private List<String> specList;

    public List<String> getSpecList() {
        return specList;
    }

    public void setSpecList(List<String> specList) {
        this.specList = specList;
    }

    @Override
    public String toString() {
        return "ShopCarAdapterModel{" +
                "itmeType=" + itmeType +
                ", id=" + id +
                ", store_id=" + store_id +
                ", storeName='" + storeName + '\'' +
                ", freeShipMoney='" + freeShipMoney + '\'' +
                ", name='" + name + '\'' +
                ", imageDefault='" + imageDefault + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", isCheck=" + isCheck +
                ", storeCheckPrice=" + storeCheckPrice +
                ", enableStore=" + enableStore +
                ", productId=" + productId +
                ", goodsOff=" + goodsOff +
                ", isBeforeSale=" + isBeforeSale +
                ", isExist=" + isExist +
                ", smallSale=" + smallSale +
                ", step=" + step +
                ", goodsId=" + goodsId +
                ", specList=" + specList +
                '}';
    }
}
