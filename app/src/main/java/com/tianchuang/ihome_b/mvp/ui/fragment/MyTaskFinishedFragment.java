package com.tianchuang.ihome_b.mvp.ui.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MyTaskFinishedAdapter;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayListBean;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/15.
 * description:我的任务（已完成）
 */

public class MyTaskFinishedFragment extends BaseRefreshAndLoadMoreFragment<MyTaskUnderWayItemBean, MyTaskUnderWayListBean> {

    public static MyTaskFinishedFragment newInstance() {
        return new MyTaskFinishedFragment();
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<MyTaskUnderWayItemBean> mData, MyTaskUnderWayListBean listBean) {
        return new MyTaskFinishedAdapter(mData);
    }

    @Override
    protected void onListitemClick(MyTaskUnderWayItemBean itemBean) {
        if (itemBean.getTaskKind() == 5) {
            addFragment(MyTaskInputDetailFragment.newInstance(itemBean));
        } else {
            addFragment(MyTaskControlPointDetailFragment.newInstance(itemBean.getId()));
        }
    }

    @Override
    protected Observable<MyTaskUnderWayListBean> getNetObservable(int maxId) {
        return MyTaskModel.myTaskFinishList(maxId).compose(RxHelper.<MyTaskUnderWayListBean>handleResult());
    }

    @Override
    protected String getEmptyString() {
        return getString(R.string.mytask_finished_empty);
    }
}
