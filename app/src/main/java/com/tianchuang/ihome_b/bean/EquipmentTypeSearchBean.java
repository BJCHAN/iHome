package com.tianchuang.ihome_b.bean;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/1.
 * description:设备类型查询
 */

public class EquipmentTypeSearchBean implements Serializable {


	/**
	 * name : 空调
	 * id : 1
	 * propertyCompanyId : 1
	 * createdDate : 1487324121
	 * lastUpdatedDate : 1487324121
	 */

	private String name;
	private int id;
	private int propertyCompanyId;
	private int createdDate;
	private int lastUpdatedDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPropertyCompanyId() {
		return propertyCompanyId;
	}

	public void setPropertyCompanyId(int propertyCompanyId) {
		this.propertyCompanyId = propertyCompanyId;
	}

	public int getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(int createdDate) {
		this.createdDate = createdDate;
	}

	public int getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(int lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
