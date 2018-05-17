package com.yasn.purchasetest.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/2/6.
 */

public class PresonaModel implements Serializable{


    /**
     * member : {"member_id":21880,"store_id":0,"uname":"13333333333","seePrice":0,"endDate":1602844185,"level_id":7,"shopName":"洗车","levelName":"银宝二星","cartCount":2,"memberInProbation":1,"digital_member":1,"is_wholesaler":0,"regionId":0,"lv_id":8,"inWhitelist":0,"preOrder":1,"lvName":"母店","haveMemberData":1,"priceDisplayMsg":null,"probationIsOpen":0,"priceDisplayType":0}
     */

    private MemberBean member;

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
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
         * cartCount : 2
         * memberInProbation : 1
         * digital_member : 1
         * is_wholesaler : 0
         * regionId : 0
         * lv_id : 8
         * inWhitelist : 0
         * preOrder : 1
         * lvName : 母店
         * haveMemberData : 1
         * priceDisplayMsg : null
         * probationIsOpen : 0
         * priceDisplayType : 0
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
        private String priceDisplayMsg;
        private int probationIsOpen;
        private int priceDisplayType;
        private String message;
        private int is_inviteCustomer;

        public int getIs_inviteCustomer() {
            return is_inviteCustomer;
        }

        public void setIs_inviteCustomer(int is_inviteCustomer) {
            this.is_inviteCustomer = is_inviteCustomer;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
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

        public String getPriceDisplayMsg() {
            return priceDisplayMsg;
        }

        public void setPriceDisplayMsg(String priceDisplayMsg) {
            this.priceDisplayMsg = priceDisplayMsg;
        }

        public int getProbationIsOpen() {
            return probationIsOpen;
        }

        public void setProbationIsOpen(int probationIsOpen) {
            this.probationIsOpen = probationIsOpen;
        }

        public int getPriceDisplayType() {
            return priceDisplayType;
        }

        public void setPriceDisplayType(int priceDisplayType) {
            this.priceDisplayType = priceDisplayType;
        }
    }
}
