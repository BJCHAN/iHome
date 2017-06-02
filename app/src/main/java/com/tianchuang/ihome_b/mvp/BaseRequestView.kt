package com.tianchuang.ihome_b.mvp

/**
 * Created by Abyss on 2017/4/1.
 * description:
 */

interface BaseRequestView : BaseView {
    fun showToast(message: String)

    fun showErrorPage()

    fun showSucceedPage()

    fun showEmptyPage()

    fun showProgress()

    fun dismissProgress()

}
