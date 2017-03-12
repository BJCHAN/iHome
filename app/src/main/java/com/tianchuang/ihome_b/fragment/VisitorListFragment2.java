package com.tianchuang.ihome_b.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.VistorListAdapter;
import com.tianchuang.ihome_b.bean.VisitorBean;
import com.tianchuang.ihome_b.bean.model.VisitorListModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/12.
 * description:
 */

public class VisitorListFragment2 extends BaseRefreshAndLoadMoreFragment<VisitorBean.VisitorItemBean, VisitorBean> {
    public static VisitorListFragment2 newInstance() {
        return new VisitorListFragment2();
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<VisitorBean.VisitorItemBean> mData, VisitorBean listBean) {
        return new VistorListAdapter(R.layout.item_visitor, mData);
    }

    @Override
    protected void onListitemClick(VisitorBean.VisitorItemBean itemBean) {

    }

    @Override
    protected Observable<VisitorBean> getNetObservable(int maxId) {
        return VisitorListModel.visitorList(maxId, "")
                .compose(RxHelper.<VisitorBean>handleResult());
    }

    @Override
    protected String getEmptyString() {
        return "最近访客为空";
    }
}
