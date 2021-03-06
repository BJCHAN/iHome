package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.DetailMultiAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ControlPointItemBean;
import com.tianchuang.ihome_b.bean.MyFormDetailBean;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ViewHelper;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/24.
 * description:控制点执行结果
 */

public class TaskControlPointResultFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    public static TaskControlPointResultFragment newInstance(TaskPointDetailBean.FormTypeVoListBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        TaskControlPointResultFragment fragment = new TaskControlPointResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("表单详情");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TaskPointDetailBean.FormTypeVoListBean bean = (TaskPointDetailBean.FormTypeVoListBean) getArguments().getSerializable("bean");
        if (bean != null) {
            MyFormDetailBean formDetailVo = bean.getFormDetailVo();
            //添加头部
//            View header = LayoutUtil.inflate(R.layout.control_point_header);
//            TextView tvPointAddress = (TextView) header.findViewById(R.id.tv_point_address);
//            TextView tvPointDate = (TextView) header.findViewById(R.id.tv_point_date);
//            TextView tvPointName = (TextView) header.findViewById(R.id.tv_point_name);
            rvList.setLayoutManager(new LinearLayoutManager(getContext()));
            DetailMultiAdapter adapter = new DetailMultiAdapter(formDetailVo.getFormDataVos());
//            tvPointName.setText(StringUtils.getNotNull(formDetailVo.getTypeName()));
//            tvPointAddress.setText(StringUtils.getNotNull(formDetailVo.get));
//            tvPointDate.setText(StringUtils.getNotNull(bean.getTime()));
            adapter.addHeaderView(ViewHelper.getFormSubmitHeaderView(formDetailVo.getTypeName()));
            rvList.setAdapter(adapter);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_point_detail;
    }

}
