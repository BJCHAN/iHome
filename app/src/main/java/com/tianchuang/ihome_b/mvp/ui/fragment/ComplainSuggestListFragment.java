package com.tianchuang.ihome_b.mvp.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.ComplainSuggestListAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.ComplainSuggestListItem;
import com.tianchuang.ihome_b.bean.ComplainSuggestProcessedBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestUntratedBean;
import com.tianchuang.ihome_b.bean.model.ComplainSuggestModel;
import com.tianchuang.ihome_b.bean.recyclerview.EmptyLoadMore;
import com.tianchuang.ihome_b.bean.recyclerview.PullToLoadMoreListener;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 投诉建议列表
 */
public class ComplainSuggestListFragment extends BaseLoadingFragment implements SwipeRefreshLayout.OnRefreshListener, PullToLoadMoreListener.OnLoadMoreListener {
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    //未处理
    public static int TYPE_UNTREATED = 0;
    //已处理
    public static int TYPE_PROCESSED = 1;
    private int type;
    private List<ComplainSuggestListItem> mComplainSuggestListItemList = new ArrayList<>();
    private List<ComplainSuggestListItem> mComplainSuggestListItemListTemp;
    private ComplainSuggestListAdapter mComplainSuggestListAdapter;
    private int untratedMaxid = 0;
    private int processedMaxid = 0;
    private ComplainSuggestListItem mComplainSuggestListItem;

    private int untratedSize;
    private int processedSize;

    //是否可以加载更多
    private boolean untratedCanLoadMore = true;
    private boolean processedCanLoadMore = true;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loadComplete;


    public static ComplainSuggestListFragment getIntance(int type) {
        ComplainSuggestListFragment complainSuggestListFragment = new ComplainSuggestListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        complainSuggestListFragment.setArguments(bundle);
        return complainSuggestListFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.base_fragment_refrsh_load;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null != arguments) {
            type = arguments.getInt("type");
        }
        //设置recycleview为垂直方向
        mLinearLayoutManager = new LinearLayoutManager(getHoldingActivity());
        mRvList.setLayoutManager(mLinearLayoutManager);
        //设置recycleview滚动监听
        mRvList.addOnScrollListener(new PullToLoadMoreListener(mSwipeRefreshLayout, this));
        //设置刷新加载进度条颜色
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.refresh_scheme_color));
        //设置下拉刷新监听
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mComplainSuggestListItemList.clear();
        mComplainSuggestListAdapter = new ComplainSuggestListAdapter(mComplainSuggestListItemList);
        mComplainSuggestListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mComplainSuggestListAdapter.setOnLoadMoreListener(new EmptyLoadMore());
        mRvList.setAdapter(mComplainSuggestListAdapter);

    }

    @Override
    protected void initData() {
//        showProgress();
        //未处理
        if (type == TYPE_UNTREATED) {
            mComplainSuggestListAdapter.setEmptyView(ViewHelper.getEmptyView(getString(R.string.complain_suggest_untreated_empty)));
            getComplainSuggestUntratedBean();
        }
        //已处理
        else {
            mComplainSuggestListAdapter.setEmptyView(ViewHelper.getEmptyView(getString(R.string.complain_suggest_processed_empty)));
            getComplainSuggestProcessedBean();
        }
        mComplainSuggestListAdapter.getEmptyView().setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        super.initListener();

        //条目的点击事件
        mRvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                int itemViewType = adapter.getItemViewType(position);
                if (itemViewType == ComplainSuggestListItem.TYPE_PROCESSED) {
                    ComplainSuggestListItem item = (ComplainSuggestListItem) adapter.getItem(position);
                    addFragment(ComplainDetailFragment.newInstance(item.getProcessedListVoBean().getId()));//跳转详情页面
                } else {
                    ComplainSuggestListItem item = (ComplainSuggestListItem) adapter.getItem(position);
                    addFragment(ComplainDetailFragment.newInstance(item.getUntratedListVoBean().getId()));
                }
            }
        });
    }
    private void setLoadMoreEnd(BaseQuickAdapter adapter,int listSize) {
        if (listSize >= 6) {
            adapter.loadMoreEnd(false);
        } else {
            adapter.loadMoreEnd(true);
        }
    }
    private void getComplainSuggestUntratedBean() {
        getComplainSuggestUntratedBean(untratedMaxid)
                .doOnSubscribe(o -> {
                        loadComplete = false;
                    }
                )
                .compose(this.<ComplainSuggestUntratedBean>bindToLifecycle()) //保证加载过程中，退出本页面造成的一系列空指针
                .subscribe(new RxSubscribe<ComplainSuggestUntratedBean>() {
                    @Override
                    public void _onNext(ComplainSuggestUntratedBean complainSuggestUntratedBean) {
                        if (null != complainSuggestUntratedBean) {
                            untratedSize = complainSuggestUntratedBean.getPageSize();
                            List<ComplainSuggestUntratedBean.ListVoBean> listVo = complainSuggestUntratedBean.getListVo();
                            int listVoSize = listVo.size();
                            mComplainSuggestListItemListTemp = new ArrayList<>();
                            for (int i = 0; i < listVoSize; i++) {
                                mComplainSuggestListItem = new ComplainSuggestListItem();
                                mComplainSuggestListItem.setType(ComplainSuggestListItem.TYPE_UNTRATED);
                                mComplainSuggestListItem.setUntratedListVoBean(listVo.get(i));
                                mComplainSuggestListItemListTemp.add(mComplainSuggestListItem);
                            }
                            if (untratedMaxid == 0) {
                                mComplainSuggestListItemList.addAll(mComplainSuggestListItemListTemp);
                                mComplainSuggestListAdapter.setNewData(mComplainSuggestListItemList);
                            } else {
                                mComplainSuggestListAdapter.addData(mComplainSuggestListItemListTemp);
                            }
                            if (listVoSize < untratedSize) {
                                untratedCanLoadMore = false;
//                                mComplainSuggestListAdapter.loadMoreEnd(false);
                                setLoadMoreEnd(mComplainSuggestListAdapter,mComplainSuggestListItemList.size());
                            }
                            if (mComplainSuggestListItemList.size() <= 0) {
                                mComplainSuggestListAdapter.getEmptyView().setVisibility(View.VISIBLE);
                            } else {
                                mComplainSuggestListAdapter.getEmptyView().setVisibility(View.GONE);
                            }
                        }
                        loadComplete = true;
                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getActivity(), message);
//                        dismissProgress();
                        showErrorPage();
                        mSwipeRefreshLayout.setRefreshing(false);//刷新完成
                        loadComplete = true;
                    }

                    @Override
                    public void onComplete() {
//                        dismissProgress();
                        if (untratedMaxid == 0) {
                            showSucceedPage();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);//刷新完成
                    }
                });
    }

    private void getComplainSuggestProcessedBean() {
        getComplainSuggestProcessedBean(processedMaxid)
                .doOnSubscribe(o ->{
                        loadComplete = false;
                        //processedMaxid == 0为第一次加载
                        if (processedMaxid == 0) {
                            mComplainSuggestListItemList.clear();
                        }
                    }
                )
                .compose(this.<ComplainSuggestProcessedBean>bindToLifecycle()) //保证加载过程中，退出本页面造成的一系列空指针
                .subscribe(new RxSubscribe<ComplainSuggestProcessedBean>() {
                    @Override
                    public void _onNext(ComplainSuggestProcessedBean complainSuggestProcessedBean) {
                        if (null != complainSuggestProcessedBean) {
                            processedSize = complainSuggestProcessedBean.getPageSize();
                            List<ComplainSuggestProcessedBean.ListVoBean> listVo = complainSuggestProcessedBean.getListVo();
                            int listVoSize = listVo.size();
                            mComplainSuggestListItemListTemp = new ArrayList<>();
                            for (int i = 0; i < listVoSize; i++) {
                                mComplainSuggestListItem = new ComplainSuggestListItem();
                                mComplainSuggestListItem.setType(ComplainSuggestListItem.TYPE_PROCESSED);
                                mComplainSuggestListItem.setProcessedListVoBean(listVo.get(i));
                                mComplainSuggestListItemListTemp.add(mComplainSuggestListItem);
                            }
                            if (processedMaxid == 0) {
                                mComplainSuggestListItemList.addAll(mComplainSuggestListItemListTemp);
                                mComplainSuggestListAdapter.setNewData(mComplainSuggestListItemList);
                            } else {
                                mComplainSuggestListAdapter.addData(mComplainSuggestListItemListTemp);
                            }
                            if (listVoSize < processedSize) {
                                processedCanLoadMore = false;
//                                mComplainSuggestListAdapter.loadMoreEnd(false);
                                setLoadMoreEnd(mComplainSuggestListAdapter,mComplainSuggestListItemList.size());
                            }
                            if (mComplainSuggestListItemList.size() <= 0) {
                                mComplainSuggestListAdapter.getEmptyView().setVisibility(View.VISIBLE);
                            } else {
                                mComplainSuggestListAdapter.getEmptyView().setVisibility(View.GONE);
                            }
                        }
                        loadComplete = true;
                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getActivity(), message);
//                        dismissProgress();
                        showErrorPage();
                        mSwipeRefreshLayout.setRefreshing(false);//刷新完成
                        loadComplete = true;
                    }

                    @Override
                    public void onComplete() {
//                        dismissProgress();
                        if (processedMaxid == 0) {
                            showSucceedPage();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);//刷新完成
                    }
                });
    }

    @Override
    public void onRefresh() {
        if (!loadComplete) {
            return;
        }
        mComplainSuggestListItemList.clear();
        //未处理
        if (type == TYPE_UNTREATED) {
            untratedMaxid = 0;
            untratedCanLoadMore = true;
            getComplainSuggestUntratedBean();
        } else {
            processedMaxid = 0;
            processedCanLoadMore = true;
            getComplainSuggestProcessedBean();
        }
    }

    @Override
    public void requestLoadMore() {
        if (!loadComplete) {
            return;
        }
        //未处理
        int size = mComplainSuggestListAdapter.getData().size();
        if (size > 0) {
            if (type == TYPE_UNTREATED && untratedCanLoadMore) {
                untratedMaxid = mComplainSuggestListAdapter.getData().get(size - 1).getUntratedListVoBean().getId();
                getComplainSuggestUntratedBean();
            } else if (type == TYPE_PROCESSED && processedCanLoadMore) {
                processedMaxid = mComplainSuggestListAdapter.getData().get(size - 1).getProcessedListVoBean().getId();
                getComplainSuggestProcessedBean();
            }
        }

    }

    //请求数据 未处理投诉建议列表
    @NonNull
    private Observable<ComplainSuggestUntratedBean> getComplainSuggestUntratedBean(int maxid) {
        return ComplainSuggestModel.INSTANCE.complainSuggestUntrated(UserUtil.getLoginBean().getPropertyCompanyId(), maxid)
                .compose(RxHelper.<ComplainSuggestUntratedBean>handleResult());
    }

    //请求数据 已处理投诉建议列表
    @NonNull
    private Observable<ComplainSuggestProcessedBean> getComplainSuggestProcessedBean(int maxid) {
        return ComplainSuggestModel.INSTANCE.complainSuggestProcessed(UserUtil.getLoginBean().getPropertyCompanyId(), maxid)
                .compose(RxHelper.<ComplainSuggestProcessedBean>handleResult());
    }
}
