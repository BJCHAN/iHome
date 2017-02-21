package com.tianchuang.ihome_b.base;

import android.view.View;

/**
 * Created by Abyss on 2016/11/15.
 */
public abstract class BaseHolder<T> {
    public View holderView;
    public BaseHolder() {
        //初始化holderview
        holderView = initHolderView();
        holderView.setTag(this);

    }
    /**
     * 获取holderview
     */
    public View getholderView() {
        return holderView;
    }
    public abstract View initHolderView();
    public abstract void bindData(T data);
}

