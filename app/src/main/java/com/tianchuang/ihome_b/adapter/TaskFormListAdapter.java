package com.tianchuang.ihome_b.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;


/**
 * Created by Abyss on 2017/3/16.
 * description:任务表单的adapter
 */

public class TaskFormListAdapter extends BaseQuickAdapter<TaskPointDetailBean.FormTypeVoListBean, BaseViewHolder> {

    public TaskFormListAdapter(List<TaskPointDetailBean.FormTypeVoListBean> data) {
        super(R.layout.task_form_item_holder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TaskPointDetailBean.FormTypeVoListBean item) {
        TextView date = helper.getView(R.id.tv_point_date);
        ImageView point = helper.getView(R.id.iv_little_point);
        if (item.isDone()) {
            date.setText(StringUtils.getNotNull("已完成"));
            Drawable img = ContextCompat.getDrawable(date.getContext(), R.mipmap.menu_arrow);
            // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            date.setCompoundDrawables(null, null, img, null); //设置右图标
            date.setCompoundDrawablePadding(DensityUtil.dip2px(date.getContext(), 5f));
            date.setTextColor(ContextCompat.getColor(date.getContext(), R.color.app_primary_color));
            point.setImageDrawable(ContextCompat.getDrawable(point.getContext(), R.drawable.bulepoint_icon));
        } else {
            date.setCompoundDrawablePadding(DensityUtil.dip2px(date.getContext(), 0f));
            date.setCompoundDrawables(null, null, null, null); //设置右图标
            point.setImageDrawable(ContextCompat.getDrawable(point.getContext(), R.drawable.little_red));
            date.setText(StringUtils.getNotNull("未完成"));
            date.setTextColor(ContextCompat.getColor(date.getContext(), R.color.TC_1));
        }

        if (helper.getLayoutPosition() == getItemCount() - 1) {//最后一个
            helper.getView(R.id.v_line).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.v_line).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.tv_point_name, StringUtils.getNotNull(item.getFormTypeName()));
    }


}
