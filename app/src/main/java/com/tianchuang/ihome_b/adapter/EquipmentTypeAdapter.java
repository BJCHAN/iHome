package com.tianchuang.ihome_b.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:抢单大厅的适配器
 */
public class EquipmentTypeAdapter extends BaseQuickAdapter<EquipmentTypeSearchBean, BaseViewHolder> {


	public EquipmentTypeAdapter(int layoutResId, List<EquipmentTypeSearchBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, EquipmentTypeSearchBean item) {
		helper.setText(R.id.tv_equipment_name, item.getName());
		View view = helper.getView(R.id.tv_equipment_name);
		if (item.isSelected()) {
			view.setSelected(true);
		} else {
			view.setSelected(false);
		}
//		view.setSelected(item.isSelected());
	}

}
