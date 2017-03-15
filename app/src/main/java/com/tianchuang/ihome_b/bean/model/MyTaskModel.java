package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.MyTaskUnderWayListBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/15.
 * description:我的任务模块
 */

public class MyTaskModel {
    /**
     * 我的任务进行中列表
     */
    public static Observable<HttpModle<MyTaskUnderWayListBean>> myTaskUnderWayList(int maxId) {
        return RetrofitService.createShowApi().myTaskUnderWayList(UserUtil.getLoginBean().getPropertyCompanyId(), maxId);
    }

}
