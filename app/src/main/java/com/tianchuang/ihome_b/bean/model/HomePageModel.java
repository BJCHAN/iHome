package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.HomePageBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/21.
 * description:主页模块
 */

public class HomePageModel {
    /**
     * 任务控制点详情
     */
    public static Observable<HttpModle<HomePageBean>> homePageList() {
        return RetrofitService.createShowApi().homePageList(UserUtil.getLoginBean().getPropertyCompanyId());
    }
}
