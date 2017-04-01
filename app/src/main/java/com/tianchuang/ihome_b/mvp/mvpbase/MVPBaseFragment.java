package com.tianchuang.ihome_b.mvp.mvpbase;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.utils.InstanceUtils;

/**
 * Created by Abyss on 2017/4/1.
 * description:fragemnt作为View,通用实现
 */

public abstract class MVPBaseFragment<V extends BaseView,T extends BasePresenterImpl<V>> extends BaseLoadingFragment implements BaseView{

    protected T mPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = InstanceUtils.getInstance(this, 1);
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
            mPresenter.detachView();
        }

    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
