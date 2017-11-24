package com.tianchuang.ihome_b.bean

/**
 * Created by Abyss on 2017/3/21.
 * description:首页列表的数据
 */

class HomePageBean {
    var noticeCount: Int = 0
    var notices: List<NotificationItemBean>? = null//  通知
    var complaintsVos: List<ComplainDetailBean>? = null //  投诉
    var internalReportVos: List<MenuInnerReportsItemBean>? = null//  内部报事
    var taskRecordVos: List<MyTaskUnderWayItemBean>? = null //  任务
    var repairs: RobHallListItem? = null//抢单大厅提醒
}
