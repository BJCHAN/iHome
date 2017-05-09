package com.tianchuang.ihome_b.view.viewholder;

import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.bean.EvaluateBean;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:评价信息的底部
 */

public class MyOrderEvaluatenfoViewHolder extends BaseHolder<EvaluateBean> {

	@BindView(R.id.ratingBar)
	AppCompatRatingBar ratingBar;
	@BindView(R.id.tv_evaluate_content)
	TextView tvEvaluateContent;

	@Override
	public View initHolderView() {
		View view = LayoutUtil.inflate(R.layout.myorder_evaluate_footer_holder);
		ButterKnife.bind(this, view);
		return view;
	}

	public void bindData(final EvaluateBean bean) {
		ratingBar.setRating(bean.getStars());
		tvEvaluateContent.setText(StringUtils.getNotNull(bean.getEvaluateContent()));
	}

}
