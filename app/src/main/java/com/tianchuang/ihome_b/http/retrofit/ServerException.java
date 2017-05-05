package com.tianchuang.ihome_b.http.retrofit;

/**
 * Created by Abyss on 2017/1/10.
 * description:自定义服务器异常
 */

public class ServerException extends RuntimeException{
    public int code;
    public String message;

    public ServerException setCode(int code) {
        this.code = code;
        return this;
    }

    public ServerException setMessage(String message) {
        this.message = message;
        return this;
    }
}
