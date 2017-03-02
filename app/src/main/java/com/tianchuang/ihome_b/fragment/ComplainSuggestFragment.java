package com.tianchuang.ihome_b.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.ComplainSuggestActivity;
import com.tianchuang.ihome_b.adapter.ViewPagerAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 投诉建议Fragment
 */
public class ComplainSuggestFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.tab_viewpager)
    ViewPager mTabViewpager;
    private ComplainSuggestActivity complainSuggestActivity;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    //未处理
    private int typeUntreated = 0;
    //已处理
    private int typeProcessed = 1;


    public static ComplainSuggestFragment newInstance() {
        return new ComplainSuggestFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_complain_suggest;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        complainSuggestActivity = (ComplainSuggestActivity) getHoldingActivity();
        complainSuggestActivity.setToolbarTitle("投诉建议");
    }

    @Override
    protected void initData() {
        super.initData();
        mFragmentList.add(ComplainSuggestListFragment.getIntance(typeUntreated));
        titleList.add(getString(R.string.untreated));
        mFragmentList.add(ComplainSuggestListFragment.getIntance(typeProcessed));
        titleList.add(getString(R.string.processed));
        mTabViewpager.setAdapter(new ViewPagerAdapter(complainSuggestActivity.getSupportFragmentManager(), mFragmentList, titleList));
        mTablayout.setupWithViewPager(mTabViewpager);
    }
}
