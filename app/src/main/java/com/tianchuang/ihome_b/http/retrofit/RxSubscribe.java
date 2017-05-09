package com.tianchuang.ihome_b.http.retrofit;



import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.TianChuangApplication;
import com.tianchuang.ihome_b.utils.NetworkUtil;
import com.tianchuang.ihome_b.utils.Utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Abyss on 2017/1/10.
 * description:对错误进行处理
 */

public abstract class RxSubscribe<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable disposable) {
        //根据需要重写,可在这里showDialog
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof DataIsNullException) {
            _onNext(null);
            onComplete();
        }else if (!NetworkUtil.isConnected(Utils.getContext())) {
            _onError(Utils.getContext().getResources().getString(R.string.network_error_message));
        }else {
            _onError(ExceptionHandle.handleException(e).message);
            e.printStackTrace();
        }
    }

    public  abstract void _onNext(T t);

    public abstract void _onError(String message);
}
