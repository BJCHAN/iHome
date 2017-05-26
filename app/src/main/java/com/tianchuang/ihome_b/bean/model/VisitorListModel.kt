package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.LoginBean
import com.tianchuang.ihome_b.bean.VisitorBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService
import com.tianchuang.ihome_b.utils.UserUtil

import io.reactivex.Observable


/**
 * Created by Abyss on 2017/3/3.
 * description:访客列表模块
 */


object VisitorListModel {
    /**
     * 访客列表
     */
    fun visitorList(maxId: Int, mobile: String): Observable<HttpModle<VisitorBean>> {
        val loginBean = UserUtil.getLoginBean()
        return RetrofitService.createShowApi().visitorList(loginBean.propertyCompanyId, mobile, maxId)
    }
}
