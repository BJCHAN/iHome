package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

/**
 * Created by Abyss on 2017/2/15.
 * description:设置页面
 */

public class SettingFragment extends BaseFragment {
	@Override
	protected void initView(View view, Bundle savedInstanceState) {

	}

	public static SettingFragment newInstance() {
		return new SettingFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_setting;
	}
}
