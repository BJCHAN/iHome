package com.tianchuang.ihome_b.http.retrofit.model;

import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.MultipartBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
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

	/**
	 * 内部报事提交
	 */
	public static Observable<HttpModle<String>> requestReportsSubmit(int propertyCompanyId, String content, ArrayList<File> files) {
		List<MultipartBody.Part> parts = MultipartBuilder.filesToMultipartBodyParts(files);
		return RetrofitService.createShowApi().reportsSubmit(propertyCompanyId, content,parts);
	}

}
