package com.tianchuang.ihome_b.http.retrofit.model;

import com.tianchuang.ihome_b.bean.recyclerview.RobHallListItem;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallRepairDetailItem;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/27.
 * description:
 */

public class RobHallModel {

	/**
	 * 请求抢单大厅列表
	 */
	public static Observable<HttpModle<ArrayList<RobHallListItem>>> requestRobHallList(int propertyCompanyId, int maxid) {
		return RetrofitService.createShowApi().robHallList(propertyCompanyId, maxid);
	}

	/**
	 * 报修故障详情
	 */
	public static Observable<HttpModle<ArrayList<RobHallRepairDetailItem>>> requestRobHallRepairDetail(int repairsId) {
		return RetrofitService.createShowApi().robHallRepairDetail(repairsId);
	}

}
