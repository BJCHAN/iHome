package com.tianchuang.ihome_b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.InnerReportsActivity;
import com.tianchuang.ihome_b.activity.MainActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.event.OpenScanEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;


/**
 * Created by Abyss on 2017/2/9.
 * description:主页
 */

public class MainFragment extends BaseFragment {

	private MainActivity holdingActivity;

	public static MainFragment newInstance() {
		return new MainFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity = ((MainActivity) getHoldingActivity());
		holdingActivity.setSpinnerText("海创园小区");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_main;
	}


	@OnClick({R.id.ll_rich_scan, R.id.ll_write_form, R.id.ll_internal_reports, R.id.ll_main_query})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.ll_rich_scan://扫一扫
				EventBus.getDefault().post(new OpenScanEvent());
				break;
			case R.id.ll_write_form://表单填报
				break;
			case R.id.ll_internal_reports://内部报事
				startActivity(new Intent(getHoldingActivity(), InnerReportsActivity.class));
				break;
			case R.id.ll_main_query://查询
				break;
		}
	}
}
