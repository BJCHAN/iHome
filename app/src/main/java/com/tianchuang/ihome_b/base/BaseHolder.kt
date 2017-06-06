package com.tianchuang.ihome_b.base

import android.view.View

/**
 * Created by Abyss on 2016/11/15.
 */
abstract class BaseHolder<in T> {
    var holderView: View

    init {
        //初始化holderview
        holderView = initHolderView()
        holderView.tag = this
    }

    /**
     * 获取holderview
     */
    fun getholderView(): View {
        return holderView
    }

    abstract fun initHolderView(): View
    abstract fun bindData(data: T)
}

