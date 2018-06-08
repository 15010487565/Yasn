package com.yasn.purchase.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yasn.purchase.R;

/**
 * Created by gs on 2018/5/28.
 */

public class OrderShopNameHolder extends RecyclerView.ViewHolder {

    public TextView tvOrderShopName;

    public OrderShopNameHolder(View view) {
        super(view);
        tvOrderShopName = (TextView) view.findViewById(R.id.tv_orderShopName);
    }
}
