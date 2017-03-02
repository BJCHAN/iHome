package com.tianchuang.ihome_b.bean.recyclerview;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:故障报修详情列表
 */

public class RobHallRepairDetailListBean implements Serializable {


	/**
	 * id : 2
	 * typeName : 进水
	 * repairsDataVos : [{"id":4,"repairsId":2,"typeId":1,"fieldId":1,"fieldType":1,"fieldKey":"fieldKey1","fieldValue":"2报修文字描述内容报修文字描述内容报修文字描述内容"},{"id":5,"repairsId":2,"typeId":1,"fieldId":2,"fieldType":2,"fieldKey":"fieldKey2","fieldValue":"异常"},{"id":6,"repairsId":2,"typeId":1,"fieldId":3,"fieldType":3,"fieldKey":"fieldKey3","fieldValue":"http://api-staff-test.hecaifu.com/filesupload/internalreport/34eb961c-fef3-4d2b-9b7c-6c2ea0faea75.jpg"},{"id":7,"repairsId":2,"typeId":1,"fieldId":3,"fieldType":3,"fieldKey":"fieldKey3","fieldValue":"http://api-staff-test.hecaifu.com/filesupload/internalreport/34eb961c-fef3-4d2b-9b7c-6c2ea0faea75.jpg"}]
	 * status : 0
	 * createdDate : 1487324326
	 */

	private int id;
	private String typeName;
	private int status;
	private int createdDate;
	private List<DetailMultiItem> repairsDataVos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(int createdDate) {
		this.createdDate = createdDate;
	}

	public List<DetailMultiItem> getRepairsDataVos() {
		return repairsDataVos;
	}

	public void setRepairsDataVos(List<DetailMultiItem> repairsDataVos) {
		this.repairsDataVos = repairsDataVos;
	}


}
