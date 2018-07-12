package com.yasn.purchase.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yasn.purchase.R;
import com.yasn.purchase.adapter.HomeMoreAdapter;
import com.yasn.purchase.adapter.OftenShopAdapter;
import com.yasn.purchase.func.CallService;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.HomeMoreModel;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

/**
 * 常购清单
 */
public class OftenShopActivity extends SimpleTopbarActivity implements OnRcItemClickListener{

    private HomeMoreAdapter adapternull;
    private OftenShopAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager,nullLinearLayoutManager;
    private RecyclerView recyclerview,shoplist_recy;
    private List<HomeMoreModel> myDataset;
    private static Class<?> rightFuncArray[] = {CallService.class};
    private LinearLayout shoplistnull;
    private boolean typeshow = false;;//临时记录显示空布局常购清单布局
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
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //初始化tabRecyclerView
        initRecyclerView();
    }

    private void initRecyclerView() {
        shoplist_recy = (RecyclerView) findViewById(R.id.shoplist_recy);
        //空布局
        shoplistnull = (LinearLayout) findViewById(R.id.shoplistnull);
        shoplistnull.setOnClickListener(this);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        /**
         * 创建空Adapter
         * 高毛利商品
         */
        nullLinearLayoutManager = new LinearLayoutManager(this);
        nullLinearLayoutManager.setAutoMeasureEnabled(true);
        recyclerview.setLayoutManager(nullLinearLayoutManager);
        adapternull = new HomeMoreAdapter(this,nullLinearLayoutManager);
        adapternull.setOnItemClickListener(this);
        recyclerview.setAdapter(adapternull);
        /**
         * 创建常购清单
         * 正式购物清单
         */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        shoplist_recy.setLayoutManager(mLinearLayoutManager);
        adapter = new OftenShopAdapter(this,mLinearLayoutManager);
        adapter.setOnItemClickListener(this);
        shoplist_recy.setAdapter(adapter);
    }
    public void callService(){
        if (typeshow){
            shoplist_recy.setVisibility(View.VISIBLE);
            shoplistnull.setVisibility(View.GONE);
            typeshow = false;
        }else {
            shoplist_recy.setVisibility(View.GONE);
            shoplistnull.setVisibility(View.VISIBLE);
            typeshow = true;
        }
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
        ToastUtil.showToast("listview item点击了"+position);
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

    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

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
}
