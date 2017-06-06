package com.tianchuang.ihome_b.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import com.tianchuang.ihome_b.R
import kotlinx.android.synthetic.main.base_toolbar.*

/**
 * Created by Abyss on 2017/2/8.
 * description:带toolbar,带添加fragment的Activity
 */
abstract class ToolBarActivity : BaseCustomActivity() {

    /**
     * 二维码的结果回调
     */
    var qrResultListener: QrResultListener? = null

    fun registerQrResultListener(qrResultListener: QrResultListener) {
        this.qrResultListener = qrResultListener
    }

    //获取第一个fragment
    protected abstract val firstFragment: BaseFragment?

    //获取Intent
    protected open fun handleIntent(intent: Intent) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ac_toolbar_toolbar != null) {
            initToolBar(ac_toolbar_toolbar)
        }
        if (null != intent) {
            handleIntent(intent)
        }
        //避免重复添加Fragment
        if (null == supportFragmentManager.fragments) {
            val firstFragment = firstFragment
            if (null != firstFragment) {
                addFragment(firstFragment)
            }
        }

    }

    fun setToolbarTitle(title: String) {
        if (!TextUtils.isEmpty(title))
            toolbar_title!!.text = title
    }

    protected open fun initToolBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        initNormalToolbar(toolbar, true)
    }

    override val layoutId: Int
        get() = R.layout.activity_base

    public override val fragmentContainerId: Int
        get() = R.id.fragment_container

    fun initNormalToolbar(toolbar: Toolbar, animMation: Boolean) {
        setFinishWithAnim(animMation)
        toolbar.setNavigationIcon(R.mipmap.back)
        toolbar.setNavigationOnClickListener { removeFragment() }
    }


}
