package com.tianchuang.ihome_b.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tianchuang.ihome_b.R
import com.tianchuang.ihome_b.TianChuangApplication
import com.tianchuang.ihome_b.view.LoadingPager

import butterknife.ButterKnife

/**
 * Created by Abyss on 2017/3/31.
 * description:抽取加载操作的fragment
 */

abstract class BaseLoadingFragment : BaseFragment() {
    protected  var loadingPager: LoadingPager? = null

    protected var mContext :Context = if (activity == null) TianChuangApplication.application else activity
    private var errorPageId:  Int = 0
    private var emptyPageId: Int = 0
    private var loadingPageId: Int = 0


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null != loadingPager) {
            val parent = loadingPager?.parent
            if (parent!=null)(parent as ViewGroup).removeView(loadingPager)
        } else {
            mRootView = inflater!!.inflate(layoutId, container, false)
            bind = ButterKnife.bind(this, mRootView!!)
            initView(mRootView, savedInstanceState)//先初始化View,再去加载数据
            if (loadingPager == null) {
                loadingPager = initLoadingPager()
            }
        }
        //显示加载页面
        if (loadingPager != null)
            loadingPager!!.show()
        return loadingPager
    }

    private fun initLoadingPager(): LoadingPager? {
        loadingPageId = R.layout.base_loading_pager
        errorPageId = R.layout.base_error_pager
        emptyPageId = R.layout.base_empty_pager
        setCustomLoadingPager()
        return object : LoadingPager(mContext, loadingPageId, errorPageId, emptyPageId) {
            override fun createSuccessView(): View? {
                return mRootView
            }

            override fun load() {
                initData()
                initListener()
            }
        }
    }



    /**
     * 设置自定义的布局,可选调用相应的方法
     */
    private fun setCustomLoadingPager() {

    }

    /**
     * 获取数据必须实现
     */
    abstract override fun initData()

    /**
     * 校验数据，自选调用
     */
    protected fun checkData(datas: Any) {
        try {
            val ds = datas as List<*>
            if (ds.isEmpty()) {
                loadingPager!!.showPagerView(LoadingPager.STATE_EMPTY)
            } else {
                loadingPager!!.showPagerView(LoadingPager.STATE_SUCCEED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if ("" == datas) {
                loadingPager!!.showPagerView(LoadingPager.STATE_EMPTY)
            } else {
                loadingPager!!.showPagerView(LoadingPager.STATE_SUCCEED)
            }
        }

    }

    fun showErrorPage() {
        loadingPager!!.showPagerView(LoadingPager.STATE_ERROR)
    }

    fun showEmptyPage() {
        loadingPager!!.showPagerView(LoadingPager.STATE_EMPTY)
    }

    fun showSucceedPage() {
        loadingPager!!.showPagerView(LoadingPager.STATE_SUCCEED)
    }
}
