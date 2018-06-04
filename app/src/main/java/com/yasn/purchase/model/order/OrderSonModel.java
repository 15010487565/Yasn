package com.yasn.purchase.model.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/5/24.
 */

public class OrderSonModel implements Serializable {

    /**
     * member : {"seePrice":0,"endDate":1602844185,"level_id":10,"regionName":null,"employee_auth":"1","shopName":"洗车","is_inviteStatus":2,"is_wholesaler":0,"lv_id":6,"inWhitelist":0,"memberInProbationStartTime":0,"preOrder":0,"lvName":"单个门店","haveMemberData":1,"member_id":21880,"store_id":0,"uname":"13333333333","is_invite":0,"pointDate":null,"is_inviteCustomer":0,"levelName":"金宝二星","cartCount":13,"memberInProbation":0,"message":null,"provinceId":1,"digital_member":1,"currentTime":1527145643,"regionId":0,"memberInProbationEndTime":0,"member_admin":21880}
     * orderStatus : {"PAY_YES":2,"SHIP_ROG":2,"ORDER_PAY":2,"ORDER_FAILLENDING":200,"SHIP_YES":1,"SHIP_NO":0,"ORDER_ROG":4,"ORDER_WAITLENDING":100,"ORDER_SHIP":3,"ORDER_RISK_CHECK":9,"ORDER_NOT_PAY":0,"ORDER_CONFIRM":1,"ORDER_COMPLETE":5,"ORDER_MAINTENANCE":7,"PAY_NO":0,"PAY_PARTIAL_PAYED":1,"ORDER_CANCELLATION":6,"SHIP_PARTIAL_CANCEL":1}
     * orders : [{"orderId":48541,"sn":"152359559423-1","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":195,"shippingType":"快递","shippingArea":"北京-门头沟区-门头沟区","paymentId":1,"paymentName":"支付宝","paymentType":"alipayDirectPlugin","paymentAccount":null,"paymoney":3403.5,"paydate":1523847647,"createTime":1523595595,"shipName":"23eee222ff","shipAddr":"33","shipZip":"123456","shipEmail":null,"shipMobile":"13333333333","shipTel":null,"goodsAmount":7371,"shippingAmount":1032.5,"orderAmount":8403.5,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":14,"shipRegionid":15,"depotid":8,"adminRemark":null,"needPayMoney":8403.5,"shipNo":null,"addressId":12367,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":1,"parentId":48540,"commission":null,"billStatus":1,"billSn":"21-1","storeName":"雅森自营","initMoney":8403.5,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":1032.5,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45459,"orderId":48541,"goodsId":3724,"productId":5776,"buyNum":0,"num":1,"shipNum":0,"name":"测试","sn":"0000111154278-3","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/23/12//18479021_thumbnail.jpg","store":null,"addon":"[{\"name\":\"容量\",\"value\":\"450ml\"},{\"name\":\"包装规格\",\"value\":\"230g*4罐\"}]","catId":493,"price":11,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false},{"page":null,"pageSize":null,"itemId":45460,"orderId":48541,"goodsId":3764,"productId":5794,"buyNum":0,"num":2,"shipNum":0,"name":"限购1111","sn":"3461-0","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/3/29/13//50065554_thumbnail.jpg","store":null,"addon":"[{\"name\":\"默认\",\"value\":\"默认\"}]","catId":493,"price":3680,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48420,"sn":"152151470178-2","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":188,"paydate":1521527848,"createTime":1521514703,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":188,"shippingAmount":0,"orderAmount":188,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":1,"adminRemark":null,"needPayMoney":188,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":99,"parentId":48418,"commission":null,"billStatus":1,"billSn":"20-99","storeName":"厂家直发","initMoney":188,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":3,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45382,"orderId":48420,"goodsId":1910,"productId":3019,"buyNum":0,"num":1,"shipNum":0,"name":"天惊空气鲨中草药空气净化液400ml装 买即送加湿器和香薰盒","sn":"yspr0001","image":"http://shoptt.yasn.com/static/attachment//store/99/goods/2017/7/17/15//06301312_thumbnail.jpg","store":null,"addon":"[{\"name\":\"包装规格\",\"value\":\"400ml\"}]","catId":574,"price":188,"gainedpoint":0,"state":0,"unit":"瓶","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48419,"sn":"152151470178-1","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":111,"shippingType":"快递","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":270,"paydate":1521515331,"createTime":1521514701,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":270,"shippingAmount":25.2,"orderAmount":295.2,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":8,"adminRemark":null,"needPayMoney":270,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":57,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":1,"parentId":48418,"commission":null,"billStatus":1,"billSn":"20-1","storeName":"雅森自营","initMoney":270,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":3,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45381,"orderId":48419,"goodsId":1907,"productId":3556,"buyNum":0,"num":2,"shipNum":0,"name":"尼罗河BAFA环保丝圈脚垫通用自裁款20mm  1套","sn":"9980015040002","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2017/4/25/10//31539017_thumbnail.jpg","store":null,"addon":"[{\"name\":\"颜色\",\"value\":\"黑紫\"}]","catId":539,"price":135,"gainedpoint":0,"state":0,"unit":"套","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48231,"sn":"151961769754-1","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":3,"paydate":1519618226,"createTime":1519617698,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":3,"shippingAmount":0,"orderAmount":3,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":8,"adminRemark":null,"needPayMoney":3,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":1,"parentId":48230,"commission":null,"billStatus":1,"billSn":"19-1","storeName":"雅森自营","initMoney":3,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45281,"orderId":48231,"goodsId":3679,"productId":5640,"buyNum":0,"num":1,"shipNum":0,"name":"多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规","sn":"745621632852-7","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/1/31/13//45077465_thumbnail.jpg","store":null,"addon":"[{\"name\":\"颜色\",\"value\":\"灰色\"}]","catId":624,"price":3,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48232,"sn":"151961769754-2","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":464,"paydate":1519618302,"createTime":1519617698,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":488,"shippingAmount":0,"orderAmount":488,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":1,"adminRemark":null,"needPayMoney":464,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":99,"parentId":48230,"commission":null,"billStatus":1,"billSn":"19-99","storeName":"厂家直发","initMoney":464,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":24,"deductMoney":24,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45282,"orderId":48232,"goodsId":2019,"productId":3249,"buyNum":0,"num":1,"shipNum":0,"name":"美固车载冰箱 半导体U30  蓝色 30L","sn":"9980019030001","image":"http://shoptt.yasn.com/static/attachment//store/99/goods/2017/4/6/9//27472773_thumbnail.jpg","store":null,"addon":"[{\"name\":\"电器颜色\",\"value\":\"蓝色\"},{\"name\":\"容量\",\"value\":\"30L\"}]","catId":633,"price":488,"gainedpoint":0,"state":0,"unit":"台","goodsType":0,"presentNum":0,"usePoint":24,"deductMoney":24,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48218,"sn":"151944132370-1","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":3,"paydate":1519441418,"createTime":1519441323,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":3,"shippingAmount":0,"orderAmount":3,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":8,"adminRemark":null,"needPayMoney":3,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":1,"parentId":48217,"commission":null,"billStatus":1,"billSn":"19-1","storeName":"雅森自营","initMoney":3,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45274,"orderId":48218,"goodsId":3679,"productId":5640,"buyNum":0,"num":1,"shipNum":0,"name":"多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规格多规","sn":"745621632852-7","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/1/31/13//45077465_thumbnail.jpg","store":null,"addon":"[{\"name\":\"颜色\",\"value\":\"灰色\"}]","catId":624,"price":3,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48219,"sn":"151944132370-2","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":464,"paydate":1519441442,"createTime":1519441323,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":488,"shippingAmount":0,"orderAmount":488,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":1,"adminRemark":null,"needPayMoney":464,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":99,"parentId":48217,"commission":null,"billStatus":1,"billSn":"19-99","storeName":"厂家直发","initMoney":464,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":24,"deductMoney":24,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45275,"orderId":48219,"goodsId":2019,"productId":3249,"buyNum":0,"num":1,"shipNum":0,"name":"美固车载冰箱 半导体U30  蓝色 30L","sn":"9980019030001","image":"http://shoptt.yasn.com/static/attachment//store/99/goods/2017/4/6/9//27472773_thumbnail.jpg","store":null,"addon":"[{\"name\":\"电器颜色\",\"value\":\"蓝色\"},{\"name\":\"容量\",\"value\":\"30L\"}]","catId":633,"price":488,"gainedpoint":0,"state":0,"unit":"台","goodsType":0,"presentNum":0,"usePoint":24,"deductMoney":24,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48184,"sn":"151810358563-1","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":269.4,"paydate":1518103614,"createTime":1518103585,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":269.4,"shippingAmount":0,"orderAmount":269.4,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":1,"adminRemark":null,"needPayMoney":269.4,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":99,"parentId":48183,"commission":null,"billStatus":1,"billSn":"19-99","storeName":"厂家直发","initMoney":269.4,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45254,"orderId":48184,"goodsId":2466,"productId":4167,"buyNum":0,"num":2,"shipNum":0,"name":"玛罗尼 密封胶浆 SL-02 200cc每瓶（3瓶）","sn":"9980106020028","image":"http://shoptt.yasn.com/static/attachment//store/99/goods/2017/5/27/16//06221531_thumbnail.jpg","store":null,"addon":"[{\"name\":\"轮胎\",\"value\":\"200cc/瓶（3瓶）\"}]","catId":560,"price":134.7,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48180,"sn":"151810292056-1","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":134.7,"paydate":1518102965,"createTime":1518102920,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":134.7,"shippingAmount":0,"orderAmount":134.7,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":1,"adminRemark":null,"needPayMoney":134.7,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":99,"parentId":48179,"commission":null,"billStatus":1,"billSn":"19-99","storeName":"厂家直发","initMoney":134.7,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45252,"orderId":48180,"goodsId":2464,"productId":4165,"buyNum":0,"num":1,"shipNum":0,"name":"玛罗尼 外胎热补胶水 TL-200 200cc每瓶（3瓶）","sn":"9980106020027","image":"http://shoptt.yasn.com/static/attachment//store/99/goods/2017/5/27/16//00539261_thumbnail.jpg","store":null,"addon":"[{\"name\":\"轮胎\",\"value\":\"200cc/瓶（3瓶）\"}]","catId":560,"price":134.7,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null},{"orderId":48174,"sn":"151810216322-1","memberId":21880,"status":2,"payStatus":2,"shipStatus":0,"shippingId":0,"shippingType":"","shippingArea":"北京-东城区-东城区","paymentId":13,"paymentName":"转账支付","paymentType":"offline","paymentAccount":null,"paymoney":139.8,"paydate":1518102200,"createTime":1518102163,"shipName":"123","shipAddr":"haha","shipZip":"123456","shipEmail":null,"shipMobile":"18999999999","shipTel":null,"goodsAmount":139.8,"shippingAmount":0,"orderAmount":139.8,"goodsNum":0,"gainedpoint":0,"consumepoint":0,"remark":"","disabled":"0","discount":0,"completeTime":0,"cancelReason":null,"signingTime":0,"theSign":null,"shipProvinceid":1,"shipCityid":2,"shipRegionid":3,"depotid":1,"adminRemark":null,"needPayMoney":139.8,"shipNo":null,"addressId":12254,"logiId":0,"logiName":null,"giftId":0,"activityGift":null,"bonusId":0,"actDiscount":0,"activityPoint":0,"isCancel":0,"storeId":99,"parentId":48173,"commission":null,"billStatus":1,"billSn":"19-99","storeName":"厂家直发","initMoney":139.8,"moreBuyToSend":"[]","manualReason":null,"pushStatus":0,"pushTimes":0,"manualStatus":0,"mergeOrderId":0,"shippingTotal":0,"cancelTime":0,"usePoint":0,"deductMoney":0,"salesName":null,"cancelStatus":0,"cancelDatetime":0,"cancelAuditDatetime":0,"cancelAuditRemark":null,"source":2,"orderType":0,"beforeSaleSendTime":0,"orderFrom":0,"regionIds":"0","createOrderMemberId":21880,"receipt":null,"shopName":null,"orderStatus":"已付款","orderItem":[{"page":null,"pageSize":null,"itemId":45249,"orderId":48174,"goodsId":2456,"productId":4147,"buyNum":0,"num":1,"shipNum":0,"name":"玛罗尼 内胎胶片+胶水22cc R-A S2-A M2-A S-A M-A L3-A S2-B M2","sn":"9980106020009","image":"http://shoptt.yasn.com/static/attachment//store/99/goods/2017/5/27/13//58034008_thumbnail.jpg","store":null,"addon":"[{\"name\":\"轮胎\",\"value\":\"L3-B 160片/盒（2盒）\"}]","catId":560,"price":139.8,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}],"childOrderList":null}]
     */

    private MemberBean member;
    private OrderStatusBean orderStatus;
    private List<OrdersBean> orders;

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

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class MemberBean {
        /**
         * seePrice : 0
         * endDate : 1602844185
         * level_id : 10
         * regionName : null
         * employee_auth : 1
         * shopName : 洗车
         * is_inviteStatus : 2
         * is_wholesaler : 0
         * lv_id : 6
         * inWhitelist : 0
         * memberInProbationStartTime : 0
         * preOrder : 0
         * lvName : 单个门店
         * haveMemberData : 1
         * member_id : 21880
         * store_id : 0
         * uname : 13333333333
         * is_invite : 0
         * pointDate : null
         * is_inviteCustomer : 0
         * levelName : 金宝二星
         * cartCount : 13
         * memberInProbation : 0
         * message : null
         * provinceId : 1
         * digital_member : 1
         * currentTime : 1527145643
         * regionId : 0
         * memberInProbationEndTime : 0
         * member_admin : 21880
         */

        private int seePrice;
        private int endDate;
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
        private Object pointDate;
        private int is_inviteCustomer;
        private String levelName;
        private int cartCount;
        private int memberInProbation;
        private Object message;
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

    public static class OrdersBean {
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
         * shopName : null
         * orderStatus : 已付款
         * orderItem : [{"page":null,"pageSize":null,"itemId":45459,"orderId":48541,"goodsId":3724,"productId":5776,"buyNum":0,"num":1,"shipNum":0,"name":"测试","sn":"0000111154278-3","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/23/12//18479021_thumbnail.jpg","store":null,"addon":"[{\"name\":\"容量\",\"value\":\"450ml\"},{\"name\":\"包装规格\",\"value\":\"230g*4罐\"}]","catId":493,"price":11,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false},{"page":null,"pageSize":null,"itemId":45460,"orderId":48541,"goodsId":3764,"productId":5794,"buyNum":0,"num":2,"shipNum":0,"name":"限购1111","sn":"3461-0","image":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/3/29/13//50065554_thumbnail.jpg","store":null,"addon":"[{\"name\":\"默认\",\"value\":\"默认\"}]","catId":493,"price":3680,"gainedpoint":0,"state":0,"unit":"","goodsType":0,"presentNum":0,"usePoint":0,"deductMoney":0,"outSn":null,"outEdNum":0,"saleSendTime":null,"weight":null,"changeGoodsName":null,"changeGoodsId":null,"activityId":null,"goodsTransfeeCharge":0,"canUsePoint":null,"beforeSale":false}]
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
        private Object receipt;
        private Object shopName;
        private String orderStatus;
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

        public Object getReceipt() {
            return receipt;
        }

        public void setReceipt(Object receipt) {
            this.receipt = receipt;
        }

        public Object getShopName() {
            return shopName;
        }

        public void setShopName(Object shopName) {
            this.shopName = shopName;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
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

        public static class OrderItemBean {
            /**
             * page : null
             * pageSize : null
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
             * weight : null
             * changeGoodsName : null
             * changeGoodsId : null
             * activityId : null
             * goodsTransfeeCharge : 0
             * canUsePoint : null
             * beforeSale : false
             */

            private Object page;
            private Object pageSize;
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
            private Object weight;
            private Object changeGoodsName;
            private Object changeGoodsId;
            private Object activityId;
            private int goodsTransfeeCharge;
            private Object canUsePoint;
            private boolean beforeSale;

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

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public Object getChangeGoodsName() {
                return changeGoodsName;
            }

            public void setChangeGoodsName(Object changeGoodsName) {
                this.changeGoodsName = changeGoodsName;
            }

            public Object getChangeGoodsId() {
                return changeGoodsId;
            }

            public void setChangeGoodsId(Object changeGoodsId) {
                this.changeGoodsId = changeGoodsId;
            }

            public Object getActivityId() {
                return activityId;
            }

            public void setActivityId(Object activityId) {
                this.activityId = activityId;
            }

            public int getGoodsTransfeeCharge() {
                return goodsTransfeeCharge;
            }

            public void setGoodsTransfeeCharge(int goodsTransfeeCharge) {
                this.goodsTransfeeCharge = goodsTransfeeCharge;
            }

            public Object getCanUsePoint() {
                return canUsePoint;
            }

            public void setCanUsePoint(Object canUsePoint) {
                this.canUsePoint = canUsePoint;
            }

            public boolean isBeforeSale() {
                return beforeSale;
            }

            public void setBeforeSale(boolean beforeSale) {
                this.beforeSale = beforeSale;
            }
        }
    }
}
