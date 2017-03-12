package com.tianchuang.ihome_b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.DataSearchActivity;
import com.tianchuang.ihome_b.activity.DeclareFormActivity;
import com.tianchuang.ihome_b.activity.InnerReportsActivity;
import com.tianchuang.ihome_b.activity.MainActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.event.OpenScanEvent;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.UserUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Abyss on 2017/2/9.
 * description:主页
 */

public class MainFragment extends BaseFragment {

    @BindView(R.id.empty_container)
    FrameLayout emptyContainer;
    @BindView(R.id.main_content)
    LinearLayout mainContent;
    private MainActivity holdingActivity;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        FragmentUtils.addFragment(getFragmentManager(), EmptyFragment.newInstance(getString(R.string.main_empty_text)), R.id.empty_container);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onStart() {
        super.onStart();
        holdingActivity = ((MainActivity) getHoldingActivity());
        LoginBean loginBean = UserUtil.getLoginBean();
        if (loginBean != null) {//主页是否显示空页面
            boolean propertyEnable = loginBean.getPropertyEnable();
            emptyContainer.setVisibility(propertyEnable ? View.GONE : View.VISIBLE);
            mainContent.setVisibility(propertyEnable ? View.VISIBLE : View.GONE);
            holdingActivity.setSpinnerText(loginBean.getPropertyCompanyName());
            holdingActivity.setIvRightEnable(propertyEnable);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @OnClick({R.id.ll_rich_scan, R.id.ll_write_form, R.id.ll_internal_reports, R.id.ll_main_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_rich_scan://扫一扫
                EventBus.getDefault().post(new OpenScanEvent());
                break;
            case R.id.ll_write_form://表单填报
                startActivity(new Intent(getHoldingActivity(), DeclareFormActivity.class));
                break;
            case R.id.ll_internal_reports://内部报事
                startActivity(new Intent(getHoldingActivity(), InnerReportsActivity.class));
                break;
            case R.id.ll_main_query://查询
                startActivity(new Intent(getHoldingActivity(), DataSearchActivity.class));
                break;
        }
    }

}
