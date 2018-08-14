package com.yasn.purchase.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/8/9.
 */

public class AuthorMemberInfoModel implements Serializable{

    /**
     * code : 200
     * message : 操作成功
     * data : {"currentTime":1533782500884,"probationTime":{"page":null,"pageSize":null,"startTime":0,"endTime":0},"authenticationType":[{"id":1,"type":"洗车店","isHide":0,"sort":3},{"id":2,"type":"美容装饰店（单个）","isHide":0,"sort":8},{"id":3,"type":"快修快保店","isHide":0,"sort":5},{"id":4,"type":"美容装饰店（连锁）","isHide":0,"sort":8},{"id":5,"type":"轮胎店","isHide":0,"sort":3},{"id":6,"type":"换油中心","isHide":0,"sort":4},{"id":7,"type":"改装店","isHide":0,"sort":3},{"id":8,"type":"一类、二类汽修厂","isHide":0,"sort":8},{"id":9,"type":"进口4S店","isHide":0,"sort":4},{"id":10,"type":"三类及以下汽修厂","isHide":0,"sort":8},{"id":11,"type":"合资4S店","isHide":0,"sort":4},{"id":12,"type":"国产4S店","isHide":0,"sort":4}],"memberData":{"shopArea":"100-200平","shopNum":100,"city":"东城区","operateTime":1508140656,"pageSize":null,"shopName":"洗车","cityId":2,"prove1":"fs:/images/no_picture.jpg","shopAddress":"北京西城区西城区北环中心","operator":"系统","prove2":"fs:/images/no_picture.jpg","xsyStatus":null,"shopTypeId":1,"province":"北京","memberDataId":13076,"approveTime":1508140656,"class":"com.yasn.shop.model.MemberDataNew","memberId":21880,"res":1,"uname":"13333333333","message":"通过CRM同步, 直接审核","provinceId":1,"regionId":3,"lvId":8,"page":null,"shopType":"洗车店","region":"东城区","status":1}}
     * totalCount : 0
     * success : true
     */

    private int code;
    private String message;
    private DataBean data;
    private int totalCount;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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
         * currentTime : 1533782500884
         * probationTime : {"page":null,"pageSize":null,"startTime":0,"endTime":0}
         * authenticationType : [{"id":1,"type":"洗车店","isHide":0,"sort":3},{"id":2,"type":"美容装饰店（单个）","isHide":0,"sort":8},{"id":3,"type":"快修快保店","isHide":0,"sort":5},{"id":4,"type":"美容装饰店（连锁）","isHide":0,"sort":8},{"id":5,"type":"轮胎店","isHide":0,"sort":3},{"id":6,"type":"换油中心","isHide":0,"sort":4},{"id":7,"type":"改装店","isHide":0,"sort":3},{"id":8,"type":"一类、二类汽修厂","isHide":0,"sort":8},{"id":9,"type":"进口4S店","isHide":0,"sort":4},{"id":10,"type":"三类及以下汽修厂","isHide":0,"sort":8},{"id":11,"type":"合资4S店","isHide":0,"sort":4},{"id":12,"type":"国产4S店","isHide":0,"sort":4}]
         * memberData : {"shopArea":"100-200平","shopNum":100,"city":"东城区","operateTime":1508140656,"pageSize":null,"shopName":"洗车","cityId":2,"prove1":"fs:/images/no_picture.jpg","shopAddress":"北京西城区西城区北环中心","operator":"系统","prove2":"fs:/images/no_picture.jpg","xsyStatus":null,"shopTypeId":1,"province":"北京","memberDataId":13076,"approveTime":1508140656,"class":"com.yasn.shop.model.MemberDataNew","memberId":21880,"res":1,"uname":"13333333333","message":"通过CRM同步, 直接审核","provinceId":1,"regionId":3,"lvId":8,"page":null,"shopType":"洗车店","region":"东城区","status":1}
         */

        private long currentTime;
        private ProbationTimeBean probationTime;
        private MemberDataBean memberData;
        private List<AuthenticationTypeBean> authenticationType;

        public long getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(long currentTime) {
            this.currentTime = currentTime;
        }

        public ProbationTimeBean getProbationTime() {
            return probationTime;
        }

        public void setProbationTime(ProbationTimeBean probationTime) {
            this.probationTime = probationTime;
        }

        public MemberDataBean getMemberData() {
            return memberData;
        }

        public void setMemberData(MemberDataBean memberData) {
            this.memberData = memberData;
        }

        public List<AuthenticationTypeBean> getAuthenticationType() {
            return authenticationType;
        }

        public void setAuthenticationType(List<AuthenticationTypeBean> authenticationType) {
            this.authenticationType = authenticationType;
        }

        public static class ProbationTimeBean {
            /**
             * page : null
             * pageSize : null
             * startTime : 0
             * endTime : 0
             */

            private Object page;
            private Object pageSize;
            private long startTime;
            private long endTime;

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

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }
        }

        public static class MemberDataBean {
            /**
             * shopArea : 100-200平
             * shopNum : 100
             * city : 东城区
             * operateTime : 1508140656
             * pageSize : null
             * shopName : 洗车
             * cityId : 2
             * prove1 : fs:/images/no_picture.jpg
             * shopAddress : 北京西城区西城区北环中心
             * operator : 系统
             * prove2 : fs:/images/no_picture.jpg
             * xsyStatus : null
             * shopTypeId : 1
             * province : 北京
             * memberDataId : 13076
             * approveTime : 1508140656
             * class : com.yasn.shop.model.MemberDataNew
             * memberId : 21880
             * res : 1
             * uname : 13333333333
             * message : 通过CRM同步, 直接审核
             * provinceId : 1
             * regionId : 3
             * lvId : 8
             * page : null
             * shopType : 洗车店
             * region : 东城区
             * status : 1
             */

            private String shopArea;
            private int shopNum;
            private String city;
            private int operateTime;
            private Object pageSize;
            private String shopName;
            private int cityId;
            private String prove1;
            private String shopAddress;
            private String operator;
            private String prove2;
            private Object xsyStatus;
            private int shopTypeId;
            private String province;
            private int memberDataId;
            private int approveTime;
            @SerializedName("class")
            private String classX;
            private int memberId;
            private int res;
            private String uname;
            private String message;
            private int provinceId;
            private int regionId;
            private int lvId;
            private Object page;
            private String shopType;
            private String region;
            private int status;

            public String getShopArea() {
                return shopArea;
            }

            public void setShopArea(String shopArea) {
                this.shopArea = shopArea;
            }

            public int getShopNum() {
                return shopNum;
            }

            public void setShopNum(int shopNum) {
                this.shopNum = shopNum;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getOperateTime() {
                return operateTime;
            }

            public void setOperateTime(int operateTime) {
                this.operateTime = operateTime;
            }

            public Object getPageSize() {
                return pageSize;
            }

            public void setPageSize(Object pageSize) {
                this.pageSize = pageSize;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public String getProve1() {
                return prove1;
            }

            public void setProve1(String prove1) {
                this.prove1 = prove1;
            }

            public String getShopAddress() {
                return shopAddress;
            }

            public void setShopAddress(String shopAddress) {
                this.shopAddress = shopAddress;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getProve2() {
                return prove2;
            }

            public void setProve2(String prove2) {
                this.prove2 = prove2;
            }

            public Object getXsyStatus() {
                return xsyStatus;
            }

            public void setXsyStatus(Object xsyStatus) {
                this.xsyStatus = xsyStatus;
            }

            public int getShopTypeId() {
                return shopTypeId;
            }

            public void setShopTypeId(int shopTypeId) {
                this.shopTypeId = shopTypeId;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public int getMemberDataId() {
                return memberDataId;
            }

            public void setMemberDataId(int memberDataId) {
                this.memberDataId = memberDataId;
            }

            public int getApproveTime() {
                return approveTime;
            }

            public void setApproveTime(int approveTime) {
                this.approveTime = approveTime;
            }

            public String getClassX() {
                return classX;
            }

            public void setClassX(String classX) {
                this.classX = classX;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getRes() {
                return res;
            }

            public void setRes(int res) {
                this.res = res;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
            }

            public int getRegionId() {
                return regionId;
            }

            public void setRegionId(int regionId) {
                this.regionId = regionId;
            }

            public int getLvId() {
                return lvId;
            }

            public void setLvId(int lvId) {
                this.lvId = lvId;
            }

            public Object getPage() {
                return page;
            }

            public void setPage(Object page) {
                this.page = page;
            }

            public String getShopType() {
                return shopType;
            }

            public void setShopType(String shopType) {
                this.shopType = shopType;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class AuthenticationTypeBean {
            /**
             * id : 1
             * type : 洗车店
             * isHide : 0
             * sort : 3
             */

            private int id;
            private String type;
            private int isHide;
            private int sort;
            private boolean checked;

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getIsHide() {
                return isHide;
            }

            public void setIsHide(int isHide) {
                this.isHide = isHide;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }
    }
}
