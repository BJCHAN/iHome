package com.tianchuang.ihome_b.mvp.modifypassword;

import com.tianchuang.ihome_b.mvp.mvpbase.BasePresenter;
import com.tianchuang.ihome_b.mvp.mvpbase.BaseRequestView;
import com.tianchuang.ihome_b.mvp.mvpbase.BaseView;

/**
 * Created by Abyss on 2017/4/1.
 * description:修改密码的契约类
 */

public interface ModifyPasswordContract {
    interface View extends BaseRequestView{
        void showRedTip(String message);
        void startFragment();
    }

    interface Presenter extends BasePresenter<View> {
        void submitPwdChanged(String oldPwd, String newPwd, String surePwd);
    }
}
