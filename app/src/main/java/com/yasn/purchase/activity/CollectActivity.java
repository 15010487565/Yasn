package com.yasn.purchase.activity;

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

import com.yasn.purchase.R;
import com.yasn.purchase.adapter.CollectAdapter;
import com.yasn.purchase.adapter.HomeMoreAdapter;
import com.yasn.purchase.func.RemoveAll;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.utils.ToastUtil;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

import static com.yasn.purchase.R.id.shoplistnull;

/**
 * 收藏
 */
public class CollectActivity extends SimpleTopbarActivity
        implements OnRcItemClickListener {

    private HomeMoreAdapter adapternull;
    private CollectAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager,nullLinearLayoutManager;
    private RecyclerView recyclerview,collect_recy;
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
        /**
         * 创建空Adapter
         * 高毛利商品
         */
        nullLinearLayoutManager = new LinearLayoutManager(this);
        nullLinearLayoutManager.setAutoMeasureEnabled(true);
        recyclerview.setLayoutManager(nullLinearLayoutManager);
        adapternull = new HomeMoreAdapter(this,nullLinearLayoutManager);
        adapternull.setOnItemClickListener(this);
        recyclerview.setAdapter(adapternull);
        /**
         * 创建常购清单
         * 正式购物清单
         */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        collect_recy.setLayoutManager(mLinearLayoutManager);
        adapter = new CollectAdapter(this,mLinearLayoutManager);
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
