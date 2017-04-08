package com.tianchuang.ihome_b.mvp.ui.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.mvp.ui.fragment.PersonalInfoFragment;

public class PersonalInfoActivity extends ToolBarActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return PersonalInfoFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        setToolbarTitle("个人信息");
        initNormalToolbar(toolbar, true);
    }
}
