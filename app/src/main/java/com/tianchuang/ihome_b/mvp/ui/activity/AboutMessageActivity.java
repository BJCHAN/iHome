package com.tianchuang.ihome_b.mvp.ui.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.mvp.ui.fragment.AboutMessageFragment;

import org.jetbrains.annotations.NotNull;

public class AboutMessageActivity extends ToolBarActivity {


	@Override
	protected BaseFragment getFirstFragment() {
		return AboutMessageFragment.newInstance();
	}

	@Override
	protected void initToolBar(@NotNull Toolbar toolbar) {
		super.initToolBar(toolbar);
		setToolbarTitle("关于i家帮");
	}
}
