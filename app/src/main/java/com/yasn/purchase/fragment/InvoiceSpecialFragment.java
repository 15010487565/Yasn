package com.yasn.purchase.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
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

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.InvoiceModel;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gs on 2018/6/14.
 * 专用发票
 */

public class InvoiceSpecialFragment extends SimpleTopbarFragment {

    private TextView tvInvoicespTitle, tvInvoicespNum, tvInvoicespAddress, tvInvoicespMobile, tvInvoicespBank, tvInvoicespBankNum;
    private TextView tvInvoiceSpecial;
    private LinearLayout llAudit, llAuditing;

    private TextView tvAuditTitleleft, tvAuditNumleft, tvAuditAddressleft, tvAuditMobileleft, tvAuditBankleft, tvAuditBankNumleft;
    private EditText tvrightAuditTitle, tvrightAuditNum, tvrightAuditAddress, tvrightAuditMobile, tvrightAuditBank, tvrightAuditBankNum;
    private TextView tvAuditState;//审核状态
    private TextView tvInvoiceFailure;//审核失败原因
    @Override
    protected void OkHttpDemand() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invoicespecial;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        RelativeLayout topbat_parent = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);
        //审核完成or未填写布局
        initAudit(view);
        //审核中布局
        initAuditing(view);

        tvInvoiceSpecial = (TextView) view.findViewById(R.id.tv_InvoiceSpecial);
        tvInvoiceSpecial.setOnClickListener(this);
        //审核状态
        tvAuditState = (TextView) view.findViewById(R.id.tv_InvoicespAuditing);
        tvAuditState.setVisibility(View.GONE);
        //审核失败愿意
        tvInvoiceFailure = (TextView) view.findViewById(R.id.tv_InvoiceAuditFailure);
        tvInvoiceFailure.setVisibility(View.GONE);
        Map<String, Object> params = new HashMap();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
            return;
        }
        okHttpGet(100, Config.GETINVOICE + "3", params);
    }

    private void initAudit(View view) {
        llAudit = (LinearLayout) view.findViewById(R.id.ll_Audit);
        tvAuditTitleleft = (TextView) view.findViewById(R.id.tv_InvoicespAuditTitle_left);
        initLeftView(tvAuditTitleleft);
        tvAuditNumleft = (TextView) view.findViewById(R.id.tv_InvoicespAuditNum_left);
        initLeftView(tvAuditNumleft);
        tvAuditAddressleft = (TextView) view.findViewById(R.id.tv_InvoicespAuditAddress_left);
        initLeftView(tvAuditAddressleft);
        tvAuditMobileleft = (TextView) view.findViewById(R.id.tv_InvoicespAuditMobile_left);
        initLeftView(tvAuditMobileleft);
        tvAuditBankleft = (TextView) view.findViewById(R.id.tv_InvoicespAuditBank_left);
        initLeftView(tvAuditBankleft);
        tvAuditBankNumleft = (TextView) view.findViewById(R.id.tv_InvoicespauditBankNum_left);
        initLeftView(tvAuditBankNumleft);

        tvrightAuditTitle = (EditText) view.findViewById(R.id.tv_InvoicespAuditTitle_right);
        tvrightAuditNum = (EditText) view.findViewById(R.id.tv_InvoicespAuditNum_right);
        tvrightAuditAddress = (EditText) view.findViewById(R.id.tv_InvoicespAuditAddress_right);
        tvrightAuditMobile = (EditText) view.findViewById(R.id.tv_InvoicespAuditMobile_right);
        tvrightAuditBank = (EditText) view.findViewById(R.id.tv_InvoicespAuditBank_right);
        tvrightAuditBankNum = (EditText) view.findViewById(R.id.tv_InvoicespauditBankNum_right);
    }

    private void initLeftView(TextView textView) {
        String s1 = textView.getText().toString();
        String s2 = textView.getText().toString().trim();
        SpannableStringBuilder span = new SpannableStringBuilder(s1);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.orange)), s1.length()-s2.length(), s1.length()-s2.length()+1,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),  R.color.black_99)), s.length() - 1, s.length(),
//                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    private void initAuditing(View view) {
        llAuditing = (LinearLayout) view.findViewById(R.id.ll_Auditing);
        tvInvoicespTitle = (TextView) view.findViewById(R.id.tv_InvoicespTitle);
        tvInvoicespNum = (TextView) view.findViewById(R.id.tv_InvoicespNum);
        tvInvoicespAddress = (TextView) view.findViewById(R.id.tv_InvoicespAddress);
        tvInvoicespMobile = (TextView) view.findViewById(R.id.tv_InvoicespMobile);
        tvInvoicespBank = (TextView) view.findViewById(R.id.tv_InvoicespBank);
        tvInvoicespBankNum = (TextView) view.findViewById(R.id.tv_InvoicespBankNum);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_InvoiceSpecial:
//                if (invoiceStatus == 0){
//                    Intent intent = new Intent();
//                    intent.putExtra("invoice", "专用发票"); //将计算的值回传回去
//                    getFragmentActivity().setResult(2, intent);
//                    getFragmentActivity().finish();
//                }else {
                    Map<String, String> params = new HashMap();
                    if (token != null && !"".equals(token)) {
                        params.put("access_token", token);
                    } else if (resetToken != null && !"".equals(resetToken)) {
                        params.put("access_token", resetToken);
                    } else {
                        ToastUtil.showToast("登录过期，请重新登录");
                        return;
                    }
                    String AuditTitleStr = tvrightAuditTitle.getText().toString().trim();
                    if (TextUtils.isEmpty(AuditTitleStr)) {
                        ToastUtil.showToast("单位名称不能为空！");
                        return;
                    } else {
                        params.put("title", AuditTitleStr);
                    }
                    String AuditNumStr = tvrightAuditNum.getText().toString().trim();
                    if (TextUtils.isEmpty(AuditNumStr)) {
                        ToastUtil.showToast("纳税识别号不能为空！");
                        return;
                    } else {
                        params.put("invoiceNum", AuditNumStr);
                    }
                    String AuditAddressStr = tvrightAuditAddress.getText().toString().trim();
                    if (TextUtils.isEmpty(AuditAddressStr)) {
                        ToastUtil.showToast("注册地址不能为空！");
                        return;
                    } else {
                        params.put("invoiceAddress", AuditAddressStr);
                    }
                    String AuditMobileStr = tvrightAuditMobile.getText().toString().trim();
                    if (TextUtils.isEmpty(AuditMobileStr)) {
                        ToastUtil.showToast("注册电话不能为空！");
                        return;
                    } else {
                        params.put("invoiceMobile", AuditMobileStr);
                    }
                    String AuditBankStr = tvrightAuditBank.getText().toString().trim();
                    if (TextUtils.isEmpty(AuditBankStr)) {
                        ToastUtil.showToast("开户银不能为空！");
                        return;
                    } else {
                        params.put("invoiceBank", AuditBankStr);
                    }
                    String AuditBankNumStr = tvrightAuditBankNum.getText().toString().trim();
                    if (TextUtils.isEmpty(AuditBankNumStr)) {
                        ToastUtil.showToast("银⾏账号不能为空！");
                        return;
                    } else {
                        params.put("invoiceBankNum", AuditBankNumStr);
                    }

                    params.put("invoiceType", "3");
                    okHttpPost(101, Config.SAVERECEIPT, params);
//                }
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:

                    InvoiceModel invoiceModel = JSON.parseObject(returnData, InvoiceModel.class);
                    InvoiceModel.DataBean data = invoiceModel.getData();
                    if (data == null) {
                        llAudit.setVisibility(View.VISIBLE);
//                        tvInvoiceSpecial.setVisibility(View.VISIBLE);
                        llAuditing.setVisibility(View.GONE);
                        tvAuditState.setVisibility(View.GONE);
                        tvInvoiceFailure.setVisibility(View.GONE);
                    } else {
                        //0审核中, 1通过, 2拒绝
                        int invoiceStatus = data.getInvoiceStatus();
                        tvAuditState.setVisibility(View.VISIBLE);
                        if (invoiceStatus == 0) {
                            llAudit.setVisibility(View.GONE);
//                            tvInvoiceSpecial.setVisibility(View.GONE);
                            llAuditing.setVisibility(View.VISIBLE);
                            tvAuditState.setText("审核中");
                            tvAuditState.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                            tvInvoiceFailure.setVisibility(View.GONE);
                            String title = data.getTitle();
                            if (!TextUtils.isEmpty(title)) {
                                tvInvoicespTitle.setText(title);
                                tvrightAuditTitle.setText(title);
                            }
                            String invoiceNum = data.getInvoiceNum();
                            if (!TextUtils.isEmpty(invoiceNum)) {
                                tvInvoicespNum.setText(invoiceNum);
                                tvrightAuditNum.setText(invoiceNum);
                            }
                            String invoiceAddress = data.getInvoiceAddress();
                            if (!TextUtils.isEmpty(invoiceAddress)) {
                                tvInvoicespAddress.setText(invoiceAddress);
                                tvrightAuditAddress.setText(invoiceAddress);
                            }
                            String invoiceMobile = data.getInvoiceMobile();
                            if (!TextUtils.isEmpty(invoiceMobile)) {
                                tvInvoicespMobile.setText(invoiceMobile);
                                tvrightAuditMobile.setText(invoiceMobile);
                            }
                            String invoiceBank = data.getInvoiceBank();
                            if (!TextUtils.isEmpty(invoiceBank)) {
                                tvInvoicespBank.setText(invoiceBank);
                                tvrightAuditBank.setText(invoiceBank);
                            }
                            String invoiceBankNum = data.getInvoiceBankNum();
                            if (!TextUtils.isEmpty(invoiceBankNum)) {
                                tvInvoicespBankNum.setText(invoiceBankNum);
                                tvrightAuditBankNum.setText(invoiceBankNum);
                            }
                        } else {
                            //1通过, 2拒绝
                            if (invoiceStatus == 2){
                                tvAuditState.setText("审核未通过");
                                tvAuditState.setTextColor(ContextCompat.getColor(getActivity(),R.color.red));
                                tvInvoiceFailure.setVisibility(View.VISIBLE);
                                String noApproval = data.getNoApproval();
                                tvInvoiceFailure.setText(TextUtils.isEmpty(noApproval)?"":noApproval);
                            }else {
                                tvAuditState.setText("审核通过");
                                tvAuditState.setTextColor(ContextCompat.getColor(getActivity(),R.color.orange));
                                tvInvoiceFailure.setVisibility(View.GONE);
                            }
                            llAudit.setVisibility(View.VISIBLE);
//                            tvInvoiceSpecial.setVisibility(View.VISIBLE);
                            llAuditing.setVisibility(View.GONE);
                            String title = data.getTitle();
                            if (!TextUtils.isEmpty(title)) {
                                tvrightAuditTitle.setText(title);
                            }
                            String invoiceNum = data.getInvoiceNum();
                            if (!TextUtils.isEmpty(invoiceNum)) {
                                tvrightAuditNum.setText(invoiceNum);
                            }
                            String invoiceAddress = data.getInvoiceAddress();
                            if (!TextUtils.isEmpty(invoiceAddress)) {
                                tvrightAuditAddress.setText(invoiceAddress);
                            }
                            String invoiceMobile = data.getInvoiceMobile();
                            if (!TextUtils.isEmpty(invoiceMobile)) {
                                tvrightAuditMobile.setText(invoiceMobile);
                            }
                            String invoiceBank = data.getInvoiceBank();
                            if (!TextUtils.isEmpty(invoiceBank)) {
                                tvrightAuditBank.setText(invoiceBank);
                            }
                            String invoiceBankNum = data.getInvoiceBankNum();
                            if (!TextUtils.isEmpty(invoiceBankNum)) {
                                tvrightAuditBankNum.setText(invoiceBankNum);
                            }
                        }
                    }

                break;
            case 101:
                if (returnCode == 200) {
                    Intent intent = new Intent();
                    intent.putExtra("invoice", "专用发票"); //将计算的值回传回去
                    getFragmentActivity().setResult(2, intent);
                    getFragmentActivity().finish();
                }
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
}
