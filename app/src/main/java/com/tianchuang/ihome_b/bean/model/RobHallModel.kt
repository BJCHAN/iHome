package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.RobHallListBean
import com.tianchuang.ihome_b.bean.RobHallRepairDetailListBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Abyss on 2017/2/27.
 * description:
 */

object RobHallModel {

    /**
     * 请求抢单大厅列表
     */
    fun requestRobHallList(propertyCompanyId: Int, maxid: Int): Observable<HttpModle<RobHallListBean>> {
        return RetrofitService.createShowApi().robHallList(propertyCompanyId, maxid)
    }

    /**
     * 报修故障详情
     */
    fun requestRobHallRepairDetail(repairsId: Int): Observable<HttpModle<RobHallRepairDetailListBean>> {
        return RetrofitService.createShowApi().robHallRepairDetail(repairsId)
    }

    /**
     * 报修故障抢单
     */
    fun requestRobRepair(repairsId: Int): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().robRepair(repairsId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}
