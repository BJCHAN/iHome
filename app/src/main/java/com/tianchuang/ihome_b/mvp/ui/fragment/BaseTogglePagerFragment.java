package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.ViewPagerAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/15.
 * description:封装本项目双页面切换的基类
 */

abstract class BaseTogglePagerFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.tab_viewpager)
    ViewPager mTabViewpager;
    protected List<Fragment> mFragmentList = new ArrayList<>();
    protected List<String> titleList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.base_toggle_pager;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {
        super.initData();
        addToggleFragment(mFragmentList, titleList);
        mTabViewpager.setAdapter(new ViewPagerAdapter(getHoldingActivity().getSupportFragmentManager(), mFragmentList, titleList));
        mTablayout.setupWithViewPager(mTabViewpager);
    }

    protected abstract void addToggleFragment(List<Fragment> mFragmentList, List<String> titleList);
}
