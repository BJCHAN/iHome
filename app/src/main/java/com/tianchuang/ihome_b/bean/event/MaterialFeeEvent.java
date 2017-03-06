package com.tianchuang.ihome_b.bean.event;

import com.tianchuang.ihome_b.bean.CommonFeeBean;

/**
 * Created by Abyss on 2017/2/16.
 * description:传递选择完的材料费用的事件
 */

public class MaterialFeeEvent {
	private CommonFeeBean commonFeeBean;

	public MaterialFeeEvent(CommonFeeBean commonFeeBean) {
		this.commonFeeBean = commonFeeBean;
	}

	public CommonFeeBean getCommonFeeBean() {
		return commonFeeBean;
	}

	public void setCommonFeeBean(CommonFeeBean commonFeeBean) {
		this.commonFeeBean = commonFeeBean;
	}
}
