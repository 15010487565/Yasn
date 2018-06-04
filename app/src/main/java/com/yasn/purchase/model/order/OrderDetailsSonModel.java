package com.yasn.purchase.model.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/5/28.
 */

public class OrderDetailsSonModel implements Serializable{

    /**
     * orderDetails : {"orderId":48541,"sn":"152359559423-1","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":195,"shippingType":"快递","shippingArea":"北京-门头沟区-门头沟区","paymentId":1,"paymentName":"支付宝","paymentType":"alipayDirectPlugin","paymentAccount":null,"paymoney":3403.5,"paydate":1523847647,"createTime":1523595595,"shipName":"23eee222ff","shipAddr":"33","shipZip":"123456","shipEmail":null,"shipMobile":"13333333333","shipTel":null,"goodsAmount":7371,"shippingAmount":1032.5,"orderAmount":8403.5,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":14,"shipRegionid":15,"depotid":8,"adminRemark":null,"needPayMoney":8403.5,"shipNo":null,"addressId":12367,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":1,"parentId":48540,"commission":null,"billStatus":1,"billSn":"21-1","storeName":"雅森自营","initMoney":8403.5,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":1032.5,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":"洗车","orderItem":[{"itemId":45459,"orderId":48541,"goodsId":3724,"productId":5776,"buyNum":0,"num":1,"shipNum":0,"name":"测试","sn":"0000111154278-3","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/23/12//18479021_thumbnail.jpg","store":null,"addon":"[{\"name\":\"容量\",\"value\":\"450ml\"},{\"name\":\"包装规格\",\"value\":\"230g*4罐\"}]","catId":493,"price":11,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null},{"itemId":45460,"orderId":48541,"goodsId":3764,"productId":5794,"buyNum":0,"num":2,"shipNum":0,"name":"限购1111","sn":"3461-0","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/3/29/13//50065554_thumbnail.jpg","store":null,"addon":"[{\"name\":\"默认\",\"value\":\"默认\"}]","catId":493,"price":3680,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null}],"childOrderList":null}
     * member : null
     * orderStatus : {"PAY_YES":2,"SHIP_ROG":2,"ORDER_PAY":2,"ORDER_FAILLENDING":200,"SHIP_YES":1,"SHIP_NO":0,"ORDER_ROG":4,"ORDER_WAITLENDING":100,"ORDER_SHIP":3,"ORDER_RISK_CHECK":9,"ORDER_NOT_PAY":0,"ORDER_CONFIRM":1,"ORDER_COMPLETE":5,"ORDER_MAINTENANCE":7,"PAY_NO":0,"PAY_PARTIAL_PAYED":1,"ORDER_CANCELLATION":6,"SHIP_PARTIAL_CANCEL":1}
     */

    private OrderDetailsBean orderDetails;
    private Object member;
    private OrderStatusBean orderStatus;

    public OrderDetailsBean getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailsBean orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Object getMember() {
        return member;
    }

    public void setMember(Object member) {
        this.member = member;
    }

    public OrderStatusBean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusBean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static class OrderDetailsBean {
        /**
         * orderId : 48541
         * sn : 152359559423-1
         * memberId : 21880
         * status : 2
         * payStatus : 2
         * shipStatus : 0
         * shippingId : 195
         * shippingType : 快递
         * shippingArea : 北京-门头沟区-门头沟区
         * paymentId : 1
         * paymentName : 支付宝
         * paymentType : alipayDirectPlugin
         * paymentAccount : null
         * paymoney : 3403.5
         * paydate : 1523847647
         * createTime : 1523595595
         * shipName : 23eee222ff
         * shipAddr : 33
         * shipZip : 123456
         * shipEmail : null
         * shipMobile : 13333333333
         * shipTel : null
         * goodsAmount : 7371.0
         * shippingAmount : 1032.5
         * orderAmount : 8403.5
         * goodsNum : 0
         * gainedpoint : 0
         * consumepoint : 0
         * remark :
         * disabled : 0
         * discount : 0.0
         * completeTime : 0
         * cancelReason : null
         * signingTime : 0
         * theSign : null
         * shipProvinceid : 1
         * shipCityid : 14
         * shipRegionid : 15
         * depotid : 8
         * adminRemark : null
         * needPayMoney : 8403.5
         * shipNo : null
         * addressId : 12367
         * logiId : 0
         * logiName : null
         * giftId : 0
         * activityGift : null
         * bonusId : 0
         * actDiscount : 0.0
         * activityPoint : 0
         * isCancel : 0
         * storeId : 1
         * parentId : 48540
         * commission : null
         * billStatus : 1
         * billSn : 21-1
         * storeName : 雅森自营
         * initMoney : 8403.5
         * moreBuyToSend : []
         * manualReason : null
         * pushStatus : 0
         * pushTimes : 0
         * manualStatus : 0
         * mergeOrderId : 0
         * shippingTotal : 1032.5
         * cancelTime : 0
         * usePoint : 0
         * deductMoney : 0.0
         * salesName : null
         * cancelStatus : 0
         * cancelDatetime : 0
         * cancelAuditDatetime : 0
         * cancelAuditRemark : null
         * source : 2
         * orderType : 0
         * beforeSaleSendTime : 0
         * orderFrom : 0
         * regionIds : 0
         * createOrderMemberId : 21880
         * receipt : null
         * shopName : 洗车
         * orderItem : [{"itemId":45459,"orderId":48541,"goodsId":3724,"productId":5776,"buyNum":0,"num":1,"shipNum":0,"name":"测试","sn":"0000111154278-3","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/23/12//18479021_thumbnail.jpg","store":null,"addon":"[{\"name\":\"容量\",\"value\":\"450ml\"},{\"name\":\"包装规格\",\"value\":\"230g*4罐\"}]","catId":493,"price":11,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null},{"itemId":45460,"orderId":48541,"goodsId":3764,"productId":5794,"buyNum":0,"num":2,"shipNum":0,"name":"限购1111","sn":"3461-0","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/3/29/13//50065554_thumbnail.jpg","store":null,"addon":"[{\"name\":\"默认\",\"value\":\"默认\"}]","catId":493,"price":3680,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null}]
         * childOrderList : null
         */

        private int orderId;
        private String sn;
        private int memberId;
        private int status;
        private int payStatus;
        private int shipStatus;
        private int shippingId;
        private String shippingType;
        private String shippingArea;
        private int paymentId;
        private String paymentName;
        private String paymentType;
        private Object paymentAccount;
        private double paymoney;
        private int paydate;
        private int createTime;
        private String shipName;
        private String shipAddr;
        private String shipZip;
        private Object shipEmail;
        private String shipMobile;
        private Object shipTel;
        private double goodsAmount;
        private double shippingAmount;
        private double orderAmount;
        private int goodsNum;
        private int gainedpoint;
        private int consumepoint;
        private String remark;
        private String disabled;
        private double discount;
        private int completeTime;
        private Object cancelReason;
        private int signingTime;
        private Object theSign;
        private int shipProvinceid;
        private int shipCityid;
        private int shipRegionid;
        private int depotid;
        private Object adminRemark;
        private double needPayMoney;
        private Object shipNo;
        private int addressId;
        private int logiId;
        private Object logiName;
        private int giftId;
        private Object activityGift;
        private int bonusId;
        private double actDiscount;
        private int activityPoint;
        private int isCancel;
        private int storeId;
        private int parentId;
        private Object commission;
        private int billStatus;
        private String billSn;
        private String storeName;
        private double initMoney;
        private String moreBuyToSend;
        private Object manualReason;
        private int pushStatus;
        private int pushTimes;
        private int manualStatus;
        private int mergeOrderId;
        private double shippingTotal;
        private int cancelTime;
        private int usePoint;
        private double deductMoney;
        private Object salesName;
        private int cancelStatus;
        private int cancelDatetime;
        private int cancelAuditDatetime;
        private Object cancelAuditRemark;
        private int source;
        private int orderType;
        private long beforeSaleSendTime;
        private int orderFrom;
        private String regionIds;
        private int createOrderMemberId;
//        private ReceiptBean receipt;
        private String shopName;
        private Object childOrderList;
        private List<OrderItemBean> orderItem;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public int getShipStatus() {
            return shipStatus;
        }

        public void setShipStatus(int shipStatus) {
            this.shipStatus = shipStatus;
        }

        public int getShippingId() {
            return shippingId;
        }

        public void setShippingId(int shippingId) {
            this.shippingId = shippingId;
        }

        public String getShippingType() {
            return shippingType;
        }

        public void setShippingType(String shippingType) {
            this.shippingType = shippingType;
        }

        public String getShippingArea() {
            return shippingArea;
        }

        public void setShippingArea(String shippingArea) {
            this.shippingArea = shippingArea;
        }

        public int getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(int paymentId) {
            this.paymentId = paymentId;
        }

        public String getPaymentName() {
            return paymentName;
        }

        public void setPaymentName(String paymentName) {
            this.paymentName = paymentName;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public Object getPaymentAccount() {
            return paymentAccount;
        }

        public void setPaymentAccount(Object paymentAccount) {
            this.paymentAccount = paymentAccount;
        }

        public double getPaymoney() {
            return paymoney;
        }

        public void setPaymoney(double paymoney) {
            this.paymoney = paymoney;
        }

        public int getPaydate() {
            return paydate;
        }

        public void setPaydate(int paydate) {
            this.paydate = paydate;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public String getShipName() {
            return shipName;
        }

        public void setShipName(String shipName) {
            this.shipName = shipName;
        }

        public String getShipAddr() {
            return shipAddr;
        }

        public void setShipAddr(String shipAddr) {
            this.shipAddr = shipAddr;
        }

        public String getShipZip() {
            return shipZip;
        }

        public void setShipZip(String shipZip) {
            this.shipZip = shipZip;
        }

        public Object getShipEmail() {
            return shipEmail;
        }

        public void setShipEmail(Object shipEmail) {
            this.shipEmail = shipEmail;
        }

        public String getShipMobile() {
            return shipMobile;
        }

        public void setShipMobile(String shipMobile) {
            this.shipMobile = shipMobile;
        }

        public Object getShipTel() {
            return shipTel;
        }

        public void setShipTel(Object shipTel) {
            this.shipTel = shipTel;
        }

        public double getGoodsAmount() {
            return goodsAmount;
        }

        public void setGoodsAmount(double goodsAmount) {
            this.goodsAmount = goodsAmount;
        }

        public double getShippingAmount() {
            return shippingAmount;
        }

        public void setShippingAmount(double shippingAmount) {
            this.shippingAmount = shippingAmount;
        }

        public double getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public int getGainedpoint() {
            return gainedpoint;
        }

        public void setGainedpoint(int gainedpoint) {
            this.gainedpoint = gainedpoint;
        }

        public int getConsumepoint() {
            return consumepoint;
        }

        public void setConsumepoint(int consumepoint) {
            this.consumepoint = consumepoint;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDisabled() {
            return disabled;
        }

        public void setDisabled(String disabled) {
            this.disabled = disabled;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public int getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(int completeTime) {
            this.completeTime = completeTime;
        }

        public Object getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(Object cancelReason) {
            this.cancelReason = cancelReason;
        }

        public int getSigningTime() {
            return signingTime;
        }

        public void setSigningTime(int signingTime) {
            this.signingTime = signingTime;
        }

        public Object getTheSign() {
            return theSign;
        }

        public void setTheSign(Object theSign) {
            this.theSign = theSign;
        }

        public int getShipProvinceid() {
            return shipProvinceid;
        }

        public void setShipProvinceid(int shipProvinceid) {
            this.shipProvinceid = shipProvinceid;
        }

        public int getShipCityid() {
            return shipCityid;
        }

        public void setShipCityid(int shipCityid) {
            this.shipCityid = shipCityid;
        }

        public int getShipRegionid() {
            return shipRegionid;
        }

        public void setShipRegionid(int shipRegionid) {
            this.shipRegionid = shipRegionid;
        }

        public int getDepotid() {
            return depotid;
        }

        public void setDepotid(int depotid) {
            this.depotid = depotid;
        }

        public Object getAdminRemark() {
            return adminRemark;
        }

        public void setAdminRemark(Object adminRemark) {
            this.adminRemark = adminRemark;
        }

        public double getNeedPayMoney() {
            return needPayMoney;
        }

        public void setNeedPayMoney(double needPayMoney) {
            this.needPayMoney = needPayMoney;
        }

        public Object getShipNo() {
            return shipNo;
        }

        public void setShipNo(Object shipNo) {
            this.shipNo = shipNo;
        }

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public int getLogiId() {
            return logiId;
        }

        public void setLogiId(int logiId) {
            this.logiId = logiId;
        }

        public Object getLogiName() {
            return logiName;
        }

        public void setLogiName(Object logiName) {
            this.logiName = logiName;
        }

        public int getGiftId() {
            return giftId;
        }

        public void setGiftId(int giftId) {
            this.giftId = giftId;
        }

        public Object getActivityGift() {
            return activityGift;
        }

        public void setActivityGift(Object activityGift) {
            this.activityGift = activityGift;
        }

        public int getBonusId() {
            return bonusId;
        }

        public void setBonusId(int bonusId) {
            this.bonusId = bonusId;
        }

        public double getActDiscount() {
            return actDiscount;
        }

        public void setActDiscount(double actDiscount) {
            this.actDiscount = actDiscount;
        }

        public int getActivityPoint() {
            return activityPoint;
        }

        public void setActivityPoint(int activityPoint) {
            this.activityPoint = activityPoint;
        }

        public int getIsCancel() {
            return isCancel;
        }

        public void setIsCancel(int isCancel) {
            this.isCancel = isCancel;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public Object getCommission() {
            return commission;
        }

        public void setCommission(Object commission) {
            this.commission = commission;
        }

        public int getBillStatus() {
            return billStatus;
        }

        public void setBillStatus(int billStatus) {
            this.billStatus = billStatus;
        }

        public String getBillSn() {
            return billSn;
        }

        public void setBillSn(String billSn) {
            this.billSn = billSn;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public double getInitMoney() {
            return initMoney;
        }

        public void setInitMoney(double initMoney) {
            this.initMoney = initMoney;
        }

        public String getMoreBuyToSend() {
            return moreBuyToSend;
        }

        public void setMoreBuyToSend(String moreBuyToSend) {
            this.moreBuyToSend = moreBuyToSend;
        }

        public Object getManualReason() {
            return manualReason;
        }

        public void setManualReason(Object manualReason) {
            this.manualReason = manualReason;
        }

        public int getPushStatus() {
            return pushStatus;
        }

        public void setPushStatus(int pushStatus) {
            this.pushStatus = pushStatus;
        }

        public int getPushTimes() {
            return pushTimes;
        }

        public void setPushTimes(int pushTimes) {
            this.pushTimes = pushTimes;
        }

        public int getManualStatus() {
            return manualStatus;
        }

        public void setManualStatus(int manualStatus) {
            this.manualStatus = manualStatus;
        }

        public int getMergeOrderId() {
            return mergeOrderId;
        }

        public void setMergeOrderId(int mergeOrderId) {
            this.mergeOrderId = mergeOrderId;
        }

        public double getShippingTotal() {
            return shippingTotal;
        }

        public void setShippingTotal(double shippingTotal) {
            this.shippingTotal = shippingTotal;
        }

        public int getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(int cancelTime) {
            this.cancelTime = cancelTime;
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

        public Object getSalesName() {
            return salesName;
        }

        public void setSalesName(Object salesName) {
            this.salesName = salesName;
        }

        public int getCancelStatus() {
            return cancelStatus;
        }

        public void setCancelStatus(int cancelStatus) {
            this.cancelStatus = cancelStatus;
        }

        public int getCancelDatetime() {
            return cancelDatetime;
        }

        public void setCancelDatetime(int cancelDatetime) {
            this.cancelDatetime = cancelDatetime;
        }

        public int getCancelAuditDatetime() {
            return cancelAuditDatetime;
        }

        public void setCancelAuditDatetime(int cancelAuditDatetime) {
            this.cancelAuditDatetime = cancelAuditDatetime;
        }

        public Object getCancelAuditRemark() {
            return cancelAuditRemark;
        }

        public void setCancelAuditRemark(Object cancelAuditRemark) {
            this.cancelAuditRemark = cancelAuditRemark;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public long getBeforeSaleSendTime() {
            return beforeSaleSendTime;
        }

        public void setBeforeSaleSendTime(long beforeSaleSendTime) {
            this.beforeSaleSendTime = beforeSaleSendTime;
        }

        public int getOrderFrom() {
            return orderFrom;
        }

        public void setOrderFrom(int orderFrom) {
            this.orderFrom = orderFrom;
        }

        public String getRegionIds() {
            return regionIds;
        }

        public void setRegionIds(String regionIds) {
            this.regionIds = regionIds;
        }

        public int getCreateOrderMemberId() {
            return createOrderMemberId;
        }

        public void setCreateOrderMemberId(int createOrderMemberId) {
            this.createOrderMemberId = createOrderMemberId;
        }
//        public ReceiptBean getReceipt() {
//            return receipt;
//        }
//
//        public void setReceipt(ReceiptBean receipt) {
//            this.receipt = receipt;
//        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Object getChildOrderList() {
            return childOrderList;
        }

        public void setChildOrderList(Object childOrderList) {
            this.childOrderList = childOrderList;
        }

        public List<OrderItemBean> getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(List<OrderItemBean> orderItem) {
            this.orderItem = orderItem;
        }

//        public static class ReceiptBean {
//
//            /**
//             * title : 单位名哈哈
//             * invoiceNum : 识别号哈哈
//             * receiptStatus : 2
//             */
//
//            private String title;
//            private String invoiceNum;
//            private int receiptStatus;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public String getInvoiceNum() {
//                return invoiceNum;
//            }
//
//            public void setInvoiceNum(String invoiceNum) {
//                this.invoiceNum = invoiceNum;
//            }
//
//            public int getReceiptStatus() {
//                return receiptStatus;
//            }
//
//            public void setReceiptStatus(int receiptStatus) {
//                this.receiptStatus = receiptStatus;
//            }
//        }

        public static class OrderItemBean {
            /**
             * itemId : 45459
             * orderId : 48541
             * goodsId : 3724
             * productId : 5776
             * buyNum : 0
             * num : 1
             * shipNum : 0
             * name : 测试
             * sn : 0000111154278-3
             * image : http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/23/12//18479021_thumbnail.jpg
             * store : null
             * addon : [{"name":"容量","value":"450ml"},{"name":"包装规格","value":"230g*4罐"}]
             * catId : 493
             * price : 11.0
             * gainedpoint : 0
             * state : 0
             * unit :
             * goodsType : 0
             * presentNum : 0
             * usePoint : 0
             * deductMoney : 0.0
             * outSn : null
             * outEdNum : 0
             * saleSendTime : null
             */

            private int itemId;
            private int orderId;
            private int goodsId;
            private int productId;
            private int buyNum;
            private int num;
            private int shipNum;
            private String name;
            private String sn;
            private String image;
            private Object store;
            private String addon;
            private int catId;
            private double price;
            private int gainedpoint;
            private int state;
            private String unit;
            private int goodsType;
            private int presentNum;
            private int usePoint;
            private double deductMoney;
            private Object outSn;
            private int outEdNum;
            private Object saleSendTime;

            public int getItemId() {
                return itemId;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
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

            public int getShipNum() {
                return shipNum;
            }

            public void setShipNum(int shipNum) {
                this.shipNum = shipNum;
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

            public Object getStore() {
                return store;
            }

            public void setStore(Object store) {
                this.store = store;
            }

            public String getAddon() {
                return addon;
            }

            public void setAddon(String addon) {
                this.addon = addon;
            }

            public int getCatId() {
                return catId;
            }

            public void setCatId(int catId) {
                this.catId = catId;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getGainedpoint() {
                return gainedpoint;
            }

            public void setGainedpoint(int gainedpoint) {
                this.gainedpoint = gainedpoint;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
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

            public int getPresentNum() {
                return presentNum;
            }

            public void setPresentNum(int presentNum) {
                this.presentNum = presentNum;
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

            public Object getOutSn() {
                return outSn;
            }

            public void setOutSn(Object outSn) {
                this.outSn = outSn;
            }

            public int getOutEdNum() {
                return outEdNum;
            }

            public void setOutEdNum(int outEdNum) {
                this.outEdNum = outEdNum;
            }

            public Object getSaleSendTime() {
                return saleSendTime;
            }

            public void setSaleSendTime(Object saleSendTime) {
                this.saleSendTime = saleSendTime;
            }
        }
    }

    public static class OrderStatusBean {
        /**
         * PAY_YES : 2
         * SHIP_ROG : 2
         * ORDER_PAY : 2
         * ORDER_FAILLENDING : 200
         * SHIP_YES : 1
         * SHIP_NO : 0
         * ORDER_ROG : 4
         * ORDER_WAITLENDING : 100
         * ORDER_SHIP : 3
         * ORDER_RISK_CHECK : 9
         * ORDER_NOT_PAY : 0
         * ORDER_CONFIRM : 1
         * ORDER_COMPLETE : 5
         * ORDER_MAINTENANCE : 7
         * PAY_NO : 0
         * PAY_PARTIAL_PAYED : 1
         * ORDER_CANCELLATION : 6
         * SHIP_PARTIAL_CANCEL : 1
         */

        private int PAY_YES;
        private int SHIP_ROG;
        private int ORDER_PAY;
        private int ORDER_FAILLENDING;
        private int SHIP_YES;
        private int SHIP_NO;
        private int ORDER_ROG;
        private int ORDER_WAITLENDING;
        private int ORDER_SHIP;
        private int ORDER_RISK_CHECK;
        private int ORDER_NOT_PAY;
        private int ORDER_CONFIRM;
        private int ORDER_COMPLETE;
        private int ORDER_MAINTENANCE;
        private int PAY_NO;
        private int PAY_PARTIAL_PAYED;
        private int ORDER_CANCELLATION;
        private int SHIP_PARTIAL_CANCEL;

        public int getPAY_YES() {
            return PAY_YES;
        }

        public void setPAY_YES(int PAY_YES) {
            this.PAY_YES = PAY_YES;
        }

        public int getSHIP_ROG() {
            return SHIP_ROG;
        }

        public void setSHIP_ROG(int SHIP_ROG) {
            this.SHIP_ROG = SHIP_ROG;
        }

        public int getORDER_PAY() {
            return ORDER_PAY;
        }

        public void setORDER_PAY(int ORDER_PAY) {
            this.ORDER_PAY = ORDER_PAY;
        }

        public int getORDER_FAILLENDING() {
            return ORDER_FAILLENDING;
        }

        public void setORDER_FAILLENDING(int ORDER_FAILLENDING) {
            this.ORDER_FAILLENDING = ORDER_FAILLENDING;
        }

        public int getSHIP_YES() {
            return SHIP_YES;
        }

        public void setSHIP_YES(int SHIP_YES) {
            this.SHIP_YES = SHIP_YES;
        }

        public int getSHIP_NO() {
            return SHIP_NO;
        }

        public void setSHIP_NO(int SHIP_NO) {
            this.SHIP_NO = SHIP_NO;
        }

        public int getORDER_ROG() {
            return ORDER_ROG;
        }

        public void setORDER_ROG(int ORDER_ROG) {
            this.ORDER_ROG = ORDER_ROG;
        }

        public int getORDER_WAITLENDING() {
            return ORDER_WAITLENDING;
        }

        public void setORDER_WAITLENDING(int ORDER_WAITLENDING) {
            this.ORDER_WAITLENDING = ORDER_WAITLENDING;
        }

        public int getORDER_SHIP() {
            return ORDER_SHIP;
        }

        public void setORDER_SHIP(int ORDER_SHIP) {
            this.ORDER_SHIP = ORDER_SHIP;
        }

        public int getORDER_RISK_CHECK() {
            return ORDER_RISK_CHECK;
        }

        public void setORDER_RISK_CHECK(int ORDER_RISK_CHECK) {
            this.ORDER_RISK_CHECK = ORDER_RISK_CHECK;
        }

        public int getORDER_NOT_PAY() {
            return ORDER_NOT_PAY;
        }

        public void setORDER_NOT_PAY(int ORDER_NOT_PAY) {
            this.ORDER_NOT_PAY = ORDER_NOT_PAY;
        }

        public int getORDER_CONFIRM() {
            return ORDER_CONFIRM;
        }

        public void setORDER_CONFIRM(int ORDER_CONFIRM) {
            this.ORDER_CONFIRM = ORDER_CONFIRM;
        }

        public int getORDER_COMPLETE() {
            return ORDER_COMPLETE;
        }

        public void setORDER_COMPLETE(int ORDER_COMPLETE) {
            this.ORDER_COMPLETE = ORDER_COMPLETE;
        }

        public int getORDER_MAINTENANCE() {
            return ORDER_MAINTENANCE;
        }

        public void setORDER_MAINTENANCE(int ORDER_MAINTENANCE) {
            this.ORDER_MAINTENANCE = ORDER_MAINTENANCE;
        }

        public int getPAY_NO() {
            return PAY_NO;
        }

        public void setPAY_NO(int PAY_NO) {
            this.PAY_NO = PAY_NO;
        }

        public int getPAY_PARTIAL_PAYED() {
            return PAY_PARTIAL_PAYED;
        }

        public void setPAY_PARTIAL_PAYED(int PAY_PARTIAL_PAYED) {
            this.PAY_PARTIAL_PAYED = PAY_PARTIAL_PAYED;
        }

        public int getORDER_CANCELLATION() {
            return ORDER_CANCELLATION;
        }

        public void setORDER_CANCELLATION(int ORDER_CANCELLATION) {
            this.ORDER_CANCELLATION = ORDER_CANCELLATION;
        }

        public int getSHIP_PARTIAL_CANCEL() {
            return SHIP_PARTIAL_CANCEL;
        }

        public void setSHIP_PARTIAL_CANCEL(int SHIP_PARTIAL_CANCEL) {
            this.SHIP_PARTIAL_CANCEL = SHIP_PARTIAL_CANCEL;
        }
    }
}
