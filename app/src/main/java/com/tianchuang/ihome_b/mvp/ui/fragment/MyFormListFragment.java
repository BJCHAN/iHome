package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.mvp.ui.activity.DeclareFormActivity;
import com.tianchuang.ihome_b.adapter.MyFormListAdapter;
import com.tianchuang.ihome_b.bean.MyFormItemBean;
import com.tianchuang.ihome_b.bean.MyFormListBean;
import com.tianchuang.ihome_b.bean.event.MyFormSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.model.FormModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Abyss on 2017/3/13.
 * description:我的表单
 */

public class MyFormListFragment extends BaseRefreshAndLoadMoreFragment<MyFormItemBean, MyFormListBean> {

    public static MyFormListFragment newInstance() {
        return new MyFormListFragment();
    }

    @Override
    protected void handleBundle() {
        super.handleBundle();
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.myform_fragment_refrsh_load;
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<MyFormItemBean> mData, MyFormListBean listBean) {
        return new MyFormListAdapter(R.layout.myform_list_item_holder, mData);
    }

    @Override
    protected void onListitemClick(MyFormItemBean itemBean) {
        addFragment(MyFormDetailFragment.newInstance(itemBean));
    }

    @Override
    protected Observable<MyFormListBean> getNetObservable(int maxId) {
        return FormModel.INSTANCE.myFormList(maxId).compose(RxHelper.<MyFormListBean>handleResult());
    }

    @Override
    protected String getEmptyString() {
        return getString(R.string.myform_no_form_string);
    }

    @OnClick(R.id.ll_add_form)
    public void onClick() {
        startActivity(new Intent(getContext(), DeclareFormActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyFormSubmitSuccessEvent event) {
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
