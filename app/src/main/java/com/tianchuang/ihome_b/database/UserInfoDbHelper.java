package com.tianchuang.ihome_b.database;

import com.tianchuang.ihome_b.TianChuangApplication;
import com.tianchuang.ihome_b.bean.LoginBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Abyss on 2017/2/23.
 * description:用户信息的数据库操作类
 */

public class UserInfoDbHelper {
	/**
	 * 添加用户信息
	 */
	public static Boolean saveUserInfo(LoginBean bean, int userid) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(bean.getId());
		userInfo.setToken(bean.getToken());
		userInfo.setName(bean.getName());
		userInfo.setMobile(bean.getMobile());
		userInfo.setRoleId(bean.getRoleId());
		userInfo.setPropertyCompanyId(bean.getPropertyCompanyId());
		userInfo.setPropertyCompanyName(bean.getPropertyCompanyName());
		userInfo.setDepartmentName(bean.getDepartmentName());
		userInfo.setPositionId(bean.getPositionId());
		userInfo.setDepartmentId(bean.getDepartmentId());
		userInfo.setPositionName(bean.getPositionName());
		TianChuangApplication.application.setUserInfo(userInfo);
		return userInfo.saveIfNotExist("userid = ?", String.valueOf(userid));
	}

	/**
	 * 删除所有用户信息
	 */
	public static Boolean deleteAllUserInfo() {
		return DataSupport.deleteAll(UserInfo.class) >= 1;
	}

	/**
	 * 删除指定用户信息
	 */
	public static Boolean deleteUserInfo(int userid) {
		return DataSupport.deleteAll(UserInfo.class, "userid = ?", String.valueOf(userid)) >= 1;
	}

	/**
	 * 更新指定用户信息
	 */
	public static Boolean updateUserInfo(LoginBean bean, int userid) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(bean.getId());
		userInfo.setToken(bean.getToken());
		userInfo.setName(bean.getName());
		userInfo.setMobile(bean.getMobile());
		userInfo.setRoleId(bean.getRoleId());
		userInfo.setPropertyCompanyId(bean.getPropertyCompanyId());
		userInfo.setPropertyCompanyName(bean.getPropertyCompanyName());
		userInfo.setDepartmentName(bean.getDepartmentName());
		userInfo.setPositionId(bean.getPositionId());
		userInfo.setDepartmentId(bean.getDepartmentId());
		userInfo.setPositionName(bean.getPositionName());
		return userInfo.updateAll("userid = ?", String.valueOf(userid)) == 1;
	}

	/**
	 * 查到指定用户信息
	 */
//	public static UserInfo findUserInfo(int userid) {
//		return DataSupport.where("userid = ?", String.valueOf(userid)).find(UserInfo.class).get(0);
//	}
}
