package com.tianchuang.ihome_b.http.retrofit;


import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.PropertyListItem;

import java.util.ArrayList;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ShowApi {
	/***
	 * 发送验证码
	 */
	@POST(BizInterface.SEND_AUTH_CODE_URL)
	Observable<HttpModle<String>> sendAuthCode(@Query("mobile") String phone);

	/***
	 * 注册账号
	 * params
	 * mobile:138********  （手机号11位）
	 * passwd:****         （密码6-16位）
	 * smsCode:****        （4位数字)
	 */
	@POST(BizInterface.REGISTER_URL)
	Observable<HttpModle<String>> registerAccount(@Query("mobile") String phone,
												  @Query("passwd") String passwd,
												  @Query("smsCode") String smsCode);

	/***
	 * 登录
	 * params
	 * mobile:138********  （手机号11位）
	 * passwd:******       （密码6-16位）
	 */
	@POST(BizInterface.LOGIN_URL)
	Observable<HttpModle<LoginBean>> login(@Query("mobile") String phone,
										   @Query("passwd") String passwd);

	/***
	 * 找回密码发送短信验证
	 * params
	 * mobile:138********  （手机号11位）
	 */
	@POST(BizInterface.RETRIEVE_URL)
	Observable<HttpModle<String>> retrievePassword(@Query("mobile") String phone);


	/***
	 * 重置密码
	 * params
	 * mobile:138********  （手机号11位）
	 * passwd:******       （密码6-16位）
	 * smsCode:****        （4位数字）
	 */
	@POST(BizInterface.RESET_PWD_URL)
	Observable<HttpModle<String>> resetPassword(@Query("mobile") String phone,
												@Query("passwd") String passwd,
												@Query("smsCode") String smsCode);

	/***
	 * 物业列表
	 * params 无
	 */
	@POST(BizInterface.PROPERTY_LIST_URL)
	Observable<HttpModle<ArrayList<PropertyListItem>>> getPropertyList();

	/***
	 * 设置常用
	 * params   roleId:xx
	 */
	@POST(BizInterface.PROPERTY_SET_OFTEN_URL)
	Observable<HttpModle<String>> setOften(@Query("roleId") int id);

	/***
	 * 常用全部取消
	 * params 无
	 */
	@POST(BizInterface.PROPERTY_CANCEL_OFTEN_URL)
	Observable<HttpModle<String>> allCancel();

}
