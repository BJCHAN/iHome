package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tianchuang.ihome_b.R;
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
		toolbar.setNavigationIcon(R.mipmap.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				removeFragment();
			}
		});
		setToolbarTitle("内部报事");
	}
}
