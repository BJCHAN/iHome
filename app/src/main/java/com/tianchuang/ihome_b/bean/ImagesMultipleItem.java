package com.tianchuang.ihome_b.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Abyss on 2017/2/25.
 * description:选择添加图片的图片种类
 */

public class ImagesMultipleItem implements MultiItemEntity {
	public static final int ADD_IMG = 1;
	public static final int IMG = 2;
	private int itemType;
	private String url;


	public ImagesMultipleItem(int itemType) {
		this.itemType = itemType;
	}

	@Override
	public int getItemType() {
		return itemType;
	}

	public String getUrl() {
		return url;
	}

	public ImagesMultipleItem setUrl(String url) {
		this.url = url;
		return this;
	}
}
