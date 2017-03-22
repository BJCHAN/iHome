package com.tianchuang.ihome_b.activity;

import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.fragment.NotificationDetailFragment;
import com.tianchuang.ihome_b.fragment.NotificationListFragment;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/15.
 * description:管理通知页面
 */
public class ManageNotificationActivity extends ToolBarActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        if (getIntent() != null) {//从主页过来
            Serializable item = getIntent().getSerializableExtra("item");
            if (item != null && item instanceof HomePageMultiItem) {
                NotificationItemBean notificationItemBean = ((HomePageMultiItem) item).getNotificationItemBean();
                return NotificationDetailFragment.newInstance(notificationItemBean.getId());
            }
        }
        return NotificationListFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        initNormalToolbar(toolbar, true);
    }
}
