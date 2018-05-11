package com.yasn.purchase.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.HotLableActivity;
import com.yasn.purchase.activity.SearchActivity;
import com.yasn.purchase.adapter.ClassifyLeftAdapter;
import com.yasn.purchase.adapter.ClassifyRightGridAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.ClassifyBrandModel;
import com.yasn.purchase.model.ClassifyLeftModel;
import com.yasn.purchase.model.ClassifyModel;
import com.yasn.purchase.model.ClassifyRightModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.NoScrollGridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.utils.SharePrefHelper;


/**
 * Created by Android on 2017/9/5.
 */
public class ClassifyFragment extends SimpleTopbarFragment implements
        OnRcItemClickListener,OnItemClickListener {

    private RelativeLayout topbat_parent;
    private RecyclerView calssifyrecy_left;
    private NoScrollGridView calssifyrecy_right;
    private ClassifyRightGridAdapter rightAdapter;
    private ClassifyLeftAdapter leftAdapter;
    private LinearLayoutManager leftLinearLayoutManager;
    //    private ConvenientBanner convenientBanner;
    private ImageView convenientBanner;
    private TextView topsearch;
//    private TagsLayout hotlabel;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    @Override
    protected void lazyLoad() {
//        if(!isPrepared || !isVisible) {
//            return;
//        }
        //填充各控件的数据
        OkHttpDemand();
    }
    @Override
    protected void OkHttpDemand() {
        token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
        resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(getActivity()).getSpString("resetTokenTime");
        //推荐品牌
        if (token != null&&!"".equals(token)){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("access_token", token);
            okHttpGet(101, Config.CLASSIFYBRAND, params);
        }else if (resetToken != null&&!"".equals(resetToken)){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("access_token", resetToken);
            okHttpGet(101, Config.CLASSIFYBRAND, params);
        }else {
            Map<String, Object> params = new HashMap<String, Object>();
            okHttpGet(101, Config.CLASSIFYBRAND, params);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("TAG_onHiddenChanged","CLASSIFY="+hidden);
        if (hidden){
            lazyLoad();
        }
    }

    public void getClassifyList() {
        //分类列表
        if (token != null&&!"".equals(token)){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("access_token", token);
            okHttpGet(100, Config.CLASSIFY, params);
        }else if (resetToken != null&&!"".equals(resetToken)){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("access_token", resetToken);
            okHttpGet(100, Config.CLASSIFY, params);
        }else {
            Map<String, Object> params = new HashMap<String, Object>();
            okHttpGet(100, Config.CLASSIFY, params);
        }
    }
    @Override
    protected void initView(LayoutInflater inflater, View view) {
        Log.e("TAG_initView","CLASSIFY_initView");
        //搜索输入框
        topsearch = (TextView) view.findViewById(R.id.topsearch);
        topsearch.setOnClickListener(this);
        topbat_parent = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);
        //左侧列表
        calssifyrecy_left = (RecyclerView) view.findViewById(R.id.calssifyrecy_left);
        leftLinearLayoutManager = new LinearLayoutManager(getActivity());
        leftLinearLayoutManager.setAutoMeasureEnabled(true);
        calssifyrecy_left.setLayoutManager(leftLinearLayoutManager);
        //实例化adapter
        leftAdapter = new ClassifyLeftAdapter(getActivity());
        leftAdapter.setOnItemClickListener(this);
        calssifyrecy_left.setAdapter(leftAdapter);
        //右侧列表
        calssifyrecy_right = (NoScrollGridView) view.findViewById(R.id.calssifyrecy_right);
        calssifyrecy_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassifyRightModel classifyRightModel = rightList.get(position);
                int itemType = classifyRightModel.getItemType();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                if (brandList !=null&&brandList.size()>0&&itemType==2){
                    int rightClassifyBrandId = classifyRightModel.getRightClassifyBrandId();
                    intent.putExtra("SECARCHBRAND",String.valueOf(rightClassifyBrandId));//品牌id
                    intent.putExtra("SECARCHTOPTAB",false);//是否显示搜索页顶部TabLayout
                    startActivity(intent);
                }else {
                    String rightClassifycatId = classifyRightModel.getRightClassifycatId();
                    intent.putExtra("SECARCHCARID",rightClassifycatId);//分类id
                    intent.putExtra("SECARCHTOPTAB",true);//是否显示搜索页顶部TabLayout
                    startActivity(intent);
                }
            }
        });
        //实例化adapter
        rightAdapter = new ClassifyRightGridAdapter(getActivity());
        calssifyrecy_right.setAdapter(rightAdapter);
        //右侧图片
        convenientBanner = (ImageView) view.findViewById(R.id.convenientBanner);
        //实例化标签
//        hotlabel = (TagsLayout) view.findViewById(R.id.hotlabel);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.topsearch://搜索按钮
                Intent intent = new Intent(getActivity(), HotLableActivity.class);
//                startActivityForResult(intent,TOPSEARCHCode);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
                break;
        }
    }

    @Override
    public void onPause() {
        getActivity().overridePendingTransition(0, 0);
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode ==TOPSEARCHCode){
//                String searchResult = data.getStringExtra("SEARCHRESULT");
//                topsearch.setText(searchResult);
//            }
//
//        }
    }

    private List<ClassifyLeftModel> leftList = new ArrayList<ClassifyLeftModel>();
    List<ClassifyRightModel> rightList = new ArrayList<>();

    private void initLeftData(List<ClassifyModel.CatsBean> classifyModelCats) {
        if (leftList != null && leftList.size() > 0) {
            leftList.clear();
        }
        int selectPosition = 0;
        if (brandList!= null && brandList.size() > 0) {
            ClassifyLeftModel map = new ClassifyLeftModel();
            map.setTitle("推荐品牌区");
            map.setItemType(1);
            map.setChecked(true);
            selectPosition = 0;
            leftList.add(map);
        }
        boolean isChecked = false;
        for (int i = 0, j = classifyModelCats.size(); i < j; i++) {
            ClassifyModel.CatsBean catsBean = classifyModelCats.get(i);
            String name = catsBean.getName();
            ClassifyLeftModel map = new ClassifyLeftModel();
            List<ClassifyModel.CatsBean.ChildrenBean> children = catsBean.getChildren();
            if (children != null && children.size() > 0 && isChecked) {
                map.setChecked(true);
                isChecked = false;
                selectPosition = i;
            }
            map.setItemType(1);
            map.setTitle(name);
            leftList.add(map);
        }

        ClassifyLeftModel footView = new ClassifyLeftModel();
        footView.setItemType(Config.TYPE_FOOTVIEW);
        leftList.add(footView);
        leftAdapter.setData(leftList);
        initRightData(classifyModelCats, selectPosition);
    }

    private void initRightData(List<ClassifyModel.CatsBean> classifyModelCats, int position) {
        if (rightList != null && rightList.size() > 0) {
            rightList.clear();
        }
        if (brandList!= null && brandList.size() > 0) {
           if (position==0){
               for (int i = 0,j = brandList.size(); i < j; i++) {
                   ClassifyBrandModel.BrandListBean brandListBean = brandList.get(i);
                   ClassifyRightModel rightMap = new ClassifyRightModel();
                   int brandId = brandListBean.getBrandId();
                   rightMap.setRightClassifyBrandId(brandId);
                   String image = brandListBean.getImage();
                   rightMap.setRightClassifyBrandImg(image);
                   String name = brandListBean.getName();
                   rightMap.setRightClassifyBrandName(name);
                   rightMap.setItemType(2);
                   rightList.add(rightMap);
               }
               //右侧顶部图片
               convenientBanner.setVisibility(View.GONE);
               //数理化右侧数据
               rightAdapter.setData(rightList);
               return;
           }
        }
        //右侧顶部图片
        ClassifyModel.CatsBean catsBean = classifyModelCats.get(position);
        initViewPagerImage(catsBean.getAdv_image());
        List<ClassifyModel.CatsBean.ChildrenBean> children = catsBean.getChildren();
        for (int k = 0, l = children.size(); k < l; k++) {
            ClassifyModel.CatsBean.ChildrenBean childrenBean = children.get(k);
            ClassifyRightModel rightMap = new ClassifyRightModel();
            rightMap.setRightClassifyImg(childrenBean.getImage());
            rightMap.setRightClassifyName(childrenBean.getName());
            rightMap.setRightClassifyparentId(String.valueOf(childrenBean.getParent_id()));
            rightMap.setRightClassifycatId(String.valueOf(childrenBean.getCat_id()));
            rightMap.setItemType(1);
            rightList.add(rightMap);
        }
        //数理化右侧数据
        rightAdapter.setData(rightList);
    }

    private void initViewPagerImage(String imageUrl) {
        if (imageUrl == null ||"".equals(imageUrl)){
            convenientBanner.setVisibility(View.GONE);
        }else {
            convenientBanner.setVisibility(View.VISIBLE);
            Glide.with(getActivity())
                    .load(imageUrl)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.failtoload)
                    .error(R.mipmap.failtoload)
                    .into(convenientBanner);
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
//        onFocusChange(topsearch,false);

        for (int i = 0; i < leftList.size(); i++) {
            ClassifyLeftModel classifyleftinfo = leftList.get(i);
            if (i == position) {
                classifyleftinfo.setChecked(true);
            } else {
                classifyleftinfo.setChecked(false);
            }
        }

        leftAdapter.notifyDataSetChanged();
        //数理化右侧数据
        initRightData(classifyModelCats, position);
    }

    @Override
    public void OnItemLongClick(View view, int position) {
    }

    @Override
    public void OnClickTabMore(int listPosition) {

    }

    @Override
    public void OnClickRecyButton(int itemPosition, int listPosition) {

    }
    //分类品牌数据
    private List<ClassifyModel.CatsBean> classifyModelCats;
    //分类数据
    private List<ClassifyBrandModel.BrandListBean> brandList;
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100://分类列表
                if (returnCode == 200) {
                    ClassifyModel classifyModel = JSON.parseObject(returnData, ClassifyModel.class);
                    classifyModelCats = classifyModel.getCats();
                    if (classifyModelCats != null && classifyModelCats.size() > 0) {
                        initLeftData(classifyModelCats);
                    }
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101://推荐品牌
                if (returnCode == 200) {
                    ClassifyBrandModel classifyBrandModel = JSON.parseObject(returnData, ClassifyBrandModel.class);
                   brandList = classifyBrandModel.getBrandList();
                    getClassifyList();
                } else {
                    ToastUtil.showToast(returnMsg);
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
    public void onItemClick(int position) {

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        super.onFocusChange(view, hasFocus);
        if (hasFocus) {//获取焦点
            startActivity(new Intent(getActivity(), HotLableActivity.class));
        } else {

        }
    }
}