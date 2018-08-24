package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.adapter.ShopCarAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.help.ShopCarUtils;
import com.yasn.purchase.listener.OnShopCarClickListener;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.model.ShopCarAdapterModel;
import com.yasn.purchase.model.ShopCarModel;
import com.yasn.purchase.model.ShopCarWholeModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;
import com.yasn.purchase.view.RcDecoration;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.help.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;


public class ShopCarActivity extends SimpleTopbarActivity implements OnShopCarClickListener
        , SwipeRefreshLayout.OnRefreshListener {

    private MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout llAnnouncement;
    private TextView tvAnnouncement;
    private RecyclerView rcShopCar;
    private ShopCarAdapter adapter;
    private LinearLayout llPayorder;
    private TextView tvNeedPayMoney, tvAddShopCar;
    private ImageView ivStoreNameSelect;
    private LinearLayout llShopcarNodata, llStoreNameSelect;
    //阶梯价临时存储数据
    private List<List<ShopCarWholeModel.DataBean>> shopCarWholeList;

    private void OkHttpDemand() {

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new Runnable() {

            @Override
            public void run() {
                String shopCar = ShopCarUtils.getShopCar(Config.SHOPPCAR, token);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = shopCar;
                handler.sendMessage(msg);
            }
        });
    }

    private void getWholeList() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(105, Config.SHOPPCARWHOLELIST, params);
    }

    @Override
    protected Object getTopbarTitle() {
        return R.string.shopcar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopcar);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initSwipeRefreshLayout();
        initView();
        //获取阶梯价列表
        getWholeList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //获取阶梯价列表
        getWholeList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG_进货单","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG_进货单","onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //获取阶梯价列表
        getWholeList();
    }

    private void initView() {
        llAnnouncement = (LinearLayout) findViewById(R.id.ll_announcement);
        tvAnnouncement = (TextView) findViewById(R.id.tv_announcement);
        //进货单列表
        rcShopCar = (RecyclerView) findViewById(R.id.rc_shopcar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

//        linearLayoutManager.setAutoMeasureEnabled(true);
        rcShopCar.setLayoutManager(linearLayoutManager);
        adapter = new ShopCarAdapter(this);
        adapter.setOnItemClickListener(this);
        //自定义的分隔线
        rcShopCar.addItemDecoration(new RcDecoration(this, RcDecoration.VERTICAL_LIST));
        rcShopCar.setAdapter(adapter);
        //底部提交订单
        llPayorder = (LinearLayout) findViewById(R.id.ll_Payorder);
        llPayorder.setVisibility(View.GONE);
        tvAddShopCar = (TextView) findViewById(R.id.tv_addShopCar);
        tvAddShopCar.setOnClickListener(this);
        //总价格
        tvNeedPayMoney = (TextView) findViewById(R.id.tv_needPayMoney);
        //全选
        ivStoreNameSelect = (ImageView) findViewById(R.id.iv_StoreNameSelect);
        llStoreNameSelect = (LinearLayout) findViewById(R.id.ll_StoreNameSelect);
        llStoreNameSelect.setOnClickListener(this);
        //无数据
        llShopcarNodata = (LinearLayout) findViewById(R.id.ll_shopcarNodata);
        llShopcarNodata.setVisibility(View.INVISIBLE);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_StoreNameSelect:
                initImageStoreNameSelect();
                break;
            case R.id.tv_addShopCar:
//                boolean isCheckBeforeSale = false;//是否存在预售商品
                boolean isPrice = false;//选中商品是否存在价格为零商品
                int isCheckNum = 0;//选中商品数量
                for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
                    ShopCarAdapterModel shopCarAdapterGood = shopCarAdapterList.get(i);
//                    String beforeSale = shopCarAdapterGood.getBeforeSale();
                    int isCheck = shopCarAdapterGood.getIsCheck();
                    int itmeType = shopCarAdapterGood.getItmeType();
                    if (isCheck == 1 && itmeType == 2) {//选中
//                        if (beforeSale != null && !"".equals(beforeSale)) {
//                            //预售
//                            isCheckBeforeSale = true;
//                        }
                        isCheckNum++;
                        double price = shopCarAdapterGood.getPrice();
                        if (price<=0){
                            isPrice = true;
                            return;
                        }
                    }
                }
                if (isPrice){
                    ToastUtil.showToast("0元商品暂不可购买，请联系客服400-9973-315或删除0元商品后提交！");
                    return;
                }
                Log.e("TAG_提交訂單","isCheckNum="+isCheckNum);
//                if (isCheckBeforeSale&&isCheckNum > 1) {
//                    ToastUtil.showToast("预售商品请单独提交订单！");
//                } else {
                    if (isCheckNum == 0) {
                        ToastUtil.showToast("请先选择需要提交的商品！");
                    } else {
                        //提交订单判断商品是否是失效
                        Map<String, Object> params = new HashMap<String, Object>();
                        if (token != null && !"".equals(token)) {
                            params.put("access_token", token);
                            okHttpGet(106, Config.SHOPPCARCLOSEANACCOUNT, params);
                        } else if (resetToken != null && !"".equals(resetToken)) {
                            params.put("access_token", resetToken);
                            okHttpGet(106, Config.SHOPPCARCLOSEANACCOUNT, params);
//                            okHttpGet(106, Config.SHOPPCARINVALIDGOODS, params);
                        } else {
                            ToastUtil.showToast("登录过期，请重新登录");
                        }
                    }
//                }
                break;
        }
    }

    //全选
    private void initImageStoreNameSelect() {
        String tag = (String) ivStoreNameSelect.getTag();
        Log.e("TAG_首次1", "tag=" + (tag == null));
        int firstCheckIds = 0;
        int firstCancelIds = 0;
        StringBuffer sbCheckIds = new StringBuffer();
        StringBuffer sbCancelIds = new StringBuffer();
        for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
            ShopCarAdapterModel shopCarAdapterGood = shopCarAdapterList.get(i);
            int productId = shopCarAdapterGood.getProductId();
            int enableStore = shopCarAdapterGood.getEnableStore();
            int goodsOff = shopCarAdapterGood.getGoodsOff();
            int itmeType = shopCarAdapterGood.getItmeType();
            String beforeSale = shopCarAdapterGood.getBeforeSale();//是否预售
            int beforeSaleNum = shopCarAdapterGood.getBeforeSaleNum();//同一店铺下预售数量
            int goodsNum = shopCarAdapterGood.getGoodsNum();//同一店铺下商品个数

            if (j == 2) {
                Log.e("TAG_首次", "j=" + j + ";tag=" + (tag == null));
                if (tag == null) {
                    //title
                    shopCarAdapterGood.setIsCheck(1);
                    ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                    //商品
                    ShopCarAdapterModel shopCarGood = shopCarAdapterList.get(i + 1);
                    int productIdGood = shopCarGood.getProductId();
                    shopCarGood.setIsCheck(1);
                    ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                    ivStoreNameSelect.setTag("");
                    isSelected(String.valueOf(productIdGood), "");
                } else {
                    //title
                    shopCarAdapterGood.setIsCheck(0);
                    ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                    //商品
                    ShopCarAdapterModel shopCarGood = shopCarAdapterList.get(i + 1);
                    int productIdGood = shopCarGood.getProductId();
                    shopCarGood.setIsCheck(0);
                    ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                    ivStoreNameSelect.setTag(null);
                    isSelected("", String.valueOf(productIdGood));
                }
                adapter.notifyDataSetChanged();
                return;
            } else {
                if (beforeSale != null && !"".equals(beforeSale)) {//预售

                    if (tag != null) {//设置为未选中
                        firstCancelIds++;
                        if (firstCancelIds == 1) {
                            sbCancelIds.append(String.valueOf(productId));
                        } else {
                            sbCancelIds.append(",");
                            sbCancelIds.append(String.valueOf(productId));
                        }
                        shopCarAdapterGood.setIsCheck(0);
                    }else {//设置为选中
                        firstCheckIds++;
                        if (firstCheckIds == 1) {
                            sbCheckIds.append(String.valueOf(productId));
                        } else {
                            sbCheckIds.append(",");
                            sbCheckIds.append(String.valueOf(productId));
                        }
                        shopCarAdapterGood.setIsCheck(1);
                    }
                } else {
                    if (tag != null) {
                        if (productId > 0 && itmeType == 2 && enableStore > 0 && goodsOff != 1) {
                            firstCancelIds++;
                            if (firstCheckIds == 1) {
                                sbCancelIds.append(String.valueOf(productId));
                            } else {
                                sbCancelIds.append(",");
                                sbCancelIds.append(String.valueOf(productId));
                            }
                        }
                        shopCarAdapterGood.setIsCheck(0);
                    } else {
                        if (productId > 0 && itmeType == 2 && enableStore > 0 && goodsOff != 1) {
                            firstCheckIds++;
                            if (firstCheckIds == 1) {
                                sbCheckIds.append(String.valueOf(productId));
                            } else {
                                sbCheckIds.append(",");
                                sbCheckIds.append(String.valueOf(productId));
                            }
                            shopCarAdapterGood.setIsCheck(1);
                        } else if (itmeType == 1 && goodsNum == beforeSaleNum) {
                            Log.e("TAG_进货单全选", "goodsNum=" + goodsNum + ";beforeSaleNum=" + beforeSaleNum);
                            shopCarAdapterGood.setIsCheck(0);
                        } else {
                            shopCarAdapterGood.setIsCheck(1);
                        }
                    }

                }
            }
        }
        if (tag != null) {//已全选，设置全部未选
            ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
            ivStoreNameSelect.setTag(null);
        } else {
            ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
            ivStoreNameSelect.setTag("");
        }
        isSelected(sbCheckIds.toString(), sbCancelIds.toString());
        adapter.notifyDataSetChanged();
    }

    private void startWebViewActivity(String url) {

        if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
            Intent intent = new Intent(ShopCarActivity.this, WebViewH5Activity.class);
            intent.putExtra("webViewUrl", url);
            startActivity(intent);
        }
    }

    private List<ShopCarAdapterModel> shopCarAdapterList;

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        Log.e("TAG_购物车","requestCode="+requestCode);
        switch (requestCode) {
            case 101://选中
                if (returnCode == 200) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(returnData);
                        int code = object.optInt("code");
                        if (code != 200) {
                            OkHttpDemand();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (returnCode == 401) {
                    cleanToken();
//                    isSelected(isCheck == 1 ? 0 : 1, productId);
                } else {
                    OkHttpDemand();
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 102://删除
                if (returnCode == 200) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(returnData);
                        int code = object.optInt("code");
                        if (code == 200) {
                            OkHttpDemand();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ToastUtil.showToast(returnMsg);
                } else if (returnCode == 401) {
                    cleanToken();
//                    deleteCart(id);
                    ToastUtil.showToast(returnMsg);
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 104://修改数量和价格
                if (returnCode == 200) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(returnData);
                        int code = object.optInt("code");
                        if (code != 200) {
                            OkHttpDemand();
                            ToastUtil.showToast(returnMsg);
                        } else {
                            if (upDataNumNotifyDialog != null && upDataNumNotifyDialog.isShowing()) {
                                upDataNumNotifyDialog.dismiss();
                                ToastUtil.showToast(returnMsg);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (returnCode == 401) {
                    cleanToken();
                    OkHttpDemand();
                } else if (returnCode == 400) {
                    if (returnMsg.indexOf("该商品限购") != -1) {
                        ToastUtil.showToast(returnMsg);
                    } else {
                        OkHttpDemand();
                        ToastUtil.showToast(returnMsg);
                    }
                } else {
                    OkHttpDemand();
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 105://阶梯价
                Log.e("TAG_阶梯价", "returnData=" + returnData);
                if (returnCode == 200) {
                    if (returnData != null && !"".equals(returnData)) {
                        ShopCarWholeModel shopCarWholeModel = JSON.parseObject(returnData, ShopCarWholeModel.class);
                        shopCarWholeList = shopCarWholeModel.getData();
                        OkHttpDemand();
                    }
                } else if (returnCode == 401) {
                    cleanToken();
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 106: //提交订单判断商品是否是失效
                if (returnCode == 200) {
//                    startWebViewActivity(Config.CHECKOUTSHOPCAR);
                    Intent intent = new Intent(ShopCarActivity.this,ShopcarPayActivity.class);
                    intent.putExtra("returnData",returnData);
                    startActivity(intent);
                } else {
                    try {
                        JSONObject object = new JSONObject(returnData);
                        int code = object.optInt("code");
                        if (code != 200) {
                            String message = object.optString("message");
                            ToastUtil.showToast(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void initShopCarNoData() {
        llShopcarNodata.setVisibility(View.VISIBLE);
    }

    //购物车数据
    private void initShopCarData(String returnData) {
        shopCarAdapterList = new ArrayList<>();
        ShopCarModel shopCarModel = JSON.parseObject(returnData, ShopCarModel.class);
        List<ShopCarModel.DataBean> data = shopCarModel.getData();
        if (data != null && data.size() > 0) {
            for (int i = 0, j = data.size(); i < j; i++) {
                ShopCarAdapterModel shopCarTitleModel = new ShopCarAdapterModel();
                ShopCarModel.DataBean dataBean = data.get(i);
                //店铺id
                int store_id = dataBean.getStore_id();
                shopCarTitleModel.setStore_id(store_id);
                //店铺名称
                String store_name = dataBean.getStore_name();
                //临时记录预售商品数量
                int beforeSaleNum = 0;

                shopCarTitleModel.setItmeType(1);
                shopCarTitleModel.setStoreName("店铺名称：" + store_name);
                ShopCarModel.DataBean.StorepriceBean storeprice = dataBean.getStoreprice();
                double needPayMoney = storeprice.getNeedPayMoney();
                shopCarTitleModel.setNeedPayMoney(needPayMoney);
                //包邮价格
                double freeShipMoney = storeprice.getFreeShipMoney();
                shopCarTitleModel.setFreeShipMoney(String.valueOf(freeShipMoney));
                shopCarAdapterList.add(shopCarTitleModel);
                //店铺商品列表
                List<ShopCarModel.DataBean.GoodslistBean> goodslist = dataBean.getGoodslist();
                //同一店铺下商品个数
                shopCarTitleModel.setGoodsNum(goodslist.size());
                if (goodslist != null && goodslist.size() > 0) {
                    //相同店铺选中总价
                    double storeCheckPrice = 0;
                    for (int k = 0, l = goodslist.size(); k < l; k++) {
                        ShopCarAdapterModel shopCarAdapterModel = new ShopCarAdapterModel();
                        ShopCarModel.DataBean.GoodslistBean goodslistBean = goodslist.get(k);
                        //id
                        int id = goodslistBean.getId();
                        shopCarAdapterModel.setId(id);

                        //Store_id
                        shopCarAdapterModel.setStore_id(store_id);
                        //商品id
                        int goodsId = goodslistBean.getGoodsId();
                        shopCarAdapterModel.setGoodsId(goodsId);
                        //商品名字
                        String name = goodslistBean.getName();
                        shopCarAdapterModel.setName(name);
                        ShopCarModel.DataBean.GoodslistBean.OthersBean others = goodslistBean.getOthers();
                        //商品规格
                        List<ShopCarModel.DataBean.GoodslistBean.OthersBean.SpecListBean> specList = others.getSpecList();
                        if (specList != null && specList.size() > 0) {
                            List<String> specListValue = new ArrayList();
                            for (int m = 0, n = specList.size(); m < n; m++) {
                                ShopCarModel.DataBean.GoodslistBean.OthersBean.SpecListBean specListBean = specList.get(m);
                                String value = specListBean.getValue();
                                specListValue.add(value);
                            }
                            shopCarAdapterModel.setSpecList(specListValue);
                        }
                        //图片
                        String imageDefault = goodslistBean.getImageDefault();
                        shopCarAdapterModel.setImageDefault(imageDefault);
                        //价格
                        double price = goodslistBean.getPrice();
                        String priceResult = String.format("%.2f", price);
                        shopCarAdapterModel.setPrice(Double.valueOf(priceResult));
                        //数量
                        int num = goodslistBean.getNum();
                        shopCarAdapterModel.setNum(num);
                        //是否选中
                        int isCheck = goodslistBean.getIsCheck();
                        shopCarAdapterModel.setIsCheck(isCheck);
                        //相同店铺选中个数
                        if (isCheck == 1) {
                            storeCheckPrice = storeCheckPrice + price * num;
                        }
                        //库存
                        int enableStore = goodslistBean.getEnableStore();
                        shopCarAdapterModel.setEnableStore(enableStore);
                        //规格id
                        int productId = goodslistBean.getProductId();
                        shopCarAdapterModel.setProductId(productId);
                        //是否下架 1下架 0上架
                        int goodsOff = goodslistBean.getGoodsOff();

                        shopCarAdapterModel.setGoodsOff(goodsOff);
                        //是否预售
                        String beforeSale = goodslistBean.getBeforeSale();
                        if (beforeSale == null || "".equals(beforeSale)) {
                            shopCarAdapterModel.setBeforeSale("");
                        } else {//预售
                            shopCarAdapterModel.setBeforeSale(beforeSale);
                            beforeSaleNum++;
                            shopCarTitleModel.setBeforeSaleNum(beforeSaleNum);
                        }
                        //商品是否存在 0不存   1存在
                        int isExist = goodslistBean.getIsExist();
                        shopCarAdapterModel.setIsExist(isExist);
                        //最小起订量
                        int smallSale = goodslistBean.getSmallSale();
                        shopCarAdapterModel.setSmallSale(smallSale);
                        //步长
                        int step = goodslistBean.getStep();
                        shopCarAdapterModel.setStep(step);
                        //限购数量
                        int limitnum = goodslistBean.getLimitnum();
                        shopCarAdapterModel.setLimitnum(limitnum);
                        //抢购
                        int hasDiscount = goodslistBean.getHasDiscount();
                        shopCarAdapterModel.setHasDiscount(hasDiscount);
                        shopCarAdapterModel.setItmeType(2);
                        shopCarAdapterList.add(shopCarAdapterModel);
                    }
                    shopCarTitleModel.setStoreCheckPrice(storeCheckPrice);
                }
            }
        }
        adapter.setData(shopCarAdapterList);
        llShopcarNodata.setVisibility(View.GONE);
        //顶部titile是否选中
        initTitleSelected();
        //总价
        upDataMoney();
    }

    private void initTitleSelected() {
//        if (shopCarAdapterList.size() == 2) {
//            Log.e("TAG_首次", "j=" + shopCarAdapterList.size());
//            String tag = (String) ivStoreNameSelect.getTag();
//            ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(0);
//            if (tag == null) {
//                //title
//                shopCarAdapterModel.setIsCheck(1);
//                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
//                //商品
//                ShopCarAdapterModel shopCarGood = shopCarAdapterList.get(1);
//                int productIdGood = shopCarGood.getProductId();
//                shopCarGood.setIsCheck(1);
//                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
//                ivStoreNameSelect.setTag("");
//                isSelected(String.valueOf(productIdGood), "");
//            } else {
//                //title
//                shopCarAdapterModel.setIsCheck(0);
//                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
//                //商品
//                ShopCarAdapterModel shopCarGood = shopCarAdapterList.get(1);
//                int productIdGood = shopCarGood.getProductId();
//                shopCarGood.setIsCheck(0);
//                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
//                ivStoreNameSelect.setTag(null);
//                isSelected("", String.valueOf(productIdGood));
//            }
//
//        } else {
            //临时标记全选按钮状态
            boolean typeCheckAll = true;
//            //预售个数
//            int beforeSaleNum = 0;
            //下架个数
            int goodsOffNum = 0;
            //无货个数
            int enableStoreNum = 0;
            //普通选中商品
            int generalNum = 0;
//            //title个数
//            int titleNum = 0;
            //临时存储title位置
            int titlePosition = 0;
            for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
                ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
                int store_id = shopCarAdapterModel.getStore_id();
                int isCheck = shopCarAdapterModel.getIsCheck();
                //预售
                String beforeSale = shopCarAdapterModel.getBeforeSale();
                //顶部title
                int itmeType = shopCarAdapterModel.getItmeType();
                //库存
                int enableStore = shopCarAdapterModel.getEnableStore();
                //是否下架 1下架 0上架
                int goodsOff = shopCarAdapterModel.getGoodsOff();
                if (itmeType == 1) {
//                    //预售个数
//                    beforeSaleNum = 0;
                    //下架个数
                    goodsOffNum = 0;
                    //无货个数
                    enableStoreNum = 0;
                    //普通选中商品
                    generalNum = 0;
                    //title个数
//                titleNum = 0;

//                    titleNum++;
                    titlePosition = i;

                } else {
//                    if (beforeSale == null || "".equals(beforeSale)) {//非预售
                        if (goodsOff == 1) {//1下架
                            goodsOffNum++;
                        } else {//上架
                            if (enableStore > 0) {//有库存
                                if (isCheck == 1) {
                                    generalNum++;
                                }
                            } else {//无库存
                                enableStoreNum++;
                            }
                        }
//                    } else {//预售
//                        beforeSaleNum++;
//                    }
                }
                if (i + 1 < j) {
                    ShopCarAdapterModel shopCarNext = shopCarAdapterList.get(i + 1);
                    int store_idNext = shopCarNext.getStore_id();
                    if (store_id != store_idNext) {
                        ShopCarAdapterModel shopCarTitleModel = shopCarAdapterList.get(titlePosition);
                        int goodsNum = shopCarTitleModel.getGoodsNum();
                        if (goodsNum
//                                - beforeSaleNum
                                - goodsOffNum - enableStoreNum == generalNum) {
                            if (generalNum == 0) {//有库存且选中的个数为0
                                shopCarTitleModel.setIsCheck(0);
                                typeCheckAll = false;
                            } else {
                                shopCarTitleModel.setIsCheck(1);
                            }
                        } else {
                            shopCarTitleModel.setIsCheck(0);
                            typeCheckAll = false;
                        }
                    }
                } else if (i + 1 == j) {
                    ShopCarAdapterModel shopCarTitleModel = shopCarAdapterList.get(titlePosition);
                    int goodsNum = shopCarTitleModel.getGoodsNum();
                    if (goodsNum
//                            - beforeSaleNum
                            - goodsOffNum - enableStoreNum == generalNum) {
                        if (generalNum == 0) {//有库存且选中的个数为0
                            shopCarTitleModel.setIsCheck(0);
                            typeCheckAll = false;
                        } else {
                            shopCarTitleModel.setIsCheck(1);
                        }
                    } else {
                        shopCarTitleModel.setIsCheck(0);
                        typeCheckAll = false;
                    }
                }
            }

            if (!typeCheckAll) {
                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
            } else {
                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
            }
//        }
        adapter.notifyDataSetChanged();
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
    public void onRefresh() {
        getWholeList();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = 0;
                handler.sendMessage(msg);
//                swipeRefreshLayout.setRefreshing(false);
            }
        };
        new Timer().schedule(timerTask, 2000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                    String returnData = (String) msg.obj;
                    HelpUtils.loge("TAG_列表", "returnData=" + returnData);
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (returnData != null && !"".equals(returnData)) {
                        int storeprice1 = returnData.indexOf("storeprice");
                        if (storeprice1 == -1) {//空购物车
                            initShopCarNoData();
                            Log.e("TAG_购物车","空");
                            EventBusMsg carNum = new EventBusMsg("carNum");
                            carNum.setCarNum(String.valueOf(0));
                            EventBus.getDefault().post(carNum);
                        } else {
                            initShopCarData(returnData);
                        }
                    } else {
                        ToastUtil.showToast("网络异常！");
                    }
                    if (progressTxt != null) {
                        progressTxt.setVisibility(View.GONE);
                    }
                    if (upDataNumNotifyDialog != null && upDataNumNotifyDialog.isShowing()) {
                        upDataNumNotifyDialog.dismiss();
                    }
                    llPayorder.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    //item点击事件
    @Override
    public void OnItemClick(View view, int position) {

        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(position);
        if (shopCarAdapterModel != null) {
            int goodsOff = shopCarAdapterModel.getGoodsOff();
            if (goodsOff == 1) {
                ToastUtil.showToast("亲，该商品已经下架了哦~");
                return;
            }
            int id = shopCarAdapterModel.getGoodsId();
//            SharePrefHelper.getInstance(this).putSpInt("GOODSFRAGMENTID", 0);
            Intent intent = new Intent(this, GoodsDetailsActivity.class);
            SharePrefHelper.getInstance(this).putSpString("GOODSID", String.valueOf(id));
            startActivity(intent);
        }
    }

    @Override
    public void OnClickMore(int listPosition) {
//        ToastUtil.showToast("调转去凑单" + listPosition);
        startWebViewActivity(Config.SHOPCARADDONITEM + adapter.getResidueDoubleFormat());
    }

    private int id;

    @Override
    public void OnClickClean(int listPosition) {
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(listPosition);
        id = shopCarAdapterModel.getId();
        showDeleteDialog(id);
    }

    private int isCheck;
//    private String productId;

    @Override
    public void OnClickSelected(int listPosition) {
        int firstCheckIds = 0;
        int firstCancelIds = 0;
        StringBuffer sbCheckIds = new StringBuffer();
        StringBuffer sbCancelIds = new StringBuffer();
        ShopCarAdapterModel shopCarSelected = shopCarAdapterList.get(listPosition);
//        String beforeSaleSelected = shopCarSelected.getBeforeSale();
        //选中状态
        int isCheck = shopCarSelected.getIsCheck();
//        if (beforeSaleSelected == null || "".equals(beforeSaleSelected)) {//非预售
            int itmeType = shopCarSelected.getItmeType();
            //选择的店铺id
            int storeId = shopCarSelected.getStore_id();
            if (itmeType == 1) {//非预售全选
                //判断店铺下是否只有一个商品
                int goodsNum = shopCarSelected.getGoodsNum();
                if (goodsNum == 1) {
                    //商品的model
                    ShopCarAdapterModel shopCarGoodsModel = shopCarAdapterList.get(listPosition + 1);
                    int productIdBeforeSale = shopCarGoodsModel.getProductId();
                    if (isCheck == 0) {
                        shopCarSelected.setIsCheck(1);
                        shopCarGoodsModel.setIsCheck(1);
                        isSelected(String.valueOf(productIdBeforeSale), "");
                    } else {
                        shopCarSelected.setIsCheck(0);
                        shopCarGoodsModel.setIsCheck(0);
                        isSelected("", String.valueOf(productIdBeforeSale));
                    }
                    adapter.notifyDataSetChanged();
                    return;
                }
                //同一店铺下多个商品
                for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
                    ShopCarAdapterModel shopCarAdapterGood = shopCarAdapterList.get(i);
                    int productId = shopCarAdapterGood.getProductId();
                    int goodId = shopCarAdapterGood.getStore_id();
                    int enableStore = shopCarAdapterGood.getEnableStore();
                    int goodsOff = shopCarAdapterGood.getGoodsOff();
                    String beforeSale = shopCarAdapterGood.getBeforeSale();
                    int store_id = shopCarAdapterGood.getStore_id();
                    //相同店铺下
                    if (beforeSale != null && !"".equals(beforeSale)) {
//                        if (storeId == store_id) {
//                            ToastUtil.showToast("预售商品请单独下单！");
//                        }
//                        int beforeSaleisCheck = shopCarAdapterGood.getIsCheck();
//                        if (beforeSaleisCheck == 1) {
//                            firstCancelIds++;
//                            if (firstCancelIds == 1) {
//                                sbCancelIds.append(String.valueOf(productId));
//                            } else {
//                                sbCancelIds.append(",");
//                                sbCancelIds.append(String.valueOf(productId));
//                            }
//                            shopCarAdapterGood.setIsCheck(0);
//                        }
                        if (isCheck == 0) {
                            firstCheckIds++;
                            if (firstCheckIds == 1) {
                                sbCheckIds.append(String.valueOf(productId));
                            } else {
                                sbCheckIds.append(",");
                                sbCheckIds.append(String.valueOf(productId));
                            }
                            shopCarAdapterGood.setIsCheck(1);
                        }else {
                            firstCancelIds++;
                            if (firstCancelIds == 1) {
                                sbCancelIds.append(String.valueOf(productId));
                            } else {
                                sbCancelIds.append(",");
                                sbCancelIds.append(String.valueOf(productId));
                            }
                            shopCarAdapterGood.setIsCheck(0);
                        }
                    } else {
                        Log.e("TAG_批量选中", "productId=" + productId);
                        Log.e("TAG_批量选中", "goodId=" + goodId + ";storeId=" + storeId);
                        Log.e("TAG_批量选中", "enableStore=" + enableStore + ";goodsOff=" + goodsOff);
                        if (productId > 0 && goodId == storeId && enableStore > 0 && goodsOff != 1) {
                            if (isCheck == 0) {
                                firstCheckIds++;
                                if (firstCheckIds == 1) {
                                    sbCheckIds.append(String.valueOf(productId));
                                } else {
                                    sbCheckIds.append(",");
                                    sbCheckIds.append(String.valueOf(productId));
                                }
                                shopCarAdapterGood.setIsCheck(1);
                            } else {
                                firstCancelIds++;
                                if (firstCancelIds == 1) {
                                    sbCancelIds.append(String.valueOf(productId));
                                } else {
                                    sbCancelIds.append(",");
                                    sbCancelIds.append(String.valueOf(productId));
                                }
                                shopCarAdapterGood.setIsCheck(0);
                            }
                        }
                    }
                }
            } else {//非预售单选
                shopCarSelected.setIsCheck(isCheck == 1 ? 0 : 1);
                if (isCheck == 0) {
                    //设为选中的id
                    sbCheckIds.append(String.valueOf(shopCarSelected.getProductId()));
//                    for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
//                        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
//                        String beforeSale = shopCarAdapterModel.getBeforeSale();
//                        int productIdCancel = shopCarAdapterModel.getProductId();
//                        int isCheckCancel = shopCarAdapterModel.getIsCheck();
//                        //预售且已选中设为未选中的id
//                        if (beforeSale != null && !"".equals(beforeSale) && isCheckCancel == 1) {
//                            firstCancelIds++;
//                            if (firstCancelIds == 1) {
//                                sbCancelIds.append(String.valueOf(productIdCancel));
//                            } else {
//                                sbCancelIds.append(",");
//                                sbCancelIds.append(String.valueOf(productIdCancel));
//                            }
//                            shopCarAdapterModel.setIsCheck(0);
//                        } else {
//
//                        }
//                    }
                } else {
                    sbCancelIds.append(String.valueOf(shopCarSelected.getProductId()));
                }
            }
//        } else {//预售
//            int productIdSelected = shopCarSelected.getProductId();
//            if (isCheck == 1) {
//                sbCancelIds.append(String.valueOf(productIdSelected));
//                shopCarSelected.setIsCheck(0);
//            } else {
//                sbCheckIds.append(String.valueOf(productIdSelected));
//                shopCarSelected.setIsCheck(1);
//                for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
//                    ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
//                    int productId = shopCarAdapterModel.getProductId();
//                    if (productId != productIdSelected) {
//                        firstCancelIds++;
//                        if (firstCancelIds == 1) {
//                            sbCancelIds.append(String.valueOf(productId));
//                        } else {
//                            sbCancelIds.append(",");
//                            sbCancelIds.append(String.valueOf(productId));
//                        }
//                        shopCarAdapterModel.setIsCheck(0);
//                    }
//                }
//            }
//
//        }
        //修改顶部title和全选状态
        upDataSelected();
        isSelected(sbCheckIds.toString(), sbCancelIds.toString());
        adapter.notifyDataSetChanged();
    }

    //删除
    private void deleteCart(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            params.put("cartId", String.valueOf(id));
            okHttpGet(102, Config.SHOPPCARDELETECART, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            params.put("cartId", String.valueOf(id));
            okHttpGet(102, Config.SHOPPCARDELETECART, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
    }

    /**
     * 选中按钮
     *
     * @param checkIds  选中
     * @param cancelIds 去掉选中
     */
    private void isSelected(String checkIds, String cancelIds) {
        Log.e("TAG_选中", "checkIds=" + checkIds + ";cancelIds=" + cancelIds);
        if (TextUtils.isEmpty(checkIds) && TextUtils.isEmpty(cancelIds)) {
            ToastUtil.showToast("无批量选择商品，预售商品请单独下单！");
            return;
        }
        //总价
        upDataMoney();
        //请求服务器数据
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            if (!TextUtils.isEmpty(checkIds)) {
                params.put("checkIds", checkIds);
            }
            if (!TextUtils.isEmpty(cancelIds)) {
                params.put("cancelIds", cancelIds);
            }
            okHttpGet(101, Config.SHOPPCARONECHECK, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            if (!TextUtils.isEmpty(checkIds)) {
                params.put("checkIds", checkIds);
            }
            if (!TextUtils.isEmpty(cancelIds)) {
                params.put("cancelIds", cancelIds);
            }
            okHttpGet(101, Config.SHOPPCARONECHECK, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
    }

    private void upDataSelected() {
        if (shopCarAdapterList.size() == 2) {
            Log.e("TAG_首次", "j=" + shopCarAdapterList.size());
            String tag = (String) ivStoreNameSelect.getTag();
            ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(0);
            if (tag == null) {
                //title
                shopCarAdapterModel.setIsCheck(1);
                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                //商品
                ShopCarAdapterModel shopCarGood = shopCarAdapterList.get(1);
                int productIdGood = shopCarGood.getProductId();
                shopCarGood.setIsCheck(1);
                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                ivStoreNameSelect.setTag("");
                isSelected(String.valueOf(productIdGood), "");
            } else {
                //title
                shopCarAdapterModel.setIsCheck(0);
                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                //商品
                ShopCarAdapterModel shopCarGood = shopCarAdapterList.get(1);
                int productIdGood = shopCarGood.getProductId();
                shopCarGood.setIsCheck(0);
                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                ivStoreNameSelect.setTag(null);
                isSelected("", String.valueOf(productIdGood));
            }

        } else {
            //临时标记全选按钮状态
            boolean typeCheckAll = true;
////            预售个数
//            int beforeSaleNum = 0;
            //下架个数
            int goodsOffNum = 0;
            //无货个数
            int enableStoreNum = 0;
            //普通选中商品
            int generalNum = 0;
//            //title个数
//            int titleNum = 0;
            //临时存储title位置
            int titlePosition = 0;
            for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
                ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
                int store_id = shopCarAdapterModel.getStore_id();
                int isCheck = shopCarAdapterModel.getIsCheck();
                //预售
                String beforeSale = shopCarAdapterModel.getBeforeSale();
                //顶部title
                int itmeType = shopCarAdapterModel.getItmeType();
                //库存
                int enableStore = shopCarAdapterModel.getEnableStore();
                //是否下架 1下架 0上架
                int goodsOff = shopCarAdapterModel.getGoodsOff();
                if (itmeType == 1) {
                    //预售个数
//                    beforeSaleNum = 0;
                    //下架个数
                    goodsOffNum = 0;
                    //无货个数
                    enableStoreNum = 0;
                    //普通选中商品
                    generalNum = 0;
                    //title个数
//                titleNum = 0;

//                    titleNum++;
                    titlePosition = i;

                } else {
//                    if (beforeSale == null || "".equals(beforeSale)) {//非预售
                        if (goodsOff == 1) {//1下架
                            goodsOffNum++;
                        } else {//上架
                            if (enableStore > 0) {//有库存
                                if (isCheck == 1) {
                                    generalNum++;
                                }
                            } else {//无库存
                                enableStoreNum++;
                            }
                        }
//                    } else {//预售
//                        beforeSaleNum++;
//                    }
                }
                if (i + 1 < j) {
                    ShopCarAdapterModel shopCarNext = shopCarAdapterList.get(i + 1);
                    int store_idNext = shopCarNext.getStore_id();
                    if (store_id != store_idNext) {
                        ShopCarAdapterModel shopCarTitleModel = shopCarAdapterList.get(titlePosition);
                        int goodsNum = shopCarTitleModel.getGoodsNum();
                        if (goodsNum
//                                - beforeSaleNum
                                - goodsOffNum - enableStoreNum == generalNum) {
                            if (generalNum == 0) {//有库存且选中的个数为0
                                shopCarTitleModel.setIsCheck(0);
                                typeCheckAll = false;
                            } else {
                                shopCarTitleModel.setIsCheck(1);
                            }
                        } else {
                            shopCarTitleModel.setIsCheck(0);
                            typeCheckAll = false;
                        }
//                        Log.e("TAG_选择shopcar", "goodsNum=" + goodsNum + ";预售=" + beforeSaleNum + ";下架=" + goodsOffNum + ";库存=" + enableStoreNum);
//                        Log.e("TAG_选择shopcar", "选中1=" + generalNum);
//                        Log.e("TAG_选择shopcar", "选中2=" + (goodsNum - beforeSaleNum - goodsOffNum - enableStoreNum));
                    }
                } else if (i + 1 == j) {
                    ShopCarAdapterModel shopCarTitleModel = shopCarAdapterList.get(titlePosition);
                    int goodsNum = shopCarTitleModel.getGoodsNum();
                    if (goodsNum
//                            - beforeSaleNum
                            - goodsOffNum - enableStoreNum == generalNum) {
                        if (generalNum == 0) {//有库存且选中的个数为0
                            shopCarTitleModel.setIsCheck(0);
                            typeCheckAll = false;
                        } else {
                            shopCarTitleModel.setIsCheck(1);
                        }
                    } else {
                        shopCarTitleModel.setIsCheck(0);
                        typeCheckAll = false;
                    }
//                    Log.e("TAG_选择shopcar", "goodsNum=" + goodsNum + ";预售=" + beforeSaleNum + ";下架=" + goodsOffNum + ";库存=" + enableStoreNum);
//                    Log.e("TAG_选择shopcar", "选中1=" + generalNum);
//                    Log.e("TAG_选择shopcar", "选中2=" + (goodsNum - beforeSaleNum - goodsOffNum - enableStoreNum));
                }
            }

            if (!typeCheckAll) {
                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
            } else {
                ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void upDataMoney() {
        double allNeedPayMoney = 0;
        for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
            ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
            int itmeType = shopCarAdapterModel.getItmeType();
            int isCheck = shopCarAdapterModel.getIsCheck();
            Log.e("TAG_进货单","itmeType="+itmeType+";isCheck"+isCheck);
            if (itmeType == 2 && isCheck == 1) {
                double price = shopCarAdapterModel.getPrice();
                int num = shopCarAdapterModel.getNum();
                Log.e("TAG_进货单","itmeType="+itmeType+";isCheck"+isCheck+";num="+num);
                BigDecimal b1 = new BigDecimal(Double.toString(allNeedPayMoney));
                BigDecimal b2 = new BigDecimal(Double.toString(price * num));
                allNeedPayMoney = b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }
        //总价格
        tvNeedPayMoney.setText("￥" + String.format("%.2f", allNeedPayMoney));
    }

    @Override
    public void OnClickAdd(int listPosition) {
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(listPosition);
        int addIsCheck = shopCarAdapterModel.getIsCheck();
        if (addIsCheck == 1) {//选中
            String beforeSale = shopCarAdapterModel.getBeforeSale();
            Log.e("TAG_shopcar", "点击添加");
            int enableStore = 0;
            if (beforeSale == null || "".equals(beforeSale)) {
                enableStore = shopCarAdapterModel.getEnableStore();
            } else {
                enableStore = 2000000;
            }
            int id = shopCarAdapterModel.getId();
            int goodsId = shopCarAdapterModel.getGoodsId();
            int productId = shopCarAdapterModel.getProductId();
            int step = shopCarAdapterModel.getStep();
            int num = shopCarAdapterModel.getNum();
            int sumNum = num + step;
            if (sumNum > enableStore) {
                if (updateNumNotifyDialog == null) {
                    showLazyWeightDialog(enableStore);
                } else {
                    updateNumNotifyDialog.dismiss();
                    showLazyWeightDialog(enableStore);
                }
            } else {
                Log.e("TAG_shopcar", "shopCarAdapterModel=" + shopCarAdapterModel.toString());
                int limitnum = shopCarAdapterModel.getLimitnum();
                if (limitnum <= 0) {
                    shopCarAdapterModel.setNum(sumNum);
                    updateNum(id, goodsId, productId, sumNum, step, listPosition);
                } else {
                    if (limitnum >= sumNum) {
                        shopCarAdapterModel.setNum(sumNum);
                        updateNum(id, goodsId, productId, sumNum, step, listPosition);
                    } else {
                        ToastUtil.showToast("该商品限购" + limitnum + "件");
                    }
                }
            }
        } else {
            ToastUtil.showToast("请选中该商品！");
        }
    }

    protected AlertDialog updateNumNotifyDialog;

    private void showLazyWeightDialog(int residueNum) {
        Log.e("TAG_更新数量", "调用");
        LayoutInflater factor = (LayoutInflater) ShopCarActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_shopcarlazyweight, null);

        TextView agree = (TextView) serviceView.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNumNotifyDialog.dismiss();
            }
        });
        TextView tvUpdataNum = (TextView) serviceView.findViewById(R.id.et_dialoglazyweight);
        tvUpdataNum.setText("库存剩余" + residueNum + "件");
        Activity activity = ShopCarActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        updateNumNotifyDialog = builder.create();
        updateNumNotifyDialog.setCancelable(false);
        updateNumNotifyDialog.setCanceledOnTouchOutside(false);
        updateNumNotifyDialog.show();
        updateNumNotifyDialog.setContentView(serviceView);
    }

    /**
     * 修改购物项数量
     *
     * @param cartId id
     * @param num    修改数量
     * @param step   步长，对应做小起订量
     */
    private void updateNum(int cartId, int goodsId, int productId, int num, int step, int updataPosition) {
        String price = "";
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(updataPosition);
        if (shopCarWholeList != null || shopCarWholeList.size() > 0) {
            for (int i = 0, j = shopCarWholeList.size(); i < j; i++) {
                List<ShopCarWholeModel.DataBean> dataBeen = shopCarWholeList.get(i);
                for (int k = 0, l = dataBeen.size(); k < l; k++) {
                    ShopCarWholeModel.DataBean shopCarWholeModel = dataBeen.get(k);
                    int wholeGoodsId = shopCarWholeModel.getGoodsId();
                    int wholeProductId = shopCarWholeModel.getProductId();
                    if (goodsId == wholeGoodsId && productId == wholeProductId) {
                        int minNum = shopCarWholeModel.getMinNum();
                        int maxNum = shopCarWholeModel.getMaxNum();
                        if (num >= minNum && num <= maxNum) {
                            double activityPrice = shopCarWholeModel.getActivityPrice();
                            if (activityPrice > 0) {
                                price = String.format("%.2f", activityPrice);
                            } else {
                                double wholesalePrice = shopCarWholeModel.getWholesalePrice();
                                price = String.format("%.2f", wholesalePrice);
                            }
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(price)) {
                shopCarAdapterModel.setPrice(Double.valueOf(price));
            }
            adapter.notifyItemChanged(updataPosition);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
            return;
        }
        params.put("cartId", String.valueOf(cartId));
        params.put("num", String.valueOf(num));
        params.put("step", String.valueOf(step));
        if (!TextUtils.isEmpty(price)) {
            Log.e("TAG_修改价格", "price=" + price);
            params.put("price", price);
        }
        okHttpGet(104, Config.SHOPPCARUPDATENUM, params);
        //总价
        upDataMoney();
    }

    @Override
    public void OnClickSubtract(int listPosition) {
        Log.e("TAG_shopcar", "点击减");
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(listPosition);
        int addIsCheck = shopCarAdapterModel.getIsCheck();
        if (addIsCheck == 1) {//选中
            int id = shopCarAdapterModel.getId();
            int goodsId = shopCarAdapterModel.getGoodsId();
            int productId = shopCarAdapterModel.getProductId();
            int step = shopCarAdapterModel.getStep();
            int smallSale = shopCarAdapterModel.getSmallSale();
            if (smallSale < 0) {
                smallSale = 1;
            }
            int num = shopCarAdapterModel.getNum();
            int sumNum = num - step;
            if (sumNum >= smallSale) {
                shopCarAdapterModel.setNum(sumNum);
                adapter.notifyItemChanged(listPosition);
                updateNum(id, goodsId, productId, sumNum, step, listPosition);
            } else {
//                if (sumNum+step==smallSale){
//
//                }else
                if (sumNum + step < smallSale) {
                    shopCarAdapterModel.setNum(smallSale);
                    adapter.notifyItemChanged(listPosition);
                    updateNum(id, goodsId, productId, smallSale, step, listPosition);
                }
                ToastUtil.showToast("该商品最小起订量为" + smallSale + "件！");
            }
        } else {
            ToastUtil.showToast("请选中该商品！");
        }
    }

    @Override
    public void setOnTouchListener(int listPosition) {
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(listPosition);
        int num = shopCarAdapterModel.getNum();
        upDataNumDialog(num, listPosition);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    protected AlertDialog deleteNotifyDialog;

    private void showDeleteDialog(final int id) {

        LayoutInflater factor = (LayoutInflater) ShopCarActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_shopcardelete, null);

        TextView agree = (TextView) serviceView.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotifyDialog.dismiss();
                Log.e("TAG_进货单","删除="+id);
                deleteCart(id);
            }
        });
        TextView refuse = (TextView) serviceView.findViewById(R.id.refuse);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotifyDialog.dismiss();
            }
        });
        Activity activity = ShopCarActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        deleteNotifyDialog = builder.create();
        deleteNotifyDialog.setCancelable(false);
        deleteNotifyDialog.setCanceledOnTouchOutside(false);
        deleteNotifyDialog.show();
        deleteNotifyDialog.setContentView(serviceView);
    }

    protected AlertDialog upDataNumNotifyDialog;
    private LinearLayout llDialogSubtractNum, llDialogAddNum;
    private EditText etDialogGoodsNum;
    private ProgressBar progressTxt;

    private void upDataNumDialog(int num, final int position) {
        LayoutInflater factor = (LayoutInflater) ShopCarActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_shopcarupdatanum, null);
        progressTxt = (ProgressBar) serviceView.findViewById(R.id.progress_txt);
        progressTxt.setVisibility(View.GONE);
        llDialogSubtractNum = (LinearLayout) serviceView.findViewById(R.id.ll_dialogSubtractNum);
        llDialogSubtractNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etNum = etDialogGoodsNum.getText().toString();
                ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(position);
                int smallSale = shopCarAdapterModel.getSmallSale();
                int step = shopCarAdapterModel.getStep();
                if (Integer.valueOf(TextUtils.isEmpty(etNum)?"0":etNum)-step<smallSale){
                    etDialogGoodsNum.setText(String.valueOf(smallSale));
                    ToastUtil.showToast("该商品最小起订量为" + smallSale + "件");
                }else {
                    int rema = (Integer.valueOf(TextUtils.isEmpty(etNum)?"0":etNum) - smallSale) % step;
                    if (rema == 0) {
                        int etSubtractNum = Integer.valueOf(TextUtils.isEmpty(etNum)?"0":etNum) - step;
                        if (etSubtractNum > 0) {
                            etDialogGoodsNum.setText(String.valueOf(etSubtractNum));
                        }
                    } else {
                        int etSubtractNum = Integer.valueOf(TextUtils.isEmpty(etNum)?"0":etNum) - rema;
                        if (etSubtractNum > 0) {
                            etDialogGoodsNum.setText(String.valueOf(etSubtractNum));
                        }
                    }
                }
            }
        });
        llDialogAddNum = (LinearLayout) serviceView.findViewById(R.id.ll_dialogAddNum);
        llDialogAddNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etNum = etDialogGoodsNum.getText().toString();
                ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(position);
                int step = shopCarAdapterModel.getStep();
                int smallSale = shopCarAdapterModel.getSmallSale();
                String beforeSale = shopCarAdapterModel.getBeforeSale();
                Log.e("TAG_shopcar", "点击添加");
                int enableStore = 0;
                if (beforeSale == null || "".equals(beforeSale)) {
                    enableStore = shopCarAdapterModel.getEnableStore();
                } else {
                    enableStore = 2000000;
                }
                int rema = (Integer.valueOf(TextUtils.isEmpty(etNum)?"0":etNum) - smallSale) % step;
                if (rema == 0) {
                    int etAddNum = Integer.valueOf(TextUtils.isEmpty(etNum)?"0":etNum) + step;
                    if (etAddNum > enableStore) {
                        ToastUtil.showToast("剩余库存已到上限！");
                    } else {
                        etDialogGoodsNum.setText(String.valueOf(etAddNum));
                    }
                } else {
                    int etAddNum = Integer.valueOf(TextUtils.isEmpty(etNum)?"0":etNum) - rema + step;
                    if (etAddNum > enableStore) {
                        ToastUtil.showToast("剩余库存已到上限！");
                    } else {
                        if (etAddNum>=smallSale){
                            etDialogGoodsNum.setText(String.valueOf(etAddNum));
                        }else {
                            etDialogGoodsNum.setText(String.valueOf(smallSale));
                        }
                    }
                }
            }
        });
        etDialogGoodsNum = (EditText) serviceView.findViewById(R.id.et_dialogGoodsNum);
        etDialogGoodsNum.setText(String.valueOf(num));
        TextView agree = (TextView) serviceView.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etNum =TextUtils.isEmpty(etDialogGoodsNum.getText())?"0": etDialogGoodsNum.getText().toString();
                dialogUpdataNum(position, etNum);
            }
        });
        TextView refuse = (TextView) serviceView.findViewById(R.id.refuse);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upDataNumNotifyDialog.dismiss();
            }
        });
        Activity activity = ShopCarActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        upDataNumNotifyDialog = builder.create();
        upDataNumNotifyDialog.setCancelable(false);
        upDataNumNotifyDialog.setCanceledOnTouchOutside(false);
        upDataNumNotifyDialog.show();
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        Window window = upDataNumNotifyDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        upDataNumNotifyDialog.setContentView(serviceView);
    }

    private void dialogUpdataNum(int position, String editNum) {
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(position);
        int id = shopCarAdapterModel.getId();
        int goodsId = shopCarAdapterModel.getGoodsId();
        int productId = shopCarAdapterModel.getProductId();
        int step = shopCarAdapterModel.getStep();
        String beforeSale = shopCarAdapterModel.getBeforeSale();
        Log.e("TAG_shopcar", "点击添加");
        int enableStore = 0;
        if (beforeSale == null || "".equals(beforeSale)) {
            enableStore = shopCarAdapterModel.getEnableStore();
        } else {
            enableStore = 2000000;
        }
        //最小起订量
        int smallSale = shopCarAdapterModel.getSmallSale();
        //输入商品数量
        Integer integer = Integer.valueOf(editNum);
        Log.e("TAG_软键盘num", "integer=" + integer + ";step=" + step + ";position=" + position + ";enableStore" + enableStore);
        if (enableStore >= integer) {
            if (integer >=smallSale){
                if (integer > 0 && (integer-smallSale) % step == 0) {
                    Log.e("TAG_shopcar", "shopCarAdapterModel=" + shopCarAdapterModel.toString());
                    int limitnum = shopCarAdapterModel.getLimitnum();
                    if (limitnum <= 0) {
                        shopCarAdapterModel.setNum(integer);
                        updateNum(id, goodsId, productId, integer, step, position);
                    } else {
                        if (limitnum >= integer) {
                            shopCarAdapterModel.setNum(integer);
                            updateNum(id, goodsId, productId, integer, step, position);
                        } else {
                            ToastUtil.showToast("该商品限购" + limitnum + "件");
                        }
                    }
                } else {
                    ToastUtil.showToast("请输入正确数量");
                }
            }else {
                ToastUtil.showToast("该商品最小起订量为" + smallSale + "件");
            }
        } else {
            ToastUtil.showToast("库存剩余" + enableStore + "件");
        }
    }
}