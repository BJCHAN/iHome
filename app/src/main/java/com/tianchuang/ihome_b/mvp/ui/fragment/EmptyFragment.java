package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/2/20.
 * description:带提醒的空fragment
 */

public class EmptyFragment extends BaseFragment {
	@BindView(R.id.tv_tip)
	TextView tvTip;

	@Override
	protected int getLayoutId() {
		return R.layout.recycler_empty_view;
	}

	public static EmptyFragment newInstance(String tip) {
		Bundle bundle = new Bundle();
		bundle.putString("tip", tip);
		EmptyFragment emptyFragment = new EmptyFragment();
		emptyFragment.setArguments(bundle);
		return emptyFragment;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		String tip = getArguments().getString("tip");
		if (!TextUtils.isEmpty(tip)) tvTip.setText(tip);
	}


}
