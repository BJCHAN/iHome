package com.tianchuang.ihome_b.view.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.TraceListAdapter;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:时间轴的底部
 */

public class MyOrderTimeLineViewHolder extends BaseHolder<List<MyOrderDetailBean.RepairsOrderLogVo>> {


	@BindView(R.id.rvTrace)
	RecyclerView rvTrace;

	@Override
	public View initHolderView() {
		View view = LayoutUtil.inflate(R.layout.myorder_time_line_footer_holder);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void bindData(final List<MyOrderDetailBean.RepairsOrderLogVo> list) {
		rvTrace.setLayoutManager(new LinearLayoutManager(rvTrace.getContext()));
		rvTrace.setAdapter(new TraceListAdapter(rvTrace.getContext(), list));
	}
}
