package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yasn.purchase.model.FindModel;
import com.yasn.purchase.video.fragment.FindAllFragment;

import java.util.List;

/**
 * Created by gs on 2018/2/8.
 */

public class FindPagerAdapter extends FragmentPagerAdapter {

    private List<FindModel.TitleBean> list;
    private Context context;
    public FindPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    public void setData(List<FindModel.TitleBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int paramInt) {

        return new FindAllFragment(list,paramInt);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getClassifyName();
    }
    @Override
    public int getCount() {
        return list ==null?0:list.size();
    }

}
