package com.tianchuang.ihome_b.utils;

import android.text.TextUtils;

import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.recyclerview.PropertyListItemBean;
import com.tianchuang.ihome_b.database.UserInfo;
import com.tianchuang.ihome_b.database.UserInfoDbHelper;

/**
 * Created by Abyss on 2017/2/15.
 * description:记载用户信息的工具类
 */

public class UserUtil {
	private static final String TOKEN = "token";
	private static final String USERID = "userId";
	private static String token = null;
	private static int userId = 0;
	private static LoginBean loginBean = null;//内存中存在的loginBean临时用

	public static LoginBean getLoginBean() {
		if (loginBean == null) {//内存中没有去数据库取
			UserInfo userInfo = UserInfoDbHelper.findUserInfo(getUserid());
			if (userInfo != null) {
				loginBean = new LoginBean();
				loginBean.setId(userInfo.getUserId());
				loginBean.setToken(userInfo.getToken());
				loginBean.setName(userInfo.getName());
				loginBean.setMobile(userInfo.getMobile());
				loginBean.setRoleId(userInfo.getRoleId());
				loginBean.setPropertyCompanyId(userInfo.getPropertyCompanyId());
				loginBean.setPropertyCompanyName(userInfo.getPropertyCompanyName());
				loginBean.setDepartmentName(userInfo.getDepartmentName());
				loginBean.setPositionId(userInfo.getPositionId());
				loginBean.setDepartmentId(userInfo.getDepartmentId());
				loginBean.setPositionName(userInfo.getPositionName());
			}
		}
		return loginBean;
	}

	public static void setLoginBean(LoginBean loginBean) {//设置临时用
		UserUtil.loginBean = loginBean;
	}

	//退出登录
	public static void logout() {
		UserInfoDbHelper.deleteUserInfo(getUserid());//删除指定用户信息
		setToken(null);
		setLoginBean(null);
		setUserId(0);
	}

	//登录
	public static void login(LoginBean s) {
		UserUtil.setToken(s.getToken());
		UserUtil.setUserId(s.getId());//userid一定不能为空
		UserUtil.setLoginBean(s);//储存在内存中
		UserInfoDbHelper.saveUserInfo(s, UserUtil.getUserid());//储存在数据库
	}

	public static void setToken(String token) {
		Utils.getSpUtils().put(TOKEN, token);//本地中添加
		UserUtil.token = null;//清除内存中的

	}

	public static String getToken() {
		if (TextUtils.isEmpty(token)) {//先在内存读取
			token = Utils.getSpUtils().getString(TOKEN);
		}
		return token;
	}

	public static void setUserId(int userid) {
		Utils.getSpUtils().put(USERID, userid);
		UserUtil.userId = 0;
	}

	public static int getUserid() {
		if (userId == 0) {//先在内存读取
			userId = Utils.getSpUtils().getInt(USERID);
		}
		return userId;
	}

	//用户是否已经登录
	public static Boolean isLogin() {
		return !TextUtils.isEmpty(getToken());
	}

	public static LoginBean propertyListItemBeanToLoginBean(PropertyListItemBean bean) {
		LoginBean loginBean = new LoginBean();
		loginBean.setId(getUserid());
		loginBean.setToken(getToken());
		loginBean.setName(bean.getEmployeeName());
		loginBean.setMobile(UserInfoDbHelper.findUserInfo(getUserid()).getMobile());
		loginBean.setRoleId(bean.getId());
		loginBean.setPropertyCompanyId(bean.getPropertyCompanyId());
		loginBean.setPropertyCompanyName(bean.getPropertyCompanyName());
		loginBean.setDepartmentName(bean.getDepartmentName());
		loginBean.setPositionId(bean.getPositionId());
		loginBean.setDepartmentId(bean.getDepartmentId());
		loginBean.setPositionName(bean.getPositionName());
		return loginBean;
	}
}
