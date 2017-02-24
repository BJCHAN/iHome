package com.tianchuang.ihome_b.http.retrofit.model;

import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/23.
 * description:内部报事请求网络
 */

public class InnerReportsModel {
	/**
	 * 内部报事列表
	 */
	public static Observable<HttpModle<ArrayList<MenuInnerReportsItemBean>>> requestReportsList(int propertyCompanyId) {
		return RetrofitService.createShowApi().reportsList(propertyCompanyId);
	}

}
