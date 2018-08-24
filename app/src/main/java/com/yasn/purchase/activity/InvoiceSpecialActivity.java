package com.yasn.purchase.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.InvoiceModel;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

/**
 * Created by gs on 2018/6/14.
 * 专用发票
 */

public class InvoiceSpecialActivity extends SimpleTopbarActivity {

    private TextView tvInvoicespTitle, tvInvoicespNum, tvInvoicespAddress, tvInvoicespMobile, tvInvoicespBank, tvInvoicespBankNum;
    private TextView tvInvoiceSpecial;//保存按钮
    private LinearLayout llAudit, llAuditing;

    private TextView tvAuditTitleleft, tvAuditNumleft, tvAuditAddressleft, tvAuditMobileleft, tvAuditBankleft, tvAuditBankNumleft;
    private EditText etrightAuditTitle, etrightAuditNum, etrightAuditAddress, etrightAuditMobile
            , etrightAuditBank, etrightAuditBankNum;
    private TextView tvInvoicespAuditing //审核状态
            , tvInvoiceFailure;//提交失败错误提示
    @Override
    protected Object getTopbarTitle() {
        return R.string.zhuanpiao;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoicespecial);
        Map<String, Object> params = new HashMap();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
            return;
        }
        okHttpGet(100, Config.SHOPGETINVOICE , params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //审核完成or未填写布局
        initAudit();
        //审核中布局
        initAuditing();
        //审核状态
        tvInvoicespAuditing = (TextView) findViewById(R.id.tv_InvoicespAuditing);
        tvInvoicespAuditing.setVisibility(View.GONE);
        //保存
        tvInvoiceSpecial = (TextView) findViewById(R.id.tv_InvoiceSpecial);
        tvInvoiceSpecial.setOnClickListener(this);
    }


    private void initAudit() {
        llAudit = (LinearLayout) findViewById(R.id.ll_Audit);
        tvAuditTitleleft = (TextView) findViewById(R.id.tv_InvoicespAuditTitle_left);
        initLeftView(tvAuditTitleleft);
        tvAuditNumleft = (TextView) findViewById(R.id.tv_InvoicespAuditNum_left);
        initLeftView(tvAuditNumleft);
        tvAuditAddressleft = (TextView) findViewById(R.id.tv_InvoicespAuditAddress_left);
        initLeftView(tvAuditAddressleft);
        tvAuditMobileleft = (TextView) findViewById(R.id.tv_InvoicespAuditMobile_left);
        initLeftView(tvAuditMobileleft);
        tvAuditBankleft = (TextView) findViewById(R.id.tv_InvoicespAuditBank_left);
        initLeftView(tvAuditBankleft);
        tvAuditBankNumleft = (TextView) findViewById(R.id.tv_InvoicespauditBankNum_left);
        initLeftView(tvAuditBankNumleft);

        etrightAuditTitle = (EditText) findViewById(R.id.tv_InvoicespAuditTitle_right);
        etrightAuditNum = (EditText) findViewById(R.id.tv_InvoicespAuditNum_right);
        etrightAuditAddress = (EditText) findViewById(R.id.tv_InvoicespAuditAddress_right);
        etrightAuditMobile = (EditText) findViewById(R.id.tv_InvoicespAuditMobile_right);
        etrightAuditBank = (EditText) findViewById(R.id.tv_InvoicespAuditBank_right);
        etrightAuditBankNum = (EditText) findViewById(R.id.tv_InvoicespauditBankNum_right);
        //提交失败错误提示
        tvInvoiceFailure = (TextView) findViewById(R.id.tv_InvoiceFailure);
    }

    private void initLeftView(TextView textView) {
        String s = textView.getText().toString();
        SpannableStringBuilder span = new SpannableStringBuilder(s);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange)), 0, 1,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),  R.color.black_99)), s.length() - 1, s.length(),
//                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    private void initAuditing() {
        llAuditing = (LinearLayout) findViewById(R.id.ll_Auditing);
        tvInvoicespTitle = (TextView) findViewById(R.id.tv_InvoicespTitle);
        tvInvoicespNum = (TextView) findViewById(R.id.tv_InvoicespNum);
        tvInvoicespAddress = (TextView) findViewById(R.id.tv_InvoicespAddress);
        tvInvoicespMobile = (TextView) findViewById(R.id.tv_InvoicespMobile);
        tvInvoicespBank = (TextView) findViewById(R.id.tv_InvoicespBank);
        tvInvoicespBankNum = (TextView) findViewById(R.id.tv_InvoicespBankNum);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_InvoiceSpecial:
                String trim = tvInvoiceSpecial.getText().toString().trim();
                if ("修改".equals(trim)){
                    llAudit.setVisibility(View.VISIBLE);
                    tvInvoicespAuditing.setVisibility(View.GONE);
                    llAuditing.setVisibility(View.GONE);
                    tvInvoiceSpecial.setVisibility(View.VISIBLE);
                    tvInvoiceSpecial.setText("保存");
                    return;
                }else if ("保存".equals(trim)){

                }
                Map<String, Object> params = new HashMap();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                } else {
                    ToastUtil.showToast("登录过期，请重新登录");
                    return;
                }
                String AuditTitleStr = etrightAuditTitle.getText().toString().trim();
                if (TextUtils.isEmpty(AuditTitleStr)) {
                    ToastUtil.showToast("单位名称不能为空！");
                    return;
                } else {
                    params.put("title", AuditTitleStr);
                }
                String AuditNumStr = etrightAuditNum.getText().toString().trim();
                if (TextUtils.isEmpty(AuditNumStr)) {
                    ToastUtil.showToast("纳税识别号不能为空！");
                    return;
                } else {
                    params.put("invoiceNum", AuditNumStr);
                }
                String AuditAddressStr = etrightAuditAddress.getText().toString().trim();
                if (TextUtils.isEmpty(AuditAddressStr)) {
                    ToastUtil.showToast("注册地址不能为空！");
                    return;
                } else {
                    params.put("invoiceAddress", AuditAddressStr);
                }
                String AuditMobileStr = etrightAuditMobile.getText().toString().trim();
                if (TextUtils.isEmpty(AuditMobileStr)) {
                    ToastUtil.showToast("注册电话不能为空！");
                    return;
                } else {
                    params.put("invoiceMobile", AuditMobileStr);
                }
                String AuditBankStr = etrightAuditBank.getText().toString().trim();
                if (TextUtils.isEmpty(AuditBankStr)) {
                    ToastUtil.showToast("开户银不能为空！");
                    return;
                } else {
                    params.put("invoiceBank", AuditBankStr);
                }
                String AuditBankNumStr = etrightAuditBankNum.getText().toString().trim();
                if (TextUtils.isEmpty(AuditBankNumStr)) {
                    ToastUtil.showToast("银⾏账号不能为空！");
                    return;
                } else {
                    params.put("invoiceBankNum", AuditBankNumStr);
                }

                okHttpGet(101, Config.SHOPINVOICE, params);

                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                try {
                    InvoiceModel invoiceModel = JSON.parseObject(returnData, InvoiceModel.class);
                    InvoiceModel.DataBean data = invoiceModel.getData();
                    if (data == null) {
                        llAudit.setVisibility(View.VISIBLE);
                        tvInvoicespAuditing.setVisibility(View.GONE);
                        llAuditing.setVisibility(View.GONE);
                        tvInvoiceSpecial.setVisibility(View.VISIBLE);
                    } else {
                        //0审核中, 1通过, 2拒绝
                        int invoiceStatus = data.getInvoiceStatus();
                        if (invoiceStatus == 0) {
                            llAudit.setVisibility(View.GONE);
                            tvInvoicespAuditing.setVisibility(View.VISIBLE);
                            llAuditing.setVisibility(View.VISIBLE);
                            tvInvoiceSpecial.setVisibility(View.GONE);

                            String title = data.getTitle();
                            if (!TextUtils.isEmpty(title)) {
                                tvInvoicespTitle.setText(title);
                            }
                            String invoiceNum = data.getInvoiceNum();
                            if (!TextUtils.isEmpty(invoiceNum)) {
                                tvInvoicespNum.setText(invoiceNum);
                            }
                            String invoiceAddress = data.getInvoiceAddress();
                            if (!TextUtils.isEmpty(invoiceAddress)) {
                                tvInvoicespAddress.setText(invoiceAddress);
                            }
                            String invoiceMobile = data.getInvoiceMobile();
                            if (!TextUtils.isEmpty(invoiceMobile)) {
                                tvInvoicespMobile.setText(invoiceMobile);
                            }
                            String invoiceBank = data.getInvoiceBank();
                            if (!TextUtils.isEmpty(invoiceBank)) {
                                tvInvoicespBank.setText(invoiceBank);
                            }
                            String invoiceBankNum = data.getInvoiceBankNum();
                            if (!TextUtils.isEmpty(invoiceBankNum)) {
                                tvInvoicespBankNum.setText(invoiceBankNum);
                            }
                        } else if (invoiceStatus == 1){//1通过,
                            llAudit.setVisibility(View.GONE);
                            tvInvoicespAuditing.setVisibility(View.VISIBLE);
                            tvInvoicespAuditing.setText("审核通过");
                            llAuditing.setVisibility(View.VISIBLE);
                            tvInvoiceSpecial.setVisibility(View.VISIBLE);

                            String title = data.getTitle();
                            if (!TextUtils.isEmpty(title)) {
                                tvInvoicespTitle.setText(title);
                            }
                            String invoiceNum = data.getInvoiceNum();
                            if (!TextUtils.isEmpty(invoiceNum)) {
                                tvInvoicespNum.setText(invoiceNum);
                            }
                            String invoiceAddress = data.getInvoiceAddress();
                            if (!TextUtils.isEmpty(invoiceAddress)) {
                                tvInvoicespAddress.setText(invoiceAddress);
                            }
                            String invoiceMobile = data.getInvoiceMobile();
                            if (!TextUtils.isEmpty(invoiceMobile)) {
                                tvInvoicespMobile.setText(invoiceMobile);
                            }
                            String invoiceBank = data.getInvoiceBank();
                            if (!TextUtils.isEmpty(invoiceBank)) {
                                tvInvoicespBank.setText(invoiceBank);
                            }
                            String invoiceBankNum = data.getInvoiceBankNum();
                            if (!TextUtils.isEmpty(invoiceBankNum)) {
                                tvInvoicespBankNum.setText(invoiceBankNum);
                            }
                            tvInvoiceSpecial.setText("修改");
                            updataInvoice(data);
                        } else if (invoiceStatus == 2){// 2拒绝
                            tvInvoiceFailure.setVisibility(View.VISIBLE);
                            String noApproval = data.getNoApproval();
                            tvInvoiceFailure.setText(TextUtils.isEmpty(noApproval)?"":noApproval);
                            llAudit.setVisibility(View.VISIBLE);
                            tvInvoicespAuditing.setVisibility(View.VISIBLE);
                            tvInvoicespAuditing.setText("审核未通过");
                            tvInvoicespAuditing.setTextColor(ContextCompat.getColor(this,R.color.red));
                            llAuditing.setVisibility(View.GONE);
                            tvInvoiceSpecial.setVisibility(View.VISIBLE);

                            updataInvoice(data);
                        } else {
                            llAudit.setVisibility(View.VISIBLE);
                            tvInvoicespAuditing.setVisibility(View.GONE);
                            llAuditing.setVisibility(View.GONE);
                            tvInvoiceSpecial.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showToast(returnMsg);
                }

                break;
            case 101:
                if (returnCode == 200) {
                    finish();
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }
    }

    private void updataInvoice(InvoiceModel.DataBean data) {
        String title = data.getTitle();
        if (!TextUtils.isEmpty(title)) {
            etrightAuditTitle.setText(title);
        }
        String invoiceNum = data.getInvoiceNum();
        if (!TextUtils.isEmpty(invoiceNum)) {
            etrightAuditNum.setText(invoiceNum);
        }
        String invoiceAddress = data.getInvoiceAddress();
        if (!TextUtils.isEmpty(invoiceAddress)) {
            etrightAuditAddress.setText(invoiceAddress);
        }
        String invoiceMobile = data.getInvoiceMobile();
        if (!TextUtils.isEmpty(invoiceMobile)) {
            etrightAuditMobile.setText(invoiceMobile);
        }
        String invoiceBank = data.getInvoiceBank();
        if (!TextUtils.isEmpty(invoiceBank)) {
            etrightAuditBank.setText(invoiceBank);
        }
        String invoiceBankNum = data.getInvoiceBankNum();
        if (!TextUtils.isEmpty(invoiceBankNum)) {
            etrightAuditBankNum.setText(invoiceBankNum);
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
