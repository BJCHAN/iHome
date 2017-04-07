package com.tianchuang.ihome_b.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;

import butterknife.BindView;


/**
 * Created by Abyss on 2017/2/8.
 * description:带toolbar,带添加fragment的Activity
 */

public abstract class ToolBarActivity extends BaseCustomActivity {

    @BindView(R.id.ac_toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    //获取第一个fragment
    protected abstract BaseFragment getFirstFragment();

    //获取Intent
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (toolbar != null) {
            initToolBar(toolbar);
        }
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        //避免重复添加Fragment
        if (null == getSupportFragmentManager().getFragments()) {
            BaseFragment firstFragment = getFirstFragment();
            if (null != firstFragment) {
                addFragment(firstFragment);
            }
        }

    }

    public void setToolbarTitle(String title) {
        if (!TextUtils.isEmpty(title))
            toolbarTitle.setText(title);
    }

    protected abstract void initToolBar(Toolbar toolbar);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    public void initNormalToolbar(Toolbar toolbar, boolean animMation) {
        setFinishWithAnim(animMation);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(v -> removeFragment());
    }


}
