package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.adapter.ShopCarPayAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.ShopCarAdapterModel;
import com.yasn.purchase.model.order.ShopcarPayModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.RcDecoration;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;


/**
 * 进货单结算
 * 2018年6月6日 09:25:11
 */
public class ShopcarPayActivity extends SimpleTopbarActivity implements CompoundButton.OnCheckedChangeListener, TextWatcher {

    private TextView tvShopCarPayName, tvShopCarPayAddress, tvShopCarPayIntegral, tvInvoice;
    private ShopCarPayAdapter adapter;
    private RecyclerView rcShopcarSignfor;
    private Switch swShopCarPay;
    private LinearLayout llShopCarPayHint, llInvoice;
    private List<ShopCarAdapterModel> shopCarAdapterList;
    //备注
    private TextView tvShopCarPayRemark, tvShopcarPayTotalMoney, tvShopcarPayCarriageMoney;
    //积分 0未使用 1使用
    private String canUsePoint = "0";

    @Override
    protected Object getTopbarTitle() {
        return "确认订单";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar_pay);
        //地址
        RelativeLayout rlStartAddress = (RelativeLayout) findViewById(R.id.rl_StartAddress);
        rlStartAddress.setOnClickListener(this);
        tvShopCarPayName = (TextView) findViewById(R.id.tv_ShopCarPayName);
        tvShopCarPayAddress = (TextView) findViewById(R.id.tv_ShopCarPayAddress);
        //商品信息
        rcShopcarSignfor = (RecyclerView) findViewById(R.id.rc_ShopcarSignfor);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

//        linearLayoutManager.setAutoMeasureEnabled(true);
        rcShopcarSignfor.setLayoutManager(linearLayoutManager);
        adapter = new ShopCarPayAdapter(this);
        //自定义的分隔线
        rcShopcarSignfor.addItemDecoration(new RcDecoration(this, RcDecoration.VERTICAL_LIST));
        rcShopcarSignfor.setAdapter(adapter);
        //积分开关
        tvShopCarPayIntegral = (TextView) findViewById(R.id.tv_ShopCarPayIntegral);
        swShopCarPay = (Switch) findViewById(R.id.sw_ShopCarPay);
        swShopCarPay.setOnCheckedChangeListener(this);
        llShopCarPayHint = (LinearLayout) findViewById(R.id.ll_ShopCarPayHint);
        llShopCarPayHint.setVisibility(View.GONE);
        //发票
        llInvoice = (LinearLayout) findViewById(R.id.ll_Invoice);
        llInvoice.setOnClickListener(this);
        tvInvoice = (TextView) findViewById(R.id.tv_Invoice);
        //备注
        tvShopCarPayRemark = (TextView) findViewById(R.id.tv_ShopCarPayRemark);
        tvShopCarPayRemark.setOnClickListener(this);
        //合计
        tvShopcarPayTotalMoney = (TextView) findViewById(R.id.tv_ShopcarPayTotalMoney);
        //运费
        tvShopcarPayCarriageMoney = (TextView) findViewById(R.id.tv_ShopcarPayCarriageMoney);
        //结算
        TextView tvStartPay = (TextView) findViewById(R.id.tv_StartPay);
        tvStartPay.setOnClickListener(this);
        String returnData = getIntent().getStringExtra("returnData");
        Log.e("TAG_结算", "returnData=" + returnData);
        initShopCarPayModel(returnData);
    }

    private int addrId;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_StartAddress://收货地址
                Intent intent = new Intent(this, AddressActivity.class);
                intent.putExtra("addrId", addrId);
                startActivityForResult(intent, 10001);
                break;
            case R.id.ll_Invoice:
                Log.e("TAG_发票", "点击");
                Intent intent1 = new Intent(this, InvoiceActivity.class);
                String trim = tvInvoice.getText().toString().trim();
                Log.e("TAG_发票","trim="+trim);
                if ("普通发票".equals(trim)){
                    intent1.putExtra("type",1);
                }else if ("专用发票".equals(trim)){
                    intent1.putExtra("type",2);
                }else {
                    intent1.putExtra("type",0);
                }
                startActivityForResult(intent1, 10000);
                break;
            case R.id.tv_ShopCarPayRemark:
                String tvShopCarPayRemarkDialog = tvShopCarPayRemark.getText().toString().trim();
                showRemarkDialog(tvShopCarPayRemarkDialog);
                break;
            case R.id.tv_RemarkOk:
                String etRemarkStr = etRemark.getText().toString().trim();
                if (!TextUtils.isEmpty(etRemarkStr)) {
                    tvShopCarPayRemark.setText(etRemarkStr);
                } else {
                    tvShopCarPayRemark.setText("无");
                }
                showRemarkDialog.dismiss();
                break;
            case R.id.tv_StartPay: //结算

                if (Config.isWebViewPay){
                    Map<String, String> params = new HashMap();
                    if (token != null && !"".equals(token)) {
                        params.put("access_token", token);

                    } else if (resetToken != null && !"".equals(resetToken)) {
                        params.put("access_token", resetToken);
                    } else {
                        ToastUtil.showToast("登录过期，请重新登录");
                        return;
                    }
                    okHttpPostHeader(100, Config.SHOPPCARUSEPOINT+canUsePoint, params);
                }else {
                    orderCreate();
                }
                break;
        }
    }

    private void orderCreate() {
        Map<String, String> params = new HashMap();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);

        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
            return;
        }
        String trim = tvShopCarPayRemark.getText().toString().trim();
        params.put("canUsePoint", canUsePoint);//积分 0未使用 1使用
        params.put("source", "2");// 0: 其它;1: 微信; 2: 安卓; 3: ios;
        params.put("remark", (TextUtils.isEmpty(trim)||"无".equals(trim)) ? "" : trim);//备注
        okHttpPostHeader(101, Config.SHOPPCARCREATEORDER, params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 10000:
                    String invoice = data.getStringExtra("invoice");
                    tvInvoice.setText(invoice);
                    break;
                case 10001:
                    String address = data.getStringExtra("address");
                    addrId = data.getIntExtra("addressId", 0);
                    if (!TextUtils.isEmpty(address)) {
                        tvShopCarPayAddress.setText(address);
                        //姓名
                        String name = data.getStringExtra("addressName");
                        //电话
                        String mobile = data.getStringExtra("addressMobile");
                        tvShopCarPayName.setText(name + "\t\t" + mobile);
                    }
                    break;
            }
        }
    }

    ShopcarPayModel.MainOrderBean mainOrder;

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100://积分抵现接⼝口
                if (returnCode == 200) {
                    orderCreate();
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101://结算
                try {
                    if (returnCode == 200) {
                        //0:经理,1:采购 ,2:财务 1,2采购+财务
                        String employeeAuth = SharePrefHelper.getInstance(this).getSpString("employeeAuth");
                        Log.e("TAG_结算", "权限=" + employeeAuth);
                        if (TextUtils.isEmpty(employeeAuth)) {
                            Intent intent = new Intent(ShopcarPayActivity.this, MyOrderActivity.class);
                            intent.putExtra("tabIndex", 1);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            if (employeeAuth.indexOf("0") == -1) {
                                if (employeeAuth.indexOf("2") == -1) {
                                    Intent intent = new Intent(ShopcarPayActivity.this, MyOrderActivity.class);
                                    intent.putExtra("tabIndex", 1);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            }
                        }
                        JSONObject object = new JSONObject(returnData);
                        int orderid = object.optInt("data");
                        Intent intent = new Intent(this, WebViewH5Activity.class);
                        intent.putExtra("webViewUrl", Config.ORDERPAY + orderid);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtil.showToast(returnMsg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void initShopCarPayModel(String returnData) {
        ShopcarPayModel shopcarPayModel = JSON.parseObject(returnData, ShopcarPayModel.class);
        ShopcarPayModel.MemberAddressBean memberAddress = shopcarPayModel.getMemberAddress();
        //收货地址
        initMemberAddress(memberAddress);
        //商品信息
        initSubOrders(shopcarPayModel);
        //积分抵现
        mainOrder = shopcarPayModel.getMainOrder();
        int enablePoint = mainOrder.getEnablePoint();
        if (enablePoint > 0) {
            tvShopCarPayIntegral.setText("当前可用积分：" + enablePoint);
            swShopCarPay.setVisibility(View.VISIBLE);
            llShopCarPayHint.setVisibility(View.GONE);
            //是否使用积分
            int usePoint = mainOrder.getUsePoint();
            if (usePoint > 0){
                swShopCarPay.setChecked(true);
            }else {
                swShopCarPay.setChecked(false);
            }
        } else {
            tvShopCarPayIntegral.setText("暂无可用积分");
            swShopCarPay.setVisibility(View.GONE);
            llShopCarPayHint.setVisibility(View.GONE);
        }
        //合计
        double needPayMoney = mainOrder.getNeedPayMoney();
        tvShopcarPayTotalMoney.setText("￥" + String.format("%.2f", needPayMoney));
        //运费
        double shippingTotal = mainOrder.getShippingTotal();
        tvShopcarPayCarriageMoney.setText("(含运费￥" + String.format("%.2f", shippingTotal) + ")");
    }

    private void initSubOrders(ShopcarPayModel shopcarPayModel) {
        shopCarAdapterList = new ArrayList<>();
        List<ShopcarPayModel.SubOrdersBean> subOrders = shopcarPayModel.getSubOrders();
        if (subOrders != null && subOrders.size() > 0) {
            for (int j = 0, k = subOrders.size(); j < k; j++) {
                ShopcarPayModel.SubOrdersBean subOrdersBean = subOrders.get(j);
                //店铺名称
                String storeName = subOrdersBean.getStoreName();
                ShopCarAdapterModel shopCarPayTitleModel = new ShopCarAdapterModel();
                shopCarPayTitleModel.setItmeType(1);
                shopCarPayTitleModel.setStoreName("店铺名称：" + storeName);
                shopCarAdapterList.add(shopCarPayTitleModel);
                List<ShopcarPayModel.SubOrdersBean.OrderItemVOSBean> orderItemVOS = subOrdersBean.getOrderItemVOS();
                if (orderItemVOS != null && orderItemVOS.size() > 0) {
                    for (int l = 0, m = orderItemVOS.size(); l < m; l++) {
                        ShopCarAdapterModel shopCarPayAdapterModel = new ShopCarAdapterModel();
                        ShopcarPayModel.SubOrdersBean.OrderItemVOSBean orderItemVOSBean = orderItemVOS.get(l);
                        //标题
                        String name = orderItemVOSBean.getName();
                        shopCarPayAdapterModel.setName(name);
                        //图片
                        String image = orderItemVOSBean.getImage();
                        shopCarPayAdapterModel.setImageDefault(image);
                        //价格
                        double price = orderItemVOSBean.getPrice();
                        shopCarPayAdapterModel.setPrice(price);
                        int num = orderItemVOSBean.getNum();
                        shopCarPayAdapterModel.setNum(num);
                        shopCarPayAdapterModel.setNeedPayMoney(price * num);
                        shopCarPayAdapterModel.setItmeType(2);
                        shopCarAdapterList.add(shopCarPayAdapterModel);
                    }
                }
                //运费
                double shippingTotal = subOrdersBean.getShippingTotal();
                ShopCarAdapterModel shopCarPayTotalModel = new ShopCarAdapterModel();
                shopCarPayTotalModel.setItmeType(3);
                shopCarPayTotalModel.setShippingTotal(shippingTotal);
                shopCarAdapterList.add(shopCarPayTotalModel);
            }
            adapter.setData(shopCarAdapterList);
        }
    }

    private void initMemberAddress(ShopcarPayModel.MemberAddressBean memberAddress) {
        if (memberAddress != null) {
            //地址is
            addrId = memberAddress.getAddrId();

            //姓名
            String name = memberAddress.getName();
            //电话
            String mobile = memberAddress.getMobile();
            tvShopCarPayName.setText(name + "\t\t" + mobile);
            //地址
            String province = memberAddress.getProvince();
            String city = memberAddress.getCity();
            String region = memberAddress.getRegion();
            String addr = memberAddress.getAddr();
            if (TextUtils.isEmpty(addr)) {
                tvShopCarPayAddress.setText(province + "-" + city + "-" + region);
            } else {
                tvShopCarPayAddress.setText(province + "-" + city + "-" + region + "-" + addr);
            }
//            tvShopCarPayAddress.requestLayout();
        }
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            Log.e("TAG_SW", "开启");
            llShopCarPayHint.setVisibility(View.VISIBLE);
            double enableDeductMoney = mainOrder.getEnableDeductMoney();
            double needPayMoney = mainOrder.getNeedPayMoney();
            tvShopcarPayTotalMoney.setText("￥" + String.format("%.2f", needPayMoney - enableDeductMoney));
            canUsePoint = "1";//积分 0未使用 1使用
        } else {
            Log.e("TAG_SW", "关闭");
            llShopCarPayHint.setVisibility(View.GONE);
            double needPayMoney = mainOrder.getNeedPayMoney();
            tvShopcarPayTotalMoney.setText("￥" + String.format("%.2f", needPayMoney));
            canUsePoint = "0";//积分 0未使用 1使用
        }
    }

    /**
     * 备注dialog
     */
    protected AlertDialog showRemarkDialog;
    EditText etRemark;
    TextView tvRemarkCun;
    TextView tvRemarkOk;

    private void showRemarkDialog(String tvShopCarPayRemarkDialog) {

        LayoutInflater factor = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = factor.inflate(R.layout.dialog_remark, null);

        etRemark = (EditText) dialogView.findViewById(R.id.et_Remark);
        tvRemarkCun = (TextView) dialogView.findViewById(R.id.tv_RemarkCun);
        tvRemarkOk = (TextView) dialogView.findViewById(R.id.tv_RemarkOk);
        tvRemarkOk.setOnClickListener(this);
        if (!TextUtils.isEmpty(tvShopCarPayRemarkDialog)) {
            etRemark.setText(tvShopCarPayRemarkDialog);
            etRemark.setSelection(tvShopCarPayRemarkDialog.length());
            tvRemarkCun.setText(tvShopCarPayRemarkDialog.length() + "/100");//需要将数字转成字符串
        }
        etRemark.addTextChangedListener(this);
        Activity activity = this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        showRemarkDialog = builder.create();
        showRemarkDialog.show();
        showRemarkDialog.setContentView(dialogView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        dialogView.setLayoutParams(layout);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        showRemarkDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        showRemarkDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String etRemarkStr = charSequence.toString().trim();
        int l = etRemarkStr.length();
        tvRemarkCun.setText(l + "/100");//需要将数字转成字符串
        if (l >= 100) {
            tvRemarkCun.setTextColor(getResources().getColor(R.color.orange));
            etRemark.setSelection(100);//EditView设置光标到最后
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
