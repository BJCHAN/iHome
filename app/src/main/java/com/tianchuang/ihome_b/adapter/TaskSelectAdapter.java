package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.QrCodeBean;

import java.util.List;

/**
 * Created by abyss on 2017/3/24.
 */

public class TaskSelectAdapter extends BaseQuickAdapter<QrCodeBean,BaseViewHolder> {
    public TaskSelectAdapter( List<QrCodeBean> data) {
        super(R.layout.task_select_item_holder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QrCodeBean item) {
        helper.setText(R.id.tv_title, item.getTaskName());
    }
}
