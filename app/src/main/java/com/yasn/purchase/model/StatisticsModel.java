package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/7/18.
 */

public class StatisticsModel implements Serializable {

    /**
     * data : {"sumInfoEachMonth":[{"orderNum":1,"statisticsMonth":"2018-03","totalCount":0.01,"goodsNum":1},{"orderNum":1,"statisticsMonth":"2017-09","totalCount":0.01,"goodsNum":1}],"TotalAll":{"orderNum":2,"totalCount":0.02,"goodsNum":2}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sumInfoEachMonth : [{"orderNum":1,"statisticsMonth":"2018-03","totalCount":0.01,"goodsNum":1},{"orderNum":1,"statisticsMonth":"2017-09","totalCount":0.01,"goodsNum":1}]
         * TotalAll : {"orderNum":2,"totalCount":0.02,"goodsNum":2}
         */

        private TotalAllBean TotalAll;
        private List<SumInfoEachMonthBean> sumInfoEachMonth;

        public TotalAllBean getTotalAll() {
            return TotalAll;
        }

        public void setTotalAll(TotalAllBean TotalAll) {
            this.TotalAll = TotalAll;
        }

        public List<SumInfoEachMonthBean> getSumInfoEachMonth() {
            return sumInfoEachMonth;
        }

        public void setSumInfoEachMonth(List<SumInfoEachMonthBean> sumInfoEachMonth) {
            this.sumInfoEachMonth = sumInfoEachMonth;
        }

        public static class TotalAllBean {
            /**
             * orderNum : 2
             * totalCount : 0.02
             * goodsNum : 2
             */

            private int orderNum;
            private double totalCount;
            private int goodsNum;

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public double getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(double totalCount) {
                this.totalCount = totalCount;
            }

            public int getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(int goodsNum) {
                this.goodsNum = goodsNum;
            }
        }

        public static class SumInfoEachMonthBean {
            /**
             * orderNum : 1
             * statisticsMonth : 2018-03
             * totalCount : 0.01
             * goodsNum : 1
             */

            private int orderNum;
            private String statisticsMonth;
            private double totalCount;
            private int goodsNum;

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public String getStatisticsMonth() {
                return statisticsMonth;
            }

            public void setStatisticsMonth(String statisticsMonth) {
                this.statisticsMonth = statisticsMonth;
            }

            public double getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(double totalCount) {
                this.totalCount = totalCount;
            }

            public int getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(int goodsNum) {
                this.goodsNum = goodsNum;
            }
        }
    }
}
