package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.HomeMultiAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ComplainDetailBean;
import com.tianchuang.ihome_b.bean.HomePageBean;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Abyss on 2017/3/22.
 * description:
 */

public class ContentFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    public static ContentFragment newInstance() {
        return new ContentFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
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
                .subscribe(new RxSubscribe<HomePageBean>() {
                    @Override
                    protected void _onNext(HomePageBean homePageBean) {
                        parseResult(homePageBean);
                    }

                    @Override
                    protected void _onError(String message) {
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
                .subscribe(new Subscriber<List<HomePageMultiItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<HomePageMultiItem> homePageMultiItems) {
                        HomeMultiAdapter homeMultiAdapter = new HomeMultiAdapter(homePageMultiItems);
                        rvList.setAdapter(homeMultiAdapter);
//                        homeMultiAdapter.notifyDataSetChanged();
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
}
