package com.tianchuang.ihome_b.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MyFormListAdapter;
import com.tianchuang.ihome_b.bean.MyFormItemBean;
import com.tianchuang.ihome_b.bean.MyFormListBean;
import com.tianchuang.ihome_b.bean.model.FormModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/13.
 * description:我的表单
 */

public class MyFormListFragment extends BaseRefreshAndLoadMoreFragment<MyFormItemBean, MyFormListBean> {


    public static MyFormListFragment newInstance() {
        return new MyFormListFragment();
    }


    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<MyFormItemBean> mData, MyFormListBean listBean) {
        return new MyFormListAdapter(R.layout.myform_list_item_holder, mData);
    }

    @Override
    protected void onListitemClick(MyFormItemBean itemBean) {

    }

    @Override
    protected Observable<MyFormListBean> getNetObservable(int maxId) {
        return FormModel.myFormList(maxId).compose(RxHelper.<MyFormListBean>handleResult());
    }

    @Override
    protected String getEmptyString() {
        return getString(R.string.myform_no_form_string);
    }
}
