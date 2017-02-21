package com.tianchuang.ihome_b.bean.recyclerview;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/2/9.
 * description:
 */

public class RobHallItem implements Serializable{
	private String type;
	private String pictureNum;
	private String date;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPictureNum() {
		return pictureNum;
	}

	public void setPictureNum(String pictureNum) {
		this.pictureNum = pictureNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
