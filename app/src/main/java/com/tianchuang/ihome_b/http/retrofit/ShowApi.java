package com.tianchuang.ihome_b.http.retrofit;


import com.tianchuang.ihome_b.bean.LoginBean;

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
	Observable<HttpModle<LoginBean>> Login(@Query("mobile") String phone,
										   @Query("passwd") String passwd);

}
