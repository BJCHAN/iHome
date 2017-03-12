package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.DataBuildingSearchBean;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;
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

}
