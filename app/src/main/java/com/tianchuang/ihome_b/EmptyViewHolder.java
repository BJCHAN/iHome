package com.tianchuang.ihome_b;

import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:空页面
 */

public class EmptyViewHolder extends BaseHolder<String> {
	@BindView(R.id.tv_tip)
	TextView tvTip;

	@Override
	public View initHolderView() {
		View view = LayoutUtil.inflate(R.layout.recycler_empty_view);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void bindData(String data) {
		tvTip.setText(StringUtils.getNotNull(data));
	}
}
