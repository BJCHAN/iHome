package com.tianchuang.ihome_b.http.retrofit;


import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerListBean;
import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.recyclerview.PropertyListItemBean;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallListBean;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallListItem;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallRepairDetailItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
	Observable<HttpModle<ArrayList<PropertyListItemBean>>> getPropertyList();

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

	/***
	 * 内部报事列表
	 * params
	 * propertyCompanyId:xxx                   应物业公司ID-对应物业列表物业公司ID
	 */
	@POST(BizInterface.INNER_REPORTS_LIST_URL)
	Observable<HttpModle<MenuInnerListBean>> reportsList(@Query("propertyCompanyId") int propertyCompanyId,
														 @Query("maxId") int maxId);

	/***
	 * 内部报事提交
	 * params
	 * propertyCompanyId:xxx                   应物业公司ID-对应物业列表物业公司ID
	 * content:xxx                             报告内容（文本内容）
	 * 图片文件
	 */
	@Multipart
	@POST(BizInterface.INNER_REPORTS_SUBMIT_URL)
	Observable<HttpModle<String>> reportsSubmit(@Query("propertyCompanyId") int propertyCompanyId,
												@Query("content") String content,
												@Part() List<MultipartBody.Part> parts);

	/***
	 * 抢单大厅列表
	 * params
	 * propertyCompanyId:xxx       应物业公司ID-对应物业列表物业公司ID
	 * maxId:xxx
	 */
	@POST(BizInterface.ROB_HALL_LIST_URL)
	Observable<HttpModle<RobHallListBean>>  robHallList(@Query("propertyCompanyId") int propertyCompanyId,
														@Query("maxId") int maxId);

	/***
	 * 报修故障详情
	 * params
	 * repairsId:xxx               报修ID-对应报修抢单列表ID
	 */
	@POST(BizInterface.ROB_HALL_DETAIL_URL)
	Observable<HttpModle<ArrayList<RobHallRepairDetailItem>>> robHallRepairDetail(@Query("repairsId") int repairsId);

}
