package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.ComplainDetailBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestProcessedBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestUntratedBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wujian on 2017/3/1.
 */

public class ComplainSuggestModel {

	/**
	 * 未处理投诉列表
	 */
	public static Observable<HttpModle<ComplainSuggestUntratedBean>> complainSuggestUntrated(int propertyCompanyId, int maxid) {
		return RetrofitService.createShowApi().complainSuggestUntrated(propertyCompanyId, maxid);
	}

	/**
	 * 已处理投诉列表
	 */
	public static Observable<HttpModle<ComplainSuggestProcessedBean>> complainSuggestProcessed(int propertyCompanyId, int maxid) {
		return RetrofitService.createShowApi().complainSuggestProcessed(propertyCompanyId, maxid);
	}

	/**
	 * 投诉详细
	 */
	public static Observable<HttpModle<ComplainDetailBean>> complainDetail(int complaintsId) {
		return RetrofitService.createShowApi().complainDetail(complaintsId);
	}

	/**
	 * 投诉详细
	 */
	public static Observable<HttpModle<String>> complainReply(int complaintsId, String content) {
		return RetrofitService.createShowApi().complainReply(complaintsId, content).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}
}
