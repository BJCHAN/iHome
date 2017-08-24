package com.tianchuang.ihome_b.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.bean.TaskInputDetailBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tianchuang.ihome_b.utils.StringUtils.getNotNull;

/**
 * Created by Abyss on 2017/2/20.
 * description:任务列表的头部
 */

public class TaskInputHeaderViewHolder extends BaseHolder<TaskInputDetailBean> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_finish_date)
    TextView tvFinishDate;

    @Override
    public View initHolderView() {
        View view = LayoutUtil.inflate(R.layout.task_input_detail_header);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(TaskInputDetailBean data) {
        tvTitle.setText(getNotNull(data.getTaskName()));
        tvDate.setText(getNotNull(DateUtils.formatDate(data.getCreatedDate(), DateUtils.TYPE_01)));
        tvContent.setText(getNotNull(data.getTaskExplains()));
        tvType.setText(getNotNull(data.getEnterTypeMsg()));
        tvAddress.setText(getNotNull(data.getBuildingDetail()));
        tvFinishDate.setText(getNotNull(DateUtils.formatDate(data.getFinishTime(), DateUtils.TYPE_01)));
    }

}
