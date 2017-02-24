package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.InnerReportsFragment;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事页面（主页）
 */
public class InnerReportsActivity extends ToolBarActivity {

	@Override
	protected BaseFragment getFirstFragment() {
		return InnerReportsFragment.newInstance();
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
