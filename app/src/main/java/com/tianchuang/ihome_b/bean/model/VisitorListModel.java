package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.VisitorBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/3.
 * description:访客列表模块
 */


public class VisitorListModel {
	/**
	 * 访客列表
	 */
	public static Observable<HttpModle<VisitorBean>> visitorList(int maxId) {
		LoginBean loginBean = UserUtil.getLoginBean();
		return RetrofitService.createShowApi().visitorList(loginBean.getPropertyCompanyId(), loginBean.getMobile(), maxId);
	}
	/**
	 * 访客列表
	 */
	public static Observable<HttpModle<VisitorBean>> visitorList(int maxId,String mobile) {
		LoginBean loginBean = UserUtil.getLoginBean();
		return RetrofitService.createShowApi().visitorList(loginBean.getPropertyCompanyId(), mobile, maxId);
	}
}
