package com.tianchuang.ihome_b.base


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tianchuang.ihome_b.R
import com.tianchuang.ihome_b.utils.StringUtils
import com.tianchuang.ihome_b.utils.ToastUtil

import butterknife.ButterKnife
import butterknife.Unbinder


/**
 * Created by Abyss on 2017/2/8.
 * description:fragment的基类
 */

abstract class BaseFragment : RxFragment() {
    //获取宿主Activity
    protected var holdingActivity: BaseActivity? = null
    protected var bind: Unbinder? = null
    protected var mRootView: View? = null

    //获取布局文件ID
    protected abstract val layoutId: Int

    //初始化控件
    protected abstract fun initView(view: View?, savedInstanceState: Bundle?)


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.holdingActivity = context as BaseActivity
    }

    fun setToolbarTitle(title: String) {
        if (holdingActivity is ToolBarActivity) {
            val toolBarActivity = this.holdingActivity as ToolBarActivity
            toolBarActivity.setToolbarTitle(title)
        }
    }

    //添加fragment
    fun addFragment(fragment: BaseFragment?) {
        holdingActivity?.addFragment(fragment)
    }

    //移除fragment
    fun removeFragment() {
        holdingActivity?.removeFragment()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null != mRootView) {//处理fragment的view重复加载
            val parent  = mRootView?.parent
            if (parent!=null)(parent as ViewGroup).removeView(mRootView)
        } else {
            mRootView = inflater!!.inflate(layoutId, container, false)
            bind = ButterKnife.bind(this, mRootView!!)
            initView(mRootView, savedInstanceState)
            initData()
            initListener()
        }
        return mRootView
    }

    //初始化监听

    protected open fun initListener() {

    }

    //初始化数据
    protected open fun initData() {

    }

    fun showProgress() {
        holdingActivity?.showProgress()
    }

    fun dismissProgress() {
        holdingActivity?.dismissProgress()
    }

    fun showToast(message: String) {
        ToastUtil.showToast(context, message)
    }

    fun startActivityWithAnim(intent: Intent) {
        startActivity(intent)
        holdingActivity?.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left)
    }

    protected fun getNotNull(text: String): String {
        return StringUtils.getNotNull(text)
    }

    override fun onDestroy() {
        super.onDestroy()
        bind?.unbind()
    }
}
