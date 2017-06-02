package com.tianchuang.ihome_b.mvp.presenter;

import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.PropertyListItemBean;
import com.tianchuang.ihome_b.bean.model.PropertyModel;
import com.tianchuang.ihome_b.database.UserInfoDbHelper;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.BasePresenterImpl;
import com.tianchuang.ihome_b.mvp.contract.PropertyListContract;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Abyss on 2017/4/17.
 * description:物业列表
 */
public class PropertyListPresenter extends BasePresenterImpl<PropertyListContract.View> implements PropertyListContract.Presenter {
    private ArrayList<PropertyListItemBean> data;

    @Override
    public void requestPropertyListData() {
        PropertyModel.INSTANCE.requestPropertyList()
                .compose(RxHelper.handleResult())
                .compose(getMView().bindToLifecycle())
                .subscribe(new RxSubscribe<ArrayList<PropertyListItemBean>>() {
                    @Override
                    public void _onNext(ArrayList<PropertyListItemBean> propertyList) {
                        data = propertyList;
                        getMView().showSucceedPage();
                        setCurrentProperty(propertyList);
                        getMView().initAdapter(propertyList);

                    }

                    @Override
                    public void _onError(String message) {
                        getMView().showToast(message);
                        getMView().showErrorPage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /**
     * 设置当前的物业
     * */
    private void setCurrentProperty(ArrayList<PropertyListItemBean> propertyList) {
        Observable.fromIterable(propertyList)
                .filter(bean->bean.getPropertyCompanyId()== UserUtil.getLoginBean().getPropertyCompanyId())//当前的物业
                .compose(getMView().bindToLifecycle())
                .subscribe(propertyListItemBean -> {
                    propertyListItemBean.setCurrentProperty(true);
                });
    }

    @Override
    public void requestSetOften(PropertyListItemBean propertyListItemBean, int position) {
        PropertyModel.INSTANCE.requestSetOften(propertyListItemBean.getId())
                .map(HttpModle::success)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(o -> getMView().showProgress())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMView().dismissProgress();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {//设为常用成功
                            for (PropertyListItemBean propertyListItemBean : data) {
                                propertyListItemBean.setOftenUse(false);
                            }
                            PropertyListItemBean bean = data.get(position);
                            LoginBean loginBean = UserUtil.propertyListItemBeanToLoginBean(bean);
                            //储存到数据库中
                            UserInfoDbHelper.saveUserInfo(loginBean, UserUtil.getUserid());
                            bean.setOftenUse(!bean.getOftenUse());
                            getMView().notifyUISetOften(position);//通知ui设置常用

                        } else {
                            getMView().showToast("设为常用失败");
                        }
                        getMView().dismissProgress();
                    }
                });
    }

    @Override
    public void requestDelete(int position) {
        PropertyModel.INSTANCE.propertyDelete(data.get(position).getId())
                .compose(getMView().bindToLifecycle())
                .doOnSubscribe(o-> getMView().showProgress())
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {
                        getMView().dismissProgress();
                        getMView().deleteItem(position);
                    }

                    @Override
                    public void _onError(String message) {
                        getMView().showToast(message);
                        getMView().dismissProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}