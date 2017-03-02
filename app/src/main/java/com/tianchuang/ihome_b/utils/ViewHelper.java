package com.tianchuang.ihome_b.utils;

import android.view.View;

import com.tianchuang.ihome_b.DetailFooterViewHolder;
import com.tianchuang.ihome_b.DetailHeaderViewHolder;
import com.tianchuang.ihome_b.EmptyViewHolder;
import com.tianchuang.ihome_b.bean.DetailHeaderBean;

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
	 * 详情页的底部，可回复
	 */
	public static View getDetailFooterView2(String typeName, int createdDate) {
		DetailHeaderViewHolder holder = new DetailHeaderViewHolder();
		holder.bindData(new DetailHeaderBean().setTypeName(typeName).setCreatedDate(createdDate));
		return holder.getholderView();
	}
}
