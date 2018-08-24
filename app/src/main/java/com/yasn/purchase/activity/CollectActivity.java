package com.yasn.purchase.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.CollectAdapter;
import com.yasn.purchase.adapter.HomeRecyclerAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.func.CollectRemoveAllFunc;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.CollectModel;
import com.yasn.purchase.model.CollectNullModel;
import com.yasn.purchase.model.HomeRecyModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * 收藏
 */
public class CollectActivity extends SimpleTopbarActivity
        implements OnRcItemClickListener
        , CollectAdapter.OnCollectListener
        , SwipeRefreshLayout.OnRefreshListener
        , MultiSwipeRefreshLayout.OnLoadListener
        , MultiSwipeRefreshLayout.OnMultiSwipeRefreshClickListener {

    private HomeRecyclerAdapter adapternull;
    private CollectAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView rcNullCollect, rcCollect;
    private static Class<?> rightFuncArray[] = {CollectRemoveAllFunc.class};
    private LinearLayout llCollectnull;
    private MultiSwipeRefreshLayout swipe_layout;
    private int pageNo = 1;
    //收藏商品集合
    List<CollectModel.ListFavoriteBean> listFavorite;
    //推荐商品集合
    List<HomeRecyModel> subjectList;
    private TextView tvCollectRecommend;

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {

        return rightFuncArray;
    }

    @Override
    protected Object getTopbarTitle() {
        return R.string.me_collect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        refreshRightFunctionZone(false);
    }

    //获得收藏
    private void initGetCollectRequest() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        params.put("pageNo", String.valueOf(pageNo));
        okHttpGet(100, Config.COLLECT, params);
    }

    //获得楼层
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

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG_收藏", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG_收藏", "onResume");
        initGetCollectRequest();
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

    private void initRecyclerView() {
        rcCollect = (RecyclerView) findViewById(R.id.rc_Collect);
        //空布局
        llCollectnull = (LinearLayout) findViewById(R.id.ll_Collectnull);
        llCollectnull.setOnClickListener(this);
        tvCollectRecommend = (TextView) findViewById(R.id.tv_CollectRecommend);
        rcNullCollect = (RecyclerView) findViewById(R.id.rc_NullCollect);

        /**
         * 创建空Adapter
         * 高毛利商品
         */
        LinearLayoutManager nullLinearLayoutManager = new LinearLayoutManager(this);
        nullLinearLayoutManager.setAutoMeasureEnabled(true);
        rcNullCollect.setLayoutManager(nullLinearLayoutManager);
        adapternull = new HomeRecyclerAdapter(this);
        adapternull.setOnItemClickListener(this);
        rcNullCollect.setAdapter(adapternull);
        /**
         * 创建常购清单
         * 正式购物清单
         */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        rcCollect.setLayoutManager(mLinearLayoutManager);
        adapter = new CollectAdapter(this, mLinearLayoutManager);
        adapter.setOnItemClickListener(this, this);
        rcCollect.setAdapter(adapter);
        rcCollect.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                Log.e("TAG_底部","isBottom="+isBottom+"visibleItemCount="+visibleItemCount+";totalItemCount="+totalItemCount);
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
    //无收藏单个item点击事件
    @Override
    public void OnItemClick(View view, int position) {
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
        Intent intent = new Intent(CollectActivity.this, GoodsDetailsActivity.class);
        SharePrefHelper.getInstance(CollectActivity.this).putSpString("GOODSID", goodsid);
        startActivity(intent);

    }

    @Override
    public void OnItemLongClick(View view, int position) {

    }

    //删除
    @Override
    public void OnItemDeleteClick(View view, int position) {
        List<CollectModel.ListFavoriteBean> data = adapter.getData();
        CollectModel.ListFavoriteBean listFavoriteBean = data.get(position);
        int goods_id = listFavoriteBean.getGoods_id();
        removeAllDialog(String.valueOf(goods_id));
    }

    //收藏单个item点击事件
    @Override
    public void OnItemClickListener(View view, int position) {
        List<CollectModel.ListFavoriteBean> data = adapter.getData();
        CollectModel.ListFavoriteBean listFavoriteBean = data.get(position);
        int goods_id = listFavoriteBean.getGoods_id();
        Intent intent = new Intent(CollectActivity.this, GoodsDetailsActivity.class);
        SharePrefHelper.getInstance(CollectActivity.this).putSpString("GOODSID", String.valueOf(goods_id));
        startActivity(intent);
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
        Intent intent = new Intent(CollectActivity.this, HomeMoreActivity.class);
        intent.putExtra("subjectId", String.valueOf(subject_id));
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Override
    public void OnClickRecyButton(int itemPosition, int listPosition) {

    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                CollectModel collectModel = JSON.parseObject(returnData, CollectModel.class);
                listFavorite = collectModel.getListFavorite();
                if ((listFavorite == null || listFavorite.size() == 0) && pageNo == 1) {
                    initGetMoreRequest();
                    rcCollect.setVisibility(View.GONE);
                    llCollectnull.setVisibility(View.VISIBLE);
                    refreshRightFunctionZone(false);
                } else {
                    rcCollect.setVisibility(View.VISIBLE);
                    llCollectnull.setVisibility(View.GONE);
                    refreshRightFunctionZone(true);
                    if (pageNo > 1) {
                        if (listFavorite == null || listFavorite.size() == 0) {
                            adapter.upFootText();
                            ToastUtil.showToast("收藏商品已全部显示！");
                        } else {
                            adapter.addData(listFavorite);
                        }
                    } else {
                        if (listFavorite == null || listFavorite.size() == 0) {
//                            adapter.upFootText();
                            ToastUtil.showToast("未搜索到收藏商品！");
                        } else {
                            adapter.setData(listFavorite);
                        }
                    }
                    swipe_layout.setLoading(false);
                    swipe_layout.setRefreshing(false);
                }

                break;
            case 101:
                CollectNullModel collectNullModel = JSON.parseObject(returnData, CollectNullModel.class);
                List<CollectNullModel.SubjectBean> subject = collectNullModel.getSubject();

                initNullCollect(subject);
                refreshRightFunctionZone(false);
                swipe_layout.setLoading(false);
                swipe_layout.setRefreshing(false);
                break;
            case 102://删除单个
                if (returnCode == 200) {
                    initGetCollectRequest();
                }
                ToastUtil.showToast(returnMsg);
                break;
            case 103://删除全部
                if (returnCode == 200) {
                    initGetMoreRequest();
                    rcCollect.setVisibility(View.GONE);
                    llCollectnull.setVisibility(View.VISIBLE);
                } else {
                    rcCollect.setVisibility(View.VISIBLE);
                    llCollectnull.setVisibility(View.GONE);
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
            if (i == 0) {
                String collectRecommend = String.format("我们为您推荐了%s。赶快去采购吧", title);
                tvCollectRecommend.setText(collectRecommend);
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
        swipe_layout.setRefreshing(false);
        swipe_layout.setLoading(false);
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        swipe_layout.setRefreshing(false);
        swipe_layout.setLoading(false);
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        swipe_layout.setRefreshing(false);
        swipe_layout.setLoading(false);
    }

    @Override
    public void onFinishResult() {
        swipe_layout.setRefreshing(false);
        swipe_layout.setLoading(false);
    }

    /**
     * 删除全部dialog
     * goods_id 删除的id 空时为全部删除
     */
    protected AlertDialog remomeAllDialog;

    public void removeAllDialog(final String goods_id) {
        LayoutInflater factor = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_collectremomeall, null);
        TextView tvCollectDelHintDialog = (TextView) serviceView.findViewById(R.id.tv_CollectDelHintDialog);
        if (TextUtils.isEmpty(goods_id)) {
            tvCollectDelHintDialog.setText("您确定删除所有收藏商品吗?");
        } else {
            tvCollectDelHintDialog.setText("您确定删除该收藏商品吗?");
        }
        TextView remove = (TextView) serviceView.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(goods_id)) {//删除全部
                    Map<String, Object> params = new HashMap<String, Object>();
                    if (token != null && !"".equals(token)) {
                        params.put("access_token", token);
                    } else if (resetToken != null && !"".equals(resetToken)) {
                        params.put("access_token", resetToken);
                    }
                    okHttpGet(103, Config.COLLECTDELETEALL, params);
                } else {//删除单个
                    Map<String, Object> params = new HashMap<String, Object>();
                    if (token != null && !"".equals(token)) {
                        params.put("access_token", token);
                    } else if (resetToken != null && !"".equals(resetToken)) {
                        params.put("access_token", resetToken);
                    }
                    okHttpGet(102, Config.DELETECOLLECT + goods_id, params);
                }
                remomeAllDialog.dismiss();
            }
        });
        TextView cancelBtn = (TextView) serviceView.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remomeAllDialog.dismiss();
            }
        });
        Activity activity = CollectActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        remomeAllDialog = builder.create();
        remomeAllDialog.show();
        remomeAllDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        swipe_layout.setRefreshing(true);
        initGetCollectRequest();
    }

    @Override
    public void OnMultiSwipeRefreshClick() {

    }

    @Override
    public void onLoad() {
        Log.e("TAG_Collect", "收藏");
        if (rcCollect != null && rcCollect.getAdapter() != null) {
            swipe_layout.setLoading(true);
            pageNo++;
            initGetCollectRequest();
        }
    }
}
