package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItem;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:抢单大厅的适配器
 */
public class RobHallAdapter extends BaseQuickAdapter<RobHallItem,BaseViewHolder> {
	public RobHallAdapter(int layoutResId, List<RobHallItem> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, RobHallItem item) {
		helper.setText(R.id.tv_type, item.getType())
				.setText(R.id.tv_picture_num, item.getPictureNum())
				.setText(R.id.tv_date, item.getDate())
				.setText(R.id.tv_content, item.getContent());

	}

}
