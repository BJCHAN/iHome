package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.FormTypeListAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import java.util.List;

import butterknife.BindView;
import rx.Observable;

/**
 * Created by Abyss on 2017/4/21.
 * description:任务表单类型列表
 */

public class TaskFormTypeListFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private List<FormTypeItemBean> mData;
    private FormTypeListAdapter adapter;
    private TaskPointDetailBean detailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.base_recyclerview;
    }

    public static TaskFormTypeListFragment newInstance(TaskPointDetailBean detailBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailBean", detailBean);
        TaskFormTypeListFragment fragment = new TaskFormTypeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("选择表单");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        detailBean = (TaskPointDetailBean) getArguments().getSerializable("detailBean");
        Observable.from(detailBean.getFormTypeVoList())
                .filter(typeVoListBean->!typeVoListBean.isDone())
                .map(TaskPointDetailBean.FormTypeVoListBean::getFormTypeVo)
                .compose(bindToLifecycle())
                .toList()
                .subscribe(formTypeItemBeanList -> {
                    mData = formTypeItemBeanList;
                    rvList.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
                    adapter = new FormTypeListAdapter(R.layout.form_type_item_holder, mData);
                    adapter.addHeaderView(LayoutUtil.inflate(R.layout.form_type_list_header_holder));
                    rvList.setAdapter(adapter);
                });
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(getHoldingActivity(), FormSubmitActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("item", mData.get(position));
                FragmentUtils.popAddFragment(getFragmentManager(),
                        ((ToolBarActivity) getHoldingActivity()).getFragmentContainerId(),
                        TaskControlPointEditFragment.newInstance(detailBean.getId(),mData.get(position)), true);
//                intent.putExtras(bundle);
//                startActivityWithAnim(intent);
            }
        });

    }
}
