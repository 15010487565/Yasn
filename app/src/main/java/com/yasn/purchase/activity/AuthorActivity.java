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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.AuthorTypeAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.func.AuthorBackTopBtnFunc;
import com.yasn.purchase.model.AuthorMemberInfoModel;
import com.yasn.purchase.threelevelganged.BaseThreeActivity;
import com.yasn.purchase.threelevelganged.CityListAllModel;
import com.yasn.purchase.utils.AlignedTextUtils;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.CustomSpinner;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.help.HelpUtils;

import static com.yasn.purchase.R.array.spinnerAuthor;

public class AuthorActivity extends BaseThreeActivity
        implements  AuthorTypeAdapter.OnItemClickListener {

    private TextView tvAuthorAccountLeft, tvAuthorAccountRight, tvAuthorNext;
    private EditText etAuthorShopName, etAuthorAddress, etAuthorShopNum;
    private LinearLayout llAuthorSinner;
    private CustomSpinner levelSpinner;
    private TextView tvAuthorThree;
    private LinearLayout llcontentView, llAuthorThree;
    private AuthorTypeAdapter authTypeAdapter;
    private List<AuthorMemberInfoModel.DataBean.AuthenticationTypeBean> authenticationType;
    private String shopType;//经营类型
    private int shopTypeId;
    private String[] spinnerArr;//营业面积数据
//    private String shopArea;//选择营业面积数据
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
        llAuthorThree = (LinearLayout) findViewById(R.id.ll_AuthorThree);
        llAuthorThree.setOnClickListener(this);
        tvAuthorThree = (TextView) findViewById(R.id.tv_AuthorThree);
        //收货地址
        etAuthorAddress = (EditText) findViewById(R.id.et_AuthorAddress);
        //营业面积
        llAuthorSinner = (LinearLayout) findViewById(R.id.ll_AuthorSinner);
//        spinnerAuthor = (Spinner) findViewById(R.id.spinner_Author);
//        tvAuthorSpinnerName = (TextView) findViewById(R.id.tv_AuthorSpinnerName);
//        tvAuthorSpinnerName.setVisibility(View.VISIBLE);
        //店铺数量
        etAuthorShopNum = (EditText) findViewById(R.id.et_AuthorShopNum);
        //下一步
        tvAuthorNext = (TextView) findViewById(R.id.tv_AuthorNext);
        tvAuthorNext.setOnClickListener(this);
        //将adapter 添加到spinner中
        spinnerArr = getResources().getStringArray(spinnerAuthor);
        List<String> levelList = Arrays.asList(spinnerArr);
        levelSpinner = new CustomSpinner(this, "请选择营业面积", levelList);
        llAuthorSinner.addView(levelSpinner);
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
                if (null != AuthorActivity.this.getCurrentFocus()) {
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
        switch (v.getId()) {
            case R.id.ll_AuthorThree:
                if (TextUtils.isEmpty(mCurrentProviceName)) {
                    getThreeList();
                } else {
                    address_select.setVisibility(View.VISIBLE);
                    isShouldHideInput();
                }
                break;
            case R.id.btn_confirm:
                address_select.setVisibility(View.GONE);
                province = mCurrentProviceName;
                provinceId = mCurrentProviceId;
                city = mCurrentCityName;
                cityId = mCurrentCityId;
                region = mCurrentDistrictName;
                regionId = mCurrentDistrictId;
                Log.e("TAG_省市区", "省=" + mCurrentProviceId + ";市=" + mCurrentCityId + ";区=" + mCurrentDistrictId);
                tvAuthorThree.setText(mCurrentProviceName + "-" + mCurrentCityName + "-" + mCurrentDistrictName);
                break;
            case R.id.btn_off:
                address_select.setVisibility(View.GONE);
                break;
            case R.id.tv_AuthorNext://下一步
                shopName = etAuthorShopName.getText().toString().trim();
                if (TextUtils.isEmpty(shopName)) {
                    ToastUtil.showToast("门店名称不能为空！");
                    return;
                }
                Log.e("TAG_省市区初始化", "省=" + mCurrentProviceId + ";市=" + mCurrentCityId + ";区=" + mCurrentDistrictId);
                Log.e("TAG_省市区选择", "省=" + province + ";市=" + city + ";区=" + region);
                if (TextUtils.isEmpty(province) && TextUtils.isEmpty(city) && TextUtils.isEmpty(region)) {
                    ToastUtil.showToast("所在地区不能为空！");
                    return;
                }
                shopAddress = etAuthorAddress.getText().toString().trim();
                if (TextUtils.isEmpty(shopAddress)) {
                    ToastUtil.showToast("收货地址不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(shopType)) {
                    ToastUtil.showToast("经营类型不能为空！");
                    return;
                }
                shopNum = etAuthorShopNum.getText().toString().trim();
                if (TextUtils.isEmpty(shopNum)) {
                    ToastUtil.showToast("请输入店铺数量");
                    return;
                }
                if (Double.valueOf(shopNum) <= 0) {
                    ToastUtil.showToast("店铺数量必须大于0");
                    return;
                }
                String showText = levelSpinner.getShowText();
                if (TextUtils.isEmpty(showText)) {
                    ToastUtil.showToast("请选择经营面积");
                    return;
                }
                showAuthDialog();
                break;
        }
    }

    String shopName;
    String shopAddress;
    String shopNum;

    private void dialogAuthorNext() {
        Map<String, String> params = new HashMap<String, String>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        params.put("lvId", "6");
        params.put("shopName", shopName);
        params.put("province", province);//省
        params.put("city", city);//市
        params.put("region", region);//区
        params.put("provinceId", provinceId);
        params.put("cityId", cityId);
        params.put("regionId", regionId);
        params.put("shopAddress", shopAddress);
        params.put("shopArea", levelSpinner.getShowText());
        params.put("shopType", shopType);
        params.put("shopTypeId", String.valueOf(shopTypeId));
        params.put("shopNum", shopNum);
        okHttpPost(102, Config.AUTHORMEMBERSUBMIT, params);
    }

    public void isShouldHideInput() {
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llcontentView.getWindowToken(), 0);
    }

    String province;
    String provinceId;
    String city;
    String cityId;
    String region;
    String regionId;

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    getMemberDataInfo = 1;
                    AuthorMemberInfoModel authorMemberInfoModel = JSON.parseObject(returnData, AuthorMemberInfoModel.class);
                    AuthorMemberInfoModel.DataBean data = authorMemberInfoModel.getData();
                    if (data != null) {
                        AuthorMemberInfoModel.DataBean.MemberDataBean memberData = data.getMemberData();
                        //服务器时间
                        long currentTime = data.getCurrentTime();
                        AuthorMemberInfoModel.DataBean.ProbationTimeBean probationTime = data.getProbationTime();
                        if (probationTime != null) {
                            //试用期开始时间
                            long startTime = probationTime.getStartTime();
                            //试用期结束时间时间
                            endTime = probationTime.getEndTime();
                            if (currentTime >= startTime && currentTime <= endTime) {
                                isProbation = true;
                            } else {
                                isProbation = false;
                            }
                        }
                        if (memberData != null) {
                            //帐号
                            String uName = memberData.getUname();
                            tvAuthorAccountRight.setText(uName == null ? "" : uName);
                            //门店全称
                            String shopName = memberData.getShopName();
                            etAuthorShopName.setText(shopName == null ? "" : shopName);
                            //所在地区
                            province = memberData.getProvince();//省
                            provinceId = String.valueOf(memberData.getProvinceId());//省id

                            city = memberData.getCity();//市
                            cityId = String.valueOf(memberData.getCityId());//市Id

                            region = memberData.getRegion();//区
                            regionId = String.valueOf(memberData.getRegionId());//区Id
                            if (!TextUtils.isEmpty(province)) {
                                tvAuthorThree.setText(province + "-" + city + "-" + region);
                            }
                            //收货地址
                            String shopAddress = memberData.getShopAddress();
                            if (!TextUtils.isEmpty(shopAddress)) {
                                etAuthorAddress.setText(shopAddress);
                            }
                            //营业面积
                            String shopArea = memberData.getShopArea();
                            for (int i = 0; i < spinnerArr.length; i++) {
                                if (spinnerArr[i].equals(shopArea)) {
                                   levelSpinner.setSelectionItem(i);
                                    break;
                                }
                            }
                            //经营类型
                            shopType = memberData.getShopType();
                            shopTypeId = memberData.getShopTypeId();
                            //店铺数量
                            int shopNum = memberData.getShopNum();
                            etAuthorShopNum.setText(String.valueOf(shopNum == 0 ? 1 : shopNum));
                        }
                        //经营类型
                        authenticationType = data.getAuthenticationType();
                        if (authenticationType != null && authenticationType.size() > 0) {
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
                    if (listRegions > 0) {
                        nsvAuthor.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 101:
                if (returnCode == 200) {
                    listRegions = 1;
                    CityListAllModel cityallinfo = JSON.parseObject(returnData, CityListAllModel.class);
                    List<CityListAllModel.ListRegionsBean> listRegions = cityallinfo.getListRegions();
                    if (listRegions != null && listRegions.size() > 0) {
                        setUpData(listRegions);
                    }
                    if (getMemberDataInfo > 0) {
                        nsvAuthor.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 102://下一步
                if (returnCode == 200) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    if (token != null && !"".equals(token)) {
                        params.put("access_token", token);
                    } else if (resetToken != null && !"".equals(resetToken)) {
                        params.put("access_token", resetToken);
                    }
                    okHttpGet(103, Config.AUTHORMEMBERISAUDIT, params);
//                    startProbationActivity();
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 103:
                if (returnCode == 200) {//lv_id = 1;正常流程
                    if (isProbation) {//试用期期间
                        startProbationActivity();
                    } else {//上传图片
                        startActivity(new Intent(this, AuthorImageActivity.class));
                    }
                } else if (returnCode == 9) {// 状态9表示crm同步已成功, 自动认证,lv_id=6
                    //清除首页认证成功弹窗提示
                } else { // 400
                    if (isProbation) {//试用期期间
                        startProbationActivity();
                    } else {//会员信息不存在
                        finish();
                    }
                }
                break;
        }
    }

    private void startProbationActivity() {
        String dateToHms = HelpUtils.getDateToHms(endTime);
        Intent intent = new Intent(this, ProbationActivity.class);
        intent.putExtra("endTime", dateToHms);
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

//    //spinnerIten
//    private boolean isFirst = true;
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
////        tvAuthorSpinnerName.setText();
//        Log.e("TAG_Spinner", "onItemSelected" + position);
//        if (isFirst) {
//            tvAuthorSpinnerName.setVisibility(View.VISIBLE);
//            isFirst = false;
//            try {
//                //以下三行代码是解决问题所在
//                Field field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
//                field.setAccessible(true);    //设置mOldSelectedPosition可访问
//                field.setInt(spinnerAuthor, AdapterView.INVALID_POSITION); //设置mOldSelectedPosition的值
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            shopArea = spinnerArr[position];
//            tvAuthorSpinnerName.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//        Log.e("TAG_Spinner", "onNothingSelected");
//    }

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
        String formatHint = String.format(tvHint.getText().toString(), (TextUtils.isEmpty(province) ? mCurrentProviceName : province)
                , (TextUtils.isEmpty(province) ? "" : province));
        SpannableStringBuilder span = new SpannableStringBuilder(formatHint);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange)), 14, 14 + province.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.orange)), 27 + province.length(), 27 + province.length() + province.length(),
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
