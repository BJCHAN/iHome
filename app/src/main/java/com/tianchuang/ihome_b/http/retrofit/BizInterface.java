package com.tianchuang.ihome_b.http.retrofit;


import com.tianchuang.ihome_b.Constants;

/**
 * Created by Abyss on 2016/12/29.
 * description:网络请求url
 */

public interface BizInterface {
    /**
     * API接口
     */
    String API = Constants.Http.getUrl();
    //登录接口
    String LOGIN_URL = "login.html";
    //发送验证码
    String SEND_AUTH_CODE_URL ="sendSms.html";
    //注册
    String REGISTER_URL = "register.html";

}
