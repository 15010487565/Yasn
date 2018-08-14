package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/7/25.
 */

public class IntegralFreezeModel implements Serializable {

    /**
     * code : 0
     * message : null
     * data : {"freezePointList":[{"page":null,"pageSize":null,"id":7150,"memberid":28,"point":0,"mp":24,"orderid":48388,"dateline":1519981994,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7167,"memberid":28,"point":0,"mp":128,"orderid":48498,"dateline":1522746752,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7168,"memberid":28,"point":0,"mp":4,"orderid":48499,"dateline":1522746753,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7169,"memberid":28,"point":0,"mp":2,"orderid":48503,"dateline":1522807935,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7269,"memberid":28,"point":0,"mp":5,"orderid":49037,"dateline":1528883870,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7428,"memberid":28,"point":0,"mp":50065,"orderid":0,"dateline":1531275717,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7267,"memberid":28,"point":0,"mp":40,"orderid":49030,"dateline":1528879647,"type":"order_pay_use"},{"page":null,"pageSize":null,"id":7268,"memberid":28,"point":0,"mp":29,"orderid":49037,"dateline":1528883870,"type":"order_pay_use"}],"point":1976}
     * totalCount : 8
     * success : false
     */

    private int code;
    private Object message;
    private DataBean data;
    private int totalCount;
    private boolean success;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * freezePointList : [{"page":null,"pageSize":null,"id":7150,"memberid":28,"point":0,"mp":24,"orderid":48388,"dateline":1519981994,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7167,"memberid":28,"point":0,"mp":128,"orderid":48498,"dateline":1522746752,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7168,"memberid":28,"point":0,"mp":4,"orderid":48499,"dateline":1522746753,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7169,"memberid":28,"point":0,"mp":2,"orderid":48503,"dateline":1522807935,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7269,"memberid":28,"point":0,"mp":5,"orderid":49037,"dateline":1528883870,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7428,"memberid":28,"point":0,"mp":50065,"orderid":0,"dateline":1531275717,"type":"order_pay_get"},{"page":null,"pageSize":null,"id":7267,"memberid":28,"point":0,"mp":40,"orderid":49030,"dateline":1528879647,"type":"order_pay_use"},{"page":null,"pageSize":null,"id":7268,"memberid":28,"point":0,"mp":29,"orderid":49037,"dateline":1528883870,"type":"order_pay_use"}]
         * point : 1976
         */

        private int point;
        private List<FreezePointListBean> freezePointList;

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public List<FreezePointListBean> getFreezePointList() {
            return freezePointList;
        }

        public void setFreezePointList(List<FreezePointListBean> freezePointList) {
            this.freezePointList = freezePointList;
        }

        public static class FreezePointListBean {
            /**
             * page : null
             * pageSize : null
             * id : 7150
             * memberid : 28
             * point : 0
             * mp : 24
             * orderid : 48388
             * dateline : 1519981994
             * type : order_pay_get
             */

            private Object page;
            private Object pageSize;
            private int id;
            private int memberid;
            private int point;
            private int mp;
            private int orderid;
            private int dateline;
            private String type;

            public Object getPage() {
                return page;
            }

            public void setPage(Object page) {
                this.page = page;
            }

            public Object getPageSize() {
                return pageSize;
            }

            public void setPageSize(Object pageSize) {
                this.pageSize = pageSize;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMemberid() {
                return memberid;
            }

            public void setMemberid(int memberid) {
                this.memberid = memberid;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public int getMp() {
                return mp;
            }

            public void setMp(int mp) {
                this.mp = mp;
            }

            public int getOrderid() {
                return orderid;
            }

            public void setOrderid(int orderid) {
                this.orderid = orderid;
            }

            public int getDateline() {
                return dateline;
            }

            public void setDateline(int dateline) {
                this.dateline = dateline;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
