package com.tianchuang.ihome_b.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wujian on 2016/2/28.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> stringList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> stringList) {
        super(fm);
        mFragmentList = list;
        this.stringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
