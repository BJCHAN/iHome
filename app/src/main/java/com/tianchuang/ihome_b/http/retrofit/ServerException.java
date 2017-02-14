package com.tianchuang.ihome_b.http.retrofit;

/**
 * Created by Abyss on 2017/1/10.
 * description:自定义服务器异常
 */

public class ServerException extends Exception {
    public ServerException(String detailMessage) {
        super(detailMessage);
    }
}
