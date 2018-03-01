package com.yasn.purchase.activityold;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;

import com.yasn.purchase.utils.DensityUtil;

/**
 * Created by chen on 2017/2/13.
 */

/**
 * 首页侧滑，处理滑动有效范围
 */
public class DrawerView extends DrawerLayout {

    public DrawerView(Context context) {
        super(context);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            if (ev.getPointerCount() == 1 && !isDrawerOpen(Gravity.RIGHT)) {
                if (ev.getY() < DensityUtil.getScreenHeight(getContext()) * 2 / 3 || ev.getY() > DensityUtil.getScreenHeight(getContext()) * 7 / 8) {
                    return false;
                }
            }
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DrawerView", "dispatchTouchEvent: "+e );
            return false;
        }
    }

    private boolean mIsDisallowIntercept = false;

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // keep the info about if the innerViews do requestDisallowInterceptTouchEvent
        mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            // the incorrect array size will only happen in the multi-touch scenario.
            if (ev.getPointerCount() > 1 && mIsDisallowIntercept) {
                requestDisallowInterceptTouchEvent(false);
                boolean handled = super.dispatchTouchEvent(ev);
                requestDisallowInterceptTouchEvent(true);
                return handled;
            } else {
                return super.dispatchTouchEvent(ev);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DrawerView", "dispatchTouchEvent: "+e );
            return false;
        }
    }

}
