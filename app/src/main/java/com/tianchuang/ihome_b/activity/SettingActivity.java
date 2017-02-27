package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseCustomActivity;
import com.tianchuang.ihome_b.bean.event.LogoutEvent;
import com.tianchuang.ihome_b.view.LogoutDialogFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/15.
 * description:设置模块
 */
public class SettingActivity extends BaseCustomActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.ac_toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.setting_item1)
    RelativeLayout rlItem1;
    @BindView(R.id.setting_item2)
    RelativeLayout rlItem2;

    @Override
    protected int getFragmentContainerId() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishWithAnim(true);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
        toolbarTitle.setText("设置");
        setItemText(rlItem1, "修改密码");
        setItemText(rlItem2, "关于物管宝");
    }

    private void setItemText(RelativeLayout rlItem, String text) {
        ((TextView) rlItem.getChildAt(1)).setText(text);
    }


    @OnClick({R.id.setting_logout, R.id.setting_item1, R.id.setting_item2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_item1://修改密码
                startActivityWithAnim(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.setting_item2://关于物管宝
                startActivityWithAnim(new Intent(this, AboutMessageActivity.class));
                break;
            case R.id.setting_logout:
                LogoutDialogFragment.Builder builder = new LogoutDialogFragment.Builder();
                builder.setSureText("退出").setTipText(getString(R.string.logout_warning_string)).build()
                        .setOnClickButtonListener(new LogoutDialogFragment.OnClickButtonListener() {
                            @Override
                            public void onClickCancel() {

                            }

                            @Override
                            public void onClickSure() {
                                EventBus.getDefault().post(new LogoutEvent());
                                finish();
                            }
                        }).show(getFragmentManager(), LogoutDialogFragment.class.getSimpleName());
                break;
        }
    }
}
