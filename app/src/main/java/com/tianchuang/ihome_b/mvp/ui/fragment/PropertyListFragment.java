package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.PropertyListAdapter;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.PropertyListItemBean;
import com.tianchuang.ihome_b.bean.event.SwitchSuccessEvent;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.mvp.MVPBaseFragment;
import com.tianchuang.ihome_b.mvp.contract.PropertyListContract;
import com.tianchuang.ihome_b.mvp.presenter.PropertyListPresenter;
import com.tianchuang.ihome_b.mvp.ui.activity.MainActivity;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.view.PropertyListDeleteDialogFragment;
import com.tianchuang.ihome_b.view.viewholder.EmptyViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/2/21.
 * description:物业列表页面
 */

public class PropertyListFragment extends MVPBaseFragment<PropertyListContract.View, PropertyListPresenter> implements PropertyListContract.View {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private MainActivity holdingActivity;
    private PropertyListAdapter listAdapter;
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
        mPresenter.requestPropertyListData();
    }

    @Override
    public void initAdapter(ArrayList<PropertyListItemBean> data) {
        listAdapter = new PropertyListAdapter(data);
        EmptyViewHolder emptyViewHolder = new EmptyViewHolder();
        emptyViewHolder.bindData(getString(R.string.property_no_join));
        listAdapter.setEmptyView(emptyViewHolder.getholderView());
        rvList.setAdapter(listAdapter);
        initmListener();
    }
    /**
     * ui设置常用
     * */
    @Override
    public void notifyUISetOften(int position) {
        listAdapter.setSelsctedPostion(position);
        listAdapter.notifyDataSetChanged();
    }

    private void initmListener() {
        //设置常用
        rvList.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.fl_often_btn:
                        PropertyListItemBean propertyListItemBean = PropertyListFragment.this.listAdapter.getData().get(position);
                        if (!propertyListItemBean.getOftenUse()) {//已经是常用的不用再请求
                            mPresenter.requestSetOften(propertyListItemBean, position);
                        }
                        break;
                    default:
                }
            }
        });
        //切换物业
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
        //长按物业删除弹窗
        rvList.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                PropertyListDeleteDialogFragment.newInstance().setConfirmDeleteListener(() -> {//确认删除的回调
                    adapter.remove(position);
                }).show(getFragmentManager(), PropertyListDeleteDialogFragment.class.getSimpleName());
            }
        });
    }

}
