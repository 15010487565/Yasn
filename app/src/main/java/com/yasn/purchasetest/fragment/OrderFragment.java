package com.yasn.purchasetest.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yasn.purchasetest.R;

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
}
