package com.yasn.purchase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasn.purchase.activityold.WebViewH5Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * Created by gs on 2018/2/3.
 */

public abstract class SimpleTopbarFragment extends BaseFragment {


    public String token;
    public String resetToken;
    public String resetTokenTime;

    protected abstract void OkHttpDemand();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
        resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(getActivity()).getSpString("resetTokenTime");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void startWebViewActivity(String webViewUrl){
        Intent intent = new Intent(getActivity(), WebViewH5Activity.class);
        intent.putExtra("webViewUrl",webViewUrl);
        startActivity(intent);
    }
    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) return "";
            else return null;
        }
    };
}
