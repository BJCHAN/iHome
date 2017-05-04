package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.bean.NotificationListBean;
import com.tianchuang.ihome_b.bean.TaskInputDetailBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import io.reactivex.Observable;


/**
 * Created by Abyss on 2017/3/15.
 * description:管理通知模块
 */

public class NotificationModel {
    /**
     * 通知列表
     */
    public static Observable<HttpModle<NotificationListBean>> notificationList(int maxId) {
        return RetrofitService.createShowApi().notificationList(UserUtil.getLoginBean().getPropertyCompanyId(), maxId);
    }

    /**
     * 通知详情
     */
    public static Observable<HttpModle<NotificationItemBean>> notificationDetail(int noticeId) {
        return RetrofitService.createShowApi().notificationDetail(noticeId);

    }

}
