package com.tianchuang.ihome_b.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.event.PlayPhoneEvent;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.mvp.ui.fragment.ConfirmFixedFragment;
import com.tianchuang.ihome_b.mvp.ui.fragment.MyOrderFragment;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
/**
 * Created by Abyss on 2017/3/9.
 * description:我的工单
 */
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
		setToolbarTitle("我的工单");
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

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(PlayPhoneEvent event) {//接收浏览图片layout的引用
		call(event.getPhone());
	}

	/**
	 * 调用拨号界面
	 *
	 * @param phone 电话号码
	 */
	private void call(String phone) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	public void startImageSelector(ImgSelConfig config, int type) {
		// 跳转到图片选择器
		ImgSelActivity.startActivity(this, config, type);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 图片选择结果回调,三张图片
		if (requestCode == ConfirmFixedFragment.TYPE_BEFORE && resultCode == RESULT_OK && data != null) {
			List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
			if (getImageByCodeListener != null) {
				getImageByCodeListener.onImageBefore(pathList);
			}
		} else if (requestCode == ConfirmFixedFragment.TYPE_AFTER && resultCode == RESULT_OK && data != null) {
			List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
			if (getImageByCodeListener != null) {
				getImageByCodeListener.onImageAfter(pathList);
			}
		}
	}


	public void setGetImageByCodeListener(GetImageByCodeListener getImageByCodeListener) {
		this.getImageByCodeListener = getImageByCodeListener;
	}

	private GetImageByCodeListener getImageByCodeListener;

	public interface GetImageByCodeListener {
		void onImageBefore(List<String> paths);

		void onImageAfter(List<String> paths);
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
