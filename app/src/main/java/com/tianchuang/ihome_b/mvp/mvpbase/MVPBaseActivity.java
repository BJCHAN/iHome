package com.tianchuang.ihome_b.mvp.mvpbase;

import android.content.Context;
import android.os.Bundle;

import com.tianchuang.ihome_b.base.BaseCustomActivity;
import com.tianchuang.ihome_b.utils.InstanceUtils;

/**
 * Created by Abyss on 2017/4/1.
 * description:Activity作为View,通用实现
 */

public abstract class MVPBaseActivity<V extends BaseView,T extends BasePresenterImpl<V>> extends BaseCustomActivity implements BaseView{
    public T mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = InstanceUtils.getInstance(this,1);
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
//            mPresenter.unsubscribe();
            mPresenter.detachView();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
