package com.tianchuang.ihome_b.http.retrofit;



import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.utils.NetworkUtil;
import com.tianchuang.ihome_b.utils.Utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Abyss on 2017/1/10.
 * description:对错误进行处理
 */

public abstract class RxSubscribe<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable disposable) {
        //根据需要重写
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetworkUtil.isConnected(Utils.getContext())) {
            //网络未连接
            _onError(Utils.getContext().getResources().getString(R.string.network_error_message));
        } else if (e instanceof ServerException) {
            //服务器错误 收到的都是除了200以外错误的状态码 或者可以加上errorinfo
            _onError(e.getMessage());
        } else {
            //其他错误，404,连接超时,或自己的代码错误等
            _onError(Utils.getContext().getResources().getString(R.string.connect_server_error));
        }
    }

    public  abstract void _onNext(T t);

    public abstract void _onError(String message);
}
