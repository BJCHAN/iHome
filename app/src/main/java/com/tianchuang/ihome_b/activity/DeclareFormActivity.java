package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.FormTypeListFragment;

/**
 * Created by Abyss on 2017/3/10.
 * description:
 */

public class DeclareFormActivity extends ToolBarActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return FormTypeListFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        setToolbarTitle("表单申报");
        initNormalToolbar(toolbar, false);
    }
}
