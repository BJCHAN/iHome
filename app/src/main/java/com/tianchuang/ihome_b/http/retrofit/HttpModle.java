package com.tianchuang.ihome_b.http.retrofit;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/1/10.
 * description:对通用的状态码进行处理
 */

public class HttpModle<T> implements Serializable {
    public String code;//状态码
    public T data;//数据
    public String msg;//错误信息
    public boolean success() {
        return "200".equals(code);
    }

    @Override
    public String toString() {
        return "HttpModle{" +
                "data=" + data +
                ", state=" + code +
                '}';
    }
}
