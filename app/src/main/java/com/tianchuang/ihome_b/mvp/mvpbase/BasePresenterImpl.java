package com.tianchuang.ihome_b.mvp.mvpbase;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Abyss on 2017/4/1.
 * description:p的通用实现
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected V mView;
//    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
//
//    public void addToSubscriptions(Subscription subscription) {
//        compositeSubscription.add(subscription);
//    }
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

//    @Override
//    public void unsubscribe() {
//        if (compositeSubscription.hasSubscriptions())
//            compositeSubscription.unsubscribe();
//    }
}
