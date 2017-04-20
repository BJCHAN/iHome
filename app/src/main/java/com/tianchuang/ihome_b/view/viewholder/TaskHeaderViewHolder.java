package com.tianchuang.ihome_b.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:任务列表的头部
 */

public class TaskHeaderViewHolder extends BaseHolder<String> {


    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    public View initHolderView() {
        View view = LayoutUtil.inflate(R.layout.task_header_holder);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(String data) {
        tvName.setText(StringUtils.getNotNull(data));
    }
}
