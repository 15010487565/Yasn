package com.yasn.purchase.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.adapter.ShopCarAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.EventBusMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.utils.SharePrefHelper;


/**
 * Created by Android on 2017/9/5.
 */
public class ShopCarFragment extends SimpleTopbarFragment implements OnRcItemClickListener{

    private LinearLayout announcement_linear;//公告
    private TextView announcement_text;//公告内容
    private RecyclerView order_recy;
    private ShopCarAdapter adapter;
//    public static int SHOPPCARWEBVIEWCODE = 40000;
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
        Log.e("TAG_initView","SHOPCAR_OkHttp");
        token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
        resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(getActivity()).getSpString("resetTokenTime");
        if ((token != null && !"".equals(token))||(resetToken != null && !"".equals(resetToken))){
            EventBus.getDefault().post(new EventBusMsg("ShopCar"));
        }else {
            startWebViewActivity(Config.LOGINWEBVIEW);
        }
    }

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        OkHttpDemand();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
//            onVisible();
        } else {
            isVisible = false;
//            onInvisible();
        }
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
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

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
            case 100:
                if (returnCode == 200){

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMsg event) {
        String msg = event.getMsg();
        Log.e("TAG_fragment","shopcar="+msg);

    }
}
