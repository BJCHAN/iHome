package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/2/23.
 * description:人工费类型适配器
 */
public class ChargeTypeAdapter extends BaseQuickAdapter<ChargeTypeListItemBean, BaseViewHolder> {
	public ChargeTypeAdapter(int layoutResId, List<ChargeTypeListItemBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, ChargeTypeListItemBean item) {
		helper.setText(R.id.tv_charge_name, item.getName())
		.setText(R.id.tv_charge_price,"￥"+item.getFees());

	}

	public String getNotNull(String string) {
		return StringUtils.getNotNull(string);
	}
}
