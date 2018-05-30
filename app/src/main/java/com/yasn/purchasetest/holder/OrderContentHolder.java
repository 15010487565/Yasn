package com.yasn.purchasetest.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yasn.purchasetest.R;
import com.yasn.purchasetest.view.TagsLayout;

/**
 * Created by gs on 2018/5/28.
 */

public class OrderContentHolder extends RecyclerView.ViewHolder {

    public ImageView ivOrderContent;
    public TextView tvOrderContentName;
    public TextView tvOrderContentNum;
    public TagsLayout tlOrderDetails;

    public OrderContentHolder(View view) {
        super(view);
        ivOrderContent = (ImageView) view.findViewById(R.id.iv_orderContent);
        tvOrderContentName = (TextView) view.findViewById(R.id.tv_orderContentName);
        tvOrderContentNum = (TextView) view.findViewById(R.id.tv_orderContentNum);
        //规格
        tlOrderDetails = (TagsLayout) view.findViewById(R.id.tl_OrderDetails);
        tlOrderDetails.setVisibility(View.INVISIBLE);
    }
}