package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.MyFormListFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFormActivity extends ToolBarActivity {



    @Override
    protected BaseFragment getFirstFragment() {
        return MyFormListFragment.newInstance();
    }


    @Override
    protected void initToolBar(Toolbar toolbar) {
        setToolbarTitle("我的表单");
        initNormalToolbar(toolbar, true);
    }


}
