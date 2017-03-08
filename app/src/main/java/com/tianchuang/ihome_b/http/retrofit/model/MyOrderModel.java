package com.tianchuang.ihome_b.http.retrofit.model;

import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.bean.MyOrderListBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by Abyss on 2017/3/3.
 * description:我的订单
 */


public class MyOrderModel {

	/**
	 * 未处理订单列表
	 */
	public static Observable<HttpModle<MyOrderListBean>> myOrderUnfinished(int propertyCompanyId, int maxid) {
		return RetrofitService.createShowApi().myOrderUnfinished(propertyCompanyId, maxid);
	}

	/**
	 * 已处理订单列表
	 */
	public static Observable<HttpModle<MyOrderListBean>> myOrderfinished(int propertyCompanyId, int maxid) {
		return RetrofitService.createShowApi().myOrderfinished(propertyCompanyId, maxid);
	}

	/**
	 * 订单详细
	 */
	public static Observable<HttpModle<MyOrderDetailBean>> myOrderDetail(int complaintsId) {
		return RetrofitService.createShowApi().myOrderDetail(complaintsId);
	}

	/**
	 * 维修确认
	 */
	public static Observable<HttpModle<String>> confirmOrder(int repairsId, String content, List<MultipartBody.Part> parts) {
		return RetrofitService.createShowApi().confirmOrder(repairsId, parts, content);
	}

	/**
	 * 材料费列表
	 */
	public static Observable<HttpModle<ArrayList<MaterialListItemBean>>> materialList() {
		return RetrofitService.createShowApi().materialList(UserUtil.getLoginBean().getPropertyCompanyId());
	}

	/**
	 * 人工费种类
	 */
	public static Observable<HttpModle<ArrayList<ChargeTypeListItemBean>>> chargeTypeList() {
		return RetrofitService.createShowApi().chargeTypeList(UserUtil.getLoginBean().getPropertyCompanyId());
	}

	/**
	 * 提交费用明细
	 */
	public static Observable<HttpModle<String>> submitFeeList(int repairId, int offline, String json) {
		return RetrofitService.createShowApi().submitFeeList(repairId, offline, json);
	}
}
