package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

/**
 * Created by abyss on 2017/3/23.
 */

public class TaskSelectFragment extends BaseFragment {
    public static TaskSelectFragment newInstance() {
        return new TaskSelectFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_select;
    }
}
