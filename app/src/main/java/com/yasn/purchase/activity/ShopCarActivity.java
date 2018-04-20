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
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.adapter.ShopCarAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.help.ShopCarUtils;
import com.yasn.purchase.listener.OnShopCarClickListener;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.model.ShopCarAdapterModel;
import com.yasn.purchase.model.ShopCarModel;
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

import static com.yasn.purchase.R.id.iv_StoreNameSelect;


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
    private LinearLayout llShopcarNodata;

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
    @Override
    protected Object getTopbarTitle() {
        return R.string.order;
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
        OkHttpDemand();
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
        ivStoreNameSelect = (ImageView) findViewById(iv_StoreNameSelect);
        ivStoreNameSelect.setOnClickListener(this);
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
            case iv_StoreNameSelect:
                initImageStoreNameSelect();
                break;
            case R.id.tv_addShopCar:
                boolean isCheckBeforeSale = false;//是否存在预售商品
                int isCheckNum = 0;//选中商品数量
                for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
                    ShopCarAdapterModel shopCarAdapterGood = shopCarAdapterList.get(i);
                    String beforeSale = shopCarAdapterGood.getBeforeSale();
                    int isCheck = shopCarAdapterGood.getIsCheck();
                    int itmeType = shopCarAdapterGood.getItmeType();
                    if (isCheck==1&&itmeType==2){//选中
                        if (beforeSale != null&&!"".equals(beforeSale)){
                            //预售
                            isCheckBeforeSale = true;
                        }
                        isCheckNum++;
                    }
                }
                if (isCheckBeforeSale&&isCheckNum>1){
                    ToastUtil.showToast("预售商品请单独提交订单！");
                }else {
                    startWebViewActivity(Config.CHECKOUTSHOPCAR);
                }
                break;
        }
    }

    private void initImageStoreNameSelect() {
        StringBuffer tag = (StringBuffer) ivStoreNameSelect.getTag();
        int first = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
            ShopCarAdapterModel shopCarAdapterGood = shopCarAdapterList.get(i);
            int productId = shopCarAdapterGood.getProductId();
            int enableStore = shopCarAdapterGood.getEnableStore();
            int goodsOff = shopCarAdapterGood.getGoodsOff();
            int itmeType = shopCarAdapterGood.getItmeType();
            String beforeSale = shopCarAdapterGood.getBeforeSale();
            int isCheck = shopCarAdapterGood.getIsCheck();
            if (beforeSale!=null&&!"".equals(beforeSale)){//预售已选中
                if (isCheck==1){
                    Log.e("TAG_进货单11111","isSelected=====");
                    isSelected(0, String.valueOf(productId));
                }
            }else {
                if (productId > 0 && itmeType == 2 && enableStore > 0 && goodsOff != 1) {
                    first++;
                    if (first == 1) {
                        sb.append(String.valueOf(productId));
                    } else {
                        sb.append(",");
                        sb.append(String.valueOf(productId));
                    }
                }
                if (tag != null) {//已全选，设置全部未选
                    shopCarAdapterGood.setIsCheck(0);
                } else {
                    shopCarAdapterGood.setIsCheck(1);
                }
            }
        }
        productId = sb.toString();
        if (tag != null) {//已全选，设置全部未选
            Log.e("TAG_进货单22222","isSelected=====");
            isSelected(0, String.valueOf(productId));
            ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
            ivStoreNameSelect.setTag(null);
        } else {
            Log.e("TAG_进货单33333","isSelected=====");
            isSelected(1, String.valueOf(productId));
            ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
            ivStoreNameSelect.setTag(sb);
        }
        adapter.notifyDataSetChanged();
    }

    private void startWebViewActivity(String url) {

        if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
            Intent intent = new Intent(ShopCarActivity.this, WebViewActivity.class);
            intent.putExtra("webViewUrl", url);
            startActivity(intent);
        }
    }

    private List<ShopCarAdapterModel> shopCarAdapterList;

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        EventBus.getDefault().post(new EventBusMsg("Success"));
        switch (requestCode) {
            case 100:
                mSwipeRefreshLayout.setRefreshing(false);
                if (returnCode == 200) {
                    int storeprice1 = returnData.indexOf("storeprice");
                    if (storeprice1 == -1) {//空购物车
                        initShopCarNoData();
                    } else {
                        initShopCarData(returnData);
                    }

                } else if (returnCode == 401) {
                    cleanToken();
                    OkHttpDemand();
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101:
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
                    Log.e("TAG_进货单44444","isSelected=====");
                    isSelected(isCheck == 1 ? 0 : 1, productId);
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 102:
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
                    deleteCart(id);
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 104:
                if (returnCode == 200) {
                    JSONObject object = null;
                    try {
                        object = new JSONObject(returnData);
                        int code = object.optInt("code");
                        if (code == 200) {
                            OkHttpDemand();
                        }else {
                            OkHttpDemand();
                            ToastUtil.showToast(returnMsg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (returnCode == 401) {
                    cleanToken();
                    OkHttpDemand();
                } else {
                    ToastUtil.showToast(returnMsg);
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
                if (goodslist != null && goodslist.size() > 0) {
                    //相同店铺是否全部选中
                    int typeIsCheck = 0;
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
                        //相同店铺是否全部选中
                        if (isCheck == 1) {
                            typeIsCheck++;
                            storeCheckPrice = storeCheckPrice + price;
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
                        if (beforeSale==null||"".equals(beforeSale)){
                            shopCarAdapterModel.setBeforeSale("");
                        }else {//预售
                            shopCarAdapterModel.setBeforeSale(beforeSale);
                            Log.e("TAG_进货单55555","isSelected=====");
//                            isSelected(0,String.valueOf(productId));
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
                    if (typeIsCheck == goodslist.size()) {
                        shopCarTitleModel.setIsCheck(1);
                    } else {
                        shopCarTitleModel.setIsCheck(0);
                    }
                    shopCarTitleModel.setStoreCheckPrice(storeCheckPrice);
                }
            }
        }
        adapter.setData(shopCarAdapterList);
        llShopcarNodata.setVisibility(View.GONE);
        //判断是否有下架商品
        upSelectGoodsOff();
        //全选状态
//        initImageStoreNameSelect();
    }

    private void upSelectGoodsOff() {
        String productIds = null;
        int first = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
            ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
            int itmeType = shopCarAdapterModel.getItmeType();

            if (itmeType == 2) {
                int goodsOff = shopCarAdapterModel.getGoodsOff();//1已下架
                int isCheck = shopCarAdapterModel.getIsCheck();
                int enableStore = shopCarAdapterModel.getEnableStore();

                if (goodsOff == 1) {
                    Log.e("TAG_下架1", "goodsOff=" + goodsOff + ";enableStore=" + enableStore + ";isCheck=" + isCheck);
                    if (isCheck == 1) {
                        int productId = shopCarAdapterModel.getProductId();
                        first++;
                        if (first == 1) {
                            sb.append(String.valueOf(productId));
                        } else {
                            sb.append(",");
                            sb.append(String.valueOf(productId));
                        }
                        productIds = sb.toString();
                        Log.e("TAG_下架2", "sb=" + sb.toString() + "===first=" + first);
                    }
                }
            }
        }
        if (first == 0) {
            upDataMoney();
        } else {
            Log.e("TAG_进货单66666","isSelected=====");
            isSelected(0, productIds);
        }
    }

    @Override
    public void onCancelResult() {
        EventBus.getDefault().post(new EventBusMsg("error"));
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        EventBus.getDefault().post(new EventBusMsg("error"));
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        EventBus.getDefault().post(new EventBusMsg("error"));
    }

    @Override
    public void onFinishResult() {
        EventBus.getDefault().post(new EventBusMsg("error"));
    }

    @Override
    public void onRefresh() {
        OkHttpDemand();
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
                    HelpUtils.loge("TAG_列表","returnData="+returnData);
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (returnData !=null&&!"".equals(returnData)){
                        int storeprice1 = returnData.indexOf("storeprice");
                        if (storeprice1 == -1) {//空购物车
                            initShopCarNoData();
                        } else {
                            initShopCarData(returnData);
                        }
                    }
                    else {
                        ToastUtil.showToast("数据请求异常！");
                    }
                    if (progressTxt!=null){
                        progressTxt.setVisibility(View.GONE);
                    }
                    if (upDataNumNotifyDialog!=null&&upDataNumNotifyDialog.isShowing()){
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
            SharePrefHelper.getInstance(this).putSpInt("GOODSFRAGMENTID", 0);
            Intent intent = new Intent(this, GoodsDetailsActivity.class);
            SharePrefHelper.getInstance(this).putSpString("GOODSID", String.valueOf(id));
            startActivity(intent);
        }
    }

    @Override
    public void OnClickMore(int listPosition) {
//        ToastUtil.showToast("调转去凑单" + listPosition);
        startWebViewActivity(Config.SHOPCARADDONITEM+adapter.getResidueDoubleFormat());
    }

    private int id;

    @Override
    public void OnClickClean(int listPosition) {
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(listPosition);
        id = shopCarAdapterModel.getId();
        showDeleteDialog(id);
    }

    private int isCheck;
    private String productId;

    @Override
    public void OnClickSelected(int listPosition) {
        ShopCarAdapterModel shopCarSelected = shopCarAdapterList.get(listPosition);
        String beforeSaleSelected = shopCarSelected.getBeforeSale();
        if (beforeSaleSelected==null||"".equals(beforeSaleSelected)){//非预售
            int itmeType = shopCarSelected.getItmeType();
            isCheck = shopCarSelected.getIsCheck();
            int storeId = shopCarSelected.getStore_id();
            if (itmeType == 1) {
                int first = 0;
                StringBuffer sb = new StringBuffer();
                for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
                    ShopCarAdapterModel shopCarAdapterGood = shopCarAdapterList.get(i);
                    int productId = shopCarAdapterGood.getProductId();
                    int goodId = shopCarAdapterGood.getStore_id();
                    int enableStore = shopCarAdapterGood.getEnableStore();
                    int goodsOff = shopCarAdapterGood.getGoodsOff();
                    String beforeSale = shopCarAdapterGood.getBeforeSale();
                    if (beforeSale!=null&&!"".equals(beforeSale)){
                        Log.e("TAG_进货单77777","isSelected=====");
                        isSelected(0, String.valueOf(productId));
                    }else {
                        if (productId > 0 && goodId == storeId && enableStore > 0 && goodsOff != 1) {
                            first++;
                            if (first == 1) {
                                sb.append(String.valueOf(productId));
                            } else {
                                sb.append(",");
                                sb.append(String.valueOf(productId));
                            }
                        }
                        if (goodId == storeId) {
                            shopCarAdapterGood.setIsCheck(isCheck == 1 ? 0 : 1);
                        }
                    }

                }
                if (sb!=null){
                    productId = sb.toString();
                    Log.e("TAG_进货单88888","isSelected=====");
                    isSelected(isCheck == 1 ? 0 : 1, String.valueOf(productId));
                }
            } else {
                shopCarSelected.setIsCheck(isCheck == 1 ? 0 : 1);
                productId = String.valueOf(shopCarSelected  .getProductId());
                Log.e("TAG_进货单99999","isSelected=====");
                isSelected(isCheck == 1 ? 0 : 1, productId);
                for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
                    ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
                    String beforeSale = shopCarAdapterModel.getBeforeSale();
                    int isCheck = shopCarAdapterModel.getIsCheck();
                    if (beforeSale!=null&&!"".equals(beforeSale)&&isCheck==1){
                        Log.e("TAG_进货单10101010","isSelected=====");
                        isSelected(0, String.valueOf(shopCarAdapterModel.getProductId()));
                    }
                }
            }
        }else {//预售
            int isCheck = shopCarSelected.getIsCheck();
            int productIdSelected = shopCarSelected.getProductId();
            if (isCheck==1){
                Log.e("TAG_进货单1=1=1=1=1","isSelected=====");
                isSelected(0, String.valueOf(productIdSelected));
            }else {
                Log.e("TAG_进货单2=2=2=2=2=2=2","isSelected=====");
                isSelected(1, String.valueOf(productIdSelected));
                int first = 0;
                StringBuffer sb = new StringBuffer();
                for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
                    ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
                    int productId = shopCarAdapterModel.getProductId();
                    if (productId !=productIdSelected){
                        first++;
                        if (first == 1) {
                            sb.append(String.valueOf(productId));
                        } else {
                            sb.append(",");
                            sb.append(String.valueOf(productId));
                        }
                    }
                }
                productId = sb.toString();
                Log.e("TAG_进货单3=3==3=3=3=3","isSelected=====");
                isSelected(0, String.valueOf(productId));
            }

        }
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

    //选择
    private void isSelected(int isCheck, String productId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            params.put("checked", String.valueOf(isCheck));
            params.put("productIds", productId);
            okHttpGet(101, Config.SHOPPCARONECHECK, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            params.put("checked", String.valueOf(isCheck));
            params.put("productIds", productId);
            okHttpGet(101, Config.SHOPPCARONECHECK, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
    }

    private void upDataMoney() {
        double allNeedPayMoney = 0;
        for (int i = 0, j = shopCarAdapterList.size(); i < j; i++) {
            ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(i);
            int itmeType = shopCarAdapterModel.getItmeType();
            int isCheck = shopCarAdapterModel.getIsCheck();
            if (itmeType == 2 && isCheck == 1) {
                double price = shopCarAdapterModel.getPrice();
                int num = shopCarAdapterModel.getNum();
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
            if (beforeSale==null||"".equals(beforeSale)){
                enableStore = shopCarAdapterModel.getEnableStore();
            }else {
                enableStore = 2000000;
            }
            int id = shopCarAdapterModel.getId();
            int step = shopCarAdapterModel.getStep();
            int num = shopCarAdapterModel.getNum();
            int sumNum = num + step;
            if (sumNum > enableStore) {
                if (updateNumNotifyDialog ==null){
                    showLazyWeightDialog(enableStore);
                }else {
                    updateNumNotifyDialog.dismiss();
                    showLazyWeightDialog(enableStore);
                }

            } else {
                shopCarAdapterModel.setNum(sumNum);
                adapter.notifyItemChanged(listPosition);
                Log.e("TAG_shopcar", "shopCarAdapterModel=" + shopCarAdapterModel.toString());
                updateNum(id, sumNum, step);
            }
        } else {
            ToastUtil.showToast("请选选中该商品！");
        }
    }

    protected AlertDialog updateNumNotifyDialog;

    private void showLazyWeightDialog(int residueNum) {
        Log.e("TAG_更新数量","调用");
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
    private void updateNum(int cartId, int num, int step) {
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
        okHttpGet(104, Config.SHOPPCARUPDATENUM, params);
    }

    @Override
    public void OnClickSubtract(int listPosition) {
        Log.e("TAG_shopcar", "点击删除");
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(listPosition);
        int addIsCheck = shopCarAdapterModel.getIsCheck();
        if (addIsCheck == 1) {//选中
            int id = shopCarAdapterModel.getId();
            int step = shopCarAdapterModel.getStep();
            int num = shopCarAdapterModel.getNum();
            int sumNum = num - step;
            if (sumNum > 0) {
                shopCarAdapterModel.setNum(sumNum);
                adapter.notifyItemChanged(listPosition);
                updateNum(id, sumNum, step);
            }
        } else {
            ToastUtil.showToast("请选选中该商品！");
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
                int step = shopCarAdapterModel.getStep();
                int rema = Integer.valueOf(etNum)%step;
                if (rema==0){
                    int etSubtractNum = Integer.valueOf(etNum)-step;
                    if (etSubtractNum>0){
                        etDialogGoodsNum.setText(String.valueOf(etSubtractNum));
                    }
                }else {
                    int etSubtractNum = Integer.valueOf(etNum)-rema;
                    if (etSubtractNum>0){
                        etDialogGoodsNum.setText(String.valueOf(etSubtractNum));
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
                String beforeSale = shopCarAdapterModel.getBeforeSale();
                Log.e("TAG_shopcar", "点击添加");
                int enableStore = 0;
                if (beforeSale==null||"".equals(beforeSale)){
                    enableStore = shopCarAdapterModel.getEnableStore();
                }else {
                    enableStore = 2000000;
                }
                int rema = Integer.valueOf(etNum)%step;
                if (rema==0){
                    int etAddNum = Integer.valueOf(etNum)+step;
                    if (etAddNum>enableStore){
                        ToastUtil.showToast("剩余库存已到上限！");
                    }else {
                        etDialogGoodsNum.setText(String.valueOf(etAddNum));
                    }
                }else {
                    int etAddNum = Integer.valueOf(etNum)-rema+step;
                    if (etAddNum>enableStore){
                        ToastUtil.showToast("剩余库存已到上限！");
                    }else {
                        etDialogGoodsNum.setText(String.valueOf(etAddNum));
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
//                dialogshow();
                String etNum = etDialogGoodsNum.getText().toString();
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
        int step = shopCarAdapterModel.getStep();
        String beforeSale = shopCarAdapterModel.getBeforeSale();
        Log.e("TAG_shopcar", "点击添加");
        int enableStore = 0;
        if (beforeSale==null||"".equals(beforeSale)){
            enableStore = shopCarAdapterModel.getEnableStore();
        }else {
            enableStore = 2000000;
        }
        //输入商品数量
        Integer integer = Integer.valueOf(editNum);
        Log.e("TAG_软键盘num", "integer=" + integer+";step="+step + ";position=" + position+";enableStore"+enableStore);
        if (integer > 0 && enableStore >= integer && integer % step == 0) {
            shopCarAdapterModel.setNum(integer);
            updateNum(id, integer, step);
        } else {
            ToastUtil.showToast("请输入正确数量");
        }

    }
}