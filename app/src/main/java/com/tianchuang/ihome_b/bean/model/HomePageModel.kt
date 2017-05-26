package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.ComplainDetailBean
import com.tianchuang.ihome_b.bean.HomePageBean
import com.tianchuang.ihome_b.bean.HomePageMultiItem
import com.tianchuang.ihome_b.bean.MenuInnerReportsItemBean
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean
import com.tianchuang.ihome_b.bean.NotificationItemBean
import com.tianchuang.ihome_b.bean.PersonalInfoBean
import com.tianchuang.ihome_b.bean.QrCodeBean
import com.tianchuang.ihome_b.bean.TaskPointDetailBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService
import com.tianchuang.ihome_b.utils.UserUtil

import java.util.ArrayList

import io.reactivex.Observable


/**
 * Created by Abyss on 2017/3/21.
 * description:主页模块
 */

object HomePageModel {
    /**
     * 主页列表
     */
    fun homePageList(): Observable<HttpModle<HomePageBean>> {
        return RetrofitService.createShowApi().homePageList(UserUtil.getLoginBean().propertyCompanyId)
    }

    /**
     * 请求解析二维码
     * taskId:xxxxxx                     //仅点击执行任务进行的扫描需传递此参数
     * propertyCompanyId:xxx             //物业公司ID（对应在物业列表接口中propertyCompanyId）
     * code:xxx                          //二维码解析到的code
     */
    fun requestQrCode(map: Map<String, String>): Observable<HttpModle<ArrayList<QrCodeBean>>> {
        return RetrofitService.createShowApi().requestQrCode(map)
    }

    /**
     * 请求解析二维码
     * taskId:xxxxxx                     //仅点击执行任务进行的扫描需传递此参数
     * propertyCompanyId:xxx             //物业公司ID（对应在物业列表接口中propertyCompanyId）
     * code:xxx                          //二维码解析到的code
     */
    fun requestTaskQrCode(map: Map<String, String>): Observable<HttpModle<TaskPointDetailBean>> {
        return RetrofitService.createShowApi().requestTaskQrCode(map)
    }

    /**
     * 个人信息
     */
    fun requestPersonInfo(): Observable<HttpModle<ArrayList<PersonalInfoBean>>> {
        return RetrofitService.createShowApi().requestPersonInfo()
    }


    fun getHomePageMultiItemList(homePageBean: HomePageBean): List<HomePageMultiItem> {
        val list = ArrayList<HomePageMultiItem>()
        for (complainItemBean in homePageBean.complaintsVos!!) {
            val homePageMultiItem = HomePageMultiItem()
            homePageMultiItem.type = HomePageMultiItem.TYPE_COMPLAIN
            homePageMultiItem.complainItemBean = complainItemBean
            list.add(homePageMultiItem)
        }
        for (menuInnerReportsItemBean in homePageBean.internalReportVos!!) {
            val homePageMultiItem = HomePageMultiItem()
            homePageMultiItem.type = HomePageMultiItem.TYPE_INNER_REPORT
            homePageMultiItem.menuInnerReportsItemBean = menuInnerReportsItemBean
            list.add(homePageMultiItem)
        }
        for (notificationItemBean in homePageBean.notices!!) {
            val homePageMultiItem = HomePageMultiItem()
            homePageMultiItem.type = HomePageMultiItem.TYPE_NOTICE
            homePageMultiItem.notificationItemBean = notificationItemBean
            list.add(homePageMultiItem)
        }
        for (myTaskUnderWayItemBean in homePageBean.taskRecordVos!!) {
            val homePageMultiItem = HomePageMultiItem()
            homePageMultiItem.type = HomePageMultiItem.TYPE_TASK
            homePageMultiItem.myTaskUnderWayItemBean = myTaskUnderWayItemBean
            list.add(homePageMultiItem)
        }
        return list
    }
}
