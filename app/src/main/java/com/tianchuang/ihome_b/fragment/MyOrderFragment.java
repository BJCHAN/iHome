package com.tianchuang.ihome_b.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyOrderActivity;
import com.tianchuang.ihome_b.adapter.ViewPagerAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的订单Fragment
 */
public class MyOrderFragment extends BaseFragment {
	@BindView(R.id.tablayout)
	TabLayout mTablayout;
	@BindView(R.id.tab_viewpager)
	ViewPager mTabViewpager;
	private MyOrderActivity holdingActivity;
	private List<Fragment> mFragmentList = new ArrayList<>();
	private List<String> titleList = new ArrayList<>();


	public static MyOrderFragment newInstance() {
		return new MyOrderFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_my_order;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = (MyOrderActivity) getHoldingActivity();
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("我的订单");
	}

	@Override
	protected void initData() {
		super.initData();
		mFragmentList.add(MyOrderStatusFragment.newInstance(1));
		titleList.add(getString(R.string.underway));//进行中
		mFragmentList.add(MyOrderStatusFragment.newInstance(2));
		titleList.add(getString(R.string.finished));//已完成
		mTabViewpager.setAdapter(new ViewPagerAdapter(holdingActivity.getSupportFragmentManager(), mFragmentList, titleList));
		mTablayout.setupWithViewPager(mTabViewpager);
	}
}
