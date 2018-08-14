package com.yasn.purchase.fragment;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 创客企业s收款账号
 * Created by gs on 2018/1/8.
 */

public class MakerShroffAccFirmFragment extends SimpleTopbarFragment {

    private EditText tvTitle, tvNum, tvAddress,tvMobile, tvBank, tvBankNum;
    private TextView tvOk;
    @Override
    protected Object getTopbarTitle() {
        return "企业账号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_makershroffaccfirm;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {

        RelativeLayout title = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        title.setVisibility(View.GONE);

        tvTitle = (EditText) view.findViewById(R.id.tv_MakerFirmFillNullTitle_right);
        tvTitle.setFilters(new InputFilter[]{filter});
        tvNum = (EditText) view.findViewById(R.id.tv_MakerFirmFillNullNum_right);
        tvNum.setFilters(new InputFilter[]{filter});
        tvAddress = (EditText) view.findViewById(R.id.tv_MakerFirmFillNullAddress_right);
        tvAddress.setFilters(new InputFilter[]{filter});
        tvMobile = (EditText) view.findViewById(R.id.tv_MakerFirmFillNullMobile_right);
        tvMobile.setFilters(new InputFilter[]{filter});
        tvBank = (EditText) view.findViewById(R.id.tv_MakerFirmFillNullBank_right);
        tvBank.setFilters(new InputFilter[]{filter});
        tvBankNum = (EditText) view.findViewById(R.id.tv_MakerFirmFillNullBankNum_right);
        tvBankNum.setFilters(new InputFilter[]{filter});
        tvOk = (TextView) view.findViewById(R.id.tv_MakerFirmFillNullOk);
        tvOk.setFilters(new InputFilter[]{filter});
        tvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_MakerFirmFillNullOk:
                String strTitle = tvTitle.getText().toString().toString();
                if (TextUtils.isEmpty(strTitle)){
                    ToastUtil.showToast("单位名称不能为空！");
                    return;
                }
                String strNum = tvNum.getText().toString().toString();
                if (TextUtils.isEmpty(strNum)){
                    ToastUtil.showToast("纳税识别号不能为空！");
                    return;
                }
                String strAddress = tvAddress.getText().toString().toString();
                if (TextUtils.isEmpty(strAddress)){
                    ToastUtil.showToast("注册地址不能为空！");
                    return;
                }
                String strMobile = tvMobile.getText().toString().toString();
                if (TextUtils.isEmpty(strMobile)){
                    ToastUtil.showToast("手机号不能为空！");
                    return;
                }
                String strBank = tvBank.getText().toString().toString();
                if (TextUtils.isEmpty(strBank)){
                    ToastUtil.showToast("开户银行不能为空！");
                    return;
                }
                String strBankNum = tvBankNum.getText().toString().toString();
                if (TextUtils.isEmpty(strBankNum)){
                    ToastUtil.showToast("银行帐号不能为空！");
                    return;
                }
                Map<String, Object> params = new HashMap<String, Object>();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                }
                params.put("name",strTitle);
                params.put("taxpayerNum", strNum);
                params.put("address", strAddress);
                params.put("mobile", strMobile);
                params.put("bankDeposit",strBank);
                params.put("accountNum", strBankNum);
                params.put("type", "1");//账户类型 1 企业；2 个人
                okHttpGet(100, Config.SUBMITMAKERRECEIPTACCOUNT , params);
                break;

        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200){
                    getActivity().finish();
                }
                ToastUtil.showToast(returnMsg);
                break;
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

    @Override
    protected void OkHttpDemand() {

    }
}