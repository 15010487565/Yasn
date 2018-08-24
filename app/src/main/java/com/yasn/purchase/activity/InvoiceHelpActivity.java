package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.HelpGridAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.fragment.InvoiceHelpFragment;
import com.yasn.purchase.model.HelpModel;
import com.yasn.purchase.view.NoScrollGridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class InvoiceHelpActivity extends SimpleTopbarActivity implements AdapterView.OnItemClickListener{

    private NoScrollGridView gvInvoiceHelp;
    private HelpGridAdapter adapter;
    private List<InvoiceHelpFragment> fragmentList = new ArrayList<InvoiceHelpFragment>();
    private List<HelpModel.DataBean> data;
    private List<Map<String, String>> listTitle = new ArrayList<>();//顶部帮助title
    private HelpModel helpModel;
    @Override
    protected Object getTopbarTitle() {
        return R.string.help;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_help);
        Map<String, Object> params = new HashMap();
        okHttpGet(100, Config.HELP, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        gvInvoiceHelp = (NoScrollGridView) findViewById(R.id.gv_InvoiceHelp);
        //实例化adapter
        adapter = new HelpGridAdapter(this);
        gvInvoiceHelp.setAdapter(adapter);
        gvInvoiceHelp.setOnItemClickListener(this);
    }

    /**
     * 初始化fragments
     */
    protected void initFragments() {
        // 初始化fragment
       if (data !=null){
           for (int i = 0,j = data.size(); i < j; i++) {
               try {
                   InvoiceHelpFragment fragment = new InvoiceHelpFragment();
                   fragmentList.add(fragment);
                   fragment.setActivity(this);
               } catch (Exception e) {
               }
           }
           Intent intent = getIntent();
           int selcet = intent.getIntExtra("selcet", 0);
           initView(selcet);
       }
    }

    private void initView(int selcet) {

        // 为布局添加fragment,开启事物
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tran = fm.beginTransaction();
        for (int i = 0,j = data.size(); i < j; i++) {
            InvoiceHelpFragment invoiceHelpFragment = fragmentList.get(i);
            tran.add(R.id.frame_content,invoiceHelpFragment , "fragment"+i);
            Map<String, String> map = new HashMap<>();
            HelpModel.DataBean dataBean = data.get(i);
            String name = dataBean.getName();
            map.put("name", name);
            if (selcet == i){
                tran.show(invoiceHelpFragment);
                map.put("isCheck", "1");
            }else {
                tran.hide(invoiceHelpFragment);
                map.put("isCheck", "0");
            }
            listTitle.add(map);
            List<HelpModel.DataBean.ChidrenListBean> chidrenList = dataBean.getChidrenList();
            Bundle bundle = new Bundle();
            bundle.putInt("selcet", i);
            bundle.putSerializable("data",helpModel);
            invoiceHelpFragment.setArguments(bundle);
        }
        tran.commit();
        adapter.setData(listTitle);
    }

    private void selectFragment(int selcet) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tran = fm.beginTransaction();
        for (int i = 0,j = data.size(); i < j; i++) {
            InvoiceHelpFragment invoiceHelpFragment = fragmentList.get(i);
            Map<String, String> map = listTitle.get(i);
            if (selcet == i){
                tran.show(invoiceHelpFragment);
                map.put("isCheck", "1");
            }else {
                tran.hide(invoiceHelpFragment);
                map.put("isCheck", "0");
            }
        }
        tran.commit();
        adapter.setData(listTitle);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    helpModel = JSON.parseObject(returnData, HelpModel.class);
                    data = helpModel.getData();
                    // 初始化fragments
                    initFragments();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        selectFragment(position);
    }
}
