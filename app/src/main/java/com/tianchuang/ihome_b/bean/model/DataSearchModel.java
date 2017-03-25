package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.BuildingRoomListBean;
import com.tianchuang.ihome_b.bean.CarDetailBean;
import com.tianchuang.ihome_b.bean.DataBuildingSearchBean;
import com.tianchuang.ihome_b.bean.EquipmentDetailBean;
import com.tianchuang.ihome_b.bean.EquipmentSearchListItemBean;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;
import com.tianchuang.ihome_b.bean.OwnerDetailBean;
import com.tianchuang.ihome_b.bean.TaskAreaListBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/14.
 * description:资料查询模块请求网络
 */

public class DataSearchModel {
    /**
     * 楼宇查询
     */
    public static Observable<HttpModle<ArrayList<DataBuildingSearchBean>>> requestBuildingSearch() {
        return RetrofitService.createShowApi().getBuildingSearch(UserUtil.getLoginBean().getPropertyCompanyId());
    }

    /**
     * 设备类型查询
     */
    public static Observable<HttpModle<ArrayList<EquipmentTypeSearchBean>>> requestEquipmentTypeSearch() {
        return RetrofitService.createShowApi().equipmentTypeSearch(UserUtil.getLoginBean().getPropertyCompanyId());
    }

    /**
     * 设备列表查询
     */
    public static Observable<HttpModle<ArrayList<EquipmentSearchListItemBean>>> requestEquipmentListSearch(int type, String place) {
        return RetrofitService.createShowApi().equipmentListSearch(UserUtil.getLoginBean().getPropertyCompanyId(), type, place);
    }

    /**
     * 设备详情
     */
    public static Observable<HttpModle<EquipmentDetailBean>> equipmentDetail(int equipmentId) {
        return RetrofitService.createShowApi().equipmentDetail(equipmentId);
    }

    /**
     * 车辆详情
     */
    public static Observable<HttpModle<CarDetailBean>> carDetail(String carNum) {
        return RetrofitService.createShowApi().carDetail(UserUtil.getLoginBean().getPropertyCompanyId(), carNum);
    }

    /**
     * 小区列表
     */
    public static Observable<HttpModle<ArrayList<TaskAreaListBean>>> requestAreaList() {
        return RetrofitService.createShowApi().requestAreaList(UserUtil.getLoginBean().getPropertyCompanyId());
    }

    /**
     * 楼宇列表
     */
    public static Observable<HttpModle<ArrayList<TaskAreaListBean.CellListBean>>> requestBuildingList(int areaId) {
        return RetrofitService.createShowApi().requestBuildingList(areaId);
    }

    /**
     * 单元列表
     */
    public static Observable<HttpModle<ArrayList<TaskAreaListBean.CellListBean.UnitListBean>>> requestUnitList(int buildingId) {
        return RetrofitService.createShowApi().requestUnitList(buildingId);
    }

    /**
     * 房间列表
     */
    public static Observable<HttpModle<ArrayList<BuildingRoomListBean>>> requestRoomList(int unitId) {
        return RetrofitService.createShowApi().requestRoomList(unitId);
    }

    /**
     * 业主详情
     */
    public static Observable<HttpModle<OwnerDetailBean>> requestOwnerDetail(int roomId) {
        return RetrofitService.createShowApi().requestOwnerDetail(roomId);
    }
}
