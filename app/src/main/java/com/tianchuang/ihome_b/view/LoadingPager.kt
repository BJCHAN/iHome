package com.tianchuang.ihome_b.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.FrameLayout
import com.tianchuang.ihome_b.utils.LayoutUtil


/**
 * Created by Abyss on 2017/1/16.
 * description:加载的常用状态页
 */

abstract class LoadingPager : FrameLayout {

    private var mLoadingView: View? = null// 转圈的view
    private var mErrorView: View? = null// 错误的view
    private var mEmptyView: View? = null// 空的view
    var succeedView: View? = null
        private set// 成功的view

    private var mState: Int = 0 // 默认的状态

    private var loadpage_empty: Int = 0
    private var loadpage_error: Int = 0
    private var loadpage_loading: Int = 0

    constructor(context: Context, @LayoutRes loading: Int, @LayoutRes error: Int, @LayoutRes empty: Int) : super(context) {
        loadpage_empty = empty
        loadpage_error = error
        loadpage_loading = loading
        init()
    }

    private fun init() {
        // 初始化状态
        mState = STATE_UNLOADED
        // 初始化三个 状态的view 这个时候 三个状态的view叠加在一起了
        mLoadingView = createLoadingView()
        if (null != mLoadingView) {
            addView(mLoadingView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        }
        mErrorView = createErrorView()
        if (null != mErrorView) {
            addView(mErrorView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        }
        mEmptyView = createEmptyView()
        if (null != mEmptyView) {
            addView(mEmptyView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT))
        }
        showPagerView(mState)
    }

    fun showPagerView(mState: Int) {
        //外部调用也得更新状态
        this.mState = mState
        // 这个时候都不为空 mState默认是STATE_UNLOADED状态所以只显示 lodaing 下面的 error
        // 和empty暂时不显示
        if (null != mLoadingView) {
            mLoadingView!!.visibility = if (mState == STATE_UNLOADED || mState == STATE_LOADING)
                View.VISIBLE
            else

                View.INVISIBLE
        }
        if (null != mErrorView) {
            mErrorView!!.visibility = if (mState == STATE_ERROR)
                View.VISIBLE
            else
                View.INVISIBLE
        }
        if (null != mEmptyView) {
            mEmptyView!!.visibility = if (mState == STATE_EMPTY)
                View.VISIBLE
            else
                View.INVISIBLE
        }

        if (mState == STATE_SUCCEED && succeedView == null) {
            succeedView = createSuccessView()
            addView(succeedView, FrameLayout.LayoutParams

            (FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        }
        if (null != succeedView) {
            succeedView!!.visibility = if (mState == STATE_SUCCEED)
                View.VISIBLE
            else
                View.INVISIBLE
        }
    }

    fun show() {
        // 第一次进来肯定要 转圈的 所以就算是 error和empty 也要让状态是 unload
        if (mState == STATE_ERROR || mState == STATE_EMPTY) {
            mState = STATE_UNLOADED
        }
        // 如果是unload 就把状态 变为 loading了 这时候从服务器拿数据
        if (mState == STATE_UNLOADED) {
            mState = STATE_LOADING
            load()
        }
        showPagerView(mState)
    }

    /**
     * 制作界面
     */

    protected abstract fun createSuccessView(): View

    /**
     * 处理下载 耗时操作
     */
    protected abstract fun load()

    /**
     * 空界面
     */
    fun createEmptyView(): View? {
        if (loadpage_empty != 0) {
            return LayoutUtil.inflate(loadpage_empty)
        }
        return null

    }

    /**
     * 失败的页面
     */
    fun createErrorView(): View? {
        if (loadpage_error != 0) {
            val emptyView = LayoutUtil.inflate(loadpage_error)
            emptyView.setOnClickListener { show() }
            return emptyView
        }
        return null
    }

    /**
     * 正在旋转的页面
     */
    fun createLoadingView(): View? {
        if (loadpage_loading != 0) {
            val loadingView = LayoutUtil.inflate(loadpage_loading)
            return loadingView
        }
        return null
    }


    companion object {
        // 加载默认的状态
        val STATE_UNLOADED = 1
        // 加载的状态
        val STATE_LOADING = 2
        // 加载失败的状态
        val STATE_ERROR = 3
        // 加载空的状态
        val STATE_EMPTY = 4
        // 加载成功的状态
        val STATE_SUCCEED = 5
    }

}