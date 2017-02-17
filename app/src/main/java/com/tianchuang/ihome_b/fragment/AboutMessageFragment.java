package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

/**
 * Created by Abyss on 2017/2/13.
 * description:关于物管宝界面
 */

public class AboutMessageFragment extends BaseFragment {

	public static AboutMessageFragment newInstance() {
		return new AboutMessageFragment();
	}


	@Override
	protected void initView(View view, Bundle savedInstanceState) {

	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_about_message;
	}
}
