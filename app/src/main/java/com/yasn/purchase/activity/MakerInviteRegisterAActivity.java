package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.CommonHelper;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MakerInviteRegisterAActivity extends BaseYasnActivity {

    private TextView tvMakerRegHint, tvMakerRegNext;
    private EditText etMakerRegAcc;
    private final static int REQUESTCODE = 1000; // 返回的结果码
    @Override
    protected Object getTopbarTitle() {
        return "邀请注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_invite_registera);
        Intent intent = getIntent();
        int isValid = intent.getIntExtra("isValid", 0);
        String makerInviteName = intent.getStringExtra("MakerInviteName");
        String strMakerRegHint = null;
        if (isValid == 0 || TextUtils.isEmpty(makerInviteName)) {
            strMakerRegHint = "邀请码无效,请核对二维码或联系克服 400-9973-315";
            SpannableStringBuilder span = new SpannableStringBuilder(strMakerRegHint);
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.black_33)), 0, strMakerRegHint.length() - 12,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange)), strMakerRegHint.length() - 12, strMakerRegHint.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvMakerRegHint.setText(span);
        } else {
            strMakerRegHint = String.format("%s 邀请您注册", makerInviteName);
            SpannableStringBuilder span = new SpannableStringBuilder(strMakerRegHint);
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange)), 0, makerInviteName.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.black_33)), makerInviteName.length(), strMakerRegHint.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvMakerRegHint.setText(span);
        }
        Log.e("TAG_邀请","makerInviteName="+makerInviteName);
        Log.e("TAG_邀请","strMakerRegHint="+strMakerRegHint);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        etMakerRegAcc = (EditText) findViewById(R.id.et_MakerRegAcc);

        tvMakerRegHint = (TextView) findViewById(R.id.tv_MakerRegHint);
        tvMakerRegNext = (TextView) findViewById(R.id.tv_MakerRegNext);
        tvMakerRegNext.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_MakerRegNext:
                String trim = etMakerRegAcc.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    boolean b = CommonHelper.with().checkPhone(trim);
                    if (!b) {
                        ToastUtil.showToast("请填写正确手机号");
                        return;
                    }
                    Map<String, Object> params = new HashMap<String, Object>();
                    if (token != null && !"".equals(token)) {
                        params.put("access_token", token);
                    } else if (resetToken != null && !"".equals(resetToken)) {
                        params.put("access_token", resetToken);
                    }
                    okHttpGet(100, Config.DETECTIONPHONE + trim, params);
                }
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    Intent intent = new Intent(this, MakerInviteRegisterBActivity.class);
                    intent.putExtra("MAKERPHONE", etMakerRegAcc.getText().toString().trim());
                    startActivityForResult(intent, REQUESTCODE);
                } else {
                    ToastUtil.showToast(returnMsg);
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
