package com.tianchuang.ihome_b.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView date = helper.getView(R.id.tv_point_date);
        ImageView point = helper.getView(R.id.iv_little_point);
        TextView btn = helper.getView(R.id.tv_expand_button);
        if (item.isDone()) {
            btn.setText("任务详情");
            date.setText(StringUtils.getNotNull(item.getTime()) + "完成");
            date.setTextColor(ContextCompat.getColor(date.getContext(), R.color.app_primary_color));
            point.setImageDrawable(ContextCompat.getDrawable(point.getContext(), R.drawable.little_gray));
        } else {
            btn.setText("任务结果");
            point.setImageDrawable(ContextCompat.getDrawable(point.getContext(), R.drawable.little_red));
            date.setText(StringUtils.getNotNull(item.getTime()));
            date.setTextColor(ContextCompat.getColor(date.getContext(), R.color.TC_1));
        }
        helper.setText(R.id.tv_point_name, StringUtils.getNotNull(item.getName()))
                .setText(R.id.tv_point_address, StringUtils.getNotNull(item.getPlace()));
    }


}
