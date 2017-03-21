package com.tianchuang.ihome_b.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by wujian on 2017/3/1.
 * 主页列表item数据
 */

public class HomePageMultiItem implements MultiItemEntity, Serializable {
    //通知
    public static final int TYPE_NOTICE = 1;
    //投诉
    public static final int TYPE_COMPLAIN = 2;
    //内部报事
    public static final int TYPE_INNER_REPORT = 3;
    //任务
    public static final int TYPE_TASK = 4;

    //数据类型
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


    @Override
    public int getItemType() {
        return getType();
    }

    //通知item
    private NotificationItemBean notificationItemBean;
    //投诉item
    private ComplainItemBean complainItemBean;
    //内部报事item
    private MenuInnerReportsItemBean menuInnerReportsItemBean;
    //任务item
    private MyTaskUnderWayItemBean myTaskUnderWayItemBean;

    public NotificationItemBean getNotificationItemBean() {
        return notificationItemBean;
    }

    public void setNotificationItemBean(NotificationItemBean notificationItemBean) {
        this.notificationItemBean = notificationItemBean;
    }

    public ComplainItemBean getComplainItemBean() {
        return complainItemBean;
    }

    public void setComplainItemBean(ComplainItemBean complainItemBean) {
        this.complainItemBean = complainItemBean;
    }

    public MenuInnerReportsItemBean getMenuInnerReportsItemBean() {
        return menuInnerReportsItemBean;
    }

    public void setMenuInnerReportsItemBean(MenuInnerReportsItemBean menuInnerReportsItemBean) {
        this.menuInnerReportsItemBean = menuInnerReportsItemBean;
    }

    public MyTaskUnderWayItemBean getMyTaskUnderWayItemBean() {
        return myTaskUnderWayItemBean;
    }

    public void setMyTaskUnderWayItemBean(MyTaskUnderWayItemBean myTaskUnderWayItemBean) {
        this.myTaskUnderWayItemBean = myTaskUnderWayItemBean;
    }
}
