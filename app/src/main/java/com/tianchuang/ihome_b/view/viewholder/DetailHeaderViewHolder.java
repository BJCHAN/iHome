package com.tianchuang.ihome_b.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.bean.DetailHeaderBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:详情的头部
 */

public class DetailHeaderViewHolder extends BaseHolder<DetailHeaderBean> {
	@BindView(R.id.tv_type)
	TextView tvType;
	@BindView(R.id.tv_date)
	TextView tvDate;

	@Override
	public View initHolderView() {
		View view = LayoutUtil.inflate(R.layout.multi_detail_header_holder);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void bindData(DetailHeaderBean data) {
		tvType.setText(data.getTypeName());
		tvDate.setText(DateUtils.formatDate(data.getCreatedDate(), DateUtils.TYPE_03));
	}
}
