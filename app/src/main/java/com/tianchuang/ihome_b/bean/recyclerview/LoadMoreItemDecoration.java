package com.tianchuang.ihome_b.bean.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Abyss on 2016/12/20.
 * description:加载更多的通用间隔
 */

public class LoadMoreItemDecoration extends RecyclerView.ItemDecoration {

	private int space;

	public LoadMoreItemDecoration(int space) {
		this.space = space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int itemCount = parent.getAdapter().getItemCount();
		if (!(itemCount > 0 && itemCount - 1 == parent.getChildAdapterPosition(view))) {//非最后一个
			outRect.top = space;
		}
	}

}
