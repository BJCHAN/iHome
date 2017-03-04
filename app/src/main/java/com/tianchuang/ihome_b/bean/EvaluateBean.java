package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/4.
 * description:评价信息
 */

public class EvaluateBean {
	private int stars; // 评价星级
	private String evaluateContent;  // 评价内容

	public int getStars() {
		return stars;
	}

	public EvaluateBean setStars(int stars) {
		this.stars = stars;
		return this;
	}

	public String getEvaluateContent() {
		return evaluateContent;
	}

	public EvaluateBean setEvaluateContent(String evaluateContent) {
		this.evaluateContent = evaluateContent;
		return this;
	}
}
