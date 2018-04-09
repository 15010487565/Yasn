package com.yasn.purchase.func;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.GoodsDetailsActivity;
import com.yasn.purchase.activity.MainActivityNew;
import com.yasn.purchase.activity.ShopCarActivity;
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.common.Config;

import www.xcd.com.mylibrary.action.QuickAction;
import www.xcd.com.mylibrary.entity.BaseActionItem;
import www.xcd.com.mylibrary.func.BaseTopImageBtnFunc;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

public class GoodsDetailsTopBtnFunc extends BaseTopImageBtnFunc  {

    public GoodsDetailsTopBtnFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.main_topbar_add;
    }

    @Override
    public int getFuncIcon() {
        return R.mipmap.goodsdetailsrighttop;
    }

    @Override
    public void onclick(View v) {
        showCreateMultiChatActionBar(v);
    }

    /**
     * 创建群聊/会议的响应事件
     *
     * @param view
     */
    public void showCreateMultiChatActionBar(View view) {
        QuickAction quickAction = new QuickAction(getActivity(), QuickAction.VERTICAL);
        quickAction.addActionItem(new BaseActionItem(0, getActivity().getString(R.string.home),
                ContextCompat.getDrawable(getActivity(), R.mipmap.goods_home)));
        quickAction.addActionItem(new BaseActionItem(1, getActivity().getString(R.string.classify),
                ContextCompat.getDrawable(getActivity(), R.mipmap.goods_classify)));
        quickAction.addActionItem(new BaseActionItem(2, getActivity().getString(R.string.order),
                ContextCompat.getDrawable(getActivity(), R.mipmap.goods_order)));
        quickAction.addActionItem(new BaseActionItem(3, getActivity().getString(R.string.vipcenter),
                ContextCompat.getDrawable(getActivity(), R.mipmap.goods_shop)));
        quickAction.addActionItem(new BaseActionItem(4, getActivity().getString(R.string.share),
                ContextCompat.getDrawable(getActivity(), R.mipmap.goods_share)));
//
//		if (IMConfigConstant.DEFAULT_SUPPORT_SEARCH_PUBACCOUNT){
//			quickAction.addActionItem(new BaseActionItem(3, getActivity().getString(R.string.search_pub_account), getActivity()
//					.getResources().getDrawable(R.drawable.topright04_2x)));
//		}
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {

            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                String token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
                String resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
                Intent intent;
                switch (actionId) {
                    case 0:
                        intent = new Intent(getActivity(), MainActivityNew.class);
                        intent.putExtra("CURRENTITEM", 0);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                        break;

                    case 1:
                        intent = new Intent(getActivity(), MainActivityNew.class);
                        intent.putExtra("CURRENTITEM", 1);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                        break;

                    case 2://进货单
//                        intent = new Intent(getActivity(), MainActivityNew.class);
//                        intent.putExtra("CURRENTITEM", 3);
//                        getActivity().startActivity(intent);
//                        getActivity().finish();
                        if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
//                            startWebViewActivity(Config.SHOPPCARWEBVIEW);
                            getActivity().startActivity(new Intent(getActivity(), ShopCarActivity.class));
                        } else {
                            startWebViewActivity(Config.LOGINWEBVIEW);
                        }
                        break;

                    case 3://门店
                        if ((token != null && !"".equals(token)) || (resetToken != null && !"".equals(resetToken))) {
                            intent = new Intent(getActivity(), MainActivityNew.class);
                            intent.putExtra("CURRENTITEM", 4);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        } else {
                            startWebViewActivity(Config.LOGINWEBVIEW);
                        }
                        break;

                    case 4:
                        ((GoodsDetailsActivity)getActivity()).onClickShare();
                        break;

                    default:
                        break;
                }
            }
        });
        quickAction.show(view);
        quickAction.setAnimStyle(QuickAction.ANIM_AUTO);
    }
    private void startWebViewActivity(String url){
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("webViewUrl", url);
        getActivity().startActivity(intent);
    }
}
