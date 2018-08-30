package com.yasn.purchase.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yasn.purchase.R;

import java.util.List;

/**
 * Created by gs on 2018/8/28.
 */

public class CustomSpinner extends RelativeLayout {

    private Context context;
    private List<String> textList;
    private View customSpinnerView;
    public TextView showTextTv;
    private SpinnerPopWindow<String> mpopWindow;
    private String showTitle;
    private String showText;

    private OnCustomItemCheckedListener onCustomItemCheckedListener;

    public CustomSpinner(Context context, String showTitle, List<String> textList) {
        super(context);
        this.context = context;
        this.showTitle = showTitle;
        this.textList = textList;
        init();
    }

    public interface OnCustomItemCheckedListener{
        void OnCustomItemChecked(int position);
    }

    public void setOnCustomItemCheckedListener(OnCustomItemCheckedListener onCustomItemCheckedListener){
        this.onCustomItemCheckedListener = onCustomItemCheckedListener;
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customSpinnerView = inflater.inflate(R.layout.custom_spinner_layout, this);

        showTextTv = (TextView) customSpinnerView.findViewById(R.id.custom_msg_tv);
        showTextTv.setText(showTitle);

        showTextTv.setOnClickListener(clickListener);
        setTextImage(R.drawable.down1);

        mpopWindow = new SpinnerPopWindow<>(getContext(), textList, itemClickListener);
        mpopWindow.setOnDismissListener(dismissListener);
    }

    //设置显示的提示文字
    public String getShowText(){
        return showText;
    }

    //对PopupWindow监听选中用户点击的项
    public void setSelectionItem(int position){
        showText = textList.get(position);
        showTextTv.setText(showText);
    }

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setTextImage(R.drawable.down1);
        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mpopWindow.dismiss();
            showTextTv.setText(textList.get(position));
            showText = textList.get(position);

            //这个接口是用来在其他界面做点击操作的时候负责调用的
            if(onCustomItemCheckedListener!= null){
                onCustomItemCheckedListener.OnCustomItemChecked(position);
            }
        }
    };

    /**
     * 显示PopupWindow
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.custom_msg_tv:
                    mpopWindow.setWidth(showTextTv.getWidth());
                    mpopWindow.showAsDropDown(showTextTv);
                    setTextImage(R.drawable.down2);
                    break;
            }
        }
    };

    /**
     * 给TextView右边设置图片
     *
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        showTextTv.setCompoundDrawables(null, null, drawable, null);
    }

}
