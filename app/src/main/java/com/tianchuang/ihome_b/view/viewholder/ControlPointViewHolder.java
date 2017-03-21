package com.tianchuang.ihome_b.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.adapter.TaskSubmitMultiAdapter;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.CustomLinearLayoutManager;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControlPointViewHolder extends BaseHolder<FormTypeItemBean> implements TaskSubmitMultiAdapter.SaveEditListener, View.OnClickListener {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private TaskSubmitMultiAdapter submitMultiAdapter;
    private List<FormTypeItemBean.FieldsBean> fields;
    private SparseArray<Object> editTexts;
    private MyTaskActivity holdingActivity;

    @Override
    public View initHolderView() {
        View view = LayoutUtil.inflate(R.layout.fragment_task_control_point_edit);
        ButterKnife.bind(this, view);
        return view;
    }

    public ControlPointViewHolder(MyTaskActivity holdingActivity) {
        this.holdingActivity = holdingActivity;
    }

    @Override
    public void bindData(FormTypeItemBean formTypeItemBean) {
        mRecyclerView.addItemDecoration(new CommonItemDecoration(DensityUtil.dip2px(holdingActivity, 20)));
        fields = formTypeItemBean.getFields();
        if (fields.size() > 0) {
            editTexts = new SparseArray<>();
            submitMultiAdapter = new TaskSubmitMultiAdapter(holdingActivity, fields);
            submitMultiAdapter.setSaveEditListener(this);
            //添加腿部
            View view = LayoutUtil.inflate(R.layout.form_submit_footer_holder);
            view.findViewById(R.id.tv_submit).setOnClickListener(this);
            submitMultiAdapter.addFooterView(view);
            mRecyclerView.setAdapter(submitMultiAdapter);
            CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(holdingActivity);
            customLinearLayoutManager.setScrollEnabled(false);
            mRecyclerView.setLayoutManager(customLinearLayoutManager);
        }
    }

    @Override
    public void SaveEdit(int position, String string) {

    }

    @Override
    public void onClick(View v) {

    }
}
