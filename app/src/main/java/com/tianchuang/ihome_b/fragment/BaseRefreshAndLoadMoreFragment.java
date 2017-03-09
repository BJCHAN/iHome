package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.BaseItemLoadBean;
import com.tianchuang.ihome_b.bean.BaseListLoadBean;
import com.tianchuang.ihome_b.bean.PullToLoadMoreListener;
import com.tianchuang.ihome_b.bean.recyclerview.EmptyLoadMore;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/3/9.
 * 封装本项目的刷新加载更多Fragment
 * description:T 是itemBean,E是ListBean
 */

abstract class BaseRefreshAndLoadMoreFragment<T extends BaseItemLoadBean, E extends BaseListLoadBean> extends BaseFragment implements PullToLoadMoreListener.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    protected int pageSize;
    protected ArrayList<T> mData;
    protected BaseQuickAdapter adapter;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.base_fragment_refrsh_load;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        handleBundle();
        rvList.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.refresh_scheme_color));
        rvList.addOnScrollListener(new PullToLoadMoreListener(mSwipeRefreshLayout, this));
        getNetObservable(0)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .compose(this.<E>bindToLifecycle())
                .subscribe(new RxSubscribe<E>() {

                    @Override
                    protected void _onNext(E bean) {
                        pageSize = bean.getPageSize();
                        mData = bean.getListVo();
                        adapter = initAdapter(mData, bean);
                        setAdapter(adapter);
                        rvList.setAdapter(adapter);
                        dismissProgress();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }


    private boolean isLoadMoreLoading = false;//是否正在加载更多

    /**
     * 加载更多
     */
    @Override
    public void requestLoadMore() {
        int size = mData.size();
        if (isLoadMoreLoading || size == 0) {
            return;
        }
        mSwipeRefreshLayout.setEnabled(false);
        isLoadMoreLoading = true;
        getNetObservable(mData.get(size - 1).getId())
                .compose(this.<E>bindToLifecycle())
                .subscribe(new RxSubscribe<E>() {
                    @Override
                    protected void _onNext(E bean) {
                        adapter.addData(bean.getListVo());
                        if (bean.getListVo().size() < pageSize) {//没有更多数据
                            adapter.loadMoreEnd(false);
                            isLoadMoreLoading = true;
                        } else {
                            adapter.loadMoreComplete();//加载完成
                            isLoadMoreLoading = false;
                        }
                        mSwipeRefreshLayout.setEnabled(true);
                    }

                    @Override
                    protected void _onError(String message) {
                        isLoadMoreLoading = false;
                        adapter.loadMoreFail();
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }


    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        getNetObservable(0)
                .compose(this.<E>bindToLifecycle())
                .subscribe(new RxSubscribe<E>() {
                    @Override
                    protected void _onNext(E bean) {
                        isLoadMoreLoading = false;
                        mData.clear();
                        mData.addAll(bean.getListVo());
                        adapter.setNewData(mData);
                        if (bean.getListVo().size() < pageSize) {//加载的view Gone掉
                            adapter.loadMoreEnd(true);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);//刷新完成
                    }

                    @Override
                    protected void _onError(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }


    /**
     * 设置adapter
     */
    private void setAdapter(final BaseQuickAdapter adapter) {
        //添加空页面
        adapter.setEmptyView(ViewHelper.getEmptyView(getEmptyString()));
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setOnLoadMoreListener(new EmptyLoadMore());
        if (mData.size() < pageSize) {//加载的view Gone掉
            adapter.loadMoreEnd(true);
        }
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                onListitemClick(mData.get(position));
            }
        });

    }

    /**
     * 处理上个页面传递来的数据
     */
    protected void handleBundle() {

    }

    /**
     * 初始化adapter
     */
    protected abstract BaseQuickAdapter initAdapter(ArrayList<T> mData, E listBean);

    /**
     * item的点击事件
     */
    protected abstract void onListitemClick(T itemBean);

    /**
     * 访问网络请求数据
     */
    protected abstract Observable<E> getNetObservable(int maxId);

    /**
     * 空页面的字符串
     */
    protected abstract String getEmptyString();
}
