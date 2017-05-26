package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.PropertyListItemBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService

import java.util.ArrayList

import io.reactivex.Observable


/**
 * Created by Abyss on 2017/2/14.
 * description:物业模块请求网络
 */

object PropertyModel {
    /**
     * 物业列表
     */
    fun requestPropertyList(): Observable<HttpModle<ArrayList<PropertyListItemBean>>> {
        return RetrofitService.createShowApi().propertyList
    }

    /**
     * 设置常用
     */
    fun requestSetOften(id: Int): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().setOften(id)
    }

    /**
     * 物业删除
     */
    fun propertyDelete(id: Int): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().propertyDelete(id)
    }

}
