package com.yasn.purchase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yasn.purchase.R;
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.adapter.SearchAdapter;
import com.yasn.purchase.adapter.SortAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.help.SobotUtil;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.CarType;
import com.yasn.purchase.model.SearchCatsModel;
import com.yasn.purchase.model.SearchModel;
import com.yasn.purchase.model.SortModel;
import com.yasn.purchase.utils.PinyinComparator;
import com.yasn.purchase.utils.PinyinUtils;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.MultiSwipeRefreshLayout;
import com.yasn.purchase.view.RcDecoration;
import com.yasn.purchase.view.TitleItemDecoration;
import com.yasn.purchase.view.WaveSideBar;

import org.angmarch.views.ArrowsSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

public class SearchActivity extends SimpleTopbarActivity implements
        TabLayout.OnTabSelectedListener
        , SortAdapter.OnItemClickListener
        , OnRcItemClickListener
        , SwipeRefreshLayout.OnRefreshListener
        , MultiSwipeRefreshLayout.OnLoadListener
        , MultiSwipeRefreshLayout.OnMultiSwipeRefreshClickListener {

    private TabLayout tablayout;
    private ArrowsSpinner searchmoney, searchsalesvolume;
    private TextView searchsynthesis, searchscreen, unselected_yasn;
    private LinearLayout topbar_left,topbar_right, selecttype, menu_layout_right, selected_yasn;
    private DrawerLayout drawer_layout;
    private FrameLayout selected_fram, yasnautofram, cartypefram;
    private boolean ISCHECKEDYASN = false;//临时标志记录是否选中雅森自营
    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator mComparator;
    private RecyclerView recyclerview_serrch, drawlayoutsearch_rv;
    private MultiSwipeRefreshLayout swipe_layout;
    private WaveSideBar mSideBar;
    private LinearLayoutManager manager;
    private SortAdapter mAdapter;
    private List<SortModel> mDateList;
    private TitleItemDecoration mDecoration;
    private TextView reset, ok;
    private String cartype;//車型文字描述
    private int carTypeId;//車型ID
    private int page = 1;//页码
    private TextView seclectName;
    private RelativeLayout ungoods_relat;
    private LinearLayoutManager mLinearLayoutManager;
    private SearchAdapter adapter;
    private TextView cartypetext;
    private TextView topsearch;
    private LinearLayout topsearchLinear;
    private boolean secarchtoptab = false;//是否显示顶部TabLayout分类布局
    private String secarchContext;//搜索内容
    private String secarchcarid;
    private String sortOld;
    private boolean isDownPull = false;//下拉刷新
    private boolean isUpPull = false;//上拉加载
    private boolean isShowProgressBar = false;//底部加载更多
    private ImageView backDrawer;
    @Override
    public boolean isTopbarVisibility() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        region = SharePrefHelper.getInstance(this).getSpString("regionId");//根据账号取地区id
        initView();
        initRightDrawerLayout();
        initSearchResult();
        //开始搜索
        Intent intent = getIntent();
        if (intent != null) {
            secarchContext = intent.getStringExtra("SECARCHCONTEXT");
            secarchcarid = intent.getStringExtra("SECARCHCARID");
            secarchtoptab = intent.getBooleanExtra("SECARCHTOPTAB", false);
            topsearch.setHint(secarchContext);
            initTabLayout();
            if (secarchtoptab) {
                topsearch.setHint(getResources().getString(R.string.home_search));
                startSearchGet("", secarchcarid, "def_desc");
            } else {
                topsearch.setHint(secarchContext);
                startSearchGet(secarchContext, "", "def_desc");
            }
        }
    }

    /**
     * 搜索
     *
     * @param sort buynum_desc ；sort=buynum_asc 销量
     *             price_desc ； sort=price_asc价格
     *             time_desc ；sort=time_asc 发布时间
     *             play_desc ；sort=play_asc 浏览次数
     */
    String region;
    private void startSearchGet(String secarchContext, String carId, String sort) {
        sortOld = sort;
        //搜索
        Map<String, Object> params = new HashMap<String, Object>();
        if (secarchContext != null && !"".equals(secarchContext)) {
            params.put("keyword", secarchContext);//搜索的内容
        }
        if (carId != null && !"".equals(carId)) {
            params.put("cat", carId);//分类id
        }
        if (region == null||"".equals(region)) {//未登录
            params.put("region", "0");//当前地区id
        } else {
            params.put("region", region);//当前地区id
        }
        params.put("sort", sort);//搜索排列顺序
        if (ISCHECKEDYASN) {
            params.put("isYasn", "yes");//是否自营
        } else {
            params.put("isYasn", "no");//是否自营
        }
        if (cartype !=null&&!"".equals(cartype)) {//是否选择车型
            params.put("carbrand", String.valueOf(carTypeId));
        }
        params.put("page", String.valueOf(page));//页码
//                params.put("brand", brand);//品牌id
        okHttpGet(100, Config.SEARCH, params);

    }

    private void initSearchResult() {
        /**
         * 创建常购清单
         * 正式购物清单
         */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_serrch.setLayoutManager(mLinearLayoutManager);
        adapter = new SearchAdapter(this);
        adapter.setOnItemClickListener(this);
        recyclerview_serrch.setAdapter(adapter);
    }

    private void initView() {
        //返回按钮
        topbar_left = (LinearLayout) findViewById(R.id.topbar_left);
        topbar_left.setOnClickListener(this);
        topbar_right = (LinearLayout) findViewById(R.id.topbar_left);
        topbar_right.setOnClickListener(this);
        //搜索输入框
        topsearch = (TextView) findViewById(R.id.topsearch);
        topsearchLinear = (LinearLayout) findViewById(R.id.topsearchLinear);
        topsearchLinear.setOnClickListener(this);
        //顶部TAB
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        tablayout.addOnTabSelectedListener(this);
        //综合
        searchsynthesis = (TextView) findViewById(R.id.search_synthesis);
        searchsynthesis.setOnClickListener(this);
        searchsynthesis.setTextColor(ContextCompat.getColor(this, R.color.orange));
        //销量
        searchsalesvolume = (ArrowsSpinner) findViewById(R.id.search_salesvolume);
        searchsalesvolume.setOnClickListener(this);
        searchsalesvolume.setTextColor(ContextCompat.getColor(this, R.color.black_66));
        //价格
        searchmoney = (ArrowsSpinner) findViewById(R.id.search_money);
        searchmoney.setOnClickListener(this);
        searchmoney.setTextColor(ContextCompat.getColor(this, R.color.black_66));
        //筛选
        searchscreen = (TextView) findViewById(R.id.search_screen);
        searchscreen.setOnClickListener(this);
        //已选，开始隐藏
        selecttype = (LinearLayout) findViewById(R.id.selecttype);
        selecttype.setVisibility(View.GONE);
        yasnautofram = (FrameLayout) findViewById(R.id.yasnautofram);
        yasnautofram.setOnClickListener(this);
        cartypefram = (FrameLayout) findViewById(R.id.cartypefram);
        cartypefram.setOnClickListener(this);
        cartypetext = (TextView) findViewById(R.id.cartypetext);
        //搜索列表
        swipe_layout = (MultiSwipeRefreshLayout) findViewById(R.id.swipe_layout);
        //下拉刷新监听
        swipe_layout.setOnRefreshListener(this);
        swipe_layout.setMultiSwipeRefreshClickListener(this);
        //上拉加載监听
        swipe_layout.setOnLoadListener(this);
        //设置样式刷新显示的位置
        swipe_layout.setProgressViewOffset(true, -20, 100);
        swipe_layout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
        recyclerview_serrch = (RecyclerView) findViewById(R.id.recyclerview_serrch);
        recyclerview_serrch.setAlpha(1);
        recyclerview_serrch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isTop = recyclerView.canScrollVertically(-1);//返回false表示不能往下滑动，即代表到顶部了；
                if (isTop){
                    swipe_layout.setEnabled(false);
                }else {
                    swipe_layout.setEnabled(true);
                }
                boolean isBottom = recyclerView.canScrollVertically(1);//返回false表示不能往上滑动，即代表到底部了；
                if (isBottom){
                    swipe_layout.setBottom(false);
                    adapter.setShowProgressBar(false);
                }else {
                    swipe_layout.setBottom(true);
                    adapter.setShowProgressBar(true);
                }
            }
        });
        //搜索无结果
        ungoods_relat = (RelativeLayout) findViewById(R.id.ungoods_relat);
        ungoods_relat.setAlpha(0);
        //抽屉
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //右侧抽屉布局
        menu_layout_right = (LinearLayout) findViewById(R.id.menu_layout_right);
        WindowManager wm = this.getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
        int height1 = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams para = menu_layout_right.getLayoutParams();//获取drawerlayout的布局
        para.width = width1 / 5 * 4;//修改宽度
        para.height = height1;//修改高度
        menu_layout_right.setLayoutParams(para); //设置修改后的布局。
        backDrawer= (ImageView) findViewById(R.id.back);
        backDrawer.setOnClickListener(this);
        //是否自营
        selected_fram = (FrameLayout) findViewById(R.id.selected_fram);
        selected_fram.setOnClickListener(this);
        selected_yasn = (LinearLayout) findViewById(R.id.selected_yasn);
        selected_yasn.setAlpha(0);
        unselected_yasn = (TextView) findViewById(R.id.unselected_yasn);
        unselected_yasn.setAlpha(1);
        //重置
        reset = (TextView) findViewById(R.id.reset);
        reset.setOnClickListener(this);
        //完成
        ok = (TextView) findViewById(R.id.ok);
        ok.setOnClickListener(this);
    }

    private void initRightDrawerLayout() {
        seclectName = (TextView) findViewById(R.id.seclectName);
        drawlayoutsearch_rv = (RecyclerView) findViewById(R.id.drawlayoutsearch_rv);
        mComparator = new PinyinComparator();
        mSideBar = (WaveSideBar) findViewById(R.id.sideBar);

        //RecyclerView设置manager
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        drawlayoutsearch_rv.setLayoutManager(manager);
        mAdapter = new SortAdapter(this);
        mAdapter.setOnItemClickListener(this);
        drawlayoutsearch_rv.setAdapter(mAdapter);
        //自定义的分隔线
        drawlayoutsearch_rv.addItemDecoration(new RcDecoration(this,RcDecoration.VERTICAL_LIST));
        mAdapter.notifyDataSetChanged();
        //设置右侧SideBar触摸监听
        mSideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("uid", uid);
        okHttpGet(101, Config.SEARCHCARTYPE, params);
    }

    private void initTabLayout() {

        if (secarchtoptab) {
            tablayout.setVisibility(View.VISIBLE);
            Map<String, Object> params = new HashMap<String, Object>();
            okHttpGet(102, Config.CLASSIFY+"/"+secarchcarid, params);
        } else {
            tablayout.setVisibility(View.GONE);
        }
    }

    /**
     * 为RecyclerView填充数据
     */
    private List<SortModel> filledData(List<CarType.CarTypesBean> list) {

        List<SortModel> mSortList = new ArrayList<>();
        for (int i = 0, j = list.size(); i < j; i++) {
            SortModel sortModel = new SortModel();
            CarType.CarTypesBean carTypesBean = list.get(i);
            int carTypeId = carTypesBean.getCarTypeId();
            sortModel.setCarTypeId(carTypeId);
            String name = carTypesBean.getName();
            sortModel.setName(name);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(name);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    boolean priceArrow;
    boolean screenArrow;
    private int tabType = 1;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.topbar_left:
                finish();
                break;
            case R.id.topbar_right:
                SobotUtil.startSobot(this,null);
                break;
            case R.id.topsearchLinear://搜索
                Intent intent = new Intent(this, HotLableActivity.class);
                startActivity(intent);
                break;
            case R.id.search_synthesis://综合
                tabType = 1;
                searchsynthesis.setTextColor(ContextCompat.getColor(this, R.color.orange));
                searchsalesvolume.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchmoney.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchscreen.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                if (!sortOld.equals("def_desc")) {
                    page = 1;
                    if (secarchtoptab) {
                        startSearchGet("", secarchcarid, "def_desc");
                    } else {
                        startSearchGet(secarchContext, "", "def_desc");
                    }
                }
                searchsalesvolume.animateArrow(false);//重置销量箭头
                searchmoney.animateArrow(false);//重置价格箭头
                break;
            case R.id.search_salesvolume://销量
                tabType = 2;
                screenArrow = searchsalesvolume.isArrowHide();
                /**
                 * screenArrow = true;箭头朝下
                 */
                searchsynthesis.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchsalesvolume.setTextColor(ContextCompat.getColor(this, R.color.orange));
                searchmoney.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchscreen.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                page = 1;
                if (secarchtoptab) {
                    if (screenArrow) {
                        startSearchGet("", secarchcarid, "buynum_desc");
                    } else {
                        startSearchGet("", secarchcarid, "buynum_asc");
                    }
                } else {
                    if (screenArrow) {
                        startSearchGet(secarchContext, "", "buynum_desc");
                    } else {
                        startSearchGet(secarchContext, "", "buynum_asc");
                    }
                }
                searchmoney.animateArrow(false);//重置价格箭头
                break;
            case R.id.search_money://价格
                tabType = 3;
                priceArrow = searchmoney.isArrowHide();
                searchsynthesis.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchsalesvolume.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchmoney.setTextColor(ContextCompat.getColor(this, R.color.orange));
                searchscreen.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                page = 1;
                if (secarchtoptab) {
                    if (priceArrow) {
                        startSearchGet("", secarchcarid, "price_desc");
                    } else {
                        startSearchGet("", secarchcarid, "price_asc");
                    }
                } else {
                    if (priceArrow) {
                        startSearchGet(secarchContext, "", "price_desc");
                    } else {
                        startSearchGet(secarchContext, "", "price_asc");
                    }
                }
                searchsalesvolume.animateArrow(false);//重置销量箭头
                break;

            case R.id.search_screen://筛选
                drawer_layout.openDrawer(menu_layout_right);
                searchsynthesis.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchsalesvolume.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchmoney.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                searchscreen.setTextColor(ContextCompat.getColor(this, R.color.orange));
                break;

            case R.id.yasnautofram://雅森自营
                yasnautofram.setVisibility(View.GONE);
                selected_yasn.setAlpha(0);
                unselected_yasn.setAlpha(1);
                ISCHECKEDYASN = false;
                break;
            case R.id.cartypefram://车类型
                cartypefram.setVisibility(View.GONE);
                cartype = "";
                break;

            case R.id.selected_fram:
                if (!ISCHECKEDYASN) {
                    selected_yasn.setAlpha(1);
                    unselected_yasn.setAlpha(0);
                    ISCHECKEDYASN = true;
                } else {
                    selected_yasn.setAlpha(0);
                    unselected_yasn.setAlpha(1);
                    ISCHECKEDYASN = false;
                }
                break;
            case R.id.reset:
                selecttype.setVisibility(View.GONE);
                selected_yasn.setAlpha(0);
                unselected_yasn.setAlpha(1);
                ISCHECKEDYASN = false;
                cartype = "";
                seclectName.setText("");
                for (int i = 0; i < mDateList.size(); i++) {
                    SortModel sortModel = mDateList.get(i);
                    sortModel.setChecked(false);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.ok:
                Log.e("TAG_车型",""+ISCHECKEDYASN+";cartype="+cartype);
                if (ISCHECKEDYASN||(cartype !=null&&!"".equals(cartype))){
                    selecttype.setVisibility(View.VISIBLE);
                    drawer_layout.closeDrawer(menu_layout_right);
                    /**
                     * cartype 车型
                     * ISCHECKEDYASN 是否选中雅森自营 true选中
                     */
                    if (ISCHECKEDYASN) {
                        yasnautofram.setVisibility(View.VISIBLE);
                    } else {
                        yasnautofram.setVisibility(View.GONE);
                    }
                    if (cartype ==null||"".equals(cartype)) {
                        cartypefram.setVisibility(View.GONE);

                    } else {
                        cartypefram.setVisibility(View.VISIBLE);
                        cartypetext.setText(cartype);
                    }
                }else {
                    ToastUtil.showToast("选择条件不能空");
                }
                break;
            case R.id.back:
                drawer_layout.closeDrawer(menu_layout_right);
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int selectPosition = tab.getPosition();
        Log.e("TAG_", selectPosition + "");
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private List<SearchModel.DataBean> data;

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200){
                    SearchModel searchmodel = JSON.parseObject(returnData, SearchModel.class);
                    data = searchmodel.getData();
                    recyclerview_serrch.setVisibility(View.VISIBLE);
                    ungoods_relat.setVisibility(View.GONE);

                    if (isDownPull) {
                        swipe_layout.setRefreshing(false);
                        isDownPull = false;
                    }
                    if (page >1) {
                        isUpPull = false;
                        swipe_layout.setLoading(false);
                        adapter.addData(data);
                    } else {
                        adapter.setData(data);
                    }
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101:
                if (returnCode == 200) {
                    CarType cartype = JSON.parseObject(returnData, CarType.class);
                    List<CarType.CarTypesBean> carTypes = cartype.getCarTypes();
                    if (carTypes != null) {
                        mDateList = filledData(carTypes);
                        // 根据a-z进行排序源数据
                        Collections.sort(mDateList, mComparator);

                        mAdapter.setData(mDateList);
                        mDecoration = new TitleItemDecoration(this, mDateList);
                        //如果add两个，那么按照先后顺序，依次渲染。
                        drawlayoutsearch_rv.addItemDecoration(mDecoration);
                        drawlayoutsearch_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
                    }
                }else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 102:
                if (returnCode == 200) {
                    SearchCatsModel searchCatsModel = JSON.parseObject(returnData, SearchCatsModel.class);
                    List<SearchCatsModel.CatsBean> cats = searchCatsModel.getCats();
                    Integer integer = Integer.valueOf(secarchcarid);
                    //title 赋值
                    for (int i = 0, j = cats.size(); i < j; i++) {
                        SearchCatsModel.CatsBean catsBean = cats.get(i);
                        String name = catsBean.getName();
                        int cat_id = catsBean.getCat_id();
                        if (integer == cat_id){
                            tablayout.addTab(tablayout.newTab().setText(name == null ? "" : name),true);
                        }else {
                            tablayout.addTab(tablayout.newTab().setText(name == null ? "" : name),false);
                        }
                    }
                }else {
                    ToastUtil.showToast(returnMsg);
                }

                break;
        }
    }

    @Override
    public void onCancelResult() {

        if (isDownPull) {
            swipe_layout.setRefreshing(false);
            isDownPull = false;

        }
        if (isUpPull) {
            isUpPull = false;
            swipe_layout.setLoading(false);
        }
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

        if (isDownPull) {
            swipe_layout.setRefreshing(false);
            isDownPull = false;

        }
        if (isUpPull) {
            isUpPull = false;
            swipe_layout.setLoading(false);
        }
    }

    @Override
    public void onParseErrorResult(int errorCode) {

        if (isDownPull) {
            swipe_layout.setRefreshing(false);
            isDownPull = false;

        }
        if (isUpPull) {
            isUpPull = false;
            swipe_layout.setLoading(false);
        }
    }

    @Override
    public void onFinishResult() {

        if (isDownPull) {
            swipe_layout.setRefreshing(false);
            isDownPull = false;

        }
        if (isUpPull) {
            isUpPull = false;
            swipe_layout.setLoading(false);
        }
    }
    //车型item
    @Override
    public void onItemClick(View view, int position) {

        for (int i = 0; i < mDateList.size(); i++) {
            SortModel sortModel = mDateList.get(i);
            if (i == position) {
                carTypeId = sortModel.getCarTypeId();
                sortModel.setChecked(true);
                cartype = sortModel.getName();
                sortModel.getLetters();
                seclectName.setText(cartype);
            } else {
                sortModel.setChecked(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
    //商品列表item
    @Override
    public void OnItemClick(View view, int position) {
        List<SearchModel.DataBean> data = adapter.getData();
        if (data!=null&&data.size()>0){
            SearchModel.DataBean dataBean = data.get(position);
            if (dataBean !=null){
                String id = dataBean.getId();
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("webViewUrl",Config.GOODSDETAILS+id);
                startActivity(intent);
            }
        }
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

    @Override
    public void OnMultiSwipeRefreshClick() {
    }

    @Override
    public void onRefresh() {
        page = 1;
        swipe_layout.setRefreshing(true);
        isDownPull = true;
        if (secarchtoptab) {
            if (tabType ==1){
                startSearchGet("", secarchcarid, "def_desc");
            }else if (tabType ==2){
                searchsalesvolume.animateArrow(true);//重置销量箭头
                startSearchGet("", secarchcarid, "buynum_desc");
            }else if (tabType ==3){
                searchmoney.animateArrow(true);//重置价格箭头
                startSearchGet("", secarchcarid, "price_desc");
            }
        } else {
            if (tabType ==1){
                startSearchGet(secarchContext, "", "def_desc");
            }else if (tabType ==2){
                searchsalesvolume.animateArrow(true);//重置销量箭头
                startSearchGet(secarchContext, "", "buynum_desc");
            }else if (tabType ==3){
                searchmoney.animateArrow(true);//重置价格箭头
                startSearchGet(secarchContext, "", "price_desc");
            }
        }
    }

    @Override
    public void onLoad() {
        if (recyclerview_serrch!=null&&recyclerview_serrch.getAdapter() != null){
            isUpPull = true;
            swipe_layout.setLoading(true);
            page++;
            if (secarchtoptab) {
                if (tabType ==1){
                    startSearchGet("", secarchcarid, "def_desc");
                }else if (tabType ==2){
                    searchsalesvolume.animateArrow(true);//重置销量箭头
                    if (screenArrow) {
                        startSearchGet("", secarchcarid, "buynum_desc");
                    } else {
                        startSearchGet("", secarchcarid, "buynum_asc");
                    }
                }else if (tabType ==3){
                    searchmoney.animateArrow(true);//重置价格箭头
                    startSearchGet("", secarchcarid, "price_desc");
                }
            } else {
                if (tabType ==1){
                    startSearchGet("", secarchcarid, "def_desc");
                }else if (tabType ==2){
                    searchsalesvolume.animateArrow(true);//重置销量箭头
                    startSearchGet("", secarchcarid, "buynum_desc");
                }else if (tabType ==3){
                    searchmoney.animateArrow(true);//重置价格箭头
                    if (priceArrow) {
                        startSearchGet(secarchContext, "", "price_desc");;
                    } else {
                        startSearchGet(secarchContext, "", "price_asc");
                    }
                }
            }
        }
    }
}
