package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事（菜单）
 */

public class MenuInnerReportsFragment extends BaseFragment {
	public static MenuInnerReportsFragment newInstance() {
		return new MenuInnerReportsFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {

	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_menu_inner_reports;
	}
}
