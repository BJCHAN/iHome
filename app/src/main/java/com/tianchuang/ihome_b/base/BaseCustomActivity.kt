package com.tianchuang.ihome_b.base

import android.os.Bundle
import android.view.KeyEvent

import com.tianchuang.ihome_b.R
import com.tianchuang.ihome_b.utils.FragmentUtils

import butterknife.ButterKnife


/**
 * Created by Abyss on 2017/2/8.
 * description:自定义的Activity
 */

abstract class BaseCustomActivity : BaseActivity() {


    private var isFinishWithAnim: Boolean? = false//退出是否加入关闭动画

    fun setFinishWithAnim(finishWithAnim: Boolean?) {
        isFinishWithAnim = finishWithAnim
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        ButterKnife.bind(this)
    }

    override fun removeFragment() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            FragmentUtils.popFragment(supportFragmentManager)
        } else {
            customFinish()
        }
    }

    //返回键返回事件
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (supportFragmentManager.backStackEntryCount <= 1) {
                customFinish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 自定义的关闭方式
     */
    private fun customFinish() {
        finish()
        if (isFinishWithAnim!!) {
            //关闭带动画
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right)
        }
    }

}
