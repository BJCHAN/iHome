package com.tianchuang.ihome_b.bean.recyclerview;

/**
 * Created by Abyss on 2017/3/1.
 * description:
 */

public class SimpleItemBean {
	private String text;
	private int id;

	public int getId() {
		return id;
	}

	public SimpleItemBean setId(int id) {
		this.id = id;
		return this;
	}

	public String getText() {
		return text;
	}

	public SimpleItemBean setText(String text) {
		this.text = text;
		return this;
	}
}
