package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.LoginFragment;
import com.tianchuang.ihome_b.utils.SystemUtil;

/**
 * Created by Abyss on 2017/2/13.
 * description:登录注册模块
 */
public class LoginActivity extends ToolBarActivity {
	private Toolbar toolbar;

	@Override
	protected BaseFragment getFirstFragment() {
		return LoginFragment.newInstance();
	}


	@Override
	protected void initToolBar(Toolbar toolbar) {
		this.toolbar = toolbar;
		SystemUtil.changeStatusBarColor(this, R.color.white);//改变状态栏颜色
		toolbar.setNavigationIcon(R.mipmap.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeFragment();
			}
		});
		toolbar.setVisibility(View.GONE);
	}

	/**
	 * 关闭当前的fragment
	 */
	public void closeFragment() {
		removeFragment();
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			toolbar.setVisibility(View.GONE);
			SystemUtil.changeStatusBarColor(this, R.color.white);//改变状态栏颜色
		}
	}

	/**
	 * 打开新的fragment
	 */
	public void openFragment(BaseFragment fragment) {
		if (toolbar.getVisibility() == View.GONE) {
			toolbar.setVisibility(View.VISIBLE);
			SystemUtil.changeStatusBarColor(this, R.color.app_primary_color);
		}
		addFragment(fragment);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			toolbar.setVisibility(View.GONE);
			SystemUtil.changeStatusBarColor(this, R.color.white);
		}
	}
}
