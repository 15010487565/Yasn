package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

public class GoodsDetailsModel implements Serializable {

    /**
     * goodsDetails : {"page":null,"pageSize":null,"goodsId":2716,"name":"测试套装1","marketEnable":1,"brief":null,"price":14,"cost":0,"mktPrice":123,"haveSpec":1,"totalViewCount":175,"totalBuyCount":52,"disabled":0,"enableStore":108,"pageTitle":null,"metaKeywords":null,"metaDescription":null,"storeId":1,"goodsTransfeeCharge":1,"storeName":"雅森自营","isDisplay":1,"advert":"","subTitle":"","goodsPoint":0,"maxCountPerUser":2,"pointUsable":1,"publishType":2,"totalPlayCount":8,"isGift":0,"videoDetailUrl":"http://video.yasn.com/details/2222.mp4","isSuccessCase":0,"successCase":null,"haveVoice":0,"voiceDetailUrl":"http://video.yasn.com/details/3333.mp3","point":5,"brandName":"保赐利","typeId":87,"unit":"","isBeforeSale":0,"goodsBeforeSale":{"id":null,"goodsId":null,"saleStartTime":1515557413000,"saleEndTime":1516162216000,"saleSendTime":1516853418000,"saleCreateTime":null},"isLimitBuy":1,"goodsLimitBuy":{"id":null,"goodsId":null,"num":2,"startTime":1515471003000,"endTime":1516853406000,"remainingTime":596460149,"createTime":null},"specs":[{"specId":26,"specName":"包装规格","specValues":[{"specValueId":1291,"specValueName":"5只装"}]},{"specId":3,"specName":"容量","specValues":[{"specValueId":1266,"specValueName":"40L"}]}],"carTypes":[{"id":7867,"goodsId":2716,"carName":"奥迪200","carDepthFirst":1,"carDepthSecond":219,"carDepthThird":227,"carDepthFourth":null}],"goodsActity":{"page":null,"pageSize":null,"activityId":261,"activityName":"买5送1","startTime":1505273828,"endTime":1517384944,"description":"<p>111<br/><\/p>","rangeType":null,"disabled":0,"type":1,"storeId":1},"storeActivity":{"page":null,"pageSize":null,"activityId":268,"activityName":"包邮活动测试","startTime":1515470841,"endTime":1517371646,"description":"<p>包邮!!!!!!!<\/p>","rangeType":1,"disabled":0,"type":0,"storeId":1},"suit":[{"id":78,"goodsId":77,"productId":1104,"depotId":8,"name":"3M 汽车漆面美容水晶蜡 PN39026 473ml/瓶","price":60,"specs":"473ml","speclist":["473ml"],"suitNum":1,"saleNum":79,"url":"shoptt.yasn.com/static/attachment//store/1/goods/2017/6/6/11//37100986_thumbnail.jpeg"},{"id":79,"goodsId":77,"productId":1104,"depotId":9,"name":"3M 汽车漆面美容水晶蜡 PN39026 473ml/瓶","price":60,"specs":"473ml","speclist":["473ml"],"suitNum":1,"saleNum":29,"url":"shoptt.yasn.com/static/attachment//store/1/goods/2017/6/6/11//37100986_thumbnail.jpeg"}],"products":[{"productId":4980,"goodsId":2716,"name":"测试套装1","sn":"123123-0","store":105,"enableStore":108,"price":14,"cost":null,"weight":null,"specValueIds":[1291,1266],"step":1,"smallSale":1,"goodsLvPrices":null,"ladderPrices":[{"id":89931,"lvId":1,"productId":4980,"goodsId":2716,"minNum":1,"maxNum":2000000000,"wholesalePrice":8,"activityPrice":"5.60"}],"copiedProductId":null,"minReferencePrice":null,"maxReferencePrice":null}],"goodsGallerys":[{"imgId":0,"goodsId":0,"thumbnail":null,"small":null,"big":"shoptt.yasn.com/static/attachment//store/1/goods/2017/8/30/19//13531655_big.png","original":null,"tiny":null,"isdefault":0,"sort":0},{"imgId":0,"goodsId":0,"thumbnail":null,"small":null,"big":"shoptt.yasn.com/static/attachment//store/1/goods/2017/8/30/16//36196213_big.png","original":null,"tiny":null,"isdefault":0,"sort":0},{"imgId":0,"goodsId":0,"thumbnail":null,"small":null,"big":"shoptt.yasn.com/static/attachment//store/1/goods/2017/8/30/16//36122877_big.png","original":null,"tiny":null,"isdefault":0,"sort":0}],"attributes":[{"attrValue":"保赐利","attrName":"品牌"}],"discount":{"discount_price":"9.80","has_discount":1}}
     * member : null
     * cartNum : 0
     */

    private GoodsDetailsBean goodsDetails;
    private MemberBean member;
    private int isFavorite;

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public GoodsDetailsBean getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(GoodsDetailsBean goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public static class MemberBean {

        /**
         * seePrice : 0
         * level_id : 2
         * shopName : 重庆市长寿区易洁洗车服务中心
         * is_wholesaler : 0
         * inviteCustomer_code : C1019
         * lv_id : 6
         * inWhitelist : 0
         * memberInProbationStartTime : 0
         * preOrder : 0
         * lvName : 单个门店
         * haveMemberData : 1
         * member_id : 28
         * store_id : 171
         * uname : 123456
         * is_invite : 0
         * pointDate : null
         * is_inviteCustomer : 1
         * levelName : 普通会员
         * cartCount : 6
         * memberInProbation : 0
         * message : null
         * digital_member : 0
         * currentTime : 1520568900
         * regionId : 0
         * memberInProbationEndTime : 0
         * member_admin : 28
         * priceDisplayMsg : null
         * probationIsOpen : 0
         * priceDisplayType : 0
         */

        private double seePrice;
        private int level_id;
        private String shopName;
        private int is_wholesaler;
        private String inviteCustomer_code;
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
        private Object pointDate;
        private int is_inviteCustomer;
        private String levelName;
        private int cartCount;
        private int memberInProbation;
        private Object message;
        private int digital_member;
        private int currentTime;
        private int regionId;
        private int memberInProbationEndTime;
        private int member_admin;
        private Object priceDisplayMsg;
        private int probationIsOpen;
        private int priceDisplayType;

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

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getIs_wholesaler() {
            return is_wholesaler;
        }

        public void setIs_wholesaler(int is_wholesaler) {
            this.is_wholesaler = is_wholesaler;
        }

        public String getInviteCustomer_code() {
            return inviteCustomer_code;
        }

        public void setInviteCustomer_code(String inviteCustomer_code) {
            this.inviteCustomer_code = inviteCustomer_code;
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

        public Object getPointDate() {
            return pointDate;
        }

        public void setPointDate(Object pointDate) {
            this.pointDate = pointDate;
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

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
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

        public Object getPriceDisplayMsg() {
            return priceDisplayMsg;
        }

        public void setPriceDisplayMsg(Object priceDisplayMsg) {
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

    public static class GoodsDetailsBean {
        /**
         * page : null
         * pageSize : null
         * goodsId : 2716
         * name : 测试套装1
         * marketEnable : 1
         * brief : null
         * price : 14.0
         * cost : 0.0
         * mktPrice : 123.0
         * haveSpec : 1
         * totalViewCount : 175
         * totalBuyCount : 52
         * disabled : 0
         * enableStore : 108
         * pageTitle : null
         * metaKeywords : null
         * metaDescription : null
         * storeId : 1
         * goodsTransfeeCharge : 1
         * storeName : 雅森自营
         * isDisplay : 1
         * advert :
         * subTitle :
         * goodsPoint : 0
         * maxCountPerUser : 2
         * pointUsable : 1
         * publishType : 2
         * totalPlayCount : 8
         * isGift : 0
         * videoDetailUrl : http://video.yasn.com/details/2222.mp4
         * isSuccessCase : 0
         * successCase : null
         * haveVoice : 0
         * voiceDetailUrl : http://video.yasn.com/details/3333.mp3
         * point : 5
         * brandName : 保赐利
         * typeId : 87
         * unit :
         * isBeforeSale : 0
         * goodsBeforeSale : {"id":null,"goodsId":null,"saleStartTime":1515557413000,"saleEndTime":1516162216000,"saleSendTime":1516853418000,"saleCreateTime":null}
         * isLimitBuy : 1
         * goodsLimitBuy : {"id":null,"goodsId":null,"num":2,"startTime":1515471003000,"endTime":1516853406000,"remainingTime":596460149,"createTime":null}
         * specs : [{"specId":26,"specName":"包装规格","specValues":[{"specValueId":1291,"specValueName":"5只装"}]},{"specId":3,"specName":"容量","specValues":[{"specValueId":1266,"specValueName":"40L"}]}]
         * carTypes : [{"id":7867,"goodsId":2716,"carName":"奥迪200","carDepthFirst":1,"carDepthSecond":219,"carDepthThird":227,"carDepthFourth":null}]
         * goodsActity : {"page":null,"pageSize":null,"activityId":261,"activityName":"买5送1","startTime":1505273828,"endTime":1517384944,"description":"<p>111<br/><\/p>","rangeType":null,"disabled":0,"type":1,"storeId":1}
         * storeActivity : {"page":null,"pageSize":null,"activityId":268,"activityName":"包邮活动测试","startTime":1515470841,"endTime":1517371646,"description":"<p>包邮!!!!!!!<\/p>","rangeType":1,"disabled":0,"type":0,"storeId":1}
         * suit : [{"id":78,"goodsId":77,"productId":1104,"depotId":8,"name":"3M 汽车漆面美容水晶蜡 PN39026 473ml/瓶","price":60,"specs":"473ml","speclist":["473ml"],"suitNum":1,"saleNum":79,"url":"shoptt.yasn.com/static/attachment//store/1/goods/2017/6/6/11//37100986_thumbnail.jpeg"},{"id":79,"goodsId":77,"productId":1104,"depotId":9,"name":"3M 汽车漆面美容水晶蜡 PN39026 473ml/瓶","price":60,"specs":"473ml","speclist":["473ml"],"suitNum":1,"saleNum":29,"url":"shoptt.yasn.com/static/attachment//store/1/goods/2017/6/6/11//37100986_thumbnail.jpeg"}]
         * products : [{"productId":4980,"goodsId":2716,"name":"测试套装1","sn":"123123-0","store":105,"enableStore":108,"price":14,"cost":null,"weight":null,"specValueIds":[1291,1266],"step":1,"smallSale":1,"goodsLvPrices":null,"ladderPrices":[{"id":89931,"lvId":1,"productId":4980,"goodsId":2716,"minNum":1,"maxNum":2000000000,"wholesalePrice":8,"activityPrice":"5.60"}],"copiedProductId":null,"minReferencePrice":null,"maxReferencePrice":null}]
         * goodsGallerys : [{"imgId":0,"goodsId":0,"thumbnail":null,"small":null,"big":"shoptt.yasn.com/static/attachment//store/1/goods/2017/8/30/19//13531655_big.png","original":null,"tiny":null,"isdefault":0,"sort":0},{"imgId":0,"goodsId":0,"thumbnail":null,"small":null,"big":"shoptt.yasn.com/static/attachment//store/1/goods/2017/8/30/16//36196213_big.png","original":null,"tiny":null,"isdefault":0,"sort":0},{"imgId":0,"goodsId":0,"thumbnail":null,"small":null,"big":"shoptt.yasn.com/static/attachment//store/1/goods/2017/8/30/16//36122877_big.png","original":null,"tiny":null,"isdefault":0,"sort":0}]
         * attributes : [{"attrValue":"保赐利","attrName":"品牌"}]
         * discount : {"discount_price":"9.80","has_discount":1}
         */

        private Object page;
        private Object pageSize;
        private int goodsId;
        private String name;
        private int marketEnable;
        private Object brief;
        private double price;
        private double cost;
        private double mktPrice;
        private int haveSpec;
        private int totalViewCount;
        private int totalBuyCount;
        private int disabled;
        private int enableStore;
        private Object pageTitle;
        private Object metaKeywords;
        private Object metaDescription;
        private int storeId;
        private int goodsTransfeeCharge;
        private String storeName;
        private int isDisplay;
        private String advert;
        private String subTitle;
        private int goodsPoint;
        private int maxCountPerUser;
        private int pointUsable;
        private int publishType;
        private int totalPlayCount;
        private int isGift;
        private String videoDetailUrl;
        private int isSuccessCase;
        private Object successCase;
        private int haveVoice;
        private String voiceDetailUrl;
        private int point;
        private String brandName;
        private int typeId;
        private String unit;
        private int isBeforeSale;
        private GoodsBeforeSaleBean goodsBeforeSale;
        private int isLimitBuy;
        private GoodsLimitBuyBean goodsLimitBuy;
        private GoodsActityBean goodsActity;
        private StoreActivityBean storeActivity;
        private DiscountBean discount;
        private List<SpecsBean> specs;
        private List<CarTypesBean> carTypes;
        private List<SuitBean> suit;
        private List<ProductsBean> products;
        private List<GoodsGallerysBean> goodsGallerys;
        private List<AttributesBean> attributes;

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

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMarketEnable() {
            return marketEnable;
        }

        public void setMarketEnable(int marketEnable) {
            this.marketEnable = marketEnable;
        }

        public Object getBrief() {
            return brief;
        }

        public void setBrief(Object brief) {
            this.brief = brief;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getMktPrice() {
            return mktPrice;
        }

        public void setMktPrice(double mktPrice) {
            this.mktPrice = mktPrice;
        }

        public int getHaveSpec() {
            return haveSpec;
        }

        public void setHaveSpec(int haveSpec) {
            this.haveSpec = haveSpec;
        }

        public int getTotalViewCount() {
            return totalViewCount;
        }

        public void setTotalViewCount(int totalViewCount) {
            this.totalViewCount = totalViewCount;
        }

        public int getTotalBuyCount() {
            return totalBuyCount;
        }

        public void setTotalBuyCount(int totalBuyCount) {
            this.totalBuyCount = totalBuyCount;
        }

        public int getDisabled() {
            return disabled;
        }

        public void setDisabled(int disabled) {
            this.disabled = disabled;
        }

        public int getEnableStore() {
            return enableStore;
        }

        public void setEnableStore(int enableStore) {
            this.enableStore = enableStore;
        }

        public Object getPageTitle() {
            return pageTitle;
        }

        public void setPageTitle(Object pageTitle) {
            this.pageTitle = pageTitle;
        }

        public Object getMetaKeywords() {
            return metaKeywords;
        }

        public void setMetaKeywords(Object metaKeywords) {
            this.metaKeywords = metaKeywords;
        }

        public Object getMetaDescription() {
            return metaDescription;
        }

        public void setMetaDescription(Object metaDescription) {
            this.metaDescription = metaDescription;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public int getGoodsTransfeeCharge() {
            return goodsTransfeeCharge;
        }

        public void setGoodsTransfeeCharge(int goodsTransfeeCharge) {
            this.goodsTransfeeCharge = goodsTransfeeCharge;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public int getIsDisplay() {
            return isDisplay;
        }

        public void setIsDisplay(int isDisplay) {
            this.isDisplay = isDisplay;
        }

        public String getAdvert() {
            return advert;
        }

        public void setAdvert(String advert) {
            this.advert = advert;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getGoodsPoint() {
            return goodsPoint;
        }

        public void setGoodsPoint(int goodsPoint) {
            this.goodsPoint = goodsPoint;
        }

        public int getMaxCountPerUser() {
            return maxCountPerUser;
        }

        public void setMaxCountPerUser(int maxCountPerUser) {
            this.maxCountPerUser = maxCountPerUser;
        }

        public int getPointUsable() {
            return pointUsable;
        }

        public void setPointUsable(int pointUsable) {
            this.pointUsable = pointUsable;
        }

        public int getPublishType() {
            return publishType;
        }

        public void setPublishType(int publishType) {
            this.publishType = publishType;
        }

        public int getTotalPlayCount() {
            return totalPlayCount;
        }

        public void setTotalPlayCount(int totalPlayCount) {
            this.totalPlayCount = totalPlayCount;
        }

        public int getIsGift() {
            return isGift;
        }

        public void setIsGift(int isGift) {
            this.isGift = isGift;
        }

        public String getVideoDetailUrl() {
            return videoDetailUrl;
        }

        public void setVideoDetailUrl(String videoDetailUrl) {
            this.videoDetailUrl = videoDetailUrl;
        }

        public int getIsSuccessCase() {
            return isSuccessCase;
        }

        public void setIsSuccessCase(int isSuccessCase) {
            this.isSuccessCase = isSuccessCase;
        }

        public Object getSuccessCase() {
            return successCase;
        }

        public void setSuccessCase(Object successCase) {
            this.successCase = successCase;
        }

        public int getHaveVoice() {
            return haveVoice;
        }

        public void setHaveVoice(int haveVoice) {
            this.haveVoice = haveVoice;
        }

        public String getVoiceDetailUrl() {
            return voiceDetailUrl;
        }

        public void setVoiceDetailUrl(String voiceDetailUrl) {
            this.voiceDetailUrl = voiceDetailUrl;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getIsBeforeSale() {
            return isBeforeSale;
        }

        public void setIsBeforeSale(int isBeforeSale) {
            this.isBeforeSale = isBeforeSale;
        }

        public GoodsBeforeSaleBean getGoodsBeforeSale() {
            return goodsBeforeSale;
        }

        public void setGoodsBeforeSale(GoodsBeforeSaleBean goodsBeforeSale) {
            this.goodsBeforeSale = goodsBeforeSale;
        }

        public int getIsLimitBuy() {
            return isLimitBuy;
        }

        public void setIsLimitBuy(int isLimitBuy) {
            this.isLimitBuy = isLimitBuy;
        }

        public GoodsLimitBuyBean getGoodsLimitBuy() {
            return goodsLimitBuy;
        }

        public void setGoodsLimitBuy(GoodsLimitBuyBean goodsLimitBuy) {
            this.goodsLimitBuy = goodsLimitBuy;
        }

        public GoodsActityBean getGoodsActity() {
            return goodsActity;
        }

        public void setGoodsActity(GoodsActityBean goodsActity) {
            this.goodsActity = goodsActity;
        }

        public StoreActivityBean getStoreActivity() {
            return storeActivity;
        }

        public void setStoreActivity(StoreActivityBean storeActivity) {
            this.storeActivity = storeActivity;
        }

        public DiscountBean getDiscount() {
            return discount;
        }

        public void setDiscount(DiscountBean discount) {
            this.discount = discount;
        }

        public List<SpecsBean> getSpecs() {
            return specs;
        }

        public void setSpecs(List<SpecsBean> specs) {
            this.specs = specs;
        }

        public List<CarTypesBean> getCarTypes() {
            return carTypes;
        }

        public void setCarTypes(List<CarTypesBean> carTypes) {
            this.carTypes = carTypes;
        }

        public List<SuitBean> getSuit() {
            return suit;
        }

        public void setSuit(List<SuitBean> suit) {
            this.suit = suit;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public List<GoodsGallerysBean> getGoodsGallerys() {
            return goodsGallerys;
        }

        public void setGoodsGallerys(List<GoodsGallerysBean> goodsGallerys) {
            this.goodsGallerys = goodsGallerys;
        }

        public List<AttributesBean> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<AttributesBean> attributes) {
            this.attributes = attributes;
        }

        public static class GoodsBeforeSaleBean {
            /**
             * id : null
             * goodsId : null
             * saleStartTime : 1515557413000
             * saleEndTime : 1516162216000
             * saleSendTime : 1516853418000
             * saleCreateTime : null
             */

            private Object id;
            private Object goodsId;
            private long saleStartTime;
            private long saleEndTime;
            private long saleSendTime;
            private Object saleCreateTime;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(Object goodsId) {
                this.goodsId = goodsId;
            }

            public long getSaleStartTime() {
                return saleStartTime;
            }

            public void setSaleStartTime(long saleStartTime) {
                this.saleStartTime = saleStartTime;
            }

            public long getSaleEndTime() {
                return saleEndTime;
            }

            public void setSaleEndTime(long saleEndTime) {
                this.saleEndTime = saleEndTime;
            }

            public long getSaleSendTime() {
                return saleSendTime;
            }

            public void setSaleSendTime(long saleSendTime) {
                this.saleSendTime = saleSendTime;
            }

            public Object getSaleCreateTime() {
                return saleCreateTime;
            }

            public void setSaleCreateTime(Object saleCreateTime) {
                this.saleCreateTime = saleCreateTime;
            }
        }

        public static class GoodsLimitBuyBean {
            /**
             * id : null
             * goodsId : null
             * num : 2
             * startTime : 1515471003000
             * endTime : 1516853406000
             * remainingTime : 596460149
             * createTime : null
             */

            private Object id;
            private Object goodsId;
            private int num;
            private long startTime;
            private long endTime;
            private int remainingTime;
            private Object createTime;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(Object goodsId) {
                this.goodsId = goodsId;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
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

            public int getRemainingTime() {
                return remainingTime;
            }

            public void setRemainingTime(int remainingTime) {
                this.remainingTime = remainingTime;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }
        }

        public static class GoodsActityBean {
            /**
             * page : null
             * pageSize : null
             * activityId : 261
             * activityName : 买5送1
             * startTime : 1505273828
             * endTime : 1517384944
             * description : <p>111<br/></p>
             * rangeType : null
             * disabled : 0
             * type : 1
             * storeId : 1
             */

            private Object page;
            private Object pageSize;
            private int activityId;
            private String activityName;
            private int startTime;
            private int endTime;
            private String description;
            private Object rangeType;
            private int disabled;
            private int type;
            private int storeId;

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

            public int getActivityId() {
                return activityId;
            }

            public void setActivityId(int activityId) {
                this.activityId = activityId;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getRangeType() {
                return rangeType;
            }

            public void setRangeType(Object rangeType) {
                this.rangeType = rangeType;
            }

            public int getDisabled() {
                return disabled;
            }

            public void setDisabled(int disabled) {
                this.disabled = disabled;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }
        }

        public static class StoreActivityBean {
            /**
             * page : null
             * pageSize : null
             * activityId : 268
             * activityName : 包邮活动测试
             * startTime : 1515470841
             * endTime : 1517371646
             * description : <p>包邮!!!!!!!</p>
             * rangeType : 1
             * disabled : 0
             * type : 0
             * storeId : 1
             */

            private Object page;
            private Object pageSize;
            private int activityId;
            private String activityName;
            private int startTime;
            private int endTime;
            private String description;
            private int rangeType;
            private int disabled;
            private int type;
            private int storeId;

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

            public int getActivityId() {
                return activityId;
            }

            public void setActivityId(int activityId) {
                this.activityId = activityId;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public int getStartTime() {
                return startTime;
            }

            public void setStartTime(int startTime) {
                this.startTime = startTime;
            }

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getRangeType() {
                return rangeType;
            }

            public void setRangeType(int rangeType) {
                this.rangeType = rangeType;
            }

            public int getDisabled() {
                return disabled;
            }

            public void setDisabled(int disabled) {
                this.disabled = disabled;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }
        }

        public static class DiscountBean {
            /**
             * discount_price : 9.80
             * has_discount : 1
             * remainingTime
             */

            private String discount_price;
            private int has_discount;
            private long remainingTime;

            public long getRemainingTime() {
                return remainingTime;
            }

            public void setRemainingTime(long remainingTime) {
                this.remainingTime = remainingTime;
            }

            public String getDiscount_price() {
                return discount_price;
            }

            public void setDiscount_price(String discount_price) {
                this.discount_price = discount_price;
            }

            public int getHas_discount() {
                return has_discount;
            }

            public void setHas_discount(int has_discount) {
                this.has_discount = has_discount;
            }
        }

        public static class SpecsBean {
            /**
             * specId : 26
             * specName : 包装规格
             * specValues : [{"specValueId":1291,"specValueName":"5只装"}]
             */

            private int specId;
            private String specName;
            private List<SpecValuesBean> specValues;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public int getSpecId() {
                return specId;
            }

            public void setSpecId(int specId) {
                this.specId = specId;
            }

            public String getSpecName() {
                return specName;
            }

            public void setSpecName(String specName) {
                this.specName = specName;
            }

            public List<SpecValuesBean> getSpecValues() {
                return specValues;
            }

            public void setSpecValues(List<SpecValuesBean> specValues) {
                this.specValues = specValues;
            }

            public static class SpecValuesBean {
                /**
                 * specValueId : 1291
                 * specValueName : 5只装
                 * isChecked 是否有货
                 */

                private int specValueId;
                private String specValueName;
                private boolean isChecked;

                public boolean isChecked() {
                    return isChecked;
                }

                public void setChecked(boolean checked) {
                    isChecked = checked;
                }

                public int getSpecValueId() {
                    return specValueId;
                }

                public void setSpecValueId(int specValueId) {
                    this.specValueId = specValueId;
                }

                public String getSpecValueName() {
                    return specValueName;
                }

                public void setSpecValueName(String specValueName) {
                    this.specValueName = specValueName;
                }

                @Override
                public String toString() {
                    return "SpecValuesBean{" +
                            "specValueId=" + specValueId +
                            ", specValueName='" + specValueName + '\'' +
                            ", isChecked=" + isChecked +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "SpecsBean{" +
                        "specId=" + specId +
                        ", specName='" + specName + '\'' +
                        ", specValues=" + specValues +
                        ", isCheck=" + isCheck +
                        '}';
            }
        }

        public static class CarTypesBean {
            /**
             * id : 7867
             * goodsId : 2716
             * carName : 奥迪200
             * carDepthFirst : 1
             * carDepthSecond : 219
             * carDepthThird : 227
             * carDepthFourth : null
             */

            private int id;
            private int goodsId;
            private String carName;
            private int carDepthFirst;
            private int carDepthSecond;
            private int carDepthThird;
            private Object carDepthFourth;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getCarName() {
                return carName;
            }

            public void setCarName(String carName) {
                this.carName = carName;
            }

            public int getCarDepthFirst() {
                return carDepthFirst;
            }

            public void setCarDepthFirst(int carDepthFirst) {
                this.carDepthFirst = carDepthFirst;
            }

            public int getCarDepthSecond() {
                return carDepthSecond;
            }

            public void setCarDepthSecond(int carDepthSecond) {
                this.carDepthSecond = carDepthSecond;
            }

            public int getCarDepthThird() {
                return carDepthThird;
            }

            public void setCarDepthThird(int carDepthThird) {
                this.carDepthThird = carDepthThird;
            }

            public Object getCarDepthFourth() {
                return carDepthFourth;
            }

            public void setCarDepthFourth(Object carDepthFourth) {
                this.carDepthFourth = carDepthFourth;
            }
        }

        public static class SuitBean {
            /**
             * id : 78
             * goodsId : 77
             * productId : 1104
             * depotId : 8
             * name : 3M 汽车漆面美容水晶蜡 PN39026 473ml/瓶
             * price : 60.0
             * specs : 473ml
             * speclist : ["473ml"]
             * suitNum : 1
             * saleNum : 79
             * url : shoptt.yasn.com/static/attachment//store/1/goods/2017/6/6/11//37100986_thumbnail.jpeg
             */

            private int id;
            private int goodsId;
            private int productId;
            private int depotId;
            private String name;
            private double price;
            private String specs;
            private int suitNum;
            private int saleNum;
            private String url;
            private List<String> speclist;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getDepotId() {
                return depotId;
            }

            public void setDepotId(int depotId) {
                this.depotId = depotId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getSpecs() {
                return specs;
            }

            public void setSpecs(String specs) {
                this.specs = specs;
            }

            public int getSuitNum() {
                return suitNum;
            }

            public void setSuitNum(int suitNum) {
                this.suitNum = suitNum;
            }

            public int getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(int saleNum) {
                this.saleNum = saleNum;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<String> getSpeclist() {
                return speclist;
            }

            public void setSpeclist(List<String> speclist) {
                this.speclist = speclist;
            }
        }

        public static class ProductsBean {
            /**
             * productId : 4980
             * goodsId : 2716
             * name : 测试套装1
             * sn : 123123-0
             * store : 105
             * enableStore : 108
             * price : 14.0
             * cost : null
             * weight : null
             * specValueIds : [1291,1266]
             * step : 1
             * smallSale : 1
             * goodsLvPrices : null
             * ladderPrices : [{"id":89931,"lvId":1,"productId":4980,"goodsId":2716,"minNum":1,"maxNum":2000000000,"wholesalePrice":8,"activityPrice":"5.60"}]
             * copiedProductId : null
             * minReferencePrice : null
             * maxReferencePrice : null
             */

            private int productId;
            private int goodsId;
            private String name;
            private String sn;
            private int store;
            private int enableStore;
            private double price;
            private Object cost;
            private Object weight;
            private int step;
            private int smallSale;
            private Object goodsLvPrices;
            private Object copiedProductId;
            private String minReferencePrice;
            private String maxReferencePrice;
            private String activityPrice;
            private List<Integer> specValueIds;
            private List<LadderPricesBean> ladderPrices;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public int getStore() {
                return store;
            }

            public void setStore(int store) {
                this.store = store;
            }

            public int getEnableStore() {
                return enableStore;
            }

            public void setEnableStore(int enableStore) {
                this.enableStore = enableStore;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public Object getCost() {
                return cost;
            }

            public void setCost(Object cost) {
                this.cost = cost;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public int getStep() {
                return step;
            }

            public void setStep(int step) {
                this.step = step;
            }

            public int getSmallSale() {
                return smallSale;
            }

            public void setSmallSale(int smallSale) {
                this.smallSale = smallSale;
            }

            public Object getGoodsLvPrices() {
                return goodsLvPrices;
            }

            public void setGoodsLvPrices(Object goodsLvPrices) {
                this.goodsLvPrices = goodsLvPrices;
            }

            public Object getCopiedProductId() {
                return copiedProductId;
            }

            public void setCopiedProductId(Object copiedProductId) {
                this.copiedProductId = copiedProductId;
            }

            public String getMinReferencePrice() {
                return minReferencePrice;
            }

            public void setMinReferencePrice(String minReferencePrice) {
                this.minReferencePrice = minReferencePrice;
            }

            public String getMaxReferencePrice() {
                return maxReferencePrice;
            }

            public void setMaxReferencePrice(String maxReferencePrice) {
                this.maxReferencePrice = maxReferencePrice;
            }

            public List<Integer> getSpecValueIds() {
                return specValueIds;
            }

            public void setSpecValueIds(List<Integer> specValueIds) {
                this.specValueIds = specValueIds;
            }

            public List<LadderPricesBean> getLadderPrices() {
                return ladderPrices;
            }

            public void setLadderPrices(List<LadderPricesBean> ladderPrices) {
                this.ladderPrices = ladderPrices;
            }

            public String getActivityPrice() {
                return activityPrice;
            }

            public void setActivityPrice(String activityPrice) {
                this.activityPrice = activityPrice;
            }

            public static class LadderPricesBean {
                /**
                 * id : 89931
                 * lvId : 1
                 * productId : 4980
                 * goodsId : 2716
                 * minNum : 1
                 * maxNum : 2000000000
                 * wholesalePrice : 8.0
                 * activityPrice : 5.60
                 */

                private int id;
                private int lvId;
                private int productId;
                private int goodsId;
                private int minNum;
                private int maxNum;
                private double wholesalePrice;
                private String activityPrice;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getLvId() {
                    return lvId;
                }

                public void setLvId(int lvId) {
                    this.lvId = lvId;
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

                public double getWholesalePrice() {
                    return wholesalePrice;
                }

                public void setWholesalePrice(double wholesalePrice) {
                    this.wholesalePrice = wholesalePrice;
                }

                public String getActivityPrice() {
                    return activityPrice;
                }

                public void setActivityPrice(String activityPrice) {
                    this.activityPrice = activityPrice;
                }
            }
        }

        public static class GoodsGallerysBean {
            /**
             * imgId : 0
             * goodsId : 0
             * thumbnail : null
             * small : null
             * big : shoptt.yasn.com/static/attachment//store/1/goods/2017/8/30/19//13531655_big.png
             * original : null
             * tiny : null
             * isdefault : 0
             * sort : 0
             */

            private int imgId;
            private int goodsId;
            private String thumbnail;
            private Object small;
            private String big;
            private Object original;
            private Object tiny;
            private int isdefault;
            private int sort;

            public int getImgId() {
                return imgId;
            }

            public void setImgId(int imgId) {
                this.imgId = imgId;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public Object getSmall() {
                return small;
            }

            public void setSmall(Object small) {
                this.small = small;
            }

            public String getBig() {
                return big;
            }

            public void setBig(String big) {
                this.big = big;
            }

            public Object getOriginal() {
                return original;
            }

            public void setOriginal(Object original) {
                this.original = original;
            }

            public Object getTiny() {
                return tiny;
            }

            public void setTiny(Object tiny) {
                this.tiny = tiny;
            }

            public int getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(int isdefault) {
                this.isdefault = isdefault;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }

        public static class AttributesBean {
            /**
             * attrValue : 保赐利
             * attrName : 品牌
             */

            private String attrValue;
            private String attrName;

            public String getAttrValue() {
                return attrValue;
            }

            public void setAttrValue(String attrValue) {
                this.attrValue = attrValue;
            }

            public String getAttrName() {
                return attrName;
            }

            public void setAttrName(String attrName) {
                this.attrName = attrName;
            }
        }
    }
}
