package com.tianchuang.ihome_b.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.TaskAreaListBean;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:我的任务选择楼宇的适配器
 */
public class TaskBuildingAdapter extends BaseQuickAdapter<TaskAreaListBean, BaseViewHolder> {


    public TaskBuildingAdapter(List<TaskAreaListBean> data) {
        super(R.layout.task_input_select_area_item_holder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskAreaListBean item) {
        helper.setText(R.id.tv_equipment_name, item.getName());
        View view = helper.getView(R.id.tv_equipment_name);
        if (item.isSelected()) {
            view.setSelected(true);
        } else {
            view.setSelected(false);
        }
//		view.setSelected(item.isSelected());
    }

}
