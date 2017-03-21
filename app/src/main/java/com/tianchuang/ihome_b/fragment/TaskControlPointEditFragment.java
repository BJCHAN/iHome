package com.tianchuang.ihome_b.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.adapter.TaskSubmitMultiAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.CustomLinearLayoutManager;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import java.util.List;
import java.util.concurrent.RunnableFuture;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/20.
 * description:控制点的编辑提交
 */

public class TaskControlPointEditFragment extends BaseFragment implements TaskSubmitMultiAdapter.SaveEditListener, View.OnClickListener {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private TaskSubmitMultiAdapter submitMultiAdapter;
    private List<FormTypeItemBean.FieldsBean> fields;
    private SparseArray<Object> editTexts;
    private MyTaskActivity holdingActivity;

    public static TaskControlPointEditFragment newInstance(FormTypeItemBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        TaskControlPointEditFragment fragment = new TaskControlPointEditFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_control_point_edit;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        holdingActivity = ((MyTaskActivity) getHoldingActivity());
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        FormTypeItemBean formTypeItemBean = (FormTypeItemBean) getArguments().getSerializable("bean");
        if (formTypeItemBean != null) {
            initView(formTypeItemBean);
        }

    }

    private void initView(FormTypeItemBean formTypeItemBean) {
        mRecyclerView.addItemDecoration(new CommonItemDecoration(DensityUtil.dip2px(getContext(), 20)));
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
            CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(getContext());
            customLinearLayoutManager.setScrollEnabled(false);
            mRecyclerView.setLayoutManager(customLinearLayoutManager);
        }
    }


    @Override
    public void SaveEdit(int position, String string) {

    }

    /**
     * 提交的按钮
     */
    @Override
    public void onClick(View v) {

    }
}
