package com.tianchuang.ihome_b.mvp

import android.content.Context
import android.os.Bundle

import com.tianchuang.ihome_b.base.BaseLoadingFragment
import com.tianchuang.ihome_b.utils.InstanceUtils

/**
 * Created by Abyss on 2017/4/1.
 * description:fragemnt作为View,通用实现
 */

abstract class MVPBaseFragment<V : BaseView, T : BasePresenterImpl<V>> : BaseLoadingFragment(), BaseView {

    protected var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = InstanceUtils.getInstance<T>(this, 1)
        @Suppress("UNCHECKED_CAST")
        mPresenter!!.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.detachView()
        }

    }

}
