package com.yasn.purchase.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.yasn.purchase.activity.base.BaseYasnFragment;

import java.io.IOException;
import java.util.Map;


/**
 * Created by Android on 2017/9/5.
 */
public class ShopCarFragment extends BaseYasnFragment {


    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

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

    @Override
    protected void OkHttpDemand() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {

    }
}
