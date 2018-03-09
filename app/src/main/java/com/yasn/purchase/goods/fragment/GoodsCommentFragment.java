package com.yasn.purchase.goods.fragment;

import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.GoodsDetailsOtherModel;
import com.yasn.purchase.utils.HtmlImageGetter;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.utils.SharePrefHelper;


/**
 * item页ViewPager里的评价Fragment
 */
public class GoodsCommentFragment extends BaseFragment {

    private TextView htmlTextView,undata;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    protected void OkHttpDemand() {
        String goodsid = SharePrefHelper.getInstance(getActivity()).getSpString("GOODSID");
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(100, Config.GOODSDETAILSOTHER+ goodsid, params);
    }
    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        OkHttpDemand();
    }

    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods_comment;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        RelativeLayout topbat_parent = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);

        undata = (TextView) view.findViewById(R.id.undata);
        htmlTextView = (TextView) view.findViewById(R.id.htmlText);
        htmlTextView.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动

        createDialog();
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    GoodsDetailsOtherModel goodsdetailsothermodel = JSON.parseObject(returnData, GoodsDetailsOtherModel.class);
                    GoodsDetailsOtherModel.GoodsIntroBean goodsIntro = goodsdetailsothermodel.getGoodsIntro();
                    int isSuccessCase = goodsIntro.getIsSuccessCase();
                    if (isSuccessCase ==1){
                        String successCase = goodsIntro.getSuccessCase();
                        HtmlImageGetter htmlImageGetter = new HtmlImageGetter(getActivity(),htmlTextView);
                        Spanned spanned = Html.fromHtml(successCase, htmlImageGetter, null);
                        htmlTextView.setText(spanned);
                        undata.setVisibility(View.GONE);
                    }else {
                        undata.setVisibility(View.VISIBLE);
                    }
                } else {
                    undata.setVisibility(View.VISIBLE);
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }
    }

    @Override
    public void onCancelResult() {
        undata.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        undata.setVisibility(View.VISIBLE);
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        undata.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishResult() {
        undata.setVisibility(View.VISIBLE);
    }
}
