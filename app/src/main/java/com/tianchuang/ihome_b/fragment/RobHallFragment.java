package com.tianchuang.ihome_b.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.FaultDetailActivity;
import com.tianchuang.ihome_b.activity.RobHallActivity;
import com.tianchuang.ihome_b.adapter.RobHallAdapter;
import com.tianchuang.ihome_b.bean.RobHallListBean;
import com.tianchuang.ihome_b.bean.RobHallListItem;
import com.tianchuang.ihome_b.bean.event.RobOrderSuccessEvent;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.bean.model.RobHallModel;
import com.tianchuang.ihome_b.utils.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/20.
 * description:抢单大厅fragment
 */

public class RobHallFragment extends BaseRefreshAndLoadMoreFragment<RobHallListItem, RobHallListBean> {


    public static RobHallFragment newInstance() {
        return new RobHallFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("抢单大厅");
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<RobHallListItem> mData, RobHallListBean listBean) {
        EventBus.getDefault().register(this);
        return new RobHallAdapter(R.layout.rob_hall_item_holder, mData);
    }

    @Override
    protected void onListitemClick(RobHallListItem itemBean) {
        Intent intent = new Intent(getHoldingActivity(), FaultDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", itemBean);
        intent.putExtras(bundle);
        getHoldingActivity().startActivityWithAnim(intent);
    }

    @Override
    protected Observable<RobHallListBean> getNetObservable(int maxId) {
        return RobHallModel.requestRobHallList(UserUtil.getLoginBean().getPropertyCompanyId(), maxId)
                .compose(RxHelper.<RobHallListBean>handleResult());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RobOrderSuccessEvent event) {
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected String getEmptyString() {
        return getString(R.string.rob_hall_empty_tip);
    }


}
