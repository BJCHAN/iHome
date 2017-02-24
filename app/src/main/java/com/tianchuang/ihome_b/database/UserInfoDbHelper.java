package com.tianchuang.ihome_b.database;

import com.tianchuang.ihome_b.bean.LoginBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Abyss on 2017/2/23.
 * description:用户信息的数据库操作类
 */

public class UserInfoDbHelper {
	public static Boolean saveUserInfo(LoginBean bean) {
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
		return userInfo.save();
	}

	public static Boolean deleteAllUserInfo() {
		return DataSupport.deleteAll(UserInfo.class) >= 1;
	}

	public static Boolean updateUserInfo(LoginBean bean) {
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
		return userInfo.update(1) == 1;
	}
}
