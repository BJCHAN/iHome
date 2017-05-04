package com.tianchuang.ihome_b.http.retrofit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Abyss on 2017/5/4.
 * description:统一的线程切换
 */

public class RxSchedulers {
    public static <T>ObservableTransformer<T,T> io_main() {
        return observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T>ObservableTransformer<T,T> io() {
        return observable -> observable.observeOn(Schedulers.io());
    }

    public static <T>ObservableTransformer<T,T> main() {
        return observable -> observable.observeOn(AndroidSchedulers.mainThread());
    }

    public static <T>ObservableTransformer<T,T> computation() {
        return observable -> observable.observeOn(Schedulers.computation());
    }
}
