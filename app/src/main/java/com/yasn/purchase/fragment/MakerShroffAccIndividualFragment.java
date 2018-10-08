package com.yasn.purchase.fragment;

import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnFragment;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 创客个人收款账号
 * Created by gs on 2018/1/8.
 */

public class MakerShroffAccIndividualFragment extends BaseYasnFragment {

    private TextView nameFillNullLeft, bankFillNullLeft
            , bankNumFillNullLeft, tvOk;
    private EditText nameFillNullRight,bankFillNullRight,bankNumFillNullRight;
    private LinearLayout llFillFinish, llFillNull;
    @Override
    protected Object getTopbarTitle() {
        return "个人账号";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_makershroffindividual;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        RelativeLayout title = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        title.setVisibility(View.GONE);
        //填写完成
        llFillFinish = (LinearLayout) view.findViewById(R.id.ll_MakerIvidualFillFinish);

        //未填写
        llFillNull = (LinearLayout) view.findViewById(R.id.ll_MakerIvidualFillNull);
        nameFillNullLeft = (TextView) view.findViewById(R.id.tv_MakerIvidualFillNullName_left);
        initLeftView(nameFillNullLeft);
        bankFillNullLeft = (TextView) view.findViewById(R.id.tv_MakerIvidualFillNullBank_left);
        initLeftView(bankFillNullLeft);
        bankNumFillNullLeft = (TextView) view.findViewById(R.id.tv_MakerIvidualFillNullBankNum_left);
        initLeftView(bankNumFillNullLeft);

        nameFillNullRight = (EditText) view.findViewById(R.id.tv_MakerIvidualFillNullName_right);
        nameFillNullRight.setFilters(new InputFilter[]{filter});
        bankFillNullRight = (EditText) view.findViewById(R.id.tv_MakerIvidualFillNullBank_right);
        bankFillNullRight.setFilters(new InputFilter[]{filter});
        bankNumFillNullRight = (EditText) view.findViewById(R.id.tv_MakerIvidualFillNullBankNum_right);
        bankNumFillNullRight.setFilters(new InputFilter[]{filter});
        //确定提交
        tvOk = (TextView) view.findViewById(R.id.tv_MakerIvidualFillNullOk);
        tvOk.setOnClickListener(this);
    }

    private void initLeftView(TextView textView) {
        String s = textView.getText().toString();
        SpannableStringBuilder span = new SpannableStringBuilder(s);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.orange)), 0, 1,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),  R.color.black_99)), s.length() - 1, s.length(),
//                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_MakerIvidualFillNullOk:
                String strTitle = nameFillNullRight.getText().toString().toString();
                if (TextUtils.isEmpty(strTitle)){
                    ToastUtil.showToast("单位名称不能为空！");
                    return;
                }
                String strBank = bankFillNullRight.getText().toString().toString();
                if (TextUtils.isEmpty(strBank)){
                    ToastUtil.showToast("开户银行不能为空！");
                    return;
                }
                String strBankNum = bankNumFillNullRight.getText().toString().toString();
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
                params.put("bankDeposit",strBank);
                params.put("accountNum", strBankNum);
                params.put("type", "2");//账户类型 1 企业；2 个人
                okHttpGet(100, Config.SUBMITMAKERRECEIPTACCOUNT , params);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
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