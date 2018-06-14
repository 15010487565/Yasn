package com.yasn.purchase.model.order;

import java.util.List;

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
    private List<OrderGoodsValueBean> list;
    private String goodsId;
    private int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public List<OrderGoodsValueBean> getList() {
        return list;
    }

    public void setList(List<OrderGoodsValueBean> list) {
        this.list = list;
    }

    public static class OrderGoodsValueBean{
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "OrderGoodsValueBean{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }
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

    @Override
    public String toString() {
        return "OrderGoodsContentModel{" +
                "ShopName='" + ShopName + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", price='" + price + '\'' +
                ", list=" + list +
                '}';
    }
}
