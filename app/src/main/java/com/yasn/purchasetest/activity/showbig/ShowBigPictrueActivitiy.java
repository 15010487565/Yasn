package com.yasn.purchasetest.activity.showbig;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Window;

import com.yasn.purchasetest.R;
import com.zdp.aseo.content.AseoZdpAseo;

import java.util.ArrayList;

public class ShowBigPictrueActivitiy extends FragmentActivity{

	private HackyViewPager viewPager;
//	private int[] resId = { R.mipmap.qqfriends,  R.mipmap.baitiao,  R.mipmap.qqfriends,R.mipmap.login_y_yasn};
	/**得到上一个界面点击图片的位置*/
	private int position=0;
	ArrayList<String> imageList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.showbig_pictrue_a);
		Intent intent=getIntent();
		imageList = intent.getStringArrayListExtra("ImageList");
		position = intent.getIntExtra("position",0);
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		initViewPager();
	}

	private void initViewPager(){

		viewPager = (HackyViewPager) findViewById(R.id.viewPager_show_bigPic);
		ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		//跳转到第几个界面
		viewPager.setCurrentItem(position);
//		Log.e("TAG_showbig", "点击了"+position);
	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter{

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
//			Log.e("TAG_showbig", "getItem="+position);
			String imageUrl = imageList.get(position);
			return new PictrueFragment(imageUrl);
		}

		@Override
		public int getCount() {
			return imageList.size();
		}

	}

}
