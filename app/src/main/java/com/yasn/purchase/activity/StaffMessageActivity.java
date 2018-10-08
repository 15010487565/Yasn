package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;
import com.yasn.purchase.adapter.StaffMsgAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.StaffMessageModel;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffMessageActivity extends BaseYasnActivity implements StaffMsgAdapter.OnInviteStatusListener {

    protected LinearLayout llStaffMessage,llError;
    private TextView tvStaffMessageCreate;
    private RecyclerView rcStaffMessage;
    private StaffMsgAdapter adapter;
    //会员管理数据
    private List<StaffMessageModel.DataBean> data;
    //修改权限
    protected AlertDialog updataAuthNotifyDialog;
    //删除员工
    protected AlertDialog delAuthNotifyDialog;

    @Override
    protected Object getTopbarTitle() {
        return R.string.staff_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_message);
        getStaffMessage();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getStaffMessage();
    }

    private void getStaffMessage(){
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(100, Config.STAFFMESSAGE, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        llError = (LinearLayout) findViewById(R.id.ll_Error);
        llError.setVisibility(View.GONE);
        llStaffMessage = (LinearLayout) findViewById(R.id.ll_StaffMessage);
        llStaffMessage.setVisibility(View.VISIBLE);

        rcStaffMessage = (RecyclerView) findViewById(R.id.rc_StaffMessage);
        LinearLayoutManager nullLinearLayoutManager = new LinearLayoutManager(this);
        nullLinearLayoutManager.setAutoMeasureEnabled(true);
        rcStaffMessage.setLayoutManager(nullLinearLayoutManager);
        adapter = new StaffMsgAdapter(this);
        adapter.setOnUpdataClickListener(this);
        rcStaffMessage.setAdapter(adapter);
        //新建员工
        tvStaffMessageCreate = (TextView) findViewById(R.id.tv_StaffMessageCreate);
        tvStaffMessageCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_StaffMessageCreate:
                startActivity(new Intent(StaffMessageActivity.this,StaffCreateActivity.class));
                break;
            case R.id.tv_CancelDialogUpdata://取消修改
                updataAuthNotifyDialog.dismiss();
                break;
            case R.id.tv_OkDialogUpdata://修改
                startActivity(new Intent(StaffMessageActivity.this,StaffCreateActivity.class));
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {
            case 100:
                //文档 Code = 0 表示成功
                Log.e("TAG_员工管理","returnCode="+returnCode);
                if (returnCode == 0){
                    StaffMessageModel staffMessageModel = JSON.parseObject(returnData,StaffMessageModel.class);
                    data = staffMessageModel.getData();
                    if ( data == null || data.size() == 0 ){
                        llError.setVisibility(View.VISIBLE);
                        llStaffMessage.setVisibility(View.GONE);
                    }else {
                        llError.setVisibility(View.GONE);
                        llStaffMessage.setVisibility(View.VISIBLE);
                        StaffMessageModel.DataBean model= new StaffMessageModel.DataBean();
                        model.setEmployeeAuth("-1");
                        model.setInviteStatus(-1);
                        model.setPhone("手机号");
                        data.add(0,model);
                        adapter.setData(data);
                    }
                }else {
                    llError.setVisibility(View.VISIBLE);
                    llStaffMessage.setVisibility(View.GONE);
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101://删除员工
                if (returnCode == 200){
                    getStaffMessage();
                }
                ToastUtil.showToast(returnMsg);
                break;
            case 102://修改员工
                if (returnCode == 200){
                    getStaffMessage();
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

    @Override
    public void OnUpdataClick(View view, int position) {
        Log.e("TAG_员工管理","修改="+position);
        StaffMessageModel.DataBean dataBean = data.get(position);
        int id = dataBean.getId();
        String employeeAuth = dataBean.getEmployeeAuth();
        showUpdataAuthDialog( employeeAuth , id );
    }

    @Override
    public void OnDelClick(View view, int position) {
        Log.e("TAG_员工管理","删除="+position);
        StaffMessageModel.DataBean dataBean = data.get(position);
        int id = dataBean.getId();
        showDelAuthDialog(Config.STAFFMESSAGEDELETE + id);
    }

    /**
     *
     * @param employeeAuth 1:采购 ,2:财务 1,2采购+财务
     * @param id
     */
    private void showUpdataAuthDialog(String employeeAuth , final int id) {
        if (updataAuthNotifyDialog !=null && updataAuthNotifyDialog.isShowing()){
            return;
        }
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_updatastaffmessage, null);
        //采购部
        final CheckBox  cbPurchase = (CheckBox) serviceView.findViewById(R.id.cb_DialogPurchase);
//        cbPurchase.setOnCheckedChangeListener(this);
        //财务部
        final CheckBox cbFinance = (CheckBox) serviceView.findViewById(R.id.cb_DialogFinance);
        if ("1".equals(employeeAuth)){
            cbPurchase.setChecked(true);
        }else if ("2".equals(employeeAuth)){
            cbFinance.setChecked(true);
        }else if ("1,2".equals(employeeAuth)){
            cbPurchase.setChecked(true);
            cbFinance.setChecked(true);
        }
        TextView tvCancel = (TextView) serviceView.findViewById(R.id.tv_CancelDialogUpdata);
        tvCancel.setOnClickListener(this);
        TextView okbtn = (TextView) serviceView.findViewById(R.id.tv_OkDialogUpdata);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String auth = null;
                //采购部
                boolean checked = cbPurchase.isChecked();
                //财务部
                boolean checked1 = cbFinance.isChecked();
                if (!checked && !checked1){
                    ToastUtil.showToast("请选择员工角色！");
                    return;
                }else {
                    if (checked && checked1) {
                        auth = "1,2";
                    }else if (checked){
                        auth = "1";
                    }else {
                        auth = "2";
                    }
                }
                Map<String, Object> params = new HashMap<String, Object>();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                }
                okHttpGet(102 , Config.STAFFMESSAGEUPDATE + auth + "/" + id , params);
                updataAuthNotifyDialog.dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(StaffMessageActivity.this);
        updataAuthNotifyDialog = builder.create();
        updataAuthNotifyDialog.show();
        updataAuthNotifyDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }

    private void showDelAuthDialog(final String url) {
        if (delAuthNotifyDialog !=null && delAuthNotifyDialog.isShowing()){
            return;
        }
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_delstaffmessage, null);
        TextView tvCancel = (TextView) serviceView.findViewById(R.id.tv_CancelStaffMessage);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delAuthNotifyDialog.dismiss();
            }
        });
        TextView okbtn = (TextView) serviceView.findViewById(R.id.tv_OkStaffMessage);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<String, Object>();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                }
                okHttpGet(101 , url , params);
                delAuthNotifyDialog.dismiss();
            }
        });
        Activity activity = StaffMessageActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        delAuthNotifyDialog = builder.create();
        delAuthNotifyDialog.show();
        delAuthNotifyDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }
}
