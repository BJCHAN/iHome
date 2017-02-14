package com.tianchuang.ihome_b.http.retrofit;


import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.utils.NetworkUtil;
import com.tianchuang.ihome_b.utils.Utils;

import rx.Subscriber;

/**
 * Created by Abyss on 2017/1/10.
 * description:对错误进行处理，自行选择用RxSubscribe或者RxSubscribeWithStatusCode
 */

public abstract class RxSubscribe<T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetworkUtil.isConnected(Utils.getContext())) {
            //网络未连接
            _onError(Utils.getContext().getResources().getString(R.string.network_error_message));
        } else if (e instanceof ServerException) {
            //服务器错误 收到的都是除了1以外错误的状态码 或者可以加上errorinfo
            _onError(e.getMessage());
        } else {
            //其他错误，404,连接超时,或自己的代码错误等
            _onError(Utils.getContext().getResources().getString(R.string.connect_server_error));
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
