package com.tianchuang.ihome_b.mvp

/**
 * Created by Abyss on 2017/4/1.
 * description:mvp的p
 */

interface BasePresenter<in V : BaseView> {
    fun attachView(view: V)
    fun detachView()
    //    void unsubscribe();
}
