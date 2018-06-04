package com.yasn.purchase.goods.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.GoodsDetailsOtherModel;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.utils.SharePrefHelper;


/**
 * 教你卖好Fragment
 */
public class GoodsDetailFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout undata;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private WebView webView;
    private TextView tvGoodsError;

    protected void OkHttpDemand() {
        String goodsid = SharePrefHelper.getInstance(getActivity()).getSpString("GOODSID");
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(100, Config.GOODSDETAILSOTHER + goodsid, params);
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
        }else {
            isVisible = false;
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        RelativeLayout topbat_parent = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);

        undata = (RelativeLayout) view.findViewById(R.id.undata);
        undata.setVisibility(View.GONE);
//        htmlTextView.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
        //XXX初始化view的各控件
        webView = (WebView) view.findViewById(R.id.webView);
        tvGoodsError = (TextView) view.findViewById(R.id.tv_GoodsError);
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
                    String intro = goodsIntro.getIntro();
                    if (intro == null||"".equals(intro)){
                        undata.setVisibility(View.VISIBLE);
                        tvGoodsError.setText("亲，未获取到教你卖好数据~");
                    }else {
//                        HtmlImageGetter htmlImageGetter = new HtmlImageGetter(getActivity(),htmlTextView);
//                        Spanned spanned = Html.fromHtml(intro, htmlImageGetter, null);
//                        htmlTextView.setText(spanned);
                        undata.setVisibility(View.GONE);
                        getHtmlData(intro,webView);
                        Log.e("TAG_intro","intro="+intro);
                    }
                } else {
                    undata.setVisibility(View.VISIBLE);
                    tvGoodsError.setText("亲，未获取到教你卖好数据~");
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
