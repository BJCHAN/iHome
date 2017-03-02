package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/2.
 * description:详情的头部bean
 */

public class DetailHeaderBean {
	private String typeName;
	private int createdDate;

	public String getTypeName() {
		return typeName;
	}

	public DetailHeaderBean setTypeName(String typeName) {
		this.typeName = typeName;
		return this;
	}

	public int getCreatedDate() {
		return createdDate;
	}

	public DetailHeaderBean setCreatedDate(int createdDate) {
		this.createdDate = createdDate;
		return this;
	}
}
