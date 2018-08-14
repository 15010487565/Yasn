package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/8/3.
 */

public class MakerShopOrderModel implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * needPayMoney : 19.9
         * createTime : 1498789911
         * statusText : 已完成
         * mobile : 18039492000
         * sn : 149878991132-1
         * shop_name : 郸城县车派俱乐部
         * order_id : 34347
         * memberId : 16829
         * status : 5
         */

        private double needPayMoney;
        private long createTime;
        private String statusText;
        private String mobile;
        private String sn;
        private String shop_name;
        private int order_id;
        private int memberId;
        private int status;

        public double getNeedPayMoney() {
            return needPayMoney;
        }

        public void setNeedPayMoney(double needPayMoney) {
            this.needPayMoney = needPayMoney;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
