package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.adapter.OrderDetailsAdapter;
import com.yasn.purchase.adapter.OrderDetailsGoodsAdapter;
import com.yasn.purchase.adapter.SetSimpleAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.func.CallService;
import com.yasn.purchase.help.SobotUtil;
import com.yasn.purchase.model.SobotModel;
import com.yasn.purchase.model.order.OrderDetailsGiftModel;
import com.yasn.purchase.model.order.OrderDetailsMainModel;
import com.yasn.purchase.model.order.OrderDetailsPayModel;
import com.yasn.purchase.model.order.OrderDetailsSonModel;
import com.yasn.purchase.model.order.OrderGoodsContentModel;
import com.yasn.purchase.model.order.OrderHeaderModel;
import com.yasn.purchase.model.order.OrderShopNameModel;
import com.yasn.purchase.utils.AlignedTextUtils;
import com.yasn.purchase.view.RecyclerViewDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.help.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.utils.ToastUtil;

/***
 * 订单详情
 * 2018年6月6日 09:24:36
 */
public class OrderDetailsActivity extends SimpleTopbarActivity
        implements OrderDetailsGoodsAdapter.OnRcOrderDetailsClickListener, AdapterView.OnItemClickListener {

    private static Class<?> rightFuncArray[] = {CallService.class};
    private RecyclerView rcOrderDetails, rcOrderGoodsDetails;
    private OrderDetailsAdapter adapter;
    private OrderDetailsGoodsAdapter goodsAdapter;
    //下单时间
    private TextView tvOrderTime;
    //发票类型
    private TextView tvBillType;
    private LinearLayout llBillType;
    //收货人
    private TextView tvConsig, tvConsigName;
    //收货地址
    private TextView tvConsigAddress;
    //留言
    private LinearLayout llRemark;
    private TextView tvRemark;
    //底部支付信息
    private LinearLayout llOrderDetailsBottom;
    //主订单还是主订单
    private int isMainOrder;
    //底部提示
    TextView tvBottomLeft, tvBottomRight;
    //主订单ID
    private int orderIdMain;

    @Override
    protected Object getTopbarTitle() {
        return R.string.orderdetails;
    }

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    //客服
    SobotModel sobotModel;

    public void startSobot() {
        SobotUtil.startSobot(this, sobotModel);
    }

    public void setSobotModel(String name, String advert, String imageUrl, String money) {
        //咨询内容标题，必填
        sobotModel.setSobotGoodsTitle("订单编号：" + name);
        //描述，选填
        if (!TextUtils.isEmpty(advert)) {
            sobotModel.setSobotGoodsDescribe("下单时间：" + advert);
        }
        //咨询内容图片，选填 但必须是图片地址
        sobotModel.setSobotGoodsImgUrl(imageUrl);
//        Log.e("TAG_客服", "money=" + money);
        sobotModel.setSobotGoodsLable(money);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        isMainOrder = getIntent().getIntExtra("isMainOrder", 0);
        //rc线
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.line_gray));
        //顶部订单详情
        rcOrderDetails = (RecyclerView) findViewById(R.id.rc_OrderDetails);
        rcOrderDetails.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderDetailsAdapter(this);
        rcOrderDetails.setAdapter(adapter);
        rcOrderDetails.addItemDecoration(recyclerViewDecoration);
        tvOrderTime = (TextView) findViewById(R.id.tv_orderTime);
        tvBillType = (TextView) findViewById(R.id.tv_billType);
        llBillType = (LinearLayout) findViewById(R.id.ll_BillType);
        llBillType.setOnClickListener(this);
        //收货人
        tvConsig = (TextView) findViewById(R.id.tv_consig);
        SpannableStringBuilder tradepriceString = AlignedTextUtils.justifyString("收货人", 4);
        tradepriceString.append("：");
        tvConsig.setText(tradepriceString);

        tvConsigName = (TextView) findViewById(R.id.tv_consigName);
        tvConsigAddress = (TextView) findViewById(R.id.tv_consigAddress);
        //商品详情
        rcOrderGoodsDetails = (RecyclerView) findViewById(R.id.rc_OrderGoodsDetails);
        rcOrderGoodsDetails.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter = new OrderDetailsGoodsAdapter(this, isMainOrder);
        goodsAdapter.setOnItemClickListener(this);
        rcOrderGoodsDetails.setAdapter(goodsAdapter);
        rcOrderGoodsDetails.addItemDecoration(recyclerViewDecoration);
        //留言
        llRemark = (LinearLayout) findViewById(R.id.ll_remark);
        tvRemark = (TextView) findViewById(R.id.tv_remark);
        //底部支付信息
        llOrderDetailsBottom = (LinearLayout) findViewById(R.id.ll_OrderDetailsBottom);
        llOrderDetailsBottom.setVisibility(View.GONE);
        //查看物流
        tvBottomLeft = (TextView) findViewById(R.id.tv_OrderDetailsBottomLeft);
        tvBottomLeft.setOnClickListener(this);
        //右侧按钮
        tvBottomRight = (TextView) findViewById(R.id.tv_OrderDetailsBottomRight);
        tvBottomRight.setOnClickListener(this);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        token = SharePrefHelper.getInstance(this).getSpString("token");
        resetToken = SharePrefHelper.getInstance(this).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(this).getSpString("resetTokenTime");
        getOrderDetails();
    }

    private void getOrderDetails() {
        int orderId = getIntent().getIntExtra("orderId", 0);
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        params.put("orderId", String.valueOf(orderId));
        okHttpGet(100, Config.ORDERDETAILS, params);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.tv_OrderDetailsBottomLeft://底部左侧查看物流
                String tvBottomLeftString = tvBottomLeft.getText().toString();
                if ("查看物流".equals(tvBottomLeftString)) {
                    queryExpress(orderIdMain);
                } else if ("取消订单".equals(tvBottomLeftString)) {
                    showCancelOrderDialog(String.valueOf(orderIdMain));
                }
                break;
            case R.id.tv_OrderDetailsBottomRight://底部右侧
                String tvBottomRightString = tvBottomRight.getText().toString();
                if ("提醒发货".equals(tvBottomRightString)) {//提醒物流=1；提醒发货=2
                    remindDialog(2,orderIdMain);
                } else if ("确认收货".equals(tvBottomRightString)) {
                    confirmReapDialog(orderIdMain);
                } else if ("转账支付".equals(tvBottomRightString)) {
                    startActivity(new Intent(this,TransAccPayActivity.class));
                }
                break;
            case R.id.iv_cancelOrder:
//                Log.e("TAG_取消订单dialog", "关闭");
                cancelOrderDialog.dismiss();
                break;
            case R.id.iv_dismissReminOrder://提醒物流和提醒发货

                showRemindContentDialog.dismiss();
                break;
            case R.id.tv_ReminOrderOk://提醒物流和提醒发货
                showRemindContentDialog.dismiss();
                break;
            case R.id.tv_cancelOrderOk:
//                Log.e("TAG_取消订单dialog", "确定");
                Map<String, Object> params = new HashMap<String, Object>();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                }
                //主订单id
                params.put("orderIdMain", String.valueOf(orderIdMain));
                params.put("reason", reason);
                okHttpGet(101, Config.ORDERCANCEL, params);
                break;
            case R.id.ll_BillType://发票类型
                String tvBillTypeString = tvBillType.getText().toString();
                if (!TextUtils.isEmpty(tvBillTypeString) && !"无".equals(tvBillTypeString)) {
                    showPopwindow();
                }
                break;
        }
    }

    private void queryExpress(int orderId) {
        Intent intent = new Intent(this,OrderExpressActivity.class);
        intent.putExtra("orderId",orderId);
        startActivity(intent);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
//                    Log.e("TAG_isMainOrder", "isMainOrder=" + isMainOrder);
                    sobotModel = new SobotModel();
                    if (isMainOrder == 1) {//主订单
                        mainOrderDetails(returnData);
                    } else if (isMainOrder == 2) {//子订单
                        sonOrderDetails(returnData, 2);
                    } else if (isMainOrder == 3) {//主订单
                        sonOrderDetails(returnData, 3);
                    }
//                    Log.e("TAG_statusTop", "statusTop=" + statusTop + ";paymentName=" + paymentName);
                    switch (statusTop) {
                        case 0:
//                        headerHolder.tvOrderPayType.setText("");
                            break;
                        case 1://待付款
                            tvBottomRight.setText("确认付款");
                            break;
                        case 2://已付款
                            if ("转账支付".equals(paymentName)) {
                                tvBottomRight.setText("转账支付");
                            } else {
                                tvBottomRight.setText("提醒发货");
                            }
                            tvBottomLeft.setVisibility(View.GONE);
                            break;
                        case 3://已发货
                            break;
                        case 4://已收货
                            break;
                        case 5://已完成
                            break;
                        case 6://已取消
                            break;
                        case 7://交易完成申请售后
                            break;
                        case 8://待人工退单
                            break;
                        case 9://风控审核中
                            break;
                        case 100://已确认
                            break;
                        case 200://已确认
                            break;
                    }
                } else {
                    ToastUtil.showToast(returnMsg);
                }

                break;
            case 101://取消订单
                if (returnCode == 200) {
                    tvBottomLeft.setText("正在审核");
                    if (cancelOrderDialog != null && cancelOrderDialog.isShowing()) {
                        cancelOrderDialog.dismiss();
                    }
                }
                ToastUtil.showToast(returnMsg);
                break;
            case 102://提醒发货
                if (returnCode == 200) {
                    try {
                        JSONObject object = new JSONObject(returnData);
                        JSONObject hurry = object.getJSONObject("hurry");
                        long lastTime = hurry.optLong("lastTime");
                        int type = hurry.optInt("type");
                        if (lastTime > 0){
                            String lastTimeString = HelpUtils.getDateToString(lastTime);
                            long now = hurry.optLong("now");
                            String nowString = HelpUtils.getDateToString(now);
                            if (lastTimeString.equals(nowString)){
                                showRemindContentDialog(3);
                            }else {
                                showRemindContentDialog(type);
                            }
                        }else {
                            showRemindContentDialog(type);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 103://确认收货
                if (returnCode == 200) {
                    getOrderDetails();
                    ToastUtil.showToast(returnMsg);
                }
                break;

        }
    }

    private void sonOrderDetails(String returnData, int type) {
        OrderDetailsSonModel orderDetailsSonModel = JSON.parseObject(returnData, OrderDetailsSonModel.class);
        if (orderDetailsSonModel != null && !"".equals(orderDetailsSonModel)) {
            OrderDetailsSonModel.OrderDetailsBean orderDetailsSon = orderDetailsSonModel.getOrderDetails();
            if (orderDetailsSon != null && !"".equals(orderDetailsSon)) {
                statusTop = orderDetailsSon.getStatus();
                //主订单ID
                orderIdMain = orderDetailsSon.getOrderId();
                //下单时间
                int createTime = orderDetailsSon.getCreateTime();
                String dateToString1 = HelpUtils.getDateToString1(createTime);
                tvOrderTime.setText(dateToString1);
                //发票类型
//                OrderDetailsSonModel.OrderDetailsBean.ReceiptBean receipt = orderDetailsSon.getReceipt();
//                if (receipt == null || "".equals(receipt)) {
                tvBillType.setText("无");
//                } else {
//                    int receiptStatus = receipt.getReceiptStatus();
//                    //发票类型，2：普通发票，3为专用发票
//                    if (receiptStatus == 2) {
//                        tvBillType.setText("普通发票");
//                    } else if (receiptStatus == 2) {
//                        tvBillType.setText("专用发票");
//                    } else {
//                        tvBillType.setText("无");
//                    }
//                }
                //收货人
                String shipName = orderDetailsSon.getShipName();
                String shipMobile = orderDetailsSon.getShipMobile();
                if (TextUtils.isEmpty(shipName)) {
                    tvConsigName.setText(shipMobile);
                } else {
                    tvConsigName.setText(shipName + "\t\t\t\t" + shipMobile);
                }
                //收货地址
                String shippingArea = orderDetailsSon.getShippingArea();
                String shipAddr = orderDetailsSon.getShipAddr();
                tvConsigAddress.setText(shipAddr == null ? shippingArea : shippingArea + "-" + shipAddr);
                //订单编号
                String sn = orderDetailsSon.getSn();
                OrderHeaderModel orderHeaderModel = new OrderHeaderModel();
                orderHeaderModel.setOrderCode(sn);
                // 订单状态
                int status = orderDetailsSon.getStatus();
                orderHeaderModel.setStatus(status);
                // 订单支付状态
                int payStatus = orderDetailsSon.getPayStatus();
                orderHeaderModel.setPayStatus(payStatus);
                // 订单发货状态
                int shipStatus = orderDetailsSon.getShipStatus();
                orderHeaderModel.setShipStatus(shipStatus);
                orderDetailsList.add(orderHeaderModel);
                //店铺名称
                String storeName = orderDetailsSon.getStoreName();
                OrderShopNameModel orderShopNameModel = new OrderShopNameModel();
                orderShopNameModel.setShopName(storeName);
                orderDetailsList.add(orderShopNameModel);

                List<OrderDetailsSonModel.OrderDetailsBean.OrderItemBean> orderItem = orderDetailsSon.getOrderItem();
                if (orderItem != null && orderItem.size() > 0) {
                    //子订单详情
                    sonOrderDetailsMethod(orderItem, type);

//                    String paymentType = orderDetailsSon.getPaymentType();
//                    int status = orderDetailsSon.getStatus();
                    OrderDetailsSonModel.OrderStatusBean orderStatus = orderDetailsSonModel.getOrderStatus();
                    if (orderStatus != null && !"".equals(orderStatus)) {
                        int order_not_pay = orderStatus.getORDER_NOT_PAY();
                        int order_confirm = orderStatus.getORDER_CONFIRM();
                        String paymentType = orderDetailsSon.getPaymentType();
                        if (!"offline".equals(paymentType) && (status == order_not_pay || status == order_confirm)) {
                            int isCancel = orderDetailsSon.getIsCancel();
                            if (isCancel == 0) {
                                tvBottomLeft.setText("取消订单");
                            } else {
                                tvBottomLeft.setText("正在审核");
                            }

                        }
                    }
                    llOrderDetailsBottom.setVisibility(View.VISIBLE);
                }
                //支付信息
                OrderDetailsPayModel payModel = new OrderDetailsPayModel();
                //商品总额
                double goodsAmount = orderDetailsSon.getGoodsAmount();
                payModel.setGoodsAmount("￥" + String.format("%.2f", goodsAmount));
                //积分抵现
                double deductMoney = orderDetailsSon.getDeductMoney();
                payModel.setDeductMoney("￥" + String.format("%.2f", deductMoney));
                //运费总额
                double shippingTotal1 = orderDetailsSon.getShippingTotal();
                payModel.setShippingTotal("￥" + String.format("%.2f", shippingTotal1));
                //小计
                double needPayMoney = orderDetailsSon.getNeedPayMoney();
                payModel.setNeedPayMoney("￥" + String.format("%.2f", needPayMoney));
                // 订单状态
                payModel.setStatus(status);
                //订单号
                int orderId = orderDetailsSon.getOrderId();
                payModel.setOrderId(orderId);
                orderDetailsList.add(payModel);
                //留言
                String remark = orderDetailsSon.getRemark();
                tvRemark.setText(TextUtils.isEmpty(remark) ? "无" : remark);
                //主订单顶部信息
                String paymentType = orderDetailsSon.getPaymentType();
                if (("offline".equals(paymentType) || status == 1) && type == 3) {
                    List<Map<String, String>> list = new ArrayList<>();

                    paymentName = orderDetailsSon.getPaymentName();
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("keyName", "付款方式");
                    map1.put("付款方式", TextUtils.isEmpty(paymentName) ? "无" : paymentName);
                    list.add(map1);

                    Map<String, String> map2 = new HashMap<>();
                    map2.put("keyName", "积分抵现");
                    map2.put("积分抵现", "￥" + String.format("%.2f", deductMoney));
                    list.add(map2);
                    Map<String, String> map3 = new HashMap<>();
                    double shippingTotal = orderDetailsSon.getShippingTotal();
                    map3.put("keyName", "运费总额");
                    map3.put("运费总额", "￥" + String.format("%.2f", shippingTotal));
                    list.add(map3);
                    Map<String, String> map4 = new HashMap<>();
                    double orderAmount = orderDetailsSon.getOrderAmount();
                    map4.put("keyName", "商品总额");
                    map4.put("商品总额", "￥" + String.format("%.2f", orderAmount));
                    list.add(map4);
                    Map<String, String> map5 = new HashMap<>();
                    double paymoney = orderDetailsSon.getPaymoney();
                    map5.put("keyName", "订单总额");
                    map5.put("订单总额", "￥" + String.format("%.2f", paymoney));
                    list.add(map5);
                    adapter.setData(list);
                }
                //客服数据
                if (orderItem != null && orderItem.size() > 0) {
                    OrderDetailsSonModel.OrderDetailsBean.OrderItemBean orderItemBean = orderItem.get(0);
                    String image = orderItemBean.getImage();
                    setSobotModel(orderDetailsSon.getSn(), dateToString1, image, String.format("%.2f", needPayMoney));
                } else {
                    setSobotModel(orderDetailsSon.getSn(), dateToString1, null, String.format("%.2f", needPayMoney));
                }
            }
        }
    }

    //子订单详情
    private void sonOrderDetailsMethod(List<OrderDetailsSonModel.OrderDetailsBean.OrderItemBean> orderItem, int type) {
        for (int i = 0, j = orderItem.size(); i < j; i++) {
            OrderDetailsSonModel.OrderDetailsBean.OrderItemBean orderItemBean1 = orderItem.get(i);

            OrderGoodsContentModel orderGoodsContentModel = new OrderGoodsContentModel();
            //商品图片
            String image = orderItemBean1.getImage();
            orderGoodsContentModel.setImage(image);
            //商品名称
            String name = orderItemBean1.getName();
            orderGoodsContentModel.setName(name);
            if (type == 2) {
                //规格
                String addon = orderItemBean1.getAddon();
                if (!TextUtils.isEmpty(addon)) {
                    try {
                        addon.replaceFirst("\"", " ");
                        addon = addon.substring(0, addon.length());
                        StringBuffer sb = new StringBuffer("\"addon\":");
                        sb.append(addon);
                        Log.e("TAG_sb", sb.toString());
                        JSONObject object = new JSONObject("{" + sb.toString() + "}");
                        JSONArray addonList = object.getJSONArray("addon");
                        List orderGoodsValueList = new ArrayList();
                        for (int k = 0, l = addonList.length(); k < l; k++) {
                            JSONObject jsonObject = addonList.getJSONObject(k);
                            String value = jsonObject.optString("value");
                            OrderGoodsContentModel.OrderGoodsValueBean valueBean = new OrderGoodsContentModel.OrderGoodsValueBean();
                            valueBean.setValue(value);
                            orderGoodsValueList.add(valueBean);
                        }
                        orderGoodsContentModel.setList(orderGoodsValueList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //商品数量
            int num = orderItemBean1.getNum();
            orderGoodsContentModel.setNum(num);
            //商品价格
            double price = orderItemBean1.getPrice();
            orderGoodsContentModel.setPrice(String.format("%.2f", price));
            //商品信息
            orderDetailsList.add(orderGoodsContentModel);

        }
        goodsAdapter.setData(orderDetailsList);
    }

    //根据总订单状态判断底部为确认发货or确认付款
    int statusTop;
    //主订单支付方式
    String paymentName;

    private void mainOrderDetails(String returnData) {
        OrderDetailsMainModel orderDetailsModel = JSON.parseObject(returnData, OrderDetailsMainModel.class);
        if (orderDetailsModel != null && !"".equals(orderDetailsModel)) {
            OrderDetailsMainModel.OrderDetailsBean orderDetails = orderDetailsModel.getOrderDetails();
            if (orderDetails != null && !"".equals(orderDetails)) {
                statusTop = orderDetails.getStatus();
                //主订单ID
                orderIdMain = orderDetails.getOrderId();
                //下单时间
                int createTime = orderDetails.getCreateTime();
                String dateToString1 = HelpUtils.getDateToString1(createTime);
                tvOrderTime.setText(dateToString1);

                //收货人
                String shipName = orderDetails.getShipName();
                String shipMobile = orderDetails.getShipMobile();
                if (TextUtils.isEmpty(shipName)) {
                    tvConsigName.setText(shipMobile);
                } else {
                    tvConsigName.setText(shipName + "\t\t\t\t" + shipMobile);
                }
                //收货地址
                String shippingArea = orderDetails.getShippingArea();
                String shipAddr = orderDetails.getShipAddr();
                tvConsigAddress.setText(shipAddr == null ? shippingArea : shippingArea + "-" + shipAddr);
                List<OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean> childOrderList = orderDetails.getChildOrderList();
                if (childOrderList != null && childOrderList.size() > 0) {
                    //客服数据
                    double needPaymoneySobot = orderDetails.getNeedPayMoney();
//                    Log.e("TAG_客服", "needPaymoneySobot=" + needPaymoneySobot);
                    OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean childOrderListBean = childOrderList.get(0);
                    List<OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean.OrderItemBean> orderItem = childOrderListBean.getOrderItem();
                    if (orderItem != null && orderItem.size() > 0) {
                        OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean.OrderItemBean orderItemBean = orderItem.get(0);
                        String image = orderItemBean.getImage();
                        setSobotModel(orderDetails.getSn(), dateToString1, image, String.format("%.2f", needPaymoneySobot));
                    } else {
                        setSobotModel(orderDetails.getSn(), dateToString1, null, String.format("%.2f", needPaymoneySobot));
                    }
                    //主订单详情
                    orderDetailsMain(childOrderList);
                    //主订单顶部信息
                    String paymentType = orderDetails.getPaymentType();
                    int status = orderDetails.getStatus();
                    if ("offline".equals(paymentType) || status == 1) {
                        List<Map<String, String>> list = new ArrayList<>();

                        paymentName = orderDetails.getPaymentName();
                        Map<String, String> map1 = new HashMap<>();
                        map1.put("keyName", "付款方式");
                        map1.put("付款方式", TextUtils.isEmpty(paymentName) ? "无" : paymentName);
                        list.add(map1);

                        Map<String, String> map2 = new HashMap<>();
                        double deductMoney = orderDetails.getDeductMoney();
                        map2.put("keyName", "积分抵现");
                        map2.put("积分抵现", "￥" + String.format("%.2f", deductMoney));
                        list.add(map2);
                        Map<String, String> map3 = new HashMap<>();
                        double shippingTotal = orderDetails.getShippingTotal();
                        map3.put("keyName", "运费总额");
                        map3.put("运费总额", "￥" + String.format("%.2f", shippingTotal));
                        list.add(map3);
                        Map<String, String> map4 = new HashMap<>();
                        double goodsAmount = orderDetails.getGoodsAmount();
                        map4.put("keyName", "商品总额");
                        map4.put("商品总额", "￥" + String.format("%.2f", goodsAmount));
                        list.add(map4);
                        Map<String, String> map5 = new HashMap<>();
                        double needPaymoney = orderDetails.getNeedPayMoney();
                        map5.put("keyName", "订单总额");
                        map5.put("订单总额", "￥" + String.format("%.2f", needPaymoney));
                        list.add(map5);
                        adapter.setData(list);
                    }
                    OrderDetailsMainModel.OrderStatusBean orderStatus = orderDetailsModel.getOrderStatus();
                    if (orderStatus != null && !"".equals(orderStatus)) {
                        int order_not_pay = orderStatus.getORDER_NOT_PAY();
                        int order_confirm = orderStatus.getORDER_CONFIRM();
                        if (!"offline".equals(paymentType) && (status == order_not_pay || status == order_confirm)) {
                            int isCancel = orderDetails.getIsCancel();
                            if (isCancel == 0) {
                                tvBottomLeft.setText("取消订单");
                            } else {
                                tvBottomLeft.setText("正在审核");
                            }
                            llOrderDetailsBottom.setVisibility(View.VISIBLE);
                        } else {
                            llOrderDetailsBottom.setVisibility(View.GONE);
                        }
                    } else {
                        llOrderDetailsBottom.setVisibility(View.GONE);
                    }
                }
                //留言
                String remark = orderDetails.getRemark();
                tvRemark.setText(TextUtils.isEmpty(remark) ? "无" : remark);

            } else {
                ToastUtil.showToast("未获取到订单详情!");
            }
        }
    }

    //主订单详情
    private List<Object> orderDetailsList = new ArrayList<>();
    //发票类型
    OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean.ReceiptBean receipt;

    private void orderDetailsMain(List<OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean> childOrderList) {
        for (int i = 0, j = childOrderList.size(); i < j; i++) {
            OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean childOrderListBean = childOrderList.get(i);
            //订单编号
            String sn = childOrderListBean.getSn();
            OrderHeaderModel orderHeaderModel = new OrderHeaderModel();
            orderHeaderModel.setOrderCode(sn);
            // 订单状态
            int status = childOrderListBean.getStatus();
            orderHeaderModel.setStatus(status);
            // 订单支付状态
            int payStatus = childOrderListBean.getPayStatus();
            orderHeaderModel.setPayStatus(payStatus);
            // 订单发货状态
            int shipStatus = childOrderListBean.getShipStatus();
            orderHeaderModel.setShipStatus(shipStatus);
            orderDetailsList.add(orderHeaderModel);
            //店铺名称
            String storeName = childOrderListBean.getStoreName();
            OrderShopNameModel orderShopNameModel = new OrderShopNameModel();
            orderShopNameModel.setShopName(storeName);
            orderDetailsList.add(orderShopNameModel);
            //发票类型
            receipt = childOrderListBean.getReceipt();
            if (receipt == null || "".equals(receipt)) {
                tvBillType.setText("无");
            } else {
                int receiptStatus = receipt.getReceiptStatus();
                //发票类型，2：普通发票，3为专用发票
                if (receiptStatus == 2) {
                    tvBillType.setText("普通发票");
                } else if (receiptStatus == 3) {
                    tvBillType.setText("专用发票");
                } else {
                    tvBillType.setText("无");
                }
            }
            //列表
            List<OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean.OrderItemBean> orderItem = childOrderListBean.getOrderItem();
            for (int k = 0, l = orderItem.size(); k < l; k++) {
                OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean.OrderItemBean orderItemBean = orderItem.get(k);
                OrderGoodsContentModel orderGoodsContentModel = new OrderGoodsContentModel();
                //商品图片
                String image = orderItemBean.getImage();
                orderGoodsContentModel.setImage(image);
                //商品名称
                String name = orderItemBean.getName();
                orderGoodsContentModel.setName(name);
                //商品数量
                int num = orderItemBean.getNum();
                orderGoodsContentModel.setNum(num);
                //商品价格
                double price = orderItemBean.getPrice();
                orderGoodsContentModel.setPrice(String.format("%.2f", price));
                //商品信息
                orderDetailsList.add(orderGoodsContentModel);
            }
            //赠品
            OrderDetailsMainModel.OrderDetailsBean.ChildOrderListBean.ActivityGiftBean activityGiftBean = childOrderListBean.getActivityGift();
//            Log.e("TAG_赠品", "activityGiftBean=" + (activityGiftBean != null));
            if (activityGiftBean != null) {
                OrderDetailsGiftModel orderDetailsGiftModel = new OrderDetailsGiftModel();
                String giftImg = activityGiftBean.getGiftImg();
                orderDetailsGiftModel.setImage(giftImg);
                String giftName = activityGiftBean.getGiftName();
                orderDetailsGiftModel.setName(giftName);
//                Log.e("TAG_赠品", "giftName=" + giftName);
                double giftPrice = activityGiftBean.getGiftPrice();
                orderDetailsGiftModel.setMoney(String.format("%.2f", giftPrice));
                orderDetailsList.add(orderDetailsGiftModel);
            }
            //支付信息
            OrderDetailsPayModel payModel = new OrderDetailsPayModel();
            //商品总额
            double goodsAmount = childOrderListBean.getGoodsAmount();
            payModel.setGoodsAmount("￥" + String.format("%.2f", goodsAmount));
            //积分抵现
            double deductMoney = childOrderListBean.getDeductMoney();
            payModel.setDeductMoney("￥" + String.format("%.2f", deductMoney));
            //运费总额
            double shippingTotal1 = childOrderListBean.getShippingTotal();
            payModel.setShippingTotal("￥" + String.format("%.2f", shippingTotal1));
            //小计
            double needPayMoney = childOrderListBean.getNeedPayMoney();
            payModel.setNeedPayMoney("￥" + String.format("%.2f", needPayMoney));
            // 订单状态
            payModel.setStatus(status);
            //订单号
            int orderId = childOrderListBean.getOrderId();
            payModel.setOrderId(orderId);
            orderDetailsList.add(payModel);
        }
        goodsAdapter.setData(orderDetailsList);
    }

    @Override
    public void onCancelResult() {

    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

    }

    @Override
    public void onParseErrorResult(int errorCode) {

    }

    @Override
    public void onFinishResult() {

    }

    //申请售后
    @Override
    public void OnDetailsApplyClick(View view, int position) {
        startWebViewActivity(Config.SHOPPHONE);
    }

    //提醒物流=1；提醒发货=2
    @Override
    public void OnDetailsRemindClick(View view, int position, int type) {

        Object o = orderDetailsList.get(position);
        if (o instanceof OrderDetailsPayModel) {
            OrderDetailsPayModel payModel = (OrderDetailsPayModel) o;
            int orderId = payModel.getOrderId();
            remindDialog(type, orderId);
        }
    }

    private void remindDialog(int type, int orderId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(102, Config.ORDERHURRY+orderId+"/"+type, params);
    }

    //查看物流
    @Override
    public void OnDetailsLookOverClick(View view, int position) {
        Object o = orderDetailsList.get(position);
        if (o instanceof OrderDetailsPayModel) {
            OrderDetailsPayModel payModel = (OrderDetailsPayModel) o;
            int orderId = payModel.getOrderId();
            queryExpress(orderId);
        }
    }

    //确认收货
    @Override
    public void OnDetailsOkClick(View view, int position) {
        Object o = orderDetailsList.get(position);
        if (o instanceof OrderDetailsPayModel) {
            OrderDetailsPayModel payModel = (OrderDetailsPayModel) o;
            int orderId = payModel.getOrderId();
            confirmReapDialog(orderId);
        }
    }

    private void confirmReapDialog( int orderId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(103, Config.ORDERCONFIRM+orderId, params);
    }

    protected void startWebViewActivity(String webViewUrl) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("webViewUrl", webViewUrl);
        startActivity(intent);
    }

    //取消订单原因列表
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        for (int j = 0, k = listCancelOrder.size(); j < k; j++) {
            Map<String, String> stringStringMap = listCancelOrder.get(j);
            if (j == i) {
                stringStringMap.put("isCheck", "1");
                reason = arrayCancelOrder[j];
            } else {
                stringStringMap.put("isCheck", "0");
            }
        }
        setSimpleAdapter.notifyDataSetChanged();
    }

    //取消订单弹窗
    List<Map<String, String>> listCancelOrder;
    String[] arrayCancelOrder = {"我不想买了", "信息填错了，重新下单", "多买了/买错了",
            "支付失败", "其他原因"};
    protected AlertDialog cancelOrderDialog;
    private SetSimpleAdapter setSimpleAdapter;
    //取消订单原因
    private String reason;

    private void showCancelOrderDialog(String orderId) {
        listCancelOrder = new ArrayList<>();
        LayoutInflater factor = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = factor.inflate(R.layout.dialog_cancelorder, null);
        for (int i = 0; i < arrayCancelOrder.length; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("reason", arrayCancelOrder[i]);
            if (i == 0) {
                reason = arrayCancelOrder[i];
                item.put("isCheck", "1");
            } else {
                item.put("isCheck", "0");
            }
            listCancelOrder.add(item);
        }
        setSimpleAdapter = new SetSimpleAdapter(this, listCancelOrder, R.layout.item_cancelorder, new String[]{"reason"},
                new int[]{R.id.tv_Reason});
        ListView rcCancelOrderList = (ListView) dialogView.findViewById(R.id.rc_cancelOrderList);
        // 给listview加入适配器
        rcCancelOrderList.setAdapter(setSimpleAdapter);
        rcCancelOrderList.setItemsCanFocus(false);
        rcCancelOrderList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        rcCancelOrderList.setOnItemClickListener(this);

        ImageView ivCancelOrder = (ImageView) dialogView.findViewById(R.id.iv_cancelOrder);
        ivCancelOrder.setOnClickListener(this);
        TextView tvCancelOrderOk = (TextView) dialogView.findViewById(R.id.tv_cancelOrderOk);
        tvCancelOrderOk.setOnClickListener(this);
        Activity activity = this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        cancelOrderDialog = builder.create();
        cancelOrderDialog.show();
        cancelOrderDialog.setContentView(dialogView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        dialogView.setLayoutParams(layout);
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_showreceipt, null);
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 点击空白处时，隐藏掉pop窗口
        window.setFocusable(true);
        setBackgroundAlpha(0.5f);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(this, R.color.white));
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.popwindow_anim_topanddown);
        // 在底部显示
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //添加控件绑定并配置适配器
        TextView ok = (TextView) view.findViewById(R.id.ok);
        //类似如此添加监听事件
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //发票类型
        int receiptStatus = receipt.getReceiptStatus();
        //发票类型，2：普通发票，3为专用发票
        TextView tvDialogReceiptType = (TextView) view.findViewById(R.id.tv_DialogReceiptType);
        if (receiptStatus == 2) {
            tvDialogReceiptType.setText("普通发票");
        } else if (receiptStatus == 3) {
            tvDialogReceiptType.setText("专用发票");
        } else {
            tvDialogReceiptType.setText("无");
        }
        //发票内容
        String title = receipt.getTitle();
        TextView tvDialogReceiptName = (TextView) view.findViewById(R.id.tv_DialogReceiptName);
        tvDialogReceiptName.setText(title == null ? "" : title);
        //税号
        TextView tvDialogReceiptNumber1 = (TextView) view.findViewById(R.id.tv_DialogReceiptNumber1);
        SpannableStringBuilder tradepriceString = AlignedTextUtils.justifyString("税号", 4);
        tradepriceString.append("：");
        tvDialogReceiptNumber1.setText(tradepriceString);
        String invoiceNum = receipt.getInvoiceNum();
        TextView tvDialogReceiptNumber = (TextView) view.findViewById(R.id.tv_DialogReceiptNumber);
        tvDialogReceiptNumber.setText(invoiceNum == null ? "" : invoiceNum);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     *
     */
    protected AlertDialog showRemindContentDialog;

    private void showRemindContentDialog(int type) {

        LayoutInflater factor = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = factor.inflate(R.layout.dialog_remindorder, null);

        ImageView ivdismissReminOrder = (ImageView) dialogView.findViewById(R.id.iv_dismissReminOrder);
        ivdismissReminOrder.setOnClickListener(this);
        TextView tvReminOrderOk = (TextView) dialogView.findViewById(R.id.tv_ReminOrderOk);
        tvReminOrderOk.setOnClickListener(this);
        ImageView ivReminOrder = (ImageView) dialogView.findViewById(R.id.iv_ReminOrder);
        TextView tvReminOrder = (TextView) dialogView.findViewById(R.id.tv_ReminOrder);
        switch (type) {
            case 1://提醒物流
                ivReminOrder.setBackgroundResource(R.mipmap.remind_express);
                ivReminOrder.setVisibility(View.VISIBLE);
                tvReminOrder.setText("亲爱的上帝，在您按这个提示前，我们已如上图一样去催物流了~！");
                break;
            case 2://提醒发货
                ivReminOrder.setBackgroundResource(R.mipmap.remind_shipping);
                ivReminOrder.setVisibility(View.VISIBLE);
                tvReminOrder.setText("禀万岁，臣已领旨，马上快马加鞭去发货！");
                break;
            default:
                ivReminOrder.setVisibility(View.GONE);
                tvReminOrder.setText("亲，您的提醒已收到，我们正拼尽全力解决！");
                break;
        }
        Activity activity = this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        showRemindContentDialog = builder.create();
        showRemindContentDialog.show();
        showRemindContentDialog.setContentView(dialogView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        dialogView.setLayoutParams(layout);
    }
}
