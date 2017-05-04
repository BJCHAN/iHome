package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.mvp.ui.activity.FormSubmitActivity;
import com.tianchuang.ihome_b.adapter.FormTypeListAdapter;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.bean.FormTypeListBean;
import com.tianchuang.ihome_b.bean.model.FormModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Created by Abyss on 2017/3/10.
 * description:表单类型列表fragment
 */

public class FormTypeListFragment extends BaseRefreshAndLoadMoreFragment<FormTypeItemBean, FormTypeListBean> {

    public static FormTypeListFragment newInstance() {
        return new FormTypeListFragment();
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<FormTypeItemBean> mData, FormTypeListBean listBean) {
        FormTypeListAdapter formTypeListAdapter = new FormTypeListAdapter(R.layout.form_type_item_holder, mData);
        formTypeListAdapter.addHeaderView(LayoutUtil.inflate(R.layout.form_type_list_header_holder));
        return formTypeListAdapter;
    }


    @Override
    protected void onListitemClick(FormTypeItemBean itemBean) {
        Intent intent = new Intent(getHoldingActivity(), FormSubmitActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", itemBean);
        intent.putExtras(bundle);
        startActivityWithAnim(intent);
    }

    @Override
    protected Observable<FormTypeListBean> getNetObservable(int maxId) {
        return FormModel.formTypeList(maxId).compose(RxHelper.<FormTypeListBean>handleResult());
    }

    @Override
    protected String getEmptyString() {
        return null;
    }
}
