package com.yasn.purchasetest.model.order;

/**
 * OrderGoodsContentModel 表示小订单中的商品
 * Created by admin on 2016/11/8.
 */
public class OrderGoodsContentModel {

    private String ShopName;
    private String image;//图片
    private String name;//商品名称
    private int num;//订单数量
    private String price;

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
