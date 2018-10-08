package com.yasn.purchase.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;
import com.yasn.purchase.adapter.OilAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.model.OilParamsModel;
import com.yasn.purchase.model.OilQueryModel;
import com.yasn.purchase.view.RecyclerViewDecoration;

import org.angmarch.views.ArrowsSpinner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OilActivity extends BaseYasnActivity implements OilAdapter.OnOilClickListener {

    private ArrowsSpinner spOilBrand, spOilCarType, spOilYeayStyle, spOilCc;
    private TextView tvOilQuery;
    private TextView tvGearboxType, tvGearboxOilModel, tv0ilModel, tvOilAddition;
    private LinearLayout llOilQuery;
    //品牌
    List<OilParamsModel> oilBrand;
    //车系
    List<OilParamsModel> oilCarType;
    //年款
    List<OilParamsModel> oilYeayStyle;
    //发动机排量
    List<OilParamsModel> oilCc;

    @Override
    protected Object getTopbarTitle() {
        return "用油查询";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil);
        getOilParams(100,0);
    }

    private void getOilParams(int requestCode , int linkageId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(requestCode, Config.SHOPOIL + linkageId, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //品牌
        spOilBrand = (ArrowsSpinner) findViewById(R.id.sp_OilBrand);
        spOilBrand.setOnClickListener(this);
        spOilBrand.animateArrow(true);
        spOilBrand.setArrowAnimate(false);
        spOilBrand.setTextColor(ContextCompat.getColor(this, R.color.black_66));
        //车系
        spOilCarType = (ArrowsSpinner) findViewById(R.id.sp_OilCarType);
        spOilCarType.setOnClickListener(this);
        spOilCarType.animateArrow(true);
        spOilCarType.setArrowAnimate(false);
        spOilCarType.setTextColor(ContextCompat.getColor(this, R.color.black_66));
        //年款
        spOilYeayStyle = (ArrowsSpinner) findViewById(R.id.sp_OilYeayStyle);
        spOilYeayStyle.setOnClickListener(this);
        spOilYeayStyle.animateArrow(true);
        spOilYeayStyle.setArrowAnimate(false);
        spOilYeayStyle.setTextColor(ContextCompat.getColor(this, R.color.black_66));
        //发动机排量
        spOilCc = (ArrowsSpinner) findViewById(R.id.sp_OilCc);
        spOilCc.setOnClickListener(this);
        spOilCc.animateArrow(true);
        spOilCc.setArrowAnimate(false);
        spOilCc.setTextColor(ContextCompat.getColor(this, R.color.black_66));
        //查询
        tvOilQuery = (TextView) findViewById(R.id.tv_OilQuery);
        tvOilQuery.setOnClickListener(this);
        //查询结果布局
        tvGearboxType = (TextView) findViewById(R.id.tv_gearboxType);
        tvGearboxOilModel = (TextView) findViewById(R.id.tv_gearboxOilModel);
        tv0ilModel = (TextView) findViewById(R.id.tv_oilModel);
        tvOilAddition = (TextView) findViewById(R.id.tv_oilAddition);
        llOilQuery = (LinearLayout) findViewById(R.id.ll_OilQuery);
        llOilQuery.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sp_OilBrand: //品牌
                showPopwindow(100, "品牌", oilBrand);
//                spOilBrand.animateArrow(true);
                break;
            case R.id.sp_OilCarType://车系
                Object tagOilBrand = spOilBrand.getTag();
                if (tagOilBrand !=null){
                    int linkage_idType = (int) tagOilBrand;
                    Log.e("TAG_车系", "linkage_id=" + linkage_idType);
                    showPopwindow(101, "车系", oilCarType);
                }
//                spOilCarType.animateArrow(true);
                break;
            case R.id.sp_OilYeayStyle: //年款
                Object tagOilCarType = spOilCarType.getTag();
                if (tagOilCarType != null){
                    int linkage_idStyle = (int) tagOilCarType;
                    Log.e("TAG_年款", "linkage_id=" + linkage_idStyle);
                    showPopwindow(102, "年款", oilYeayStyle);
                }
//                spOilYeayStyle.animateArrow(true);
                break;
            case R.id.sp_OilCc: //发动机排量
                Object spOilYeayStyleTag = spOilYeayStyle.getTag();
                if (spOilYeayStyleTag !=null){
                    int linkage_idCc = (int) spOilYeayStyleTag;
                    Log.e("TAG_发动机排量", "linkage_id=" + linkage_idCc);
                    showPopwindow(103, "发动机排量", oilCc);
                }
//                spOilCc.animateArrow(true);
                break;
            case R.id.tv_OilQuery:
                Object spOilCcTag = spOilCc.getTag();
                if (spOilCcTag !=null){
                    int linkage_idQuery = (int) spOilCcTag;
                    Log.e("TAG_发动机排量", "linkage_id=" + linkage_idQuery);
                    Map<String, Object> params = new HashMap<String, Object>();
                    if (token != null && !"".equals(token)) {
                        params.put("access_token", token);
                    } else if (resetToken != null && !"".equals(resetToken)) {
                        params.put("access_token", resetToken);
                    }
                    okHttpGet(104, Config.SHOPOILQUERY + linkage_idQuery, params);
                }
                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {
            case 100: //品牌
                if (returnCode == 200) {
                    oilBrand = JSON.parseArray(returnData, OilParamsModel.class);

                }
                break;
            case 101: //车系
                if (returnCode == 200) {
                    oilCarType = JSON.parseArray(returnData, OilParamsModel.class);

                }
                break;
            case 102: //年款
                if (returnCode == 200) {
                    oilYeayStyle = JSON.parseArray(returnData, OilParamsModel.class);

                }
                break;
            case 103: //发动机排量
                if (returnCode == 200) {
                    oilCc = JSON.parseArray(returnData, OilParamsModel.class);

                }
                break;
            case 104://查询结果
                OilQueryModel oilQueryModel = JSON.parseObject(returnData, OilQueryModel.class);
                //变速箱类型
                String gearboxType = oilQueryModel.getGearboxType();
                tvGearboxType.setText(gearboxType == null ? "" : gearboxType);
                //变速箱油适配型号
                String gearboxOilModel = oilQueryModel.getGearboxOilModel();
                tvGearboxOilModel.setText(gearboxOilModel == null ? "" : gearboxOilModel);
                //机油适配型号
                String oilModel = oilQueryModel.getOilModel();
                tv0ilModel.setText(oilModel == null ? "" : oilModel);
                //机油加注量
                String oilAddition = oilQueryModel.getOilAddition();
                tvOilAddition.setText(oilAddition == null ? "" : oilAddition);
                llOilQuery.setVisibility(View.VISIBLE);
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

    /**
     * 显示popupWindow
     */
    PopupWindow window;
    private void showPopwindow(int type, String topName, List<OilParamsModel> list) {
        if (list == null ||list.size() == 0){
            return;
        }
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_oil, null);
        TextView tvTopNameOil = (TextView) view.findViewById(R.id.tv_TopNameOil);
        tvTopNameOil.setText(topName);
        RecyclerView rcOilPopwindow = (RecyclerView) view.findViewById(R.id.rc_OilPopwindow);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rcOilPopwindow.setLayoutManager(linearLayoutManager);
        OilAdapter adapter = new OilAdapter(type, OilActivity.this);
        adapter.setOnItemClickListener(this);
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                this, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.line_gray));
        rcOilPopwindow.addItemDecoration(recyclerViewDecoration);
        rcOilPopwindow.setAdapter(adapter);

        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        window.setFocusable(true);
        final WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha=0.3f;
        //此行代码主要是解决在华为手机上半透明效果无效的bug
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        getWindow().setAttributes(attributes);
        int height =  getWindowManager().getDefaultDisplay().getHeight();
        window.setHeight(height * 6/10);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
//        window.setBackgroundDrawable(dw);
        window.setBackgroundDrawable(null);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.popwindow_anim_topanddown);
        // 在底部显示
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
                attributes.alpha=1.0f;
                getWindow().setAttributes(attributes);
            }
        });
        adapter.setData(list);
    }

    @Override
    public void OnItemClick(int type, int position, List<OilParamsModel> list) {
        OilParamsModel oilParamsModel = list.get(position);
        String name = oilParamsModel.getName();
        int linkage_id = oilParamsModel.getLinkage_id();
        Log.e("TAG_OnItemClick", "linkage_id=" + linkage_id);
        switch (type) {
            case 100: //品牌
                spOilBrand.setText(name);
                spOilBrand.setTag(linkage_id);
                getOilParams(101,linkage_id);
                spOilCarType.setText("车系");
                spOilYeayStyle.setText("年款");
                spOilCc.setText("发动机排量");
                llOilQuery.setVisibility(View.GONE);
                break;
            case 101: //车系
                spOilCarType.setText(name);
                spOilCarType.setTag(linkage_id);
                getOilParams(102,linkage_id);
                spOilYeayStyle.setText("年款");
                spOilCc.setText("发动机排量");
                llOilQuery.setVisibility(View.GONE);
                break;
            case 102: //年款
                spOilYeayStyle.setText(name);
                spOilYeayStyle.setTag(linkage_id);
                getOilParams(103,linkage_id);
                spOilCc.setText("发动机排量");
                llOilQuery.setVisibility(View.GONE);
                break;
            case 103: //发动机排量
                spOilCc.setText(name);
                spOilCc.setTag(linkage_id);
                break;
        }
        if (window !=null){
            window.dismiss();
        }
    }
}
