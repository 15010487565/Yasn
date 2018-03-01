package com.yasn.purchase.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;
import com.yasn.purchase.R;
import com.yasn.purchase.func.GoodsDetailsTopBtnFunc;
import com.yasn.purchase.goods.adapter.ItemTitlePagerAdapter;
import com.yasn.purchase.goods.fragment.GoodsCommentFragment;
import com.yasn.purchase.goods.fragment.GoodsDetailFragment;
import com.yasn.purchase.goods.fragment.GoodsInfoFragment;
import com.yasn.purchase.goods.view.NoScrollViewPager;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.BadgeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;


public class GoodsDetailsActivity extends SimpleTopbarActivity implements GoodsInfoFragment.CallBack{

    PagerSlidingTabStrip titleTabs;
    LinearLayout title;
    RelativeLayout titleGoods,titleSell,titleSucceed;
    NoScrollViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment goodsInfoFragment = new GoodsInfoFragment();
    private GoodsDetailFragment goodsDetailFragment = new GoodsDetailFragment();
    private GoodsCommentFragment goodsCommentFragment = new GoodsCommentFragment();
    private String[] titles = new String[]{"商品详情", "教你卖好", "成功案例"};
    /**
     * 初始化分享弹出框
     */
    Dialog mShareDialog;
    TextView share_wexin, share_wexinfriends, share_qq, share_qzone;
    private ShareEntity testBean;
    //进货单数量
    private BadgeView main_tabitem_redpoint;
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
        titleTabs = (PagerSlidingTabStrip) findViewById(R.id.title_tabs);
        title = (LinearLayout) findViewById(R.id.title);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        main_tabitem_redpoint = (BadgeView) findViewById(R.id.main_tabitem_redpoint);
        init();
        initTabLayout();
    }

    private void initTabLayout() {
        titleGoods = (RelativeLayout) findViewById(R.id.titleGoods);
        titleGoods.setOnClickListener(this);
        titleSell = (RelativeLayout) findViewById(R.id.titleSell);
        titleSell.setOnClickListener(this);
        titleSucceed = (RelativeLayout) findViewById(R.id.titleSucceed);
        titleSucceed.setOnClickListener(this);
    }

    private void init() {
        fragmentList.add(goodsInfoFragment);
        fragmentList.add(goodsDetailFragment);
        fragmentList.add(goodsCommentFragment);
        viewPager.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, titles));
        viewPager.setOffscreenPageLimit(3);
        titleTabs.setViewPager(viewPager);
        goodsInfoFragment.setsetCartNum(this);
        int goodsfragmentid = SharePrefHelper.getInstance(this).getSpInt("GOODSFRAGMENTID");
        if (goodsfragmentid == 1){
            viewPager.setCurrentItem(1);
        }else if (goodsfragmentid == 2){
            viewPager.setCurrentItem(2);
        }else {
            viewPager.setCurrentItem(0);
        }
    }
    public void operaTitleBar(boolean scroAble, boolean titleVisiable, boolean tanVisiable) {
        viewPager.setNoScroll(false);
        title.setVisibility(titleVisiable? View.VISIBLE:View.GONE);
        Log.e("TAG_tanVisiable","title="+title.getVisibility());
        titleTabs.setVisibility(tanVisiable? View.VISIBLE:View.GONE);
        Log.e("TAG_tanVisiable","titleTabs="+titleTabs.getVisibility());
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
            case R.id.titleGoods:
                goodsInfoFragment.activityChangeFragment();
                title.setAlpha(0);
                titleTabs.setAlpha(1);
                ToastUtil.showToast("点击了商品详情");
                viewPager.setCurrentItem(0,true);
                viewPager.setCurrentItem(1,false);
                viewPager.setCurrentItem(2,false);
                break;
            case R.id.titleSell:
                title.setVisibility(View.GONE);
                titleTabs.setVisibility(View.VISIBLE);
                ToastUtil.showToast("点击了教你卖好");
                titleTabs.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0,false);
                viewPager.setCurrentItem(1,true);
                viewPager.setCurrentItem(2,false);
                break;
            case R.id.titleSucceed:
                title.setVisibility(View.GONE);
                titleTabs.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0,false);
                viewPager.setCurrentItem(1,false);
                viewPager.setCurrentItem(2,true);
                ToastUtil.showToast("点击了成功案例");
                break;
        }
        if (mShareDialog != null && mShareDialog.isShowing()) {
            mShareDialog.dismiss();
        }
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
        testBean = new ShareEntity("测试商品", "测试商品===");
        testBean.setUrl("http://www.baidu.com"); //分享链接
        testBean.setImgUrl("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=0&spn=0&di=53059380110&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=49366202%2C632101467&os=3303681949%2C3335890514&simid=3365052636%2C338127663&adpicid=0&lpn=0&ln=1984&fr=&fmq=1516087811793_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic4.nipic.com%2F20091217%2F3885730_124701000519_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3FnAzdH3F8alAzdH3Fnlwnldjwlwuw8j0k_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0");
        if (dialogIsActivity()) {
            initShareDialog();
        }
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
    public void setCartNum(int cartNum) {
        Log.e("TAG_cartNum", "cartNum=" + cartNum);
        if (cartNum == 0){
            main_tabitem_redpoint.setVisibility(View.GONE);
        }else {
            main_tabitem_redpoint.setVisibility(View.VISIBLE);
            main_tabitem_redpoint.setText(cartNum);
        }
    }
}
