package com.yasn.purchasetest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yasn.purchasetest.R;
import com.yasn.purchasetest.activity.base.YasnBaseActivity;
import com.yasn.purchasetest.adapter.GuideAdapter;
import com.yasn.purchasetest.custom.indicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * 引导页
 */

public class GuideActivity extends YasnBaseActivity implements View.OnClickListener {

    //code跳转码
    public static int GUIDEACTIVITYCODE = 10000;
    private ViewPager viewPager;
    private CirclePageIndicator indicator;

    private GuideAdapter adapter;
    private int[] images;
    private boolean isRegister;
    ArrayList<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
    }

    private void initData() {
        adapter = new GuideAdapter(this);
        if (imageList == null || imageList.size() == 0) {
            images = new int[4];
            images[0] = R.mipmap.guide_pager1_bg;
            images[1] = R.mipmap.guide_pager2_bg;
            images[2] = R.mipmap.guide_pager3_bg;
            images[3] = R.mipmap.guide_pager4_bg;
            adapter.setList(images);
        } else {
            System.out.println("IAMGES-->" + imageList);
            adapter.setImageUrls(imageList);
        }

        adapter.setListener(this);
        viewPager.setAdapter(adapter);
        indicator.setFillColor(ContextCompat.getColor(this,R.color.white));
        indicator.setViewPager(viewPager);
        isRegister = false;
    }


    @Override
    public void onClick(View view) {
//        if (isRegister) {
//            Bundle bundle = new Bundle();
//            bundle.putInt("type", 4);
//            startActivity(new Intent(this, MainActivityOld.class));
//        } else
//            startActivity(new Intent(this, MainActivityOld.class));
        // 设置结果，并进行传送
        this.setResult(Activity.RESULT_OK);
        finish();
    }
}
