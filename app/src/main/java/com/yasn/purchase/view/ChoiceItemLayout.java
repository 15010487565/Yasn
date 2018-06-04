package com.yasn.purchase.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.yasn.purchase.R;

/**
 * Created by gs on 2018/1/3.
 */

public class ChoiceItemLayout extends LinearLayout implements Checkable {

    private boolean mChecked;
    private Context context;
    public ChoiceItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        setBackgroundResource(checked? R.color.white: R.color.black_f7);
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}