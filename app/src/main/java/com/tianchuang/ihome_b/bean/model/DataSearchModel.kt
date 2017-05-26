package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.BuildingRoomListBean
import com.tianchuang.ihome_b.bean.CarDetailBean
import com.tianchuang.ihome_b.bean.DataBuildingSearchBean
import com.tianchuang.ihome_b.bean.EquipmentDetailBean
import com.tianchuang.ihome_b.bean.EquipmentSearchListItemBean
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean
import com.tianchuang.ihome_b.bean.OwnerDetailBean
import com.tianchuang.ihome_b.bean.TaskAreaListBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService
import com.tianchuang.ihome_b.utils.UserUtil

import java.util.ArrayList

import io.reactivex.Observable

/**
 * Created by Abyss on 2017/2/14.
 * description:资料查询模块请求网络
 */

object DataSearchModel {
    /**
     * 楼宇查询
     */
    fun requestBuildingSearch(): Observable<HttpModle<ArrayList<DataBuildingSearchBean>>> {
        return RetrofitService.createShowApi().getBuildingSearch(UserUtil.getLoginBean().propertyCompanyId)
    }

    /**
     * 设备类型查询
     */
    fun requestEquipmentTypeSearch(): Observable<HttpModle<ArrayList<EquipmentTypeSearchBean>>> {
        return RetrofitService.createShowApi().equipmentTypeSearch(UserUtil.getLoginBean().propertyCompanyId)
    }

    /**
     * 设备列表查询
     */
    fun requestEquipmentListSearch(type: Int): Observable<HttpModle<ArrayList<EquipmentSearchListItemBean>>> {
        return RetrofitService.createShowApi().equipmentListSearch(UserUtil.getLoginBean().propertyCompanyId, type)
    }

    /**
     * 设备详情
     */
    fun equipmentDetail(equipmentId: Int): Observable<HttpModle<EquipmentDetailBean>> {
        return RetrofitService.createShowApi().equipmentDetail(equipmentId)
    }

    /**
     * 车辆详情
     */
    fun carDetail(carNum: String): Observable<HttpModle<CarDetailBean>> {
        return RetrofitService.createShowApi().carDetail(UserUtil.getLoginBean().propertyCompanyId, carNum)
    }

    /**
     * 小区列表
     */
    fun requestAreaList(): Observable<HttpModle<ArrayList<TaskAreaListBean>>> {
        return RetrofitService.createShowApi().requestAreaList(UserUtil.getLoginBean().propertyCompanyId)
    }

    /**
     * 楼宇列表
     */
    fun requestBuildingList(areaId: Int): Observable<HttpModle<ArrayList<TaskAreaListBean.CellListBean>>> {
        return RetrofitService.createShowApi().requestBuildingList(areaId)
    }

    /**
     * 单元列表
     */
    fun requestUnitList(buildingId: Int): Observable<HttpModle<ArrayList<TaskAreaListBean.CellListBean.UnitListBean>>> {
        return RetrofitService.createShowApi().requestUnitList(buildingId)
    }

    /**
     * 房间列表
     */
    fun requestRoomList(unitId: Int): Observable<HttpModle<ArrayList<BuildingRoomListBean>>> {
        return RetrofitService.createShowApi().requestRoomList(unitId)
    }

    /**
     * 业主详情
     */
    fun requestOwnerDetail(roomId: Int): Observable<HttpModle<OwnerDetailBean>> {
        return RetrofitService.createShowApi().requestOwnerDetail(roomId)
    }
}
