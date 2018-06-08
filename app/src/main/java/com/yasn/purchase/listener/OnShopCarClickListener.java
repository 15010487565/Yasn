package com.yasn.purchase.listener;

import android.view.View;

/**
 * Created by gs on 2017/12/29.
 */

public interface OnShopCarClickListener {

    void OnItemClick(View view, int position);
    //布局Title去凑单点击事件
    void OnClickMore(int listPosition);
    //删除
    void OnClickClean(int listPosition);
    //选中点击事件
    void OnClickSelected(int listPosition);
    //加点击事件
    void OnClickAdd(int listPosition);
    //减点击事件
    void OnClickSubtract(int listPosition);
    //EditText点击事件
    void setOnTouchListener(int listPosition);
}
