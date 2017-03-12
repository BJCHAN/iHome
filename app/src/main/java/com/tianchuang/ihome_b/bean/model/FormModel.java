package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.FormTypeListBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by Abyss on 2017/3/10.
 * description:表单模块
 */

public class FormModel {

    /**
     * 表单类型列表
     */
    public static Observable<HttpModle<FormTypeListBean>> formTypeList(int maxId) {
        return RetrofitService.createShowApi().formTypeList(UserUtil.getLoginBean().getPropertyCompanyId(), maxId);
    }

    /**
     * 表单提交
     */
    public static Observable<HttpModle<String>> formSubmit(int formId, HashMap<String, String> map, List<MultipartBody.Part> parts) {
//        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
//            parts.add(MultipartBody.Part.createFormData(stringStringEntry.getKey(), stringStringEntry.getValue()));
//        }
//        map.clear();
        map.put("propertyCompanyId", String.valueOf(UserUtil.getLoginBean().getPropertyCompanyId()));
        map.put("formTypeId", String.valueOf(formId));
//        parts.add(MultipartBody.Part.createFormData("propertyCompanyId", String.valueOf(UserUtil.getLoginBean().getPropertyCompanyId())));
//        parts.add(MultipartBody.Part.createFormData("formTypeId", String.valueOf(formId)));
        return RetrofitService.createShowApi().formSubmit(map, parts);
    }
}