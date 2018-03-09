package com.yasn.purchase.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.FindPagerAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.model.FindModel;
import com.yasn.purchase.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Android on 2017/9/5.
 */
public class FindFragment extends SimpleTopbarFragment implements
        ViewPager.OnPageChangeListener ,TabLayout.OnTabSelectedListener{

    private ViewPager pager;
    private TabLayout tableLayout;
    private String findId = "-1";//发现页id
    private int pageCount = 1;
    FindPagerAdapter adapter;

    @Override
    protected Object getTopbarTitle() {
        return "发现";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void OkHttpDemand() {
        Log.e("TAG_initView","FIND_OkHttp");
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(100, Config.FIND+findId+"/"+pageCount, params);
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
            onVisible();
        } else {
            isVisible = false;
//            onInvisible();
        }
    }
    @Override
    protected void initView(LayoutInflater inflater, View view) {
        pager = (ViewPager)view. findViewById(R.id.pager);
        tableLayout = (TabLayout) view.findViewById(R.id.table);
        pager.setOnPageChangeListener(this);
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        tableLayout.setupWithViewPager(pager);
        /**
         * TabLayout.MODE_SCROLLABLE    支持滑动
         * TabLayout.MODE_FIXED     不支持，默认不支持水平滑动
         */
        tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        adapter =  new FindPagerAdapter(getChildFragmentManager(),getActivity());
        pager.setAdapter(adapter);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200){
                    FindModel findmodel = JSON.parseObject(returnData, FindModel.class);
                    if (findmodel!=null){
                        List<FindModel.TitleBean> title = findmodel.getTitle();
                        FindModel.TitleBean titelBean = new FindModel.TitleBean();
                        titelBean.setClassifyId(-1);
                        titelBean.setClassifyName("全部");
                        title.add(0,titelBean);
                        adapter.setData(title);
                    }
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
    public void onPageScrolled(int onPageScrolled, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        pager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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
        Log.e("TAG_fragment", "find=" + msg);
//        if ("loginSucceed".equals(msg)&&getUserVisibleHint()) {
//            OkHttpDemand();
//        }
    }
}
