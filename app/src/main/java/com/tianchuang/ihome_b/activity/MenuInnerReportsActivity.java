package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.MenuInnerReportsFragment;

public class MenuInnerReportsActivity extends ToolBarActivity {


	@Override
	protected BaseFragment getFirstFragment() {
		return MenuInnerReportsFragment.newInstance();
	}

	@Override
	protected void initToolBar(Toolbar toolbar) {
		initNormalToolbar(toolbar,true);
		setToolbarTitle("内部报事");
	}
}
