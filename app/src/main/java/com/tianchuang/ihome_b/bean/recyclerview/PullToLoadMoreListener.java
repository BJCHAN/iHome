package com.tianchuang.ihome_b.bean.recyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Abyss on 2017/2/28.
 * description:recycleView 加载更多的监听
 */

public class PullToLoadMoreListener extends RecyclerView.OnScrollListener {
    //用来标记是否正在向最后一个滑动
    boolean isSlidingToLast = false;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public PullToLoadMoreListener(SwipeRefreshLayout swipeRefreshLayout, OnLoadMoreListener onLoadMoreListener) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if (dy >= 0) {
            //大于0表示，正在向下滚动
            isSlidingToLast = true;
        } else {
            //小于等于0 表示停止或向上滚动
            isSlidingToLast = false;
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的ItemPosition
            int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
            int totalItemCount = manager.getItemCount();

            // 判断是否滚动到底部，并且是向下滚动
            if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                //加载更多功能的代码
                if (!mSwipeRefreshLayout.isRefreshing())
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.requestLoadMore();
                    }
            }
        }

    }

    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {
        void requestLoadMore();
    }
}
