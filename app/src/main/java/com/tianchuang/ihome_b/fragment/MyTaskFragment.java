package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.adapter.ViewPagerAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/9.
 * description:我的任务
 */

public class MyTaskFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.tab_viewpager)
    ViewPager mTabViewpager;
    private MyTaskActivity holdingActivity;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public static MyTaskFragment newInstance() {
        return new MyTaskFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
      holdingActivity.setToolbarTitle("我的任务");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        holdingActivity = ((MyTaskActivity) getHoldingActivity());
    }

    @Override
    protected void initData() {
        mFragmentList.add(MyOrderStatusFragment.newInstance(MyOrderStatusFragment.UNDER_WAY));
        titleList.add(getString(R.string.underway));//进行中
        mFragmentList.add(MyOrderStatusFragment.newInstance(MyOrderStatusFragment.FINISHED));
        titleList.add(getString(R.string.finished));//已完成
        mTabViewpager.setAdapter(new ViewPagerAdapter(holdingActivity.getSupportFragmentManager(), mFragmentList, titleList));
        mTablayout.setupWithViewPager(mTabViewpager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_task;
    }
}
