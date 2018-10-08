package com.yasn.purchase.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.BaseYasnActivity;
import com.yasn.purchase.adapter.RcCursorAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.db.RecordSQLiteOpenHelper;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.HotLableModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.RcDecoration;
import com.yasn.purchase.view.TagsLayout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.xcd.com.mylibrary.help.HelpUtils;

import static www.xcd.com.mylibrary.utils.SharePrefHelper.context;

public class HotLableActivity extends BaseYasnActivity implements View.OnClickListener, OnRcItemClickListener ,TextWatcher{

    private EditText topsearch;
    private LinearLayout topbar_right, topbar_left;
    private TextView address;
    private TextView searchbutton;
    private RecyclerView listView;
    private LinearLayout cleanhistory;
    private TagsLayout hotlabel;//热门标签
    private LinearLayout hotlableLinear;
    private ImageView ivClean;
    private LinearLayout llHistorysearch;//历史搜索
    @Override
    public boolean isTopbarVisibility() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotlable);
        hotlableLinear = (LinearLayout) findViewById(R.id.hotlableLinear);
        //返回按钮
        topbar_left = (LinearLayout) findViewById(R.id.topbar_left);
        topbar_left.setOnClickListener(this);
        //文本输入框
        topsearch = (EditText) findViewById(R.id.tv_Topsearch);
        topsearch.setOnFocusChangeListener(this);
        topsearch.setFocusable(true);
        topsearch.setFocusableInTouchMode(true);
        topsearch.requestFocus();

        HelpUtils.setEditTextInhibitInputSpeChat(topsearch);
        topsearch.addTextChangedListener(this);
        ivClean = (ImageView) findViewById(R.id.iv_clean);
        ivClean.setVisibility(View.GONE);
        ivClean.setOnClickListener(this);
        //搜索按钮
        searchbutton = (TextView) findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(this);
        // 4. 历史搜索记录 = ListView显示
        llHistorysearch = (LinearLayout) findViewById(R.id.ll_Historysearch);
        listView = (RecyclerView) findViewById(R.id.rc_HotLable);
        //清空历史记录
        cleanhistory = (LinearLayout) findViewById(R.id.cleanhistory);
        cleanhistory.setOnClickListener(this);
        //实例化历史搜索数据
        getHistorySearchData();
        //客服按钮
        topbar_right = (LinearLayout) findViewById(R.id.topbar_right);
        topbar_right.setOnClickListener(this);
        topbar_right.setVisibility(View.GONE);
        hotlabel = (TagsLayout) findViewById(R.id.hotlabel);
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(100, Config.OFTENSEARCH, params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }

        }, 300);
    }

    @SuppressWarnings("ResourceType")
    private void initLable(List<HotLableModel.DataBean> list) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < list.size(); i++) {
            final TextView textView = new TextView(this);
            HotLableModel.DataBean dataBean = list.get(i);
            textView.setText(dataBean.getKey_word());
            textView.setTextColor(ContextCompat.getColor(this, R.color.black_66));
            textView.setBackgroundResource(R.drawable.text_n_f5);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String topsearchString = textView.getText().toString().trim();
                    if (TextUtils.isEmpty(topsearchString)){
                        return;
                    }
                    startActivity(topsearchString);
                }
            });
            textView.setTag(list.get(i));
            lp.setMargins(10, 10, 10, 10);
            hotlabel.addView(textView, lp);
        }
    }
    private void startActivity(String topsearchString){
        // 1. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
        boolean hasData = hasData(topsearchString);
        // 2. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
        if (!hasData) {
            insertData(topsearchString);
            queryData("");
        }else {
            deleteData(topsearchString);
            insertData(topsearchString);
            queryData("");
        }

        Intent intent = new Intent(HotLableActivity.this,SearchActivity.class);
        intent.putExtra("SECARCHCONTEXT",topsearchString);
        intent.putExtra("SECARCHTOPTAB",false);//是否显示搜索页顶部TabLayout
        startActivity(intent);
        Map<String, Object> params = new HashMap<String, Object>();
        okHttpGet(1000, Config.SYNCHSEARCH+topsearchString, params);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchbutton:
                String topsearchString = topsearch.getText().toString().trim();
                if (TextUtils.isEmpty(topsearchString)){
                    ToastUtil.showToast("搜索内容不能为空！");
                    return;
                }
                // 1. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                boolean hasData = hasData(topsearchString);
                // 2. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                if (!hasData) {
                    insertData(topsearchString);
                    queryData("");
                }
//                topsearch.setText("");
                startActivity(topsearchString);
                break;
            case R.id.cleanhistory:
                // 清空数据库->>关注2
                deleteData();
                // 模糊搜索空字符 = 显示所有的搜索历史（此时是没有搜索记录的）
                queryData("");
                cleanhistory.setVisibility(View.GONE);
                llHistorysearch.setVisibility(View.GONE);
                break;
            case R.id.topbar_left:
                finish();
                break;
            case R.id.iv_clean:
                topsearch.setText("");
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void OnItemClick(View view, int position) {
        LinearLayout layout = (LinearLayout)view;
        TextView status = (TextView) layout.findViewById(R.id.historysearch_title);
        if (status !=null&&status.getText()!=null)
        startActivity(status.getText().toString());
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

    // 数据库变量
    // 用于存放历史搜索记录
    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private RcCursorAdapter adapter;

    private void getHistorySearchData() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        listView.setLayoutManager(mLinearLayoutManager);

        adapter = new RcCursorAdapter(this);
        adapter.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        //自定义的分隔线
        listView.addItemDecoration(new RcDecoration(this,RcDecoration.VERTICAL_LIST));
        adapter.notifyDataSetChanged();

        // 2. 实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(this);

        // 3. 第1次进入时查询所有的历史搜索记录
        queryData("");
    }

    /**
     * 关注1
     * 模糊查询数据 & 显示到ListView列表上
     */
    private void queryData(String tempName) {

        // 1. 模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc limit 10", null);
//        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
//        adapter = new SimpleCursorAdapter(this, R.layout.recycleritem_historysearch, cursor, new String[]{"name"},
//                new int[]{R.id.historysearch_title}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 3. 设置适配器
        adapter.changeCursor(cursor);
        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if (cursor.getCount() != 0) {
            cleanhistory.setVisibility(View.VISIBLE);
            llHistorysearch.setVisibility(View.VISIBLE);
        } else {
            cleanhistory.setVisibility(View.GONE);
            llHistorysearch.setVisibility(View.GONE);
        }
        ;

    }

    /**
     * 关注2：清空数据库
     */
    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    /**
     * 关注3
     * 检查数据库中是否已经有该搜索记录
     */
    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 关注4
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }
    //删除数据信息
    public void deleteData(String tempName){
        db = helper.getWritableDatabase();
        Log.e("TAG_删除","db="+db.isOpen());
        if(db.isOpen()){
            db.execSQL("delete from records where name=('" + tempName + "')" );
            db.close();
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {
            case 100:
                if (returnCode == 0){
                    HotLableModel hotlablemodel = JSON.parseObject(returnData, HotLableModel.class);
                    List<HotLableModel.DataBean> data = hotlablemodel.getData();
                    if (data ==null||data.size()==0){
                        hotlableLinear.setVisibility(View.GONE);
                    }else {
                        hotlableLinear.setVisibility(View.VISIBLE);
                        initLable(data);
                    }
                }else {
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String trim = topsearch.getText().toString().trim();
        Log.e("TAG_模糊","trimCode="+trim);
        if (TextUtils.isEmpty(trim)){
            ivClean.setVisibility(View.GONE);
        }else {
            ivClean.setVisibility(View.VISIBLE);
        }
        topsearch.setSelection(topsearch.getText().length());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
