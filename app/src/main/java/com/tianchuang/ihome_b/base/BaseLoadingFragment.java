package com.tianchuang.ihome_b.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.TianChuangApplication;
import com.tianchuang.ihome_b.view.LoadingPager;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/3/31.
 * description:抽取加载操作的fragment
 */

public abstract class BaseLoadingFragment extends BaseFragment {
    protected LoadingPager loadingPager;
    protected Context mContext = getActivity() == null ? TianChuangApplication.application : getActivity();
    private int errorPagerId;
    private int emptyPagerId;
    private int loadingPagerId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != loadingPager) {
            ViewGroup parent = (ViewGroup) loadingPager.getParent();
            if (null != parent) {
                parent.removeView(loadingPager);
            }
        } else {
            rootView = inflater.inflate(getLayoutId(), container, false);
            bind = ButterKnife.bind(this, rootView);
            initView(rootView, savedInstanceState);
            if (loadingPager == null) {
                loadingPager = getLoadingPager();
            }
        }
        //显示加载页面
        if (loadingPager != null)
            loadingPager.show();
        return loadingPager;
    }

    @NonNull
    private LoadingPager getLoadingPager() {
        loadingPagerId = R.layout.base_loading_pager;
        errorPagerId = R.layout.base_error_pager;
        emptyPagerId = R.layout.base_empty_pager;
        setCustomLoadingPager();
        return new LoadingPager(mContext, loadingPagerId, errorPagerId, emptyPagerId) {
            @Override
            protected View createSuccessView() {
                return rootView;
            }

            @Override
            protected void load() {
                initData();
                initListener();
            }
        };
    }

    /**
     * 设置自定义的布局,可选调用相应的方法
     */
    private void setCustomLoadingPager() {

    }

    public void setErrorPagerId(@LayoutRes int errorPagerId) {
        this.errorPagerId = errorPagerId;
    }

    public void setEmptyPagerId(@LayoutRes int emptyPagerId) {
        this.emptyPagerId = emptyPagerId;
    }

    public void setLoadingPagerId(@LayoutRes int loadingPagerId) {
        this.loadingPagerId = loadingPagerId;
    }

    /**
     * 必须在获取到数据后调用checkData方法
     */
    protected abstract void initData();

    /**
     * 校验数据，必须调用
     */
    protected void checkData(Object datas) {
//        if (datas == null) {
//            loadingPager.showPagerView(LoadingPager.STATE_ERROR);//  请求服务器失败
//        } else {
        try {
            @SuppressWarnings("unchecked") List<Object> ds = (List<Object>) datas;
            if (ds.size() == 0) {
                loadingPager.showPagerView(LoadingPager.STATE_EMPTY);
            } else {
                loadingPager.showPagerView(LoadingPager.STATE_SUCCEED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if ("".equals(datas)) {
                loadingPager.showPagerView(LoadingPager.STATE_EMPTY);
            } else {
                loadingPager.showPagerView(LoadingPager.STATE_SUCCEED);
            }
        }
//        }

    }

    protected void showErrorPage() {
        loadingPager.showPagerView(LoadingPager.STATE_ERROR);
    }

    protected void showEmptyPage() {
        loadingPager.showPagerView(LoadingPager.STATE_EMPTY);
    }

    protected void showSucceedPage() {
        loadingPager.showPagerView(LoadingPager.STATE_SUCCEED);
    }
}
