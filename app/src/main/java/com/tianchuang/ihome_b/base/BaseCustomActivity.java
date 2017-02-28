package com.tianchuang.ihome_b.base;

import android.os.Bundle;
import android.view.KeyEvent;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.utils.FragmentUtils;

import butterknife.ButterKnife;


/**
 * Created by Abyss on 2017/2/8.
 * description:自定义的Activity
 */

public abstract class BaseCustomActivity extends BaseActivity {


    private Boolean isFinishWithAnim = false;//退出是否加入关闭动画

    public void setFinishWithAnim(Boolean finishWithAnim) {
        isFinishWithAnim = finishWithAnim;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    @Override
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            FragmentUtils.popFragment(getSupportFragmentManager());
        } else {
            customFinish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                customFinish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 自定义的关闭方式
     */
    private void customFinish() {
        finish();
        if (isFinishWithAnim) {
            //关闭带动画
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
    }

}
