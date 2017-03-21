package com.tianchuang.ihome_b.bean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/4.
 * description:费用信息
 */

public class RepairsFeeBean {
	private float totalFee;
	private List<MyOrderDetailBean.RepairsFeeVosBean> repairsFeeVos;

	public float getTotalFee() {
		return totalFee;
	}

	public RepairsFeeBean setTotalFee(float toalFee) {
		this.totalFee = toalFee;
		return this;
	}

	public List<MyOrderDetailBean.RepairsFeeVosBean> getRepairsFeeVos() {
		return repairsFeeVos;
	}

	public RepairsFeeBean setRepairsFeeVos(List<MyOrderDetailBean.RepairsFeeVosBean> repairsFeeVos) {
		this.repairsFeeVos = repairsFeeVos;
		return this;
	}
}
