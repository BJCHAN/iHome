package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.FormTypeListBean
import com.tianchuang.ihome_b.bean.MyFormDetailBean
import com.tianchuang.ihome_b.bean.MyFormListBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService
import com.tianchuang.ihome_b.utils.UserUtil

import java.util.HashMap

import io.reactivex.Observable
import okhttp3.MultipartBody

/**
 * Created by Abyss on 2017/3/10.
 * description:表单模块
 */

object FormModel {

    /**
     * 表单类型列表
     */
    fun formTypeList(maxId: Int): Observable<HttpModle<FormTypeListBean>> {
        return RetrofitService.createShowApi().formTypeList(UserUtil.getLoginBean().propertyCompanyId, maxId)
    }

    /**
     * 表单提交
     */
    fun formSubmit(formId: Int , map: HashMap<String, String>, parts: List<MultipartBody.Part>): Observable<HttpModle<String>> {
        map.put("propertyCompanyId", UserUtil.getLoginBean().propertyCompanyId.toString())
        map.put("formTypeId", formId.toString())
        return RetrofitService.createShowApi().formSubmit(map, parts)
    }

    /**
     * 我的表单列表
     */
    fun myFormList(maxId: Int): Observable<HttpModle<MyFormListBean>> {
        return RetrofitService.createShowApi().myFormList(UserUtil.getLoginBean().propertyCompanyId, maxId)
    }

    /**
     * 我的表单详情
     */
    fun myFormDetail(formId: Int): Observable<HttpModle<MyFormDetailBean>> {
        return RetrofitService.createShowApi().myFormDetail(formId)
    }
}
