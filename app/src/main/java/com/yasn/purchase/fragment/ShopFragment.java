package com.yasn.purchase.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.yasn.purchase.R;
import com.yasn.purchase.activity.AddressActivity;
import com.yasn.purchase.activity.AuthorActivity;
import com.yasn.purchase.activity.LoginActivity;
import com.yasn.purchase.activity.MainActivity;
import com.yasn.purchase.activity.MakerCodeActivity;
import com.yasn.purchase.activity.MakerCreateActivity;
import com.yasn.purchase.activity.MakerExploitShopActivity;
import com.yasn.purchase.activity.MakerShopOrderActivity;
import com.yasn.purchase.activity.MakerShroffAccountActivity;
import com.yasn.purchase.activity.SettingActivity;
import com.yasn.purchase.activity.StaffCreateActivity;
import com.yasn.purchase.activity.StaffMessageActivity;
import com.yasn.purchase.activity.base.BaseYasnFragment;
import com.yasn.purchase.adapter.ShopFuncAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.help.LoginOut;
import com.yasn.purchase.help.SobotUtil;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.EventBusMsg;
import com.yasn.purchase.model.ShopInfoModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;
import com.yasn.purchase.view.RcDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.xcd.com.mylibrary.help.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.view.BadgeView;
import www.xcd.com.mylibrary.widget.SnsTabWidget;


/**
 * Created by Android on 2017/9/5.
 * 门店
 */
public class ShopFragment extends BaseYasnFragment implements OnRcItemClickListener ,SwipeRefreshLayout.OnRefreshListener {

    private RelativeLayout title;
    private LinearLayout integral, feedback, callservice, collect;
    //统计查看更多、创客
    private LinearLayout lookall,maker;
    private RecyclerView shoprecy;
    private MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private int imageUrl[] = {R.mipmap.jifen, R.mipmap.help, R.mipmap.phoneconsult
            , R.mipmap.serviceing, R.mipmap.zhuanpiao,R.mipmap.oil};
    private int nameTitle[] = {R.string.mejifen,  R.string.help
            , R.string.phoneconsult, R.string.serviceing, R.string.zhuanpiao,R.string.yongyouquery};
    private int imageUrlAuth[] = {R.mipmap.jifen, R.mipmap.shouhuo_address,
//            R.mipmap.numbervip,
            R.mipmap.help, R.mipmap.phoneconsult, R.mipmap.serviceing
            , R.mipmap.zhuanpiao,R.mipmap.oil};
    private int nameTitleAuth[] = {R.string.mejifen, R.string.shouhuo_addressv,
//            R.string.numbervip,
            R.string.help, R.string.phoneconsult, R.string.serviceing
            , R.string.zhuanpiao,R.string.yongyouquery};
    private SnsTabWidget tabWidget,makertabwidget;
    private static int[] ORDER_TAB_TEXT = new int[]{
            R.string.obligation, R.string.overhang, R.string.waitreceiving, R.string.allorder
    };
    private static int[] ORDER_TAB_IMAGE = new int[]{
            R.mipmap.obligation, R.mipmap.overhang, R.mipmap.waitreceiving, R.mipmap.allorder
    };

    private static int[] MAKERDREDGE_TAB_TEXT = new int[]{
            R.string.makerdredge,R.string.makerdredge,R.string.makerdredge,R.string.makerdredge
    };
    private static int[] MAKERDREDGE_TAB_IMAGE = new int[]{
            R.mipmap.makerdredge,R.mipmap.makerdredge,R.mipmap.makerdredge,R.mipmap.makerdredge
    };
    private static int[] MAKER_TAB_TEXT = new int[]{
            R.string.makerqrcode, R.string.makerreceiptaccount, R.string.makerexploitshop, R.string.makershoporder
    };
    private static int[] MAKER_TAB_IMAGE = new int[]{
            R.mipmap.makerqrcode, R.mipmap.makerreceiptaccount, R.mipmap.makerexploitshop, R.mipmap.makershoporder
    };
    /**
     * 采购金额、采购数量、订单数
     *
     * @return
     */
    private TextView totalMoney, goodsNum, goodsHint, orderNum;
    //员工管理、创建员工、管理员工
    LinearLayout meanage,createstaff,meanagestaff;
    //设置
    private ImageView setting;

    //    public static int SHOPWEBVIEWCODE = 50000;
    private RelativeLayout rlMainError;//错误布局
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void OkHttpDemand() {
        Log.e("TAG_initView","SHOP_OkHttp");
        token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
        resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(getActivity()).getSpString("resetTokenTime");
        if (token != null && !"".equals(token)) {
            //门店个人信息
            Map<String, Object> paramsShop = new HashMap<String, Object>();
            paramsShop.put("access_token", token);
            okHttpGet(101, Config.SHOP, paramsShop);
        } else if (resetToken != null && !"".equals(resetToken)) {
            //门店个人信息
            Map<String, Object> paramsShop = new HashMap<String, Object>();
            paramsShop.put("access_token", resetToken);
            okHttpGet(102, Config.SHOP, paramsShop);
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("TAG_onHiddenChanged","shop="+hidden);
        if (!hidden) { //隐藏时所作的事情
            lazyLoad();
            isVisible = false;
        }else {
            isVisible = true;
        }
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        title = (RelativeLayout) view.findViewById(R.id.title);
        title.setVisibility(View.GONE);
        //下拉刷新
        initSwipeRefreshLayout(view);
        initTopShopInfo(view);
        //设置
        setting = (ImageView) view.findViewById(R.id.setting);
        setting.setOnClickListener(this);
        //积分
        integral = (LinearLayout) view.findViewById(R.id.integral);
        integral.setOnClickListener(this);
        //反馈
        feedback = (LinearLayout) view.findViewById(R.id.feedback);
        feedback.setOnClickListener(this);
        //客服
        callservice = (LinearLayout) view.findViewById(R.id.callservice);
        callservice.setOnClickListener(this);
        //收藏
        collect = (LinearLayout) view.findViewById(R.id.collect);
        collect.setOnClickListener(this);
        //员工管理
        meanage = (LinearLayout) view.findViewById(R.id.meanage);
        meanage.setVisibility(View.GONE);
        //我的订单
        initShopOrder(view);
        //我的创客
        maker = (LinearLayout) view.findViewById(R.id.maker);
        makertabwidget = (SnsTabWidget) view.findViewById(R.id.makertabwidget);
        maker.setVisibility(View.GONE);
        initShopMaker();
        //采购金额
        totalMoney = (TextView) view.findViewById(R.id.totalMoney);
        totalMoney.setOnClickListener(this);
        //采购数量
        goodsNum = (TextView) view.findViewById(R.id.goodsNum);
        goodsNum.setOnClickListener(this);
        goodsHint = (TextView) view.findViewById(R.id.goodsHint);
        //订单数
        orderNum = (TextView) view.findViewById(R.id.orderNum);
        orderNum.setOnClickListener(this);
        //统计查看更多
        lookall = (LinearLayout) view.findViewById(R.id.lookall);
        lookall.setOnClickListener(this);
        //员工管理
        createstaff = (LinearLayout) view.findViewById(R.id.ll_createstaff);
        createstaff.setOnClickListener(this);
        meanagestaff = (LinearLayout) view.findViewById(R.id.ll_meanagestaff);
        meanagestaff.setOnClickListener(this);
        //实例化功能列表
        initFuncData(imageUrl,nameTitle);
        //error界面
        rlMainError = (RelativeLayout) view.findViewById(R.id.rl_mainError);
        rlMainError.setVisibility(View.GONE);
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
    private TextView account, companyName, department, shopGrade;
    private TextView undredgeYsenHelp, okdredgeYsenHelp;
    private ImageView shopImage;
    private TextView whiteTopText;
    private LinearLayout gradeLinear;//药总和星级

    private void initTopShopInfo(View view) {
        shopImage = (ImageView) view.findViewById(R.id.shopImage);
        account = (TextView) view.findViewById(R.id.account);
        companyName = (TextView) view.findViewById(R.id.company_name);
        gradeLinear = (LinearLayout) view.findViewById(R.id.gradeLinear);
        department = (TextView) view.findViewById(R.id.department);
        shopGrade = (TextView) view.findViewById(R.id.shop_grade);
        undredgeYsenHelp = (TextView) view.findViewById(R.id.undredge_YsenHelp);
        undredgeYsenHelp.setOnClickListener(this);
        okdredgeYsenHelp = (TextView) view.findViewById(R.id.okdredge_YsenHelp);
        okdredgeYsenHelp.setVisibility(View.GONE);
        whiteTopText = (TextView) view.findViewById(R.id.whiteTopText);
        whiteTopText.setVisibility(View.GONE);
    }

    private void initFuncData(int imageUrl[],int nameTitle[]) {
        List<Map<String, Integer>> list = new ArrayList<>();
        for (int i = 0; i < imageUrl.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            map.put("ImageUrl", imageUrl[i]);
            map.put("nameTitle", nameTitle[i]);
            list.add(map);
        }
        GridLayoutManager girdLayoutManager = new GridLayoutManager(getActivity(), 4);
        shoprecy.setLayoutManager(girdLayoutManager);
        //实例化adapter
        ShopFuncAdapter adapter = new ShopFuncAdapter(getActivity(), list);
        adapter.setOnItemClickListener(this);
        shoprecy.setAdapter(adapter);
        RcDecoration rcDecoration = new RcDecoration(getActivity(), RcDecoration.VERTICAL_LIST);
        shoprecy.addItemDecoration(rcDecoration);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.undredge_YsenHelp:
                String undredgeYsenHelpContext = undredgeYsenHelp.getText().toString();
                if ("去认证".equals(undredgeYsenHelpContext)) {
//                    startWebViewActivity(Config.ATTESTATION);
                    startActivity(new Intent(getActivity(),AuthorActivity.class));
                } else if ("开通雅森帮".equals(undredgeYsenHelpContext)) {
                    startWebViewActivity(Config.DREDGEYASNHELP);
                } else if ("查看原因".equals(undredgeYsenHelpContext)) {
                    String memssage = SharePrefHelper.getInstance(getActivity()).getSpString("memssage");
                    showAuthDialog(memssage);
                }
                break;
            case R.id.setting:
//                startWebViewActivity(Config.LOGINOUTWEBVIEW);
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                break;
            case R.id.integral://积分

                ((MainActivity)getActivity()).startIntegralActivity();
                break;
            case R.id.feedback://反馈
                SobotUtil.startSobot(getActivity(),null);
                break;
            case R.id.callservice://客服
                SobotUtil.startSobot(getActivity(),null);
                break;
            case R.id.collect://收藏
                ((MainActivity)getActivity()).startCollectActivity();
                break;
            case R.id.lookall://统计查看更多
                ((MainActivity)getActivity()).startStatisticsActivity(String.valueOf(member_id));
                break;
            case R.id.ll_createstaff://创建员工
//                startWebViewActivity(Config.SHOPCREATESTAFF);
                startActivity(new Intent(getActivity(), StaffCreateActivity.class));
                break;
            case R.id.ll_meanagestaff://管理员工
//                startWebViewActivity(Config.SHOPMEANAGESTAFF);
                startActivity(new Intent(getActivity(), StaffMessageActivity.class));
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        mSwipeRefreshLayout.setRefreshing(false);
        rlMainError.setVisibility(View.GONE);

        switch (requestCode) {
            case 101:
                if (returnCode == 200) {
                    initShopData(returnData);
                } else if (returnCode==401){
                    SharePrefHelper.getInstance(getActivity()).putSpString("token", "");
                    thread = new Thread(networkTask);
                    thread.start();
                }else {
                    ToastUtil.showToast("数据加载异常，请下拉重新加载！");
                }
                break;
            case 102:
                if (returnCode == 200) {
                    initShopData(returnData);
                } else if (returnCode==401){
                    SharePrefHelper.getInstance(getActivity()).putSpString("resetToken", "");
                    SharePrefHelper.getInstance(getActivity()).putSpString("resetTokenTime", "");
                    SharePrefHelper.getInstance(getActivity()).putSpString("regionId", "");
                    SharePrefHelper.getInstance(getActivity()).putSpString("priceDisplayMsg", "");
                    ((MainActivity)getActivity()).startBaseActivity(getActivity(),LoginActivity.class);
                }
                break;
            case 103:
                if (returnCode == 200){
                    Log.e("TAG_邀请","isInvite="+isInvite);
                    if (isInvite == 1){//新用户
                        mInviteNotifyDialog.dismiss();
                    }else {
                        LoginOut.startLoginOut(getActivity());
                        mInviteNotifyDialog.dismiss();
                    }
                }
                ToastUtil.showToast(returnMsg);
                break;
            case 104:
                if (returnCode == 200){
                    if (isInvite == 1){//新用户
                        LoginOut.startLoginOut(getActivity());
                        mInviteNotifyDialog.dismiss();
                    }else {
                        mInviteNotifyDialog.dismiss();
                    }
                }
                ToastUtil.showToast(returnMsg);
                break;
        }

    }

    Thread thread;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (networkTime) {
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("access_token", resetToken);
                        okHttpGet(102, Config.SHOP, params);
                    } else {
                        SharePrefHelper.getInstance(getActivity()).putSpString("token", "");
                        SharePrefHelper.getInstance(getActivity()).putSpString("resetToken", "");
                        SharePrefHelper.getInstance(getActivity()).putSpString("resetTokenTime", "");
                        SharePrefHelper.getInstance(getActivity()).putSpString("regionId", "");
                        SharePrefHelper.getInstance(getActivity()).putSpString("priceDisplayMsg", "");
                        ((MainActivity)getActivity()).startBaseActivity(getActivity(),LoginActivity.class);
                        ToastUtil.showToast("登录已过期,请重新登录！");
                    }
                    break;
                case 1:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    boolean networkTime;
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
            resetTokenTime = SharePrefHelper.getInstance(getActivity()).getSpString("resetTokenTime");
            networkTime = HelpUtils.getNetworkTime(resetTokenTime);
            handler.sendEmptyMessage(0);
        }
    };
    int lv_id = 0;
    int digital_member;
    int member_id;
    private String mobile;
    private void initShopData(String returnData) {
        try {
            if (returnData != null) {
                JSONObject object = new JSONObject(returnData);
                JSONObject countInfor = object.getJSONObject("countInfor");
                int obligationCount = countInfor.optInt("1");
                resetRedPoint(0, obligationCount);
                int overhangCount = countInfor.optInt("2");
                resetRedPoint(1, overhangCount);
                int waitreceivingCount = countInfor.optInt("3");
                resetRedPoint(2, waitreceivingCount);
                resetRedPoint(3, 0);
                ShopInfoModel shopinfomodel = JSON.parseObject(returnData, ShopInfoModel.class);
                if (shopinfomodel != null) {
                    ShopInfoModel.MemberBean member = shopinfomodel.getMember();
                    if (member != null) {
                        member_id = member.getMember_id();
                        //收获地区所在省
                        int provinceId = member.getProvinceId();
                            SharePrefHelper.getInstance(getActivity()).putSpString("provinceId", String.valueOf(provinceId));
                        //地方站名字
                        String regionName = member.getRegionName();
                        SharePrefHelper.getInstance(getActivity()).putSpString("regionName",(regionName==null?"":regionName));
                        //创客
                        makerType = member.getIs_inviteCustomer();
                        initShopMaker();
                        String uname = member.getUname();
                        if (uname == null){
                            uname = "";
                            account.setText("");
                        }else {
                            account.setText(uname);
                        }
                        SharePrefHelper.getInstance(getActivity()).putSpString("uname",uname == null?"游客":uname);
                        String shopName = member.getShopName();
                        if (shopName == null){
                            String lvName = member.getLvName();
                            companyName.setText(lvName);
                        }else {
                            companyName.setText(shopName);
                        }
                        mobile = member.getMobile();

                        String levelName = member.getLevelName();
                        shopGrade.setText(levelName == null ? "未知" : levelName);
                        digital_member = member.getDigital_member();
                        lv_id = member.getLv_id();
                        //临时存储lv_id，判断数据是否普改变
                        int lv_idOld = SharePrefHelper.getInstance(getActivity()).getSpInt("lv_id");
                        if (lv_idOld != lv_id&&lv_idOld>0){
                            SharePrefHelper.getInstance(getActivity()).putSpInt("lv_id",lv_id);
                            //刷新首页数据
                            EventBus.getDefault().post(new EventBusMsg("refresh"));
                        }else if (lv_idOld==-1){
                            SharePrefHelper.getInstance(getActivity()).putSpInt("lv_id",lv_id);
                        }
                        int regionId = member.getRegionId();
                        Log.e("TAG_regionId","门店regionId="+regionId);
                        String regionIdOld = SharePrefHelper.getInstance(getActivity()).getSpString("regionId");
                        Log.e("TAG_regionId","门店regionIdOld="+regionIdOld);
                        if (!TextUtils.isEmpty(regionIdOld)){
                            if (regionId != Integer.parseInt(regionIdOld)){
                                //刷新首页数据
                                EventBus.getDefault().post(new EventBusMsg("refresh"));
                            }
                        }
                        //修改小红点
                        EventBusMsg carNum = new EventBusMsg("carNum");
                        carNum.setCarNum(String.valueOf(member.getCartCount()));
                        EventBus.getDefault().post(carNum);

                        if (this.lv_id >5){
                            //实例化功能列表
                            initFuncData(imageUrlAuth,nameTitleAuth);
                        }else {
                            //实例化功能列表
                            initFuncData(imageUrl,nameTitle);
                        }
                        if (digital_member == 1) {//是数字会员
                            undredgeYsenHelp.setVisibility(View.GONE);
                            okdredgeYsenHelp.setVisibility(View.GONE);//会员有效期
                            gradeLinear.setVisibility(View.VISIBLE);
                            department.setVisibility(View.VISIBLE);
                            int endDate = member.getEndDate();
                            String expireTime = HelpUtils.getDateToString(endDate);
                            String minNumberString = String.format("会员有效期 %s", expireTime);
                            SpannableStringBuilder span = new SpannableStringBuilder(minNumberString);
                            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.orange)), 0, 5,
                                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            okdredgeYsenHelp.setText(span);
                            shopImage.setBackgroundResource(R.mipmap.login_y_yasn);
                        } else {
                            shopImage.setBackgroundResource(R.mipmap.login_n_yasn);
                            undredgeYsenHelp.setVisibility(View.GONE);
                            okdredgeYsenHelp.setVisibility(View.GONE);
                            if (this.lv_id == 2) {
                                whiteTopText.setText("认证审核中");
                                whiteTopText.setVisibility(View.VISIBLE);
                                meanage.setVisibility(View.GONE);
                                gradeLinear.setVisibility(View.VISIBLE);
                                department.setVisibility(View.GONE);
                            } else if (this.lv_id == 3) {
                                whiteTopText.setText("审核未通过  ");
                                whiteTopText.setVisibility(View.VISIBLE);
                                undredgeYsenHelp.setText("查看原因");
                                meanage.setVisibility(View.GONE);
                                undredgeYsenHelp.setVisibility(View.VISIBLE);
                                gradeLinear.setVisibility(View.VISIBLE);
                                department.setVisibility(View.GONE);
                            } else if (this.lv_id == 5) {
                                whiteTopText.setText("");
                                whiteTopText.setVisibility(View.GONE);
                                undredgeYsenHelp.setText("去认证");
                                meanage.setVisibility(View.GONE);
                                undredgeYsenHelp.setVisibility(View.VISIBLE);
                                gradeLinear.setVisibility(View.GONE);
                                department.setVisibility(View.GONE);
                            }else if (this.lv_id == 1) {
                                whiteTopText.setText("");
                                whiteTopText.setVisibility(View.GONE);
                                undredgeYsenHelp.setText("去认证");
                                meanage.setVisibility(View.GONE);
                                undredgeYsenHelp.setVisibility(View.VISIBLE);
                                gradeLinear.setVisibility(View.GONE);
                                department.setVisibility(View.GONE);
                            } else {
                                whiteTopText.setText("");
                                whiteTopText.setVisibility(View.GONE);
                                undredgeYsenHelp.setVisibility(View.GONE);
//                                undredgeYsenHelp.setText("开通雅森帮");
                                gradeLinear.setVisibility(View.VISIBLE);
                                department.setVisibility(View.VISIBLE);
                                okdredgeYsenHelp.setVisibility(View.GONE);
                            }
                        }
                    }
                    ShopInfoModel.StatisticsBean statistics = shopinfomodel.getStatistics();
                    if (statistics != null) {
                        totalMoney.setText("￥" + String.format("%.2f", Double.valueOf(statistics.getTotalCount())));
                        goodsNum.setText("￥" + String.format("%.2f", Double.valueOf(statistics.getGoodsNum())));
                        orderNum.setText(String.valueOf(statistics.getOrderNum()) + "笔");
                    }
                    ShopInfoModel.StoreBean store = shopinfomodel.getStore();
                    if (store != null) {
                        String employeeAuth = store.getEmployeeAuth();
                        SharePrefHelper.getInstance(getActivity()).putSpString("employeeAuth",employeeAuth);
                        String  dutyAuth = "";
                        if (employeeAuth != null) {//0:经理,1:采购 ,2:财务 1,2采购+财务
                            if ("0".equals(employeeAuth)){
                                department.setText("(经理)");
                                dutyAuth = "经理";
                                if (lv_id == 6) {
                                    meanage.setVisibility(View.VISIBLE);
                                }else {
                                    meanage.setVisibility(View.GONE);
                                }
                            }else if ("1".equals(employeeAuth)){
                                department.setText("(采购)");
                                dutyAuth = "采购";
                            }else if ("2".equals(employeeAuth)){
                                department.setText("(财务)");
                                dutyAuth = "财务";
                            }else {
                                int i = employeeAuth.indexOf("2");
                                double goodsNum = statistics.getGoodsNum();
                                Log.e("TAG_goodsNum","goodsNum="+goodsNum);
                                if (i != -1) {
                                    dutyAuth = "采购+财务";
                                    this.goodsNum.setText("￥" + String.format("%.2f", goodsNum));
                                    goodsHint.setText("待支付金额");
                                    department.setText("(采购+财务)");
                                } else {
                                    if ("0".equals(employeeAuth)) {
                                        this.goodsNum.setText("￥" + String.format("%.2f",goodsNum));
                                        goodsHint.setText("待支付金额");
                                    } else {
                                        this.goodsNum.setText(((int)goodsNum) + "件");
                                        goodsHint.setText("采购商品数");
                                    }
                                }
                            }
                        }
                        int isInvite = store.getIsInvite();
                        if (isInvite == 1 || isInvite == 2) {
                            String shopName = store.getShopName();
                            String admin = store.getAdmin();

                            companyName.setText((shopName == null || "".equals(shopName) || "无".equals(shopName)) ? "" : shopName);
                            if (mInviteNotifyDialog ==null){
                                showInviteDialog(admin,dutyAuth,isInvite);
                            }
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initShopOrder(View view) {
        tabWidget = (SnsTabWidget) view.findViewById(R.id.ordertabwidget);
        // LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < ORDER_TAB_TEXT.length; i++) {
            // 实例化tabitem
            View viewShop = inflater.inflate(R.layout.item_view_shop_tab, null);
            // 为每一个Tab按钮设置图标、文字和内容
            setTextViewStyle(viewShop, i, (i == 0));
            tabWidget.addView(viewShop);
        }
        // 添加监听
        tabWidget.setTabSelectionListener(new OrderTabSelectionListener());
        shoprecy = (RecyclerView) view.findViewById(R.id.shop_recy);
        resetRedPoint(0, 0);
        resetRedPoint(1, 0);
        resetRedPoint(2, 0);
        resetRedPoint(3, 0);
    }
    private int makerType = 0;
    private void initShopMaker() {
        // LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (makerType == 0){//无权限开通创客
            maker.setVisibility(View.GONE);
        }else {
            maker.setVisibility(View.VISIBLE);
            int childCount = makertabwidget.getChildCount();
            if (childCount>0){
                makertabwidget.removeAllViews();
            }
            if (makerType == 1) {//已开通创客
                for (int i = 0; i < MAKER_TAB_TEXT.length; i++) {
                    // 实例化tabitem
                    View viewShop = inflater.inflate(R.layout.item_view_shop_tab, null);
                    // 为每一个Tab按钮设置图标、文字和内容
                    setTextViewStyle(viewShop, i, MAKER_TAB_IMAGE,MAKER_TAB_TEXT);
                    // red number
                    BadgeView textRedpoint = (BadgeView) viewShop.findViewById(R.id.shop_tabitem_redpoint);
                    textRedpoint.setVisibility(View.GONE);
                    makertabwidget.addView(viewShop);
                }
            }else if (makerType == 2){//未开通创客,但允许
                for (int i = 0; i < MAKERDREDGE_TAB_TEXT.length; i++) {
                    // 实例化tabitem
                    View viewShop = inflater.inflate(R.layout.item_view_shop_tab, null);
                    // 为每一个Tab按钮设置图标、文字和内容
                    if (i == 0){
                        viewShop.setVisibility(View.VISIBLE);
                        setTextViewStyle(viewShop, i,MAKERDREDGE_TAB_IMAGE,MAKERDREDGE_TAB_TEXT);
                    }else {
                        viewShop.setVisibility(View.INVISIBLE);
                        setTextViewStyle(viewShop, i,MAKERDREDGE_TAB_IMAGE,MAKERDREDGE_TAB_TEXT);
                    }
                    // red number
                    BadgeView textRedpoint = (BadgeView) viewShop.findViewById(R.id.shop_tabitem_redpoint);
                    textRedpoint.setVisibility(View.GONE);
                    makertabwidget.addView(viewShop);
                }
            }
            // 添加监听
            makertabwidget.setTabSelectionListener(new MakerTabSelectionListener());
        }
    }

    protected void setTextViewStyle(View view, int index, boolean isCur) {
        // TextView
        TextView textView = (TextView) view.findViewById(R.id.shop_tabitem_text);
        // 设置文字
        textView.setText(ORDER_TAB_TEXT[index]);
        textView.setTextSize(14);
        // ImageView
        ImageView imageView = (ImageView) view.findViewById(R.id.shop_tabitem_icon);
        // 非高亮图标
        imageView.setImageResource(ORDER_TAB_IMAGE[index]);

    }

    protected void setTextViewStyle(View view, int index, int[] imageArray, int[] textArray) {
        // TextView
        TextView textView = (TextView) view.findViewById(R.id.shop_tabitem_text);
        // 设置文字
        textView.setText(textArray[index]);
        textView.setTextSize(14);
        // ImageView
        ImageView imageView = (ImageView) view.findViewById(R.id.shop_tabitem_icon);
        // 非高亮图标
        imageView.setImageResource(imageArray[index]);

    }
    /**
     * 重设红点
     *
     * @param index
     * @param number
     */
    private void resetRedPoint(int index, int number) {
        View view = tabWidget.getChildAt(index);
        // red number
        BadgeView textRedpoint = (BadgeView) view.findViewById(R.id.shop_tabitem_redpoint);
        if (index != 3) {
            if (number > 0) {
                if (String.valueOf(number).length() > 2) {
                    textRedpoint.setText("...");
                } else {
                    textRedpoint.setText(String.valueOf(number));
                }
                //隐藏红点
                textRedpoint.setVisibility(View.VISIBLE);
            } else {
                textRedpoint.setText("");
                textRedpoint.setVisibility(View.GONE);
            }
        } else {
            textRedpoint.setVisibility(View.GONE);
        }

    }

    @Override
    public void onRefresh() {
        OkHttpDemand();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = getActivity();
                handler.sendMessage(msg);
//                swipeRefreshLayout.setRefreshing(false);
            }
        };
        new Timer().schedule(timerTask, 2000);
    }

    //我的订单
    private class OrderTabSelectionListener implements SnsTabWidget.ITabSelectionListener {

        @Override
        public void onTabSelectionChanged(int tabIndex) {
            switch (tabIndex){
                case 0://待付款
                    ((MainActivity)getActivity()).startOrderActivity(1);
                    break;
                case 1://代发货
                    ((MainActivity)getActivity()).startOrderActivity(2);
                    break;
                case 2://待收货
                    ((MainActivity)getActivity()).startOrderActivity(3);
                    break;
                case 3://全部订单
                    ((MainActivity)getActivity()).startOrderActivity(0);
                    break;
            }
        }
    }

    //我的创客
    private class MakerTabSelectionListener implements SnsTabWidget.ITabSelectionListener {

        @Override
        public void onTabSelectionChanged(int tabIndex) {
            if (makerType == 2){
                switch (tabIndex){
                    case 0://开通创客
//                        startWebViewActivity(Config.MAKERDREDGE);
                        startActivity(new Intent(getActivity(), MakerCreateActivity.class));
                        break;
                }
            }else if (makerType == 1){
                switch (tabIndex){
                    case 0://推广二维码
//                        startWebViewActivity(Config.MAKERQRCODE);
                        startActivity(new Intent(getActivity(), MakerCodeActivity.class));
                        break;
                    case 1://收款账号
//                        startWebViewActivity(Config.MAKERRECEIPTACCOUNT);
                        startActivity(new Intent(getActivity(), MakerShroffAccountActivity.class));
                        break;
                    case 2://开拓门店
//                        startWebViewActivity(Config.MAKEREXPLOITSHOP);
                        startActivity(new Intent(getActivity(), MakerExploitShopActivity.class));
                        break;
                    case 3://门店订单
//                        startWebViewActivity(Config.MAKERSHOPORDER);
                        startActivity(new Intent(getActivity(), MakerShopOrderActivity.class));
                        break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void onCancelResult() {
        rlMainError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        rlMainError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        rlMainError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishResult() {
        rlMainError.setVisibility(View.VISIBLE);
    }
    //点击事件
    @Override
    public void OnItemClick(View view, int position) {
        if (lv_id>5){
            switch (position){
                case 0://我的积分
                    ((MainActivity)getActivity()).startIntegralActivity();
                    break;
                case 1://收货地址
//                    startWebViewActivity(Config.SHOPPLACEOFRECEIPT);
                    startActivity(new Intent(getActivity(), AddressActivity.class));
                    break;
//                case 2://雅森帮
//                    ((MainActivity)getActivity()).startYasnActivity(digital_member);
//                    break;
                case 2://帮助中心
//                    startWebViewActivity(Config.SHOPHELP);
                    ((MainActivity)getActivity()).startHelpActivity();
                    break;
                case 3://电话咨询
//                    startWebViewActivity(Config.SHOPPHONE);
                    ((MainActivity)getActivity()).startShopPhoneActivity();
                    break;
                case 4://在线客服
                    SobotUtil.startSobot(getActivity(),null);
                    break;
                case 5://专票资质
//                    startWebViewActivity(Config.SHOPAPTITUDE);
                    ((MainActivity)getActivity()).startInvoiceSpecialActivity();
                    break;
                case 6://用油查询
                    ((MainActivity)getActivity()).startOilActivity();
                    break;
            }
        }else {
            switch (position){
                case 0://我的积分
                    ((MainActivity)getActivity()).startIntegralActivity();
                    break;
                case 1://帮助中心
//                    startWebViewActivity(Config.SHOPHELP);
                    ((MainActivity)getActivity()).startHelpActivity();
                    break;
                case 2://电话咨询
//                    startWebViewActivity(Config.SHOPPHONE);
                    ((MainActivity)getActivity()).startShopPhoneActivity();
                    break;
                case 3://在线客服
                    SobotUtil.startSobot(getActivity(),null);
                    break;
                case 4://专票资质
//                    startWebViewActivity(Config.SHOPAPTITUDE);
                    ((MainActivity)getActivity()).startInvoiceSpecialActivity();
                    break;
                case 5://用油查询
                    ((MainActivity)getActivity()).startOilActivity();
                    break;
            }
        }

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
    //认证弹窗
    protected AlertDialog mAuthNotifyDialog;

    private void showAuthDialog(String reasonString) {
        if (mAuthNotifyDialog !=null && mAuthNotifyDialog.isShowing()){
            return;
        }
        LayoutInflater factor = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_reason, null);
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
//                startWebViewActivity(Config.ATTESTATION);
                startActivity(new Intent(getActivity(),AuthorActivity.class));
                mAuthNotifyDialog.dismiss();
            }
        });
        Activity activity = getActivity();
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        mAuthNotifyDialog = builder.create();
        mAuthNotifyDialog.show();
        mAuthNotifyDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }
    //邀请弹窗
    protected AlertDialog mInviteNotifyDialog;
    /**
     * 1未注册用户被邀请 2已注册用户被邀请
     */
    private int isInvite = 0;
    private void showInviteDialog(String admin,String dutyAuth,int isInvite) {
        if (mInviteNotifyDialog !=null && mInviteNotifyDialog.isShowing()){
            return;
        }
        this.isInvite = isInvite;
        LayoutInflater factor = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_invite, null);
        TextView tvInvitehint = (TextView) serviceView.findViewById(R.id.tv_invitehint);
        String invitehint = "";
        if (isInvite == 1){//新用户
            invitehint = getActivity().getResources().getString(R.string.newinvitehint);
        }else if (isInvite == 2){
            invitehint = getActivity().getResources().getString(R.string.oldinvitehint);
        }
        invitehint = String.format(invitehint, admin,dutyAuth);
        SpannableStringBuilder span = new SpannableStringBuilder(invitehint);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),R.color.orange)), 6,6+admin.length() ,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),R.color.orange)), 27+admin.length(),27+admin.length()+dutyAuth.length() ,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvInvitehint.setText(span);
//        Log.e("TAG_邀请","tvInvitehint="+tvInvitehint.getText().toString());
        TextView agree = (TextView) serviceView.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInviteNotifyDialog.dismiss();
                Map<String, Object> paramsShop = new HashMap<String, Object>();
                if (token != null && !"".equals(token)) {
                    paramsShop.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    paramsShop.put("access_token", resetToken);
                }
                okHttpGet(103, Config.AGREEINVITE, paramsShop);
            }
        });
        TextView refuse = (TextView) serviceView.findViewById(R.id.refuse);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInviteNotifyDialog.dismiss();
                Map<String, Object> paramsShop = new HashMap<String, Object>();
                if (token != null && !"".equals(token)) {
                    paramsShop.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    paramsShop.put("access_token", resetToken);
                }
                okHttpGet(104, Config.REFUSEINVITE, paramsShop);
            }
        });
        Activity activity = getActivity();
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        mInviteNotifyDialog = builder.create();
        mInviteNotifyDialog.setCancelable(false);
        mInviteNotifyDialog.setCanceledOnTouchOutside(false);
        mInviteNotifyDialog.show();
        mInviteNotifyDialog.setContentView(serviceView);
//        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
//        serviceView.setLayoutParams(layout);
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
        Log.e("TAG_onEventMain","SHOP="+msg);
        if ("loginSucceed".equals(msg)&&getUserVisibleHint()) {
            OkHttpDemand();
        }else if ("refreshorder".equals(msg)) {
            OkHttpDemand();
        }

        else if ("paySucceed".equals(msg)){
            OkHttpDemand();
        }
    }
}
