package com.yasn.purchase.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.GoodsDetailsActivity;
import com.yasn.purchase.activity.HotLableActivity;
import com.yasn.purchase.activity.MainActivityNew;
import com.yasn.purchase.adapter.HomeRecyclerAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.help.SobotUtil;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.model.HomeModel;
import com.yasn.purchase.model.HomeRecyModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;
import com.yasn.purchase.view.RecyclerLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.xcd.com.mylibrary.utils.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;


/**
 * Created by Android on 2017/9/5.
 */
public class HomeFragment extends SimpleTopbarFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        OnItemClickListener, TabLayout.OnTabSelectedListener,
        MultiSwipeRefreshLayout.OnMultiSwipeRefreshClickListener,
        OnRcItemClickListener {

    private HomeRecyclerAdapter adapter;
    private ConvenientBanner homeConvenientBanner;
    private TabLayout tablayout;
    private LinearLayout notLogin, linearLayout2, orderStateLinear;
    private RelativeLayout home_infomessage;
    private MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private RelativeLayout topbat_parent;
//    private NestedScrollView nestedScrollView;
//    private RecyclerView recyNested;
    private RecyclerView recyclerview;
    private RecyclerLayoutManager mLinearLayoutManager, layoutManagerNested;
    private TextView topsearch, address;
    private boolean move = false;
    private boolean animationBool = true;//定位是否开启动画，默认true
    private int mIndex = 0;
    private FrameLayout topinfomssage;
    private LinearLayout shoplist, yasnbang, home_collect, home_service;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        isFrist = true;
        OkHttpDemand();
    }
    @Override
    protected void OkHttpDemand() {
        Log.e("TAG_initView","HOMEFR_AGMENT");
        token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
        resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(getActivity()).getSpString("resetTokenTime");
        Log.e("TAG_TOKEN请求","token="+token);
        Log.e("TAG_TOKEN请求","resetToken="+resetToken);
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null&&!"".equals(token)){
            params.put("access_token", token);
        }else if (resetToken != null&&!"".equals(resetToken)){
            params.put("access_token", resetToken);
        } else {
            notLogin.setVisibility(View.VISIBLE);
            home_infomessage.setVisibility(View.GONE);
            SharePrefHelper.getInstance(getActivity()).putSpString("regionId","");
        }
        okHttpGet(100, Config.HOME, params);
        SharePrefHelper.getInstance(getActivity()).putSpBoolean("isLoginHome", true);
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
        topbat_parent = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);

        //地方站
        address = (TextView) view.findViewById(R.id.address);
        //搜索输入框
        topsearch = (TextView) view.findViewById(R.id.topsearch);
        topsearch.setOnClickListener(this);
        initLoginView(view);
        //收藏
        home_collect = (LinearLayout) view.findViewById(R.id.home_collect);
        home_collect.setOnClickListener(this);
        home_collect.setVisibility(View.GONE);
        //客服
        home_service = (LinearLayout) view.findViewById(R.id.home_service);
        home_service.setOnClickListener(this);
        //常购清单
        shoplist = (LinearLayout) view.findViewById(R.id.shoplist);
        shoplist.setOnClickListener(this);
        yasnbang = (LinearLayout) view.findViewById(R.id.yasnbang);
        yasnbang.setOnClickListener(this);
        //订单状态
        initViewOrder(view);
        //下拉刷新
        initSwipeRefreshLayout(view);
        homeConvenientBanner = (ConvenientBanner) view.findViewById(R.id.home_convenientBanner);
        //初始化tab
        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        tablayout.addOnTabSelectedListener(this);
        initRecyclerView(view);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
    }
    private void initLoginData(HomeModel.MemberBean member) {
        if (member ==null||"".equals(member)){
            notLogin.setVisibility(View.VISIBLE);
            home_infomessage.setVisibility(View.GONE);
            loginImage.setBackgroundResource(R.mipmap.unlogin);
            home_collect.setVisibility(View.GONE);
        }else {
            loginImage.setBackgroundResource(R.mipmap.login_n_yasn);
            notLogin.setVisibility(View.GONE);
            home_infomessage.setVisibility(View.VISIBLE);
            if (member ==null){
                notLogin.setVisibility(View.VISIBLE);
                home_infomessage.setVisibility(View.GONE);
                home_collect.setVisibility(View.GONE);
            }else {
                home_collect.setVisibility(View.VISIBLE);
                //
                int member_id = member.getMember_id();
                SharePrefHelper.getInstance(getActivity()).putSpString("memberid",String.valueOf(member_id));
//                HelpUtils.getGoodsNum(Config.CARTGOODSNUM+member_id,handler);
                ((MainActivityNew)getActivity()).setCartNum(Integer.valueOf(member.getCartCount()));
                //账号
                String uname = member.getUname();
                homeAccount.setText(uname == null?"未知":uname);
                SharePrefHelper.getInstance(getActivity()).putSpString("uname",uname == null?"游客":uname);
                //星级
                String levelName = member.getLevelName();
                homeGrade.setText((levelName == null||"".equals(levelName))?"未知":levelName);
                //地方站ID
                int regionId = member.getRegionId();
                SharePrefHelper.getInstance(getActivity()).putSpString("regionId",String.valueOf(regionId));
                int digital_member = member.getDigital_member();
                if (digital_member == 0){//未开通雅森帮
                    undredgeYsenHelp.setVisibility(View.GONE);
                    okdredgeYsenHelp.setVisibility(View.GONE);
                    homeGrade.setVisibility(View.VISIBLE);
                    int lv_id = member.getLv_id();
                    if (lv_id == 2){
                        whiteTopText.setText("认证审核中");
                        homeGrade.setVisibility(View.GONE);
                    }else if (lv_id == 3){
                        whiteTopText.setText("审核未通过  ");
                        undredgeYsenHelp.setText("查看原因");
                        String memssage = member.getMessage();//审核未通过原因
                        Log.e("TAG_原因","memssage="+memssage);
                        SharePrefHelper.getInstance(getActivity()).putSpString("memssage",memssage);
                        undredgeYsenHelp.setVisibility(View.VISIBLE);
                        homeGrade.setVisibility(View.GONE);
                    }else if (lv_id == 5){
                        whiteTopText.setText("");
                        undredgeYsenHelp.setText("去认证");
                        undredgeYsenHelp.setVisibility(View.VISIBLE);
                        homeGrade.setVisibility(View.GONE);
                    }else if (lv_id == 1){
                        whiteTopText.setText("");
                        undredgeYsenHelp.setText("去认证");
                        undredgeYsenHelp.setVisibility(View.VISIBLE);
                        homeGrade.setVisibility(View.GONE);
                    }else {
                        whiteTopText.setText("");
                        undredgeYsenHelp.setVisibility(View.VISIBLE);
                        undredgeYsenHelp.setText("开通雅森帮");
                        homeGrade.setVisibility(View.VISIBLE);
                        okdredgeYsenHelp.setVisibility(View.GONE);
                    }
                }else  if (digital_member == 1){//已开通雅森帮
                    loginImage.setBackgroundResource(R.mipmap.login_y_yasn);
                    undredgeYsenHelp.setVisibility(View.INVISIBLE);
                    okdredgeYsenHelp.setVisibility(View.VISIBLE);
                    int endDate = member.getEndDate();
                    String expireTime = HelpUtils.getDateToString(endDate);
                    String minNumberString = String.format("会员有效期 %s", expireTime);
                    SpannableStringBuilder span = new SpannableStringBuilder(minNumberString);
                    span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),R.color.orange)), 0, 5,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    okdredgeYsenHelp.setText(span);
                }
            }

        }
    }
    private ImageView loginImage;
    private TextView login,register;
    private TextView homeAccount,homeGrade,undredgeYsenHelp,okdredgeYsenHelp;
    private TextView whiteTopText;
    private void initLoginView(View view) {
        topinfomssage = (FrameLayout) view.findViewById(R.id.home_topinfomssage);
        topinfomssage.setOnClickListener(this);

        notLogin = (LinearLayout) view.findViewById(R.id.notlogin);
        home_infomessage = (RelativeLayout) view.findViewById(R.id.home_infomessage);
        //登陆图片
        loginImage = (ImageView) view.findViewById(R.id.loginImage);
        //登陆
        login = (TextView) view.findViewById(R.id.login);
        login.setOnClickListener(this);
        //注册
        register = (TextView) view.findViewById(R.id.register);
        register.setOnClickListener(this);
        //账号
        homeAccount = (TextView) view.findViewById(R.id.home_account);
        //星级
        homeGrade = (TextView) view.findViewById(R.id.home_grade);
        whiteTopText = (TextView) view.findViewById(R.id.whiteTopText);
        //未开头雅森帮
        undredgeYsenHelp = (TextView) view.findViewById(R.id.undredge_YsenHelp);
        undredgeYsenHelp.setVisibility(View.VISIBLE);
        undredgeYsenHelp.setOnClickListener(this);
        //已开头雅森帮
        okdredgeYsenHelp = (TextView) view.findViewById(R.id.okdredge_YsenHelp);
        okdredgeYsenHelp.setVisibility(View.INVISIBLE);
    }

    private void initRecyclerView(View view) {
        //初始化tabRecyclerView
//        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
//        nestedScrollView.setVisibility(View.VISIBLE);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
//        nestedScrollView.setVisibility(View.GONE);
//        recyNested = (RecyclerView) view.findViewById(R.id.recyNested);
        mLinearLayoutManager = new RecyclerLayoutManager(getActivity());
        mLinearLayoutManager.setScrollEnabled(true);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        recyclerview.setLayoutManager(mLinearLayoutManager);
//        layoutManagerNested = new RecyclerLayoutManager(getActivity());
//        layoutManagerNested.setScrollEnabled(true);
//        layoutManagerNested.setAutoMeasureEnabled(true);
//        recyNested.setLayoutManager(layoutManagerNested);
        //创建Adapter
        adapter = new HomeRecyclerAdapter(getActivity());
        adapter.setOnItemClickListener(this);

        recyclerview.setAdapter(adapter);
//        recyNested.setAdapter(adapter);
        recyclerview.addOnScrollListener(new RecyclerViewListener());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.login:
                startWebViewActivity(Config.LOGINWEBVIEW);
                break;
            case R.id.register:
                startWebViewActivity(Config.REGISTERWEBVIEW);
                break;
            case R.id.undredge_YsenHelp:
                String undredgeYsenHelpContext = undredgeYsenHelp.getText().toString();
                if ("去认证".equals(undredgeYsenHelpContext)){
                    startWebViewActivity(Config.ATTESTATION);
                }else  if ("开通雅森帮".equals(undredgeYsenHelpContext)){
                    startWebViewActivity(Config.DREDGEYASNHELP);
                }else  if ("查看原因".equals(undredgeYsenHelpContext)){
                    String memssage = SharePrefHelper.getInstance(getActivity()).getSpString("memssage");
                    showUpgradeDialog(memssage);
                }
                break;
            case R.id.home_topinfomssage:
                break;
            case R.id.topsearch://搜索按钮
                intent = new Intent(getActivity(), HotLableActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
                break;
            case R.id.shoplist://常购清单
//                startActivity(new Intent(getActivity(), OftenShopActivity.class));
                startWebViewActivity(Config.SHOPLIST);
                break;
            case R.id.yasnbang://雅森帮
                startWebViewActivity(Config.YASNBANG);
                break;
            case R.id.home_collect://收藏
//                startActivity(new Intent(getActivity(), CollectActivity.class));
                startWebViewActivity(Config.HOMECOLLECT);
                break;
            case R.id.home_service://客服
                SobotUtil.startSobot(getActivity(),null);
                break;
            case R.id.orderStateLinear:
                startWebViewActivity(Config.MEORDER);
                break;
        }
    }

    private void initSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setMultiSwipeRefreshClickListener(this);
        //设置样式刷新显示的位置
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {//展开
                    isOpen = true;
                    mSwipeRefreshLayout.setEnabled(true);
                    //设置不刷新
//                    mSwipeRefreshLayout.setEnabled(false);
//                    nestedScrollView.setVisibility(View.VISIBLE);
//                    recyclerview.setVisibility(View.GONE);
                } else {
                    isOpen = false;
                    mSwipeRefreshLayout.setEnabled(false);
//                    if (System.currentTimeMillis() - firstTime < 500) {//500毫秒记录一次
//                        if (isOnClickTab) {
//                            nestedScrollView.setVisibility(View.GONE);
//                            recyclerview.setVisibility(View.VISIBLE);
//                        } else {
//                            nestedScrollView.setVisibility(View.VISIBLE);
//                            recyclerview.setVisibility(View.GONE);
//                        }
//                        isOnClickTab = false;
//                    } else {
//                        firstTime = System.currentTimeMillis();
//                    }
                }
            }
        });
    }

    List<HomeRecyModel> homerecyList = new ArrayList<>();//储存转换后的数据格式
    List<String> tabTextList = new ArrayList<>();
    //tablayout和Recycler赋值
    private void initTabLayout(List<HomeModel.SubjectsBean> subjects, HomeModel.PriceDataBean priceData, HomeModel.MemberBean member) {
        int tabCount = tablayout.getTabCount();
        if (homerecyList != null) {
            homerecyList.clear();
        }
        int priceDisplayType = 0;
        String priceDisplayMsg = "";
        if (priceData != null) {
            priceDisplayType = priceData.getPriceDisplayType();
            priceDisplayMsg = priceData.getPriceDisplayMsg();
        }
        for (int i = 0, j = subjects.size(); i < j; i++) {

            HomeModel.SubjectsBean subjectsBean = subjects.get(i);
            String title = subjectsBean.getTitle();
            if (tabCount<=0){
                tabTextList.add(title);
                TabLayout.Tab tab = tablayout.newTab().setText(title);
                tablayout.addTab(tab);
            }

            HomeRecyModel homerecyTab = new HomeRecyModel();
            homerecyTab.setItemType(1);
            homerecyTab.setText(title);
            int subject_id = subjectsBean.getSubject_id();
            homerecyTab.setSubject_id(subject_id);
            homerecyList.add(homerecyTab);
            List<HomeModel.SubjectsBean.ContentBean> contentList = subjectsBean.getContent();
            for (int k = 0, l = contentList.size(); k < l; k++) {
                HomeModel.SubjectsBean.ContentBean contentBean = contentList.get(k);

                int goods_id = contentBean.getId();
                String small = contentBean.getSmall();//图片
                String name = contentBean.getGoods_name();//名字
                String advert = contentBean.getAdvert();//促销折扣提示文字
                int has_discount = contentBean.getHas_discount();//是否有折扣价
                String discount_price = contentBean.getDiscount_price();//折扣价
                double price = contentBean.getPrice();//正常价格
                int totalBuyCount = contentBean.getTotalBuyCount();//已售数量

                HomeRecyModel homeRecy = new HomeRecyModel();
                homeRecy.setGoodsid(String.valueOf(goods_id));//商品ID
                homeRecy.setItemType(2);
                homeRecy.setText(name);
                homeRecy.setImage(small);
                homeRecy.setSubject_id(subject_id);
                if (priceDisplayType == 0) {//取正常价格
                    if (has_discount == 0) {//正常价格
                        homeRecy.setMoney(String.valueOf(price));
                    } else {//折扣价
                        homeRecy.setMoney(discount_price);
                    }
                } else {//取文字信息
                    homeRecy.setMoney(priceDisplayMsg == null ? "" : priceDisplayMsg);
                }
                homeRecy.setAdvert(advert);
                homeRecy.setTotalBuyCount(String.valueOf(totalBuyCount));

                int store_id = contentBean.getStore_id();
                if (store_id == 1) {//自营
                    homeRecy.setAutotrophy(true);
                } else {
                    homeRecy.setAutotrophy(false);
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
                int have_voice = contentBean.getHave_voice();//是否有音频 1：有
                homeRecy.setButton3(have_voice);
                int is_success_case = contentBean.getIs_success_case(); //是否成功案例 1：有
                homeRecy.setButton2(is_success_case);
                homerecyList.add(homeRecy);
            }
        }
        HomeRecyModel footView = new HomeRecyModel();
        footView.setItemType(Config.TYPE_FOOTVIEW);
        homerecyList.add(footView);
        if (member ==null){
            adapter.setData(homerecyList,"登录看价格");
            SharePrefHelper.getInstance(getActivity()).putSpString("loginState","登录看价格");
        }else {
            int priceDisplayType1 = member.getPriceDisplayType();
            if (priceDisplayType1 == 0){
                adapter.setData(homerecyList,"0");
                SharePrefHelper.getInstance(getActivity()).putSpString("loginState","0");
            }else {
                String priceDisplayMsg1 = member.getPriceDisplayMsg();
                adapter.setData(homerecyList,priceDisplayMsg1);
                SharePrefHelper.getInstance(getActivity()).putSpString("loginState",priceDisplayMsg1);
            }
        }
        List<HomeModel.AdvsBean> advs = homemodel.getAdvs();
        if (advs != null && advs.size() > 0) {
            //轮播图数据
            initViewPagerImage(advs);
        } else {
            homeConvenientBanner.setVisibility(View.GONE);
        }
    }

    private void initViewPagerImage(List<HomeModel.AdvsBean> advs) {
        if (advs.size() > 1) {
            homeConvenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                @Override
                public LocalImageHolderView createHolder() {
                    return new LocalImageHolderView();
                }
            }, advs)
           .setPointViewVisible(true)    //设置指示器是否可见
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.mipmap.normal, R.mipmap.unnormal})
                    //设置指示器位置（左、中、右）
                    .setOnItemClickListener(this)
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .startTurning(3000)
                    .setManualPageable(true)  //设置手动影响（设置了该项无法手动切换）
            ;
        } else {
            homeConvenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new LocalImageHolderView();
                }
            }, advs)
                    .setPointViewVisible(false)    //设置指示器是否可见
                    .setOnItemClickListener(this)
                    .setManualPageable(false)  //设置手动影响（设置了该项无法手动切换）
            ;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        homeConvenientBanner.startTurning(3000);// 2s 换一张
    }

    @Override
    public void onPause() {
        super.onPause();
        homeConvenientBanner.stopTurning();
        getActivity().overridePendingTransition(0, 0);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
    private ImageView orderImage,expressStateImage;
    private TextView expressStateText;
    private TextView orderHint,orderTime;
    private void initViewOrder(View view) {
        /**
         * 订单状态 status
         *      <待支付:1> <待发货:2 ><待收货:3> <已收货:4 ><已完成:5><取消:6>
         * 创建时间 createTime
         * 物流名称 logiName
         * 物流单号 shipNo
         * 取消时间 cancelDateTime
         * 商品图片 image
         */
        orderStateLinear = (LinearLayout) view.findViewById(R.id.orderStateLinear);
        orderStateLinear.setOnClickListener(this);
        orderImage = (ImageView) view.findViewById(R.id.orderImage);
        expressStateImage = (ImageView) view.findViewById(R.id.express_stateimage);
        expressStateText = (TextView) view.findViewById(R.id.express_stateText);
        orderHint = (TextView) view.findViewById(R.id.orderHint);
        orderTime = (TextView) view.findViewById(R.id.orderTime);
    }

    List<HomeModel.SubjectsBean> subjects;
    HomeModel homemodel;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                mSwipeRefreshLayout.setRefreshing(false);
                if (returnCode == 200) {
                    homemodel = JSON.parseObject(returnData, HomeModel.class);
                    HomeModel.PlaceBean place = homemodel.getPlace();
                    //会员信息
                    HomeModel.MemberBean member1 = homemodel.getMember();
                    initLoginData(member1);
                    if (place!=null&&!"".equals(place)){
                        String regionName = place.getRegionName();
                        address.setText(regionName==null?"":regionName);
                    }else {
                        address.setText("");
                    }
                    //订单状态
                    setOrderState();
                    //tablayout和Recycler赋值
                    subjects = homemodel.getSubjects();
                    HomeModel.PriceDataBean priceData = homemodel.getPriceData();
                    HomeModel.MemberBean member = homemodel.getMember();
                    if (subjects != null && subjects.size() > 0) {
                        initTabLayout(subjects, priceData,member);
                        tablayout.setVisibility(View.VISIBLE);
                    } else {
                        tablayout.setVisibility(View.GONE);
                    }
                } else if (returnCode == 401) {
                    cleanToken();
                    OkHttpDemand();
                }else {
                    homeConvenientBanner.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void setOrderState() {
        HomeModel.OrderBean order = homemodel.getOrder();
        if (order!=null){
            orderStateLinear.setVisibility(View.VISIBLE);
            int status = order.getStatus();
            int createTime = order.getCreateTime();
            String createTimeString = HelpUtils.getDateToString(createTime);
            int cancelDateTime = order.getCancelDateTime();
            String cancelTimeString = HelpUtils.getDateToString(cancelDateTime);
            String logiName = order.getLogiName();
            String shipNo = order.getShipNo();
            if (status == 1){
                expressStateImage.setBackgroundResource(R.mipmap.daifukuan);
                expressStateText.setText("待付款");
                orderHint.setText("请尽快完成付款,否则会取消!");
                orderTime.setText(createTimeString+" 下单成功");
            }else if (status == 2){
                expressStateImage.setBackgroundResource(R.mipmap.daifahuo);
                expressStateText.setText("代发货");
                orderHint.setText("仓库正在紧急备货中!");
                orderTime.setText(createTimeString+" 下单成功");
            }if (status == 3){
                expressStateImage.setBackgroundResource(R.mipmap.daishouhuo);
                expressStateText.setText("待收货");
                orderHint.setText("您的订单已发货!");
                orderTime.setText(logiName+" "+shipNo);
            }if (status == 4){
                expressStateImage.setBackgroundResource(R.mipmap.yiwancheng);
                expressStateText.setText("已收货");
                orderHint.setText("您的订单已收货!");
                if (TextUtils.isEmpty(logiName)&&TextUtils.isEmpty(shipNo)){
                    orderTime.setVisibility(View.GONE);
                }else {
                    orderTime.setVisibility(View.VISIBLE);
                    orderTime.setText(logiName+" "+shipNo);
                }
            }if (status == 5){
                expressStateImage.setBackgroundResource(R.mipmap.yiwancheng);
                expressStateText.setText("已完成");
                orderHint.setText("您的订单已完成!");
                if (TextUtils.isEmpty(logiName)&&TextUtils.isEmpty(shipNo)){
                    orderTime.setVisibility(View.GONE);
                }else {
                    orderTime.setVisibility(View.VISIBLE);
                    orderTime.setText(logiName+" "+shipNo);
                }
            }if (status == 6){
                expressStateImage.setBackgroundResource(R.mipmap.yiquxiao);
                expressStateText.setText("已取消");
                orderHint.setText("您的订单已取消!");
                orderTime.setText(cancelTimeString+" 订单取消");
            }
            String image = order.getImage();
            Glide.with(this)
                    .load(image)
                    .centerCrop()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(orderImage);
        }else {
            orderStateLinear.setVisibility(View.GONE);
        }
    }

    //viewpager点击事件
    @Override
    public void onItemClick(int position) {
//        ToastUtil.showToast(position+"");
    }

    //列表item点击事件
    @Override
    public void OnItemClick(View view, int position) {
        startFragmentCount(0, position);
    }

    @Override
    public void OnItemLongClick(View view, int position) {

    }

    @Override
    public void OnClickTabMore(int listPosition) {
        if (homerecyList == null) {
            return;
        }
        HomeRecyModel homeRecyModel = homerecyList.get(listPosition);
        int subject_id = homeRecyModel.getSubject_id();
        String text = homeRecyModel.getText();
        startWebViewActivity(Config.ONCLICKTABMORE+"?id="+subject_id+"&title="+text);
//        if ("高毛利商品".equals(text)) {
//            startActivity(new Intent(getActivity(), HighProfitActivity.class));
//        }
    }

    @Override
    public void OnClickRecyButton(int itemPosition, int listPosition) {
        startFragmentCount(itemPosition, listPosition);
    }

    private void startFragmentCount(int itemPosition, int listPosition) {
        if (homerecyList != null) {
            HomeRecyModel homeRecyModel = homerecyList.get(listPosition);
            int itemType = homeRecyModel.getItemType();
            if (itemType == 0) {
                return;
            }
            //itemPosition 教你卖好、成功案例、语音讲解对应的Position
            SharePrefHelper.getInstance(getActivity()).putSpInt("GOODSFRAGMENTID", itemPosition);
            String goodsid = homeRecyModel.getGoodsid();
            Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
            SharePrefHelper.getInstance(getActivity()).putSpString("GOODSID", goodsid);
            startActivity(intent);
//            startWebViewActivity(Config.GOODSDETAILS+goodsid);
        }
    }

    boolean isFrist = true;
    boolean isOnClickTab = false;
//    boolean isDefaultOnClickTab = false;
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int selectPosition = tab.getPosition();
        if (isFrist) {
            isFrist = false;
            return;
        }
        isOnClickTab = true;
        if (isOpen){
            appBarLayout.setExpanded(false, true);
        }
//        nestedScrollView.setVisibility(View.GONE);
//        recyclerview.setVisibility(View.VISIBLE);
        String tabText = tab.getText().toString();
        Log.e("TAG_TAB", "selectPosition=" + selectPosition);
        //定位计算方法
        for (int i = 0, j = homerecyList.size(); i < j; i++) {
            HomeRecyModel homeRecyModel = homerecyList.get(i);
            if (tabText.equals(homeRecyModel.getText())) {
                move(i);
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh() {
//        isFrist = true;
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

    //点击列表取消软键盘
    @Override
    public void OnMultiSwipeRefreshClick() {
//        onFocusChange(topsearch,false);
    }

    public class LocalImageHolderView implements Holder<HomeModel.AdvsBean> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
//            Log.e("TAG_轮播图","createView");
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, HomeModel.AdvsBean advsbean) {
            String atturl = advsbean.getAtturl();
//            Log.e("TAG_轮播图","atturl="+atturl);
            Glide.with(getActivity().getApplicationContext())
                    .load(atturl)
                    .centerCrop()
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.errorimage)
                    .error(R.mipmap.errorimage)
                    .into(imageView);
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Bundle bundle_car = msg.getData();
                    int position = bundle_car.getInt("position");
                    break;
                case 0:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    private void move(int n) {
        if (adapter == null) {
            return;
        }
        if (n < 0 || n >= adapter.getItemCount()) {
            ToastUtil.showToast("超出范围了");
            return;
        }
        mIndex = n;
        recyclerview.stopScroll();
        isOpen = true;//recy位置发生改变
        if (animationBool) {
            smoothMoveToPosition(n);
        } else {
            moveToPosition(n);
        }
    }

    //动画定位
    private void smoothMoveToPosition(int position) {

        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        Log.e("TAG_Scroll", "firstItem=" + firstItem+";position="+position+";lastItem="+lastItem);
        if (position <= firstItem) {
            recyclerview.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            int top = recyclerview.getChildAt(position - firstItem).getTop();
            recyclerview.smoothScrollBy(0, top);
        } else {
            recyclerview.smoothScrollToPosition(position);
            move = true;
        }
    }

    //无动画快速定位
    private void moveToPosition(int position) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (position <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerview.scrollToPosition(position);
        } else if (position <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerview.getChildAt(position - firstItem).getTop();
            recyclerview.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerview.scrollToPosition(position);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }

    }

    private boolean isOpen = false;//

    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerViewB, int newState) {
            super.onScrollStateChanged(recyclerview, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE && animationBool) {
                move = false;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < recyclerview.getChildCount()) {
                    int top = recyclerview.getChildAt(n).getTop();
                    recyclerview.smoothScrollBy(0, top);
                }
            }
//            //表示是否能向上滚动，true表示能滚动，false表示已经滚动到底部
//            boolean canScrollVertically = recyclerview.canScrollVertically(1);
//            if (canScrollVertically&&isOpen){
//                mLinearLayoutManager.setScrollEnabled(true);
//            }else {
//                appBarLayout.setExpanded(false, true);
//            }
            //表示是否能向下滚动，true表示能滚动，false表示已经滚动到顶部
            boolean scrollVertically = recyclerview.canScrollVertically(-1);
            if (!isOpen && !scrollVertically && newState == RecyclerView.SCROLL_STATE_IDLE) {
                Log.e("TAG_滚动","isOnClickTab="+isOnClickTab);
                if (isOnClickTab){
                    isOnClickTab = false;
                }else {
                    //设置折叠或展开状态为折叠
//                    nestedScrollView.setVisibility(View.VISIBLE);
//                    recyclerview.setVisibility(View.GONE);
                    //expanded true:展开  false:收缩
                    appBarLayout.setExpanded(true, true);
                    return;
                }
            }
            Log.e("TAG_滚动","滚动");
//            appBarLayout.setExpanded(false, true);
        }

        @Override
        public void onScrolled(RecyclerView recyclerViewB, int dx, int dy) {
            super.onScrolled(recyclerview, dx, dy);
            if (move && !animationBool) {
                move = false;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < recyclerview.getChildCount()) {
                    int top = recyclerview.getChildAt(n).getTop();
                    recyclerview.scrollBy(0, top);
                }
            }
        }
    }

    protected AlertDialog mUpgradeNotifyDialog;

    private void showUpgradeDialog(String reasonString) {
        LayoutInflater factor = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.reason_dialog, null);
        TextView reason = (TextView) serviceView.findViewById(R.id.reason);
        if ((reasonString == null || "".equals(reasonString))){
            reason.setText("认证审核失败！");
        }else {
            reason.setText("由于" + reasonString + ",认证失败,请重新认证!");
        }
        TextView okbtn = (TextView) serviceView.findViewById(R.id.okbtn);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWebViewActivity(Config.ATTESTATION);
            }
        });
        Activity activity = getActivity();
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        mUpgradeNotifyDialog = builder.create();
        mUpgradeNotifyDialog.show();
        mUpgradeNotifyDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
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
        Log.e("TAG_fragment","home="+msg);
        if ("loginSucceed".equals(msg)&&getUserVisibleHint()){
            isFrist = true;
            OkHttpDemand();
        }
        else if ("loginout".equals(msg)){
            isFrist = true;
            home_collect.setVisibility(View.GONE);
            OkHttpDemand();
        }
    }
}
