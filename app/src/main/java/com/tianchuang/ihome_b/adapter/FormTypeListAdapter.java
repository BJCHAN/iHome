package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/10.
 * description:
 */

public class FormTypeListAdapter extends BaseQuickAdapter<FormTypeItemBean, BaseViewHolder> {
    public FormTypeListAdapter(int layoutResId, List<FormTypeItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FormTypeItemBean item) {
        helper.setText(R.id.tv_title, item.getName());
    }
}
