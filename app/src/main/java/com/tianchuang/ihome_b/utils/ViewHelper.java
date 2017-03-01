package com.tianchuang.ihome_b.utils;

import android.view.View;

import com.tianchuang.ihome_b.EmptyViewHolder;

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
}
