package com.tianchuang.ihome_b.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:详情的不可回复的底部
 */

public class DetailFooterViewHolder extends BaseHolder<String> {


	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.tv_content)
	TextView tvContent;

	@Override
	public View initHolderView() {
		View view = LayoutUtil.inflate(R.layout.multi_detail_footer_holder);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void bindData(String data) {
		tvContent.setText(data);
		tvTitle.setText("投诉回复");
	}
}
