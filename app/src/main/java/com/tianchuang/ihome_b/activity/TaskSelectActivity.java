package com.tianchuang.ihome_b.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.TaskSelectFragment;

/**
 * 控制点型任务选择页面
 */
public class TaskSelectActivity extends ToolBarActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return TaskSelectFragment.newInstance();
    }


    @Override
    protected void initToolBar(Toolbar toolbar) {
        setToolbarTitle("请选择任务");
        initNormalToolbar(toolbar, true);
    }
}
