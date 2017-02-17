package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.DrawMenuAdapter;
import com.tianchuang.ihome_b.base.BaseActivity;
import com.tianchuang.ihome_b.bean.DrawMenuItem;
import com.tianchuang.ihome_b.bean.DrawMenuItemDecoration;
import com.tianchuang.ihome_b.bean.event.LogoutEvent;
import com.tianchuang.ihome_b.fragment.MainFragment;
import com.tianchuang.ihome_b.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/9.
 * description:主页
 */
public class MainActivity extends BaseActivity {

	@BindView(R.id.id_draw_menu_item_list_select)
	RecyclerView mRecyclerView;
	@BindView(R.id.id_drawer_layout)
	DrawerLayout mDrawerLayout;
	@BindArray(R.array.draw_menu_items)
	String[] items;
	@BindView(R.id.spinner)
	MaterialSpinner spinner;
	private DrawMenuAdapter menuAdapter;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_main2;
	}

	@Override
	protected int getFragmentContainerId() {
		return R.id.main_fragment_container;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		ButterKnife.bind(this);
		EventBus.getDefault().register(this);
		addFragment(MainFragment.newInstance());//添加主页fragment
		initView();
		//设置监听
		initListener();
	}

	private void initView() {
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.addItemDecoration(new DrawMenuItemDecoration(20));
		ArrayList<DrawMenuItem> drawMenuItems = new ArrayList<>();
		for (String item : items) {
			DrawMenuItem drawMenuItem = new DrawMenuItem();
			drawMenuItem.setName(item);
			drawMenuItems.add(drawMenuItem);
		}
		menuAdapter = new DrawMenuAdapter(R.layout.draw_menu_item_holder, drawMenuItems);
		mRecyclerView.setAdapter(menuAdapter);
		String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
		spinner.setItems(items);
		spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

			@Override
			public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

			}

		});
	}

	@OnClick({R.id.iv_navigation_icon, R.id.iv_right})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_navigation_icon://侧滑菜单的入口
				toggle();
				break;
			case R.id.iv_right://抢单大厅的入口
				Toast.makeText(this, "抢单大厅", Toast.LENGTH_SHORT).show();
				break;
		}
	}


	private void initListener() {
		menuAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int i) {
				switch (i) {
					case 8:
						startActivityWithAnim(new Intent(MainActivity.this, SettingActivity.class));
						break;
				}
			}
		});
		mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

			/**
			 * @param drawerView
			 * @param slideOffset   偏移(0-1)
			 */
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				// 判断是否左菜单并设置移动(如果不这样设置,则主页面的内容不会向右移动)
				if (drawerView.getTag().equals("left")) {
					View content = mDrawerLayout.getChildAt(0);
					int offset = (int) (drawerView.getWidth() * slideOffset);
					content.setTranslationX(offset);
				}
			}

			@Override
			public void onDrawerOpened(View drawerView) {
			}

			@Override
			public void onDrawerClosed(View drawerView) {
			}

			@Override
			public void onDrawerStateChanged(int newState) {

			}
		});
	}


	/**
	 * 自定义NavigationIcon设置关联DrawerLayout
	 */
	private void toggle() {
		int drawerLockMode = mDrawerLayout.getDrawerLockMode(GravityCompat.START);
		if (mDrawerLayout.isDrawerVisible(GravityCompat.START)
				&& (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
			mDrawerLayout.closeDrawer(GravityCompat.START);
		} else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
			mDrawerLayout.openDrawer(GravityCompat.START);
		}
	}

	//返回键返回事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
				int drawerLockMode = mDrawerLayout.getDrawerLockMode(GravityCompat.START);
				if (mDrawerLayout.isDrawerVisible(GravityCompat.START)
						&& (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {//菜单未关闭，先关闭菜单
					mDrawerLayout.closeDrawer(GravityCompat.START);
				} else {//关闭页面
					finish();
				}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(LogoutEvent event) {
		finish();
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
