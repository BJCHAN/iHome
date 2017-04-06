package com.tianchuang.ihome_b.base;

import android.content.Context;
import android.os.Bundle;
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
    private int errorPageId;
    private int emptyPageId;
    private int loadingPageId;


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
            initView(rootView, savedInstanceState);//先初始化View,再去加载数据
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
        loadingPageId = R.layout.base_loading_pager;
        errorPageId = R.layout.base_error_pager;
        emptyPageId = R.layout.base_empty_pager;
        setCustomLoadingPager();
        return new LoadingPager(mContext, loadingPageId, errorPageId, emptyPageId) {
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

    public void setErrorPageId(@LayoutRes int errorPageId) {
        this.errorPageId = errorPageId;
    }

    public void setEmptyPageId(@LayoutRes int emptyPageId) {
        this.emptyPageId = emptyPageId;
    }

    public void setLoadingPageId(@LayoutRes int loadingPageId) {
        this.loadingPageId = loadingPageId;
    }

    /**
     * 获取数据必须实现
     */
    protected abstract void initData();

    /**
     * 校验数据，自选调用
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

    public void showErrorPage() {
        loadingPager.showPagerView(LoadingPager.STATE_ERROR);
    }

    public void showEmptyPage() {
        loadingPager.showPagerView(LoadingPager.STATE_EMPTY);
    }

    public void showSucceedPage() {
        loadingPager.showPagerView(LoadingPager.STATE_SUCCEED);
    }
}
