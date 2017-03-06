package com.tianchuang.ihome_b.bean;

import com.google.zxing.common.detector.MathUtils;

/**
 * Created by Abyss on 2017/3/6.
 * description:通用上传的费用bean
 */

public class CommonFeeBean {

	/**
	 * title : 人工费
	 * type : 1
	 * refId : 1
	 * counts : 1
	 * fee : 20.00
	 */

	private String title;
	private int type;
	private int refId;
	private float counts;
	private String fee;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public float getCounts() {
		return counts;
	}

	public void setCounts(float counts) {
		this.counts = counts;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
}
