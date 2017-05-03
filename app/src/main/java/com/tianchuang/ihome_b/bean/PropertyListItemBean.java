package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:物业列表的item
 */

public class PropertyListItemBean implements Serializable {
	/**
	 * "id": 1,
	 * "employeeId": 2,
	 * "employeeName": "李斯",
	 * "propertyCompanyId": 1,
	 * "propertyCompanyName": "海创园",
	 * "departmentId": 1,
	 * "departmentName": "工程维修部",
	 * "positionId": 1,
	 * "positionName": "水电维修工",
	 * "oftenUse": true
	 * propertyEnable
	 * menuList
	 * noticeCount 小红点
	 */
	private int id;
	private int employeeId;
	private int propertyCompanyId;
	private String propertyCompanyName;
	private String employeeName;
	private int departmentId;
	private String departmentName;
	private int positionId;
	private String positionName;
	private Boolean oftenUse;
	private List<Integer> menuList;
	private int noticeCount;
	private boolean isCurrentProperty=false;//是否是当前的物业,默认不是

	public boolean isCurrentProperty() {
		return isCurrentProperty;
	}

	public void setCurrentProperty(boolean currentProperty) {
		isCurrentProperty = currentProperty;
	}

	public int getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(int noticeCount) {
		this.noticeCount = noticeCount;
	}

	public List<Integer> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Integer> menuList) {
		this.menuList = menuList;
	}

	public boolean getPropertyEnable() {
		return propertyEnable;
	}

	public void setPropertyEnable(boolean propertyEnable) {
		this.propertyEnable = propertyEnable;
	}

	private boolean propertyEnable;
	public Boolean getOftenUse() {
		return oftenUse;
	}

	public void setOftenUse(Boolean oftenUse) {
		this.oftenUse = oftenUse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
}
