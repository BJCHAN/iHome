package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.ComplainDetailBean
import com.tianchuang.ihome_b.bean.ComplainSuggestProcessedBean
import com.tianchuang.ihome_b.bean.ComplainSuggestUntratedBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wujian on 2017/3/1.
 */

object ComplainSuggestModel {

    /**
     * 未处理投诉列表
     */
    fun complainSuggestUntrated(propertyCompanyId: Int, maxid: Int): Observable<HttpModle<ComplainSuggestUntratedBean>> {
        return RetrofitService.createShowApi().complainSuggestUntrated(propertyCompanyId, maxid)
    }

    /**
     * 已处理投诉列表
     */
    fun complainSuggestProcessed(propertyCompanyId: Int, maxid: Int): Observable<HttpModle<ComplainSuggestProcessedBean>> {
        return RetrofitService.createShowApi().complainSuggestProcessed(propertyCompanyId, maxid)
    }

    /**
     * 投诉详细
     */
    fun complainDetail(complaintsId: Int): Observable<HttpModle<ComplainDetailBean>> {
        return RetrofitService.createShowApi().complainDetail(complaintsId)
    }

    /**
     * 投诉详细
     */
    fun complainReply(complaintsId: Int, content: String): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().complainReply(complaintsId, content).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
