package com.tianchuang.ihome_b.bean.event;

/**
 * Created by Abyss on 2017/2/16.
 * description:拨打电话的事件
 */

public class PlayPhoneEvent {
	private String phone;

	public PlayPhoneEvent(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
