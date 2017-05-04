package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.BaseItemLoadBean;
import com.tianchuang.ihome_b.bean.BaseListLoadBean;
import com.tianchuang.ihome_b.bean.recyclerview.EmptyLoadMore;
import com.tianchuang.ihome_b.bean.recyclerview.PullToLoadMoreListener;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by Abyss on 2017/3/9.
 * 封装本项目的刷新加载更多Fragment
 * description:T 是itemBean,E是ListBean
 */

abstract class BaseRefreshAndLoadMoreFragment<T extends BaseItemLoadBean, E extends BaseListLoadBean> extends BaseLoadingFragment implements PullToLoadMoreListener.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    protected int pageSize;
    protected ArrayList<T> mData = new ArrayList<>();
    protected BaseQuickAdapter adapter;
    private boolean isLoadMoreLoading = false;//是否正在加载更多
    private boolean isOpenListAnim = true;//是否开启列表动画,默认开启

    public void setOpenListAnim(boolean openListAnim) {
        isOpenListAnim = openListAnim;
    }

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

    }

    @Override
    protected void initData() {
        getNetObservable(0)
                .compose(this.bindToLifecycle())
                .retry(2)
                .subscribe(new RxSubscribe<E>() {

                    @Override
                    public void _onNext(E bean) {
                        pageSize = bean.getPageSize();
                        ArrayList listVo = bean.getListVo();
//                        checkData(listVo);
                        if (listVo.size() > 0)
                            mData.addAll(listVo);
                        adapter = initAdapter(mData, bean);
                        setAdapter();
                        rvList.setAdapter(adapter);
                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        showErrorPage();
                    }

                    @Override
                    public void onComplete() {
                        showSucceedPage();
                    }
                });
    }

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
                .compose(this.bindToLifecycle())
                .subscribe(new RxSubscribe<E>() {
                    @Override
                    public void _onNext(E bean) {
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
                    public void _onError(String message) {
                        isLoadMoreLoading = false;
                        adapter.loadMoreFail();
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        getNetObservable(0)
                .compose(this.bindToLifecycle())
                .subscribe(new RxSubscribe<E>() {
                    @Override
                    public void _onNext(E bean) {
                        isLoadMoreLoading = false;
                        mData.clear();
                        mData.addAll(bean.getListVo());
                        adapter.setNewData(mData);
                        int size = bean.getListVo().size();
                        if (size < pageSize) {
                            setLoadMoreEnd(size);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);//刷新完成
                    }

                    @Override
                    public void _onError(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 设置adapter
     */
    private void setAdapter() {
        //添加空页面
        adapter.setEmptyView(ViewHelper.getEmptyView(getEmptyString()));
        if (isOpenListAnim) {
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        }
        adapter.setOnLoadMoreListener(new EmptyLoadMore());
        int size = mData.size();
        if (size < pageSize) {//加载的view Gone掉
            setLoadMoreEnd(size);
        }
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                onListitemClick(mData.get(position));
            }
        });

    }

    /**
     * 根据数据判断是否显示没有更多数据
     * */
    private void setLoadMoreEnd(int listSize) {
        if (listSize >= 6) {//显示
            adapter.loadMoreEnd(false);
        } else {//不显示
            adapter.loadMoreEnd(true);//加载的view Gone掉
        }
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
