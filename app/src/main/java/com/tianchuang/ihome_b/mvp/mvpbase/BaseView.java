package com.tianchuang.ihome_b.mvp.mvpbase;

import android.content.Context;

import rx.Observable;

/**
 * Created by Abyss on 2017/4/1.
 * description:
 */

public interface BaseView {
    Context getContext();
    <T> Observable.Transformer<T, T> bindToLifecycle();
}
