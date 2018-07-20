package com.yasn.purchase.listener;

import android.view.View;

/**
 * Created by gs on 2017/12/29.
 */

public interface OnRcItemClickListener {
    void OnItemClick(View view, int position);

    void OnItemLongClick(View view, int position);

    //一布局更多点击事件/删除
    void OnClickTabMore(int listPosition);

    //子控件点击事件
    void OnClickRecyButton(int itemPosition, int listPosition);
}
