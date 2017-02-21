package com.tianchuang.ihome_b.http.retrofit.model;

import com.tianchuang.ihome_b.bean.PropertyListItem;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/14.
 * description:物业模块请求网络
 */

public class PropertyModel {
	/**
	 * 物业列表
	 */
	public static Observable<HttpModle<ArrayList<PropertyListItem>>> requestPropertyList() {
		return RetrofitService.createShowApi().getPropertyList();
	}

	/**
	 * 设置常用
	 */
	public static Observable<HttpModle<String>> requestSetOften(int id) {
		return RetrofitService.createShowApi().setOften(id);
	}
	/**
	 * 全部取消
	 */
	public static Observable<HttpModle<String>> requestAllCancel() {
		return RetrofitService.createShowApi().allCancel();
	}

}
