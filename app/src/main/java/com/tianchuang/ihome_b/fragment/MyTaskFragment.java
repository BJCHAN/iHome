package com.tianchuang.ihome_b.fragment;

import android.support.v4.app.Fragment;

import com.tianchuang.ihome_b.R;

import java.util.List;

/**
 * Created by Abyss on 2017/3/9.
 * description:我的任务
 */

public class MyTaskFragment extends BaseTogglePagerFragment {

    public static MyTaskFragment newInstance() {
        return new MyTaskFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("我的任务");
    }


    @Override
    protected void addToggleFragment(List<Fragment> mFragmentList, List<String> titleList) {
        mFragmentList.add(MyTaskUnderWayFragment.newInstance());
        titleList.add(getString(R.string.underway));//进行中
        mFragmentList.add(MyTaskUnderWayFragment.newInstance());
        titleList.add(getString(R.string.finished));//已完成
    }
}
