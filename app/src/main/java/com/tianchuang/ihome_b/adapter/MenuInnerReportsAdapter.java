package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.PropertyListItemBean;
import com.tianchuang.ihome_b.utils.DateUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/2/23.
 * description:菜单内部报事的适配器
 */
public class MenuInnerReportsAdapter extends BaseQuickAdapter<MenuInnerReportsItemBean,BaseViewHolder> {
	public MenuInnerReportsAdapter(int layoutResId, List<MenuInnerReportsItemBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, MenuInnerReportsItemBean item) {
		PropertyListItemBean propertyEmployeeRoleVo = item.getPropertyEmployeeRoleVo();
		helper.setText(R.id.tv_content, item.getContent())
				.setText(R.id.tv_info, propertyEmployeeRoleVo.getEmployeeName()
						+ "/" + propertyEmployeeRoleVo.getDepartmentName()
						+ "-" + propertyEmployeeRoleVo.getPositionName())
				.setText(R.id.tv_date, DateUtils.formatDate(item.getLastUpdatedDae(), DateUtils.TYPE_01))
				.setText(R.id.tv_status, item.getStatusMsg());
	}

}
