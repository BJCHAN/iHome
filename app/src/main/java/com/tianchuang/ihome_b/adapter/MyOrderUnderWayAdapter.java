package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.MyOrderCommonBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/2/23.
 * description:我的订单进行中适配器
 */
public class MyOrderUnderWayAdapter extends BaseQuickAdapter<MyOrderCommonBean, BaseViewHolder> {
	public MyOrderUnderWayAdapter(int layoutResId, List<MyOrderCommonBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, MyOrderCommonBean item) {
		helper.setText(R.id.tv_type, getNotNull(item.getTypeName()))
				.setText(R.id.tv_picture_num, item.getImgCount() + "")
				.setText(R.id.tv_date, DateUtils.formatDate(item.getCreatedDate(), DateUtils.TYPE_02))
				.setText(R.id.tv_content, getNotNull(item.getContent()))
				.setText(R.id.tv_status, getNotNull(item.getStatusMsg()));
	}

	public String getNotNull(String string) {
		return StringUtils.getNotNull(string);
	}
}
