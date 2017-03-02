package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.recyclerview.SimpleItemBean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/1.
 * description:简单条目的适配器
 */

public class SimpleItemAdapter extends BaseQuickAdapter<SimpleItemBean, BaseViewHolder> {


	public SimpleItemAdapter(int layoutResId, List<SimpleItemBean> data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, SimpleItemBean item) {
		helper.setText(R.id.tv_title,item.getText());
	}
}
