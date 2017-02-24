package com.tianchuang.ihome_b.bean.recyclerview;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/2/9.
 * description:
 */

public class MenuInnerReportsItemBean implements Serializable{

	/**
	 * id : 1
	 * propertyEmployeeRoleVo : {"id":1,"employeeId":2,"employeeName":"李斯","propertyCompanyId":1,"propertyCompanyName":"海创园","departmentId":1,"departmentName":"工程维修部","positionId":1,"positionName":"水电维修工","oftenUse":true}
	 * content : 测试未处理
	 * photo1Url : http://api-staff-test.hecaifu.com/internalreport/88f7af0d-1cb8-4a6c-8f94-8f9492138fc2.png
	 * photo2Url : http://api-staff-test.hecaifu.com/internalreport/88f7af0d-1cb8-4a6c-8f94-8f9492138fc2.png
	 * photo3Url : http://api-staff-test.hecaifu.com/internalreport/88f7af0d-1cb8-4a6c-8f94-8f9492138fc2.png
	 * statusMsg : 未处理
	 * createdDate : 1487746560
	 * lastUpdatedDae : 1487746560
	 */

	private int id;
	private PropertyListItemBean propertyEmployeeRoleVo;
	private String content;
	private String photo1Url;
	private String photo2Url;
	private String photo3Url;
	private String statusMsg;
	private int createdDate;
	private int lastUpdatedDae;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PropertyListItemBean getPropertyEmployeeRoleVo() {
		return propertyEmployeeRoleVo;
	}

	public void setPropertyEmployeeRoleVo(PropertyListItemBean propertyEmployeeRoleVo) {
		this.propertyEmployeeRoleVo = propertyEmployeeRoleVo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhoto1Url() {
		return photo1Url;
	}

	public void setPhoto1Url(String photo1Url) {
		this.photo1Url = photo1Url;
	}

	public String getPhoto2Url() {
		return photo2Url;
	}

	public void setPhoto2Url(String photo2Url) {
		this.photo2Url = photo2Url;
	}

	public String getPhoto3Url() {
		return photo3Url;
	}

	public void setPhoto3Url(String photo3Url) {
		this.photo3Url = photo3Url;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public int getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(int createdDate) {
		this.createdDate = createdDate;
	}

	public int getLastUpdatedDae() {
		return lastUpdatedDae;
	}

	public void setLastUpdatedDae(int lastUpdatedDae) {
		this.lastUpdatedDae = lastUpdatedDae;
	}

}
