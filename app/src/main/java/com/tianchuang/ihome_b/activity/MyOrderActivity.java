package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.fragment.MyOrderFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyOrderActivity extends ToolBarActivity {
	private TransferImage mTransferImage;

	@Override
	protected BaseFragment getFirstFragment() {
		return MyOrderFragment.newInstance();
	}

	@Override
	protected void handleIntent(Intent intent) {
		EventBus.getDefault().register(this);
	}

	@Override
	protected void initToolBar(Toolbar toolbar) {
		initNormalToolbar(toolbar, true);
		setToolbarTitle("我的订单");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(TransferLayoutEvent event) {//接收浏览图片layout的引用
		this.mTransferImage = event.transferImage;
	}

	//返回键返回事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			if (mTransferImage != null && mTransferImage.isShown()) {
				mTransferImage.dismiss();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
