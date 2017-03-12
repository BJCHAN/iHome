package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MyOrderUnderWayAdapter;
import com.tianchuang.ihome_b.bean.MyOrderCommonBean;
import com.tianchuang.ihome_b.bean.MyOrderListBean;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.bean.model.MyOrderModel;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/22.
 * description:我的订单（进行中和已完成）
 */

public class MyOrderStatusFragment extends BaseRefreshAndLoadMoreFragment<MyOrderCommonBean, MyOrderListBean> {

    public static final int UNDER_WAY = 201;
    public static final int FINISHED = 202;
    private int currentType;

    //type 种类 1 为进行中  2 为 已完成

    public static MyOrderStatusFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        MyOrderStatusFragment fragment = new MyOrderStatusFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void handleBundle() {
        currentType = getArguments().getInt("type");

    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<MyOrderCommonBean> mData, MyOrderListBean listBean) {
        if (currentType == UNDER_WAY) {
            return new MyOrderUnderWayAdapter(R.layout.myorder_under_way_item_holder
                    , mData);
        } else {
            return new MyOrderUnderWayAdapter(R.layout.myorder_finnished_item_holder
                    , mData);
        }
    }

    @Override
    protected void onListitemClick(MyOrderCommonBean itemBean) {
        addFragment(MyOrderDetailFragment.newInstance(itemBean.getId()));
    }

    @Override
    protected Observable<MyOrderListBean> getNetObservable(int maxId) {
        if (currentType == UNDER_WAY) {
            return MyOrderModel.myOrderUnfinished(UserUtil.getLoginBean().getPropertyCompanyId(), maxId)
                    .compose(RxHelper.<MyOrderListBean>handleResult());
        } else {
            return MyOrderModel.myOrderfinished(UserUtil.getLoginBean().getPropertyCompanyId(), maxId)
                    .compose(RxHelper.<MyOrderListBean>handleResult());
        }
    }

    /**
     * 访问网络请求数据
     */
    @Override
    protected String getEmptyString() {
        if (currentType == UNDER_WAY) {
            return getString(R.string.myorder_underway_empty);
        } else {
            return getString(R.string.myorder_finished_empty);
        }
    }
}
