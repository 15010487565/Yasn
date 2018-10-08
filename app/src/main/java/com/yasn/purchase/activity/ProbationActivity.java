package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;

import java.io.IOException;
import java.util.Map;

public class ProbationActivity extends BaseYasnActivity {

    private TextView tvProbationEndTime,tvProbationShopHome, tvProbationAuthor;
    @Override
    protected Class<?> getTopbarLeftFunc() {
        return null;
    }

    @Override
    protected Object getTopbarTitle() {
        return "试用开通";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probation);
        String endTime = getIntent().getStringExtra("endTime");
        endTime = (endTime == null ? "0000-00-00 00:00:00" : endTime);

        String formatHint = String.format(tvProbationEndTime.getText().toString(),endTime);
        SpannableStringBuilder span = new SpannableStringBuilder(formatHint);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,R.color.orange)), 6,6+endTime.length() ,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvProbationEndTime.setText(span);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        tvProbationEndTime = (TextView) findViewById(R.id.tv_ProbationEndTime);
        tvProbationShopHome = (TextView) findViewById(R.id.tv_ProbationShopHome);
        tvProbationShopHome.setOnClickListener(this);
        tvProbationAuthor = (TextView) findViewById(R.id.tv_ProbationAuthor);
        tvProbationAuthor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_ProbationShopHome://去购物
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.tv_ProbationAuthor://去认证
                startActivity(new Intent(this,AuthorImageActivity.class));
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
