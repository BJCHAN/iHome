package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.MenuInnerListBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.MultipartBuilder;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by Abyss on 2017/2/23.
 * description:内部报事请求网络
 */

public class InnerReportsModel {
    /**
     * 内部报事列表
     */
    public static Observable<HttpModle<MenuInnerListBean>> requestReportsList(int propertyCompanyId, int maxId) {
        return RetrofitService.createShowApi().reportsList(propertyCompanyId, maxId);
    }

    /**
     * 内部报事提交
     */
    public static Observable<HttpModle<String>> requestReportsSubmit(int propertyCompanyId, String content, ArrayList<File> files) {
        List<MultipartBody.Part> parts = MultipartBuilder.filesToMultipartBodyParts(files);
        return RetrofitService.createShowApi().reportsSubmit(propertyCompanyId, content, parts);
    }

    /**
     * 内部报事列表
     */
    public static Observable<HttpModle<String>> reportsProcessing(int reportId) {
        return RetrofitService.createShowApi().reportsProcessing(UserUtil.getLoginBean().getPropertyCompanyId(), reportId);
    }

    /**
     * 内部报事列表
     */
    public static Observable<HttpModle<String>> reportsFinished(int reportId) {
        return RetrofitService.createShowApi().reportsFinished(UserUtil.getLoginBean().getPropertyCompanyId(), reportId);
    }

}
