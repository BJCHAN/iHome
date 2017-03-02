package com.tianchuang.ihome_b.bean.recyclerview;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/2/9.
 * description:抢单大厅列表item
 */

public class RobHallListItem implements Serializable{

	/**
	 * id : 2
	 * TypeName : 进水
	 * content : 2报修文字描述内容报修文字描述内容报修文字描述内容
	 * imgCount : 2
	 * createdDate : 1487324326
	 */

	private int id;
	private String typeName;
	private String content;
	private int imgCount;
	private int createdDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRepairsTypeName() {
		return typeName;
	}

	public void setRepairsTypeName(String repairsTypeName) {
		this.typeName = repairsTypeName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getImgCount() {
		return imgCount;
	}

	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}

	public int getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(int createdDate) {
		this.createdDate = createdDate;
	}
}
