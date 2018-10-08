package com.yasn.purchase.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnFragment;
import com.yasn.purchase.adapter.HelpRecyclerAdapter;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.HelpFragmentModel;
import com.yasn.purchase.model.HelpModel;
import com.yasn.purchase.view.RecyclerLayoutManager;
import com.yasn.purchase.view.RecyclerViewDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_CONTENT;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_HEADER;

/**
 * Created by gs on 2018/6/14.
 * 购物指南
 */

public class InvoiceHelpFragment extends BaseYasnFragment implements OnRcItemClickListener{

    private RecyclerView rcHelp;
    List<HelpFragmentModel> helpRecyList = new ArrayList<>();//储存转换后的数据格式

    @Override
    protected void OkHttpDemand() {

    }

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_help;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        RelativeLayout topbat_parent = (RelativeLayout) view.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        //初始化tabRecyclerView
        rcHelp = (RecyclerView) view.findViewById(R.id.rc_Help);
        RecyclerLayoutManager mLinearLayoutManager = new RecyclerLayoutManager(getActivity());
        mLinearLayoutManager.setScrollEnabled(true);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        rcHelp.setLayoutManager(mLinearLayoutManager);
        //创建Adapter
        HelpRecyclerAdapter adapter = new HelpRecyclerAdapter(getActivity());
        adapter.setOnItemClickListener(this);
        //rc线
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.line_c3));
        rcHelp.addItemDecoration(recyclerViewDecoration);
        rcHelp.setAdapter(adapter);

        Bundle bundle = getArguments();
        // 从bundle数据包中取出数据
        int selcet = bundle.getInt("selcet");
        Log.e("TAG_HELP","selcet"+selcet);
        HelpModel helpModel = (HelpModel) bundle.getSerializable("data");
        List<HelpModel.DataBean> data = helpModel.getData();
        HelpModel.DataBean dataBean = data.get(selcet);
        List<HelpModel.DataBean.ChidrenListBean> chidrenList = dataBean.getChidrenList();
        for (int i = 0, j = chidrenList.size(); i < j; i++) {
            HelpFragmentModel helpTitle = new HelpFragmentModel();
            HelpModel.DataBean.ChidrenListBean chidrenListBean = chidrenList.get(i);
            //顶部标题
            String childrenName = chidrenListBean.getChildrenName();
            helpTitle.setTitle(childrenName);
            //左侧图标
            String descript = chidrenListBean.getDescript();
            Log.e("TAG_图标1","descript="+descript);
            descript = descript.substring(descript.indexOf("\"")+1,descript.length());
            Log.e("TAG_图标2","descript="+descript);
            descript = descript.substring(0,descript.indexOf("\""));
            Log.e("TAG_图标3","descript="+descript);
            helpTitle.setDescript(descript);
            helpTitle.setItemType(ITEM_HEADER);
            helpRecyList.add(helpTitle);

            List<HelpModel.DataBean.ChidrenListBean.ArticleeBean> articlee = chidrenListBean.getArticlee();
            for (int k = 0,l =articlee.size(); k < l; k++) {
                HelpFragmentModel helpContent = new HelpFragmentModel();
                HelpModel.DataBean.ChidrenListBean.ArticleeBean articleeBean = articlee.get(k);
                String title = articleeBean.getTitle();
                helpContent.setTitle(title);
                String content = articleeBean.getContent();
                helpContent.setContent(content);
                helpContent.setItemType(ITEM_CONTENT);
                helpRecyList.add(helpContent);
            }
        }
        adapter.setData(helpRecyList);
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

    @Override
    public void OnItemClick(View view, int position) {

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
}
