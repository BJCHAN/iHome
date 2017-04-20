package com.tianchuang.ihome_b.mvp.ui.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.mvp.ui.fragment.MyTaskControlPointDetailFragment;
/**
 * 任务详情activity
 * */
public class ControlPointDetailActivity extends TaskSelectActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        TaskPointDetailBean detailBean = (TaskPointDetailBean) getIntent().getSerializableExtra("detailBean");
            return MyTaskControlPointDetailFragment.newInstance(detailBean);

    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        initNormalToolbar(toolbar, true);
    }
}
