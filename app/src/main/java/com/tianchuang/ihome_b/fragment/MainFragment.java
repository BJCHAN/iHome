package com.tianchuang.ihome_b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.ComplainSuggestActivity;
import com.tianchuang.ihome_b.activity.DataSearchActivity;
import com.tianchuang.ihome_b.activity.DeclareFormActivity;
import com.tianchuang.ihome_b.activity.InnerReportsActivity;
import com.tianchuang.ihome_b.activity.MainActivity;
import com.tianchuang.ihome_b.activity.ManageNotificationActivity;
import com.tianchuang.ihome_b.activity.MenuInnerReportsActivity;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.adapter.HomeMultiAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ComplainDetailBean;
import com.tianchuang.ihome_b.bean.HomePageBean;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.bean.event.OpenScanEvent;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Abyss on 2017/2/9.
 * description:主页
 */

public class MainFragment extends BaseFragment {

    @BindView(R.id.empty_container)
    FrameLayout emptyContainer;
    @BindView(R.id.main_content)
    LinearLayout mainContent;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MainActivity holdingActivity;
    private HomeMultiAdapter homeMultiAdapter;
    private List<HomePageMultiItem> mData;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        FragmentUtils.addFragment(getFragmentManager(), EmptyFragment.newInstance(getString(R.string.main_empty_text)), R.id.empty_container);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mData = new ArrayList<>();
        //根据item的类型判断去那个详情页
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mData.size() > 0) {
                    HomePageMultiItem homePageMultiItem = mData.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("item", homePageMultiItem);
                    switch (homePageMultiItem.getType()) {
                        case HomePageMultiItem.TYPE_COMPLAIN://建议
                            intent.setClass(getContext(), ComplainSuggestActivity.class);
                            startActivityWithAnim(intent);
                            break;
                        case HomePageMultiItem.TYPE_TASK://任务
                            if (homePageMultiItem.getMyTaskUnderWayItemBean().getTaskKind() == 5) {//录入数据型
                                intent.setClass(getContext(), MyTaskActivity.class);
                                startActivityWithAnim(intent);
                            } else {//控制点型


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
                .compose(RxHelper.<HomePageBean>handleResult())
                .compose(this.<HomePageBean>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<HomePageBean>() {
                    @Override
                    protected void _onNext(HomePageBean homePageBean) {
                        parseResult(homePageBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        dismissProgress();
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    /**
     * 解析网络数据
     */
    private void parseResult(HomePageBean homePageBean) {

        Observable.just(homePageBean)
                .observeOn(Schedulers.io())
                .map(new Func1<HomePageBean, List<HomePageMultiItem>>() {
                    @Override
                    public List<HomePageMultiItem> call(HomePageBean homePageBean) {//获取主页多类型数据集合
                        return getHomePageMultiItemList(homePageBean);
                    }
                })
                .compose(this.<List<HomePageMultiItem>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HomePageMultiItem>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgress();
                    }

                    @Override
                    public void onNext(List<HomePageMultiItem> homePageMultiItems) {
                        mData.clear();
                        mData.addAll(homePageMultiItems);
                        homeMultiAdapter = new HomeMultiAdapter(mData);
                        homeMultiAdapter.setEmptyView(ViewHelper.getEmptyView("当前数据为空"));
                        rvList.setAdapter(homeMultiAdapter);
                        dismissProgress();
                    }
                });
    }

    private List<HomePageMultiItem> getHomePageMultiItemList(HomePageBean homePageBean) {
        List<HomePageMultiItem> list = new ArrayList<>();
        for (ComplainDetailBean complainItemBean : homePageBean.getComplaintsVos()) {
            HomePageMultiItem homePageMultiItem = new HomePageMultiItem();
            homePageMultiItem.setType(HomePageMultiItem.TYPE_COMPLAIN);
            homePageMultiItem.setComplainItemBean(complainItemBean);
            list.add(homePageMultiItem);
        }
        for (MenuInnerReportsItemBean menuInnerReportsItemBean : homePageBean.getInternalReportVos()) {
            HomePageMultiItem homePageMultiItem = new HomePageMultiItem();
            homePageMultiItem.setType(HomePageMultiItem.TYPE_INNER_REPORT);
            homePageMultiItem.setMenuInnerReportsItemBean(menuInnerReportsItemBean);
            list.add(homePageMultiItem);
        }
        for (NotificationItemBean notificationItemBean : homePageBean.getNotices()) {
            HomePageMultiItem homePageMultiItem = new HomePageMultiItem();
            homePageMultiItem.setType(HomePageMultiItem.TYPE_NOTICE);
            homePageMultiItem.setNotificationItemBean(notificationItemBean);
            list.add(homePageMultiItem);
        }
        for (MyTaskUnderWayItemBean myTaskUnderWayItemBean : homePageBean.getTaskRecordVos()) {
            HomePageMultiItem homePageMultiItem = new HomePageMultiItem();
            homePageMultiItem.setType(HomePageMultiItem.TYPE_TASK);
            homePageMultiItem.setMyTaskUnderWayItemBean(myTaskUnderWayItemBean);
            list.add(homePageMultiItem);
        }
        return list;
    }


    @Override
    protected void initListener() {

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
            holdingActivity.setIvRightEnable(propertyEnable);
        }

    }


    @OnClick({R.id.ll_rich_scan, R.id.ll_write_form, R.id.ll_internal_reports, R.id.ll_main_query})
    public void onClick(View view) {
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


}
