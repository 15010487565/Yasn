package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/1/22.
 */

public class ShopCarModel implements Serializable {


    /**
     * code : 200
     * message : null
     * data : [{"store_id":1,"activity_name":"199包邮","activity_id":275,"store_name":"雅森自营","storeprice":{"goodsPrice":706,"orderPrice":706,"shippingPrice":0,"needPayMoney":706,"discountPrice":0,"weight":20000,"point":0,"discountItem":{},"actDiscount":0,"giftId":0,"bonusId":0,"isFreeShip":0,"freeShipMoney":199,"remindFreePrice":null,"actFreeShip":null,"exchangePoint":null,"activityPoint":null,"usePoint":null,"deductMoney":null,"moreBuyToSend":null,"activityGoods":[],"activity":[],"productId":[],"goodsNum":[],"goodsGiftId":[],"goodsGiftNum":[],"goodsBonusId":[],"goodsBonusNum":[],"points":[],"pointsNum":[],"act_free_ship":0},"goodslist":[{"id":59169,"productId":1129,"goodsId":1,"name":"保赐利R134A雪种B-2072/铝罐装 精冷 1箱（250g*30支）","mktprice":353,"price":353,"coupPrice":353,"subtotal":null,"num":1,"limitnum":null,"imageDefault":"http://shoptt.yasn.com/static/attachment//store/1/goods/2017/5/15/11//37432818_thumbnail.jpg","point":0,"itemtype":0,"sn":"ZC6926327633674","addon":"[{\"name\":\"容量\",\"value\":\"250g*30\"}]","specs":"250g*30","catid":493,"others":{"specList":[{"name":"容量","value":"250g*30"}]},"exchange":null,"unit":"箱","goodsType":0,"pmtList":null,"weight":10000,"activityId":275,"isCheck":1,"sendNum":null,"usePoint":null,"deductMoney":null,"storeId":1,"storeName":"雅森自营","goodsTransfeeCharge":0,"isExist":1,"enableStore":5,"step":1,"smallSale":1,"isBeforeSale":0,"goodsOff":0,"beforeSale":null},{"id":59170,"productId":1129,"goodsId":1,"name":"保赐利R134A雪种B-2072/铝罐装 精冷 1箱（250g*30支）","mktprice":353,"price":353,"coupPrice":353,"subtotal":null,"num":1,"limitnum":null,"imageDefault":"http://shoptt.yasn.com/static/attachment//store/1/goods/2017/5/15/11//37432818_thumbnail.jpg","point":0,"itemtype":0,"sn":"ZC6926327633674","addon":"[{\"name\":\"容量\",\"value\":\"250g*30\"}]","specs":"250g*30","catid":493,"others":{"specList":[{"name":"容量","value":"250g*30"}]},"exchange":null,"unit":"箱","goodsType":0,"pmtList":null,"weight":10000,"activityId":275,"isCheck":1,"sendNum":null,"usePoint":null,"deductMoney":null,"storeId":1,"storeName":"雅森自营","goodsTransfeeCharge":0,"isExist":1,"enableStore":5,"step":1,"smallSale":1,"isBeforeSale":0,"goodsOff":0,"beforeSale":null}]}]
     */

    private int code;
    private Object message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * store_id : 1
         * activity_name : 199包邮
         * activity_id : 275
         * store_name : 雅森自营
         * storeprice : {"goodsPrice":706,"orderPrice":706,"shippingPrice":0,"needPayMoney":706,"discountPrice":0,"weight":20000,"point":0,"discountItem":{},"actDiscount":0,"giftId":0,"bonusId":0,"isFreeShip":0,"freeShipMoney":199,"remindFreePrice":null,"actFreeShip":null,"exchangePoint":null,"activityPoint":null,"usePoint":null,"deductMoney":null,"moreBuyToSend":null,"activityGoods":[],"activity":[],"productId":[],"goodsNum":[],"goodsGiftId":[],"goodsGiftNum":[],"goodsBonusId":[],"goodsBonusNum":[],"points":[],"pointsNum":[],"act_free_ship":0}
         * goodslist : [{"id":59169,"productId":1129,"goodsId":1,"name":"保赐利R134A雪种B-2072/铝罐装 精冷 1箱（250g*30支）","mktprice":353,"price":353,"coupPrice":353,"subtotal":null,"num":1,"limitnum":null,"imageDefault":"http://shoptt.yasn.com/static/attachment//store/1/goods/2017/5/15/11//37432818_thumbnail.jpg","point":0,"itemtype":0,"sn":"ZC6926327633674","addon":"[{\"name\":\"容量\",\"value\":\"250g*30\"}]","specs":"250g*30","catid":493,"others":{"specList":[{"name":"容量","value":"250g*30"}]},"exchange":null,"unit":"箱","goodsType":0,"pmtList":null,"weight":10000,"activityId":275,"isCheck":1,"sendNum":null,"usePoint":null,"deductMoney":null,"storeId":1,"storeName":"雅森自营","goodsTransfeeCharge":0,"isExist":1,"enableStore":5,"step":1,"smallSale":1,"isBeforeSale":0,"goodsOff":0,"beforeSale":null},{"id":59170,"productId":1129,"goodsId":1,"name":"保赐利R134A雪种B-2072/铝罐装 精冷 1箱（250g*30支）","mktprice":353,"price":353,"coupPrice":353,"subtotal":null,"num":1,"limitnum":null,"imageDefault":"http://shoptt.yasn.com/static/attachment//store/1/goods/2017/5/15/11//37432818_thumbnail.jpg","point":0,"itemtype":0,"sn":"ZC6926327633674","addon":"[{\"name\":\"容量\",\"value\":\"250g*30\"}]","specs":"250g*30","catid":493,"others":{"specList":[{"name":"容量","value":"250g*30"}]},"exchange":null,"unit":"箱","goodsType":0,"pmtList":null,"weight":10000,"activityId":275,"isCheck":1,"sendNum":null,"usePoint":null,"deductMoney":null,"storeId":1,"storeName":"雅森自营","goodsTransfeeCharge":0,"isExist":1,"enableStore":5,"step":1,"smallSale":1,"isBeforeSale":0,"goodsOff":0,"beforeSale":null}]
         */

        private int store_id;
        private String activity_name;
        private int activity_id;
        private String store_name;
        private StorepriceBean storeprice;
        private List<GoodslistBean> goodslist;

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public StorepriceBean getStoreprice() {
            return storeprice;
        }

        public void setStoreprice(StorepriceBean storeprice) {
            this.storeprice = storeprice;
        }

        public List<GoodslistBean> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<GoodslistBean> goodslist) {
            this.goodslist = goodslist;
        }

        public static class StorepriceBean {
            /**
             * goodsPrice : 706.0
             * orderPrice : 706.0
             * shippingPrice : 0.0
             * needPayMoney : 706.0
             * discountPrice : 0.0
             * weight : 20000.0
             * point : 0
             * discountItem : {}
             * actDiscount : 0.0
             * giftId : 0
             * bonusId : 0
             * isFreeShip : 0
             * freeShipMoney : 199.0
             * remindFreePrice : null
             * actFreeShip : null
             * exchangePoint : null
             * activityPoint : null
             * usePoint : null
             * deductMoney : null
             * moreBuyToSend : null
             * activityGoods : []
             * activity : []
             * productId : []
             * goodsNum : []
             * goodsGiftId : []
             * goodsGiftNum : []
             * goodsBonusId : []
             * goodsBonusNum : []
             * points : []
             * pointsNum : []
             * act_free_ship : 0.0
             */

            private double goodsPrice;
            private double orderPrice;
            private double shippingPrice;
            private double needPayMoney;
            private double discountPrice;
            private double weight;
            private int point;
            private DiscountItemBean discountItem;
            private double actDiscount;
            private int giftId;
            private int bonusId;
            private int isFreeShip;
            private double freeShipMoney;
            private Object remindFreePrice;
            private Object actFreeShip;
            private Object exchangePoint;
            private Object activityPoint;
            private Object usePoint;
            private Object deductMoney;
            private Object moreBuyToSend;
            private double act_free_ship;
            private List<?> activityGoods;
            private List<?> activity;
            private List<?> productId;
            private List<?> goodsNum;
            private List<?> goodsGiftId;
            private List<?> goodsGiftNum;
            private List<?> goodsBonusId;
            private List<?> goodsBonusNum;
            private List<?> points;
            private List<?> pointsNum;

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public double getOrderPrice() {
                return orderPrice;
            }

            public void setOrderPrice(double orderPrice) {
                this.orderPrice = orderPrice;
            }

            public double getShippingPrice() {
                return shippingPrice;
            }

            public void setShippingPrice(double shippingPrice) {
                this.shippingPrice = shippingPrice;
            }

            public double getNeedPayMoney() {
                return needPayMoney;
            }

            public void setNeedPayMoney(double needPayMoney) {
                this.needPayMoney = needPayMoney;
            }

            public double getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(double discountPrice) {
                this.discountPrice = discountPrice;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public DiscountItemBean getDiscountItem() {
                return discountItem;
            }

            public void setDiscountItem(DiscountItemBean discountItem) {
                this.discountItem = discountItem;
            }

            public double getActDiscount() {
                return actDiscount;
            }

            public void setActDiscount(double actDiscount) {
                this.actDiscount = actDiscount;
            }

            public int getGiftId() {
                return giftId;
            }

            public void setGiftId(int giftId) {
                this.giftId = giftId;
            }

            public int getBonusId() {
                return bonusId;
            }

            public void setBonusId(int bonusId) {
                this.bonusId = bonusId;
            }

            public int getIsFreeShip() {
                return isFreeShip;
            }

            public void setIsFreeShip(int isFreeShip) {
                this.isFreeShip = isFreeShip;
            }

            public double getFreeShipMoney() {
                return freeShipMoney;
            }

            public void setFreeShipMoney(double freeShipMoney) {
                this.freeShipMoney = freeShipMoney;
            }

            public Object getRemindFreePrice() {
                return remindFreePrice;
            }

            public void setRemindFreePrice(Object remindFreePrice) {
                this.remindFreePrice = remindFreePrice;
            }

            public Object getActFreeShip() {
                return actFreeShip;
            }

            public void setActFreeShip(Object actFreeShip) {
                this.actFreeShip = actFreeShip;
            }

            public Object getExchangePoint() {
                return exchangePoint;
            }

            public void setExchangePoint(Object exchangePoint) {
                this.exchangePoint = exchangePoint;
            }

            public Object getActivityPoint() {
                return activityPoint;
            }

            public void setActivityPoint(Object activityPoint) {
                this.activityPoint = activityPoint;
            }

            public Object getUsePoint() {
                return usePoint;
            }

            public void setUsePoint(Object usePoint) {
                this.usePoint = usePoint;
            }

            public Object getDeductMoney() {
                return deductMoney;
            }

            public void setDeductMoney(Object deductMoney) {
                this.deductMoney = deductMoney;
            }

            public Object getMoreBuyToSend() {
                return moreBuyToSend;
            }

            public void setMoreBuyToSend(Object moreBuyToSend) {
                this.moreBuyToSend = moreBuyToSend;
            }

            public double getAct_free_ship() {
                return act_free_ship;
            }

            public void setAct_free_ship(double act_free_ship) {
                this.act_free_ship = act_free_ship;
            }

            public List<?> getActivityGoods() {
                return activityGoods;
            }

            public void setActivityGoods(List<?> activityGoods) {
                this.activityGoods = activityGoods;
            }

            public List<?> getActivity() {
                return activity;
            }

            public void setActivity(List<?> activity) {
                this.activity = activity;
            }

            public List<?> getProductId() {
                return productId;
            }

            public void setProductId(List<?> productId) {
                this.productId = productId;
            }

            public List<?> getGoodsNum() {
                return goodsNum;
            }

            public void setGoodsNum(List<?> goodsNum) {
                this.goodsNum = goodsNum;
            }

            public List<?> getGoodsGiftId() {
                return goodsGiftId;
            }

            public void setGoodsGiftId(List<?> goodsGiftId) {
                this.goodsGiftId = goodsGiftId;
            }

            public List<?> getGoodsGiftNum() {
                return goodsGiftNum;
            }

            public void setGoodsGiftNum(List<?> goodsGiftNum) {
                this.goodsGiftNum = goodsGiftNum;
            }

            public List<?> getGoodsBonusId() {
                return goodsBonusId;
            }

            public void setGoodsBonusId(List<?> goodsBonusId) {
                this.goodsBonusId = goodsBonusId;
            }

            public List<?> getGoodsBonusNum() {
                return goodsBonusNum;
            }

            public void setGoodsBonusNum(List<?> goodsBonusNum) {
                this.goodsBonusNum = goodsBonusNum;
            }

            public List<?> getPoints() {
                return points;
            }

            public void setPoints(List<?> points) {
                this.points = points;
            }

            public List<?> getPointsNum() {
                return pointsNum;
            }

            public void setPointsNum(List<?> pointsNum) {
                this.pointsNum = pointsNum;
            }

            public static class DiscountItemBean {
            }
        }

        public static class GoodslistBean {
            /**
             * id : 59169
             * productId : 1129
             * goodsId : 1
             * name : 保赐利R134A雪种B-2072/铝罐装 精冷 1箱（250g*30支）
             * mktprice : 353.0
             * price : 353.0
             * coupPrice : 353.0
             * subtotal : null
             * num : 1
             * limitnum : null
             * imageDefault : http://shoptt.yasn.com/static/attachment//store/1/goods/2017/5/15/11//37432818_thumbnail.jpg
             * point : 0
             * itemtype : 0
             * sn : ZC6926327633674
             * addon : [{"name":"容量","value":"250g*30"}]
             * specs : 250g*30
             * catid : 493
             * others : {"specList":[{"name":"容量","value":"250g*30"}]}
             * exchange : null
             * unit : 箱
             * goodsType : 0
             * pmtList : null
             * weight : 10000.0
             * activityId : 275
             * isCheck : 1
             * sendNum : null
             * usePoint : null
             * deductMoney : null
             * storeId : 1
             * storeName : 雅森自营
             * goodsTransfeeCharge : 0
             * isExist : 1
             * enableStore : 5
             * step : 1
             * smallSale : 1
             * isBeforeSale : 0
             * goodsOff : 0
             * beforeSale : null
             */

            private int id;
            private int productId;
            private int goodsId;
            private String name;
            private double mktprice;
            private double price;
            private double coupPrice;
            private Object subtotal;
            private int num;
            private int limitnum;
            private String imageDefault;
            private int point;
            private int itemtype;
            private String sn;
            private String addon;
            private String specs;
            private int catid;
            private OthersBean others;
            private Object exchange;
            private String unit;
            private int goodsType;
            private Object pmtList;
            private double weight;
            private int activityId;
            private int isCheck;
            private Object sendNum;
            private Object usePoint;
            private Object deductMoney;
            private int storeId;
            private String storeName;
            private int goodsTransfeeCharge;
            private int isExist;
            private int enableStore;
            private int step;
            private int smallSale;
            private int isBeforeSale;
            private int goodsOff;
            private String beforeSale;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getMktprice() {
                return mktprice;
            }

            public void setMktprice(double mktprice) {
                this.mktprice = mktprice;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getCoupPrice() {
                return coupPrice;
            }

            public void setCoupPrice(double coupPrice) {
                this.coupPrice = coupPrice;
            }

            public Object getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(Object subtotal) {
                this.subtotal = subtotal;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getLimitnum() {
                return limitnum;
            }

            public void setLimitnum(int limitnum) {
                this.limitnum = limitnum;
            }

            public String getImageDefault() {
                return imageDefault;
            }

            public void setImageDefault(String imageDefault) {
                this.imageDefault = imageDefault;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public int getItemtype() {
                return itemtype;
            }

            public void setItemtype(int itemtype) {
                this.itemtype = itemtype;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getAddon() {
                return addon;
            }

            public void setAddon(String addon) {
                this.addon = addon;
            }

            public String getSpecs() {
                return specs;
            }

            public void setSpecs(String specs) {
                this.specs = specs;
            }

            public int getCatid() {
                return catid;
            }

            public void setCatid(int catid) {
                this.catid = catid;
            }

            public OthersBean getOthers() {
                return others;
            }

            public void setOthers(OthersBean others) {
                this.others = others;
            }

            public Object getExchange() {
                return exchange;
            }

            public void setExchange(Object exchange) {
                this.exchange = exchange;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public int getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(int goodsType) {
                this.goodsType = goodsType;
            }

            public Object getPmtList() {
                return pmtList;
            }

            public void setPmtList(Object pmtList) {
                this.pmtList = pmtList;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }

            public int getActivityId() {
                return activityId;
            }

            public void setActivityId(int activityId) {
                this.activityId = activityId;
            }

            public int getIsCheck() {
                return isCheck;
            }

            public void setIsCheck(int isCheck) {
                this.isCheck = isCheck;
            }

            public Object getSendNum() {
                return sendNum;
            }

            public void setSendNum(Object sendNum) {
                this.sendNum = sendNum;
            }

            public Object getUsePoint() {
                return usePoint;
            }

            public void setUsePoint(Object usePoint) {
                this.usePoint = usePoint;
            }

            public Object getDeductMoney() {
                return deductMoney;
            }

            public void setDeductMoney(Object deductMoney) {
                this.deductMoney = deductMoney;
            }

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public int getGoodsTransfeeCharge() {
                return goodsTransfeeCharge;
            }

            public void setGoodsTransfeeCharge(int goodsTransfeeCharge) {
                this.goodsTransfeeCharge = goodsTransfeeCharge;
            }

            public int getIsExist() {
                return isExist;
            }

            public void setIsExist(int isExist) {
                this.isExist = isExist;
            }

            public int getEnableStore() {
                return enableStore;
            }

            public void setEnableStore(int enableStore) {
                this.enableStore = enableStore;
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

            public int getIsBeforeSale() {
                return isBeforeSale;
            }

            public void setIsBeforeSale(int isBeforeSale) {
                this.isBeforeSale = isBeforeSale;
            }

            public int getGoodsOff() {
                return goodsOff;
            }

            public void setGoodsOff(int goodsOff) {
                this.goodsOff = goodsOff;
            }

            public String getBeforeSale() {
                return beforeSale;
            }

            public void setBeforeSale(String beforeSale) {
                this.beforeSale = beforeSale;
            }

            public static class OthersBean {
                private List<SpecListBean> specList;

                public List<SpecListBean> getSpecList() {
                    return specList;
                }

                public void setSpecList(List<SpecListBean> specList) {
                    this.specList = specList;
                }

                public static class SpecListBean {
                    /**
                     * name : 容量
                     * value : 250g*30
                     */

                    private String name;
                    private String value;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }
            }
        }
    }
}
