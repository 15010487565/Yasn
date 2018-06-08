package com.yasn.purchase.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.OrderDetailsActivity;

/**
 * Created by gs on 2018/2/3.
 */

public abstract class OrderFragment extends SimpleTopbarFragment {

    protected LinearLayout llOrderError;
    @Override
    protected void initView(LayoutInflater inflater, View view) {
        llOrderError = (LinearLayout)view.findViewById(R.id.ll_orderError);
        llOrderError.setVisibility(View.GONE);
    }
    protected void startOrderDetailsActivity(int orderId,int isMainOrder){
        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("isMainOrder",isMainOrder);
        startActivity(intent);
    }
}
