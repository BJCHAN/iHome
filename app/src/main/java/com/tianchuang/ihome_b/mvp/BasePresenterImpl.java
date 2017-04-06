package com.tianchuang.ihome_b.mvp;

import android.content.Context;

/**
 * Created by Abyss on 2017/4/1.
 * description:p的通用实现
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public Context getContext() {//最好在获取资源时使用
        return mView.getContext();
    }

}
