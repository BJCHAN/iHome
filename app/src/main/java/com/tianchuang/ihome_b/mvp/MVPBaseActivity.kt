package com.tianchuang.ihome_b.mvp

import android.content.Context
import android.os.Bundle

import com.tianchuang.ihome_b.base.BaseCustomActivity
import com.tianchuang.ihome_b.utils.InstanceUtils

/**
 * Created by Abyss on 2017/4/1.
 * description:Activity作为View,通用实现
 */

abstract class MVPBaseActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseCustomActivity(), BaseView {
    var mPresenter: T? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = InstanceUtils.getInstance<T>(this, 1)
        mPresenter!!.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            //            mPresenter.unsubscribe();
            mPresenter!!.detachView()
        }
    }

}
