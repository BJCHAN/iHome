package com.tianchuang.ihome_b.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.TaskRoomDataListBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/16.
 * description:
 */

public class TaskInputDetailListAdapter extends BaseQuickAdapter<TaskRoomDataListBean, BaseViewHolder> {
    public TaskInputDetailListAdapter(List<TaskRoomDataListBean> data) {
        super(R.layout.task_input_data_item_holder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskRoomDataListBean item) {
        TextView view = (TextView) helper.getView(R.id.tv_currentData);
        Float num = Float.valueOf(item.getCurrentData());
        view.setTextColor(ContextCompat.getColor(view.getContext(), num == 0 ? R.color.TC_1 : R.color.app_primary_color));
        view.setText(num == 0 ? "未录入" : String.valueOf(item.getCurrentData()));
        helper.setText(R.id.tv_address, StringUtils.getNotNull(item.getRoomInfo()));
    }
}
