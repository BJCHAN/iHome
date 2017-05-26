package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.MenuInnerListBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService
import com.tianchuang.ihome_b.utils.MultipartBuilder
import com.tianchuang.ihome_b.utils.UserUtil

import java.io.File
import java.util.ArrayList

import io.reactivex.Observable
import okhttp3.MultipartBody

/**
 * Created by Abyss on 2017/2/23.
 * description:内部报事请求网络
 */

object InnerReportsModel {
    /**
     * 内部报事列表
     */
    fun requestReportsList(propertyCompanyId: Int, maxId: Int): Observable<HttpModle<MenuInnerListBean>> {
        return RetrofitService.createShowApi().reportsList(propertyCompanyId, maxId)
    }

    /**
     * 内部报事提交
     */
    fun requestReportsSubmit(propertyCompanyId: Int, content: String, files: ArrayList<File>): Observable<HttpModle<String>> {
        val parts = MultipartBuilder.filesToMultipartBodyParts(files)
        return RetrofitService.createShowApi().reportsSubmit(propertyCompanyId, content, parts)
    }

    /**
     * 内部报事列表
     */
    fun reportsProcessing(reportId: Int): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().reportsProcessing(UserUtil.getLoginBean().propertyCompanyId, reportId)
    }

    /**
     * 内部报事列表
     */
    fun reportsFinished(reportId: Int): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().reportsFinished(UserUtil.getLoginBean().propertyCompanyId, reportId)
    }

}
