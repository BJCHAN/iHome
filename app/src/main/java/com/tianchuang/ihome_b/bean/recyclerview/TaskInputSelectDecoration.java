package com.tianchuang.ihome_b.bean.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Abyss on 2016/12/20.
 * description:引导标签之间的间隔
 */

public class TaskInputSelectDecoration extends RecyclerView.ItemDecoration {

	private int space;

	public TaskInputSelectDecoration(int space) {
		this.space = space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//		if (parent.getChildLayoutPosition(view) % 3 == 2) {
//			outRect.right = space * 2;
//		}

			outRect.left = space;

		outRect.bottom = space;
		outRect.top = space;
	}

}
