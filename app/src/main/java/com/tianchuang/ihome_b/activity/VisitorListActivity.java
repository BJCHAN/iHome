package com.tianchuang.ihome_b.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.VistorListAdapter;
import com.tianchuang.ihome_b.base.BaseActivity;
import com.tianchuang.ihome_b.bean.PullToLoadMoreListener;
import com.tianchuang.ihome_b.bean.VisitorBean;
import com.tianchuang.ihome_b.bean.recyclerview.EmptyLoadMore;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.VisitorListModel;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action0;

public class VisitorListActivity extends BaseActivity implements SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener, PullToLoadMoreListener.OnLoadMoreListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.rv_visitor_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private VistorListAdapter mlistAdapter;
    private List<VisitorBean.VisitorItemBean> mModels;
    private int pageSize = 5;
    private View emptyView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_visitor_list;
    }

    @Override
    protected int getFragmentContainerId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.addOnScrollListener(new PullToLoadMoreListener(mSwipeRefreshLayout, this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mModels = new ArrayList<>();
        searchView.setOnQueryTextListener(this);
        initSearchView(searchView);
        searchView.clearFocus();//去除焦点，避免键盘弹出
        //设置搜索按钮的dianj事件
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.VISIBLE);
                searchIcon.setVisibility(View.INVISIBLE);
                tvTitle.setVisibility(View.INVISIBLE);
            }
        });
        //获取初始数据
        requestNet(0)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<VisitorBean>() {
                    @Override
                    protected void _onNext(VisitorBean bean) {
                        pageSize = bean.getPageSize();
                        mModels = bean.getListVo();
                        mlistAdapter = new VistorListAdapter(R.layout.item_visitor, mModels);
                        initAdapter(mlistAdapter);
                        mRecyclerView.setAdapter(mlistAdapter);
                        isLoadShow(mModels);
                        dismissProgress();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(VisitorListActivity.this, message);
                        dismissProgress();

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }


    private Observable<VisitorBean> requestNet(int maxId) {
        return VisitorListModel.visitorList(maxId)
                .compose(RxHelper.<VisitorBean>handleResult());
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<VisitorBean.VisitorItemBean> oldData = new ArrayList<>();

    /**
     * 根据文本过滤列表条目
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        oldData = mModels;
        if (TextUtils.isEmpty(newText)) {
            mlistAdapter.setNewData(oldData);
            setEmptyText(getString(R.string.visitorlist_empty_text));
            return true;
        }
        List<VisitorBean.VisitorItemBean> newList = filter(mModels, newText);
        mlistAdapter.setNewData(newList);
        if (newList.size() == 0 && emptyView != null) {//搜索结果为空
            setEmptyText("匹配结果为空");
        }
        isLoadShow(newList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    private void setEmptyText(String emptyText) {
        if (emptyView != null) {
            ((TextView) emptyView.findViewById(R.id.tv_tip)).setText(emptyText);
        }
    }

    /**
     * 判断加载view是否需要显示
     */
    private void isLoadShow(List<VisitorBean.VisitorItemBean> newList) {
        if (newList.size() < pageSize) {
            mlistAdapter.loadMoreEnd(true);
        }
    }

    private static List<VisitorBean.VisitorItemBean> filter(List<VisitorBean.VisitorItemBean> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();
        final List<VisitorBean.VisitorItemBean> filteredModelList = new ArrayList<>();
        for (VisitorBean.VisitorItemBean model : models) {
            final String text = model.getMobile().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private boolean isLoadMoreLoading = false;//是否正在加载更多

    /**
     * 加载更多
     */
    @Override
    public void requestLoadMore() {
        int size = mModels.size();
        if (isLoadMoreLoading || size == 0) {
            return;
        }
        mSwipeRefreshLayout.setEnabled(false);
        isLoadMoreLoading = true;
        requestNet(mModels.get(size - 1).getId())
                .compose(this.<VisitorBean>bindToLifecycle())
                .subscribe(new RxSubscribe<VisitorBean>() {
                    @Override
                    protected void _onNext(VisitorBean bean) {
                        mlistAdapter.addData(bean.getListVo());
                        if (bean.getListVo().size() < pageSize) {//没有更多数据
                            mlistAdapter.loadMoreEnd(false);
                            isLoadMoreLoading = true;
                        } else {
                            mlistAdapter.loadMoreComplete();//加载完成
                            isLoadMoreLoading = false;
                        }
                        mSwipeRefreshLayout.setEnabled(true);
                    }

                    @Override
                    protected void _onError(String message) {
                        isLoadMoreLoading = false;
                        mlistAdapter.loadMoreFail();
                        ToastUtil.showToast(VisitorListActivity.this, message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        requestNet(0)
                .compose(this.<VisitorBean>bindToLifecycle())
                .subscribe(new RxSubscribe<VisitorBean>() {
                    @Override
                    protected void _onNext(VisitorBean bean) {
                        isLoadMoreLoading = false;
                        mModels.clear();
                        mModels.addAll(bean.getListVo());
                        mlistAdapter.setNewData(mModels);
                        isLoadShow(bean.getListVo());
                        mSwipeRefreshLayout.setRefreshing(false);//刷新完成
                    }

                    @Override
                    protected void _onError(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        ToastUtil.showToast(VisitorListActivity.this, message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    private void initAdapter(final VistorListAdapter adapter) {
        //添加空页面
        emptyView = ViewHelper.getEmptyView(getString(R.string.visitorlist_empty_text));
        adapter.setEmptyView(emptyView);
        adapter.setOnLoadMoreListener(new EmptyLoadMore());
        isLoadShow(mModels);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                VisitorBean.VisitorItemBean menuInnerReportsItemBean = (VisitorBean.VisitorItemBean) adapter.getData().get(position);
            }
        });

    }

    /**
     * 初始化searchView
     */
    private void initSearchView(SearchView searchView) {
        final int editViewId = getResources().getIdentifier("search_src_text", "id", getPackageName());
        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete) searchView.findViewById(editViewId);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        LayoutUtil.removeDevider(searchView);//去除下划线
        if (mEdit != null) {
            mEdit.setHintTextColor(ContextCompat.getColor(this, R.color.gray));
            mEdit.setTextColor(ContextCompat.getColor(this, R.color.white_2));
            mEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            mEdit.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            finishWithAnim();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finishWithAnim();
    }
}
