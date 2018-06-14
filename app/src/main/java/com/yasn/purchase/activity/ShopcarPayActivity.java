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
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.ShopCarPayAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.ShopCarAdapterModel;
import com.yasn.purchase.model.order.ShopcarPayModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.RcDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

import static com.yasn.purchase.R.id.ll_ShopCarPayHint;
import static com.yasn.purchase.R.id.sw_ShopCarPay;

/**
 * 进货单结算
 * 2018年6月6日 09:25:11
 */
public class ShopcarPayActivity extends SimpleTopbarActivity implements CompoundButton.OnCheckedChangeListener, TextWatcher {

    private TextView tvShopCarPayName,tvShopCarPayAddress,tvShopCarPayIntegral,tvInvoice;
    private ShopCarPayAdapter adapter;
    private RecyclerView rcShopcarSignfor;
    private Switch swShopCarPay;
    private LinearLayout llShopCarPayHint,llInvoice;
    private List<ShopCarAdapterModel> shopCarAdapterList;
    //备注
    private TextView tvShopCarPayRemark,tvShopcarPayTotalMoney, tvShopcarPayCarriageMoney;
    @Override
    protected Object getTopbarTitle() {
        return "确认订单";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar_pay);
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
        swShopCarPay = (Switch) findViewById(sw_ShopCarPay);
        swShopCarPay.setOnCheckedChangeListener(this);
        llShopCarPayHint = (LinearLayout) findViewById(ll_ShopCarPayHint);
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
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            okHttpGet(100, Config.SHOPPCARCLOSEANACCOUNT, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            okHttpGet(100, Config.SHOPPCARCLOSEANACCOUNT, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_Invoice:
                Log.e("TAG_发票","点击");
                startActivityForResult(new Intent(this,
                        InvoiceActivity.class), 10000);
                break;
            case R.id.tv_ShopCarPayRemark:
                showRemarkDialog();
                break;
            case R.id.tv_RemarkOk:
                String etRemarkStr = etRemark.getText().toString().trim();
                if (!TextUtils.isEmpty(etRemarkStr)){
                    tvShopCarPayRemark.setText(etRemarkStr);
                }
                showRemarkDialog.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == 4) {
            String invoice=data.getStringExtra("invoice");
            tvInvoice.setText(invoice);
        }
    }
    ShopcarPayModel.MainOrderBean mainOrder;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100://选中
                if (returnCode == 200) {
                    ShopcarPayModel shopcarPayModel = JSON.parseObject(returnData, ShopcarPayModel.class);
                    ShopcarPayModel.MemberAddressBean memberAddress = shopcarPayModel.getMemberAddress();
                    //收货地址
                    initMemberAddress(memberAddress);
                    //商品信息
                    initSubOrders(shopcarPayModel);
                    //积分抵现
                   mainOrder = shopcarPayModel.getMainOrder();
                    int enablePoint = mainOrder.getEnablePoint();
                    if (enablePoint >0){
                        tvShopCarPayIntegral.setText("当前可用积分："+enablePoint);
                        swShopCarPay.setVisibility(View.VISIBLE);
                        llShopCarPayHint.setVisibility(View.VISIBLE);
                    }else {
                        tvShopCarPayIntegral.setText("暂无可用积分");
                        swShopCarPay.setVisibility(View.GONE);
                        llShopCarPayHint.setVisibility(View.GONE);
                    }
                    //合计
                    double needPayMoney = mainOrder.getNeedPayMoney();
                    tvShopcarPayTotalMoney.setText("￥"+String.format("%.2f",needPayMoney));
                    //运费
                    double shippingTotal = mainOrder.getShippingTotal();
                    tvShopcarPayCarriageMoney.setText("(含运费￥"+String.format("%.2f",shippingTotal)+")");
                }
                break;
        }
    }

    private void initSubOrders(ShopcarPayModel shopcarPayModel) {
        shopCarAdapterList = new ArrayList<>();
        List<ShopcarPayModel.SubOrdersBean> subOrders = shopcarPayModel.getSubOrders();
        if (subOrders !=null &&subOrders.size() > 0 ){
            for (int j = 0,k = subOrders.size(); j < k; j++) {
                ShopcarPayModel.SubOrdersBean subOrdersBean = subOrders.get(j);
                //店铺名称
                String storeName = subOrdersBean.getStoreName();
                ShopCarAdapterModel shopCarPayTitleModel = new ShopCarAdapterModel();
                shopCarPayTitleModel.setItmeType(1);
                shopCarPayTitleModel.setStoreName("店铺名称：" + storeName);
                shopCarAdapterList.add(shopCarPayTitleModel);
                List<ShopcarPayModel.SubOrdersBean.OrderItemVOSBean> orderItemVOS = subOrdersBean.getOrderItemVOS();
                if (orderItemVOS !=null &&orderItemVOS.size() > 0 ){
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
            Log.e("TAG_支付","list="+shopCarAdapterList.toString());
            adapter.setData(shopCarAdapterList);
        }
    }

    private void initMemberAddress(ShopcarPayModel.MemberAddressBean memberAddress) {
        if (memberAddress !=null){
            //姓名
            String name = memberAddress.getName();
            //电话
            String mobile = memberAddress.getMobile();
            tvShopCarPayName.setText(name+"\t\t"+mobile);
            //地址
            String province = memberAddress.getProvince();
            String city = memberAddress.getCity();
            String region = memberAddress.getRegion();
            String addr = memberAddress.getAddr();
            if (TextUtils.isEmpty(addr)){
                tvShopCarPayAddress.setText(province+"-"+city+"-"+region);
            }else {
                tvShopCarPayAddress.setText(province+"-"+city+"-"+region+"-"+addr);
            }
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
        if (isChecked){
            Log.e("TAG_SW","开启");
            llShopCarPayHint.setVisibility(View.VISIBLE);
            double enableDeductMoney = mainOrder.getEnableDeductMoney();
            double needPayMoney = mainOrder.getNeedPayMoney();

            tvShopcarPayTotalMoney.setText("￥"+String.format("%.2f",needPayMoney-enableDeductMoney));
        }else {
            Log.e("TAG_SW","关闭");
            llShopCarPayHint.setVisibility(View.GONE);
            double needPayMoney = mainOrder.getNeedPayMoney();
            tvShopcarPayTotalMoney.setText("￥"+String.format("%.2f",needPayMoney));

        }
    }
    /**
     * 备注dialog
     */
    protected AlertDialog showRemarkDialog;
    EditText etRemark;
    TextView tvRemarkCun;
    TextView tvRemarkOk;
    private void showRemarkDialog() {

        LayoutInflater factor = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = factor.inflate(R.layout.dialog_remark, null);

        etRemark = (EditText) dialogView.findViewById(R.id.et_Remark);
        etRemark.addTextChangedListener(this);
        tvRemarkCun = (TextView) dialogView.findViewById(R.id.tv_RemarkCun);
        tvRemarkOk = (TextView) dialogView.findViewById(R.id.tv_RemarkOk);
        tvRemarkOk.setOnClickListener(this);
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
