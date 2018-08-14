package org.angmarch.views;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author angelo.marchesin
 */
@SuppressLint("AppCompatCustomView")
@SuppressWarnings("unused")
public class ArrowsSpinner extends TextView {

    private static final int MAX_LEVEL = 10000;
    private static final int DEFAULT_ELEVATION = 16;
    private static final String INSTANCE_STATE = "instance_state";
    private static final String SELECTED_INDEX = "selected_index";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";

    private int selectedIndex;
    private Drawable drawable;

    private boolean isArrowHide;
    private int textColor;
    private int backgroundSelector;

    private Context context;
    private boolean isArrowAnimate = true;//是否有动画效果
    @SuppressWarnings("ConstantConditions")
    public ArrowsSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public ArrowsSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ArrowsSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @SuppressLint("NewApi")
	private void init(Context context, AttributeSet attrs) {
        this.context = context;
        Resources resources = getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceSpinner);
        int defaultPadding = resources.getDimensionPixelSize(R.dimen.one_and_a_half_grid_unit);

        setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        setPadding(resources.getDimensionPixelSize(R.dimen.three_grid_unit), defaultPadding, defaultPadding,
            defaultPadding);
        setClickable(true);

        backgroundSelector = typedArray.getResourceId(R.styleable.NiceSpinner_backgroundSelector, R.drawable.selector);
        setBackgroundResource(backgroundSelector);
        textColor = typedArray.getColor(R.styleable.NiceSpinner_textTint, -1);
        setTextColor(textColor);

        isArrowHide = typedArray.getBoolean(R.styleable.NiceSpinner_hideArrow, false);
        if (!isArrowHide) {
            Drawable basicDrawable = ContextCompat.getDrawable(context, R.drawable.arrow);
            int resId = typedArray.getColor(R.styleable.NiceSpinner_arrowTint, -1);
            if (basicDrawable != null) {
                drawable = DrawableCompat.wrap(basicDrawable);
                if (resId != -1) {
                    DrawableCompat.setTint(drawable, resId);
                }
            }
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }

        typedArray.recycle();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && isArrowAnimate) {
            if (!isArrowHide) {
                animateArrow(!isArrowHide);
//                setTextColor(ContextCompat.getColor(context,R.color.orange));
            } else {
                animateArrow(!isArrowHide);
//                setTextColor(ContextCompat.getColor(context,R.color.black_33));
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 箭头方向
     * @param shouldRotateUp true箭头朝下
     */
    public void animateArrow(boolean shouldRotateUp) {
        isArrowHide = (shouldRotateUp);
        int start = shouldRotateUp ? 0 : MAX_LEVEL;
        int end = shouldRotateUp ? MAX_LEVEL : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(drawable, "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.start();
    }

    public boolean isArrowHide(){
        return isArrowHide;
    }

    public void setArrowAnimate(boolean isArrowAnimate) {
       this.isArrowAnimate = isArrowAnimate;
    }
    public void setTintColor(@ColorRes int resId) {
        if (drawable != null && !isArrowHide) {
            DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), resId));
        }
    }
}
