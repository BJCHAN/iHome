package com.tianchuang.ihome_b.bean;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Abyss on 2016/12/20.
 * description:侧滑菜单item之间的间隔
 */

public class DrawMenuItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public DrawMenuItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childLayoutPosition = parent.getChildLayoutPosition(view);
            if (childLayoutPosition == 0||childLayoutPosition == 2 || childLayoutPosition == 4 || childLayoutPosition == 8) {
                outRect.top = space * 2;
            }
    }

}
