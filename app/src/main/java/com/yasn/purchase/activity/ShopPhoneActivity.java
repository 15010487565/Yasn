package com.yasn.purchase.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;
import com.yasn.purchase.utils.AlignedTextUtils;

import java.io.IOException;
import java.util.Map;

import static www.xcd.com.mylibrary.help.HelpUtils.REQUEST_CODE_ASK_CALL_PHONE;

public class ShopPhoneActivity extends BaseYasnActivity {

    private TextView tvShopPhoneLeft, tvShopPhoneCall;
    @Override
    protected Object getTopbarTitle() {
        return R.string.phoneconsult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_phone);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        tvShopPhoneLeft = (TextView) findViewById(R.id.tv_ShopPhoneLeft);
        SpannableStringBuilder discountString = AlignedTextUtils.justifyString("海南站", 4);
        discountString.append("：");
        tvShopPhoneLeft.setText(discountString);
        tvShopPhoneCall = (TextView) findViewById(R.id.tv_ShopPhoneCall);
        tvShopPhoneCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_ShopPhoneCall:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
                    }else {
                        Intent telIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4009973315"));
                        telIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(telIntent);
                    }
                }else {
                    Intent telIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4009973315"));
                    telIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(telIntent);
                }
                break;
        }
    }

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
}
