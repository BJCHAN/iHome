package com.tianchuang.ihome_b.http.retrofit;


import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ShowApi {
	/***
	 * 发送验证码
	 */
	@POST(BizInterface.SEND_AUTH_CODE_URL)
	Observable<HttpModle<String>> sendAuthCode(@Query("mobile") String phone);

}
