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
	protected int addFragment(BaseFragment fragment) {
		int i = 0;
		if (fragment != null) {
			i = getSupportFragmentManager().beginTransaction()
					.replace(getFragmentContainerId(), fragment, fragment.getClass().getSimpleName())
					.addToBackStack(fragment.getClass().getSimpleName())
					.commitAllowingStateLoss();
		}
		return i;
	}

	//移除fragment
	protected void removeFragment() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
			getSupportFragmentManager().popBackStack();
		} else {
			finish();
		}
	}


}
