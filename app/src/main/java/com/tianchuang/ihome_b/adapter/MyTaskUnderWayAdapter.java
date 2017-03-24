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
 * description:我的任务进行中的adapter
 */

public class MyTaskUnderWayAdapter extends BaseQuickAdapter<MyTaskUnderWayItemBean,BaseViewHolder> {

    public MyTaskUnderWayAdapter(int layoutResId, List<MyTaskUnderWayItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTaskUnderWayItemBean item) {
                helper.setText(R.id.tv_type, StringUtils.getNotNull(item.getTaskName()))
                        .setText(R.id.tv_content,StringUtils.getNotNull(DateUtils
                                .formatDate(item.getCreatedDate(),DateUtils.TYPE_04)+item.getTaskExplains()));
    }
}
