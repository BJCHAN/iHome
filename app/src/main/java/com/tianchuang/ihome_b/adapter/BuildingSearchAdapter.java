package com.tianchuang.ihome_b.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.DataBuildingSearchBean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/2.
 * description:楼宇查询的适配器
 */

public class BuildingSearchAdapter extends BaseQuickAdapter<DataBuildingSearchBean, BaseViewHolder> {
	public BuildingSearchAdapter(int layoutResId, List<DataBuildingSearchBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, DataBuildingSearchBean item) {
		helper.setText(R.id.tv_building_name,item.getName());
		RecyclerView mList = (RecyclerView) helper.getView(R.id.rv_list);
		mList.setLayoutManager(new LinearLayoutManager(mList.getContext()));
		mList.setAdapter(new BuildingSearchInnerItemAdapter(R.layout.building_search_inner_item_holder,item.getItemList()));
	}
}
