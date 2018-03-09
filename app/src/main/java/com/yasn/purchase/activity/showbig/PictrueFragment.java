package com.yasn.purchase.activity.showbig;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;

@SuppressLint("ValidFragment")
public class PictrueFragment extends Fragment{

	private int resId;
	private String  imageUrl;

	public PictrueFragment(int resId) {
		this.resId = resId;
	}
	public PictrueFragment(String imageUrl) {

		this.imageUrl = imageUrl;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.shoebig_scale_pic_item, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		ScaleView imageView = (ScaleView) view
				.findViewById(R.id.scale_pic_item);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.e("TAG_放大","关闭点击事件");
				getActivity().finish();
			}
		});
//		imageView.setImageResource(resId);
		Log.e("TAG_放大","imageUrl="+imageUrl);
		if (imageUrl.indexOf("http://")==-1){
			imageUrl = "http://"+imageUrl;
		}
		Glide.with(getActivity())
				.load(imageUrl)
				.centerCrop()
				.crossFade()
				.placeholder(R.mipmap.login_n_yasn)
				.error(R.mipmap.login_n_yasn)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(imageView);
	}
}

