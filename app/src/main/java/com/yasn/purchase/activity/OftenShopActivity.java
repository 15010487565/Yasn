package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;
import com.yasn.purchase.adapter.HomeRecyclerAdapter;
import com.yasn.purchase.adapter.OftenShopAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.func.OftenShopFunc;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.CollectNullModel;
import com.yasn.purchase.model.HomeMoreModel;
import com.yasn.purchase.model.HomeRecyModel;
import com.yasn.purchase.model.OftenModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * 常购清单
 */
public class OftenShopActivity extends BaseYasnActivity
        implements OnRcItemClickListener
        , OftenShopAdapter.OnOftenAddShopCarListener
        , SwipeRefreshLayout.OnRefreshListener
        , MultiSwipeRefreshLayout.OnLoadListener
        , MultiSwipeRefreshLayout.OnMultiSwipeRefreshClickListener {

    private HomeRecyclerAdapter adapternull;
    private OftenShopAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView rcOftenShop, rcOftenShopNull;
    private List<HomeMoreModel> myDataset;
    private static Class<?> rightFuncArray[] = {OftenShopFunc.class};
    private LinearLayout llShoplistnull;
    private int pagNo = 1;
    private MultiSwipeRefreshLayout swipe_layout;
    //推荐商品集合
    List<HomeRecyModel> subjectList;
    //常够清单集合
    List<OftenModel.DataBean.RegularPurcaseBean> regularPurcase;
    private TextView tvOftenShopRecommend;
    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected Object getTopbarTitle() {
        return R.string.shoplist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oftenshop);
        int carNum = SharePrefHelper.getInstance(OftenShopActivity.this).getSpInt("carNum");
        setRedPoint(carNum);
        initOftenRequest();
    }

    //获得常购清单
    private void initOftenRequest() {
        if (TextUtils.isEmpty(token)&&TextUtils.isEmpty(resetToken)){
            initGetMoreRequest();
        }else {
            Map<String, Object> params = new HashMap<String, Object>();
            if (token != null && !"".equals(token)) {
                params.put("access_token", token);
            } else if (resetToken != null && !"".equals(resetToken)) {
                params.put("access_token", resetToken);
            }
            params.put("pageNo", String.valueOf(pagNo));
            params.put("pageSize", "10");
            okHttpGet(100, Config.SHOPLISTGET , params);
//        okHttpGet(100, Config.SHOPLIST + "/" + pagNo, params);
        }
    }

    //获得推荐商品，更多接口默认获取第一楼层
    private void initGetMoreRequest() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(101, Config.ONCLICKTABMORE, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //初始化tabRecyclerView
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initRecyclerView() {
        rcOftenShopNull = (RecyclerView) findViewById(R.id.rc_OftenShopNull);
        //空布局
        llShoplistnull = (LinearLayout) findViewById(R.id.ll_OftenShopNull);
        llShoplistnull.setVisibility(View.GONE);
        tvOftenShopRecommend = (TextView) findViewById(R.id.tv_OftenShopRecommend);
        rcOftenShop = (RecyclerView) findViewById(R.id.rc_OftenShop);
        rcOftenShop.setVisibility(View.VISIBLE);
        /**
         * 创建空Adapter
         * 高毛利商品
         */
        LinearLayoutManager nullLinearLayoutManager = new LinearLayoutManager(this);
        nullLinearLayoutManager.setAutoMeasureEnabled(true);
        rcOftenShopNull.setLayoutManager(nullLinearLayoutManager);
        adapternull = new HomeRecyclerAdapter(this);
        adapternull.setOnItemClickListener(this);
        rcOftenShopNull.setAdapter(adapternull);
        /**
         * 创建常购清单
         * 正式购物清单
         */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        rcOftenShop.setLayoutManager(mLinearLayoutManager);
        adapter = new OftenShopAdapter(this, mLinearLayoutManager);
        adapter.setOnItemClickListener(this, this);
        rcOftenShop.setAdapter(adapter);
        rcOftenShop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isTop = recyclerView.canScrollVertically(-1);//返回false表示不能往下滑动，即代表到顶部了；
                if (isTop) {
                    swipe_layout.setEnabled(false);
                } else {
                    swipe_layout.setEnabled(true);
                }
                boolean isBottom = recyclerView.canScrollVertically(1);//返回false表示不能往上滑动，即代表到底部了；
                //屏幕中最后一个可见子项的position
//                int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                //当前屏幕所看到的子项个数
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                //当前RecyclerView的所有子项个数
                int totalItemCount = mLinearLayoutManager.getItemCount();
                if (isBottom) {
                    swipe_layout.setBottom(false);
                } else {
                    if (visibleItemCount == totalItemCount) {
                        swipe_layout.setBottom(false);
                        adapter.upFootText();
                    } else {
                        swipe_layout.setBottom(true);
                    }
                }
            }
        });
    }

    private void initSwipeRefreshLayout() {
        //搜索列表
        swipe_layout = (MultiSwipeRefreshLayout) findViewById(R.id.swipe_layout);
        //下拉刷新监听
        swipe_layout.setOnRefreshListener(this);
        swipe_layout.setMultiSwipeRefreshClickListener(this);
        //上拉加載监听
        swipe_layout.setOnLoadListener(this);
        //设置样式刷新显示的位置
        swipe_layout.setProgressViewOffset(true, -20, 100);
        swipe_layout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
    }

    @Override
    public void OnItemClick(View view, int position) {
        if (subjectList != null) {
            HomeRecyModel homeRecyModel = subjectList.get(position);
            int itemType = homeRecyModel.getItemType();
            if (itemType == 1) {
                return;
            }
            int market_enable = homeRecyModel.getMarket_enable();
            if (market_enable == 0) {
                ToastUtil.showToast("亲，该商品已经下架了哦~");
                return;
            }
            //itemPosition 教你卖好、成功案例、语音讲解对应的Position
            String goodsid = homeRecyModel.getGoodsid();
            Intent intent = new Intent(OftenShopActivity.this, GoodsDetailsActivity.class);
            SharePrefHelper.getInstance(OftenShopActivity.this).putSpString("GOODSID", goodsid);
            startActivity(intent);
            Log.e("TAG_收藏空", "goodsid=" + goodsid);
        } else if (regularPurcase != null) {
            List<OftenModel.DataBean.RegularPurcaseBean> data = adapter.getData();
            OftenModel.DataBean.RegularPurcaseBean regularPurcaseBean = data.get(position);
            int goods_id = regularPurcaseBean.getGoods_id();
            Intent intent = new Intent(OftenShopActivity.this, GoodsDetailsActivity.class);
            SharePrefHelper.getInstance(OftenShopActivity.this).putSpString("GOODSID", String.valueOf(goods_id));
            startActivity(intent);
            Log.e("TAG_收藏", "goodsid=" + goods_id);
        }
    }

    //
    @Override
    public void OnItemLongClick(View view, int position) {

    }

    @Override
    public void OnClickTabMore(int listPosition) {
        if (subjectList == null) {
            return;
        }
        HomeRecyModel homeRecyModel = subjectList.get(listPosition);
        int subject_id = homeRecyModel.getSubject_id();
        String title = homeRecyModel.getText();
//        startWebViewActivity(Config.ONCLICKTABMORE + "?id=" + subject_id + "&title=" + text);
        Intent intent = new Intent(OftenShopActivity.this, HomeMoreActivity.class);
        intent.putExtra("subjectId", String.valueOf(subject_id));
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Override
    public void OnClickRecyButton(int itemPosition, int listPosition) {

    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {
            case 100:
                if (returnData == null || "".equals(returnData)){
                    if (pagNo == 1) {
                        initGetMoreRequest();
                        rcOftenShop.setVisibility(View.GONE);
                        llShoplistnull.setVisibility(View.VISIBLE);
//                        refreshRightFunctionZone(false);
                    }
                }else {
                    OftenModel oftenModel = JSON.parseObject(returnData, OftenModel.class);
                    OftenModel.DataBean data = oftenModel.getData();
                    if (data != null) {
                        regularPurcase = data.getRegularPurcase();
                        if ((regularPurcase == null || regularPurcase.size() == 0) && pagNo == 1) {
                            initGetMoreRequest();
                            rcOftenShop.setVisibility(View.GONE);
                            llShoplistnull.setVisibility(View.VISIBLE);
//                            refreshRightFunctionZone(false);
                        } else {
                            rcOftenShop.setVisibility(View.VISIBLE);
                            llShoplistnull.setVisibility(View.GONE);
//                            refreshRightFunctionZone(true);
                            if (pagNo > 1) {
                                if (regularPurcase == null || regularPurcase.size() == 0) {
                                    adapter.upFootText();
                                    ToastUtil.showToast("常够商品已全部显示！");
                                    swipe_layout.setBottom(false);
                                }else {
                                    adapter.addData(regularPurcase);
                                }
                            } else {
                                if (regularPurcase == null || regularPurcase.size() == 0) {
//                                adapter.upFootText();
                                    ToastUtil.showToast("未搜索到常够商品！");
                                } else {
                                    adapter.setData(regularPurcase);
                                }
                            }
                            swipe_layout.setLoading(false);
                            swipe_layout.setRefreshing(false);
                        }

                    }else {
                        if (pagNo == 1) {
                            initGetMoreRequest();
                            rcOftenShop.setVisibility(View.GONE);
                            llShoplistnull.setVisibility(View.VISIBLE);
//                            refreshRightFunctionZone(false);
                        }else {
                            adapter.upFootText();
                            ToastUtil.showToast("常够商品已全部显示！");
                            swipe_layout.setBottom(false);
                        }
                    }
                }
                break;

            case 101:
                CollectNullModel collectNullModel = JSON.parseObject(returnData, CollectNullModel.class);
                List<CollectNullModel.SubjectBean> subject = collectNullModel.getSubject();
                initNullCollect(subject);
                rcOftenShop.setVisibility(View.GONE);
                llShoplistnull.setVisibility(View.VISIBLE);
//                refreshRightFunctionZone(false);
                swipe_layout.setLoading(false);
                swipe_layout.setRefreshing(false);
                break;
            case 102:
                if (returnCode == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(returnData);
                        int number = jsonObject.optInt("number");
                        Log.e("TAG_购物车","number="+number);
                        SharePrefHelper.getInstance(OftenShopActivity.this).putSpInt("carNum", number);
                        setRedPoint(number);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ToastUtil.showToast(returnMsg);
                break;
        }
    }

    private void initNullCollect(List<CollectNullModel.SubjectBean> subject) {
        //储存转换后的数据格式,无收藏显示推荐
        subjectList = new ArrayList<>();
        for (int i = 0, j = subject.size(); i < j; i++) {

            CollectNullModel.SubjectBean subjectBean = subject.get(i);
            String title = subjectBean.getTitle();
            HomeRecyModel subjectModel = new HomeRecyModel();
            subjectModel.setItemType(1);
            subjectModel.setText(title);
            if (i==0){
                String collectRecommend = String.format("我们为您推荐了%s。赶快去采购吧", title);
                tvOftenShopRecommend.setText(collectRecommend);
            }
            int subject_id = subjectBean.getSubject_id();
            subjectModel.setSubject_id(subject_id);
            subjectList.add(subjectModel);
            List<CollectNullModel.SubjectBean.ContentBean> contentList = subjectBean.getContent();
            for (int k = 0, l = contentList.size(); k < l; k++) {
                CollectNullModel.SubjectBean.ContentBean contentBean = contentList.get(k);

                int goods_id = contentBean.getId();
                String small = contentBean.getSmall();//图片
                String name = contentBean.getGoods_name();//名字
                String advert = contentBean.getAdvert();//促销折扣提示文字
                int has_discount = contentBean.getHas_discount();//是否有折扣价
                String discount_price = contentBean.getDiscount_price();//折扣价
                double price = contentBean.getPrice();//正常价格
                int totalBuyCount = contentBean.getTotal_buy_count();//已售数量

                HomeRecyModel homeRecy = new HomeRecyModel();
                homeRecy.setGoodsid(String.valueOf(goods_id));//商品ID
                homeRecy.setItemType(2);
                homeRecy.setText(name);
                homeRecy.setImage(small);
                homeRecy.setSubject_id(subject_id);
//                if (priceDisplayType == 0) {//取正常价格
                String result;
                if (has_discount == 0) {//正常价格
                    result = String.format("%.2f", price);
                } else {//折扣价
                    result = String.format("%.2f", Double.valueOf(discount_price));
                }
                homeRecy.setMoney(result);
//                } else {//取文字信息
//                    homeRecy.setMoney(priceDisplayMsg == null ? "" : priceDisplayMsg);
//                }
                homeRecy.setAdvert(advert);
                homeRecy.setTotalBuyCount(String.valueOf(totalBuyCount));

                int store_id = contentBean.getStore_id();
                if (store_id == 1) {//自营
                    homeRecy.setAutotrophy(true);
                } else {
                    homeRecy.setAutotrophy(false);
//                    int region_id = subjectsBean.getRegion_id();
//                    if (store_id != 99 && region_id > 0) {//地方站、非自营、非脱商品====直供
//                        homeRecy.setRegionName(true);
//                    } else {
                    homeRecy.setRegionName(false);
//                    }
                }
                int is_limit_buy = contentBean.getIs_limit_buy();
                if (is_limit_buy == 1) {//限购
                    homeRecy.setPurchase(true);
                } else {
                    homeRecy.setPurchase(false);
                }
                int is_before_sale = contentBean.getIs_before_sale();
                if (is_before_sale == 1) {//预售
                    homeRecy.setPresell(true);
                } else {
                    homeRecy.setPresell(false);
                }
                int market_enable = contentBean.getMarket_enable();
                homeRecy.setMarket_enable(market_enable);
                int have_voice = contentBean.getHave_voice();//是否有音频 1：有
                homeRecy.setButton3(have_voice);
                int is_success_case = contentBean.getIs_success_case(); //是否成功案例 1：有
                homeRecy.setButton2(is_success_case);
                subjectList.add(homeRecy);
            }
        }
        String loginState = SharePrefHelper.getInstance(this).getSpString("loginState");
        adapternull.setData(subjectList, loginState);
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
        pagNo = 1;
        swipe_layout.setRefreshing(true);
        adapter.cleanData();
        initOftenRequest();
    }

    @Override
    public void OnMultiSwipeRefreshClick() {

    }

    @Override
    public void onLoad() {
        if (rcOftenShop != null && rcOftenShop.getAdapter() != null) {
            swipe_layout.setLoading(true);
            pagNo++;
            initOftenRequest();
        }
    }

    //添加购物车
    @Override
    public void OnAddShopCarClick(View view, int position) {
        List<OftenModel.DataBean.RegularPurcaseBean> data = adapter.getData();
        OftenModel.DataBean.RegularPurcaseBean regularPurcaseBean = data.get(position);
        int product_id = regularPurcaseBean.getProduct_id();
        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("productId", String.valueOf(product_id));
        params1.put("num", "1");
        if (token != null && !"".equals(token)) {
            params1.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params1.put("access_token", resetToken);
        }
        okHttpGet(102, Config.ADDSHOPCAR, params1);
    }
}
