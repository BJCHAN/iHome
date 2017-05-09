package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.mvp.ui.activity.ComplainSuggestActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.ControlPointDetailActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.DataSearchActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.DeclareFormActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.InnerReportsActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.MainActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.ManageNotificationActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.MenuInnerReportsActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.MyTaskActivity;
import com.tianchuang.ihome_b.adapter.HomeMultiAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.HomePageBean;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.event.NotifyHomePageRefreshEvent;
import com.tianchuang.ihome_b.bean.event.OpenScanEvent;
import com.tianchuang.ihome_b.bean.event.SwitchSuccessEvent;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Abyss on 2017/2/9.
 * description:主页
 */

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.empty_container)
    FrameLayout emptyContainer;
    @BindView(R.id.main_content)
    LinearLayout mainContent;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.ll_write_form)
    LinearLayout llWriteForm;
    @BindView(R.id.ll_internal_reports)
    LinearLayout llInternalReports;
    @BindView(R.id.ll_main_query)
    LinearLayout llMainQuery;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private MainActivity holdingActivity;
    private HomeMultiAdapter homeMultiAdapter;
    private List<HomePageMultiItem> mData;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private int currentPostion = -1;//记录列表跳转的位置

    public void setCurrentPostion(int currentPostion) {//用于本地维护是否刷新列表数据
        this.currentPostion = currentPostion;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    private LittleRedListener littleRedListener;

    public MainFragment setLittleRedListener(LittleRedListener littleRedListener) {
        this.littleRedListener = littleRedListener;
        return this;
    }

    public interface LittleRedListener {
        void onRedPointChanged(int noticeCount);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        FragmentUtils.addFragment(getFragmentManager(), EmptyFragment.newInstance(getString(R.string.main_empty_text)), R.id.empty_container);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        initTab();
        mData = new ArrayList<>();
        homeMultiAdapter = new HomeMultiAdapter(mData);
        homeMultiAdapter.setEmptyView(ViewHelper.getEmptyView("当前数据为空"));
        rvList.setAdapter(homeMultiAdapter);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.refresh_scheme_color));

        //根据item的类型判断去那个详情页
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mData.size() > 0) {
                    HomePageMultiItem homePageMultiItem = mData.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("item", homePageMultiItem);
                    currentPostion = position;
                    switch (homePageMultiItem.getType()) {
                        case HomePageMultiItem.TYPE_COMPLAIN://建议
                            intent.setClass(getContext(), ComplainSuggestActivity.class);
                            startActivityWithAnim(intent);
                            break;
                        case HomePageMultiItem.TYPE_TASK://任务
                            if (homePageMultiItem.getMyTaskUnderWayItemBean().getTaskKind() == 13) {//录入数据型
                                intent.setClass(getContext(), MyTaskActivity.class);
                                startActivityWithAnim(intent);
                            } else {//控制点型
                                intent.setClass(getContext(), ControlPointDetailActivity.class);
                                intent.putExtra("taskRecordId", homePageMultiItem.getMyTaskUnderWayItemBean().getId());
                                startActivityWithAnim(intent);

                            }
                            break;
                        case HomePageMultiItem.TYPE_INNER_REPORT://内部报事
                            intent.setClass(getContext(), MenuInnerReportsActivity.class);
                            startActivityWithAnim(intent);
                            break;
                        case HomePageMultiItem.TYPE_NOTICE://通知
                            intent.setClass(getContext(), ManageNotificationActivity.class);
                            startActivityWithAnim(intent);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        LoginBean loginBean = UserUtil.getLoginBean();
        if (loginBean == null || !loginBean.getPropertyEnable()) {
            return;
        }
        HomePageModel.homePageList()
                .compose(RxHelper.handleResult())
                .compose(bindToLifecycle())
                .retry(2)
                .doOnSubscribe(o -> {
                    if (!swipeLayout.isRefreshing()) showProgress();
                })
                .subscribe(new RxSubscribe<HomePageBean>() {
                    @Override
                    public void _onNext(HomePageBean homePageBean) {
                        parseResult(homePageBean);
                        if (littleRedListener != null) {
                            littleRedListener.onRedPointChanged(homePageBean.getNoticeCount());
                        }
                    }

                    @Override
                    public void _onError(String message) {
                        dismissProgress();
                        ToastUtil.showToast(getContext(), message);
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 解析网络数据
     */
    private void parseResult(HomePageBean homePageBean) {
        Observable.just(homePageBean)
                .observeOn(Schedulers.io())
                .map(bean -> HomePageModel.getHomePageMultiItemList(bean))
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HomePageMultiItem>>() {

                    @Override
                    public void onComplete() {
                        swipeLayout.setRefreshing(false);
                        dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgress();
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(List<HomePageMultiItem> homePageMultiItems) {
                        mData.clear();
                        mData.addAll(homePageMultiItems);
                        homeMultiAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//用户切换成功
    public void onMessageEvent(SwitchSuccessEvent event) {//切换用户的事件
        initTab();
        swipeLayout.setRefreshing(true);
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NotifyHomePageRefreshEvent event) {//通知主页刷新
        if (currentPostion == -1) {//非主页跳转的通知
            swipeLayout.setRefreshing(true);
            initData();
        } else {
            homeMultiAdapter.remove(currentPostion);
            currentPostion = -1;
        }


    }

    private void initTab() {
        LoginBean loginBean = UserUtil.getLoginBean();
        if (loginBean == null) {
            return;
        }
        List<Integer> menuList = loginBean.getMenuList();
        llWriteForm.setVisibility(View.GONE);
        llInternalReports.setVisibility(View.GONE);
        llMainQuery.setVisibility(View.GONE);
        if (menuList != null && menuList.size() > 0) {
            Observable.fromIterable(menuList)
                    .compose(bindToLifecycle())
                    .subscribe(integer -> setViewVisibility(integer));
        }
    }

    private void setViewVisibility(Integer integer) {
        switch (integer) {
            case 2://提交表单
                llWriteForm.setVisibility(View.VISIBLE);
                break;
            case 4://内部报事提交
                llInternalReports.setVisibility(View.VISIBLE);
                break;
            case 6://数据查询
                llMainQuery.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void initListener() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        holdingActivity = ((MainActivity) getHoldingActivity());
        LoginBean loginBean = UserUtil.getLoginBean();
        if (loginBean != null) {//主页是否显示空页面
            boolean propertyEnable = loginBean.getPropertyEnable();
            emptyContainer.setVisibility(propertyEnable ? View.GONE : View.VISIBLE);
            mainContent.setVisibility(propertyEnable ? View.VISIBLE : View.GONE);
            holdingActivity.setSpinnerText(loginBean.getPropertyCompanyName());
            List<Integer> menuList = loginBean.getMenuList();
            //是否显示抢单大厅
            holdingActivity.setIvRightEnable(menuList != null && menuList.size() > 0 && propertyEnable && menuList.contains(8));
        }

    }


    @OnClick({R.id.ll_rich_scan, R.id.ll_write_form, R.id.ll_internal_reports, R.id.ll_main_query})
    public void onClick(View view) {
        currentPostion = -1;//初始化当前的位置
        switch (view.getId()) {
            case R.id.ll_rich_scan://扫一扫
                EventBus.getDefault().post(new OpenScanEvent());
                break;
            case R.id.ll_write_form://表单填报
                startActivity(new Intent(getHoldingActivity(), DeclareFormActivity.class));
                break;
            case R.id.ll_internal_reports://内部报事
                startActivity(new Intent(getHoldingActivity(), InnerReportsActivity.class));
                break;
            case R.id.ll_main_query://查询
                startActivity(new Intent(getHoldingActivity(), DataSearchActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        currentPostion = -1;//初始化当前的位置
        initData();
    }
}
