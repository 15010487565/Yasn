package com.yasn.purchasetest.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gxz.PagerSlidingTabStrip;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;
import com.yasn.purchasetest.R;
import com.yasn.purchasetest.activityold.WebViewActivity;
import com.yasn.purchasetest.common.Config;
import com.yasn.purchasetest.func.GoodsDetailsTopBtnFunc;
import com.yasn.purchasetest.goods.adapter.ItemTitlePagerAdapter;
import com.yasn.purchasetest.goods.fragment.GoodsCommentFragment;
import com.yasn.purchasetest.goods.fragment.GoodsDetailFragment;
import com.yasn.purchasetest.goods.fragment.GoodsInfoFragment;
import com.yasn.purchasetest.help.SobotUtil;
import com.yasn.purchasetest.model.EventBusMsg;
import com.yasn.purchasetest.model.GoodsDetailsOtherModel;
import com.yasn.purchasetest.model.SobotModel;
import com.yasn.purchasetest.utils.ToastUtil;

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

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.view.BadgeView;
import www.xcd.com.mylibrary.view.NoScrollViewPager;


public class GoodsDetailsActivity extends SimpleTopbarActivity implements GoodsInfoFragment.CallBack {

    PagerSlidingTabStrip titleTabs;
    LinearLayout llTitle;
    RelativeLayout titleGoods, titleSell, titleSucceed;
    NoScrollViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment goodsInfoFragment = new GoodsInfoFragment();
    private GoodsDetailFragment goodsDetailFragment = new GoodsDetailFragment();
    private GoodsCommentFragment goodsCommentFragment = new GoodsCommentFragment();
    private String[] titlesNSucceed = new String[]{"商品详情", "教你卖好"};
    private String[] titlesYSucceed = new String[]{"商品详情", "教你卖好", "成功案例"};
    /**
     * 初始化分享弹出框
     */
    Dialog mShareDialog;
    TextView share_wexin, share_wexinfriends, share_qq, share_qzone;
    private ShareEntity testBean;
    //进货单数量
    private BadgeView main_tabitem_redpoint;
    RelativeLayout tvUndata;
    //收藏图片、文字
    private LinearLayout llCollect;
    private ImageView ivCollect;
    private TextView tvCollect;
    private LinearLayout llCallPhone;
    private LinearLayout llService;
    //进货单
    private FrameLayout flShopCar;
    //加入进货单
    private TextView tvAddShopCar;
    private String goodsId;
    /**
     * Topbar功能列表
     */
    private static Class<?> rightFuncArray[] = {GoodsDetailsTopBtnFunc.class};

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetails);
        EventBus.getDefault().register(this);
        token = SharePrefHelper.getInstance(this).getSpString("token");
        resetToken = SharePrefHelper.getInstance(this).getSpString("resetToken");
        goodsId = SharePrefHelper.getInstance(this).getSpString("GOODSID");
        titleTabs = (PagerSlidingTabStrip) findViewById(R.id.title_tabs);
        llTitle = (LinearLayout) findViewById(R.id.ll_title);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        tvUndata = (RelativeLayout) findViewById(R.id.tv_undata);
        tvUndata.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setScroll(true);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("TAG_详情", "viewPager=" + viewPager.isScroll());
                return false;
            }
        });
        main_tabitem_redpoint = (BadgeView) findViewById(R.id.main_tabitem_redpoint);
        main_tabitem_redpoint.setVisibility(View.GONE);
        initTabLayout();
        initBottomView();

    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        String goodsid = SharePrefHelper.getInstance(this).getSpString("GOODSID");
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(103, Config.GOODSDETAILSOTHER+ goodsid, params);
    }

    /**
     * loginState 状态：0：已登陆
     */
    private String loginState;

    private void initBottomView() {
        //收藏
        llCollect = (LinearLayout) findViewById(R.id.ll_collect);
        llCollect.setOnClickListener(this);
        ivCollect = (ImageView) findViewById(R.id.iv_collect);
        tvCollect = (TextView) findViewById(R.id.tv_collect);
        //进货单
        flShopCar = (FrameLayout) findViewById(R.id.fl_shopCar);
        flShopCar.setOnClickListener(this);
        //电话咨询
        llCallPhone = (LinearLayout) findViewById(R.id.ll_callPhone);
        llCallPhone.setOnClickListener(this);
        //在线客服
        llService = (LinearLayout) findViewById(R.id.ll_service);
        llService.setOnClickListener(this);
        //加入进货单
        tvAddShopCar = (TextView) findViewById(R.id.tv_addShopCar);
        tvAddShopCar.setOnClickListener(this);
        loginState = SharePrefHelper.getInstance(this).getSpString("loginState");
        if ("0".equals(loginState)) {
            llCollect.setVisibility(View.VISIBLE);
            flShopCar.setVisibility(View.VISIBLE);
            llCallPhone.setVisibility(View.VISIBLE);
            llService.setVisibility(View.VISIBLE);
            tvAddShopCar.setText("加入进货单");
        } else if ("登录看价格".equals(loginState)) {
            llCollect.setVisibility(View.GONE);
            flShopCar.setVisibility(View.GONE);
            llCallPhone.setVisibility(View.VISIBLE);
            llService.setVisibility(View.VISIBLE);
            tvAddShopCar.setText("去登录");
        } else {
            llCollect.setVisibility(View.VISIBLE);
            flShopCar.setVisibility(View.GONE);
            llCallPhone.setVisibility(View.VISIBLE);
            llService.setVisibility(View.VISIBLE);
            tvAddShopCar.setText("去认证");
        }
    }

    private boolean isCollect;

    public void setCollectImage(boolean isCollect) {
        this.isCollect = isCollect;
        if (isCollect) {//已收藏
            ivCollect.setBackgroundResource(R.mipmap.collect_orange_entity);
            tvCollect.setText("已收藏");
        } else {
            ivCollect.setBackgroundResource(R.mipmap.collect_orange);
            tvCollect.setText("收藏");
        }
    }

    private void initTabLayout() {
        titleGoods = (RelativeLayout) findViewById(R.id.titleGoods);
        titleGoods.setOnClickListener(this);
        titleSell = (RelativeLayout) findViewById(R.id.titleSell);
        titleSell.setOnClickListener(this);
        titleSucceed = (RelativeLayout) findViewById(R.id.titleSucceed);
        titleSucceed.setOnClickListener(this);
    }

    /**
     * isSuccessCase == 0存在成功案例
     * @param isSuccessCase
     */
    private void initSucceed(int isSuccessCase) {
        fragmentList.add(goodsInfoFragment);
        fragmentList.add(goodsDetailFragment);
        if (isSuccessCase == 1){
            fragmentList.add(goodsCommentFragment);
            viewPager.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                        fragmentList, titlesYSucceed));
            titleSucceed.setVisibility(View.VISIBLE);
        }else {
            viewPager.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                    fragmentList, titlesNSucceed));
            titleSucceed.setVisibility(View.GONE);
        }

        viewPager.setOffscreenPageLimit(3);
        titleTabs.setViewPager(viewPager);
        goodsInfoFragment.setsetCartNum(this);
//        int goodsfragmentid = SharePrefHelper.getInstance(this).getSpInt("GOODSFRAGMENTID");
//        if (goodsfragmentid == 1) {
//            viewPager.setCurrentItem(1);
//        } else if (goodsfragmentid == 2) {
//            viewPager.setCurrentItem(2);
//        } else {
            viewPager.setCurrentItem(0);
//        }
    }

    public void operaTitleBar(boolean scroAble, boolean titleVisiable, boolean tanVisiable) {
        if (titleVisiable) {
            viewPager.setScroll(false);
        } else {
            viewPager.setScroll(true);
        }
        llTitle.setVisibility(titleVisiable ? View.VISIBLE : View.GONE);
        titleTabs.setVisibility(tanVisiable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.share_qq:
                ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_QQ, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_qzone:
                ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_QZONE, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_wexin:
                ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_wexinfriends:
                ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE, testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.titleGoods://商品详情
                goodsInfoFragment.activityChangeFragment();
                llTitle.setVisibility(View.GONE);
                titleTabs.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0, false);
                viewPager.setScroll(true);
                break;
            case R.id.titleSell://教你卖号
                llTitle.setVisibility(View.GONE);
                titleTabs.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(1, false);
                viewPager.setScroll(true);
                break;
            case R.id.titleSucceed://成功案例
                llTitle.setVisibility(View.GONE);
                titleTabs.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(2, false);
                viewPager.setScroll(true);
                break;
            case R.id.ll_collect://收藏
                collectRequest();
                break;
            case R.id.ll_callPhone:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ToastUtil.showToast("没有拨打电话权限");
                    } else {
                        Intent telIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4009973315"));
                        telIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(telIntent);
                    }
                } else {
                    Intent telIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4009973315"));
                    telIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(telIntent);
                }
                break;
            case R.id.ll_service:
                SobotUtil.startSobot(this, getSobotModel());
                break;
            case R.id.fl_shopCar:
//                startWebViewActivity(Config.SHOPPCARWEBVIEW);
                if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
                    startActivity(new Intent(GoodsDetailsActivity.this, ShopCarActivity.class));
                } else {
                    startWebViewActivity(Config.LOGINWEBVIEW);
                }
                break;
            case R.id.tv_addShopCar:
                String trim = tvAddShopCar.getText().toString().trim();
                Log.e("TAG_详情提交", "trim=" + trim);
                if ("去登录".equals(trim)) {
                    startWebViewActivity(Config.LOGINWEBVIEW);
                } else if ("去认证".equals(trim)) {
                    startWebViewActivity(Config.ATTESTATION);
                } else if ("加入进货单".equals(trim)) {
                    /**
                     * productId 货品Id
                     * num 数量
                     * activityId 促销活动Id(没有就不传)
                     */
                    addShopCarRequest();
                }
                break;
        }
        if (mShareDialog != null && mShareDialog.isShowing()) {
            mShareDialog.dismiss();
        }
    }

    private void addShopCarRequest() {
        int productId = goodsInfoFragment.getProductId();
        Log.e("TAG_详情加入规格", "productId=" + productId);
        if (productId == 0) {
            ToastUtil.showToast("请选择规格！");
            return;
        }

        String goodsNum = goodsInfoFragment.getGoodsNum();
        if (goodsNum == null || "".equals(goodsNum)) {
            goodsNum = "0";
        }
        Integer integerGoodsNum = Integer.valueOf(goodsNum);
        int smallSale = goodsInfoFragment.getSmallSale();
        int step = goodsInfoFragment.getStep();
//        Log.e("TAG_activity", "加入进货单goodsNum=" + integerGoodsNum + ";smallSale=" + smallSale + "；step=" + step);
        if (integerGoodsNum < smallSale) {
            ToastUtil.showToast("最小起订量为" + smallSale + "件，请输入正确数量！");
            return;
        }
        if (smallSale > 0) {
            int remainder = (integerGoodsNum - smallSale) % step;
//            Log.e("TAG_activity", "加入进货单remainder=" + remainder);
            if (remainder > 0) {
                ToastUtil.showToast("最小起订量为" + smallSale + "件，每次加减不得少于"+step+"件，请输入正确数量！");
                return;
            }
        }
        if (integerGoodsNum == 0) {
            ToastUtil.showToast("最小起订量为" + smallSale + "件，请输入正确数量！");
            return;
        }

        int activityId = goodsInfoFragment.getActivityId();
        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put("productId", String.valueOf(productId));
        params1.put("num", goodsNum);
        if (activityId != 0) {
            params1.put("activityId", String.valueOf(activityId));
        }
        if (token != null && !"".equals(token)) {
            params1.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params1.put("access_token", resetToken);
        }
        okHttpGet(102, Config.ADDSHOPCAR, params1);
    }

    private void collectRequest() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        if (isCollect) {//已收藏
            okHttpGet(101, Config.DELETECOLLECT + goodsId, params);
        } else {
            okHttpGet(100, Config.ADDCOLLECT + goodsId, params);
        }
    }

    private void startWebViewActivity(String url) {

//        if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
//            startActivity(new Intent(GoodsDetailsActivity.this, ShopCarActivity.class));
//        } else {
        Intent intent = new Intent(GoodsDetailsActivity.this, WebViewActivity.class);
        intent.putExtra("webViewUrl", url);
        startActivity(intent);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 分享回调处理
         */
        if (requestCode == ShareConstant.REQUEST_CODE) {
            if (data != null) {
                int channel = data.getIntExtra(ShareConstant.EXTRA_SHARE_CHANNEL, -1);
                int status = data.getIntExtra(ShareConstant.EXTRA_SHARE_STATUS, -1);
                switch (status) {
                    /** 成功 **/
                    case ShareConstant.SHARE_STATUS_COMPLETE:

                        break;
                    /** 失败 **/
                    case ShareConstant.SHARE_STATUS_FAILED:
                        if (dialogIsActivity()) {
                            initShareDialog();
                        }
                        break;
                    /** 取消 **/
                    case ShareConstant.SHARE_STATUS_CANCEL:
                        if (dialogIsActivity()) {
                            initShareDialog();
                        }
                        break;
                }
            }
        }

    }

    public void onClickShare() {
        if (dialogIsActivity()) {
            initShareDialog();
        }
    }

    public void tempSaveFragmentShare(String title, String url, String imageUrl) {
        testBean = new ShareEntity(title, "我在车品宝发现了一个不错的商品，赶快来看看吧~");
        testBean.setUrl(url); //分享链接
        testBean.setImgUrl(imageUrl);
    }

    public void initShareDialog() {
        mShareDialog = new Dialog(this, R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true);
        mShareDialog.setCancelable(true);
        Window window = mShareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);
        View view = View.inflate(this, R.layout.lay_share, null);
        share_wexin = (TextView) view.findViewById(R.id.share_wexin);
        share_wexin.setOnClickListener(this);
        share_wexinfriends = (TextView) view.findViewById(R.id.share_wexinfriends);
        share_wexinfriends.setOnClickListener(this);
        share_qq = (TextView) view.findViewById(R.id.share_qq);
        share_qq.setOnClickListener(this);
        share_qzone = (TextView) view.findViewById(R.id.share_qzone);
        share_qzone.setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        mShareDialog.show();
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    setCollectImage(true);
                } else if (returnCode == 401) {
                    cleanToken();
                    collectRequest();
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101:
                if (returnCode == 200) {
                    setCollectImage(false);
                } else if (returnCode == 401) {
                    cleanToken();
                    collectRequest();
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 102:
                if (returnCode == 200) {
                    try {
                        JSONObject object = new JSONObject(returnData);
                        int code = object.optInt("code");
                        if (code == 200) {
                            setCartNum(object.optInt("number"));
                            ToastUtil.showToast(object.optString("message"));
                        } else {
                            setCartNum(object.optInt("number"));
                            ToastUtil.showToast(object.optString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (returnCode == 401) {
                    cleanToken();
                    collectRequest();
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 103:
                if (returnCode == 200) {
                    GoodsDetailsOtherModel goodsdetailsothermodel = JSON.parseObject(returnData, GoodsDetailsOtherModel.class);
                    GoodsDetailsOtherModel.GoodsIntroBean goodsIntro = goodsdetailsothermodel.getGoodsIntro();
                    int isSuccessCase = goodsIntro.getIsSuccessCase();
                    initSucceed(isSuccessCase);
                } else {
                    initSucceed(0);
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }
    }

    @Override
    public void onCancelResult() {
        initSucceed(0);
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        initSucceed(0);
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        initSucceed(0);
    }

    @Override
    public void onFinishResult() {
        initSucceed(0);
    }

    @Override
    public void setCartNum(int cartNum) {
        Log.e("TAG_cartNum", "cartNum=" + cartNum);
        EventBusMsg carNum = new EventBusMsg("carNum");
        carNum.setCarNum(String.valueOf(cartNum));
        EventBus.getDefault().post(carNum);
        if (cartNum == 0) {
            main_tabitem_redpoint.setVisibility(View.GONE);
        } else {
            main_tabitem_redpoint.setVisibility(View.VISIBLE);
            main_tabitem_redpoint.setText(String.valueOf(cartNum));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMsg event) {
        String msg = event.getMsg();
        Log.e("TAG_详情页", "msg=" + msg);
       if ("carNum".equals(msg)) {
           Integer cartNum = Integer.valueOf(event.getCarNum());
//           Log.e("TAG_详情页", "cartNum=" + cartNum);
           if (cartNum == 0) {
               main_tabitem_redpoint.setVisibility(View.GONE);
           } else {
               main_tabitem_redpoint.setVisibility(View.VISIBLE);
               main_tabitem_redpoint.setText(String.valueOf(cartNum));
           }
        }
    }

    public void setViewPagerGone() {
        viewPager.setVisibility(View.GONE);
        tvUndata.setVisibility(View.VISIBLE);
    }

    /**
     * loginState 0 登录状态
     * @param isOnclick 庫存是否大於0
     * @param isBeforeSale 是否預售 , 0否1是
     */
    public void setTvAddShopCar(boolean isOnclick, boolean isBeforeSale) {
//        Log.e("TAG_加入进货单", "isBeforeSale=" + isBeforeSale);
        if ("0".equals(loginState)) {
            if (isOnclick) {//库存大于0
                tvAddShopCar.setEnabled(true);
                tvAddShopCar.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            } else {
                if (isBeforeSale) {//预售
                    tvAddShopCar.setEnabled(true);
                    tvAddShopCar.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
                } else {
                    tvAddShopCar.setEnabled(false);
                    tvAddShopCar.setBackgroundColor(ContextCompat.getColor(this, R.color.black_99));
                }
            }
        } else {
            tvAddShopCar.setEnabled(true);
            tvAddShopCar.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        }
    }

    public SobotModel sobotModel;

    public SobotModel getSobotModel() {
        return sobotModel;
    }

    public void setSobotModel(SobotModel sobotModel) {
        this.sobotModel = sobotModel;
    }

}
