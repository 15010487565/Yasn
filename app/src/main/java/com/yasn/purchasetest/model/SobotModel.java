package com.yasn.purchasetest.model;

/**
 * Created by gs on 2018/3/19.
 */

public class SobotModel {

    //咨询内容标题，必填
    private String sobotGoodsTitle;
    //咨询内容图片，选填 但必须是图片地址
    private String sobotGoodsImgUrl;
    //咨询来源页，必填
    private String sobotGoodsFromUrl;
    //描述，选填
    private String sobotGoodsDescribe;
    //标签，选填
    private String sobotGoodsLable;

    public String getSobotGoodsTitle() {
        return sobotGoodsTitle;
    }

    public void setSobotGoodsTitle(String sobotGoodsTitle) {
        this.sobotGoodsTitle = sobotGoodsTitle;
    }

    public String getSobotGoodsImgUrl() {
        return sobotGoodsImgUrl;
    }

    public void setSobotGoodsImgUrl(String sobotGoodsImgUrl) {
        this.sobotGoodsImgUrl = sobotGoodsImgUrl;
    }

    public String getSobotGoodsFromUrl() {
        return sobotGoodsFromUrl;
    }

    public void setSobotGoodsFromUrl(String sobotGoodsFromUrl) {
        this.sobotGoodsFromUrl = sobotGoodsFromUrl;
    }

    public String getSobotGoodsDescribe() {
        return sobotGoodsDescribe;
    }

    public void setSobotGoodsDescribe(String sobotGoodsDescribe) {
        this.sobotGoodsDescribe = sobotGoodsDescribe;
    }

    public String getSobotGoodsLable() {
        return sobotGoodsLable;
    }

    public void setSobotGoodsLable(String sobotGoodsLable) {
        this.sobotGoodsLable = sobotGoodsLable;
    }

    @Override
    public String toString() {
        return "SobotModel{" +
                "sobotGoodsTitle='" + sobotGoodsTitle + '\'' +
                ", sobotGoodsImgUrl='" + sobotGoodsImgUrl + '\'' +
                ", sobotGoodsFromUrl='" + sobotGoodsFromUrl + '\'' +
                ", sobotGoodsDescribe='" + sobotGoodsDescribe + '\'' +
                ", sobotGoodsLable='" + sobotGoodsLable + '\'' +
                '}';
    }
}
