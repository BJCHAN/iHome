package com.tianchuang.ihome_b.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.bean.DetailHeaderBean;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:详情的头部
 */

public class FormSubmitHeaderViewHolder extends BaseHolder<String> {


    @BindView(R.id.tv_form_type)
    TextView tvFormType;

    @Override
    public View initHolderView() {
        View view = LayoutUtil.inflate(R.layout.form_submit_header_holder);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(String data) {
        tvFormType.setText(StringUtils.getNotNull(data));
    }
}
