package com.yasn.purchase.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.adapter.InvoiceGridAdapter;
import com.yasn.purchase.fragment.InvoiceCommonFragment;
import com.yasn.purchase.fragment.InvoiceSpecialFragment;
import com.yasn.purchase.func.InvoiceHintTopBtnFunc;
import com.yasn.purchase.view.NoScrollGridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;

public class InvoiceActivity extends SimpleTopbarActivity implements AdapterView.OnItemClickListener {

    private NoScrollGridView gv_Invoice;
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
        gv_Invoice = (NoScrollGridView) findViewById(R.id.gv_Invoice);
        //实例化adapter
        adapter = new InvoiceGridAdapter(this);
        adapter.setData(invoiceType);
        gv_Invoice.setAdapter(adapter);
        //无发票
        tvInvoiceNo = (TextView) findViewById(R.id.tv_InvoiceNo);
        tvInvoiceNo.setOnClickListener(this);
        // 初始化fragments
        initFragments();
        initView();
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
            case R.id.tv_InvoiceNo:
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
        clickFragmentBtn(position + 1);
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
