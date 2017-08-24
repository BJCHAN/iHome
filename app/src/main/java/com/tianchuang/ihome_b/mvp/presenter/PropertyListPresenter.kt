package com.tianchuang.ihome_b.mvp.presenter

import com.tianchuang.ihome_b.bean.PropertyListItemBean
import com.tianchuang.ihome_b.bean.model.PropertyModel
import com.tianchuang.ihome_b.database.UserInfoDbHelper
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RxHelper
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe
import com.tianchuang.ihome_b.mvp.BasePresenterImpl
import com.tianchuang.ihome_b.mvp.contract.PropertyListContract
import com.tianchuang.ihome_b.utils.UserUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Created by Abyss on 2017/4/17.
 * description:物业列表
 */
class PropertyListPresenter : BasePresenterImpl<PropertyListContract.View>(), PropertyListContract.Presenter {
    private var data: ArrayList<PropertyListItemBean>? = null

    override fun requestPropertyListData() {
        PropertyModel.requestPropertyList()
                .compose(RxHelper.handleResult<ArrayList<PropertyListItemBean>>())
                .compose(mView!!.bindToLifecycle<ArrayList<PropertyListItemBean>>())
                .subscribe(object : RxSubscribe<ArrayList<PropertyListItemBean>>() {
                    override fun _onNext(propertyList: ArrayList<PropertyListItemBean>) {
                        data = propertyList
                        mView!!.showSucceedPage()
                        setCurrentProperty(propertyList)
                        mView!!.initAdapter(propertyList)

                    }

                    override fun _onError(message: String) {
                        mView!!.showToast(message)
                        mView!!.showErrorPage()
                    }

                    override fun onComplete() {

                    }
                })
    }

    /**
     * 设置当前的物业
     */
    private fun setCurrentProperty(propertyList: ArrayList<PropertyListItemBean>) {
        Observable.fromIterable(propertyList)
                .filter { bean -> bean.propertyCompanyId == UserUtil.getLoginBean().propertyCompanyId }//当前的物业
                .compose(mView!!.bindToLifecycle<PropertyListItemBean>())
                .subscribe { propertyListItemBean -> propertyListItemBean.isCurrentProperty = true }
    }

    override fun requestSetOften(propertyListItemBean: PropertyListItemBean, position: Int) {
        PropertyModel.requestSetOften(propertyListItemBean.id)
                .map { httpModle -> httpModle.success() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {  mView!!.showProgress() }
                .subscribe(object : Observer<Boolean> {
                    override fun onComplete() {}

                    override fun onError(e: Throwable) {
                        mView!!.dismissProgress()
                    }

                    override fun onSubscribe(@NonNull d: Disposable) {

                    }

                    override fun onNext(aBoolean: Boolean?) {
                        if (aBoolean!!) {//设为常用成功
                            for (propertyListItemBean in data!!) {
                                propertyListItemBean.oftenUse = false
                            }
                            val bean = data!![position]
                            val loginBean = UserUtil.propertyListItemBeanToLoginBean(bean)
                            //储存到数据库中
                            UserInfoDbHelper.saveUserInfo(loginBean, UserUtil.getUserid())
                            bean.oftenUse = !bean.oftenUse
                            mView!!.notifyUISetOften(position)//通知ui设置常用

                        } else {
                            mView!!.showToast("设为常用失败")
                        }
                        mView!!.dismissProgress()
                    }
                })
    }

    override fun requestDelete(position: Int) {
        PropertyModel.propertyDelete(data!![position].id)
                .compose(mView!!.bindToLifecycle<HttpModle<String>>())
                .doOnSubscribe { _ -> mView!!.showProgress() }
                .compose(RxHelper.handleResult<String>())
                .subscribe(object : RxSubscribe<String>() {
                    override fun _onNext(s: String) {
                        mView!!.dismissProgress()
                        mView!!.deleteItem(position)
                    }

                    override fun _onError(message: String) {
                        mView!!.showToast(message)
                        mView!!.dismissProgress()
                    }

                    override fun onComplete() {

                    }
                })
    }
}