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
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.bean.model.RobHallModel;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/20.
 * description:抢单大厅fragment
 */

public class RobHallFragment extends BaseRefreshAndLoadMoreFragment<RobHallListItem, RobHallListBean> {

    private RobHallActivity holdingActivity;

    public static RobHallFragment newInstance() {
        return new RobHallFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        holdingActivity.setToolbarTitle("抢单大厅");
    }

    @Override
    protected void handleBundle() {
        holdingActivity = ((RobHallActivity) getHoldingActivity());
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<RobHallListItem> mData, RobHallListBean listBean) {
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

    @Override
    protected String getEmptyString() {
        return getString(R.string.rob_hall_empty_tip);
    }


}
