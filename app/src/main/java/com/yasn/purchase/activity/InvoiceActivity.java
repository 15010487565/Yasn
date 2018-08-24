package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.adapter.InvoiceGridAdapter;
import com.yasn.purchase.fragment.InvoiceCommonFragment;
import com.yasn.purchase.fragment.InvoiceSpecialFragment;
import com.yasn.purchase.func.InvoiceHintTopBtnFunc;
import com.yasn.purchase.view.NoScrollGridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;

import static com.yasn.purchase.R.id.tv_InvoiceNo;

public class InvoiceActivity extends SimpleTopbarActivity implements AdapterView.OnItemClickListener {

    private NoScrollGridView gvInvoice;
    private InvoiceGridAdapter adapter;
    private String[] invoiceType = {"不开发票", "普通发票", "专用发票"};
    private static Class<?> rightFuncArray[] = {InvoiceHintTopBtnFunc.class};
    public static Class<?> fragmentArray[] = {
            InvoiceCommonFragment.class,//普通发票
            InvoiceSpecialFragment.class,//专用发票
    };
    /**
     * fragment列表
     */
    private List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
    private TextView tvInvoiceNo;
    private FrameLayout frame_content;
    private List<Map<String, String>> list = new ArrayList<>();

    @Override
    protected Object getTopbarTitle() {
        return "发票";
    }

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        gvInvoice = (NoScrollGridView) findViewById(R.id.gv_Invoice);
        //实例化adapter
        adapter = new InvoiceGridAdapter(this);
        for (int i = 0; i < invoiceType.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", invoiceType[i]);
            if (i == 0) {
                map.put("isCheck", "1");
            } else {
                map.put("isCheck", "0");
            }
            list.add(map);
        }
        adapter.setData(list);
        gvInvoice.setAdapter(adapter);
        gvInvoice.setOnItemClickListener(this);
        //无发票
        tvInvoiceNo = (TextView) findViewById(R.id.tv_InvoiceNo);
        tvInvoiceNo.setOnClickListener(this);
        //fragment预留布局
        frame_content = (FrameLayout) findViewById(R.id.frame_content);
        // 初始化fragments
        initFragments();
        initView();
        frame_content.setVisibility(View.GONE);
        tvInvoiceNo.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化fragments
     */
    protected void initFragments() {
        // 初始化fragment
        for (int i = 0; i < fragmentArray.length; i++) {
            BaseFragment fragment = null;
            try {
                fragment = (BaseFragment) fragmentArray[i].newInstance();
                fragment.setActivity(this);
            } catch (Exception e) {
            }
            fragmentList.add(fragment);
        }
    }

    private void initView() {

        // 为布局添加fragment,开启事物
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tran = fm.beginTransaction();

        //R.id.relative为布局
        tran.add(R.id.frame_content, fragmentList.get(0), "commonInvoice").show(fragmentList.get(0))
                .add(R.id.frame_content, fragmentList.get(1), "specialInvoice").hide(fragmentList.get(1));

        tran.commit();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case tv_InvoiceNo:
                Intent intent = new Intent();
                intent.putExtra("invoice", "不开发票"); //将计算的值回传回去
                setResult(0, intent);
                finish();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (list != null && list.size() > 0) {
            list.clear();
        }
        for (int i = 0; i < invoiceType.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", invoiceType[i]);
            if (i == position) {
                map.put("isCheck", "1");
            } else {
                map.put("isCheck", "0");
            }
            list.add(map);
        }
        adapter.setData(list);
        if (position == 0) {
            frame_content.setVisibility(View.GONE);
            tvInvoiceNo.setVisibility(View.VISIBLE);
        } else {
            frame_content.setVisibility(View.VISIBLE);
            tvInvoiceNo.setVisibility(View.GONE);
            clickFragmentBtn(position - 1);
        }
    }

    private void clickFragmentBtn(int tabIndex) {
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();

        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == tabIndex) {
                fragmentTransaction.show(fragmentList.get(i));
            } else {
                fragmentTransaction.hide(fragmentList.get(i));
            }
        }
        fragmentTransaction.commit();
    }
}
