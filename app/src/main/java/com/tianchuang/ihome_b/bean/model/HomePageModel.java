package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.HomePageBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;
import java.util.Map;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/21.
 * description:主页模块
 */

public class HomePageModel {
    /**
     * 主页列表
     */
    public static Observable<HttpModle<HomePageBean>> homePageList() {
        return RetrofitService.createShowApi().homePageList(UserUtil.getLoginBean().getPropertyCompanyId());
    }

    /**
     * 请求解析二维码
     * taskId:xxxxxx                     //仅点击执行任务进行的扫描需传递此参数
     * propertyCompanyId:xxx             //物业公司ID（对应在物业列表接口中propertyCompanyId）
     * code:xxx                          //二维码解析到的code
     */
    public static Observable<HttpModle<ArrayList<QrCodeBean>>> requestQrCode(Map<String, String> map) {
        return RetrofitService.createShowApi().requestQrCode(map);
    }
}
