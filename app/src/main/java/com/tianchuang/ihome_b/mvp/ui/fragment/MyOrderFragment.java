package com.tianchuang.ihome_b.mvp.ui.fragment;


import android.support.v4.app.Fragment;

import com.tianchuang.ihome_b.R;

import java.util.List;

/**
 * 我的订单Fragment
 */
public class MyOrderFragment extends BaseTogglePagerFragment {

    public static MyOrderFragment newInstance() {
        return new MyOrderFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("我的订单");
    }

    @Override
    protected void addToggleFragment(List<Fragment> mFragmentList, List<String> titleList) {
        mFragmentList.add(MyOrderStatusFragment.newInstance(MyOrderStatusFragment.UNDER_WAY));
        titleList.add(getString(R.string.underway));//进行中
        mFragmentList.add(MyOrderStatusFragment.newInstance(MyOrderStatusFragment.FINISHED));
        titleList.add(getString(R.string.finished));//已完成
    }
}
