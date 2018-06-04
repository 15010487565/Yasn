package com.yasn.purchase.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yasn.purchase.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by gs on 2017/12/26.
 */

public class MultiSwipeRefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    private float startY;
    private float startX;
    // 记录viewPager是否拖拽的标记
    private boolean mIsBeingDragged;
    private View mSwipeableChildren;
    //点击监听，关闭软键盘
    private OnMultiSwipeRefreshClickListener onClickListener;
    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    /**
     * ListView的加载中footer
     */
    private View mListViewFooter;

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * listview实例
     */
    private ListView mListView;
    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;


    public MultiSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(
                R.layout.include_footview, null, false);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 滚动时到了最底部也可以加载更多
        if (canLoad()) {
            loadData();
        }
    }

    public interface OnMultiSwipeRefreshClickListener {
        void OnMultiSwipeRefreshClick();
    }

    public void setMultiSwipeRefreshClickListener(OnMultiSwipeRefreshClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float currentX = ev.getX();
        float currentY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = currentY;
                startX = currentX;
                mIsBeingDragged = false;
                if (onClickListener != null) {
                    onClickListener.OnMultiSwipeRefreshClick();
                }
                // 按下
                mYDown = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsBeingDragged) {
                    return false;
                }
                float dx = Math.abs(currentX - startX);
                float dy = Math.abs(currentY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (dx > dy && dx > mTouchSlop) {
                    mIsBeingDragged = true;
                    return false;
                }
                // 移动
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                break;
        }
        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev);
    }
    /* 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
//        return  !isLoading && isPullUp();
    }
    /**
     * 判断是否到了最底部
     */
    private boolean isBottom;
    public void setBottom(boolean isBottom) {
        this.isBottom =isBottom;
    }
    public boolean isBottom() {

//        if (mListView != null && mListView.getAdapter() != null) {
//            int lastVisiblePosition = mListView.getLastVisiblePosition();
//            int listCount = mListView.getAdapter().getCount() - 1;
//            return lastVisiblePosition == listCount;
//        }
        return isBottom;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    public boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }
    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
//            mListView.addFooterView(mListViewFooter);
        } else {
//            mListView.removeFooterView(mListViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }
    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }
    /**
     * 传入SwipeRefreshLayout的Target的id，如R.id.viewPager
     * swipeRefreshLayout.setSwipeableChildren(R.id.viewPager);
     *
     * @param ids
     */
    public void setSwipeableChildren(final int ids) {
        mSwipeableChildren = findViewById(ids);
    }

    @Override
    public boolean canChildScrollUp() {
        return canViewScrollUp(mSwipeableChildren);
    }

    /**
     * 根据传入的ViewPager，查找内部的ListView，并判断其是否到顶部
     *
     * @param view
     * @return
     */
    private static boolean canViewScrollUp(View view) {
        if (view != null && view instanceof ViewPager) {
            for (int i = 0; i < ((ViewPager) view).getChildCount(); i++) {
                View child = ((ViewPager) view).getChildAt(i);
                if (child.isShown()) {
                    if (child instanceof RelativeLayout) {
                        View subChild = ((RelativeLayout) child).getChildAt(0);
                        if (subChild instanceof AbsListView) {
                            final AbsListView listView = (AbsListView) subChild;
                            return ViewCompat.canScrollVertically(listView, -1);
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 加载更多的监听器
     */
    public static interface OnLoadListener {
        public void onLoad();
    }
    /**
     * 自动刷新
     * 代码混淆 keep 掉 MultiSwipeRefreshLayout
     * 不keep正式版本是不会自动刷新,debug版本会,很多人会以为是机型问题,别问我是怎么知道的
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            // setRefreshing，参数为boolean,boolean类型的方法
            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            // 若调用私有方法，必须抑制java对权限的检查
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
