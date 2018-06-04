package com.yasn.purchase.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.yasn.purchase.R;
import com.yasn.purchase.goods.bean.RecommendGoodsBean;

import java.util.List;


/**
 * item页底部的推荐商品适配器
 */
public class ItemRecommendAdapter implements Holder<List<RecommendGoodsBean>> {

    GridView gridView;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, null);
        gridView = (GridView)view.findViewById(R.id.recommend_gridView);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, List<RecommendGoodsBean> data) {
        gridView.setAdapter(new ItemRecommendGoodsAdapter(context, data));
    }
}
