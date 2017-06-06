package com.tianchuang.ihome_b.base

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

import com.afollestad.materialdialogs.MaterialDialog
import com.tianchuang.ihome_b.R
import com.tianchuang.ihome_b.TianChuangApplication
import com.tianchuang.ihome_b.utils.FragmentUtils
import com.tianchuang.ihome_b.utils.MaterialDialogsUtil


/**
 * Created by Abyss on 2017/2/8.
 * description:FragmentActivity的基类
 */

abstract class BaseActivity : RxFragmentActivity() {

    var materialDialogsUtil: MaterialDialogsUtil? = null
        private set
    private var progressDialog: MaterialDialog? = null

    //布局文件ID
    protected abstract val layoutId : Int

    //添加Fragment容器的ID
    protected abstract val fragmentContainerId : Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        materialDialogsUtil = MaterialDialogsUtil(this)
        TianChuangApplication.application.addActivity(this)
    }

    //添加fragment
    fun addFragment(fragment: BaseFragment?): Fragment? {
        var i: Fragment? = null
        if (fragment != null) {
            i = FragmentUtils.replaceFragment(supportFragmentManager, fragmentContainerId, fragment, true)
        }
        return i
    }

    //移除fragment
    open fun removeFragment() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            FragmentUtils.popFragment(supportFragmentManager)
        } else {
            finish()
        }
    }



    //权限提示框
    //    @RequiresApi(api = Build.VERSION_CODES.M)
    fun showPermissionInfo(permissionContent: String, isCloese: Boolean) {
        if (materialDialogsUtil == null)
            materialDialogsUtil = MaterialDialogsUtil(this)
        //		int color = getResources().getColor(R.color.chartreuse);
        val titleColor = ContextCompat.getColor(this,R.color.TC_1)
        val contentColor = ContextCompat.getColor(this,R.color.TC_2)
        materialDialogsUtil!!.getBuilder(getString(R.string.perssion_tip), permissionContent, getString(R.string.perssion_go_setting), getString(R.string.perssion_cancel))
                .negativeColor(titleColor)
                .positiveColor(titleColor)
                .titleColor(titleColor)
                .contentColor(contentColor)
                .cancelable(false).callback(object : MaterialDialog.ButtonCallback() {
            override fun onPositive(dialog: MaterialDialog?) {
                dialogDismiss(isCloese)
            }

            override fun onNeutral(dialog: MaterialDialog?) {}

            override fun onNegative(dialog: MaterialDialog?) {
                materialDialogsUtil!!.dismiss()
                if (isCloese) {
                    finish()
                }
            }
        })
        materialDialogsUtil!!.show()
    }

    private fun dialogDismiss(isCloese: Boolean) {
        Handler().postDelayed({
            val localIntent = Intent()
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                localIntent.data = Uri.fromParts("package", packageName, null)
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.action = Intent.ACTION_VIEW
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
                localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName)
            }
            startActivityWithAnim(localIntent)
            if (isCloese) {
                finish()
            }
        }, 500)
        materialDialogsUtil!!.dismiss()
    }


    fun startActivityWithAnim(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left)
    }

    fun finishWithAnim() {
        finish()
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right)
    }


    fun showProgress() {
        progressDialog = materialDialogsUtil?.progressDialog
        progressDialog?.show()
    }

    fun dismissProgress() {
        progressDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        TianChuangApplication.application.removeActivity(this)
    }
}
