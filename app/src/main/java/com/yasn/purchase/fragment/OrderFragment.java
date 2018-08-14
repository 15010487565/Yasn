package com.yasn.purchase.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.OrderDetailsActivity;

/**
 * Created by gs on 2018/2/3.
 */

public abstract class OrderFragment extends SimpleTopbarFragment {

    protected LinearLayout llError;
    protected ImageView ivError;
    protected TextView tvErrorHint;
    @Override
    protected void initView(LayoutInflater inflater, View view) {
        llError = (LinearLayout)view.findViewById(R.id.ll_Error);
        llError.setVisibility(View.GONE);
        ivError = (ImageView) view.findViewById(R.id.iv_Error);
        tvErrorHint = (TextView) view.findViewById(R.id.tv_ErrorHint);
    }
    protected void startOrderDetailsActivity(int orderId,int isMainOrder){
        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("isMainOrder",isMainOrder);
        startActivity(intent);
    }
}
