package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.AuthorAreaAdapter;
import com.yasn.purchase.adapter.AuthorTypeAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.func.AuthorBackTopBtnFunc;
import com.yasn.purchase.model.AuthorMemberInfoModel;
import com.yasn.purchase.threelevelganged.BaseThreeActivity;
import com.yasn.purchase.threelevelganged.CityListAllModel;
import com.yasn.purchase.utils.AlignedTextUtils;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.help.HelpUtils;

public class AuthorActivity extends BaseThreeActivity
        implements AdapterView.OnItemSelectedListener,AuthorTypeAdapter.OnItemClickListener{

    private TextView tvAuthorAccountLeft, tvAuthorAccountRight, tvAuthorSpinnerName, tvAuthorNext;
    private EditText etAuthorShopName, etAuthorAddress, etAuthorShopNum;
    private Spinner spinnerAuthor;
    private AuthorAreaAdapter adapter;
    private TextView tvAuthorThree;
    private LinearLayout llcontentView;
    private AuthorTypeAdapter authTypeAdapter;
    private List<AuthorMemberInfoModel.DataBean.AuthenticationTypeBean> authenticationType;
    private String shopType;//经营类型
    private int shopTypeId;
    private String[] spinnerArr;//营业面积数据
    private String shopArea;//选择营业面积数据
    //是否试用期时间默认否
    private boolean isProbation = false;
    //试用期结束时间时间
    private long endTime;
    private NestedScrollView nsvAuthor;
    private int getMemberDataInfo = -1;
    private int listRegions = -1;
    @Override
    protected Class<?> getTopbarLeftFunc() {
        return AuthorBackTopBtnFunc.class;
    }

    @Override
    protected Object getTopbarTitle() {
        return "会员认证";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        //
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(100, Config.AUTHORMEMBERINFO, params);
        getThreeList();
        setUpViews();
    }

    private void getThreeList() {
        //城市列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("regionId", "0");
        okHttpGet(101, Config.REGIONLIST, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //根布局
        nsvAuthor = (NestedScrollView) findViewById(R.id.nsv_Author);
        nsvAuthor.setVisibility(View.GONE);
        tvAuthorAccountLeft = (TextView) findViewById(R.id.tv_AuthorAccount_Left);
        SpannableStringBuilder discountString = AlignedTextUtils.justifyString("帐号", 4);
        discountString.append(":");
        tvAuthorAccountLeft.setText(discountString);
        tvAuthorAccountRight = (TextView) findViewById(R.id.tv_AuthorAccount_Right);
        etAuthorShopName = (EditText) findViewById(R.id.et_AuthorShopName);
        //城市列表
        tvAuthorThree = (TextView) findViewById(R.id.tv_AuthorThree);
        tvAuthorThree.setOnClickListener(this);
        //收货地址
        etAuthorAddress = (EditText) findViewById(R.id.et_AuthorAddress);
        //营业面积
        spinnerAuthor = (Spinner) findViewById(R.id.spinner_Author);
        tvAuthorSpinnerName = (TextView) findViewById(R.id.tv_AuthorSpinnerName);
        //店铺数量
        etAuthorShopNum = (EditText) findViewById(R.id.et_AuthorShopNum);
        //下一步
        tvAuthorNext = (TextView) findViewById(R.id.tv_AuthorNext);
        tvAuthorNext.setOnClickListener(this);
        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        adapter = new AuthorAreaAdapter(this);
//        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，可自定义修改
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinnerArr = getResources().getStringArray(R.array.spinnerAuthor);
        adapter.setData(spinnerArr);
        spinnerAuthor.setAdapter(adapter);
//        spinnerAuthor.setSelection(0,false);
        //添加事件Spinner事件监听
        spinnerAuthor.setOnItemSelectedListener(this);
        //经营类型
        //RecyclerView设置manager
        RecyclerView rcAuthorType = (RecyclerView) findViewById(R.id.rc_AuthorType);
        LinearLayoutManager linearManage = new LinearLayoutManager(this);
        linearManage.setOrientation(LinearLayoutManager.VERTICAL);
        rcAuthorType.setLayoutManager(linearManage);
        authTypeAdapter = new AuthorTypeAdapter(this);
        authTypeAdapter.setOnItemClickListener(this);
        rcAuthorType.setAdapter(authTypeAdapter);
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                this, LinearLayoutManager.HORIZONTAL, 30, getResources().getColor(R.color.white));
        rcAuthorType.addItemDecoration(recyclerViewDecoration);
        //edittext外部焦点
        initEditTextFocus();
    }

    private void initEditTextFocus() {
        llcontentView = (LinearLayout) findViewById(R.id.ll_contentView);
        llcontentView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(null != AuthorActivity.this.getCurrentFocus()){
                    /**
                     * 点击空白位置 隐藏软键盘
                     */
                    llcontentView.setFocusable(true);
                    llcontentView.setFocusableInTouchMode(true);
                    llcontentView.requestFocus();
                    InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    return mInputMethodManager.hideSoftInputFromWindow(AuthorActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_AuthorThree:
                address_select.setVisibility(View.VISIBLE);
                isShouldHideInput();
                break;
            case R.id.btn_confirm:
                address_select.setVisibility(View.GONE);
                Log.e("TAG_省市区","省="+mCurrentProviceId+";市="+mCurrentCityId+";区="+mCurrentDistrictId);
                tvAuthorThree.setText(mCurrentProviceName+"-"+mCurrentCityName+"-"+mCurrentDistrictName);
                break;
            case R.id.btn_off:
                address_select.setVisibility(View.GONE);
                break;
            case R.id.tv_AuthorNext://下一步
                showAuthDialog();
                break;
        }
    }

    private void dialogAuthorNext() {
        String shopName = etAuthorShopName.getText().toString().trim();
        if (TextUtils.isEmpty(shopName)){
            ToastUtil.showToast("门店名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mCurrentProviceName)&&TextUtils.isEmpty(mCurrentCityName)&&TextUtils.isEmpty(mCurrentDistrictName)){
            ToastUtil.showToast("所在地区不能为空！");
            return;
        }
        String shopAddress = etAuthorAddress.getText().toString().trim();
        if (TextUtils.isEmpty(shopAddress)){
            ToastUtil.showToast("收货地址不能为空！");
            return;
        }
        if (TextUtils.isEmpty(shopType)){
            ToastUtil.showToast("经营类型不能为空！");
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        params.put("lvId", "6");
        params.put("shopName",shopName );
        params.put("province", mCurrentProviceName);//省
        params.put("city", mCurrentCityName);//市
        params.put("region", mCurrentDistrictName);//区
        params.put("provinceId", mCurrentProviceId);
        params.put("cityId",mCurrentCityId);
        params.put("regionId", mCurrentDistrictId);
        params.put("shopAddress", shopAddress);
        params.put("shopArea", shopArea == null ? "" : "100平以下");
        params.put("shopType", shopType);
        params.put("shopTypeId", String.valueOf(shopTypeId));
        params.put("shopNum", etAuthorShopNum.getText().toString().trim());
        okHttpPost(102, Config.AUTHORMEMBERSUBMIT, params);
    }

    private void isShouldHideInput(){
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llcontentView.getWindowToken(), 0);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode){
            case 100 :
                if (returnCode == 200){
                    getMemberDataInfo = 1;
                    AuthorMemberInfoModel authorMemberInfoModel = JSON.parseObject(returnData,AuthorMemberInfoModel.class);
                    AuthorMemberInfoModel.DataBean data = authorMemberInfoModel.getData();
                    if (data != null){
                        AuthorMemberInfoModel.DataBean.MemberDataBean memberData = data.getMemberData();
                        //服务器时间
                        long currentTime = data.getCurrentTime();
                        AuthorMemberInfoModel.DataBean.ProbationTimeBean probationTime = data.getProbationTime();
                        if (probationTime !=null){
                            //试用期开始时间
                            long startTime = probationTime.getStartTime();
                            //试用期结束时间时间
                            endTime = probationTime.getEndTime();
                            if (currentTime >= startTime && currentTime <= endTime){
                                isProbation = true;
                            }else {
                                isProbation = false;
                            }
                        }
                        if (memberData != null){
                            //帐号
                            String uName = memberData.getUname();
                            tvAuthorAccountRight.setText(uName == null ? "" : uName);
                            //门店全称
                            String shopName = memberData.getShopName();
                            etAuthorShopName.setText(shopName == null ? "" : shopName);
                            //所在地区
                            mCurrentProviceName = memberData.getProvince();//省
                            int provinceId = memberData.getProvinceId();
                            mCurrentProviceId = String.valueOf(provinceId);//省id

                            mCurrentCityName = memberData.getCity();//市
                            int cityId = memberData.getCityId();
                            mCurrentCityId = String.valueOf(cityId);//市Id

                            mCurrentDistrictName = memberData.getRegion();//区
                            int regionId = memberData.getRegionId();
                            mCurrentDistrictId = String.valueOf(regionId);//区Id
                            if (!TextUtils.isEmpty(mCurrentProviceName)){
                                tvAuthorThree.setText(mCurrentProviceName+"-"+mCurrentCityName+"-"+mCurrentDistrictName);
                            }
                            //收货地址
                            String shopAddress = memberData.getShopAddress();
                            if (!TextUtils.isEmpty(shopAddress)){
                                etAuthorAddress.setText(shopAddress);
                            }
                            //营业面积
                            String shopArea = memberData.getShopArea();
                            for (int i = 0; i < spinnerArr.length; i++) {
                               if ( spinnerArr[i].equals(shopArea)){
                                   spinnerAuthor.setSelection(i,false);
                                   break;
                               }
                            }
                            //经营类型
                            shopType = memberData.getShopType();
                            shopTypeId = memberData.getShopTypeId();
                            //店铺数量
                            int shopNum = memberData.getShopNum();
                            etAuthorShopNum.setText(String.valueOf(shopNum == 0 ? 1 : shopNum ));
                        }
                        //经营类型
                        authenticationType = data.getAuthenticationType();
                        if (authenticationType !=null && authenticationType.size() > 0){
                            for (int i = 0; i < authenticationType.size(); i++) {
                                AuthorMemberInfoModel.DataBean.AuthenticationTypeBean authenticationTypeBean = authenticationType.get(i);
                                int id = authenticationTypeBean.getId();
                                if (id == shopTypeId) {
                                    shopType = authenticationTypeBean.getType();
                                    shopTypeId = authenticationTypeBean.getId();
                                    authenticationTypeBean.setChecked(true);
                                } else {
                                    authenticationTypeBean.setChecked(false);
                                }
                            }
                            authTypeAdapter.setData(authenticationType);
                            authTypeAdapter.notifyDataSetChanged();
                        }
                    }
                    if (listRegions > 0){
                        nsvAuthor.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 101:
                if (returnCode == 200){
                    listRegions = 1;
                    CityListAllModel cityallinfo = JSON.parseObject(returnData, CityListAllModel.class);
                    List<CityListAllModel.ListRegionsBean> listRegions = cityallinfo.getListRegions();
                    setUpData(listRegions);
                    if (getMemberDataInfo > 0){
                        nsvAuthor.setVisibility(View.VISIBLE);
                    }
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 102://下一步
                if (returnCode == 200 ){
                    Map<String, Object> params = new HashMap<String, Object>();
                    if (token != null && !"".equals(token)) {
                        params.put("access_token", token);
                    } else if (resetToken != null && !"".equals(resetToken)) {
                        params.put("access_token", resetToken);
                    }
                    okHttpGet(103, Config.AUTHORMEMBERISAUDIT, params);
//                    startProbationActivity();
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 103:
                if (returnCode == 200){//lv_id = 1;正常流程
                    if (isProbation) {//试用期期间
                        startProbationActivity();
                    } else {//上传图片
                       startActivity(new Intent(this,AuthorImageActivity.class));
                    }
                }else if (returnCode == 9){// 状态9表示crm同步已成功, 自动认证,lv_id=6
                    //清除首页认证成功弹窗提示
                }else { // 400
                    if (isProbation) {//试用期期间
                        startProbationActivity();
                    } else {//会员信息不存在
                        finish();
                    }
                }
                break;
        }
    }
    private void startProbationActivity(){
        String dateToHms = HelpUtils.getDateToHms(endTime);
        Intent intent = new Intent(this,ProbationActivity.class);
        intent.putExtra("endTime",dateToHms);
        startActivity(intent);
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
    //spinnerIten
//    private boolean isFirst = true;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//        tvAuthorSpinnerName.setText();
        Log.e("TAG_Spinner","onItemSelected");
        shopArea = spinnerArr[position];
//        if (true){
//            isFirst = false;
//        }else {
//            tvAuthorSpinnerName.setText(shopArea);
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e("TAG_Spinner","onNothingSelected");
    }

    @Override
    public void onItemClick(View view, int position) {
        for (int i = 0; i < authenticationType.size(); i++) {
            AuthorMemberInfoModel.DataBean.AuthenticationTypeBean authenticationTypeBean = authenticationType.get(i);
            if (i == position) {
                shopType = authenticationTypeBean.getType();
                shopTypeId = authenticationTypeBean.getId();
                authenticationTypeBean.setChecked(true);
            } else {
                authenticationTypeBean.setChecked(false);
            }
        }
        authTypeAdapter.notifyDataSetChanged();
    }
    //提交信息
    protected AlertDialog submitOneDialog;
    private void showAuthDialog() {
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_submitauthorone, null);
        TextView tvHint = (TextView) serviceView.findViewById(R.id.tv_AuthorDialogSubmit);
        String formatHint = String.format(tvHint.getText().toString(), mCurrentProviceName,mCurrentProviceName);
        SpannableStringBuilder span = new SpannableStringBuilder(formatHint);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,R.color.orange)), 14,14+mCurrentProviceName.length() ,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,R.color.orange)), 27+mCurrentProviceName.length(),27+mCurrentProviceName.length()+mCurrentProviceName.length() ,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvHint.setText(span);

        TextView okbtn = (TextView) serviceView.findViewById(R.id.tv_tv_AuthorDialogSubmitOk);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAuthorNext();
                submitOneDialog.dismiss();
            }
        });
        TextView refuse = (TextView) serviceView.findViewById(R.id.tv_tv_AuthorDialogSubmitCancel);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOneDialog.dismiss();
            }
        });
        Activity activity = AuthorActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        submitOneDialog = builder.create();
        submitOneDialog.setCancelable(false);
        submitOneDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return false; //默认返回 false
                }
            }
        });
        submitOneDialog.show();
        submitOneDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }
}
