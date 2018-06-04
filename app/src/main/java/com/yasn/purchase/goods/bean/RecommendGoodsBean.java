package com.yasn.purchase.goods.bean;

import java.math.BigDecimal;

public class RecommendGoodsBean {

    public String title;
    public String imag;
    public BigDecimal price;
    public String currentPrice;


    public RecommendGoodsBean(String title, String imag, BigDecimal price, String currentPrice) {
        this.title = title;
        this.imag = imag;
        this.price = price;
        this.currentPrice = currentPrice;
    }

}
