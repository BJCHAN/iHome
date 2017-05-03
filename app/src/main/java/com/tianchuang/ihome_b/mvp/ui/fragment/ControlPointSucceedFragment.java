package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.FragmentUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Abyss on 2017/4/24.
 * description:
 */

public class ControlPointSucceedFragment extends BaseLoadingFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.sure_bt)
    TextView tvSure;
    private TaskPointDetailBean detailBean;

    public static ControlPointSucceedFragment newInstance(TaskPointDetailBean detailBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailBean", detailBean);
        ControlPointSucceedFragment fragment = new ControlPointSucceedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("执行任务");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_control_point_succeed;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        detailBean = (TaskPointDetailBean) getArguments().getSerializable("detailBean");
        tvTitle.setText(getNotNull(detailBean.getTaskName()));
        tvDate.setText(getNotNull(DateUtils.formatDate(detailBean.getCreatedDate(), DateUtils.TYPE_01)));
        tvContent.setText(getNotNull(detailBean.getTaskExplains()));
        tvTip.setText(String.format("%s已完成", detailBean.getScanControlPointName()));
    }

    @Override
    protected void initData() {
        showSucceedPage();
        List<TaskPointDetailBean.FormTypeVoListBean> formTypeVoList = detailBean.getFormTypeVoList();
        if (formTypeVoList == null || formTypeVoList.size() == 0) {
            tvSure.setVisibility(View.INVISIBLE);
        } else {
            Observable.from(formTypeVoList)
                    .filter(typeVoListBean -> typeVoListBean.isDone())
                    .toList()
                    .subscribe(list -> {
                        if (list.size() == formTypeVoList.size()) {
                            tvSure.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }


    @OnClick({R.id.tv_detail_btn, R.id.sure_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_detail_btn:
                removeFragment();
                break;
            case R.id.sure_bt:
                FragmentUtils.popAddFragment(getFragmentManager(),
                        ((ToolBarActivity) getHoldingActivity()).getFragmentContainerId(),
                        TaskFormTypeListFragment.newInstance(detailBean), true);
                break;
        }
    }
}
