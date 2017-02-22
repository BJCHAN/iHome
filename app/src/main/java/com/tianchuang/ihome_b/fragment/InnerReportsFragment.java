package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.InnerReportsActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事fragment(主页)
 */

public class InnerReportsFragment extends BaseFragment {

	@BindView(R.id.et_content)
	EditText etContent;
	@BindView(R.id.iv_add1)
	ImageView ivAdd1;
	@BindView(R.id.iv_add2)
	ImageView ivAdd2;
	@BindView(R.id.iv_add3)
	ImageView ivAdd3;
	@BindView(R.id.loginBt)
	Button loginBt;
	private InnerReportsActivity holdingActivity;

	public static InnerReportsFragment newInstance() {
		return new InnerReportsFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {

	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity = ((InnerReportsActivity) getHoldingActivity());
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_inner_reports;
	}


	@OnClick({R.id.iv_add1, R.id.iv_add2, R.id.iv_add3, R.id.loginBt})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_add1:
				break;
			case R.id.iv_add2:
				break;
			case R.id.iv_add3:
				break;
			case R.id.loginBt:
				FragmentUtils.popAddFragment(getFragmentManager(), holdingActivity.getFragmentContainerId(), InnerReportsSuccess.newInstance(), true);
				break;
		}
	}
}
