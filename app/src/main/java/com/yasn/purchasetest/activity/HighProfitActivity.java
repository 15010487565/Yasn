package com.yasn.purchasetest.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yasn.purchasetest.R;
import com.yasn.purchasetest.adapter.HighProfitAdapter;
import com.yasn.purchasetest.func.CallService;
import com.yasn.purchasetest.listener.OnRcItemClickListener;
import com.yasn.purchasetest.model.HighProfitModel;
import com.yasn.purchasetest.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class HighProfitActivity extends SimpleTopbarActivity implements OnRcItemClickListener {

    private HighProfitAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager,nullLinearLayoutManager;
    private RecyclerView recyclerview;
    private List<HighProfitModel> myDataset;
    private static Class<?> rightFuncArray[] = {CallService.class};
    private LinearLayout shoplistnull;
    private boolean typeshow = false;;//临时记录显示空布局常购清单布局
    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected Object getTopbarTitle() {
        return R.string.highprofit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highprofit);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //初始化tabRecyclerView
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerview = (RecyclerView) findViewById(R.id.highprofit_recy);
        //初始化数据
        myDataset = new ArrayList<HighProfitModel>();
        for (int i = 0; i < 10; i++) {
            if (i==0){
                myDataset.add(new HighProfitModel("高毛利商品",2,"￥0.0"));
            }else {
                HighProfitModel homelistviewinfo = new HighProfitModel(i+"",2,"","￥0.0");
                if (i==1){
                    homelistviewinfo.setText(i+"");
                    homelistviewinfo.setImage("http://g.hiphotos.baidu.com/image/h%3D300/sign=8166baec8826cffc762ab9b289014a7d/b3fb43166d224f4a1687bf6603f790529822d1ad.jpg");
                }else if (i==2){
                    homelistviewinfo.setText("#商品#大师2342傅胜多124345456754634234gdgfdfdfgdf");
                    homelistviewinfo.setImage("http://c.hiphotos.baidu.com/image/h%3D300/sign=b37d89c4b83533faeab6952e98d1fdca/9f510fb30f2442a77396fd6fd843ad4bd013025b.jpg");
                } else {
                    homelistviewinfo.setText("商品sdadaadaads213213sssasdadd大师傅胜231231313123多");
                    homelistviewinfo.setImage("http://a.hiphotos.baidu.com/image/h%3D300/sign=98d086fb45086e0675a8394b32097b5a/023b5bb5c9ea15ce169afac3bf003af33b87b287.jpg");
                }

                homelistviewinfo.setMoney("￥0.0"+i);
                myDataset.add(homelistviewinfo);
            }
        }
        /**
         * 创建常购清单
         * 正式购物清单
         */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        recyclerview.setLayoutManager(mLinearLayoutManager);
        adapter = new HighProfitAdapter(myDataset,this);
        adapter.setOnItemClickListener(this);
        recyclerview.setAdapter(adapter);
    }
    public void callService(){

    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.shoplistnull:

                break;
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        ToastUtil.showToast("listview item点击了"+position);
    }

    @Override
    public void OnItemLongClick(View view, int position) {

    }

    @Override
    public void OnClickTabMore(int listPosition) {
        ToastUtil.showToast("点击了更多");
    }

    @Override
    public void OnClickRecyButton(int itemPosition, int listPosition) {
        HighProfitModel info = myDataset.get(listPosition);
        switch (itemPosition){
            case 1:
                ToastUtil.showToast(info.getButton1());
                break;
            case 2:
                ToastUtil.showToast(info.getButton2());
                break;
            case 3:
                ToastUtil.showToast(info.getButton3());
                break;
            case 4:
                ToastUtil.showToast("点击了加入购物车"+info.getButton3());
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
}
