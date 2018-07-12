package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.AddressUpdataActivity;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.AddressAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.AddressModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class AddressActivity extends SimpleTopbarActivity implements AddressAdapter.OnAddressClickListener
        ,SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView rcAddress;
    private AddressAdapter adapter;
    private int addrId;
    private List<AddressModel> addressModels;
    private MultiSwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected Object getTopbarTitle() {
        return "收获地址";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        addrId = getIntent().getIntExtra("addrId",0);
        //新增收获地址
        TextView tvAddressAdd = (TextView) findViewById(R.id.tv_AddressAdd);
        tvAddressAdd.setOnClickListener(this);
        rcAddress = (RecyclerView) findViewById(R.id.rc_Address);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rcAddress.setLayoutManager(linearLayoutManager);
        adapter = new AddressAdapter(this);
        adapter.setOnItemClickListener(this);
        //自定义的分隔线
        //rc线
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                this, LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.line_gray));
        rcAddress.addItemDecoration(recyclerViewDecoration);
        rcAddress.setAdapter(adapter);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initSwipeRefreshLayout();
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            okHttpGet(100, Config.ADDRESS, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            okHttpGet(100, Config.ADDRESS, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
    }
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        afterSetContentView();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_AddressAdd://新增
                Intent intent = new Intent(this, AddressUpdataActivity.class);
                intent.putExtra("type",0);
                startActivityForResult(intent,10002);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10002:
                if (resultCode == 1){//保存
                    afterSetContentView();
                }
                break;

        }
    }

    private String addressSelcet;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100://初始化數據
                if (returnCode == 200) {
                    addressModels = JSON.parseArray(returnData, AddressModel.class);
                    adapter.setData(addressModels,addrId);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case 101: //本次收货地址
                if (returnCode == 200){
                    Intent intent = new Intent();
                    intent.putExtra("addressId",addrIdSelcet);
                    intent.putExtra("address", this.addressSelcet); //将计算的值回传回去
                    intent.putExtra("addressName", this.addressName); //将计算的值回传回去
                    intent.putExtra("addressMobile", this.addressMobile); //将计算的值回传回去
                    setResult(1,intent);
                    finish();
                }
                break;
            case 102: //默认收货地址
                if (returnCode == 200){
                    afterSetContentView();
                }
                ToastUtil.showToast(returnMsg);
                break;
            case 103: //删除
                if (returnCode == 200){
                    afterSetContentView();
                }
                ToastUtil.showToast(returnMsg);
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
    //本次收货地址
    private int addrIdSelcet;
    private String addressName;
    private String addressMobile;
    @Override
    public void OnItemClick(View view, int position) {
        AddressModel addressModel = addressModels.get(position);
        addrIdSelcet = addressModel.getAddrId();
        //姓名
        addressName = addressModel.getName();
        //电话
        addressMobile = addressModel.getMobile();
        //地址
        String province = addressModel.getProvince();
        String city = addressModel.getCity();
        String region = addressModel.getRegion();
        String addr = addressModel.getAddr();
        if (TextUtils.isEmpty(addr)){
            addressSelcet = province+"-"+city+"-"+region;
        }else {
            addressSelcet = province+"-"+city+"-"+region+"-"+addr;
        }

        Map<String, String> params = new HashMap<String, String>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            okHttpPostHeader(101, Config.ADDRESSNOW+addrId, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            okHttpPostHeader(101, Config.ADDRESSNOW+addrId, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
    }
    //默认收货地址
    @Override
    public void OnItemSettingAddressClick(View view, int position) {
        AddressModel addressModel = addressModels.get(position);
        int addrId = addressModel.getAddrId();
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
            okHttpGet(102, Config.ADDRESSDEFAULT+addrId, params);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
            okHttpGet(102, Config.ADDRESSDEFAULT+addrId, params);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
        }
    }
    //编辑
    @Override
    public void OnItemUpdataAddressClick(View view, int position) {
        AddressModel addressModel = addressModels.get(position);
        Intent intent = new Intent(this, AddressUpdataActivity.class);
        intent.putExtra("type",1);
        int addrId = addressModel.getAddrId();
        intent.putExtra("addrId",addrId);
        String name = addressModel.getName();
        intent.putExtra("name",name);
        String mobile = addressModel.getMobile();
        intent.putExtra("mobile",mobile);
        //地址
        String province = addressModel.getProvince();
        String city = addressModel.getCity();
        String region = addressModel.getRegion();
        intent.putExtra("region",province+"-"+city+"-"+region);

        String addr = addressModel.getAddr();
        intent.putExtra("address",addr);
        //默认选中0或1  1：默认选中
        int defAddr = addressModel.getDefAddr();
        intent.putExtra("defAddr",defAddr);

        startActivityForResult(intent,10002);
    }

    //删除
    @Override
    public void OnItemDeleteAddressClick(View view, int position) {
        AddressModel addressModel = addressModels.get(position);
        int addrId = addressModel.getAddrId();
        showDeleteDialog(addrId);
    }
    protected AlertDialog deleteNotifyDialog;

    private void showDeleteDialog(final int addrId) {

        LayoutInflater factor = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_shopcardelete, null);
        TextView tvDelete = (TextView) serviceView.findViewById(R.id.tv_Delete);
        tvDelete.setText("确认要删除此后收货地址吗？");
        TextView agree = (TextView) serviceView.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotifyDialog.dismiss();
                Map<String, Object> params = new HashMap<String, Object>();
                if (token != null && !"".equals(token)) {
//            params.put("access_token", token);
                    okHttpGet(103,  Config.ADDRESSNOWDELETE+addrId, params);
                } else if (resetToken != null && !"".equals(resetToken)) {
//            params.put("access_token", resetToken);
                    okHttpGet(103, Config.ADDRESSNOWDELETE+addrId, params);
                } else {
                    ToastUtil.showToast("登录过期，请重新登录");
                }
            }
        });
        TextView refuse = (TextView) serviceView.findViewById(R.id.refuse);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotifyDialog.dismiss();
            }
        });
        Activity activity = AddressActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        deleteNotifyDialog = builder.create();
        deleteNotifyDialog.setCancelable(false);
        deleteNotifyDialog.setCanceledOnTouchOutside(false);
        deleteNotifyDialog.show();
        deleteNotifyDialog.setContentView(serviceView);
    }

    @Override
    public void onRefresh() {
        afterSetContentView();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };
        new Timer().schedule(timerTask, 2000);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
}
