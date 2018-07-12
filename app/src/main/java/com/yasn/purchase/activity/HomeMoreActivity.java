package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.HomeMoreAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.func.CallService;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.HomeMoreModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * 首页更多
 */
public class HomeMoreActivity extends SimpleTopbarActivity
        implements OnRcItemClickListener
        , SwipeRefreshLayout.OnRefreshListener
        , MultiSwipeRefreshLayout.OnLoadListener
        , MultiSwipeRefreshLayout.OnMultiSwipeRefreshClickListener {

    private HomeMoreAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager,nullLinearLayoutManager;
    private RecyclerView rcHomeMore;
//    private List<HomeMoreModel> myDataset;
    private static Class<?> rightFuncArray[] = {CallService.class};
    private LinearLayout shoplistnull;
    private boolean typeshow = false;;//临时记录显示空布局常购清单布局
    private MultiSwipeRefreshLayout swipe_layout;
    private String subjectId;
    private int pageNo = 1;
    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemore);
        Intent intent = getIntent();
        subjectId = intent.getStringExtra("subjectId");
        String title = intent.getStringExtra("title");
        resetTopbarTitle(title);
        token = SharePrefHelper.getInstance(this).getSpString("token");
        resetToken = SharePrefHelper.getInstance(this).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(this).getSpString("resetTokenTime");
        initGetRequest();
    }

    private void initGetRequest() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        params.put("subjectId", subjectId);
        params.put("pageNo", String.valueOf(pageNo));
        okHttpGet(100, Config.ONCLICKTABMORE, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //初始化tabRecyclerView
        initRecyclerView();
        initSwipeRefreshLayout();
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
        rcHomeMore = (RecyclerView) findViewById(R.id.rc_HomeMore);
        /**
         * 创建常购清单
         * 正式购物清单
         */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        rcHomeMore.setLayoutManager(mLinearLayoutManager);
        adapter = new HomeMoreAdapter(this,mLinearLayoutManager);
        adapter.setOnItemClickListener(this);
        rcHomeMore.setAdapter(adapter);
        rcHomeMore.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isTop = recyclerView.canScrollVertically(-1);//返回false表示不能往下滑动，即代表到顶部了；
                if (isTop){
                    swipe_layout.setEnabled(false);
                }else {
                    swipe_layout.setEnabled(true);
                }
                boolean isBottom = recyclerView.canScrollVertically(1);//返回false表示不能往上滑动，即代表到底部了；
                //屏幕中最后一个可见子项的position
                int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                //当前屏幕所看到的子项个数
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                //当前RecyclerView的所有子项个数
                int totalItemCount = mLinearLayoutManager.getItemCount();
                Log.e("TAG_底部","isBottom="+isBottom+"；visibleItemCount="+visibleItemCount+";totalItemCount="+totalItemCount);
                if (isBottom ){
                    swipe_layout.setBottom(false);
                }else {
                    if (visibleItemCount == totalItemCount){
                        swipe_layout.setBottom(false);
                    }else {
                        swipe_layout.setBottom(true);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.shoplistnull:

                break;
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        List<HomeMoreModel.SubjectBean.ContentBean> data = adapter.getData();
        if (data!=null&&data.size()>0){
            HomeMoreModel.SubjectBean.ContentBean dataBean = data.get(position);
            if (dataBean !=null){
                int market_enable = dataBean.getMarket_enable();
                if (market_enable == 0) {
                    ToastUtil.showToast("亲，该商品已经下架了哦~");
                    return;
                }
                int id = dataBean.getId();
                Intent intent = new Intent(this, GoodsDetailsActivity.class);
                SharePrefHelper.getInstance(this).putSpString("GOODSID", String.valueOf(id));
                startActivity(intent);
            }
        }
    }

    @Override
    public void OnItemLongClick(View view, int position) {

    }

    @Override
    public void OnClickTabMore(int listPosition) {
        ToastUtil.showToast("点击了更多");
    }

    @Override
    public void OnClickRecyButton(int itemPosition, int listPosition) {
//        HomeMoreModel info = myDataset.get(listPosition);
//        switch (itemPosition){
//            case 1:
//                ToastUtil.showToast(info.getButton1());
//                break;
//            case 2:
//                ToastUtil.showToast(info.getButton2());
//                break;
//            case 3:
//                ToastUtil.showToast(info.getButton3());
//                break;
//            case 4:
//                ToastUtil.showToast("点击了加入购物车"+info.getButton3());
//                break;
//        }
        List<HomeMoreModel.SubjectBean.ContentBean> data = adapter.getData();
        if (data!=null&&data.size()>0){
            HomeMoreModel.SubjectBean.ContentBean dataBean = data.get(listPosition);
            if (dataBean !=null){
                int market_enable = dataBean.getMarket_enable();
                if (market_enable == 0) {
                    ToastUtil.showToast("亲，该商品已经下架了哦~");
                    return;
                }
                int id = dataBean.getId();
                Intent intent = new Intent(this, GoodsDetailsActivity.class);
                SharePrefHelper.getInstance(this).putSpString("GOODSID", String.valueOf(id));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                HomeMoreModel homeMoreModel = JSON.parseObject(returnData, HomeMoreModel.class);
                List<HomeMoreModel.SubjectBean> subject = homeMoreModel.getSubject();
                if (subject !=null && subject.size()>0){
                    for (int i = 0,j = subject.size(); i < j; i++) {
                        HomeMoreModel.SubjectBean subjectBean = subject.get(i);
                        List<HomeMoreModel.SubjectBean.ContentBean> content = subjectBean.getContent();
//                        adapter.setData(content);
                        if (pageNo >1) {
                            adapter.addData(content);
                        } else {
                            if (content==null||content.size()==0){
                                ToastUtil.showToast("未搜索到该商品名称！");
                            }else {
                                adapter.setData(content);
                            }
                        }
                    }
                }
                swipe_layout.setLoading(false);
                swipe_layout.setRefreshing(false);
                break;
        }
    }

    @Override
    public void onCancelResult() {
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onFinishResult() {
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        swipe_layout.setRefreshing(true);
        initGetRequest();
    }

    @Override
    public void OnMultiSwipeRefreshClick() {
    }

    @Override
    public void onLoad() {
        Log.e("TAG_homemore","上拉加載");
        if (rcHomeMore !=null && rcHomeMore.getAdapter() != null) {
            swipe_layout.setLoading(true);
            pageNo++;
            initGetRequest();
        }
    }
}
