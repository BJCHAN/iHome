package com.tianchuang.ihome_b.view.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.RepairsFeeAdapter;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.bean.RepairsFeeBean;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:报修人员信息的底部
 */

public class MyOrderFeeInfoViewHolder extends BaseHolder<RepairsFeeBean> {
	@BindView(R.id.tv_price)
	TextView tvPrice;
	@BindView(R.id.rv_list)
	RecyclerView rvList;

	@Override
	public View initHolderView() {
		View view = LayoutUtil.inflate(R.layout.myorder_fee_footer_holder);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void bindData(final RepairsFeeBean bean) {
		String text = StringUtils.formatNum(bean.getTotalFee());
		tvPrice.setText("￥"+text);
		rvList.setLayoutManager(new LinearLayoutManager(rvList.getContext()));
		rvList.setAdapter(new RepairsFeeAdapter(R.layout.myorder_fee_adapter_item_holder,bean.getRepairsFeeVos()));
	}

}
