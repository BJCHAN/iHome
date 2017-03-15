package com.tianchuang.ihome_b.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MyTaskUnderWayAdapter;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayListBean;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/15.
 * description:我的任务（进行中）
 */

public class MyTaskUnderWayFragment extends BaseRefreshAndLoadMoreFragment<MyTaskUnderWayItemBean, MyTaskUnderWayListBean> {

    public static MyTaskUnderWayFragment newInstance() {
        return new MyTaskUnderWayFragment();
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<MyTaskUnderWayItemBean> mData, MyTaskUnderWayListBean listBean) {
        return new MyTaskUnderWayAdapter(mData);
    }

    @Override
    protected void onListitemClick(MyTaskUnderWayItemBean itemBean) {

    }

    @Override
    protected Observable<MyTaskUnderWayListBean> getNetObservable(int maxId) {
        return MyTaskModel.myTaskUnderWayList(maxId).compose(RxHelper.<MyTaskUnderWayListBean>handleResult());
    }

    @Override
    protected String getEmptyString() {
        return getString(R.string.mytask_under_way_empty_string);
    }
}
