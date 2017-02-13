package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;

/**
 * Created by Abyss on 2017/2/13.
 * description:查看协议界面
 */

public class ProtocolNoteFragment extends BaseFragment {

	private LoginActivity holdingActivity;

	public static ProtocolNoteFragment newInstance() {
		return new ProtocolNoteFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((LoginActivity) getHoldingActivity());
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("用户注册协议");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_protocol_note;
	}
}
