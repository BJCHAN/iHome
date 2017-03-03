package com.tianchuang.ihome_b.activity;

import android.os.Bundle;

import com.tianchuang.ihome_b.base.BaseActivity;

public class VisitorListActivity extends BaseActivity {

	@Override
	protected int getLayoutId() {
		return 0;
	}

	@Override
	protected int getFragmentContainerId() {
		return 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
	}
}
