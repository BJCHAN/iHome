package com.tianchuang.ihome_b.mvp;

import android.content.Context;

import io.reactivex.ObservableTransformer;


/**
 * Created by Abyss on 2017/4/1.
 * description:
 */

public interface BaseView {
    Context getContext();
    <T> ObservableTransformer<T,T> bindToLifecycle();
}
