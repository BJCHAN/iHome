package com.tianchuang.ihome_b.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.base.BaseFragment;

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

    public static TaskInputSuccessFragment newInstance(String taskName) {
        Bundle bundle = new Bundle();
        bundle.putString("taskName", taskName);
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
        String taskName = getArguments().getString("taskName");
        btContinue.setText(String.format("继续录入%s数据", taskName));
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
                break;
            case R.id.tv_finish:
                holdingActivity.closeAllFragment();
                break;
        }
    }
}
