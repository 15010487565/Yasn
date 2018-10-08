package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.threelevelganged.BaseThreeActivity;
import com.yasn.purchase.threelevelganged.CityListAllModel;
import com.yasn.purchase.utils.AlignedTextUtils;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static com.yasn.purchase.R.id.et_AddreddUpdataName;


public class AddressUpdataActivity extends BaseThreeActivity {

    private TextView tvName, tvMobile, tvRegion, tvAddressCancel, tvAddressSave;
    private EditText etName, etMobile, etAddr;
    private LinearLayout llRegion, llcontentView;
    private RelativeLayout rlSelect;
    private ImageView ivSelect;
    String province;
    String city;
    String region;
    String provinceId;
    String cityId;
    String regionId;

    int defAddr;//默认
    int type; // 0 新增  1 编辑
    int addrId;//编辑当前收货地址id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_updata);
    }

    private void initView() {
        //收货人
        tvName = (TextView) findViewById(R.id.tv_AddreddUpdataName);
        SpannableStringBuilder retailName = AlignedTextUtils.justifyString("收货人", 4);
        retailName.append("：");
        tvName.setText(retailName);
        etName = (EditText) findViewById(et_AddreddUpdataName);
        etName.setOnFocusChangeListener(this);
        //联系电话
        tvMobile = (TextView) findViewById(R.id.tv_AddreddUpdataMobile);
        SpannableStringBuilder retailMobile = AlignedTextUtils.justifyString("手机号", 4);
        retailMobile.append("：");
        tvMobile.setText(retailMobile);
        etMobile = (EditText) findViewById(R.id.et_AddreddUpdataMobile);
        etMobile.setOnFocusChangeListener(this);
        //所在地区
        llRegion = (LinearLayout) findViewById(R.id.ll_AddreddUpdataRegion);
        llRegion.setOnClickListener(this);
        tvRegion = (TextView) findViewById(R.id.tv_AddreddUpdataRegion);
        //详细地址
        etAddr = (EditText) findViewById(R.id.et_AddreddUpdataAddr);
        etAddr.setOnFocusChangeListener(this);
        rlSelect = (RelativeLayout) findViewById(R.id.rl_AddreddSelect);
        rlSelect.setOnClickListener(this);
        ivSelect = (ImageView) findViewById(R.id.iv_AddreddSelect);
        tvAddressCancel = (TextView) findViewById(R.id.tv_AddressCancel);
        tvAddressCancel.setOnClickListener(this);
        tvAddressSave = (TextView) findViewById(R.id.tv_AddressSave);
        tvAddressSave.setOnClickListener(this);
        llcontentView = (LinearLayout) findViewById(R.id.ll_contentView);
        llcontentView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("TAG_===", "点击1");
                if (null != AddressUpdataActivity.this.getCurrentFocus()) {
                    Log.e("TAG_===", "点击2");
                    /**
                     * 点击空白位置 隐藏软键盘
                     */
                    llcontentView.setFocusable(true);
                    llcontentView.setFocusableInTouchMode(true);
                    llcontentView.requestFocus();
                    InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    return mInputMethodManager.hideSoftInputFromWindow(AddressUpdataActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    private void isShouldHideInput() {
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llcontentView.getWindowToken(), 0);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        initView();
        Intent intent = getIntent();
        // 0 新增  1 编辑
        type = intent.getIntExtra("type", 0);
        if (type != 0) {//编辑
            String name = intent.getStringExtra("name");
            etName.setText(name);
            //联系电话
            String mobile = intent.getStringExtra("mobile");
            etMobile.setText(mobile);
            //地址
            province = intent.getStringExtra("province");
            city = intent.getStringExtra("city");
            region = intent.getStringExtra("region");
            provinceId = intent.getStringExtra("provinceId");
            cityId = intent.getStringExtra("cityId");
            regionId = intent.getStringExtra("regionId");
            tvRegion.setText(province + "-" + city + "-" + region);
            //详细地址
            String addr = intent.getStringExtra("address");
            etAddr.setText(addr);
            addrId = intent.getIntExtra("addrId", 0);
        }
        defAddr = intent.getIntExtra("defAddr", 0);
        if (defAddr == 1) {
            ivSelect.setBackgroundResource(R.mipmap.checkbox_checked);
        } else {
            ivSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
        }
        setUpViews();
        getThreeList();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_AddreddSelect:
                if (defAddr != 1) {
                    ivSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                    defAddr = 1;
                } else {
                    ivSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                    defAddr = 0;
                }
                isShouldHideInput();
                break;
            case R.id.ll_AddreddUpdataRegion://所在地区
                if (TextUtils.isEmpty(mCurrentProviceName)){
                    getThreeList();
                }else {
                    address_select.setVisibility(View.VISIBLE);
                    isShouldHideInput();
                }
                break;
            case R.id.tv_AddressCancel://取消
                finish();
                break;
            case R.id.tv_AddressSave://保存
                upDataAddress();
                break;
            case R.id.btn_confirm:
                address_select.setVisibility(View.GONE);
                province = mCurrentProviceName;
                city = mCurrentCityName;
                region = mCurrentDistrictName;
                provinceId = mCurrentProviceId;
                cityId = mCurrentCityId;
                regionId = mCurrentDistrictId;
                tvRegion.setText(mCurrentProviceName + "-" + mCurrentCityName + "-" + mCurrentDistrictName);
                break;
            case R.id.btn_off:
                address_select.setVisibility(View.GONE);
                break;
        }
    }

    private void getThreeList() {
        //城市列表
        String regionId = SharePrefHelper.getInstance(this).getSpString("provinceId");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("regionId", regionId);
        okHttpGet(100, Config.REGIONLIST, params);
    }

    //保存
    private void upDataAddress() {
        Map<String, String> params = new HashMap();
        int editOrAdd = 0; // 0 新增  1 编辑
        if (type == 0) {
            editOrAdd = 0;
        } else {
            editOrAdd = addrId;
        }
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        } else {
            ToastUtil.showToast("登录过期，请重新登录");
            return;
        }
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("收货人姓名不能为空！");
            return;
        } else {
            params.put("name", name);
        }
        String mobile = etMobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.showToast("手机号不能为空！");
            return;
        } else {
            params.put("mobile", mobile);
        }
        if (TextUtils.isEmpty(province)
                && TextUtils.isEmpty(city)
                && TextUtils.isEmpty(region)) {
            ToastUtil.showToast("所在地区不能为空！");
            return;
        } else {
            params.put("province", province);
            params.put("city", city);
            params.put("region", region);
            params.put("provinceId", provinceId);
            params.put("cityId", cityId);
            params.put("regionId", regionId);
        }
        params.put("zip", "123456");
        String address = etAddr.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showToast("详细地址不能为空！");
            return;
        } else {
            params.put("addr", address);
        }
        params.put("defAddr", String.valueOf(defAddr));
        okHttpPostHeader(101, Config.ADDRESSUPDATA + editOrAdd, params);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {

            case 100:
                if (returnCode == 200) {
                    CityListAllModel cityallinfo = JSON.parseObject(returnData, CityListAllModel.class);
                    List<CityListAllModel.ListRegionsBean> listRegions = cityallinfo.getListRegions();
//                    Log.e("TAG_城市列表1","mProvinceDatas=");
                    setUpData(listRegions);
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101://保存
                if (returnCode == 200) {
                    finish();
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
}
