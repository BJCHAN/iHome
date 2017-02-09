package com.tianchuang.ihome_b.base;

import android.view.KeyEvent;


/**
 * Created by Abyss on 2017/2/8.
 * description:FragmentActivity的基类
 */

public abstract class BaseActivity extends RxFragmentActivity {
	//布局文件ID
	protected abstract int getLayoutId();

	//添加Fragment容器的ID
	protected abstract int getFragmentContainerId();

	//添加fragment
	protected void addFragment(BaseFragment fragment) {
		if (fragment != null) {
			getSupportFragmentManager().beginTransaction()
					.replace(getFragmentContainerId(), fragment, fragment.getClass().getSimpleName())
					.addToBackStack(fragment.getClass().getSimpleName())
					.commitAllowingStateLoss();
		}
	}

	//移除fragment
	protected void removeFragment() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
			getSupportFragmentManager().popBackStack();
		} else {
			finish();
		}
	}

	//返回键返回事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
				finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
