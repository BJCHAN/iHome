package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:维修费用的适配器
 */
public class RepairsFeeAdapter extends BaseQuickAdapter<MyOrderDetailBean.RepairsFeeVosBean, BaseViewHolder> {
	public RepairsFeeAdapter(int layoutResId, List<MyOrderDetailBean.RepairsFeeVosBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, MyOrderDetailBean.RepairsFeeVosBean item) {
		helper.setText(R.id.tv_fee_name, item.getTitle())
				.setText(R.id.tv_fee_num, "￥" + StringUtils.formatNum(item.getFee()));
	}

}
