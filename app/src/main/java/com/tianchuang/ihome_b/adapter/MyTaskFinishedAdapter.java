package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/15.
 * description:我的任务完成的的adapter
 */

public class MyTaskFinishedAdapter extends BaseQuickAdapter<MyTaskUnderWayItemBean, BaseViewHolder> {
    public MyTaskFinishedAdapter(List<MyTaskUnderWayItemBean> data) {
        super(R.layout.mytask_finnished_item_holder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTaskUnderWayItemBean item) {
        helper.setText(R.id.tv_type, StringUtils.getNotNull(item.getTaskName()))
                .setText(R.id.tv_date, StringUtils.getNotNull(DateUtils.formatDate(item.getCreatedDate(), DateUtils.TYPE_02)))
                .setText(R.id.tv_content, item.getTaskExplains());
    }
}
