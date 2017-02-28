package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.AboutMessageFragment;

public class AboutMessageActivity extends ToolBarActivity {


	@Override
	protected BaseFragment getFirstFragment() {
		return AboutMessageFragment.newInstance();
	}

	@Override
	protected void initToolBar(Toolbar toolbar) {
		setToolbarTitle("关于物管宝");
		initNormalToolbar(toolbar,true);
	}
}
