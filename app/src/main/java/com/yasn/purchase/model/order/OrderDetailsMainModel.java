package com.yasn.purchase.model.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/5/24.
 */

public class OrderDetailsMainModel implements Serializable {

    /**
     * orderDetails : {"orderId":19133,"sn":"148297838747","memberId":8562,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"上海-闵行区-闵行区","paymentId":13,"paymentName":"线下支付","paymentType":"offline","paymentAccount":null,"paymoney":0,"paydate":1482978387,"createTime":1482978387,"shipName":"卢经理","shipAddr":"上海凯斯汽车装饰用品批发市场B区22-25号","shipZip":"201100","shipEmail":null,"shipMobile":"18962412397","shipTel":null,"goodsAmount":93600,"shippingAmount":0,"orderAmount":93600,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":"","signingTime":0,"theSign":null,"shipProvinceid":754,"shipCityid":771,"shipRegionid":772,"depotid":0,"adminRemark":null,"needPayMoney":93600,"shipNo":null,"addressId":5048,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":0,"parentId":null,"commission":null,"billStatus":0,"billSn":null,"storeName":null,"initMoney":93600,"moreBuyToSend":null,"manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":0,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":8562,"receipt":null,"shopName":"喜力汽车用品批发","orderItem":null,"childOrderList":[{"orderId":19134,"sn":"148297838747-1","memberId":8562,"status":5,"payStatus":2,"shipStatus":2,"shippingId":0,"shippingType":"","shippingArea":"上海-闵行区-闵行区","paymentId":13,"paymentName":"线下支付","paymentType":"offline","paymentAccount":null,"paymoney":93600,"paydate":0,"createTime":1482978387,"shipName":"卢经理","shipAddr":"上海凯斯汽车装饰用品批发市场B区22-25号","shipZip":"201100","shipEmail":null,"shipMobile":"18962412397","shipTel":null,"goodsAmount":93600,"shippingAmount":0,"orderAmount":93600,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":1484899307,"cancelReason":null,"signingTime":1484899307,"theSign":"zengmeifang","shipProvinceid":754,"shipCityid":771,"shipRegionid":772,"depotid":0,"adminRemark":null,"needPayMoney":93600,"shipNo":"123456","addressId":5048,"logiId":115,"logiName":"其他","giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":126,"parentId":19133,"commission":null,"billStatus":1,"billSn":"6-126","storeName":"车虎汽车用品旗舰店","initMoney":93600,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":1,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":0,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":8562,"receipt":null,"shopName":"喜力汽车用品批发","orderItem":[{"itemId":21892,"orderId":19134,"goodsId":1122,"productId":1751,"buyNum":0,"num":1200,"shipNum":1200,"name":"车虎防盗器","sn":"78787787878-0","image":"http://caigou.yasn.com:8080/static/attachment//store/126/goods/2016/11/8/18//33249510_thumbnail.jpg","store":null,"addon":"[{\"name\":\"颜色\",\"value\":\"黑色\"}]","catId":552,"price":78,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null}],"childOrderList":null}]}
     * member : {"seePrice":0,"level_id":11,"regionName":null,"employee_auth":"0","shopName":"喜力汽车用品批发","is_inviteStatus":0,"is_wholesaler":0,"lv_id":6,"inWhitelist":0,"memberInProbationStartTime":0,"preOrder":0,"lvName":"单个门店","haveMemberData":1,"member_id":8562,"store_id":0,"uname":"18962412397","is_invite":0,"is_inviteCustomer":0,"levelName":"金宝三星","memberInProbation":0,"provinceId":754,"digital_member":0,"currentTime":1527167390,"regionId":0,"memberInProbationEndTime":0,"member_admin":8562}
     * orderStatus : {"PAY_YES":2,"SHIP_ROG":2,"ORDER_PAY":2,"ORDER_FAILLENDING":200,"SHIP_YES":1,"SHIP_NO":0,"ORDER_ROG":4,"ORDER_WAITLENDING":100,"ORDER_SHIP":3,"ORDER_RISK_CHECK":9,"ORDER_NOT_PAY":0,"ORDER_CONFIRM":1,"ORDER_COMPLETE":5,"ORDER_MAINTENANCE":7,"PAY_NO":0,"PAY_PARTIAL_PAYED":1,"ORDER_CANCELLATION":6,"SHIP_PARTIAL_CANCEL":1}
     */

    private OrderDetailsBean orderDetails;
    private MemberBean member;
    private OrderStatusBean orderStatus;

    public OrderDetailsBean getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailsBean orderDetails) {
        this.orderDetails = orderDetails;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
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
         * orderId : 19133
         * sn : 148297838747
         * memberId : 8562
         * status : 2
         * payStatus : 2
         * shipStatus : 0
         * shippingId : 0
         * shippingType :
         * shippingArea : 上海-闵行区-闵行区
         * paymentId : 13
         * paymentName : 线下支付
         * paymentType : offline
         * paymentAccount : null
         * paymoney : 0.0
         * paydate : 1482978387
         * createTime : 1482978387
         * shipName : 卢经理
         * shipAddr : 上海凯斯汽车装饰用品批发市场B区22-25号
         * shipZip : 201100
         * shipEmail : null
         * shipMobile : 18962412397
         * shipTel : null
         * goodsAmount : 93600.0
         * shippingAmount : 0.0
         * orderAmount : 93600.0
         * goodsNum : 0
         * gainedpoint : 0
         * consumepoint : 0
         * remark :
         * disabled : 0
         * discount : 0.0
         * completeTime : 0
         * cancelReason :
         * signingTime : 0
         * theSign : null
         * shipProvinceid : 754
         * shipCityid : 771
         * shipRegionid : 772
         * depotid : 0
         * adminRemark : null
         * needPayMoney : 93600.0
         * shipNo : null
         * addressId : 5048
         * logiId : 0
         * logiName : null
         * giftId : 0
         * activityGift : null
         * bonusId : 0
         * actDiscount : 0.0
         * activityPoint : 0
         * isCancel : 0
         * storeId : 0
         * parentId : null
         * commission : null
         * billStatus : 0
         * billSn : null
         * storeName : null
         * initMoney : 93600.0
         * moreBuyToSend : null
         * manualReason : null
         * pushStatus : 0
         * pushTimes : 0
         * manualStatus : 0
         * mergeOrderId : 0
         * shippingTotal : 0.0
         * cancelTime : 0
         * usePoint : 0
         * deductMoney : 0.0
         * salesName : null
         * cancelStatus : 0
         * cancelDatetime : 0
         * cancelAuditDatetime : 0
         * cancelAuditRemark : null
         * source : 0
         * orderType : 0
         * beforeSaleSendTime : 0
         * orderFrom : 0
         * regionIds : 0
         * createOrderMemberId : 8562
         * receipt : null
         * shopName : 喜力汽车用品批发
         * orderItem : null
         * childOrderList : [{"orderId":19134,"sn":"148297838747-1","memberId":8562,"status":5,"payStatus":2,"shipStatus":2,"shippingId":0,"shippingType":"","shippingArea":"上海-闵行区-闵行区","paymentId":13,"paymentName":"线下支付","paymentType":"offline","paymentAccount":null,"paymoney":93600,"paydate":0,"createTime":1482978387,"shipName":"卢经理","shipAddr":"上海凯斯汽车装饰用品批发市场B区22-25号","shipZip":"201100","shipEmail":null,"shipMobile":"18962412397","shipTel":null,"goodsAmount":93600,"shippingAmount":0,"orderAmount":93600,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":1484899307,"cancelReason":null,"signingTime":1484899307,"theSign":"zengmeifang","shipProvinceid":754,"shipCityid":771,"shipRegionid":772,"depotid":0,"adminRemark":null,"needPayMoney":93600,"shipNo":"123456","addressId":5048,"logiId":115,"logiName":"其他","giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":126,"parentId":19133,"commission":null,"billStatus":1,"billSn":"6-126","storeName":"车虎汽车用品旗舰店","initMoney":93600,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":1,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":0,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":8562,"receipt":null,"shopName":"喜力汽车用品批发","orderItem":[{"itemId":21892,"orderId":19134,"goodsId":1122,"productId":1751,"buyNum":0,"num":1200,"shipNum":1200,"name":"车虎防盗器","sn":"78787787878-0","image":"http://caigou.yasn.com:8080/static/attachment//store/126/goods/2016/11/8/18//33249510_thumbnail.jpg","store":null,"addon":"[{\"name\":\"颜色\",\"value\":\"黑色\"}]","catId":552,"price":78,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null}],"childOrderList":null}]
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
        private String cancelReason;
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
        private Object parentId;
        private Object commission;
        private int billStatus;
        private Object billSn;
        private Object storeName;
        private double initMoney;
        private Object moreBuyToSend;
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
        private String shopName;
        private Object orderItem;
        private List<ChildOrderListBean> childOrderList;

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

        public String getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(String cancelReason) {
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

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
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

        public Object getBillSn() {
            return billSn;
        }

        public void setBillSn(Object billSn) {
            this.billSn = billSn;
        }

        public Object getStoreName() {
            return storeName;
        }

        public void setStoreName(Object storeName) {
            this.storeName = storeName;
        }

        public double getInitMoney() {
            return initMoney;
        }

        public void setInitMoney(double initMoney) {
            this.initMoney = initMoney;
        }

        public Object getMoreBuyToSend() {
            return moreBuyToSend;
        }

        public void setMoreBuyToSend(Object moreBuyToSend) {
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

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Object getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(Object orderItem) {
            this.orderItem = orderItem;
        }

        public List<ChildOrderListBean> getChildOrderList() {
            return childOrderList;
        }

        public void setChildOrderList(List<ChildOrderListBean> childOrderList) {
            this.childOrderList = childOrderList;
        }


        public static class ChildOrderListBean {

            /**
             * orderId : 48419
             * sn : 152151470178-1
             * memberId : 21880
             * status : 2
             * payStatus : 2
             * shipStatus : 0
             * shippingId : 111
             * shippingType : 快递
             * shippingArea : 北京-东城区-东城区
             * paymentId : 13
             * paymentName : 转账支付
             * paymentType : offline
             * paymentAccount : null
             * paymoney : 270
             * paydate : 1521515331
             * createTime : 1521514701
             * shipName : 123
             * shipAddr : haha
             * shipZip : 123456
             * shipEmail : null
             * shipMobile : 18999999999
             * shipTel : null
             * goodsAmount : 270
             * shippingAmount : 25.2
             * orderAmount : 295.2
             * goodsNum : 0
             * gainedpoint : 0
             * consumepoint : 0
             * remark :
             * disabled : 0
             * discount : 0
             * completeTime : 0
             * cancelReason : null
             * signingTime : 0
             * theSign : null
             * shipProvinceid : 1
             * shipCityid : 2
             * shipRegionid : 3
             * depotid : 8
             * adminRemark : null
             * needPayMoney : 270
             * shipNo : null
             * addressId : 12254
             * logiId : 0
             * logiName : null
             * giftId : 57
             * activityGift : {"page":null,"pageSize":null,"giftId":57,"giftName":"比方说","giftPrice":100,"giftImg":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/8/19//59243102.jpg","giftType":1,"actualStore":100,"enableStore":99,"createTime":1521514182,"goodsId":3721,"disabled":0}
             * bonusId : 0
             * actDiscount : 0
             * activityPoint : 0
             * isCancel : 0
             * storeId : 1
             * parentId : 48418
             * commission : null
             * billStatus : 1
             * billSn : 20-1
             * storeName : 雅森自营
             * initMoney : 270
             * moreBuyToSend : []
             * manualReason : null
             * pushStatus : 0
             * pushTimes : 0
             * manualStatus : 0
             * mergeOrderId : 0
             * shippingTotal : 0
             * cancelTime : 0
             * usePoint : 0
             * deductMoney : 0
             * salesName : null
             * cancelStatus : 0
             * cancelDatetime : 0
             * cancelAuditDatetime : 0
             * cancelAuditRemark : null
             * source : 3
             * orderType : 0
             * beforeSaleSendTime : 0
             * orderFrom : 0
             * regionIds : 0
             * createOrderMemberId : 21880
             * receipt : null
             * shopName : 洗车
             * orderItem : [{"itemId":45381,"orderId":48419,"goodsId":1907,"productId":3556,"buyNum":0,"num":2,"shipNum":0,"name":"尼罗河BAFA环保丝圈脚垫通用自裁款20mm 1套","sn":"9980015040002","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2017/4/25/10//31539017_thumbnail.jpg","store":null,"addon":"[name:颜色,value:黑紫]","catId":539,"price":135,"gainedpoint":0,"state":0,"unit":"套","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null}]
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
            private int paymoney;
            private int paydate;
            private int createTime;
            private String shipName;
            private String shipAddr;
            private String shipZip;
            private Object shipEmail;
            private String shipMobile;
            private Object shipTel;
            private int goodsAmount;
            private double shippingAmount;
            private double orderAmount;
            private int goodsNum;
            private int gainedpoint;
            private int consumepoint;
            private String remark;
            private String disabled;
            private int discount;
            private int completeTime;
            private Object cancelReason;
            private int signingTime;
            private Object theSign;
            private int shipProvinceid;
            private int shipCityid;
            private int shipRegionid;
            private int depotid;
            private Object adminRemark;
            private int needPayMoney;
            private Object shipNo;
            private int addressId;
            private int logiId;
            private Object logiName;
            private int giftId;
            private ActivityGiftBean activityGift;
            private int bonusId;
            private int actDiscount;
            private int activityPoint;
            private int isCancel;
            private int storeId;
            private int parentId;
            private Object commission;
            private int billStatus;
            private String billSn;
            private String storeName;
            private int initMoney;
            private String moreBuyToSend;
            private Object manualReason;
            private int pushStatus;
            private int pushTimes;
            private int manualStatus;
            private int mergeOrderId;
            private int shippingTotal;
            private int cancelTime;
            private int usePoint;
            private int deductMoney;
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
            private ReceiptBean receipt;
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

            public int getPaymoney() {
                return paymoney;
            }

            public void setPaymoney(int paymoney) {
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

            public int getGoodsAmount() {
                return goodsAmount;
            }

            public void setGoodsAmount(int goodsAmount) {
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

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
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

            public int getNeedPayMoney() {
                return needPayMoney;
            }

            public void setNeedPayMoney(int needPayMoney) {
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

            public ActivityGiftBean getActivityGift() {
                return activityGift;
            }

            public void setActivityGift(ActivityGiftBean activityGift) {
                this.activityGift = activityGift;
            }

            public int getBonusId() {
                return bonusId;
            }

            public void setBonusId(int bonusId) {
                this.bonusId = bonusId;
            }

            public int getActDiscount() {
                return actDiscount;
            }

            public void setActDiscount(int actDiscount) {
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

            public int getInitMoney() {
                return initMoney;
            }

            public void setInitMoney(int initMoney) {
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

            public int getShippingTotal() {
                return shippingTotal;
            }

            public void setShippingTotal(int shippingTotal) {
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

            public int getDeductMoney() {
                return deductMoney;
            }

            public void setDeductMoney(int deductMoney) {
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

            public ReceiptBean getReceipt() {
                return receipt;
            }

            public void setReceipt(ReceiptBean receipt) {
                this.receipt = receipt;
            }

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

            public static class ReceiptBean {

                /**
                 * title : 单位名哈哈
                 * invoiceNum : 识别号哈哈
                 * receiptStatus : 2
                 */

                private String title;
                private String invoiceNum;
                private int receiptStatus;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getInvoiceNum() {
                    return invoiceNum;
                }

                public void setInvoiceNum(String invoiceNum) {
                    this.invoiceNum = invoiceNum;
                }

                public int getReceiptStatus() {
                    return receiptStatus;
                }

                public void setReceiptStatus(int receiptStatus) {
                    this.receiptStatus = receiptStatus;
                }
            }

            public static class ActivityGiftBean {
                /**
                 * page : null
                 * pageSize : null
                 * giftId : 57
                 * giftName : 比方说
                 * giftPrice : 100
                 * giftImg : http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/8/19//59243102.jpg
                 * giftType : 1
                 * actualStore : 100
                 * enableStore : 99
                 * createTime : 1521514182
                 * goodsId : 3721
                 * disabled : 0
                 */

                private Object page;
                private Object pageSize;
                private int giftId;
                private String giftName;
                private int giftPrice;
                private String giftImg;
                private int giftType;
                private int actualStore;
                private int enableStore;
                private int createTime;
                private int goodsId;
                private int disabled;

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

                public int getGiftId() {
                    return giftId;
                }

                public void setGiftId(int giftId) {
                    this.giftId = giftId;
                }

                public String getGiftName() {
                    return giftName;
                }

                public void setGiftName(String giftName) {
                    this.giftName = giftName;
                }

                public int getGiftPrice() {
                    return giftPrice;
                }

                public void setGiftPrice(int giftPrice) {
                    this.giftPrice = giftPrice;
                }

                public String getGiftImg() {
                    return giftImg;
                }

                public void setGiftImg(String giftImg) {
                    this.giftImg = giftImg;
                }

                public int getGiftType() {
                    return giftType;
                }

                public void setGiftType(int giftType) {
                    this.giftType = giftType;
                }

                public int getActualStore() {
                    return actualStore;
                }

                public void setActualStore(int actualStore) {
                    this.actualStore = actualStore;
                }

                public int getEnableStore() {
                    return enableStore;
                }

                public void setEnableStore(int enableStore) {
                    this.enableStore = enableStore;
                }

                public int getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(int createTime) {
                    this.createTime = createTime;
                }

                public int getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(int goodsId) {
                    this.goodsId = goodsId;
                }

                public int getDisabled() {
                    return disabled;
                }

                public void setDisabled(int disabled) {
                    this.disabled = disabled;
                }
            }

            public static class OrderItemBean {
                /**
                 * itemId : 45381
                 * orderId : 48419
                 * goodsId : 1907
                 * productId : 3556
                 * buyNum : 0
                 * num : 2
                 * shipNum : 0
                 * name : 尼罗河BAFA环保丝圈脚垫通用自裁款20mm 1套
                 * sn : 9980015040002
                 * image : http://shoptt.yasn.com/static/attachment//store/1/goods/2017/4/25/10//31539017_thumbnail.jpg
                 * store : null
                 * addon : [name:颜色,value:黑紫]
                 * catId : 539
                 * price : 135
                 * gainedpoint : 0
                 * state : 0
                 * unit : 套
                 * goodsType : 0
                 * presentNum : 0
                 * usePoint : 0
                 * deductMoney : 0
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
                private int price;
                private int gainedpoint;
                private int state;
                private String unit;
                private int goodsType;
                private int presentNum;
                private int usePoint;
                private int deductMoney;
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

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
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

                public int getDeductMoney() {
                    return deductMoney;
                }

                public void setDeductMoney(int deductMoney) {
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
    }

    public static class MemberBean {
        /**
         * seePrice : 0
         * level_id : 11
         * regionName : null
         * employee_auth : 0
         * shopName : 喜力汽车用品批发
         * is_inviteStatus : 0
         * is_wholesaler : 0
         * lv_id : 6
         * inWhitelist : 0
         * memberInProbationStartTime : 0
         * preOrder : 0
         * lvName : 单个门店
         * haveMemberData : 1
         * member_id : 8562
         * store_id : 0
         * uname : 18962412397
         * is_invite : 0
         * is_inviteCustomer : 0
         * levelName : 金宝三星
         * memberInProbation : 0
         * provinceId : 754
         * digital_member : 0
         * currentTime : 1527167390
         * regionId : 0
         * memberInProbationEndTime : 0
         * member_admin : 8562
         */

        private int seePrice;
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
        private int is_inviteCustomer;
        private String levelName;
        private int memberInProbation;
        private int provinceId;
        private int digital_member;
        private int currentTime;
        private int regionId;
        private int memberInProbationEndTime;
        private int member_admin;

        public int getSeePrice() {
            return seePrice;
        }

        public void setSeePrice(int seePrice) {
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
