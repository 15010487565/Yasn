package com.yasn.purchase.video.fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.fragment.SimpleTopbarFragment;
import com.yasn.purchase.listener.SampleListener;
import com.yasn.purchase.model.FindAllModel;
import com.yasn.purchase.model.FindModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.video.adapter.FindAllAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.view.XListView;


/**
 * Created by Android on 2017/9/5.
 */
public class FindAllFragment extends SimpleTopbarFragment implements AbsListView.OnScrollListener,XListView.IXListViewListener {


    XListView listView;
    FrameLayout videoFullContainer;
    LinearLayout activityListVideo;

    ListVideoUtil listVideoUtil;
    FindAllAdapter adapter;
    private int lastVisibleItem;
    private int firstVisibleItem;
    private RelativeLayout title;
    private String findId = "-1";//发现页id
    private int pageCount = 1;
    private List<FindModel.TitleBean> titleList;
    private int selectPosition = 0;

    public FindAllFragment(List<FindModel.TitleBean> titleList, int selectPosition) {
        this.titleList = titleList;
        this.selectPosition = selectPosition;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_findall;
    }

    @Override
    protected void OkHttpDemand() {
        Map<String, Object> params = new HashMap<String, Object>();
        FindModel.TitleBean titleBean = titleList.get(selectPosition);
        findId = String.valueOf(titleBean.getClassifyId());
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
            onInvisible();
        }
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        title = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        title.setVisibility(View.GONE);

        listView = (XListView) view.findViewById(R.id.listView);
        listView.setPullLoadEnable(false);
        listView.setXListViewListener(this);
        listView.setPullRefreshEnable(true);

//        videoFullContainer = (FrameLayout) view.findViewById(R.id.video_full_container);
        activityListVideo = (LinearLayout) view.findViewById(R.id.activity_list_video);

        listVideoUtil = new ListVideoUtil(getActivity());
        listVideoUtil.setFullViewContainer(videoFullContainer);
        listVideoUtil.setHideStatusBar(true);
        //listVideoUtil.setHideActionBar(true);
        listVideoUtil.setNeedLockFull(true);

        listView.setOnScrollListener(this);
//        initFootView();
        //小窗口关闭被点击的时候回调处理回复页面
        listVideoUtil.setVideoAllCallBack(new SampleListener(){
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                Debuger.printfLog("Duration " + listVideoUtil.getDuration() + " CurrentPosition " + listVideoUtil.getCurrentPositionWhenPlaying());
            }

            @Override
            public void onQuitSmallWidget(String url, Object... objects) {
                super.onQuitSmallWidget(url, objects);
                //大于0说明有播放,//对应的播放列表TAG
                //当前播放的位置
                int position = listVideoUtil.getPlayPosition();
                if (position >= 0 && listVideoUtil.getPlayTAG().equals(FindAllAdapter.TAG)) {
                    //不可视的时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //释放掉视频
                        listVideoUtil.releaseVideoPlayer();
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
        adapter = new FindAllAdapter(getActivity(), listVideoUtil);
        adapter.setRootView(activityListVideo);
        listView.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(listView);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
    }
    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        listVideoUtil.releaseVideoPlayer();
        GSYVideoPlayer.releaseAllVideos();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
    List<FindAllModel.DataBean> list;

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
            case 100:
                if (returnCode == 200){
                    FindAllModel findallmodel = JSON.parseObject(returnData, FindAllModel.class);
                    if (findallmodel!=null){
                        list = findallmodel.getData();
                        if (list == null||list.size()==0){
                            ToastUtil.showToast("暂无更多数据！");
                            listView.setPullLoadEnable(false);
                        }else {
                            if (onRefresh){
                                adapter.setData(list);
                                onRefresh = false;
                            }else if (onLoadMore){
                                adapter.addData(list);
                                onLoadMore = false;
                            }else{
                                adapter.setData(list);
                            }
                        }
                        listView.stopLoadMore();
                        listView.stopRefresh();
                    }
                }else {
                    ToastUtil.showToast(returnMsg);
                }

                break;
        }

    }

    @Override
    public void onCancelResult() {
        listView.stopLoadMore();
        listView.stopRefresh();
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        listView.stopLoadMore();
        listView.stopRefresh();;
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        listView.stopLoadMore();
        listView.stopRefresh();;
    }

    @Override
    public void onFinishResult() {
       listView.stopLoadMore();
        listView.stopRefresh();
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

            }
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //有更多
        this.firstVisibleItem = firstVisibleItem;
//        lastVisibleItem = firstVisibleItem + visibleItemCount;
        lastVisibleItem = listView.getLastVisiblePosition();
        if (list!=null){
            if(firstVisibleItem>0&&totalItemCount > visibleItemCount){
                listView.setPullLoadEnable(true);
            }else {
                listView.setPullLoadEnable(false);
            }
        }else {
            listView.setPullLoadEnable(false);
        }
        //大于0说明有播放,//对应的播放列表TAG
        //当前播放的位置
        int position = listVideoUtil.getPlayPosition();
        if (position >= 0 && listVideoUtil.getPlayTAG().equals(FindAllAdapter.TAG)) {
            //不可视的是时候
            if ((position < firstVisibleItem || position > lastVisibleItem)) {
                Log.e("TAG_视频播放==","刷新");
//                //如果是小窗口就不需要处理
//                if (!listVideoUtil.isSmall()) {
//                    //小窗口
//                    int size = CommonUtil.dip2px(getActivity(), 150);
//                    listVideoUtil.showSmallVideo(new Point(size, size), false, true);
//                }
                GSYVideoManager.onPause();
            } else {
                if (listVideoUtil.isSmall()) {
                    listVideoUtil.smallVideoToNormal();
                }
            }
        }
    }
    boolean onRefresh = false;
    @Override
    public void onRefresh() {
        onRefresh = true;
        pageCount = 1;
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(100, Config.FIND+findId+"/"+pageCount, params);
    }
    boolean onLoadMore = false;
    @Override
    public void onLoadMore() {
        onLoadMore = true;
        pageCount++;
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(100, Config.FIND+findId+"/"+pageCount, params);
    }
}
