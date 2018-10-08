package com.yasn.purchase.activity.base;

import android.util.Log;
import android.view.View;

import com.yasn.purchase.help.LoginOut;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.DialogUtil;

/**
 * Created by gs on 2018/9/26.
 */

public abstract class BaseYasnActivity extends SimpleTopbarActivity {

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        Log.e("TAG_BaseYasnActivity", "returnCode=" + returnCode + ";returnData=" + returnData);
        try {
            if (returnCode == 423) {
                JSONObject object = new JSONObject(returnData);
                loginOut(object.optString("error"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
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

    private void loginOut( String message) {
        DialogUtil.getInstance()
                .setContext(this)
                .setCancelable(false)
                .title("温馨提示")
                .message(message)
                .sureText("确定")
                .setSureOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginOut.startLoginOut(BaseYasnActivity.this);
                    }
                }).showDefaultDialog();
    }
}
