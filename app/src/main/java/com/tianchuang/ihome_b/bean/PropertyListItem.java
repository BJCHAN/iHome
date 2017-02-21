package com.tianchuang.ihome_b.bean;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/2/9.
 * description:物业列表的item
 */

public class PropertyListItem implements Serializable {

	private int id;
	private int employeeId;
	private int propertyCompanyId;
	private String propertyCompanyName;
	private int departmentId;
	private String departmentName;
	private int positionId;
	private String positionName;
	private Boolean oftenUse;

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
}
