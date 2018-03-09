package com.yasn.purchase.goods.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.GoodsDetailsActivity;
import com.yasn.purchase.activity.showbig.ShowBigPictrueActivitiy;
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.adapter.SimpleRecyclerAdapter;
import com.yasn.purchase.adapter.TradePriceAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.goods.adapter.BannerHolderView;
import com.yasn.purchase.goods.view.SlideDetailsLayout;
import com.yasn.purchase.model.GoodsDetailsModel;
import com.yasn.purchase.model.GoodsDetailsOtherModel;
import com.yasn.purchase.utils.AlignedTextUtils;
import com.yasn.purchase.utils.HtmlImageGetter;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.FlowLayout;
import com.yasn.purchase.view.RcDecoration;
import com.yasn.purchase.view.ShoppingSelectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.utils.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static www.xcd.com.mylibrary.utils.HelpUtils.getDateToString;
import static www.xcd.com.mylibrary.utils.SharePrefHelper.context;


/**
 * item页ViewPager里的商品Fragment
 */
public class GoodsInfoFragment extends BaseFragment implements
        ShoppingSelectView.OnSelectedListener, OnItemClickListener,
        SlideDetailsLayout.OnSlideDetailsListener, MediaPlayer.OnPreparedListener {

    private RelativeLayout topbat_parent;
    ConvenientBanner banner;
    TextView goodsDetail;
    TextView goodsConfig;
    View tabCursor;
    SlideDetailsLayout slideDetailsLayout;
    ScrollView scrollView;

    private List<TextView> tabTextList = new ArrayList<>();
    private int currIndex = 0;
    public GoodsDetailsActivity activity;
    private float fromX;
    private TextView autotrophy, purchase, presell, title;
    private TextView viewpage_number;
    private long time = 2592000;
    /**
     * 活动折扣提示内容
     */
    private TextView highprofit_action;
    /**
     * 卖点
     *
     * @param context
     */
    private LinearLayout sellinglinear;
    private TextView sellingpoints,sellingview;

    /**
     * 规格
     *
     * @param context
     */
    private TextView specs;
    private ShoppingSelectView label_include;

    /**
     * 折扣，内容
     * 布局
     *
     * @param context
     */
    private TextView discount, discountview;
    private LinearLayout discountLinear;

    /**
     * 顶部限购 限购价 正常价 已售数量
     * 促销
     * 包邮 HOT 限购 预售
     * 正常已售数量
     *
     * @param context
     */
    private LinearLayout top_purchasepromotion;
    private TextView soldout_money, originalprice, soldOut, purchase_time;
    private TextView promotion, unpostage, hot, purchase_promotion, presell_promotion;
    private LinearLayout promotionlinear, unpostage_linear, hot_linear, purchase_linear, presell_linear;
    private LinearLayout sold_Linear;
    private TextView originalprice2, soldOut2;
    /**
     * 批发价
     *
     * @param context
     */
    private TextView tradeprice, minNumber;
    private RecyclerView tradeprice_recy;
    private LinearLayout llLadderPrices;

    /**
     * 建议售价
     *
     * @param context
     */
    private TextView retailPrice, retailPriceView;

    /**
     * 购买数量
     * 剩余库存
     *
     * @param context
     */
    private EditText etGoodsNum;
    private LinearLayout ivSubtractNum, ivAddNum;
    private int smallSale = 0;//最小起订量
    private int enableStoreNum;//库存
    private TextView enableStore;
    //是否雅森自营提示语
    private TextView storeName;
    //七天退货
    private TextView salesReturn;
    //车型
    private TextView carTypes;
    private LinearLayout carTypesLinear, pull_up_view;
    //商品属性列表adapter
    private SimpleRecyclerAdapter simpleadapter;
    //商品属性列表
    private RecyclerView attributesList;
    private TextView htmlTextView, undata;

    /**
     * 音频布局
     *
     * @param context
     */
    private RelativeLayout voice;
    private TextView voice_time;
    private ImageView voice_start;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    String goodsidString;

    protected void OkHttpDemand() {
        goodsidString = SharePrefHelper.getInstance(getActivity()).getSpString("GOODSID");
        Map<String, Object> params = new HashMap<String, Object>();
        String token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
        String resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(100, Config.GOODSDETAILS + goodsidString, params);
        //读取临时文件
//        String goodsdetailsmodel = HelpUtils.getJson(getActivity(), "goods.json");
        Map<String, Object> params1 = new HashMap<String, Object>();
        okHttpGet(101, Config.GOODSDETAILSOTHER + goodsidString, params1);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        OkHttpDemand();
    }

    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
//            onVisible();
        } else {
            isVisible = false;
            if (mediaplayer != null) {
                mediaplayer.pause();
                flag = false;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailsActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods_info;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        init(view);
        initTradePricePosition(null, -1);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
    }

    private void initSoldNumber(TextView textView, String number, int textColor) {
        String minNumberString = String.format("已售%s笔", number);
        SpannableStringBuilder span = new SpannableStringBuilder(minNumberString);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), textColor)), 0, 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), textColor)), minNumberString.length() - 1, minNumberString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    //本数据的代码可以再优化，写到另一个Controller处理
    private void init(View rootView) {
        initGoodsView(rootView);
        initTabView();
        initGoodsTitleContent(null);
        initTradePrice();
        createDialog();
    }

    private void initGoodsView(View rootView) {
        topbat_parent = (RelativeLayout) rootView.findViewById(R.id.topbat_parent);
        topbat_parent.setVisibility(View.GONE);
        banner = (ConvenientBanner) rootView.findViewById(R.id.banner);
        viewpage_number = (TextView) rootView.findViewById(R.id.viewpage_number);
        //音频布局
        voice = (RelativeLayout) rootView.findViewById(R.id.voice);
        voice.setOnClickListener(this);
        voice_time = (TextView) rootView.findViewById(R.id.voice_time);
        voice_start = (ImageView) rootView.findViewById(R.id.voice_start);
        //顶部限购
        top_purchasepromotion = (LinearLayout) rootView.findViewById(R.id.top_purchasepromotion);
        soldout_money = (TextView) rootView.findViewById(R.id.soldout_money);
        originalprice = (TextView) rootView.findViewById(R.id.originalprice);
        originalprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        soldOut = (TextView) rootView.findViewById(R.id.soldOut);
        purchase_time = (TextView) rootView.findViewById(R.id.purchase_time);
        //正常已售数量
        sold_Linear = (LinearLayout) rootView.findViewById(R.id.sold_Linear);
        originalprice2 = (TextView) rootView.findViewById(R.id.originalprice2);
        originalprice2.setOnClickListener(this);
        soldOut2 = (TextView) rootView.findViewById(R.id.soldOut2);
        initSoldNumber(soldOut2, "0", R.color.black_66);
        //内容
        title = (TextView) rootView.findViewById(R.id.title);
        autotrophy = (TextView) rootView.findViewById(R.id.autotrophy);
        purchase = (TextView) rootView.findViewById(R.id.purchase);
        presell = (TextView) rootView.findViewById(R.id.presell);
        //折扣提示内容
        highprofit_action = (TextView) rootView.findViewById(R.id.highprofit_action);
        //零售价
        retailPrice = (TextView) rootView.findViewById(R.id.retailPrice);
        retailPriceView = (TextView) rootView.findViewById(R.id.retailPriceView);
//        SpannableString retailPriceString = AlignedTextUtils.formatText("建议售价:");
        SpannableStringBuilder retailPriceString = AlignedTextUtils.justifyString("建议售价", 4);
        retailPriceString.append("：");
        retailPrice.setText(retailPriceString);
        //批发价、折扣价
        llLadderPrices = (LinearLayout) rootView.findViewById(R.id.ll_ladderPrices);
        tradeprice = (TextView) rootView.findViewById(R.id.tradeprice);
        minNumber = (TextView) rootView.findViewById(R.id.minNumber);

//        SpannableString tradepriceString = AlignedTextUtils.formatText("批发价:");
        SpannableStringBuilder tradepriceString = AlignedTextUtils.justifyString("批发价", 4);
        tradepriceString.append("：");
        tradeprice.setText(tradepriceString);
        tradeprice_recy = (RecyclerView) rootView.findViewById(R.id.tradeprice_recy);
        //卖点
        sellinglinear = (LinearLayout) rootView.findViewById(R.id.sellinglinear);
        sellingpoints = (TextView) rootView.findViewById(R.id.sellingpoints);
        sellingview  = (TextView) rootView.findViewById(R.id.sellingview);
//        SpannableString sellingString = AlignedTextUtils.formatText("卖点:");
        SpannableStringBuilder sellingString = AlignedTextUtils.justifyString("卖点", 4);
        sellingString.append("：");
        sellingpoints.setText(sellingString);
        //规格
        specs = (TextView) rootView.findViewById(R.id.specs);
//        SpannableString specsString = AlignedTextUtils.formatText("规  格:");
        SpannableStringBuilder specsString = AlignedTextUtils.justifyString("规格", 4);
        specsString.append("：");
        specs.setText(specsString);
        //折扣
        discount = (TextView) rootView.findViewById(R.id.discount);
//        SpannableString discountString = AlignedTextUtils.formatText("折  扣:");
        SpannableStringBuilder discountString = AlignedTextUtils.justifyString("折扣", 4);
        discountString.append("：");
        discount.setText(discountString);
        discountview = (TextView) rootView.findViewById(R.id.discountview);
        discountLinear = (LinearLayout) rootView.findViewById(R.id.discountLinear);
        //促销
        promotion = (TextView) rootView.findViewById(R.id.promotion);
//        SpannableString promotionString = AlignedTextUtils.formatText("促  销:");
        SpannableStringBuilder promotionString = AlignedTextUtils.justifyString("促销", 4);
        promotionString.append("：");
        promotion.setText(promotionString);
        //购买数量
        etGoodsNum = (EditText) rootView.findViewById(R.id.et_goodsNum);
        ivSubtractNum = (LinearLayout) rootView.findViewById(R.id.iv_subtractNum);
        ivSubtractNum.setOnClickListener(this);
        ivAddNum = (LinearLayout) rootView.findViewById(R.id.iv_addNum);
        ivAddNum.setOnClickListener(this);
        enableStore = (TextView) rootView.findViewById(R.id.enableStore);
        unpostage = (TextView) rootView.findViewById(R.id.unpostage);
        hot = (TextView) rootView.findViewById(R.id.hot);
        purchase_promotion = (TextView) rootView.findViewById(R.id.purchase_promotion);
        presell_promotion = (TextView) rootView.findViewById(R.id.presell_promotion);
        promotionlinear = (LinearLayout) rootView.findViewById(R.id.promotionlinear);
        promotionlinear.setVisibility(View.VISIBLE);
        unpostage_linear = (LinearLayout) rootView.findViewById(R.id.unpostage_linear);
        hot_linear = (LinearLayout) rootView.findViewById(R.id.hot_linear);
        purchase_linear = (LinearLayout) rootView.findViewById(R.id.purchase_linear);
        presell_linear = (LinearLayout) rootView.findViewById(R.id.preselle_linear);
        //规格标签
        label_include = (ShoppingSelectView) rootView.findViewById(R.id.label_include);
        label_include.setOnSelectedListener(this);
        //
        storeName = (TextView) rootView.findViewById(R.id.storeName);
        //七天退货
        salesReturn = (TextView) rootView.findViewById(R.id.salesReturn);
        //适用车型
        carTypes = (TextView) rootView.findViewById(R.id.carTypes);
        carTypesLinear = (LinearLayout) rootView.findViewById(R.id.carTypesLinear);
        carTypesLinear.setOnClickListener(this);
        //商品属性列表
        attributesList = (RecyclerView) rootView.findViewById(R.id.attributesList);
        simpleadapter = new SimpleRecyclerAdapter(getActivity());
        attributesList.setAdapter(simpleadapter);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        attributesList.setLayoutManager(mLinearLayoutManager);
        //上拉文本
        pull_up_view = (LinearLayout) rootView.findViewById(R.id.pull_up_view);
        pull_up_view.setOnClickListener(this);
        //底部上拉内容
        undata = (TextView) rootView.findViewById(R.id.undata);
        htmlTextView = (TextView) rootView.findViewById(R.id.htmlText);
        htmlTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

        slideDetailsLayout = (SlideDetailsLayout) rootView.findViewById(R.id.slideDetailsLayout);
        slideDetailsLayout.setOnSlideDetailsListener(this);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
    }

    private void initGoodsTitleContent(GoodsDetailsModel.GoodsDetailsBean goodsDetails) {
        if (goodsDetails == null) {
            autotrophy.setVisibility(View.GONE);
            purchase.setVisibility(View.GONE);
            presell.setVisibility(View.GONE);
            title.setText("");
            return;
        }
        StringBuffer sb = new StringBuffer();
        int goneNum = 0;
        String goodsDetailsName = goodsDetails.getName();
        String storeNameString = "";
        int storeId = goodsDetails.getStoreId();
        if (storeId == 1) {
            autotrophy.setVisibility(View.VISIBLE);
            goneNum += 3;
            sb.append("自营 ");
            storeNameString = " 雅森 ";
            salesReturn.setVisibility(View.VISIBLE);
        } else {
            autotrophy.setVisibility(View.GONE);
            storeNameString = " 厂家 ";
            salesReturn.setVisibility(View.GONE);
        }
        String minNumberString = String.format("该商品由%s发货及售后", storeNameString);
        SpannableStringBuilder storeNameSpan = new SpannableStringBuilder(minNumberString);
        storeNameSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.black_33)), 4, 8,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        storeName.setText(storeNameSpan);

        int isLimitBuy = goodsDetails.getIsLimitBuy();
        if (isLimitBuy == 1) {
            purchase.setVisibility(View.VISIBLE);
            sb.append("限购 ");
            goneNum += 3;
        } else {
            purchase.setVisibility(View.GONE);
        }
        int isBeforeSale = goodsDetails.getIsBeforeSale();
        if (isBeforeSale == 1) {
            presell.setVisibility(View.VISIBLE);
            goneNum += 3;
            sb.append("预售 ");
        } else {
            purchase.setVisibility(View.GONE);
        }
        SpannableStringBuilder span = new SpannableStringBuilder(sb + goodsDetailsName);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.white)), 0, goneNum,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        title.setText(span);
        Log.e("TAG_title", "title=" + title.getText().toString());
    }

    private void initTabView() {
        tabTextList.add(goodsDetail);
        tabTextList.add(goodsConfig);
    }

    private boolean flag = true;
    private int duration = 0;

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.carTypesLinear:
                if (carTypesList == null) {
                    return;
                }
                int size = carTypesList.size();
                if (size == 1) {
                    return;
                }
                if (size > 1) {
                    showPopwindow(carTypesList);
                }
                break;
            case R.id.pull_up_view:
                slideDetailsLayout.smoothOpen(true);
                break;
            case R.id.voice:
                if (mediaplayer == null) {
                    return;
                }
                if (!mediaplayer.isPlaying()) {
                    voice_start.setBackgroundResource(R.mipmap.voice_stop);
                    mediaplayer.start();
                    flag = true;
                    handler.postDelayed(runnableVoice, 1000);
                } else {
                    voice_start.setBackgroundResource(R.mipmap.voice_start);
                    mediaplayer.pause();
                    flag = false;
                }
                break;
            case R.id.iv_subtractNum:
                int subtractNum = Integer.valueOf(etGoodsNum.getText().toString().trim()) - smallSale;
                if (subtractNum <= 0) {
                    etGoodsNum.setText("0");
                } else {
                    etGoodsNum.setText(String.valueOf(subtractNum));
                }
                break;
            case R.id.iv_addNum:
                if (enableStoreNum == 0){
                    ToastUtil.showToast("库存不足");
                }else {
                    int addNum = Integer.valueOf(etGoodsNum.getText().toString().trim()) ;
                    if (smallSale > 0 ) {
                        int allAddNum = addNum + smallSale;
                        if (allAddNum <= enableStoreNum){
                            etGoodsNum.setText(String.valueOf(allAddNum));
                        }else {
                            ToastUtil.showToast("库存不足");
                        }
                    } else {
                        int allAddNum = addNum + 1;
                        if (allAddNum <= enableStoreNum) {
                            etGoodsNum.setText(String.valueOf(allAddNum));
                        } else {
                            ToastUtil.showToast("库存不足");
                        }
                    }
                }

                break;
            case R.id.originalprice2:
                String trim = originalprice2.getText().toString().trim();
                if ("登录看价格".equals(trim)){
                    startWebViewActivity(Config.LOGINWEBVIEW);
                }else if ("认证看价格".equals(trim)){
                    startWebViewActivity(Config.ATTESTATION);
                }
                break;
        }
    }

    Runnable runnableVoice = new Runnable() {
        @Override
        public void run() {
            if (flag) {
                duration -= 1000;
                String formatLongToTimeStr = HelpUtils.formatLongToTime((long) duration);
                voice_time.setText(formatLongToTimeStr);
                if (duration > 0) {
                    handler.postDelayed(this, 1000);
                } else {
                    handler.removeCallbacks(runnableVoice);
                }
            }
        }
    };

    /**
     * 显示popupWindow
     */
    private void showPopwindow(List<GoodsDetailsModel.GoodsDetailsBean.CarTypesBean> carTypesList) {
        int[] screenSize = HelpUtils.getScreenSize(getActivity());
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindowlayout, null);
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.white));
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //添加控件绑定并配置适配器
        TextView ok = (TextView) view.findViewById(R.id.ok);
        ListView goodsCartlist = (ListView) view.findViewById(R.id.goodsCarType);
        //类似如此添加监听事件
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        List<String> cartypeList = new ArrayList();
        for (GoodsDetailsModel.GoodsDetailsBean.CarTypesBean cartypesbean : carTypesList) {
            cartypeList.add(cartypesbean.getCarName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), R.layout.goodscartypeitem, cartypeList);
        goodsCartlist.setAdapter(adapter);
        if (carTypesList != null) {
            int listViewHeight = setListViewHeight(goodsCartlist, adapter, carTypesList.size());
            int height = (int) (screenSize[1] * 0.4);
            if (listViewHeight > (screenSize[1] * 0.4)) {
                ViewGroup.LayoutParams params = goodsCartlist.getLayoutParams();
                params.height = height;
                params.width = screenSize[0];
                goodsCartlist.setLayoutParams(params);
            }
        }
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });
    }

    /**
     * 动态设置listView的高度 count 总条目
     */
    private int setListViewHeight(ListView listView, ArrayAdapter<String> adapter, int count) {
        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * count);
        return params.height;
    }

    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, currIndex * tabCursor.getWidth(), 0, 0);
        anim.setFillAfter(true);
        anim.setDuration(50);
        fromX = currIndex * tabCursor.getWidth();
        tabCursor.startAnimation(anim);

        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == currIndex ? getResources().getColor(R.color.red) : getResources().getColor(R.color.black));
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            String formatLongToTimeStr = HelpUtils.formatLongToTimeStr(time);
            int timeLength = formatLongToTimeStr.length();
            Log.e("TAG_详情", "时间=" + formatLongToTimeStr);
            SpannableStringBuilder style = new SpannableStringBuilder(formatLongToTimeStr);
            int blacktop_bar = ContextCompat.getColor(getActivity(), R.color.top_bar_background);
            style.setSpan(new BackgroundColorSpan(blacktop_bar), 0, timeLength - 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.WHITE), 0, timeLength - 10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            style.setSpan(new BackgroundColorSpan(blacktop_bar), timeLength - 9, timeLength - 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.WHITE), timeLength - 9, timeLength - 7, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            style.setSpan(new BackgroundColorSpan(blacktop_bar), timeLength - 6, timeLength - 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.WHITE), timeLength - 6, timeLength - 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            style.setSpan(new BackgroundColorSpan(blacktop_bar), timeLength - 3, timeLength - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(Color.WHITE), timeLength - 3, timeLength - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            purchase_time.setText(style);

            if (time > 0) {
                handler.postDelayed(this, 1000);
            } else {
                handler.removeCallbacks(runnable);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG_详情", "调用onPause");
        if (mediaplayer != null) {
            mediaplayer.pause();
            flag = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler.removeCallbacks(runnableVoice);
            handler.removeCallbacksAndMessages(null);
        }
    }

    //根据规格显示的商品列表
    List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean> products;
    //商品详情数据
    GoodsDetailsModel.GoodsDetailsBean goodsDetails;

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    succeedResult(returnData);
                } else  if (returnCode == 401){
                    cleanToken();
                    OkHttpDemand();
                }else {
                    ((GoodsDetailsActivity) getActivity()).setViewPagerGone();
                    ToastUtil.showToast(returnMsg);
                }
                break;
            case 101:
                if (returnCode == 200) {
                    GoodsDetailsOtherModel goodsdetailsothermodel = JSON.parseObject(returnData, GoodsDetailsOtherModel.class);
                    GoodsDetailsOtherModel.GoodsIntroBean goodsIntro = goodsdetailsothermodel.getGoodsIntro();
                    String intro = goodsIntro.getIntro();
                    if (intro == null || "".equals(intro)) {
                        undata.setVisibility(View.VISIBLE);
                    } else {
                        HtmlImageGetter htmlImageGetter = new HtmlImageGetter(getActivity(), htmlTextView);
                        Spanned spanned = Html.fromHtml(intro, htmlImageGetter, null);
                        htmlTextView.setText(spanned);
                        undata.setVisibility(View.GONE);
                    }

                } else {
                    undata.setVisibility(View.VISIBLE);
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }
    }

    //车型集合
    List<GoodsDetailsModel.GoodsDetailsBean.CarTypesBean> carTypesList;

    private void succeedResult(String returnData) {
        GoodsDetailsModel goodsdetailsmodel = JSON.parseObject(returnData, GoodsDetailsModel.class);
        //进货单数量
        GoodsDetailsModel.MemberBean member = goodsdetailsmodel.getMember();
        if (member !=null&&!"".equals(member)){
            int cartNum = member.getCartCount();
            if (callBack != null) {
                callBack.setCartNum(cartNum);
            }
        }

        goodsDetails = goodsdetailsmodel.getGoodsDetails();
        //音频布局
        initVoice();
        //设置临时价格
        String loginState = SharePrefHelper.getInstance(getActivity()).getSpString("loginState");
        if ("0".equals(loginState)) {
            double price = goodsDetails.getPrice();
            soldout_money.setText("￥" + String.valueOf(price));
            originalprice.setText("￥" + String.valueOf(price));
            originalprice2.setText("￥" + String.valueOf(price));
            //规格数据
            label_include.setData(goodsDetails);
            //批发价
            products = goodsDetails.getProducts();
            initTradePricePosition(products, 0);
        } else {
            soldout_money.setText(loginState);
            originalprice.setText(loginState);
            originalprice2.setText(loginState);
        }
        //
        String advert = goodsDetails.getAdvert();
        if (advert == null || "".equals(advert)) {
            highprofit_action.setVisibility(View.GONE);
        } else {
            highprofit_action.setText(advert);
            highprofit_action.setVisibility(View.VISIBLE);
        }

        //已售数量
        int totalBuyCount = goodsDetails.getTotalBuyCount();
        initSoldNumber(soldOut, String.valueOf(totalBuyCount), R.color.white);
        initSoldNumber(soldOut2, String.valueOf(totalBuyCount), R.color.black_66);
        //轮播图
        initBannerView(goodsDetails);
        //设置title
        initGoodsTitleContent(goodsDetails);
        //卖点
        String subTitle = goodsDetails.getSubTitle();
        if (TextUtils.isEmpty(subTitle)){
            sellinglinear.setVisibility(View.GONE);
        }else {
            sellinglinear.setVisibility(View.VISIBLE);
            sellingview.setText(subTitle);
        }
        //折扣是否可用
        int pointUsable = goodsDetails.getPointUsable();// 是否可用积分, 0否1是
        if (pointUsable == 1) {
            discountLinear.setVisibility(View.VISIBLE);
            int point = goodsDetails.getPoint();
            String discountString = String.format("积分可折扣 %s%s", point, "%");
            discountview.setText(discountString);
        } else {
            discountLinear.setVisibility(View.GONE);
        }
        //促销数据
        initPromotion(goodsDetails);
        //车型carTypes
        carTypesList = goodsDetails.getCarTypes();
        if (carTypesList == null || carTypesList.size() == 0) {
            return;
        }
        int size = carTypesList.size();
        if (size == 1) {
            carTypes.setText(carTypesList.get(0).getCarName());
        } else {
            carTypes.setText("多款车型");
        }
        //商品属性列表
        List<GoodsDetailsModel.GoodsDetailsBean.AttributesBean> attributes = goodsDetails.getAttributes();
        if (attributes != null && attributes.size() > 0) {
            initAttributes(attributes);
        }
        ((GoodsDetailsActivity) getActivity()).tempSaveFragmentShare(goodsDetails.getName(),
                Config.GOODSDETAILSWEB + goodsidString,
                goodsDetails.getGoodsGallerys().get(0).getThumbnail()
        );
        int isFavorite = goodsdetailsmodel.getIsFavorite();
        if (isFavorite == 0) {
            ((GoodsDetailsActivity) getActivity()).setCollectImage(false);
        } else if (isFavorite == 1) {
            ((GoodsDetailsActivity) getActivity()).setCollectImage(true);
        } else {
            ((GoodsDetailsActivity) getActivity()).setCollectImage(false);
        }
    }

    private void initAttributes(List<GoodsDetailsModel.GoodsDetailsBean.AttributesBean> attributes) {
        simpleadapter.setData(getSimpleAdapterData(attributes));
    }

    private List<Map<String, String>> getSimpleAdapterData(List<GoodsDetailsModel.GoodsDetailsBean.AttributesBean> attributes) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (GoodsDetailsModel.GoodsDetailsBean.AttributesBean attributesbean : attributes) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("attrName", attributesbean.getAttrName());
            map.put("attrValue", attributesbean.getAttrValue());
            list.add(map);
        }
        return list;
    }

    private TradePriceAdapter adapter;

    private void initTradePrice() {

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tradeprice_recy.setLayoutManager(mLinearLayoutManager);
        //实例化adapter
        adapter = new TradePriceAdapter(getActivity());
        tradeprice_recy.setAdapter(adapter);
        RcDecoration rcDecoration = new RcDecoration(getActivity(), RcDecoration.HORIZONTAL_LIST);
        tradeprice_recy.addItemDecoration(rcDecoration);
    }

    private int productId;//商品规格id

    private void initTradePricePosition(List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean> products, int position) {
        if (position == -1) {
            enableStore.setVisibility(View.GONE);
            return;
        }
        GoodsDetailsModel.GoodsDetailsBean.ProductsBean productsBean;
        enableStore.setVisibility(View.VISIBLE);
        List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean> ladderPrices;

        if (products != null) {
            productsBean = products.get(position);
            productId = productsBean.getProductId();
            //最小起订量
            smallSale = productsBean.getSmallSale();
            enableStoreNum = productsBean.getEnableStore();
            if (enableStoreNum > 5) {
                enableStore.setText("库存:充足");
                ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true);
            } else {
                enableStore.setText("库存:" + String.valueOf(enableStoreNum));
                if (enableStoreNum == 0) {
                    ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(false);
                } else {
                    ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true);
                }
            }
//            etGoodsNum.setText(String.valueOf(smallSale));
            //阶梯价
            ladderPrices = productsBean.getLadderPrices();
            if (ladderPrices != null&&ladderPrices.size()>0) {
                llLadderPrices.setVisibility(View.VISIBLE);
                //折扣价数据
                adapter.setData(ladderPrices);
                double goodsNumDou = Long.valueOf(etGoodsNum.getText().toString());
                for (int i = 0, k = ladderPrices.size(); i < k; i++) {
                    GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean ladderPricesBean = ladderPrices.get(i);
                    int minNum = ladderPricesBean.getMinNum();
                    int maxNum = ladderPricesBean.getMaxNum();
                    if (goodsNumDou >= minNum && goodsNumDou <= maxNum) {
                        String activityPrice = ladderPricesBean.getActivityPrice();
                        if (activityPrice == null || "".equals(activityPrice) || "0.00".equals(activityPrice)) {
                            double wholesalePrice = ladderPricesBean.getWholesalePrice();
                            soldout_money.setText("￥" + String.valueOf(wholesalePrice));
                            originalprice.setText("￥" + String.valueOf(wholesalePrice));
                            originalprice2.setText("￥" + String.valueOf(wholesalePrice));
                        } else {
                            double wholesalePrice = ladderPricesBean.getWholesalePrice();
                            soldout_money.setText("￥" + activityPrice);
                            originalprice.setText("￥" + String.valueOf(wholesalePrice));
                            originalprice2.setText("￥" + activityPrice);
                        }
                    }
                }
            } else {
                llLadderPrices.setVisibility(View.GONE);
                String activityPrice = productsBean.getActivityPrice();
                if (activityPrice != null && !"".equals(activityPrice) || "0.00".equals(activityPrice)) {
                    soldout_money.setText("￥" + activityPrice);
                    originalprice.setText("￥" + activityPrice);
                    originalprice2.setText("￥" + activityPrice);
                } else {
                    String price = String.valueOf(productsBean.getPrice());
                    soldout_money.setText("￥" + price);
                    originalprice.setText("￥" + price);
                    originalprice2.setText("￥" + price);
                }
            }
        } else {
            smallSale = 0;
//            etGoodsNum.setText(String.valueOf(smallSale));
        }
        String minNumberString = String.format("最小起订量:%s  %s件起批", smallSale, smallSale);
        SpannableStringBuilder span = new SpannableStringBuilder(minNumberString);
        span.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_text14_black_66), 0, 6,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_text14_black_66), minNumberString.length() - 2, minNumberString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        minNumber.setText(span);
    }

    private void initBannerView(GoodsDetailsModel.GoodsDetailsBean goodsdetailsmodelData) {
        banner.setCanLoop(true);
        final List<GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean> goodsGallerys = goodsdetailsmodelData.getGoodsGallerys();
        //初始化商品图片轮播
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView(viewpage_number, goodsGallerys);
            }
        }, goodsGallerys)
                .setOnItemClickListener(this)
//                .startTurning(2000)
        ;
        banner.setCanLoop(false);
    }

    private int PROMOTIONVISIBILIT = 0;
    private int activityId;//活动商品id

    private void initPromotion(GoodsDetailsModel.GoodsDetailsBean goodsdetailsmodelData) {
        PROMOTIONVISIBILIT = 0;
        GoodsDetailsModel.GoodsDetailsBean.GoodsActityBean goodsActity = goodsdetailsmodelData.getGoodsActity();
        if (goodsActity == null) {//HOT
            unpostage.setText("XXXXX");
            unpostage_linear.setVisibility(View.GONE);
        } else {
            String activityName = goodsActity.getActivityName();
            unpostage_linear.setVisibility(View.VISIBLE);
            unpostage.setText(activityName);
            PROMOTIONVISIBILIT += 1;
            activityId = goodsActity.getActivityId();
        }
        GoodsDetailsModel.GoodsDetailsBean.StoreActivityBean storeActivity = goodsdetailsmodelData.getStoreActivity();
        if (storeActivity == null || "".equals(storeActivity)) {//包邮
            hot.setText("XXX");
            hot_linear.setVisibility(View.GONE);
        } else {
            PROMOTIONVISIBILIT += 1;
            String activityName1 = storeActivity.getActivityName();
            hot_linear.setVisibility(View.VISIBLE);
            hot.setText(activityName1);
            activityId = storeActivity.getActivityId();
        }
        int isLimitBuy = goodsdetailsmodelData.getIsLimitBuy();
        if (isLimitBuy == 1) {// 是否限购 0否1是
            GoodsDetailsModel.GoodsDetailsBean.GoodsLimitBuyBean goodsLimitBuy = goodsdetailsmodelData.getGoodsLimitBuy();
            long startTime = goodsLimitBuy.getStartTime();
            String saleStartTime = getDateToString(startTime);
            long endTime = goodsLimitBuy.getEndTime();
            String saleEndTime = getDateToString(endTime);
            purchase_promotion.setText(saleStartTime + "到" + saleEndTime + ",限购");
            purchase_linear.setVisibility(View.VISIBLE);
            PROMOTIONVISIBILIT += 1;
            top_purchasepromotion.setVisibility(View.VISIBLE);
            sold_Linear.setVisibility(View.GONE);
            //限时抢购倒计时
            time = goodsLimitBuy.getRemainingTime();
            handler.postDelayed(runnable, 1000);
        } else {
            top_purchasepromotion.setVisibility(View.GONE);
            sold_Linear.setVisibility(View.VISIBLE);
            purchase_promotion.setText("XXXX");
            purchase_linear.setVisibility(View.GONE);
        }
        // 是否预售, 0否1是
        int isBeforeSale = goodsdetailsmodelData.getIsBeforeSale();
        if (isBeforeSale == 1) {
            GoodsDetailsModel.GoodsDetailsBean.GoodsBeforeSaleBean goodsBeforeSale = goodsdetailsmodelData.getGoodsBeforeSale();
            String saleSendTime = getDateToString(goodsBeforeSale.getSaleSendTime());
            String saleEndTime = getDateToString(goodsBeforeSale.getSaleEndTime());
            presell_promotion.setText(saleEndTime + "截止，预计" + saleSendTime + "发货");
            presell_linear.setVisibility(View.VISIBLE);
            PROMOTIONVISIBILIT += 1;
        } else {
            presell_promotion.setText("XXXX");
            presell_linear.setVisibility(View.GONE);
        }
        if (PROMOTIONVISIBILIT > 0) {
            promotionlinear.setVisibility(View.VISIBLE);
        } else {
            promotionlinear.setVisibility(View.GONE);
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

    //    //临时储存单个对比id
    List<Integer> goodsIdList = new ArrayList<>();
    //储存选中id
    HashMap<Integer, Integer> hashmapSelect = new HashMap<>();
    //选中的id集合
    List<Integer> listCompare = new ArrayList<Integer>();

    @Override
    public void onSelected(String title, String smallTitle, int childId, int parentId, boolean isSelectFirst, boolean isChecked) {
        ToastUtil.showToast(title + "=" + smallTitle + "=" + childId + ";=" + parentId);
//        retailPriceView
        Log.e("TAG_isSelectFirst", "isSelectFirst=" + isSelectFirst);
        if (isSelectFirst) {
            return;
        }
        //获取规格种类个数
        int specsCount = label_include.getChildCount();
        int flowlayoutCon = 0;
        for (int i = 0, j = specsCount; i < j; i++) {
            View flowlayout = label_include.getChildAt(i);
            if (flowlayout instanceof FlowLayout) {
                flowlayoutCon++;
                FlowLayout flowlayout1 = (FlowLayout) flowlayout;
                int flowlayoutId = flowlayout1.getId();
                if (flowlayoutId == parentId) {
                    for (int k = 0, l = flowlayout1.getChildCount(); k < l; k++) {
                        View radiobutton = flowlayout1.getChildAt(k);
                        if (radiobutton instanceof CheckBox) {
                            CheckBox radiobutton1 = (CheckBox) radiobutton;
                            int radiobuttonId = radiobutton1.getId();
                            if (radiobuttonId == childId) {
                                boolean checked = radiobutton1.isChecked();
                                if (checked) {
                                    hashmapSelect.put(flowlayoutId, radiobuttonId);
                                } else {
                                    hashmapSelect.remove(flowlayoutId);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (hashmapSelect.size() == 0) {
            label_include.removeAllViews();
            //规格数据
            label_include.setData(goodsDetails);
            return;
        }
        if (listCompare.size() > 0) {
            listCompare.clear();
        }
        //hashmap转list
        // 通过entrySet()取得key值和value值
        Iterator<Map.Entry<Integer, Integer>> itor = hashmapSelect.entrySet().iterator();
        while (itor.hasNext()) {
            Map.Entry<Integer, Integer> entry = itor.next();
            Integer entryKey = entry.getKey();
            if (parentId == entryKey) {
                hashmapSelect.put(parentId, childId);
            }
            listCompare.add(hashmapSelect.get(entryKey));
        }
        Integer tmpInteger1[] = new Integer[listCompare.size()];
        int tmpInt1[] = new int[listCompare.size()];
        listCompare.toArray(tmpInteger1);
        // 赋值输出
        for (int i = 0; i < tmpInteger1.length; i++) {
            tmpInt1[i] = tmpInteger1[i].intValue();
        }
        Log.e("TAG_选中id", "tmpInt1=" + tmpInt1.toString());
        if (checkedAllList != null && checkedAllList.size() > 0) {
            checkedAllList.clear();
        }
        for (int i = 0; i < tmpInt1.length; i++) {
            permutation(tmpInt1, 0, 0, i + 1);
        }
        if (checkedAllList == null && checkedAllList.size() == 0) {
            return;
        }
        Log.e("TAG_选中id组合", "checkedAllList=" + checkedAllList);
        if (checkedAllMap != null || checkedAllMap.size() > 0) {
            checkedAllMap.clear();
        }
        for (List<Integer> checkedList : checkedAllList) {
            if (goodsIdList != null || goodsIdList.size() > 0) {
                goodsIdList.clear();
            }
            goodsIdList.addAll(checkedList);
            List<GoodsDetailsModel.GoodsDetailsBean.SpecsBean> specs = goodsDetails.getSpecs();
            List<Map<Integer, Integer>> checkedMapId = new ArrayList<>();
            for (int i = 0, j = goodsIdList.size(); i < j; i++) {
                Integer selectId = goodsIdList.get(i);
                Map<Integer, Integer> map = new HashMap<>();
                for (int m = 0, n = specs.size(); m < n; m++) {
                    GoodsDetailsModel.GoodsDetailsBean.SpecsBean specsBean = specs.get(m);
                    int specId = specsBean.getSpecId();
                    List<GoodsDetailsModel.GoodsDetailsBean.SpecsBean.SpecValuesBean> specValues = specsBean.getSpecValues();
//                    boolean isExistKey = false;
                    for (int k = 0, l = specValues.size(); k < l; k++) {
                        GoodsDetailsModel.GoodsDetailsBean.SpecsBean.SpecValuesBean specValuesBean = specValues.get(k);
                        int specValueId = specValuesBean.getSpecValueId();
                        if (selectId == specValueId) {//与选中id设置规格标签id
                            map.put(specId, selectId);
                            break;
                        }
                    }
                }
                if (map.size() > 0) {
                    checkedMapId.add(map);
                }
            }
            if (checkedMapId.size() > 0) {
                checkedAllMap.add(checkedMapId);
            }
        }
        Log.e("TAG_选中id个规格key", "checkedAllMap=" + checkedAllMap);
        //不可点击的id
        Set<Integer> unClickSet = new HashSet<>();
        for (int o = 0, p = specsCount; o < p; o++) {
            View flowlayout = label_include.getChildAt(o);
            if (flowlayout instanceof FlowLayout) {
                FlowLayout flowlayout1 = (FlowLayout) flowlayout;
                int flowlayout1Id = flowlayout1.getId();
                for (List<Map<Integer, Integer>> list : checkedAllMap) {
                    for (int q = 0, r = flowlayout1.getChildCount(); q < r; q++) {
                        View radiobutton = flowlayout1.getChildAt(q);
                        if (radiobutton instanceof CheckBox) {
                            CheckBox checkbox = (CheckBox) radiobutton;
                            int checkboxId = checkbox.getId();
                            //选中规格生成所有集合后的后的单个map转集合
                            List<Integer> mapChangeList = new ArrayList<>();
                            for (Map<Integer, Integer> map : list) {
                                Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
                                while (iterator.hasNext()) {
                                    Map.Entry<Integer, Integer> next = iterator.next();
                                    Integer nextKey = next.getKey();
                                    if (flowlayout1Id != nextKey) {
                                        mapChangeList.add(map.get(nextKey));
                                    }
                                }
                            }
                            mapChangeList.add(checkboxId);
                            Log.e("TAG_添加后的", "mapChangeList=" + mapChangeList);
                            int specGross = 0;
                            for (int i = 0, j = products.size(); i < j; i++) {
                                GoodsDetailsModel.GoodsDetailsBean.ProductsBean productsBean = products.get(i);
                                List<Integer> specValueIds = productsBean.getSpecValueIds();
                                //包含
                                boolean containsAll = specValueIds.containsAll(mapChangeList);
                                if (containsAll) {
                                    int enableStore = productsBean.getEnableStore();
                                    specGross += enableStore;
                                }
                            }
                            boolean checked = checkbox.isChecked();
                            checkbox.setEnabled(true);
                            if (!checked) {
                                checkbox.setBackgroundResource(R.drawable.text_black_blackf7);
                                checkbox.setTextColor(ContextCompat.getColor(context, R.color.black_33));
                                if (specGross == 0) {
                                    unClickSet.add(checkbox.getId());
                                }
                            } else {
                                checkbox.setBackgroundResource(R.drawable.text_orange_blackf7);
                                checkbox.setTextColor(ContextCompat.getColor(context, R.color.orange));
                            }
                            Log.e("TAG_checkbox", checkbox.getText().toString() + "=" + checkbox.isEnabled());
                        }
                    }

                }
            }
        }
        Log.e("TAG_不可点击id组合", "unClickSet=" + unClickSet.toString());
        selectOneId(unClickSet);
        if (listCompare.size() == flowlayoutCon) {
            enableStore.setVisibility(View.VISIBLE);
            for (int m = 0, n = products.size(); m < n; m++) {
                GoodsDetailsModel.GoodsDetailsBean.ProductsBean productsBean = products.get(m);
                List<Integer> specValueIds = productsBean.getSpecValueIds();
                if (listCompare != null && specValueIds != null) {
                    boolean compare = compare(listCompare, specValueIds);
                    if (compare) {
                        initTradePricePosition(products, m);
                    }
                }
            }
        } else {
            enableStore.setVisibility(View.GONE);
        }
    }

    private void selectOneId(Set unClickSet) {
        for (int o = 0, p = label_include.getChildCount(); o < p; o++) {
            View flowlayout = label_include.getChildAt(o);
            if (flowlayout instanceof FlowLayout) {
                FlowLayout flowlayout1 = (FlowLayout) flowlayout;
                for (int q = 0, r = flowlayout1.getChildCount(); q < r; q++) {
                    View radiobutton = flowlayout1.getChildAt(q);
                    if (radiobutton instanceof CheckBox) {
                        CheckBox checkbox = (CheckBox) radiobutton;
                        int checkboxId = checkbox.getId();
                        boolean checked = checkbox.isChecked();
                        Log.e("TAG_比较按钮", "checkbox=" + checkbox.getText().toString() + ";Enabled=" + checkbox.isEnabled());
                        if (!checked) {
                            Iterator<Integer> iterator = unClickSet.iterator();
                            while (iterator.hasNext()) {
                                Integer next = iterator.next();
                                if (checkboxId == next) {
                                    Log.e("TAG_比较ID", "checkboxId=" + checkboxId + ";next=" + next);
                                    checkbox.setEnabled(false);
                                    checkbox.setBackgroundResource(R.drawable.text_black99_blacke0);
                                    checkbox.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_33));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int hashSetArray[];
    public int totalcount = 0;
    //选中的全部组合
    List<List<Integer>> checkedAllList = new ArrayList<>();
    List<List<Map<Integer, Integer>>> checkedAllMap = new ArrayList<>();

    public void permutation(int a[], int count, int count2, int except) {
        if (count2 == except) {
            System.out.println(Arrays.toString(hashSetArray));
            try {
                if (hashSetArray != null && hashSetArray.length != 0) {
                    List<Integer> list = new ArrayList<Integer>();
                    for (int i : hashSetArray) {
                        list.add(i);
                    }
                    checkedAllList.add(list);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            totalcount++;
        } else {
            if (count2 == 0) {
                hashSetArray = new int[except];
            }
            for (int i = count; i < a.length; i++) {
                hashSetArray[count2] = a[i];
                permutation(a, i + 1, count2 + 1, except);
            }
        }
    }

    public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }

    //轮播图点击事件
    @Override
    public void onItemClick(int position) {
        Log.e("TAG_详情轮播图", "点击了");
        List<GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean> goodsGallerys = goodsDetails.getGoodsGallerys();
        ArrayList<String> imageShowBigList = new ArrayList<String>();
        for (int i = 0, j = goodsGallerys.size(); i < j; i++) {
            GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean goodsGallerysBean = goodsGallerys.get(i);
            imageShowBigList.add(goodsGallerysBean.getBig());
        }
        // 跳到查看大图界面
        Intent intent = new Intent(getActivity(), ShowBigPictrueActivitiy.class);
        intent.getIntExtra("position", position);
        intent.putStringArrayListExtra("ImageList", imageShowBigList);
        startActivity(intent);
    }

    @Override
    public void onStatusChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            activity.operaTitleBar(true, true, false);
        } else {
            //当前为商品详情页
            activity.operaTitleBar(false, false, true);
        }
    }

    public void activityChangeFragment() {
        scrollView.smoothScrollTo(0, 0);
        slideDetailsLayout.smoothClose(true);
    }

    private CallBack callBack;

    public void setsetCartNum(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        public void setCartNum(int cartnum);
    }

    private MediaPlayer mediaplayer;

    private void initVoice() {
        if (goodsDetails == null) {
            return;
        }
        int haveVoice = goodsDetails.getHaveVoice();
        if (haveVoice == 1) {
            voice.setVisibility(View.VISIBLE);
            String voiceDetailUrl = goodsDetails.getVoiceDetailUrl();
            if (voiceDetailUrl == null) {
                return;
            }
//            voiceDetailUrl = "http://dl.stream.qqmusic.qq.com/C400000mtPsZ0kRcZi.m4a?vkey=68F79E941D3C7734B04518389C93637BAA1D992BBCCEFCE79E8C9EA7D2D0C4CD448CD355302F5552A845D2D186947ED14AF15BFC6DE46082&guid=9292650742&uin=1657379258&fromtag=66";
            try {
                if (mediaplayer == null) {
                    mediaplayer = new MediaPlayer();
                }
                if (mediaplayer.isPlaying()) {
                    mediaplayer.stop();
                    mediaplayer.release();
                    mediaplayer = null;
                    mediaplayer = new MediaPlayer();
                }
                mediaplayer.setDataSource(voiceDetailUrl);
                mediaplayer.setOnPreparedListener(this);//准备好的监听
                mediaplayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            voice.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        voice.setVisibility(View.VISIBLE);
        duration = mp.getDuration();//总时长
        String formatLongToTimeStr = HelpUtils.formatLongToTime((long) duration);
        voice_time.setText(formatLongToTimeStr);
    }

    public String getGoodsNum() {
        return etGoodsNum.getText().toString().trim();
    }

    public int getActivityId() {
        return activityId;
    }

    public int getProductId() {
        return productId;
    }

    private void startWebViewActivity(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("webViewUrl", url);
        getActivity().startActivity(intent);
    }
}