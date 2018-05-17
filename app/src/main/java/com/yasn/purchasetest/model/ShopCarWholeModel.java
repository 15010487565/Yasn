package com.yasn.purchasetest.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/4/28.
 */

public class ShopCarWholeModel implements Serializable {

    /**
     * code : 200
     * message : null
     * data : [[{"id":92604,"storeId":1,"productId":5601,"goodsId":3662,"minNum":1,"maxNum":3,"lvId":1,"wholesalePrice":12,"activityPrice":0},{"id":92613,"storeId":1,"productId":5601,"goodsId":3662,"minNum":4,"maxNum":2000000000,"lvId":1,"wholesalePrice":12,"activityPrice":0}],[{"id":97978,"storeId":1,"productId":6429,"goodsId":3818,"minNum":1,"maxNum":3,"lvId":1,"wholesalePrice":20,"activityPrice":0},{"id":97987,"storeId":1,"productId":6429,"goodsId":3818,"minNum":4,"maxNum":2000000000,"lvId":1,"wholesalePrice":18,"activityPrice":0}],[{"id":null,"storeId":1,"productId":6499,"goodsId":3807,"minNum":1,"maxNum":2000000000,"lvId":1,"wholesalePrice":1000,"activityPrice":0}],[{"id":70935,"storeId":99,"productId":4069,"goodsId":2415,"minNum":1,"maxNum":2000000000,"lvId":1,"wholesalePrice":205,"activityPrice":0}],[{"id":94789,"storeId":1,"productId":5776,"goodsId":3724,"minNum":1,"maxNum":2,"lvId":1,"wholesalePrice":11,"activityPrice":0},{"id":94798,"storeId":1,"productId":5776,"goodsId":3724,"minNum":3,"maxNum":2000000000,"lvId":1,"wholesalePrice":9,"activityPrice":0}],[{"id":78151,"storeId":99,"productId":2392,"goodsId":1566,"minNum":1,"maxNum":2000000000,"lvId":1,"wholesalePrice":492,"activityPrice":0}],[{"id":72939,"storeId":99,"productId":3626,"goodsId":2218,"minNum":1,"maxNum":2000000000,"lvId":1,"wholesalePrice":145,"activityPrice":0}],[{"id":null,"storeId":1,"productId":6490,"goodsId":3817,"minNum":1,"maxNum":2000000000,"lvId":1,"wholesalePrice":77,"activityPrice":0}],[{"id":null,"storeId":1,"productId":6493,"goodsId":3817,"minNum":1,"maxNum":2000000000,"lvId":1,"wholesalePrice":90,"activityPrice":0}],[{"id":null,"storeId":1,"productId":4965,"goodsId":2707,"minNum":1,"maxNum":2000000000,"lvId":1,"wholesalePrice":5656,"activityPrice":0}],[{"id":94981,"storeId":1,"productId":1088,"goodsId":12,"minNum":1,"maxNum":2,"lvId":1,"wholesalePrice":1.1,"activityPrice":0},{"id":94990,"storeId":1,"productId":1088,"goodsId":12,"minNum":3,"maxNum":4,"lvId":1,"wholesalePrice":2,"activityPrice":0},{"id":94999,"storeId":1,"productId":1088,"goodsId":12,"minNum":5,"maxNum":6,"lvId":1,"wholesalePrice":12,"activityPrice":0},{"id":95008,"storeId":1,"productId":1088,"goodsId":12,"minNum":7,"maxNum":8,"lvId":1,"wholesalePrice":21,"activityPrice":0},{"id":95017,"storeId":1,"productId":1088,"goodsId":12,"minNum":9,"maxNum":10,"lvId":1,"wholesalePrice":31,"activityPrice":0},{"id":95026,"storeId":1,"productId":1088,"goodsId":12,"minNum":11,"maxNum":12,"lvId":1,"wholesalePrice":41,"activityPrice":0},{"id":95035,"storeId":1,"productId":1088,"goodsId":12,"minNum":13,"maxNum":2000000000,"lvId":1,"wholesalePrice":51,"activityPrice":0}]]
     */

    private int code;
    private Object message;
    private List<List<DataBean>> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 92604
         * storeId : 1
         * productId : 5601
         * goodsId : 3662
         * minNum : 1
         * maxNum : 3
         * lvId : 1
         * wholesalePrice : 12.0
         * activityPrice : 0.0
         */

        private int id;
        private int storeId;
        private int productId;
        private int goodsId;
        private int minNum;
        private int maxNum;
        private int lvId;
        private double wholesalePrice;
        private double activityPrice;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getMinNum() {
            return minNum;
        }

        public void setMinNum(int minNum) {
            this.minNum = minNum;
        }

        public int getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(int maxNum) {
            this.maxNum = maxNum;
        }

        public int getLvId() {
            return lvId;
        }

        public void setLvId(int lvId) {
            this.lvId = lvId;
        }

        public double getWholesalePrice() {
            return wholesalePrice;
        }

        public void setWholesalePrice(double wholesalePrice) {
            this.wholesalePrice = wholesalePrice;
        }

        public double getActivityPrice() {
            return activityPrice;
        }

        public void setActivityPrice(double activityPrice) {
            this.activityPrice = activityPrice;
        }
    }
}
