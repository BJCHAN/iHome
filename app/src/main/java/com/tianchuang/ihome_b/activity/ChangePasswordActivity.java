package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.ChangePasswordFragment;

/**
 * Created by Abyss on 2017/2/13.
 * description:修改密码模块
 */
public class ChangePasswordActivity extends ToolBarActivity {


	@Override
	protected BaseFragment getFirstFragment() {
		return ChangePasswordFragment.newInstance();
	}

	@Override
	protected void initToolBar(Toolbar toolbar) {
		setFinishWithAnim(true);
		setToolbarTitle("修改密码");
		toolbar.setNavigationIcon(R.mipmap.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				removeFragment();
			}
		});
	}
	public int getContainer() {
		return getFragmentContainerId();
	}
}
