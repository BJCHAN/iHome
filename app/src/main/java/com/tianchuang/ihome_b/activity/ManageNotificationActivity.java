package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.fragment.NotificationListFragment;

/**
 * Created by Abyss on 2017/3/15.
 * description:管理通知页面
 */
public class ManageNotificationActivity extends ToolBarActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return NotificationListFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        initNormalToolbar(toolbar, true);
    }
}
