package com.yasn.purchase.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gs on 2018/1/22.
 */

public class ShopInfoModel implements Serializable {

    /**
     * countInfor : {"0":0,"1":28,"2":18,"3":8,"4":0,"100":0,"5":8,"6":30,"7":4,"200":0,"9":0}
     * member : {"member_id":21880,"store_id":0,"uname":"13333333333","seePrice":0,"endDate":1602844185,"level_id":7,"shopName":"洗车","levelName":"银宝二星","cartCount":0,"memberInProbation":1,"digital_member":1,"is_wholesaler":0,"regionId":0,"lv_id":8,"inWhitelist":0,"preOrder":1,"lvName":"母店","haveMemberData":1}
     * store : {"employeeAuth":"0","shopName":"无","isInvite":0}
     * statistics : {"orderNum":2,"statisticsMonth":"2018-02","totalCount":2687.6,"goodsNum":13}
     */

    private CountInforBean countInfor;
    private MemberBean member;
    private StoreBean store;
    private StatisticsBean statistics;

    public CountInforBean getCountInfor() {
        return countInfor;
    }

    public void setCountInfor(CountInforBean countInfor) {
        this.countInfor = countInfor;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }

    public StatisticsBean getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsBean statistics) {
        this.statistics = statistics;
    }

    public static class CountInforBean {
        /**
         * 0 : 0
         * 1 : 28
         * 2 : 18
         * 3 : 8
         * 4 : 0
         * 100 : 0
         * 5 : 8
         * 6 : 30
         * 7 : 4
         * 200 : 0
         * 9 : 0
         */

        @SerializedName("0")
        private int _$0;
        @SerializedName("1")
        private int _$1;
        @SerializedName("2")
        private int _$2;
        @SerializedName("3")
        private int _$3;
        @SerializedName("4")
        private int _$4;
        @SerializedName("100")
        private int _$100;
        @SerializedName("5")
        private int _$5;
        @SerializedName("6")
        private int _$6;
        @SerializedName("7")
        private int _$7;
        @SerializedName("200")
        private int _$200;
        @SerializedName("9")
        private int _$9;

        public int get_$0() {
            return _$0;
        }

        public void set_$0(int _$0) {
            this._$0 = _$0;
        }

        public int get_$1() {
            return _$1;
        }

        public void set_$1(int _$1) {
            this._$1 = _$1;
        }

        public int get_$2() {
            return _$2;
        }

        public void set_$2(int _$2) {
            this._$2 = _$2;
        }

        public int get_$3() {
            return _$3;
        }

        public void set_$3(int _$3) {
            this._$3 = _$3;
        }

        public int get_$4() {
            return _$4;
        }

        public void set_$4(int _$4) {
            this._$4 = _$4;
        }

        public int get_$100() {
            return _$100;
        }

        public void set_$100(int _$100) {
            this._$100 = _$100;
        }

        public int get_$5() {
            return _$5;
        }

        public void set_$5(int _$5) {
            this._$5 = _$5;
        }

        public int get_$6() {
            return _$6;
        }

        public void set_$6(int _$6) {
            this._$6 = _$6;
        }

        public int get_$7() {
            return _$7;
        }

        public void set_$7(int _$7) {
            this._$7 = _$7;
        }

        public int get_$200() {
            return _$200;
        }

        public void set_$200(int _$200) {
            this._$200 = _$200;
        }

        public int get_$9() {
            return _$9;
        }

        public void set_$9(int _$9) {
            this._$9 = _$9;
        }
    }

    public static class MemberBean {
        /**
         * member_id : 21880
         * store_id : 0
         * uname : 13333333333
         * seePrice : 0
         * endDate : 1602844185
         * level_id : 7
         * shopName : 洗车
         * levelName : 银宝二星
         * cartCount : 0
         * memberInProbation : 1
         * digital_member : 1
         * is_wholesaler : 0
         * regionId : 0
         * lv_id : 8
         * inWhitelist : 0
         * preOrder : 1
         * lvName : 母店
         * haveMemberData : 1
         */

        private int member_id;
        private int store_id;
        private String uname;
        private int seePrice;
        private int endDate;
        private int level_id;
        private String shopName;
        private String levelName;
        private int cartCount;
        private int memberInProbation;
        private int digital_member;
        private int is_wholesaler;
        private int regionId;
        private int lv_id;
        private int inWhitelist;
        private int preOrder;
        private String lvName;
        private int haveMemberData;
        private int is_inviteCustomer;

        public int getIs_inviteCustomer() {
            return is_inviteCustomer;
        }

        public void setIs_inviteCustomer(int is_inviteCustomer) {
            this.is_inviteCustomer = is_inviteCustomer;
        }

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public int getSeePrice() {
            return seePrice;
        }

        public void setSeePrice(int seePrice) {
            this.seePrice = seePrice;
        }

        public int getEndDate() {
            return endDate;
        }

        public void setEndDate(int endDate) {
            this.endDate = endDate;
        }

        public int getLevel_id() {
            return level_id;
        }

        public void setLevel_id(int level_id) {
            this.level_id = level_id;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getCartCount() {
            return cartCount;
        }

        public void setCartCount(int cartCount) {
            this.cartCount = cartCount;
        }

        public int getMemberInProbation() {
            return memberInProbation;
        }

        public void setMemberInProbation(int memberInProbation) {
            this.memberInProbation = memberInProbation;
        }

        public int getDigital_member() {
            return digital_member;
        }

        public void setDigital_member(int digital_member) {
            this.digital_member = digital_member;
        }

        public int getIs_wholesaler() {
            return is_wholesaler;
        }

        public void setIs_wholesaler(int is_wholesaler) {
            this.is_wholesaler = is_wholesaler;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public int getLv_id() {
            return lv_id;
        }

        public void setLv_id(int lv_id) {
            this.lv_id = lv_id;
        }

        public int getInWhitelist() {
            return inWhitelist;
        }

        public void setInWhitelist(int inWhitelist) {
            this.inWhitelist = inWhitelist;
        }

        public int getPreOrder() {
            return preOrder;
        }

        public void setPreOrder(int preOrder) {
            this.preOrder = preOrder;
        }

        public String getLvName() {
            return lvName;
        }

        public void setLvName(String lvName) {
            this.lvName = lvName;
        }

        public int getHaveMemberData() {
            return haveMemberData;
        }

        public void setHaveMemberData(int haveMemberData) {
            this.haveMemberData = haveMemberData;
        }
    }

    public static class StoreBean {
        /**
         * employeeAuth : 0
         * shopName : 无
         * isInvite : 0
         * admin;
         */

        private String employeeAuth;
        private String shopName;
        private int isInvite;
        private String admin;

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public String getEmployeeAuth() {
            return employeeAuth;
        }

        public void setEmployeeAuth(String employeeAuth) {
            this.employeeAuth = employeeAuth;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getIsInvite() {
            return isInvite;
        }

        public void setIsInvite(int isInvite) {
            this.isInvite = isInvite;
        }
    }

    public static class StatisticsBean {
        /**
         * orderNum : 2
         * statisticsMonth : 2018-02
         * totalCount : 2687.6
         * goodsNum : 13
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
