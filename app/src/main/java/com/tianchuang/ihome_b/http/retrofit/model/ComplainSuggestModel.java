package com.tianchuang.ihome_b.http.retrofit.model;

import com.tianchuang.ihome_b.bean.ComplainSuggestProcessedBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestUntratedBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;

import rx.Observable;

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
}
