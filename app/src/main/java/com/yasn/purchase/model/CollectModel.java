package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/7/16.
 */

public class CollectModel implements Serializable{

    /**
     * listFavorite : [{"small":"http://shoptt.yasn.com/static/attachment//store/159/goods/2017/8/30/10//49539078_small.png","store_id":159,"discount_price":"5.00","have_voice":0,"goods_id":2708,"advert":"123124124124","is_before_sale":0,"maxcountperuser":1,"totalBuyCount":6,"price":5,"name":"测试限购","has_discount":0,"is_limit_buy":0,"is_success_case":0},{"small":"http://shoptt.yasn.com/static/attachment//store/99/goods/2017/2/18/15//35461660_small.jpg","store_id":99,"discount_price":"168.00","have_voice":0,"goods_id":1356,"advert":"15天包换，13个月保修","is_before_sale":0,"maxcountperuser":0,"totalBuyCount":229,"price":168,"name":"凌度前后双录高清后视镜行车记录仪A66C","has_discount":0,"is_limit_buy":0,"is_success_case":0},{"small":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/5/17/18//15379702_small.jpg","store_id":1,"discount_price":"1.00","have_voice":0,"goods_id":3724,"advert":"2个规格测试","is_before_sale":0,"maxcountperuser":0,"totalBuyCount":46,"price":1,"success_case":"<p>成功案例成功案例11111111<br/><\/p>","name":"测试","has_discount":0,"is_limit_buy":0,"is_success_case":1}]
     * priceData : {"priceDisplayMsg":null,"priceDisplayType":0,"probationIsOpen":1}
     * member : {"seePrice":0,"level_id":2,"regionName":null,"employee_auth":"0","shopName":"长伟","is_inviteStatus":0,"is_wholesaler":0,"lv_id":6,"inWhitelist":0,"memberInProbationStartTime":0,"preOrder":0,"lvName":"单个门店","haveMemberData":1,"member_id":24640,"store_id":0,"uname":"13466663750","is_invite":0,"mobile":"13466663750","is_inviteCustomer":2,"levelName":"普通会员","cartCount":8,"memberInProbation":0,"provinceId":360,"digital_member":0,"currentTime":1531713157,"regionId":0,"memberInProbationEndTime":0,"member_admin":24640}
     */

    private PriceDataBean priceData;
    private MemberBean member;
    private List<ListFavoriteBean> listFavorite;

    public PriceDataBean getPriceData() {
        return priceData;
    }

    public void setPriceData(PriceDataBean priceData) {
        this.priceData = priceData;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public List<ListFavoriteBean> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(List<ListFavoriteBean> listFavorite) {
        this.listFavorite = listFavorite;
    }

    public static class PriceDataBean {
        /**
         * priceDisplayMsg : null
         * priceDisplayType : 0
         * probationIsOpen : 1
         */

        private Object priceDisplayMsg;
        private int priceDisplayType;
        private int probationIsOpen;

        public Object getPriceDisplayMsg() {
            return priceDisplayMsg;
        }

        public void setPriceDisplayMsg(Object priceDisplayMsg) {
            this.priceDisplayMsg = priceDisplayMsg;
        }

        public int getPriceDisplayType() {
            return priceDisplayType;
        }

        public void setPriceDisplayType(int priceDisplayType) {
            this.priceDisplayType = priceDisplayType;
        }

        public int getProbationIsOpen() {
            return probationIsOpen;
        }

        public void setProbationIsOpen(int probationIsOpen) {
            this.probationIsOpen = probationIsOpen;
        }
    }

    public static class MemberBean {
        /**
         * seePrice : 0
         * level_id : 2
         * regionName : null
         * employee_auth : 0
         * shopName : 长伟
         * is_inviteStatus : 0
         * is_wholesaler : 0
         * lv_id : 6
         * inWhitelist : 0
         * memberInProbationStartTime : 0
         * preOrder : 0
         * lvName : 单个门店
         * haveMemberData : 1
         * member_id : 24640
         * store_id : 0
         * uname : 13466663750
         * is_invite : 0
         * mobile : 13466663750
         * is_inviteCustomer : 2
         * levelName : 普通会员
         * cartCount : 8
         * memberInProbation : 0
         * provinceId : 360
         * digital_member : 0
         * currentTime : 1531713157
         * regionId : 0
         * memberInProbationEndTime : 0
         * member_admin : 24640
         */

        private double seePrice;
        private int level_id;
        private Object regionName;
        private String employee_auth;
        private String shopName;
        private int is_inviteStatus;
        private int is_wholesaler;
        private int lv_id;
        private int inWhitelist;
        private int memberInProbationStartTime;
        private int preOrder;
        private String lvName;
        private int haveMemberData;
        private int member_id;
        private int store_id;
        private String uname;
        private int is_invite;
        private String mobile;
        private int is_inviteCustomer;
        private String levelName;
        private int cartCount;
        private int memberInProbation;
        private int provinceId;
        private int digital_member;
        private int currentTime;
        private int regionId;
        private int memberInProbationEndTime;
        private int member_admin;

        public double getSeePrice() {
            return seePrice;
        }

        public void setSeePrice(double seePrice) {
            this.seePrice = seePrice;
        }

        public int getLevel_id() {
            return level_id;
        }

        public void setLevel_id(int level_id) {
            this.level_id = level_id;
        }

        public Object getRegionName() {
            return regionName;
        }

        public void setRegionName(Object regionName) {
            this.regionName = regionName;
        }

        public String getEmployee_auth() {
            return employee_auth;
        }

        public void setEmployee_auth(String employee_auth) {
            this.employee_auth = employee_auth;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getIs_inviteStatus() {
            return is_inviteStatus;
        }

        public void setIs_inviteStatus(int is_inviteStatus) {
            this.is_inviteStatus = is_inviteStatus;
        }

        public int getIs_wholesaler() {
            return is_wholesaler;
        }

        public void setIs_wholesaler(int is_wholesaler) {
            this.is_wholesaler = is_wholesaler;
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

        public int getMemberInProbationStartTime() {
            return memberInProbationStartTime;
        }

        public void setMemberInProbationStartTime(int memberInProbationStartTime) {
            this.memberInProbationStartTime = memberInProbationStartTime;
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

        public int getIs_invite() {
            return is_invite;
        }

        public void setIs_invite(int is_invite) {
            this.is_invite = is_invite;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIs_inviteCustomer() {
            return is_inviteCustomer;
        }

        public void setIs_inviteCustomer(int is_inviteCustomer) {
            this.is_inviteCustomer = is_inviteCustomer;
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

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public int getDigital_member() {
            return digital_member;
        }

        public void setDigital_member(int digital_member) {
            this.digital_member = digital_member;
        }

        public int getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(int currentTime) {
            this.currentTime = currentTime;
        }

        public int getRegionId() {
            return regionId;
        }

        public void setRegionId(int regionId) {
            this.regionId = regionId;
        }

        public int getMemberInProbationEndTime() {
            return memberInProbationEndTime;
        }

        public void setMemberInProbationEndTime(int memberInProbationEndTime) {
            this.memberInProbationEndTime = memberInProbationEndTime;
        }

        public int getMember_admin() {
            return member_admin;
        }

        public void setMember_admin(int member_admin) {
            this.member_admin = member_admin;
        }
    }

    public static class ListFavoriteBean {
        /**
         * small : http://shoptt.yasn.com/static/attachment//store/159/goods/2017/8/30/10//49539078_small.png
         * store_id : 159
         * discount_price : 5.00
         * have_voice : 0
         * goods_id : 2708
         * advert : 123124124124
         * is_before_sale : 0
         * maxcountperuser : 1
         * totalBuyCount : 6
         * price : 5
         * name : 测试限购
         * has_discount : 0
         * is_limit_buy : 0
         * is_success_case : 0
         * success_case : <p>成功案例成功案例11111111<br/></p>
         */

        private String small;
        private int store_id;
        private String discount_price;
        private int have_voice;
        private int goods_id;
        private String advert;
        private int is_before_sale;
        private int maxcountperuser;
        private int totalBuyCount;
        private double price;
        private String name;
        private int has_discount;
        private int is_limit_buy;
        private int is_success_case;
        private String success_case;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(String discount_price) {
            this.discount_price = discount_price;
        }

        public int getHave_voice() {
            return have_voice;
        }

        public void setHave_voice(int have_voice) {
            this.have_voice = have_voice;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getAdvert() {
            return advert;
        }

        public void setAdvert(String advert) {
            this.advert = advert;
        }

        public int getIs_before_sale() {
            return is_before_sale;
        }

        public void setIs_before_sale(int is_before_sale) {
            this.is_before_sale = is_before_sale;
        }

        public int getMaxcountperuser() {
            return maxcountperuser;
        }

        public void setMaxcountperuser(int maxcountperuser) {
            this.maxcountperuser = maxcountperuser;
        }

        public int getTotalBuyCount() {
            return totalBuyCount;
        }

        public void setTotalBuyCount(int totalBuyCount) {
            this.totalBuyCount = totalBuyCount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHas_discount() {
            return has_discount;
        }

        public void setHas_discount(int has_discount) {
            this.has_discount = has_discount;
        }

        public int getIs_limit_buy() {
            return is_limit_buy;
        }

        public void setIs_limit_buy(int is_limit_buy) {
            this.is_limit_buy = is_limit_buy;
        }

        public int getIs_success_case() {
            return is_success_case;
        }

        public void setIs_success_case(int is_success_case) {
            this.is_success_case = is_success_case;
        }

        public String getSuccess_case() {
            return success_case;
        }

        public void setSuccess_case(String success_case) {
            this.success_case = success_case;
        }
    }
}
