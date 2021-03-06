package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.ui.activity.MyTaskActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.TaskInputResponseBean;
import com.tianchuang.ihome_b.bean.event.NotifyHomePageRefreshEvent;
import com.tianchuang.ihome_b.bean.event.NotifyTaskDetailRefreshEvent;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/22.
 * description:录入数据成功
 */

public class TaskInputSuccessFragment extends BaseFragment {

    @BindView(R.id.bt_continue)
    Button btContinue;
    @BindView(R.id.tv_finish)
    Button tvFinish;
    private MyTaskActivity holdingActivity;
    private TaskInputResponseBean bean;

    public static TaskInputSuccessFragment newInstance(TaskInputResponseBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        TaskInputSuccessFragment taskInputSuccessFragment = new TaskInputSuccessFragment();
        taskInputSuccessFragment.setArguments(bundle);
        return taskInputSuccessFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        holdingActivity = ((MyTaskActivity) getHoldingActivity());
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bean = (TaskInputResponseBean) getArguments().getSerializable("bean");
        btContinue.setText(String.format("继续录入%s数据", bean.getTaskName()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_input_success;
    }


    @OnClick({R.id.bt_continue, R.id.tv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_continue:
                removeFragment();
                EventBus.getDefault().post(new NotifyTaskDetailRefreshEvent());
                break;
            case R.id.tv_finish:
                MyTaskModel.INSTANCE.taskFinishedConfirm(bean.getTaskRecordId())
                        .compose(RxHelper.<String>handleResult())
                        .compose(this.<String>bindToLifecycle())
                        .subscribe(new RxSubscribe<String>() {
                            @Override
                            public void _onNext(String s) {
                                EventBus.getDefault().post(new NotifyTaskDetailRefreshEvent());
                                EventBus.getDefault().post(new NotifyHomePageRefreshEvent());//通知主页刷新
                                holdingActivity.closeAllFragment();
                            }

                            @Override
                            public void _onError(String message) {
                                ToastUtil.showToast(getContext(), "数据未完成录入");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }
    }
}
