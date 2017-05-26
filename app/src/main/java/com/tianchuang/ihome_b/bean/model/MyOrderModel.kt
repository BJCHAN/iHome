package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean
import com.tianchuang.ihome_b.bean.MaterialListItemBean
import com.tianchuang.ihome_b.bean.MyOrderDetailBean
import com.tianchuang.ihome_b.bean.MyOrderListBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService
import com.tianchuang.ihome_b.utils.UserUtil

import java.util.ArrayList

import io.reactivex.Observable
import okhttp3.MultipartBody

/**
 * Created by Abyss on 2017/3/3.
 * description:我的工单
 */


object MyOrderModel {

    /**
     * 未处理工单列表
     */
    fun myOrderUnfinished(propertyCompanyId: Int, maxid: Int): Observable<HttpModle<MyOrderListBean>> {
        return RetrofitService.createShowApi().myOrderUnfinished(propertyCompanyId, maxid)
    }

    /**
     * 已处理工单列表
     */
    fun myOrderfinished(propertyCompanyId: Int, maxid: Int): Observable<HttpModle<MyOrderListBean>> {
        return RetrofitService.createShowApi().myOrderfinished(propertyCompanyId, maxid)
    }

    /**
     * 工单详细
     */
    fun myOrderDetail(complaintsId: Int): Observable<HttpModle<MyOrderDetailBean>> {
        return RetrofitService.createShowApi().myOrderDetail(complaintsId)
    }

    /**
     * 维修确认
     */
    fun confirmOrder(repairsId: Int, content: String, parts: List<MultipartBody.Part>): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().confirmOrder(repairsId, parts, content)
    }

    /**
     * 材料费列表
     */
    fun materialList(): Observable<HttpModle<ArrayList<MaterialListItemBean>>> {
        return RetrofitService.createShowApi().materialList(UserUtil.getLoginBean().propertyCompanyId)
    }

    /**
     * 人工费种类
     */
    fun chargeTypeList(): Observable<HttpModle<ArrayList<ChargeTypeListItemBean>>> {
        return RetrofitService.createShowApi().chargeTypeList(UserUtil.getLoginBean().propertyCompanyId)
    }

    /**
     * 提交费用明细
     */
    fun submitFeeList(repairId: Int, offline: Int, json: String): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().submitFeeList(repairId, offline, json)
    }
}
