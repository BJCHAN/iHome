package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.NotificationItemBean
import com.tianchuang.ihome_b.bean.NotificationListBean
import com.tianchuang.ihome_b.bean.TaskInputDetailBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService
import com.tianchuang.ihome_b.utils.UserUtil

import io.reactivex.Observable


/**
 * Created by Abyss on 2017/3/15.
 * description:管理通知模块
 */

object NotificationModel {
    /**
     * 通知列表
     */
    fun notificationList(maxId: Int): Observable<HttpModle<NotificationListBean>> {
        return RetrofitService.createShowApi().notificationList(UserUtil.getLoginBean().propertyCompanyId, maxId)
    }

    /**
     * 通知详情
     */
    fun notificationDetail(noticeId: Int): Observable<HttpModle<NotificationItemBean>> {
        return RetrofitService.createShowApi().notificationDetail(noticeId)

    }

}
