package com.tianchuang.ihome_b.adapter;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.ControlPointItemBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/16.
 * description:控制点的adapter
 */

public class TaskControlPointDetailListAdapter extends BaseQuickAdapter<ControlPointItemBean, BaseViewHolder> {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    public TaskControlPointDetailListAdapter(List<ControlPointItemBean> data) {
        super(R.layout.task_control_point_item_holder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ControlPointItemBean item) {
        helper.setText(R.id.tv_point_name, StringUtils.getNotNull(item.getName()))
                .setText(R.id.tv_point_address, StringUtils.getNotNull(item.getPlace()))
                .setText(R.id.tv_point_date, StringUtils.getNotNull(item.getTime()));
    }


}
