package com.yasn.purchasetest.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchasetest.R;
import com.yasn.purchasetest.adapter.CollectAdapter;
import com.yasn.purchasetest.adapter.HighProfitAdapter;
import com.yasn.purchasetest.func.RemoveAll;
import com.yasn.purchasetest.listener.OnRcItemClickListener;
import com.yasn.purchasetest.model.HighProfitModel;
import com.yasn.purchasetest.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

import static com.yasn.purchasetest.R.id.shoplistnull;

public class CollectActivity extends SimpleTopbarActivity implements OnRcItemClickListener {

    private HighProfitAdapter adapternull;
    private CollectAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager,nullLinearLayoutManager;
    private RecyclerView recyclerview,collect_recy;
    private List<HighProfitModel> myDataset;
    private static Class<?> rightFuncArray[] = {RemoveAll.class};
    private LinearLayout collectnull;
    private boolean typeshow = false;//临时记录显示空布局或者收藏布局
    private boolean removeallbool = false;//是否显示删除全部按钮
    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        if (removeallbool){
            return rightFuncArray;
        }else {
            return null;
        }
    }

    @Override
    protected Object getTopbarTitle() {
        return R.string.mycollect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //初始化tabRecyclerView
        initRecyclerView();
    }

    private void initRecyclerView() {
        collect_recy = (RecyclerView) findViewById(R.id.collect_recy);
        //空布局
        collectnull = (LinearLayout) findViewById(R.id.collectnull);
        collectnull.setOnClickListener(this);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        //初始化数据
        myDataset = new ArrayList<HighProfitModel>();
        for (int i = 0; i < 10; i++) {
            if (i==0){
                myDataset.add(new HighProfitModel("高毛利商品",1,"￥0.0"));
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
         * 创建空Adapter
         * 高毛利商品
         */
        nullLinearLayoutManager = new LinearLayoutManager(this);
        nullLinearLayoutManager.setAutoMeasureEnabled(true);
        recyclerview.setLayoutManager(nullLinearLayoutManager);
        adapternull = new HighProfitAdapter(myDataset,this);
        adapternull.setOnItemClickListener(this);
        recyclerview.setAdapter(adapternull);
        /**
         * 创建常购清单
         * 正式购物清单
         */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        collect_recy.setLayoutManager(mLinearLayoutManager);
        adapter = new CollectAdapter(myDataset,this);
        adapter.setOnItemClickListener(this);
        collect_recy.setAdapter(adapter);
        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 模拟耗时的操作。
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 更新主线程ＵＩ
                CollectActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        removeallbool = true;
                        addViewToRightFunctionZone();
                    }
                });
            }
        }).start();
    }
    public void removeAllDialog(){
        if (typeshow){
            collect_recy.setVisibility(View.VISIBLE);
            collectnull.setVisibility(View.GONE);
            typeshow = false;
        }else {
            collect_recy.setVisibility(View.GONE);
            collectnull.setVisibility(View.VISIBLE);
            typeshow = true;
            remomeAllDialog();
        }
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case shoplistnull:

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
                ToastUtil.showToast("点击了删除按钮"+listPosition);
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
    /**
     * 删除全部dialog
     */
    protected AlertDialog remomeAllDialog;

    private void remomeAllDialog() {
        LayoutInflater factor = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.remomeall_dialog, null);
        TextView remove = (TextView) serviceView.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtil.showToast("点击删除全部！");
            }
        });
            TextView cancelBtn = (TextView) serviceView.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//隐藏版本更新对话框
                ToastUtil.showToast("再看看！");
            }
        });
        Activity activity = CollectActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        remomeAllDialog = builder.create();
        remomeAllDialog.show();
        remomeAllDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }
}
