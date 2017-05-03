package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.DataBuildingSearchBean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/2.
 * description:楼宇查询的适配器
 */

public class BuildingSearchInnerItemAdapter extends BaseQuickAdapter<DataBuildingSearchBean.ItemListBean, BaseViewHolder> {
	public BuildingSearchInnerItemAdapter(int layoutResId, List<DataBuildingSearchBean.ItemListBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, DataBuildingSearchBean.ItemListBean item) {
		helper.setText(R.id.tv_building_number, item.getNumber())
				.setText(R.id.tv_building_unit_and_floor, item.getUnit() + "个单元");
	}
}
