package com.yasn.purchase.model.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/6/13.
 */

public class ShopcarPayModel implements Serializable{

    /**
     * mainOrder : {"shippingTotal":0,"usePoint":0,"deductMoney":0,"needPayMoney":0,"enablePoint":0,"enableDeductMoney":0}
     * subOrders : [{"storeName":"雅森自营","shippingTotal":0,"usePoint":0,"deductMoney":0,"orderType":0,"moreBuyToSend":"[]","weight":0,"needPayMoney":0,"enablePoint":0,"enableDeductMoney":0,"orderItemVOS":[{"goodsId":3746,"productId":5770,"buyNum":0,"num":1,"name":"测试测试","sn":"186535-0","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/3/20/15//09253348_thumbnail.jpg","price":0,"unit":""}]}]
     * memberAddress : {"addrId":12497,"memberId":24941,"name":"测试订单状态账号","country":null,"province":"北京","city":"东城区","region":"东城区","addr":"东城区银座","tel":null,"mobile":"13681315199","defAddr":1,"inUse":0}
     * invoice : null
     */

    private MainOrderBean mainOrder;
    private MemberAddressBean memberAddress;
    private Object invoice;
    private List<SubOrdersBean> subOrders;

    public MainOrderBean getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(MainOrderBean mainOrder) {
        this.mainOrder = mainOrder;
    }

    public MemberAddressBean getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(MemberAddressBean memberAddress) {
        this.memberAddress = memberAddress;
    }

    public Object getInvoice() {
        return invoice;
    }

    public void setInvoice(Object invoice) {
        this.invoice = invoice;
    }

    public List<SubOrdersBean> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(List<SubOrdersBean> subOrders) {
        this.subOrders = subOrders;
    }

    public static class MainOrderBean {
        /**
         * shippingTotal : 0.0
         * usePoint : 0
         * deductMoney : 0.0
         * needPayMoney : 0.0
         * enablePoint : 0
         * enableDeductMoney : 0.0
         */

        private double shippingTotal;
        private int usePoint;
        private double deductMoney;
        private double needPayMoney;
        private int enablePoint;
        private double enableDeductMoney;

        public double getShippingTotal() {
            return shippingTotal;
        }

        public void setShippingTotal(double shippingTotal) {
            this.shippingTotal = shippingTotal;
        }

        public int getUsePoint() {
            return usePoint;
        }

        public void setUsePoint(int usePoint) {
            this.usePoint = usePoint;
        }

        public double getDeductMoney() {
            return deductMoney;
        }

        public void setDeductMoney(double deductMoney) {
            this.deductMoney = deductMoney;
        }

        public double getNeedPayMoney() {
            return needPayMoney;
        }

        public void setNeedPayMoney(double needPayMoney) {
            this.needPayMoney = needPayMoney;
        }

        public int getEnablePoint() {
            return enablePoint;
        }

        public void setEnablePoint(int enablePoint) {
            this.enablePoint = enablePoint;
        }

        public double getEnableDeductMoney() {
            return enableDeductMoney;
        }

        public void setEnableDeductMoney(double enableDeductMoney) {
            this.enableDeductMoney = enableDeductMoney;
        }
    }

    public static class MemberAddressBean {
        /**
         * addrId : 12497
         * memberId : 24941
         * name : 测试订单状态账号
         * country : null
         * province : 北京
         * city : 东城区
         * region : 东城区
         * addr : 东城区银座
         * tel : null
         * mobile : 13681315199
         * defAddr : 1
         * inUse : 0
         */

        private int addrId;
        private int memberId;
        private String name;
        private Object country;
        private String province;
        private String city;
        private String region;
        private String addr;
        private Object tel;
        private String mobile;
        private int defAddr;
        private int inUse;

        public int getAddrId() {
            return addrId;
        }

        public void setAddrId(int addrId) {
            this.addrId = addrId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public Object getTel() {
            return tel;
        }

        public void setTel(Object tel) {
            this.tel = tel;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getDefAddr() {
            return defAddr;
        }

        public void setDefAddr(int defAddr) {
            this.defAddr = defAddr;
        }

        public int getInUse() {
            return inUse;
        }

        public void setInUse(int inUse) {
            this.inUse = inUse;
        }
    }

    public static class SubOrdersBean {
        /**
         * storeName : 雅森自营
         * shippingTotal : 0.0
         * usePoint : 0
         * deductMoney : 0.0
         * orderType : 0
         * moreBuyToSend : []
         * weight : 0.0
         * needPayMoney : 0.0
         * enablePoint : 0
         * enableDeductMoney : 0.0
         * orderItemVOS : [{"goodsId":3746,"productId":5770,"buyNum":0,"num":1,"name":"测试测试","sn":"186535-0","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/3/20/15//09253348_thumbnail.jpg","price":0,"unit":""}]
         */

        private String storeName;
        private double shippingTotal;
        private int usePoint;
        private double deductMoney;
        private int orderType;
        private String moreBuyToSend;
        private double weight;
        private double needPayMoney;
        private int enablePoint;
        private double enableDeductMoney;
        private List<OrderItemVOSBean> orderItemVOS;

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public double getShippingTotal() {
            return shippingTotal;
        }

        public void setShippingTotal(double shippingTotal) {
            this.shippingTotal = shippingTotal;
        }

        public int getUsePoint() {
            return usePoint;
        }

        public void setUsePoint(int usePoint) {
            this.usePoint = usePoint;
        }

        public double getDeductMoney() {
            return deductMoney;
        }

        public void setDeductMoney(double deductMoney) {
            this.deductMoney = deductMoney;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public String getMoreBuyToSend() {
            return moreBuyToSend;
        }

        public void setMoreBuyToSend(String moreBuyToSend) {
            this.moreBuyToSend = moreBuyToSend;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public double getNeedPayMoney() {
            return needPayMoney;
        }

        public void setNeedPayMoney(double needPayMoney) {
            this.needPayMoney = needPayMoney;
        }

        public int getEnablePoint() {
            return enablePoint;
        }

        public void setEnablePoint(int enablePoint) {
            this.enablePoint = enablePoint;
        }

        public double getEnableDeductMoney() {
            return enableDeductMoney;
        }

        public void setEnableDeductMoney(double enableDeductMoney) {
            this.enableDeductMoney = enableDeductMoney;
        }

        public List<OrderItemVOSBean> getOrderItemVOS() {
            return orderItemVOS;
        }

        public void setOrderItemVOS(List<OrderItemVOSBean> orderItemVOS) {
            this.orderItemVOS = orderItemVOS;
        }

        public static class OrderItemVOSBean {
            /**
             * goodsId : 3746
             * productId : 5770
             * buyNum : 0
             * num : 1
             * name : 测试测试
             * sn : 186535-0
             * image : http://shoptt.yasn.com/static/attachment//store/1/goods/2018/3/20/15//09253348_thumbnail.jpg
             * price : 0.0
             * unit :
             */

            private int goodsId;
            private int productId;
            private int buyNum;
            private int num;
            private String name;
            private String sn;
            private String image;
            private double price;
            private String unit;

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

            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
        }
    }
}
