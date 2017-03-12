package com.tianchuang.ihome_b.utils;

import android.view.View;

import com.tianchuang.ihome_b.view.viewholder.DetailFooterViewHolder;
import com.tianchuang.ihome_b.view.viewholder.DetailHeaderViewHolder;
import com.tianchuang.ihome_b.view.viewholder.EmptyViewHolder;
import com.tianchuang.ihome_b.view.viewholder.FormSubmitHeaderViewHolder;
import com.tianchuang.ihome_b.view.viewholder.MyOrderEvaluatenfoViewHolder;
import com.tianchuang.ihome_b.view.viewholder.MyOrderFeeInfoViewHolder;
import com.tianchuang.ihome_b.view.viewholder.MyOrderOwnerInfoViewHolder;
import com.tianchuang.ihome_b.view.viewholder.MyOrderTimeLineViewHolder;
import com.tianchuang.ihome_b.bean.DetailHeaderBean;
import com.tianchuang.ihome_b.bean.EvaluateBean;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.bean.RepairsFeeBean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/1.
 * description:一些封装好的View页面
 */

public class ViewHelper {
	/**
	 * 空页面
	 */
	public static View getEmptyView(String tip) {
		EmptyViewHolder emptyViewHolder = new EmptyViewHolder();
		emptyViewHolder.bindData(tip);
		return emptyViewHolder.getholderView();
	}

	/**
	 * 详情页的头部
	 */
	public static View getDetailHeaderView(String typeName, int createdDate) {
		DetailHeaderViewHolder holder = new DetailHeaderViewHolder();
		holder.bindData(new DetailHeaderBean().setTypeName(typeName).setCreatedDate(createdDate));
		return holder.getholderView();
	}

	/**
	 * 详情页的底部，不可回复
	 */
	public static View getDetailFooterView(String content) {
		DetailFooterViewHolder holder = new DetailFooterViewHolder();
		holder.bindData(content);
		return holder.getholderView();
	}

	/**
	 * 我的订单，报修人员信息的底部
	 *
	 * @param ownersInfoVo
	 */
	public static View getMyOrderOwnerInfoFooterView(MyOrderDetailBean.OwnersInfoVoBean ownersInfoVo) {
		MyOrderOwnerInfoViewHolder holder = new MyOrderOwnerInfoViewHolder();
		holder.bindData(ownersInfoVo);
		return holder.getholderView();
	}

	/**
	 * 我的订单，维修费用的底部
	 *
	 * @param feeInfoVo
	 */
	public static View getMyOrderFeeInfoFooterView(RepairsFeeBean feeInfoVo) {
		MyOrderFeeInfoViewHolder holder = new MyOrderFeeInfoViewHolder();
		holder.bindData(feeInfoVo);
		return holder.getholderView();
	}

	/**
	 * 我的订单，评价的底部
	 *
	 * @param evaluateBean
	 */
	public static View getMyOrderEvaluateInfoFooterView(EvaluateBean evaluateBean) {
		MyOrderEvaluatenfoViewHolder holder = new MyOrderEvaluatenfoViewHolder();
		holder.bindData(evaluateBean);
		return holder.getholderView();
	}

	/**
	 * 我的订单，时间轴的底部
	 *
	 * @param list
	 */
	public static View getTimeLineFooterView(List<MyOrderDetailBean.RepairsOrderLogVo> list) {
		MyOrderTimeLineViewHolder holder = new MyOrderTimeLineViewHolder();
		holder.bindData(list);
		return holder.getholderView();
	}
	/**
	 * 表单提交页的头部
	 */
	public static View getFormSubmitHeaderView(String typeName) {
		FormSubmitHeaderViewHolder holder = new FormSubmitHeaderViewHolder();
		holder.bindData(typeName);
		return holder.getholderView();
	}

}
