package com.yasn.purchase.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.model.GoodsDetailsModel;

import java.util.List;

/*
 * 项目名:    shopping-selection
 * 包名       com.zsy.shoppingselect
 * 文件名:    ShoppingSelectView
 * 创建者:    ZSY
 * 创建时间:  2017/5/5 on 15:58
 * 描述:     TODO 商品规格选择View
 */
public class ShoppingSelectView extends LinearLayout {
    /**
     * 数据源
     */
    private GoodsDetailsModel.GoodsDetailsBean list;
    /**
     * 上下文
     */
    private Context context;

    /**
     * 规格标题栏的文本间距
     */
    private int titleMargin = 5;
    /**
     * 整个商品属性的左右间距
     */
    private int flowLayoutMargin = 16;
    /**
     * 属性按钮的高度
     */
    private int buttonHeight = 25;
    /**
     * 属性按钮之间的左边距
     */
    private int buttonLeftMargin = 10;
    /**
     * 属性按钮之间的上边距
     */
    private int buttonTopMargin = 5;
    /**
     * 文字与按钮的边距
     */
    private int textPadding = 10;
    /**
     * 选择后的回调监听
     */
    private OnSelectedListener listener;
    //是否默认选中第一个
    private boolean isSelectFirst = false;

    public ShoppingSelectView(Context context) {
        super(context);
        initView(context);
    }

    public ShoppingSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
    }

    public void getView() {
        List<GoodsDetailsModel.GoodsDetailsBean.SpecsBean> specs = list.getSpecs();
        if (specs == null||specs.size() < 0) {
            return;
        }
        if (specs.size() == 1) {
            if (specs.get(0).getSpecValues().size() == 1) {
                setOrientation(LinearLayout.HORIZONTAL);
            } else {
                setOrientation(LinearLayout.VERTICAL);
            }
        } else {
            setOrientation(LinearLayout.VERTICAL);
        }
        int checkboxNum = 0;
        for (GoodsDetailsModel.GoodsDetailsBean.SpecsBean attr : specs) {
            List<GoodsDetailsModel.GoodsDetailsBean.SpecsBean.SpecValuesBean> specValues = attr.getSpecValues();
            for (int i = 0,n = specValues.size(); i < n; i++) {
                checkboxNum+=1;
            }
        }
        for (GoodsDetailsModel.GoodsDetailsBean.SpecsBean attr : specs) {
            //设置规格分类的标题
            TextView textView = new TextView(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = dip2px(context, titleMargin);
            textView.setText(attr.getSpecName());
            params.setMargins(margin, margin, margin, margin);
            textView.setTextSize(14);
            textView.setLayoutParams(params);
            addView(textView);
            //设置一个大规格下的所有小规格
            FlowLayout layout = new FlowLayout(context);
            layout.setId(attr.getSpecId());
            layout.setTitle(attr.getSpecName());
            layout.setPadding(dip2px(context, flowLayoutMargin), 0, dip2px(context, flowLayoutMargin), 0);
            //设置选择监听
            Log.e("TAG_设置选择监听", "listener=" + (listener != null));
            if (listener != null) {
                layout.setListener(listener);
            }
            List<GoodsDetailsModel.GoodsDetailsBean.SpecsBean.SpecValuesBean> specValues = attr.getSpecValues();
            for (int i = 0,n = specValues.size(); i < n; i++) {
                GoodsDetailsModel.GoodsDetailsBean.SpecsBean.SpecValuesBean smallAttr = specValues.get(i);
                //属性按钮
                CheckBox button = new CheckBox(context.getApplicationContext());
                //设置按钮的参数
                LayoutParams buttonParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
//                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        dip2px(context, buttonHeight));
                //设置文字的边距
                int padding = dip2px(context, textPadding);
                button.setPadding(padding, 0, padding, 0);
                //设置margin属性，需传入LayoutParams否则会丢失原有的布局参数
                MarginLayoutParams marginParams = new MarginLayoutParams(buttonParams);
                marginParams.leftMargin = dip2px(context, buttonLeftMargin);
                marginParams.topMargin = dip2px(context, buttonTopMargin);

                button.setGravity(Gravity.CENTER);
                button.setButtonDrawable(android.R.color.transparent);
                button.setTextSize(14);
                button.setText(smallAttr.getSpecValueName());//设置规格内容
//                lp.setMargins(10, 5, 10,0);
                button.setLayoutParams(marginParams);
                int specValueId = smallAttr.getSpecValueId();
                button.setId(specValueId);//设置规格ID
                Log.e("TAG_specs1", "specs=" + specs.toString());
                Log.e("TAG_specs2", "specs=" + specs.size()+";checkboxNum="+checkboxNum);
                //默认选中第一个specsChecked
                if (specs.size() == 1&&(checkboxNum/specs.size())==1) {
                    isSelectFirst = true;
                    button.setChecked(true);//默认选中
                    button.setEnabled(false);//默认必选
                    button.setBackgroundResource(R.drawable.text_orange_blackf7);
                    button.setTextColor(ContextCompat.getColor(context,R.color.orange));
                }else if (specs.size() > 1&&(checkboxNum/specs.size())==1) {
                    isSelectFirst = true;
                    button.setEnabled(false);//默认必选
                    button.setChecked(true);//默认选中
                    button.setBackgroundResource(R.drawable.text_orange_blackf7);
                    button.setTextColor(ContextCompat.getColor(context,R.color.orange));
                }else {
                    isSelectFirst = false;
                    button.setEnabled(true);//可切换
                    button.setChecked(false);//默认选中
                    button.setBackgroundResource(R.drawable.text_black_blackf7);
                    button.setTextColor(ContextCompat.getColor(context,R.color.black_66));
                }
                boolean isChecked = false;
                List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean> products = list.getProducts();
                for (int j = 0, k = products.size(); j < k; j++) {
                    GoodsDetailsModel.GoodsDetailsBean.ProductsBean productsBean = products.get(j);
                    int enableStore = productsBean.getEnableStore();
//                    Log.e("TAG_false1", "j="+j+"；enableStore=" + enableStore+";ProductId="+productsBean.getProductId());
                    if (enableStore > 0) {
                        List<Integer> specValueIds = productsBean.getSpecValueIds();
                        for (int l = 0, m = specValueIds.size(); l < m; l++) {
                            Integer specId = specValueIds.get(l);
//                            Log.e("TAG_false2", "specId=" + specId+";specValueId="+specValueId);
                            if (specId == specValueId) {
                                isChecked = true;
                            }
                        }
                    }
                }
                if (!isSelectFirst){
                    Log.e("TAG_false3", "button=" + button.getText().toString()+";isChecked="+isChecked);
                    if (isChecked){
                        button.setEnabled(true);
                        button.setBackgroundResource(R.drawable.text_black_blackf7);
                        button.setTextColor(ContextCompat.getColor(context,R.color.black_33));
                        isCheckedSpecs = true;
                    }else {
                        if (isBeforeSale == 1){ // 是否预售, 0否1是
                            button.setEnabled(true);
                            button.setBackgroundResource(R.drawable.text_black_blackf7);
                            button.setTextColor(ContextCompat.getColor(context,R.color.black_33));
                            isCheckedSpecs = true;
                        }else {
                            button.setEnabled(false);
                            button.setBackgroundResource(R.drawable.text_black99_blacke0);
                            button.setTextColor(ContextCompat.getColor(context, R.color.black_33));
                        }
                    }
                }
                layout.addView(button);
            }
            addView(layout);
        }
    }
    //是否存在可选中规格
    private boolean isCheckedSpecs = false;
    private int isBeforeSale;// 是否预售, 0否1是
    public boolean getIsChecked(){
        return isCheckedSpecs;
    }
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setData(GoodsDetailsModel.GoodsDetailsBean data,int isBeforeSale) {
        list = data;
        this.isBeforeSale = isBeforeSale;
        getView();
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * 选择成功回调
     */
    public interface OnSelectedListener {
        void onSelected(String title, String smallTitle, int childId, int parentId,boolean isSelectFirst, boolean isChecked);
    }
}
