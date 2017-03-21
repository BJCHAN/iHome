package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.MyTaskUnderWayListBean;
import com.tianchuang.ihome_b.bean.TaskControlPointDetailBean;
import com.tianchuang.ihome_b.bean.TaskInputDetailBean;
import com.tianchuang.ihome_b.bean.TaskInputResponseBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/15.
 * description:我的任务模块
 */

public class MyTaskModel {
    /**
     * 我的任务进行中列表
     */
    public static Observable<HttpModle<MyTaskUnderWayListBean>> myTaskUnderWayList(int maxId) {
        return RetrofitService.createShowApi().myTaskUnderWayList(UserUtil.getLoginBean().getPropertyCompanyId(), maxId);
    }

    /**
     * 任务录入详情
     */
    public static Observable<HttpModle<TaskInputDetailBean>> taskInputDetail(int taskRecordId) {
        return RetrofitService.createShowApi().taskInputDetail(taskRecordId);

    }

    /**
     * 任务数据录入提交
     */
    public static Observable<HttpModle<TaskInputResponseBean>> taskInputSubmit(int taskRecordId, int buildingId, int cellId, int unitId, int roomNum) {
        return RetrofitService.createShowApi().taskInputBuildingSubmit(taskRecordId, UserUtil.getLoginBean().getPropertyCompanyId(), buildingId, cellId, unitId, roomNum);

    }

    /**
     * taskFinishedConfirm
     * 提交读数
     */
    public static Observable<HttpModle<String>> taskCurrentDataSubmit(int dataId, String currentData) {
        return RetrofitService.createShowApi().taskCurrentDataSubmit(dataId, currentData);

    }

    /**
     * 完成任务确认
     */
    public static Observable<HttpModle<String>> taskFinishedConfirm(int taskRecordId) {
        return RetrofitService.createShowApi().taskFinishedConfirm(taskRecordId);
    }

    /**
     * 任务控制点详情
     */
    public static Observable<HttpModle<TaskControlPointDetailBean>> taskControlPointDetail(int taskRecordId) {
        return RetrofitService.createShowApi().taskControlPointDetail(taskRecordId);
    }
}
