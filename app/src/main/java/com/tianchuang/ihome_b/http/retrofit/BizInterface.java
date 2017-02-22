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
	String SEND_AUTH_CODE_URL = "sendSms.html";
	//注册
	String REGISTER_URL = "register.html";
	//找回密码发送短信验证
	String RETRIEVE_URL = "password/reset/sendSms.html";
	//注册
	String RESET_PWD_URL = "password/reset.html";
	//物业列表
	String PROPERTY_LIST_URL = "property/role/join/list.html";
	//设为常用
	String PROPERTY_SET_OFTEN_URL = "property/role/often/set.html";
	//全部取消
	String PROPERTY_CANCEL_OFTEN_URL = "property/role/often/cancel.html";

}
