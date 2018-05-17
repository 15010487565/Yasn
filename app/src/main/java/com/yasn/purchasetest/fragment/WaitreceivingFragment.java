package com.yasn.purchasetest.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yasn.purchasetest.R;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;

/**
 * Created by gs on 2018/1/8.
 */

public class WaitreceivingFragment extends BaseFragment{

    private RelativeLayout title;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waitreceiving;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        title = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        title.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
        }

    }

    @Override
    public void onCancelResult() {

    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

    }

    @Override
    public void onParseErrorResult(int errorCode) {

    }

    @Override
    public void onFinishResult() {

    }
}