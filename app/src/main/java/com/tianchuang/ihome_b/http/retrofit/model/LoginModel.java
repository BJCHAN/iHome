package com.tianchuang.ihome_b.http.retrofit.model;

import com.tianchuang.ihome_b.bean.BaseHttpBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Abyss on 2017/2/14.
 * description:登录模块请求网络
 */

public class LoginModel {
	/**
	 * 发送验证码
	 */
	public static Observable<HttpModle<String>> requestAuthCode(String phone) {
		return RetrofitService.createShowApi().sendAuthCode(phone);
	}
}
