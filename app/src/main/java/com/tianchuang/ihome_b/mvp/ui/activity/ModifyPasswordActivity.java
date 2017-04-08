package com.tianchuang.ihome_b.mvp.ui.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.mvp.ui.fragment.ModifyPasswordFragment;

/**
 * Created by Abyss on 2017/2/13.
 * description:修改密码模块
 */
public class ModifyPasswordActivity extends ToolBarActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return ModifyPasswordFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        setFinishWithAnim(true);
        setToolbarTitle("修改密码");
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(v -> removeFragment());
    }

    public int getContainer() {
        return getFragmentContainerId();
    }
}
