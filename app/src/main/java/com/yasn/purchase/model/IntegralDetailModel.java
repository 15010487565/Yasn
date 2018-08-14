package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/7/25.
 */

public class IntegralDetailModel implements Serializable{

    /**
     * code : 0
     * message : null
     * data : {"pointHistoryList":[{"page":null,"pageSize":null,"id":24,"memberId":28,"point":0,"time":1484875708,"reason":"2017年01月20日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":297,"memberId":28,"point":0,"time":1484993763,"reason":"2017年01月21日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":1600,"memberId":28,"point":0,"time":1486344739,"reason":"2017年02月06日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":2813,"memberId":28,"point":0,"time":1486827822,"reason":"2017年02月11日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":2929,"memberId":28,"point":0,"time":1486878897,"reason":"2017年02月12日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":3463,"memberId":28,"point":0,"time":1487075922,"reason":"2017年02月14日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":5000,"memberId":28,"point":0,"time":1487303796,"reason":"2017年02月17日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":13688,"memberId":28,"point":0,"time":1489140403,"reason":"2017年03月10日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":14618,"memberId":28,"point":0,"time":1489395436,"reason":"2017年03月13日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":15353,"memberId":28,"point":0,"time":1489542583,"reason":"2017年03月15日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null}],"point":1976}
     * totalCount : 10
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
         * pointHistoryList : [{"page":null,"pageSize":null,"id":24,"memberId":28,"point":0,"time":1484875708,"reason":"2017年01月20日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":297,"memberId":28,"point":0,"time":1484993763,"reason":"2017年01月21日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":1600,"memberId":28,"point":0,"time":1486344739,"reason":"2017年02月06日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":2813,"memberId":28,"point":0,"time":1486827822,"reason":"2017年02月11日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":2929,"memberId":28,"point":0,"time":1486878897,"reason":"2017年02月12日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":3463,"memberId":28,"point":0,"time":1487075922,"reason":"2017年02月14日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":5000,"memberId":28,"point":0,"time":1487303796,"reason":"2017年02月17日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":13688,"memberId":28,"point":0,"time":1489140403,"reason":"2017年03月10日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":14618,"memberId":28,"point":0,"time":1489395436,"reason":"2017年03月13日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null},{"page":null,"pageSize":null,"id":15353,"memberId":28,"point":0,"time":1489542583,"reason":"2017年03月15日登录","relatedId":null,"type":1,"operator":"member","mp":1,"pointType":0,"enableMp":null}]
         * point : 1976
         */

        private int point;
        private List<PointHistoryListBean> pointHistoryList;

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public List<PointHistoryListBean> getPointHistoryList() {
            return pointHistoryList;
        }

        public void setPointHistoryList(List<PointHistoryListBean> pointHistoryList) {
            this.pointHistoryList = pointHistoryList;
        }

        public static class PointHistoryListBean {
            /**
             * page : null
             * pageSize : null
             * id : 24
             * memberId : 28
             * point : 0
             * time : 1484875708
             * reason : 2017年01月20日登录
             * relatedId : null
             * type : 1
             * operator : member
             * mp : 1
             * pointType : 0
             * enableMp : null
             */

            private Object page;
            private Object pageSize;
            private int id;
            private int memberId;
            private int point;
            private long time;
            private String reason;
            private Object relatedId;
            private int type;
            private String operator;
            private int mp;
            private int pointType;
            private Object enableMp;

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

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public Object getRelatedId() {
                return relatedId;
            }

            public void setRelatedId(Object relatedId) {
                this.relatedId = relatedId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public int getMp() {
                return mp;
            }

            public void setMp(int mp) {
                this.mp = mp;
            }

            public int getPointType() {
                return pointType;
            }

            public void setPointType(int pointType) {
                this.pointType = pointType;
            }

            public Object getEnableMp() {
                return enableMp;
            }

            public void setEnableMp(Object enableMp) {
                this.enableMp = enableMp;
            }
        }
    }
}
