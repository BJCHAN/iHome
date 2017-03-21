package com.tianchuang.ihome_b.bean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/21.
 * description:首页列表的数据
 */

public class HomePageBean {
    private List<NotificationItemBean> notices;//  通知
    private List<ComplainItemBean> complaintsVos; //  投诉
    private List<MenuInnerReportsItemBean> internalReportVos;//  内部报事
    private List<MyTaskUnderWayItemBean> taskRecordVos; //  任务

    public List<NotificationItemBean> getNotices() {
        return notices;
    }

    public void setNotices(List<NotificationItemBean> notices) {
        this.notices = notices;
    }

    public List<ComplainItemBean> getComplaintsVos() {
        return complaintsVos;
    }

    public void setComplaintsVos(List<ComplainItemBean> complaintsVos) {
        this.complaintsVos = complaintsVos;
    }

    public List<MenuInnerReportsItemBean> getInternalReportVos() {
        return internalReportVos;
    }

    public void setInternalReportVos(List<MenuInnerReportsItemBean> internalReportVos) {
        this.internalReportVos = internalReportVos;
    }

    public List<MyTaskUnderWayItemBean> getTaskRecordVos() {
        return taskRecordVos;
    }

    public void setTaskRecordVos(List<MyTaskUnderWayItemBean> taskRecordVos) {
        this.taskRecordVos = taskRecordVos;
    }


}
