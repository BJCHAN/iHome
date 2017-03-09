package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.MyTaskFragment;
/**
 * Created by Abyss on 2017/3/9.
 * description:我的任务模块
 */
public class MyTaskActivity extends ToolBarActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return MyTaskFragment.newInstance();
    }


    @Override
    protected void initToolBar(Toolbar toolbar) {
        initNormalToolbar(toolbar, true);
    }
}
