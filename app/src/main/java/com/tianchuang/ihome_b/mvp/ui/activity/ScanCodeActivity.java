package com.tianchuang.ihome_b.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.mvp.ui.fragment.ScanCodeFragment;
import com.tianchuang.ihome_b.utils.zxing.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanCodeActivity extends AppCompatActivity {

	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;
	@BindView(R.id.ac_toolbar_toolbar)
	Toolbar toolbar;
	private ScanCodeFragment scanCodeFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_code);
		ButterKnife.bind(this);
		scanCodeFragment = new ScanCodeFragment();
		// 为二维码扫描界面设置定制化界面
		CodeUtils.setFragmentArgs(scanCodeFragment, R.layout.my_camera);
		scanCodeFragment.setAnalyzeCallback(analyzeCallback);
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, scanCodeFragment).commit();

		initView();
	}

	private void initView() {
		toolbarTitle.setText("扫一扫");
		toolbar.setNavigationIcon(R.mipmap.back);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}


	/**
	 * 二维码解析回调函数
	 */
	CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
		@Override
		public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
			bundle.putString(CodeUtils.RESULT_STRING, result);
			resultIntent.putExtras(bundle);
			ScanCodeActivity.this.setResult(RESULT_OK, resultIntent);
			ScanCodeActivity.this.finish();
		}

		@Override
		public void onAnalyzeFailed() {
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
			bundle.putString(CodeUtils.RESULT_STRING, "");
			resultIntent.putExtras(bundle);
			ScanCodeActivity.this.setResult(RESULT_OK, resultIntent);
			ScanCodeActivity.this.finish();
		}
	};

}
