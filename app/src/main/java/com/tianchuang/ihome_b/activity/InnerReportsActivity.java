package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.InnerReportsFragment;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事页面（主页）
 */
public class InnerReportsActivity extends ToolBarActivity {

	public static final int REQUEST_CODE_IMAGE = 201;

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

	public void startImageSelector(ImgSelConfig config) {
		// 跳转到图片选择器
		ImgSelActivity.startActivity(this, config, REQUEST_CODE_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 图片选择结果回调,三张图片
		if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
			List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
			if (getImageByCodeListener != null) {
				getImageByCodeListener.onImage(pathList);
			}
		}
	}

	public void setGetImageByCodeListener(GetImageByCodeListener getImageByCodeListener) {
		this.getImageByCodeListener = getImageByCodeListener;
	}

	private GetImageByCodeListener getImageByCodeListener;

	public interface GetImageByCodeListener {
		void onImage(List<String> path);
	}
}
