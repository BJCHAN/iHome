package com.tianchuang.ihome_b.http.retrofit.model;

import com.tianchuang.ihome_b.bean.ComplainDetailBean;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.bean.MyOrderListBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/3.
 * description:我的订单
 */


public class MyOrderModel {

	/**
	 * 未处理投诉列表
	 */
	public static Observable<HttpModle<MyOrderListBean>> myOrderUnfinished(int propertyCompanyId, int maxid) {
		return RetrofitService.createShowApi().myOrderUnfinished(propertyCompanyId, maxid);
	}

	/**
	 * 已处理投诉列表
	 */
	public static Observable<HttpModle<MyOrderListBean>> myOrderfinished(int propertyCompanyId, int maxid) {
		return RetrofitService.createShowApi().myOrderfinished(propertyCompanyId, maxid);
	}

	/**
	 * 投诉详细
	 */
	public static Observable<HttpModle<MyOrderDetailBean>> myOrderDetail(int complaintsId) {
		return RetrofitService.createShowApi().myOrderDetail(complaintsId);
	}
//
//	/**
//	 * 投诉回复
//	 */
//	public static Observable<HttpModle<String>> complainReply(int complaintsId, String content) {
//		return RetrofitService.createShowApi().complainReply(complaintsId, content).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//	}
}
