package com.tianchuang.ihome_b.mvp.mvpbase;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Abyss on 2017/4/1.
 * description:p的通用实现
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected V mView;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public CompositeSubscription getCompositeSubscription() {
        return compositeSubscription;
    }

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void unsubscribe() {
        if (compositeSubscription.hasSubscriptions())
            compositeSubscription.clear();
    }
}
