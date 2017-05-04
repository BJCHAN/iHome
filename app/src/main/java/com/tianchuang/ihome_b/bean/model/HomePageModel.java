package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.ComplainDetailBean;
import com.tianchuang.ihome_b.bean.HomePageBean;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.bean.PersonalInfoBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by Abyss on 2017/3/21.
 * description:主页模块
 */

public class HomePageModel {
    /**
     * 主页列表
     */
    public static Observable<HttpModle<HomePageBean>> homePageList() {
        return RetrofitService.createShowApi().homePageList(UserUtil.getLoginBean().getPropertyCompanyId());
    }

    /**
     * 请求解析二维码
     * taskId:xxxxxx                     //仅点击执行任务进行的扫描需传递此参数
     * propertyCompanyId:xxx             //物业公司ID（对应在物业列表接口中propertyCompanyId）
     * code:xxx                          //二维码解析到的code
     */
    public static Observable<HttpModle<ArrayList<QrCodeBean>>> requestQrCode(Map<String, String> map) {
        return RetrofitService.createShowApi().requestQrCode(map);
    }
    /**
     * 请求解析二维码
     * taskId:xxxxxx                     //仅点击执行任务进行的扫描需传递此参数
     * propertyCompanyId:xxx             //物业公司ID（对应在物业列表接口中propertyCompanyId）
     * code:xxx                          //二维码解析到的code
     */
    public static Observable<HttpModle<TaskPointDetailBean>> requestTaskQrCode(Map<String, String> map) {
        return RetrofitService.createShowApi().requestTaskQrCode(map);
    }

    /**
     * 个人信息
     */
    public static Observable<HttpModle<ArrayList<PersonalInfoBean>>> requestPersonInfo() {
        return RetrofitService.createShowApi().requestPersonInfo();
    }


    public static  List<HomePageMultiItem> getHomePageMultiItemList(HomePageBean homePageBean) {
        List<HomePageMultiItem> list = new ArrayList<>();
        for (ComplainDetailBean complainItemBean : homePageBean.getComplaintsVos()) {
            HomePageMultiItem homePageMultiItem = new HomePageMultiItem();
            homePageMultiItem.setType(HomePageMultiItem.TYPE_COMPLAIN);
            homePageMultiItem.setComplainItemBean(complainItemBean);
            list.add(homePageMultiItem);
        }
        for (MenuInnerReportsItemBean menuInnerReportsItemBean : homePageBean.getInternalReportVos()) {
            HomePageMultiItem homePageMultiItem = new HomePageMultiItem();
            homePageMultiItem.setType(HomePageMultiItem.TYPE_INNER_REPORT);
            homePageMultiItem.setMenuInnerReportsItemBean(menuInnerReportsItemBean);
            list.add(homePageMultiItem);
        }
        for (NotificationItemBean notificationItemBean : homePageBean.getNotices()) {
            HomePageMultiItem homePageMultiItem = new HomePageMultiItem();
            homePageMultiItem.setType(HomePageMultiItem.TYPE_NOTICE);
            homePageMultiItem.setNotificationItemBean(notificationItemBean);
            list.add(homePageMultiItem);
        }
        for (MyTaskUnderWayItemBean myTaskUnderWayItemBean : homePageBean.getTaskRecordVos()) {
            HomePageMultiItem homePageMultiItem = new HomePageMultiItem();
            homePageMultiItem.setType(HomePageMultiItem.TYPE_TASK);
            homePageMultiItem.setMyTaskUnderWayItemBean(myTaskUnderWayItemBean);
            list.add(homePageMultiItem);
        }
        return list;
    }
}
