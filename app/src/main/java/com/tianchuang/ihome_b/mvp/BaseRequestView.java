package com.tianchuang.ihome_b.mvp;

/**
 * Created by Abyss on 2017/4/1.
 * description:
 */

public interface BaseRequestView extends BaseView {
    void showToast(String message);

    void showErrorPage();

    void showSucceedPage();

    void showEmptyPage();

    void showProgress();

    void dismissProgress();

}
