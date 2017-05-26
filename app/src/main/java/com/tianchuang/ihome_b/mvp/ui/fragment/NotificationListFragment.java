package com.tianchuang.ihome_b.mvp.ui.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.NotificationListAdapter;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.bean.NotificationListBean;
import com.tianchuang.ihome_b.bean.model.NotificationModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Abyss on 2017/3/15.
 * description:通知列表fragment
 */

public class NotificationListFragment extends BaseRefreshAndLoadMoreFragment<NotificationItemBean, NotificationListBean> {

    public static NotificationListFragment newInstance() {
        return new NotificationListFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("管理通知");
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<NotificationItemBean> mData, NotificationListBean listBean) {
        return new NotificationListAdapter(mData);
    }

    @Override
    protected void onListitemClick(NotificationItemBean itemBean) {
        addFragment(NotificationDetailFragment.newInstance(itemBean.getId()));
    }

    @Override
    protected Observable<NotificationListBean> getNetObservable(int maxId) {
        return NotificationModel.INSTANCE.notificationList(maxId).compose(RxHelper.<NotificationListBean>handleResult());
    }

    @Override
    protected String getEmptyString() {
        return getString(R.string.notification_list_empty_string);
    }
}
