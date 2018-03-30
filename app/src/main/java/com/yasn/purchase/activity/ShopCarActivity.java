package com.yasn.purchase.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.ShopCarAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnShopCarClickListener;
import com.yasn.purchase.model.ShopCarAdapterModel;
import com.yasn.purchase.model.ShopCarModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;
import com.yasn.purchase.view.RcDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;


public class ShopCarActivity extends SimpleTopbarActivity implements OnShopCarClickListener, SwipeRefreshLayout.OnRefreshListener {

    private MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout llAnnouncement;
    private TextView tvAnnouncement;
    private RecyclerView rcShopCar;
    private  ShopCarAdapter adapter;
    private  LinearLayout llPayorder;
    private TextView tvNeedPayMoney;
    private void OkHttpDemand() {
        token = SharePrefHelper.getInstance(ShopCarActivity.this).getSpString("token");
        resetToken = SharePrefHelper.getInstance(ShopCarActivity.this).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(ShopCarActivity.this).getSpString("resetTokenTime");
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            okHttpGet(100, "http://192.168.50.166:9100/api/composite-service/cart/get-cart-list", params);
//            okHttpGet(100, Config.SHOPPCAR, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            okHttpGet(100, "http://192.168.50.166:9100/api/composite-service/cart/get-cart-list", params);
//            okHttpGet(100, Config.SHOPPCAR, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
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
        rcShopCar.addItemDecoration(new RcDecoration(this,RcDecoration.VERTICAL_LIST));
        adapter.notifyDataSetChanged();
        rcShopCar.setAdapter(adapter);
        //底部提交订单
        llPayorder = (LinearLayout) findViewById(R.id.ll_Payorder);
        llPayorder.setVisibility(View.GONE);
        //总价格
        tvNeedPayMoney = (TextView) findViewById(R.id.tv_needPayMoney);
    }
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
    }

    private List<ShopCarAdapterModel> shopCarAdapterList;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                mSwipeRefreshLayout.setRefreshing(false);
                if (returnCode == 200) {
                   shopCarAdapterList = new ArrayList<>();
                    ShopCarModel shopCarModel = JSON.parseObject(returnData, ShopCarModel.class);
                    List<ShopCarModel.DataBean> data = shopCarModel.getData();
                    if (data!=null&&data.size()>0){
                        for (int i = 0,j = data.size(); i < j; i++) {
                            ShopCarAdapterModel shopCarTitleModel = new ShopCarAdapterModel();
                            ShopCarModel.DataBean dataBean = data.get(i);
                            //店铺id
                            int store_id = dataBean.getStore_id();
                            shopCarTitleModel.setStore_id(store_id);
                            //店铺名称
                            String store_name = dataBean.getStore_name();
                            shopCarTitleModel.setItmeType(1);
                            shopCarTitleModel.setStoreName("店铺名称："+store_name);
                            ShopCarModel.DataBean.StorepriceBean storeprice = dataBean.getStoreprice();
                            //总价格
                            double needPayMoney = storeprice.getNeedPayMoney();
                            tvNeedPayMoney.setText("￥"+String.valueOf(needPayMoney));
                            //包邮价格
                            double freeShipMoney = storeprice.getFreeShipMoney();
                            shopCarTitleModel.setFreeShipMoney(String.valueOf(freeShipMoney));
                            shopCarAdapterList.add(shopCarTitleModel);
                            //店铺商品列表
                            List<ShopCarModel.DataBean.GoodslistBean> goodslist = dataBean.getGoodslist();
                            if (goodslist !=null&&goodslist.size()>0){
                                //相同店铺是否全部选中
                                int typeIsCheck = 0;
                                //相同店铺选中总价
                                double storeCheckPrice = 0;
                                for (int k = 0,l = goodslist.size(); k < l; k++) {
                                    ShopCarAdapterModel shopCarAdapterModel = new ShopCarAdapterModel();
                                    ShopCarModel.DataBean.GoodslistBean goodslistBean = goodslist.get(k);
                                    shopCarAdapterModel.setStore_id(store_id);
                                    //商品名字
                                    String name = goodslistBean.getName();
                                    shopCarAdapterModel.setName(name);
                                    ShopCarModel.DataBean.GoodslistBean.OthersBean others = goodslistBean.getOthers();
                                    //商品规格
                                    List<ShopCarModel.DataBean.GoodslistBean.OthersBean.SpecListBean> specList = others.getSpecList();
                                    if (specList !=null&&specList.size()>0){
                                        List<String> specListValue =new ArrayList();
                                        for (int m = 0,n = specList.size(); m < n; m++) {
                                            ShopCarModel.DataBean.GoodslistBean.OthersBean.SpecListBean specListBean = specList.get(m);
                                            String value = specListBean.getValue();
                                            Log.e("TAG_进货单标签Activity","value="+value);
                                            specListValue.add(value);
                                        }
                                        shopCarAdapterModel.setSpecList(specListValue);
                                    }
                                    //图片
                                    String imageDefault = goodslistBean.getImageDefault();
                                    shopCarAdapterModel.setImageDefault(imageDefault);
                                    //价格
                                    double price = goodslistBean.getPrice();
                                    shopCarAdapterModel.setPrice(price);
                                    //数量
                                    int num = goodslistBean.getNum();
                                    shopCarAdapterModel.setNum(num);
                                    //是否选中
                                    int isCheck = goodslistBean.getIsCheck();
                                    Log.e("TAG_进货单标签Activity","isCheck="+isCheck);
                                    shopCarAdapterModel.setIsCheck(isCheck);
                                    //相同店铺是否全部选中
                                    if (isCheck==1){
                                        typeIsCheck++;
                                        storeCheckPrice=storeCheckPrice+price;
                                    }
                                    //库存
                                    int enableStore = goodslistBean.getEnableStore();
                                    shopCarAdapterModel.setEnableStore(enableStore);
                                    //规格id
                                    int productId = goodslistBean.getProductId();
                                    shopCarAdapterModel.setProductId(productId);
                                    shopCarAdapterModel.setItmeType(2);
                                    shopCarAdapterList.add(shopCarAdapterModel);
                                }
                                if (typeIsCheck==goodslist.size()){
                                    shopCarTitleModel.setIsCheck(1);
                                }else {
                                    shopCarTitleModel.setIsCheck(0);
                                }
                                shopCarTitleModel.setStoreCheckPrice(storeCheckPrice);
                            }
                        }
                    }
                    adapter.setData(shopCarAdapterList);
                    llPayorder.setVisibility(View.VISIBLE);
                }else if (returnCode == 401) {
                    cleanToken();
                    OkHttpDemand();
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101:
                if (returnCode == 200) {
                    OkHttpDemand();
                    ToastUtil.showToast(returnMsg);
                }else if (returnCode == 401) {
                    cleanToken();
                    isSelected(isCheck==1?0:1,productId);
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
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
            }
        }
    };


    @Override
    public void OnClickMore(int listPosition) {
        ToastUtil.showToast("调转去凑单"+listPosition);
    }

    @Override
    public void OnClickClean(int listPosition) {
        ToastUtil.showToast("点击删除"+listPosition);
    }
    private int isCheck;
    private int productId;
    @Override
    public void OnClickSelected(int listPosition) {
        ToastUtil.showToast("点击选中"+listPosition);
        ShopCarAdapterModel shopCarAdapterModel = shopCarAdapterList.get(listPosition);
        int itmeType = shopCarAdapterModel.getItmeType();
        isCheck = shopCarAdapterModel.getIsCheck();
        if (itmeType == 1){

        }else {
            productId = shopCarAdapterModel.getProductId();
            isSelected(isCheck==1?0:1,productId);
        }
    }
    private void isSelected(int isCheck,int productId){
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            params.put("checked", String.valueOf(isCheck));
            params.put("productIds",  String.valueOf(productId));
            okHttpGet(101, Config.SHOPPCARCHECK, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            params.put("checked", String.valueOf(isCheck));
            params.put("productIds",  String.valueOf(productId));
            okHttpGet(101, Config.SHOPPCARCHECK, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
    }
    @Override
    public void OnClickAdd(int listPosition) {
        ToastUtil.showToast("点击添加"+listPosition);
    }

    @Override
    public void OnClickSubtract(int listPosition) {
        ToastUtil.showToast("点击删除"+listPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
