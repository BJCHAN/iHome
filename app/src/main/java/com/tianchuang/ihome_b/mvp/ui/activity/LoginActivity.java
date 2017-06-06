package com.tianchuang.ihome_b.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.mvp.ui.fragment.LoginFragment;
import com.tianchuang.ihome_b.utils.SystemUtil;
import com.tianchuang.ihome_b.utils.UserUtil;

/**
 * Created by Abyss on 2017/2/13.
 * description:登录注册模块
 */
public class LoginActivity extends ToolBarActivity {

	@Override
	protected BaseFragment getFirstFragment() {
		return LoginFragment.newInstance();
	}


	@Override
	protected void initToolBar(Toolbar toolbar) {
		super.initToolBar(toolbar);
		checkToWhere();
		getSupportActionBar().hide();
		SystemUtil.changeStatusBarColor(this, R.color.white);//改变状态栏颜色
		toolbar.setNavigationIcon(R.mipmap.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				closeFragment();
			}
		});
	}

	/**
	 * 判断是否已经登录，跳转主页
	 */
	private void checkToWhere() {
		if (UserUtil.isLogin() && !TextUtils.isEmpty(UserUtil.getToken())) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}

	/**
	 * 关闭当前的fragment
	 */
	public void closeFragment() {
		removeFragment();
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			setToolbarVisible(false);
		}
	}

	/**
	 * 关闭所有fragment
	 */
	public void closeAllFragment() {
		while (getSupportFragmentManager().getBackStackEntryCount() != 1) {
			closeFragment();
		}
	}

	/**
	 * 打开新的fragment
	 */
	public void openFragment(BaseFragment fragment) {
		setToolbarVisible(true);
		addFragment(fragment);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			setToolbarVisible(false);
		}
	}

	/**
	 * 设置状态栏和工具栏的颜色
	 */
	private void setToolbarVisible(Boolean isVisible) {
		if (isVisible) {
			getSupportActionBar().show();
			SystemUtil.changeStatusBarColor(this, R.color.app_primary_color);
		} else {
			getSupportActionBar().hide();
			SystemUtil.changeStatusBarColor(this, R.color.white);//改变状态栏颜色
		}
	}

}
