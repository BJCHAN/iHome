package com.tianchuang.ihome_b.database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Abyss on 2017/2/23.
 * description:用户信息
 */

public class UserInfo extends DataSupport {

	/**
	 * id : 2
	 * name : 李斯
	 * mobile : 18158171066
	 * token : c9816495-798e-4d2b-bd51-440100709724
	 * roleId : 0
	 * propertyCompanyId : 1
	 * propertyCompanyName : 海创园
	 * departmentId : 1
	 * departmentName : 工程维修部
	 * positionId : 1
	 * positionName : 水电维修工
	 * propertyEnable
	 */
	@Column(unique = true)
	private int userId;
	@Column(defaultValue = "unknown")
	private String name;
	private String mobile;
	private String token;
	private int roleId;
	private int propertyCompanyId;
	private String propertyCompanyName;
	private int departmentId;
	private String departmentName;
	private int positionId;
	private String positionName;
	private boolean propertyEnable;

	public boolean getPropertyEnable() {
		return propertyEnable;
	}

	public void setPropertyEnable(boolean propertyEnable) {
		this.propertyEnable = propertyEnable;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getPropertyCompanyId() {
		return propertyCompanyId;
	}

	public void setPropertyCompanyId(int propertyCompanyId) {
		this.propertyCompanyId = propertyCompanyId;
	}

	public String getPropertyCompanyName() {
		return propertyCompanyName;
	}

	public void setPropertyCompanyName(String propertyCompanyName) {
		this.propertyCompanyName = propertyCompanyName;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
