package com.yasn.purchase.func;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.yasn.purchase.activity.MainActivity;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.func.BaseTopImageBtnFunc;


public class AuthorBackTopBtnFunc extends BaseTopImageBtnFunc {

	public AuthorBackTopBtnFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.topbar_back;
	}

	@Override
	public int getFuncIcon() {
		return R.drawable.selector_back_btn;
	}

	@Override
	public void onclick(View v) {
		getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
	}

}
