package com.yasn.purchase.goods.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
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
import com.yasn.purchase.activity.AuthorActivity;
import com.yasn.purchase.activity.GoodsDetailsActivity;
import com.yasn.purchase.activity.LoginActivity;
import com.yasn.purchase.activity.showbig.ShowBigPictrueActivitiy;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.adapter.SimpleRecyclerAdapter;
import com.yasn.purchase.adapter.TradePriceAdapter;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.goods.adapter.BannerHolderView;
import com.yasn.purchase.goods.view.SlideDetailsLayout;
import com.yasn.purchase.model.GoodsDetailsModel;
import com.yasn.purchase.model.GoodsDetailsOtherModel;
import com.yasn.purchase.model.SobotModel;
import com.yasn.purchase.utils.AlignedTextUtils;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.FlowLayout;
import com.yasn.purchase.view.RecyclerViewDecoration;
import com.yasn.purchase.view.ShoppingSelectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.help.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static com.yasn.purchase.R.id.et_goodsNum;
import static www.xcd.com.mylibrary.help.HelpUtils.getDateToString;
import static www.xcd.com.mylibrary.utils.SharePrefHelper.context;


/**
 * item页ViewPager里的商品Fragment
 */
public class GoodsInfoFragment extends BaseFragment implements
        ShoppingSelectView.OnSelectedListener, OnItemClickListener,
        SlideDetailsLayout.OnSlideDetailsListener
        , MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener
        , ViewPager.OnPageChangeListener, TextWatcher {

    private RelativeLayout topbat_parent;
    ConvenientBanner banner;
    TextView goodsDetail;
    TextView goodsConfig;
    SlideDetailsLayout slideDetailsLayout;
    ScrollView scrollView;

    private List<TextView> tabTextList = new ArrayList<>();
    public GoodsDetailsActivity activity;
    private TextView autotrophy, purchase, presell, title;
    private TextView tvVpNumber;
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
    private TextView sellingpoints, sellingview;

    /**
     * 规格
     *
     * @param context
     */
    private TextView specs;
    private LinearLayout ll_specs;
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
    private LinearLayout llTopPurchasepromotion;
    private TextView tvTopActivionPrice, tvTopActivionOriginalPrice, tvTopActivionSoldOutNum, tvActionTime;
    private TextView promotion, unpostage, hot, purchase_promotion, presell_promotion;
    private LinearLayout promotionlinear, unpostage_linear, hot_linear, purchase_linear, presell_linear;
    private LinearLayout llSold;
    private TextView tvGoodsOriginalPrice, tvGoodsOriginalSoldOutNum;
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
    private LinearLayout llRetailPrice;
    /**
     * 购买数量
     * 剩余库存
     *
     * @param context
     */
    private EditText etGoodsNum;
    private LinearLayout ivSubtractNum, ivAddNum;
    private int smallSale = 1;//最小起订量
    private int step;//步长
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
    private RelativeLayout undata;
    private TextView tvGoodsError;
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

        Map<String, Object> params1 = new HashMap<String, Object>();
        okHttpGet(101, Config.GOODSDETAILSOTHER + goodsidString, params1);
    }

    @Override
    protected void lazyLoad() {
//        if (!isPrepared || !isVisible) {
//            return;
//        }
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
                voice_start.setBackgroundResource(R.mipmap.voice_start);
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
        banner.setOnPageChangeListener(this);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) banner.getLayoutParams();
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;//获取屏幕宽度
        lp.width = width;
        lp.height = width;
        banner.setLayoutParams(lp);

        tvVpNumber = (TextView) rootView.findViewById(R.id.tv_VpNumber);
        //音频布局
        voice = (RelativeLayout) rootView.findViewById(R.id.voice);
        voice.setOnClickListener(this);
        voice_time = (TextView) rootView.findViewById(R.id.voice_time);
        voice_start = (ImageView) rootView.findViewById(R.id.voice_start);
        //顶部限购
        llTopPurchasepromotion = (LinearLayout) rootView.findViewById(R.id.ll_TopPurchasepromotion);
        llTopPurchasepromotion.setVisibility(View.GONE);
        //顶部活动价
        tvTopActivionPrice = (TextView) rootView.findViewById(R.id.tv_TopActivionPrice);
        //顶部活动原价
        tvTopActivionOriginalPrice = (TextView) rootView.findViewById(R.id.tv_TopActivionOriginalPrice);
        tvTopActivionOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        //活动已售数量
        tvTopActivionSoldOutNum = (TextView) rootView.findViewById(R.id.tv_TopActivionSoldOutNum);
        //活动剩余时间
        tvActionTime = (TextView) rootView.findViewById(R.id.tv_ActionTime);
        //正常已售布局
        llSold = (LinearLayout) rootView.findViewById(R.id.ll_Sold);
        //原价
        tvGoodsOriginalPrice = (TextView) rootView.findViewById(R.id.tv_GoodsOriginalPrice);
        tvGoodsOriginalPrice.setOnClickListener(this);
        //原价已售数量
        tvGoodsOriginalSoldOutNum = (TextView) rootView.findViewById(R.id.tv_GoodsOriginalSoldOutNum);
        initSoldNumber(tvGoodsOriginalSoldOutNum, "0", R.color.black_66);
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
        llRetailPrice = (LinearLayout) rootView.findViewById(R.id.ll_retailPrice);
        llRetailPrice.setVisibility(View.GONE);
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
        sellingview = (TextView) rootView.findViewById(R.id.sellingview);
//        SpannableString sellingString = AlignedTextUtils.formatText("卖点:");
        SpannableStringBuilder sellingString = AlignedTextUtils.justifyString("卖点", 4);
        sellingString.append("：");
        sellingpoints.setText(sellingString);
        //规格
        ll_specs = (LinearLayout) rootView.findViewById(R.id.ll_specs);
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
        etGoodsNum = (EditText) rootView.findViewById(et_goodsNum);
//        etGoodsNum.addTextChangedListener(this);
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
        undata = (RelativeLayout) rootView.findViewById(R.id.undata);
        tvGoodsError = (TextView) rootView.findViewById(R.id.tv_GoodsError);
//        htmlTextView = (TextView) rootView.findViewById(R.id.htmlText);
//        htmlTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

        slideDetailsLayout = (SlideDetailsLayout) rootView.findViewById(R.id.slideDetailsLayout);
        slideDetailsLayout.setVisibility(View.GONE);
        slideDetailsLayout.setOnSlideDetailsListener(this);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        //
        webView = (WebView) rootView.findViewById(R.id.webView);
    }

    private WebView webView;


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
            autotrophy.setText("自营");
            goneNum += 3;
            sb.append("自营 ");
            storeNameString = " 雅森 ";
            salesReturn.setVisibility(View.VISIBLE);
        } else {
            if (storeId != 99) {
                String regionName = SharePrefHelper.getInstance(context).getSpString("regionName");
                if (regionName == null || "".equals(regionName)) {
                    autotrophy.setVisibility(View.GONE);
                    autotrophy.setText("");
                } else {
                    autotrophy.setVisibility(View.VISIBLE);
                    autotrophy.setText(regionName + "直供");
                    sb.append(regionName + "直供 ");
                    goneNum = goneNum + regionName.length() + 3;
                }
            } else {
                autotrophy.setVisibility(View.GONE);
                autotrophy.setText("");
            }

            storeNameString = " 厂家 ";
            salesReturn.setVisibility(View.GONE);
        }
        try {
            String minNumberString = String.format("该商品由%s发货及售后", storeNameString);
            SpannableStringBuilder storeNameSpan = new SpannableStringBuilder(minNumberString);
            storeNameSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.black_33)), 4, 8,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            storeName.setText(storeNameSpan);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            presell.setVisibility(View.GONE);
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
    private int allDuration = 0;//总时长

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
                if (productId == 0){
                    ToastUtil.showToast("请选择商品规格！");
                    etGoodsNum.setEnabled(false);
                    return;
                }
                int subtractNum = Integer.valueOf(etGoodsNum.getText().toString().trim()) - step;
                etGoodsNum.removeTextChangedListener(this);
                if (subtractNum < smallSale) {
                    etGoodsNum.setText(String.valueOf(smallSale));
                    ToastUtil.showToast("该商品最小起量为" + smallSale + "件！");
                } else {
                    etGoodsNum.setText(String.valueOf(subtractNum));
                }
                etGoodsNum.addTextChangedListener(this);
                resetLadderPrices();
                break;
            case R.id.iv_addNum:
                if (productId == 0){
                    etGoodsNum.setEnabled(false);
                    ToastUtil.showToast("请选择商品规格！");
                    return;
                }
                etGoodsNum.removeTextChangedListener(this);
                //是否預售 , 0否1是
                int isBeforeSale = goodsDetails.getIsBeforeSale();
                int addNum = Integer.valueOf(etGoodsNum.getText().toString().trim());
                int allAddNum = addNum + (step >0?step:1);
                if (isBeforeSale == 0) {
                    if (allAddNum <= enableStoreNum) {
                        if (isLimitBuy == 1) {// 是否限购 0否1是
                            if (allAddNum > num) {
                                ToastUtil.showToast("该商品限购" + num + "件");
                            } else {
                                etGoodsNum.setText(String.valueOf(allAddNum));
                            }
                        }else {
                            etGoodsNum.setText(String.valueOf(allAddNum));
                        }
                        etGoodsNum.setText(String.valueOf(allAddNum));
                    } else {
                        ToastUtil.showToast("库存仅剩" + (enableStoreNum<=0?0:enableStoreNum) + "件");
                    }
                } else {
                    if (isLimitBuy == 1) {// 是否限购 0否1是
                        if (allAddNum > num) {
                            ToastUtil.showToast("该商品限购" + num + "件");
                        } else {
                            etGoodsNum.setText(String.valueOf(allAddNum));
                        }
                    }else {
                        etGoodsNum.setText(String.valueOf(allAddNum));
                    }
                }

                etGoodsNum.addTextChangedListener(this);

                resetLadderPrices();
                break;
            case R.id.tv_GoodsOriginalPrice:
                String trim = tvGoodsOriginalPrice.getText().toString().trim();
                if ("登录看价格".equals(trim)) {
                    ((GoodsDetailsActivity)getActivity()).startBaseActivity(getActivity(),LoginActivity.class);
                } else if ("认证看价格".equals(trim)) {
//                    startWebViewActivity(Config.ATTESTATION);
                    ((SimpleTopbarActivity)getActivity()).showStartAuthorDialog(AuthorActivity.class);
                }
                break;
        }
    }

    Runnable runnableVoice = new Runnable() {
        @Override
        public void run() {
            if (flag) {
                duration -= 1000;
                if (duration > 0) {
                    String formatLongToTimeStr = HelpUtils.formatLongToTime((long) duration);
                    voice_time.setText(formatLongToTimeStr);
                    handler.postDelayed(this, 1000);
                } else {
                    voice_start.setBackgroundResource(R.mipmap.voice_start);
                    voice_time.setText("语音讲解");
                    duration = allDuration;
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
        View view = inflater.inflate(R.layout.popwindow_goodscartype, null);
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.white));
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.popwindow_anim_leftandright);
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
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), R.layout.popwindow_goodscartype_item, cartypeList);
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
            tvActionTime.setText(style);

            if (time > 0) {
                handler.postDelayed(this, 1000);
            } else {
                llTopPurchasepromotion.setVisibility(View.GONE);
                llSold.setVisibility(View.VISIBLE);
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
            voice_start.setBackgroundResource(R.mipmap.voice_start);
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
    String loginState;//账号状态

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    loginState = SharePrefHelper.getInstance(getActivity()).getSpString("loginState");
                    slideDetailsLayout.setVisibility(View.VISIBLE);
                    succeedResult(returnData);
                    Log.e("TAG_loginState1","loginState="+loginState);
                    if (!"0".equals(loginState)) {
                        llLadderPrices.setVisibility(View.GONE);
                        tvTopActivionPrice.setText(loginState);
                        tvTopActivionOriginalPrice.setText(loginState);
                        tvGoodsOriginalPrice.setText(loginState);
                        //建议售价
                        llRetailPrice.setVisibility(View.GONE);
                    }
                } else if (returnCode == 401) {
                    cleanToken();
                    OkHttpDemand();
                } else {
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
                        tvGoodsError.setText("亲，未获取到教你卖好数据~");
                    } else {
//                        HtmlImageGetter htmlImageGetter = new HtmlImageGetter(getActivity(), htmlTextView);
//                        Spanned spanned = Html.fromHtml(intro, htmlImageGetter, null);
//                        htmlTextView.setText(spanned);
                        undata.setVisibility(View.GONE);
                        getHtmlData(intro, webView);
                    }

                } else {
                    undata.setVisibility(View.VISIBLE);
                    tvGoodsError.setText("亲，未获取到教你卖好数据~");
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }
    }

    //车型集合
    List<GoodsDetailsModel.GoodsDetailsBean.CarTypesBean> carTypesList;

    private void succeedResult(String returnData) {
        SobotModel sobotModel = new SobotModel();//客服数据
        GoodsDetailsModel goodsdetailsmodel = JSON.parseObject(returnData, GoodsDetailsModel.class);
        //进货单数量
        GoodsDetailsModel.MemberBean member = goodsdetailsmodel.getMember();
        if (member != null && !"".equals(member)) {
            int cartNum = member.getCartCount();
            if (callBack != null) {
                callBack.setCartNum(cartNum);
            }
        }

        goodsDetails = goodsdetailsmodel.getGoodsDetails();
        //音频布局
        initVoice();
        //轮播图
        initBannerView(goodsDetails);
        //设置title
        initGoodsTitleContent(goodsDetails);
        //设置临时价格
        double price = goodsDetails.getPrice();
        String priceResult = "￥" + String.format("%.2f", price);
        tvTopActivionPrice.setText(priceResult);
        tvTopActivionOriginalPrice.setText(priceResult);
        tvGoodsOriginalPrice.setText(priceResult);

        //规格数据
        List<GoodsDetailsModel.GoodsDetailsBean.SpecsBean> specs = goodsDetails.getSpecs();
        if (specs == null || specs.size() == 0) {
            ll_specs.setVisibility(View.GONE);
        } else {
            String specName = specs.get(0).getSpecName();
            String specValueName = specs.get(0).getSpecValues().get(0).getSpecValueName();
            Log.e("TAG_specName", "specName=" +(specName==null)+ ("默认".equals(specName)+""+"".equals(specName)));
            if (specs.size() == 1 && (specName==null||"默认".equals(specValueName)||"".equals(specValueName))) {
                ll_specs.setVisibility(View.GONE);
            } else {
                ll_specs.setVisibility(View.VISIBLE);
                label_include.setData(goodsDetails,goodsDetails.getIsBeforeSale());
            }
        }
        //批发价
        products = goodsDetails.getProducts();
        //临时记录是否存在库存大于0的规格
        boolean typeEnableStoreNum = false;
        for (int i = 0,j = products.size(); i < j; i++) {
            GoodsDetailsModel.GoodsDetailsBean.ProductsBean productsBean = products.get(i);
            int enableStore = productsBean.getEnableStore();
            if (enableStore>0){
                typeEnableStoreNum = true;
                initTradePricePosition(products, i);
                break;
            }
        }
        if (!typeEnableStoreNum){//库存全部为0，默认选取第一个商品规格
            initTradePricePosition(products, 0);
        }
        List<GoodsDetailsModel.GoodsDetailsBean.SpecsBean.SpecValuesBean> specValues = specs.get(0).getSpecValues();
        Log.e("TAG_批发价设置后", "specValues=" + specValues.size());
        if (specValues.size() == 1) {
            productId = productsBean.getProductId();
            etGoodsNum.setEnabled(true);
        } else {
            productId = 0;
            etGoodsNum.setEnabled(false);
        }
        Log.e("TAG_规格","products="+products.size());
        if (products.size() >0){
            if (products.size() ==1){
                tradeprice_recy.setVisibility(View.VISIBLE);
                tradeprice.setVisibility(View.VISIBLE);
            }else {
                tradeprice_recy.setVisibility(View.GONE);
                tradeprice.setVisibility(View.GONE);
            }
        }else {
            if (productId == 0){
                tradeprice_recy.setVisibility(View.GONE);
                tradeprice.setVisibility(View.GONE);
            }else {
                tradeprice_recy.setVisibility(View.VISIBLE);
                tradeprice.setVisibility(View.VISIBLE);
            }
        }
        boolean isChecked = label_include.getIsChecked();
        if (isChecked) {//存在选中状态，总库存大于0
            //是否預售 , 0否1是
            int isBeforeSale = goodsDetails.getIsBeforeSale();
            ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true, isBeforeSale==1?true:false);
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
        initSoldNumber(tvTopActivionSoldOutNum, String.valueOf(totalBuyCount), R.color.white);
        initSoldNumber(tvGoodsOriginalSoldOutNum, String.valueOf(totalBuyCount), R.color.black_66);
        //卖点
        String subTitle = goodsDetails.getSubTitle();
        if (TextUtils.isEmpty(subTitle)) {
            sellinglinear.setVisibility(View.GONE);
        } else {
            sellinglinear.setVisibility(View.VISIBLE);
            sellingview.setText(subTitle);
        }
        //折扣是否可用
        int pointUsable = goodsDetails.getPointUsable();// 是否可用积分, 0否1是
        if (pointUsable == 1) {
            int point = goodsDetails.getPoint();
            if (point > 0 ){
                String discountString = String.format("积分可折扣 %s%s", point, "%");
                discountview.setText(discountString);
                discountLinear.setVisibility(View.VISIBLE);
            }else {
                String discountString = String.format("积分可折扣 %s%s", point, "%");
                discountview.setText(discountString);
                discountLinear.setVisibility(View.GONE);
            }
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
        //咨询内容标题，必填
        sobotModel.setSobotGoodsTitle(goodsDetails.getName());
        //描述，选填
        sobotModel.setSobotGoodsDescribe(goodsDetails.getAdvert());
        //咨询内容图片，选填 但必须是图片地址
        if (goodsGallerys != null && goodsGallerys.size() > 0) {
            GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean goodsGallerysBean = goodsGallerys.get(0);
            sobotModel.setSobotGoodsImgUrl(goodsGallerysBean.getThumbnail());
        }
        sobotModel.setSobotGoodsLable(tvTopActivionPrice.getText().toString().trim());
        ((GoodsDetailsActivity) getActivity()).setSobotModel(sobotModel);
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
//        RcDecoration rcDecoration = new RcDecoration(getActivity(), RcDecoration.HORIZONTAL_LIST);
//        tradeprice_recy.addItemDecoration(rcDecoration);
        tradeprice_recy.addItemDecoration(new RecyclerViewDecoration(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.orange)));
        adapter.notifyDataSetChanged();
    }

    private int productId;//商品规格id
    private int typeLadderPricesposition;//临时选中的规格对应折扣价的position
    //建议售价
    GoodsDetailsModel.GoodsDetailsBean.ProductsBean productsBean;
    //阶梯价
    List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean> ladderPrices;

    private void initTradePricePosition(List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean> products, int position) {
        etGoodsNum.removeTextChangedListener(this);
        typeLadderPricesposition = position;
        if (position == -1) {
            enableStore.setVisibility(View.GONE);
            productId = 0;
            etGoodsNum.setEnabled(false);
            return;
        }
        enableStore.setVisibility(View.VISIBLE);

        if (products != null) {
            productsBean = products.get(position);
            //建议售价
            String minReferencePrice = productsBean.getMinReferencePrice();
            String maxReferencePrice = productsBean.getMaxReferencePrice();
            if (minReferencePrice != null && maxReferencePrice != null && Double.valueOf(maxReferencePrice) > 0) {
                if (products.size() ==1){
                    llRetailPrice.setVisibility(View.VISIBLE);
                    retailPriceView.setText("￥" + String.format("%.2f", Double.valueOf(minReferencePrice)) + "-￥" + String.format("%.2f", Double.valueOf(maxReferencePrice)));
                }else {
                    llRetailPrice.setVisibility(View.GONE);
                }
            } else {
                llRetailPrice.setVisibility(View.GONE);
            }
            productId = productsBean.getProductId();
            etGoodsNum.setEnabled(true);
            //最小起订量
            smallSale = productsBean.getSmallSale();
            //步长
            step = productsBean.getStep();
            enableStoreNum = productsBean.getEnableStore();
//            Log.e("TAG_库存", "position=" + position + ";enableStoreNum=" + enableStoreNum);
            if (enableStoreNum >= 10) {
                enableStore.setText("库存:充足");
                etGoodsNum.setText(String.valueOf(smallSale));
                //是否預售 , 0否1是
                int isBeforeSale = goodsDetails.getIsBeforeSale();
                ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true, isBeforeSale==1?true:false);
            } else {
                //是否預售 , 0否1是
                int isBeforeSale = goodsDetails.getIsBeforeSale();
                if (isBeforeSale == 1) {
                    enableStore.setText("库存:充足");
                } else {
                    if (enableStoreNum <= 0) {
                        enableStore.setText("库存:0");
                    }else {
                        enableStore.setText("库存:" + String.valueOf(enableStoreNum));
                    }
                }
                if (enableStoreNum <= 0) {
                    etGoodsNum.setText((smallSale<=0?1:smallSale)+"");
                    ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(false, isBeforeSale==1?true:false);
                } else {
                    etGoodsNum.setText(String.valueOf((smallSale<=0?1:smallSale)));
                    ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true, isBeforeSale==1?true:false);
                }
            }
            //阶梯价
            ladderPrices = productsBean.getLadderPrices();
            resetLadderPrices();
        } else {
            smallSale = 1;
            llRetailPrice.setVisibility(View.GONE);
            etGoodsNum.setText(String.valueOf(smallSale));
        }
        //最小起訂量
        String minNumberString = String.format("最小起订量:%s      %s件起批", smallSale, step);
        SpannableStringBuilder span = new SpannableStringBuilder(minNumberString);
        span.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_text14_black_66), 0, 6,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_text14_black_66), minNumberString.length() - 2, minNumberString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if (smallSale<=1&&step<=1){
            minNumber.setVisibility(View.GONE);
        }else {
            minNumber.setVisibility(View.VISIBLE);
        }
        minNumber.setText(span);
        //庫存字体颜色
        SpannableStringBuilder enableStoreSpan = new SpannableStringBuilder(enableStore.getText().toString());
        enableStoreSpan.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_text14_black_66), 0, 3,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        enableStore.setText(enableStoreSpan);

        etGoodsNum.addTextChangedListener(this);
    }

    private void resetLadderPrices() {
        if (ladderPrices != null && ladderPrices.size() > 0) {

            //折扣价数据
//            Log.e("TAG_折扣价","products="+products.size()+";typeLadderPricesposition="+typeLadderPricesposition);
            if (productId == 0){
                llLadderPrices.setVisibility(View.GONE);
                tradeprice_recy.setVisibility(View.GONE);
                tradeprice.setVisibility(View.GONE);
            }else {
                llLadderPrices.setVisibility(View.VISIBLE);
                tradeprice_recy.setVisibility(View.VISIBLE);
                tradeprice.setVisibility(View.VISIBLE);
            }
            adapter.setData(ladderPrices);

            double goodsNumDou = Long.valueOf(etGoodsNum.getText().toString());
            for (int i = 0, k = ladderPrices.size(); i < k; i++) {
                GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean ladderPricesBean = ladderPrices.get(i);
                int minNum = ladderPricesBean.getMinNum();
                int maxNum = ladderPricesBean.getMaxNum();
                if (goodsNumDou >= minNum && goodsNumDou <= maxNum) {
                    String activityPrice = ladderPricesBean.getActivityPrice();
                    double wholesalePrice = ladderPricesBean.getWholesalePrice();
                    String wholesalePriceResult = "￥" + String.format("%.2f", wholesalePrice);
//                    Log.e("TAG_活动价", "tvTopActivionPrice=" + wholesalePriceResult);
                    tvTopActivionOriginalPrice.setText(wholesalePriceResult);
                    if (activityPrice == null
//                            || Double.valueOf(activityPrice)==0
                            ) {
                        tvTopActivionPrice.setText(wholesalePriceResult);
                        tvGoodsOriginalPrice.setText(wholesalePriceResult);
//                        Log.e("TAG_活动价", "tvTopActivionPrice=" + wholesalePriceResult);
//                        Log.e("TAG_活动价市场价", "tvTopActivionOriginalPrice=" + activityPrice);
                    } else {
                        String activityPriceResult = "￥" + String.format("%.2f", Double.valueOf(activityPrice));
                        tvTopActivionPrice.setText(activityPriceResult);
                        tvGoodsOriginalPrice.setText(activityPriceResult);
//                        Log.e("TAG_活动价市场价", "tvTopActivionOriginalPrice=" + activityPriceResult);
                    }
                }
            }
        } else {
            llLadderPrices.setVisibility(View.GONE);
            tradeprice_recy.setVisibility(View.GONE);
            tradeprice.setVisibility(View.GONE);
            String activityPrice = productsBean.getActivityPrice();
            String price = String.valueOf(productsBean.getPrice());
            String priceResult = "￥" + String.format("%.2f", Double.valueOf(price));
            tvTopActivionOriginalPrice.setText(priceResult);
            if (activityPrice != null
//                    && Double.valueOf(activityPrice)>0
                    ) {
                String activityPriceResult = "￥" + String.format("%.2f", Double.valueOf(activityPrice));
                tvTopActivionPrice.setText(activityPriceResult);
                tvGoodsOriginalPrice.setText(activityPriceResult);
            } else {
                tvTopActivionPrice.setText(priceResult);
                tvGoodsOriginalPrice.setText(priceResult);
            }
        }
        if (!"0".equals(loginState)) {
            tvTopActivionPrice.setText(loginState);
            tvTopActivionOriginalPrice.setText(loginState);
            tvGoodsOriginalPrice.setText(loginState);
        }
    }

    List<GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean> goodsGallerys;

    private void initBannerView(GoodsDetailsModel.GoodsDetailsBean goodsdetailsmodelData) {
        banner.setCanLoop(true);
        goodsGallerys = goodsdetailsmodelData.getGoodsGallerys();
        //初始化商品图片轮播
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView(tvVpNumber, goodsGallerys);
            }
        }, goodsGallerys)
                .setOnItemClickListener(this)
//                .startTurning(2000)
        ;
        banner.setCanLoop(false);
    }

    private int PROMOTIONVISIBILIT = 0;
    private int activityId;//活动商品id
    private int isLimitBuy;// 是否限购 0否1是
    private int num;//限购数量

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
        isLimitBuy = goodsdetailsmodelData.getIsLimitBuy();
        if (isLimitBuy == 1) {// 是否限购 0否1是
            GoodsDetailsModel.GoodsDetailsBean.GoodsLimitBuyBean goodsLimitBuy = goodsdetailsmodelData.getGoodsLimitBuy();
            long startTime = goodsLimitBuy.getStartTime();
            String saleStartTime = getDateToString(startTime);
            long endTime = goodsLimitBuy.getEndTime();
            String saleEndTime = getDateToString(endTime);
            num = goodsLimitBuy.getNum();
            purchase_promotion.setText(saleStartTime + "到" + saleEndTime + ",限购" + num + "个");
            purchase_linear.setVisibility(View.VISIBLE);
            PROMOTIONVISIBILIT += 1;

        } else {
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
//            ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true);
        } else {
            presell_promotion.setText("XXXX");
            presell_linear.setVisibility(View.GONE);
//            ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(false);
        }
        if (PROMOTIONVISIBILIT > 0) {
            promotionlinear.setVisibility(View.VISIBLE);
        } else {
            promotionlinear.setVisibility(View.GONE);
        }
        GoodsDetailsModel.GoodsDetailsBean.DiscountBean discount = goodsdetailsmodelData.getDiscount();
        if (discount == null) {
            llTopPurchasepromotion.setVisibility(View.GONE);
            llSold.setVisibility(View.VISIBLE);

        } else {
            int has_discount = discount.getHas_discount();
            if (has_discount == 0) {
                llTopPurchasepromotion.setVisibility(View.GONE);
                llSold.setVisibility(View.VISIBLE);
//                ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(false,null);
            } else {
                llTopPurchasepromotion.setVisibility(View.VISIBLE);
                llSold.setVisibility(View.GONE);
                //限时抢购价
//                String discount_price = discount.getDiscount_price();
//                tvTopActivionOriginalPrice.setText(discount_price == null ? "" : String.format("%.2f", Double.valueOf(discount_price)));
                //限时抢购倒计时
                time = discount.getRemainingTime();
                handler.postDelayed(runnable, 1000);
//                ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true,null);
            }
        }
    }

    @Override
    public void onCancelResult() {
        slideDetailsLayout.setVisibility(View.GONE);
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        slideDetailsLayout.setVisibility(View.GONE);
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        slideDetailsLayout.setVisibility(View.GONE);
    }

    @Override
    public void onFinishResult() {
        slideDetailsLayout.setVisibility(View.GONE);
    }

    //    //临时储存单个对比id
    List<Integer> goodsIdList = new ArrayList<>();
    //储存选中id
    HashMap<Integer, Integer> hashmapSelect = new HashMap<>();
    //选中的id集合
    List<Integer> listCompare = new ArrayList<Integer>();

    @Override
    public void onSelected(String title, String smallTitle, int childId, int parentId, boolean isSelectFirst, boolean isChecked) {
//        retailPriceView
//        Log.e("TAG_isSelectFirst", "isSelectFirst=" + isSelectFirst);
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
//                                Log.e("TAG_checked", "checked=" + checked);
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
//        Log.e("TAG_hashmapSelect", "hashmapSelect=" + hashmapSelect.size());
        if (hashmapSelect.size() == 0) {
            label_include.removeAllViews();
            //重置规格id
            productId = 0;
            etGoodsNum.setEnabled(false);
            //规格数据
            label_include.setData(goodsDetails,goodsDetails.getIsBeforeSale());
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
//        Log.e("TAG_选中id", "tmpInt1=" + tmpInt1.toString());
        if (checkedAllList != null && checkedAllList.size() > 0) {
            checkedAllList.clear();
        }
        for (int i = 0; i < tmpInt1.length; i++) {
            permutation(tmpInt1, 0, 0, i + 1);
        }
        if (checkedAllList == null && checkedAllList.size() == 0) {
            return;
        }
//        Log.e("TAG_选中id组合", "checkedAllList=" + checkedAllList);
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
//        Log.e("TAG_选中id个规格key", "checkedAllMap=" + checkedAllMap);
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
//                            Log.e("TAG_添加后的", "mapChangeList=" + mapChangeList);
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
                                checkbox.setTextColor(ContextCompat.getColor(getActivity(), R.color.black_33));
                                if (specGross == 0) {
                                    unClickSet.add(checkbox.getId());
                                }
                            } else {
                                checkbox.setBackgroundResource(R.drawable.text_orange_blackf7);
                                checkbox.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange));
                            }
//                            Log.e("TAG_checkbox", checkbox.getText().toString() + "=" + checkbox.isEnabled());
                        }
                    }

                }
            }
        }
//        Log.e("TAG_不可点击id组合", "unClickSet=" + unClickSet.toString());
        int isBeforeSale = goodsDetails.getIsBeforeSale(); // 是否预售, 0否1是
        if (isBeforeSale==0){//非预售产品规格库存为0置灰
            selectOneId(unClickSet);
        }
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
            productId = 0;
            etGoodsNum.setEnabled(false);
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
//                        Log.e("TAG_比较按钮", "checkbox=" + checkbox.getText().toString() + ";Enabled=" + checkbox.isEnabled());
                        if (!checked) {
                            Iterator<Integer> iterator = unClickSet.iterator();
                            while (iterator.hasNext()) {
                                Integer next = iterator.next();
                                if (checkboxId == next) {
//                                    Log.e("TAG_比较ID", "checkboxId=" + checkboxId + ";next=" + next);
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
        List<GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean> goodsGallerys = goodsDetails.getGoodsGallerys();
        ArrayList<String> imageShowBigList = new ArrayList<String>();
        for (int i = 0, j = goodsGallerys.size(); i < j; i++) {
            GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean goodsGallerysBean = goodsGallerys.get(i);
            imageShowBigList.add(goodsGallerysBean.getBig());
        }
        // 跳到查看大图界面
        Intent intent = new Intent(getActivity(), ShowBigPictrueActivitiy.class);
        intent.putExtra("position", position);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        Log.e("TAG_轮播", "onPageSelected=" + position);
        tvVpNumber.setText(goodsGallerys == null ? "0/0" : ((position + 1) + "/" + goodsGallerys.size()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        Log.e("TAG_TextChanged", "trim=" + charSequence.toString() + ";typeLadderPricesposition=" + typeLadderPricesposition);
        String textChanged = charSequence.toString();
        if (textChanged != null && !"".equals(textChanged)) {
            initTradePricePositionTextChanged(products, typeLadderPricesposition);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //文本输入查询价格
    private void initTradePricePositionTextChanged(List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean> products, int position) {
        etGoodsNum.removeTextChangedListener(this);
        if (position == -1) {
            enableStore.setVisibility(View.GONE);
            productId = 0;
            etGoodsNum.setEnabled(false);
            return;
        }
        GoodsDetailsModel.GoodsDetailsBean.ProductsBean productsBean;
        enableStore.setVisibility(View.VISIBLE);
        List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean> ladderPrices;

        if (products != null) {
            productsBean = products.get(position);
            productId = productsBean.getProductId();
            etGoodsNum.setEnabled(true);
//            Log.e("TAG_库存规格id", "productId=" + productId);
            enableStoreNum = productsBean.getEnableStore();
//            Log.e("TAG_库存", "position=" + position + ";enableStoreNum=" + enableStoreNum);
            //是否預售 , 0否1是
            int isBeforeSale = goodsDetails.getIsBeforeSale();
            if (enableStoreNum >= 10) {
                enableStore.setText("库存:充足");
                ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true,  isBeforeSale==1?true:false);
            } else {
                if (isBeforeSale == 1) {
                    enableStore.setText("库存:充足");
                } else {
                    if (enableStoreNum <= 0) {
                        enableStore.setText("库存:0");
                    }else {
                        enableStore.setText("库存:" + String.valueOf(enableStoreNum));
                    }
                }
                if (enableStoreNum == 0) {
                    ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(false, isBeforeSale==1?true:false);
                } else {
                    ((GoodsDetailsActivity) getActivity()).setTvAddShopCar(true, isBeforeSale==1?true:false);
                }
            }
            //阶梯价
            ladderPrices = productsBean.getLadderPrices();
            if (ladderPrices != null && ladderPrices.size() > 0) {
                //折扣价数据
                if (ladderPrices == null || ladderPrices.size() == 0) {
                    tradeprice_recy.setVisibility(View.GONE);
                    tradeprice.setVisibility(View.GONE);
                    llLadderPrices.setVisibility(View.GONE);
                } else {
                    if (productId==0){
                        tradeprice_recy.setVisibility(View.GONE);
                        tradeprice.setVisibility(View.GONE);
                        llLadderPrices.setVisibility(View.GONE);
                    }else {
                        tradeprice_recy.setVisibility(View.VISIBLE);
                        tradeprice.setVisibility(View.VISIBLE);
                        llLadderPrices.setVisibility(View.VISIBLE);
                    }
                    adapter.setData(ladderPrices);
                }
                double goodsNumDou = Long.valueOf(etGoodsNum.getText().toString());
                for (int i = 0, k = ladderPrices.size(); i < k; i++) {
                    GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean ladderPricesBean = ladderPrices.get(i);
                    int minNum = ladderPricesBean.getMinNum();
                    int maxNum = ladderPricesBean.getMaxNum();
                    if (goodsNumDou >= minNum && goodsNumDou <= maxNum) {
                        String activityPrice = ladderPricesBean.getActivityPrice();
                        double wholesalePrice = ladderPricesBean.getWholesalePrice();
                        String wholesalePriceResult = "￥" + String.format("%.2f", wholesalePrice);
                        tvTopActivionOriginalPrice.setText(String.valueOf(wholesalePriceResult));
                        if (activityPrice == null
//                                || Double.valueOf(activityPrice)==0
                                ) {
                            tvTopActivionPrice.setText(String.valueOf(wholesalePriceResult));
                            tvGoodsOriginalPrice.setText(String.valueOf(wholesalePriceResult));
                        } else {
                            String activityPriceResult = "￥" + String.format("%.2f", Double.valueOf(activityPrice));
                            tvTopActivionPrice.setText(activityPriceResult);
                            tvGoodsOriginalPrice.setText(activityPriceResult);
                        }
                    }
                }
            } else {
                llLadderPrices.setVisibility(View.GONE);
                String activityPrice = productsBean.getActivityPrice();
                String price = "￥" + String.format("%.2f", productsBean.getPrice());
                tvTopActivionOriginalPrice.setText(price);
                if (activityPrice != null
//                        && Double.valueOf(activityPrice)>0
                        ) {
                    String activityPrice1 = "￥" + String.format("%.2f", Double.valueOf(activityPrice));
                    tvTopActivionPrice.setText(activityPrice1);
                    tvGoodsOriginalPrice.setText(activityPrice1);
                } else {
                    tvTopActivionPrice.setText(price);
                    tvGoodsOriginalPrice.setText(price);
                }
            }
        }
//        Log.e("TAG_loginState3","loginState="+loginState);
        if (!"0".equals(loginState)) {
            tvTopActivionPrice.setText(loginState);
            tvTopActivionOriginalPrice.setText(loginState);
            tvGoodsOriginalPrice.setText(loginState);
        }
        etGoodsNum.addTextChangedListener(this);
    }

    public interface CallBack {
        public void setCartNum(int cartnum);
    }

    private MediaPlayer mediaplayer;

    private void initVoice() {
        if (goodsDetails == null) {
            return;
        }
        //暂时隐藏，初始化完成后布局显示
        voice.setVisibility(View.GONE);
        int haveVoice = goodsDetails.getHaveVoice();
        if (haveVoice == 1) {
//            voice.setVisibility(View.VISIBLE);
            String voiceDetailUrl = goodsDetails.getVoiceDetailUrl();
            if (voiceDetailUrl == null) {
                return;
            }
//            voiceDetailUrl = "http://dl.stream.qqmusic.qq.com/C400000mtPsZ0kRcZi.m4a?vkey=68F79E941D3C7734B04518389C93637BAA1D992BBCCEFCE79E8C9EA7D2D0C4CD448CD355302F5552A845D2D186947ED14AF15BFC6DE46082&guid=9292650742&uin=1657379258&fromtag=66";
            try {
                if (mediaplayer == null) {
                    mediaplayer = new MediaPlayer();
                }
                if (mediaplayer != null && mediaplayer.isPlaying()) {
//                    mediaplayer.stop();
                    mediaplayer.pause();
                    mediaplayer.seekTo(0);

                    mediaplayer.release();
                    mediaplayer = null;
                    mediaplayer = new MediaPlayer();
                }
                Log.e("TAG_语音讲解","voiceDetailUrl="+voiceDetailUrl);
                mediaplayer.reset();
                mediaplayer.setDataSource(voiceDetailUrl);
                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaplayer.prepareAsync();
                mediaplayer.setOnPreparedListener(this);//准备好的监听
                mediaplayer.setOnErrorListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    String formatLongToTimeStr;//总时长

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.e("TAG_语音讲解","onPrepared准备完成");
        voice.setVisibility(View.VISIBLE);
        duration = mp.getDuration();//总时长
        allDuration = duration;
        formatLongToTimeStr = HelpUtils.formatLongToTime((long) duration);
        voice_time.setText("语音讲解");
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        voice.setVisibility(View.GONE);
        Log.e("TAG_语音讲解", "播放错误: " + what + " Extra code: " + extra);
        return true;
    }

    //商品价格
    public boolean getGoodsMoney() {
        Double aDouble = Double.valueOf(tvTopActivionPrice.getText().toString().substring(1));
        Double aDouble1 = Double.valueOf(tvTopActivionOriginalPrice.getText().toString().substring(1));
        Double aDouble2 = Double.valueOf(tvGoodsOriginalPrice.getText().toString().substring(1));
        if (aDouble >0 && aDouble1 > 0 && aDouble2 > 0 ){
            return true;

        }else {
            return false;

        }
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
        Intent intent = new Intent(getActivity(), WebViewH5Activity.class);
        intent.putExtra("webViewUrl", url);
        getActivity().startActivity(intent);
    }

    //步长，供activity调用
    public int getStep() {
        return step;
    }
    //最小起订量，供activity调用
    public int getSmallSale() {
        return smallSale;
    }
}
