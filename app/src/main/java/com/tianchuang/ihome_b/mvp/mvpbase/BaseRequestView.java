package com.tianchuang.ihome_b.mvp.mvpbase;

/**
 * Created by Abyss on 2017/4/1.
 * description:
 */

public interface BaseRequestView extends BaseView {
    void onRequestError(String msg);
    void onRequestCompleted();
}
