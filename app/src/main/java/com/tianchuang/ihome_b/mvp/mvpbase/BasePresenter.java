package com.tianchuang.ihome_b.mvp.mvpbase;

/**
 * Created by Abyss on 2017/4/1.
 * description:mvp的p
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
    void unsubscribe();
}
