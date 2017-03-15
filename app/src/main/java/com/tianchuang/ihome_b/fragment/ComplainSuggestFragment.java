package com.tianchuang.ihome_b.fragment;


import android.support.v4.app.Fragment;

import com.tianchuang.ihome_b.R;

import java.util.List;

/**
 * 投诉建议Fragment
 */
public class ComplainSuggestFragment extends BaseTogglePagerFragment {

    public static BaseTogglePagerFragment newInstance() {
        return new ComplainSuggestFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("投诉建议");
    }

    @Override
    protected void addToggleFragment(List<Fragment> mFragmentList, List<String> titleList) {
        mFragmentList.add(ComplainSuggestListFragment.getIntance(ComplainSuggestListFragment.TYPE_UNTREATED));
        titleList.add(getString(R.string.untreated));
        mFragmentList.add(ComplainSuggestListFragment.getIntance(ComplainSuggestListFragment.TYPE_PROCESSED));
        titleList.add(getString(R.string.processed));
    }
}
