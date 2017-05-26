package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.BuildingRoomItemBean
import com.tianchuang.ihome_b.bean.MyTaskUnderWayListBean
import com.tianchuang.ihome_b.bean.TaskControlPointDetailBean
import com.tianchuang.ihome_b.bean.TaskInputDetailBean
import com.tianchuang.ihome_b.bean.TaskInputResponseBean
import com.tianchuang.ihome_b.bean.TaskPointDetailBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService
import com.tianchuang.ihome_b.utils.UserUtil

import java.util.ArrayList
import java.util.HashMap

import io.reactivex.Observable
import okhttp3.MultipartBody

/**
 * Created by Abyss on 2017/3/15.
 * description:我的任务模块
 */

object MyTaskModel {
    /**
     * 我的任务进行中列表
     */
    fun myTaskUnderWayList(maxId: Int): Observable<HttpModle<MyTaskUnderWayListBean>> {
        return RetrofitService.createShowApi().myTaskUnderWayList(UserUtil.getLoginBean().propertyCompanyId, maxId)
    }

    /**
     * 我的任务已完成列表
     */
    fun myTaskFinishList(maxId: Int): Observable<HttpModle<MyTaskUnderWayListBean>> {
        return RetrofitService.createShowApi().myTaskFinishList(UserUtil.getLoginBean().propertyCompanyId, maxId)
    }

    /**
     * 任务录入详情
     */
    fun taskInputDetail(taskRecordId: Int): Observable<HttpModle<TaskInputDetailBean>> {
        return RetrofitService.createShowApi().taskInputDetail(taskRecordId)

    }

    /**
     * 任务数据录入提交
     */
    fun taskInputSubmit(taskRecordId: Int, buildingId: Int, cellId: Int, unitId: Int, roomNum: Int): Observable<HttpModle<TaskInputResponseBean>> {
        return RetrofitService.createShowApi().taskInputBuildingSubmit(taskRecordId, UserUtil.getLoginBean().propertyCompanyId, buildingId, cellId, unitId, roomNum)

    }

    /**
     * taskFinishedConfirm
     * 提交读数
     */
    fun taskCurrentDataSubmit(dataId: Int, currentData: String): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().taskCurrentDataSubmit(dataId, currentData)

    }

    /**
     * 完成任务确认
     */
    fun taskFinishedConfirm(taskRecordId: Int): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().taskFinishedConfirm(taskRecordId)
    }

    /**
     * 任务控制点详情
     */
    fun taskControlPointDetail(taskRecordId: Int): Observable<HttpModle<TaskPointDetailBean>> {
        return RetrofitService.createShowApi().taskControlPointDetail(taskRecordId)
    }

    /**
     * 表单提交
     */
    fun taskFormSubmit(recordId: Int, formId: Int, map: HashMap<String, String>, parts: List<MultipartBody.Part>): Observable<HttpModle<String>> {
        map.put("propertyCompanyId", UserUtil.getLoginBean().propertyCompanyId.toString())
        map.put("taskRecordId", recordId.toString())
        map.put("formTypeId", formId.toString())
        return RetrofitService.createShowApi().taskControlPointSubmit(map, parts)
    }

    /**
     * 房间号列表
     */
    fun requestRoomNumList(buildingId: Int, buildingCellId: Int, buildingUnitId: Int): Observable<HttpModle<ArrayList<BuildingRoomItemBean>>> {
        return RetrofitService.createShowApi().requestRoomNumList(UserUtil.getLoginBean().propertyCompanyId, buildingId, buildingCellId, buildingUnitId)
    }
}
