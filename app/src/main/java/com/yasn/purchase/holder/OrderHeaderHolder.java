package com.yasn.purchase.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yasn.purchase.R;

/**
 * Created by gs on 2018/5/28.
 */

public class OrderHeaderHolder extends RecyclerView.ViewHolder {
    public TextView tvOrderNumber;
    public TextView tvOrderPayType;
    public OrderHeaderHolder(View view) {
        super(view);
        tvOrderNumber = (TextView) view.findViewById(R.id.tv_orderNumber);
        tvOrderPayType = (TextView) view.findViewById(R.id.tv_orderPayType);
        tvOrderPayType.setVisibility(View.GONE);
    }
}
