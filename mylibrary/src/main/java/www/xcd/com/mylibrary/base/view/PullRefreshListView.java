package www.xcd.com.mylibrary.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import www.xcd.com.mylibrary.R;

/**
 * Created by Android on 2017/9/29.
 */

public class PullRefreshListView extends ListView implements AbsListView.OnScrollListener {

    private Context context;

    private View headerView;

    private final int STATE_PULL_REFRESH = 0;

    private final int STATE_RELEASE_REFRESH = 1;

    private final int STATE_REFRESHING = 2;

    private int currentState = STATE_PULL_REFRESH;

    private TextView mRefreshStatusTip;

    private TextView mRefreshTime;

    private ProgressBar mProgressBar;

    private ImageView mArrowImg;

    private int headerViewHeight;

    private int startY = -1;   //初始值

    private RotateAnimation upAnimation;

    private RotateAnimation downAnimation;

    private View footerView;

    private int footerViewHeight;

    public PullRefreshListView(Context context) {
        this(context, null);
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        context = getContext();
        initView();
        initData();


    }

    private void initData() {
        //初始化头布局
        headerView.measure(0, 0);
        //得到头布局高度
        headerViewHeight = headerView.getMeasuredHeight();
        //设置上边距，隐藏头布局
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        //将头布局添加至listView中
        this.addHeaderView(headerView, null, false); //头布局selectable=false

        //初始化底部布局数据
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, 0, 0, -footerViewHeight);
        this.addFooterView(footerView, null, false);

        //初始化动画
        initAnimation();

    }


    private void initView() {
        //初始化listView头布局
        headerView = View.inflate(context, R.layout.layout_pull_listview_header, null);
        mProgressBar = (ProgressBar) headerView.findViewById(R.id.pb_refresh);
        mArrowImg = (ImageView) headerView.findViewById(R.id.iv_arrow);
        mRefreshStatusTip = (TextView) headerView.findViewById(R.id.tv_listview_top_tip);
        mRefreshTime = (TextView) headerView.findViewById(R.id.tv_listview_top_time);

        //初始化底部布局
        footerView = View.inflate(context, R.layout.layout_pull_listview_footer, null);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                //如果当前正在刷新，则不处理
                if (currentState == STATE_REFRESHING) {
                    break;
                }

                if (startY == -1) {
                    startY = (int) event.getRawY();   //保证startY有值
                }
                int deltaY = (int) (event.getRawY() - startY);  //手指移动的偏移量
                if (deltaY > 0 && getFirstVisiblePosition() == 0) {  //只有手指向下滑动(deltaY>0)并且第一个item为可见的时候，才下拉刷新
                    int paddingTop = -headerViewHeight + deltaY;
                    headerView.setPadding(0, paddingTop, 0, 0);  //将更新的padding设置给headerview,实时更新下拉布局的位置
                    if (paddingTop > 0 && currentState != STATE_RELEASE_REFRESH) {
                        currentState = STATE_RELEASE_REFRESH;
                        mArrowImg.startAnimation(upAnimation);
                    } else if (paddingTop < 0 && currentState != STATE_PULL_REFRESH) {
                        currentState = STATE_PULL_REFRESH;
                        mArrowImg.startAnimation(downAnimation);
                    }
                    return true;  //拦截move事件，不让listview处理
                }
                break;

            case MotionEvent.ACTION_UP:

                startY = -1;  //重置

                if (currentState == STATE_PULL_REFRESH) {  //手指抬起时，如果当前状态是下拉刷新，则直接隐藏头布局。
                    headerView.setPadding(0, -headerViewHeight, 0, 0);

                } else if (currentState == STATE_RELEASE_REFRESH) {   //手指抬起时，如果当前状态是松开刷新，则进入正在刷新状态
                    currentState = STATE_REFRESHING;
                    //显示正在刷新状态
                    headerView.setPadding(0, 0, 0, 0);
                    //更新当前状态
                    refreshHeaderState(currentState);

                    //监听回调
                    if (onRefreshListener != null) {
                        onRefreshListener.onRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    private void refreshHeaderState(int currentState) {
        switch (currentState) {
            case STATE_PULL_REFRESH:
                mRefreshStatusTip.setText(getResources().getString(R.string.state_pull_refresh));
                mArrowImg.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                break;
            case STATE_RELEASE_REFRESH:
                mRefreshStatusTip.setText(getResources().getString(R.string.state_release_refresh));
                mArrowImg.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                break;
            case STATE_REFRESHING:
                mRefreshStatusTip.setText(getResources().getString(R.string.state_refreshing));
                mArrowImg.clearAnimation();  //清除动画才能设置其可见性
                mArrowImg.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                mRefreshTime.setText(getResources().getString(R.string.last_refresh_time));
                break;
        }
    }


    private void initAnimation() {
        //向上旋转动画
        upAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(300);
        upAnimation.setFillAfter(true);
        //向下旋转动画
        downAnimation = new RotateAnimation(-180, -360, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(300);
        downAnimation.setFillAfter(true);
    }


    /**
     * 重置headerview中的刷新状态和progressbar的显示
     */
    public void resetHeaderFooterView() {
        currentState = STATE_PULL_REFRESH;
        mRefreshStatusTip.setText(getResources().getString(R.string.state_pull_refresh));
        mArrowImg.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    interface OnRefreshListener {
        void onRefresh();

        void loadMore();  //加载更多，通过listview的滚动监听实现
    }

    OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }


    /**
     * ListView的滑动监听
     *
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_FLING || scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (getLastVisiblePosition() == getCount() - 1) {   //滑动到最后一条，显示tooterView
                footerView.setPadding(0, 0, 0, 0);
                setSelection(getCount() - 1);  //将最后一条拉到屏幕中显示，这样，footerview就可以显示出来了，否则，footerview需要再次滑动才能显示在屏幕中
            }
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
