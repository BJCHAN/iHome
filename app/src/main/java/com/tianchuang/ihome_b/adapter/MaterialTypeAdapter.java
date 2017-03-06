package com.tianchuang.ihome_b.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.bean.MyOrderCommonBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/2/23.
 * description:材料类型适配器
 */
public class MaterialTypeAdapter extends BaseQuickAdapter<MaterialListItemBean, BaseViewHolder> {
	public MaterialTypeAdapter(int layoutResId, List<MaterialListItemBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, MaterialListItemBean item) {
		helper.setText(R.id.tv_material_name, item.getTypeName());

	}

	public String getNotNull(String string) {
		return StringUtils.getNotNull(string);
	}
}
