package com.tianchuang.ihome_b.mvp

import android.content.Context

/**
 * Created by Abyss on 2017/4/1.
 * description:p的通用实现
 */

open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {
     protected var mView: V? = null

    override fun attachView(view: V) {
        this.mView = view
    }

    override fun detachView() {
        this.mView = null
    }

    //最好在获取资源时使用
    val context: Context
        get() = mView!!.getContext()

}
