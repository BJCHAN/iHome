package com.tianchuang.ihome_b.bean.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

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

        if (parent.getAdapter() instanceof BaseQuickAdapter) {
            BaseQuickAdapter adapter = (BaseQuickAdapter) parent.getAdapter();

            int itemViewType = adapter.getItemViewType(parent.getChildAdapterPosition(view));
            if (itemViewType == BaseQuickAdapter.FOOTER_VIEW) {
            } else {

                outRect.top = space;
            }
        }
    }

}
