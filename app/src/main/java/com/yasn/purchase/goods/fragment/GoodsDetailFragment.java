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
 * item页ViewPager里的教你卖好Fragment
 */
public class GoodsDetailFragment extends BaseFragment implements View.OnClickListener{

    private TextView htmlTextView,undata;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        RelativeLayout topbat_parent = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);

        undata = (TextView) view.findViewById(R.id.undata);
        htmlTextView = (TextView) view.findViewById(R.id.htmlText);
        htmlTextView.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
//        htmlTextView.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        String goodsid = SharePrefHelper.getInstance(getActivity()).getSpString("GOODSID");
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(100, Config.GOODSDETAILSOTHER + "/" + goodsid, params);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    HtmlImageGetter htmlImageGetter = new HtmlImageGetter(getActivity(),htmlTextView);
                    GoodsDetailsOtherModel goodsdetailsothermodel = JSON.parseObject(returnData, GoodsDetailsOtherModel.class);
                    GoodsDetailsOtherModel.GoodsIntroBean goodsIntro = goodsdetailsothermodel.getGoodsIntro();
                    String intro = goodsIntro.getIntro();
                    if (intro == null||"".equals(intro)){
                        Spanned spanned = Html.fromHtml(intro, htmlImageGetter, null);
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
