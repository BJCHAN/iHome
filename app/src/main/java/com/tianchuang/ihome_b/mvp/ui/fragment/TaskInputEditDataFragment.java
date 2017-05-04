package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.mvp.ui.activity.MyTaskActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.TaskInputResponseBean;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/3/17.
 * description:数据录入读数
 */

public class TaskInputEditDataFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_lastData)
    TextView tvLastData;
    @BindView(R.id.tv_currentData)
    EditText tvCurrentData;
    @BindView(R.id.tv_sure)
    Button tvSure;
    private TaskInputResponseBean taskBean;
    private MyTaskActivity holdingActivity;

    public static TaskInputEditDataFragment newInstance(TaskInputResponseBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        TaskInputEditDataFragment taskInputEditDataFragment = new TaskInputEditDataFragment();
        taskInputEditDataFragment.setArguments(bundle);
        return taskInputEditDataFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        taskBean = ((TaskInputResponseBean) getArguments().getSerializable("bean"));
        if (taskBean != null) {
            tvName.setText(StringUtils.getNotNull(taskBean.getTaskName()));
            TaskInputResponseBean.DataInfoBean info = taskBean.getDataInfo();
            if (info != null) {
                tvLastData.setText(StringUtils.getNotNull(info.getLastData()));
                tvAddress.setText(StringUtils.getNotNull(info.getRoomInfo()));
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mytask_input_edit_data;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        holdingActivity = ((MyTaskActivity) getHoldingActivity());
    }

    @OnClick(R.id.tv_sure)
    public void onClick() {
        String currentData = tvCurrentData.getText().toString().trim();
        if (StringUtils.isNumber(currentData)) {//有效数字
            TaskInputResponseBean.DataInfoBean dataInfo = taskBean.getDataInfo();
            if (dataInfo == null) {
                ToastUtil.showToast(getContext(), "数据为空");
                return;
            }
            Float currentNum = Float.valueOf(currentData);
            Float lastNum = Float.valueOf(dataInfo.getLastData());
            if (currentNum < lastNum) {
                ToastUtil.showToast(getContext(), "本期读数不得小于上期读数");
                return;
            }

            String formatNum = StringUtils.formatNumWithFour(currentData);
            tvCurrentData.setText(formatNum);
            MyTaskModel.taskCurrentDataSubmit(dataInfo.getId(), formatNum)
                    .compose(RxHelper.<String>handleResult())
                    .doOnSubscribe(o -> showProgress())
                    .compose(this.<String>bindToLifecycle())
                    .subscribe(new RxSubscribe<String>() {
                        @Override
                        public void _onNext(String s) {

                        }

                        @Override
                        public void _onError(String message) {
                            ToastUtil.showToast(getContext(), message);
                            dismissProgress();
                        }

                        @Override
                        public void onComplete() {
                            FragmentUtils.popAddFragment(getFragmentManager(),
                                    holdingActivity.getFragmentContainerId(),
                                    TaskInputSuccessFragment.newInstance(taskBean), true);
                            dismissProgress();

                        }
                    });
        } else {
            ToastUtil.showToast(getContext(), "请输入有效数");
        }
    }
}
