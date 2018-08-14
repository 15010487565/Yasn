package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.StatisticsAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.StatisticsModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

/**
 * 2018年7月18日 14:39:14
 * 统计
 */
public class StatisticsActivity extends SimpleTopbarActivity {

    private TextView stotalMoney, statisGoodsNum, statisOrderNum;
    private RecyclerView rcStatistic;
    private StatisticsAdapter adapter;
    private NestedScrollView nsStatistics;
    @Override
    protected Object getTopbarTitle() {
        return R.string.statistics;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Intent intent = getIntent();
        String memberId = intent.getStringExtra("memberId");
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        params.put("memberId", memberId);
        okHttpGet(100, Config.SHOPSTATISTICS, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        nsStatistics = (NestedScrollView) findViewById(R.id.ns_statistics);
        stotalMoney = (TextView) findViewById(R.id.tv_StatisticStotalMoney);
        statisGoodsNum = (TextView) findViewById(R.id.tv_StatisticGoodsNum);
        statisOrderNum = (TextView) findViewById(R.id.tv_StatisticOrderNum);
        rcStatistic = (RecyclerView) findViewById(R.id.rc_Statistic);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rcStatistic.setLayoutManager(linearLayoutManager);
        adapter = new StatisticsAdapter(this);
        rcStatistic.setAdapter(adapter);
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                this, LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.line_gray));
        rcStatistic.addItemDecoration(recyclerViewDecoration);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                StatisticsModel statisticsModel = JSON.parseObject(returnData, StatisticsModel.class);
                StatisticsModel.DataBean data = statisticsModel.getData();
                if (data !=null){
                    StatisticsModel.DataBean.TotalAllBean totalAll = data.getTotalAll();
                    //采购金额
                    double totalCount = totalAll.getTotalCount();
//                    if (totalCount >= 1000000){
//                        DecimalFormat f = new DecimalFormat("00.##E0");
//                        String format = f.format(totalCount);
//                        stotalMoney.setText("￥" + format);
//                    }else {
                        stotalMoney.setText("￥" + String.format("%.2f", totalCount));
//                    }
                    //总商品数
                    int goodsNum = totalAll.getGoodsNum();
                    statisGoodsNum.setText(goodsNum + "件");
                    //总订单数
                    int orderNum = totalAll.getOrderNum();
                    statisOrderNum.setText(orderNum + "笔");
                    //年月
                    List<StatisticsModel.DataBean.SumInfoEachMonthBean> sumInfoEachMonth = data.getSumInfoEachMonth();
                    adapter.setData(sumInfoEachMonth);
                    nsStatistics.setVisibility(View.VISIBLE);
                }else {
                    nsStatistics.setVisibility(View.GONE);
                    ToastUtil.showToast("暂无统计数据！");
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
