package com.yasn.purchase.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.adapter.ShopCarAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.xcd.com.mylibrary.utils.SharePrefHelper;


/**
 * Created by Android on 2017/9/5.
 */
public class ShopCarFragment extends SimpleTopbarFragment implements OnRcItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout announcement_linear;//公告
    private TextView announcement_text;//公告内容
    private RecyclerView order_recy;
    private ShopCarAdapter adapter;
    //    public static int SHOPPCARWEBVIEWCODE = 40000;
    private MultiSwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected Object getTopbarTitle() {
        return R.string.order;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopcar;
    }

    @Override
    protected void OkHttpDemand() {
        Log.e("TAG_initView", "SHOPCAR_OkHttp");
        token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
        resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(getActivity()).getSpString("resetTokenTime");
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            okHttpGet(100, Config.SHOPPCAR, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            okHttpGet(100, Config.SHOPPCAR, params);
        } else {
            startWebViewActivity(Config.LOGINWEBVIEW);
        }
    }

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        OkHttpDemand();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
//            onVisible();
        } else {
            isVisible = false;
//            onInvisible();
        }
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        initSwipeRefreshLayout(view);
        announcement_linear = (LinearLayout) view.findViewById(R.id.announcement_linear);
        announcement_text = (TextView) view.findViewById(R.id.announcement_text);
        //进货单列表
        order_recy = (RecyclerView) view.findViewById(R.id.order_recy);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(true);
        order_recy.setLayoutManager(linearLayoutManager);
        adapter = new ShopCarAdapter(getActivity());
        adapter.setOnItemClickListener(this);
        order_recy.setAdapter(adapter);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
    }

    private void initSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        mSwipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {

                }else if (returnCode == 401) {
                    cleanToken();
                    OkHttpDemand();
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
    public void OnItemClick(View view, int position) {

    }

    @Override
    public void OnItemLongClick(View view, int position) {

    }

    @Override
    public void OnClickTabMore(int listPosition) {

    }

    @Override
    public void OnClickRecyButton(int itemPosition, int listPosition) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

    }

    @Override
    public void onRefresh() {
        OkHttpDemand();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = getActivity();
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
}
