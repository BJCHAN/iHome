package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.ComplainSuggestFragment;

public class ComplainSuggestActivity extends ToolBarActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return ComplainSuggestFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        initNormalToolbar(toolbar, true);
        setToolbarTitle("投诉建议");
    }
}
