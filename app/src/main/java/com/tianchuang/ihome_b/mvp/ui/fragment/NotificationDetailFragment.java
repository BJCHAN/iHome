package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.bean.event.NotifyHomePageRefreshEvent;
import com.tianchuang.ihome_b.bean.model.NotificationModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/15.
 * description:公告详情页面
 */

public class NotificationDetailFragment extends BaseLoadingFragment {
    @BindView(R.id.tv_notification_type)
    TextView tvNotificationType;
    @BindView(R.id.tv_notification_date)
    TextView tvNotificationDate;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification_detail;
    }

    public static NotificationDetailFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("noticeId", id);
        NotificationDetailFragment notificationDetailFragment = new NotificationDetailFragment();
        notificationDetailFragment.setArguments(bundle);
        return notificationDetailFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("公告详情");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }


    @Override
    protected void initData() {
        int noticeId = getArguments().getInt("noticeId");
        NotificationModel.INSTANCE.notificationDetail(noticeId)
                .compose(RxHelper.<NotificationItemBean>handleResult())
                .compose(this.<NotificationItemBean>bindToLifecycle())
                .subscribe(new RxSubscribe<NotificationItemBean>() {
                    @Override
                    public void _onNext(NotificationItemBean itemBean) {
                        checkData(itemBean);
                        tvNotificationType.setText(StringUtils.getNotNull(itemBean.getTitle()));
                        tvNotificationDate.setText(StringUtils.getNotNull(
                                DateUtils.formatDate(itemBean.getCreatedDate(), DateUtils.TYPE_05)));
                        tvContent.setText(StringUtils.getNotNull(itemBean.getContent()));
                        EventBus.getDefault().post(new NotifyHomePageRefreshEvent());//通知主页刷新
                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        showErrorPage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
