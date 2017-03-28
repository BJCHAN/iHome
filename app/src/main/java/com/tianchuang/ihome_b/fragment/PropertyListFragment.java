package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MainActivity;
import com.tianchuang.ihome_b.adapter.PropertyListAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.PropertyListItemBean;
import com.tianchuang.ihome_b.bean.event.SwitchSuccessEvent;
import com.tianchuang.ihome_b.bean.model.PropertyModel;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.database.UserInfoDbHelper;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.view.viewholder.EmptyViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Abyss on 2017/2/21.
 * description:物业列表页面
 */

public class PropertyListFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MainActivity holdingActivity;
    private PropertyListAdapter listAdapter;
    private ArrayList<PropertyListItemBean> data;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_property_list;
    }

    public static PropertyListFragment newInstance() {
        return new PropertyListFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        holdingActivity = ((MainActivity) getHoldingActivity());
        holdingActivity.setSpinnerText("我的物业");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
        rvList.addItemDecoration(new CommonItemDecoration(DensityUtil.dip2px(getContext(), 10)));
    }

    @Override
    protected void initData() {
        //请求物业列表的数据
        PropertyModel.requestPropertyList()
                .compose(RxHelper.<ArrayList<PropertyListItemBean>>handleResult())
                .compose(this.<ArrayList<PropertyListItemBean>>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<ArrayList<PropertyListItemBean>>() {
                    @Override
                    protected void _onNext(ArrayList<PropertyListItemBean> propertyList) {
                        data = propertyList;
                        listAdapter = new PropertyListAdapter( data);
                        EmptyViewHolder emptyViewHolder = new EmptyViewHolder();
                        emptyViewHolder.bindData(getString(R.string.property_no_join));
                        listAdapter.setEmptyView(emptyViewHolder.getholderView());
                        rvList.setAdapter(listAdapter);
                        initmListener(listAdapter);
                        dismissProgress();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getHoldingActivity(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    private void initmListener(PropertyListAdapter listAdapter) {
        rvList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.fl_often_btn:
                        PropertyListItemBean propertyListItemBean = PropertyListFragment.this.listAdapter.getData().get(position);
                        if (!propertyListItemBean.getOftenUse()) {//已经是常用的不用再请求
                            requestSetOften(propertyListItemBean, position);
                        }
                        break;
                    default:
                }
            }
        });
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                PropertyListItemBean propertyListItemBean = PropertyListFragment.this.listAdapter.getData().get(position);
                LoginBean loginBean = UserUtil.propertyListItemBeanToLoginBean(propertyListItemBean);
                UserUtil.setLoginBean(loginBean);//更新内存中的loginbean
                PropertyListFragment.this.removeFragment();
                ToastUtil.showToast(holdingActivity, "切换成功");
                EventBus.getDefault().post(new SwitchSuccessEvent());
            }
        });
    }

    /**
     * 请求网络设为常用
     */
    private void requestSetOften(PropertyListItemBean propertyListItemBean, final int i) {
        PropertyModel.requestSetOften(propertyListItemBean.getId())
                .map(new Func1<HttpModle<String>, Boolean>() {
                    @Override
                    public Boolean call(HttpModle<String> stringHttpModle) {
                        return stringHttpModle.success();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgress();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {//设为常用成功
                            for (PropertyListItemBean propertyListItemBean : data) {
                                propertyListItemBean.setOftenUse(false);
                            }
                            PropertyListItemBean bean = data.get(i);
                            LoginBean loginBean = UserUtil.propertyListItemBeanToLoginBean(bean);
                            //储存到数据库中
                            UserInfoDbHelper.saveUserInfo(loginBean, UserUtil.getUserid());
                            bean.setOftenUse(!bean.getOftenUse());
                            listAdapter.setSelsctedPostion(i);
                            listAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showToast(getHoldingActivity(), "设为常用失败");
                        }
                        dismissProgress();

                    }
                });
    }


}
