package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.event.RobOrderSuccessEvent;
import com.tianchuang.ihome_b.fragment.RobHallFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Abyss on 2017/220.
 * description:抢单大厅模块
 */
public class RobHallActivity extends ToolBarActivity {

	@Override
	protected BaseFragment getFirstFragment() {
		return RobHallFragment.newInstance();
	}



	@Override
	protected void initToolBar(Toolbar toolbar) {
		initNormalToolbar(toolbar,true);
		setToolbarTitle("抢单大厅");
	}

}
