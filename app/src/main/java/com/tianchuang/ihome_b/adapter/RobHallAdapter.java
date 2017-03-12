package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.RobHallListItem;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:抢单大厅的适配器
 */
public class RobHallAdapter extends BaseQuickAdapter<RobHallListItem, BaseViewHolder> {
	public RobHallAdapter(int layoutResId, List<RobHallListItem> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, RobHallListItem item) {
		helper.setText(R.id.tv_type, StringUtils.getNotNull(item.getRepairsTypeName()))
				.setText(R.id.tv_picture_num, StringUtils.getNotNull(item.getImgCount() + ""))
				.setText(R.id.tv_date, StringUtils.getNotNull(DateUtils.formatDate(item.getCreatedDate(), DateUtils.TYPE_02)))
				.setText(R.id.tv_content, StringUtils.getNotNull(item.getContent()));
	}

}
