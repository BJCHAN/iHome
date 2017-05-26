package com.tianchuang.ihome_b.mvp.ui.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.mvp.ui.fragment.AboutMessageFragment;

public class AboutMessageActivity extends ToolBarActivity {


	@Override
	protected BaseFragment getFirstFragment() {
		return AboutMessageFragment.newInstance();
	}

	@Override
	protected void initToolBar(Toolbar toolbar) {
		setToolbarTitle("关于i家帮");
		initNormalToolbar(toolbar,true);
	}
}
