package com.tianchuang.ihome_b.bean.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Abyss on 2016/12/20.
 * description:抢单大厅item之间的间隔
 */

public class CommonItemDecoration extends RecyclerView.ItemDecoration {

	private int space;

	public CommonItemDecoration(int space) {
		this.space = space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int childLayoutPosition = parent.getChildLayoutPosition(view);
		if (childLayoutPosition != 0) {
			outRect.top = space;
		}
	}

}
