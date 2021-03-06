package com.tianchuang.ihome_b.mvp.presenter;

import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean;
import com.tianchuang.ihome_b.bean.CommonFeeBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.bean.event.FeeSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.model.MyOrderModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.BasePresenterImpl;
import com.tianchuang.ihome_b.mvp.contract.FeeDetailContract;
import com.tianchuang.ihome_b.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Created by Abyss on 2017/04/06
 */

public class FeeDetailPresenter extends BasePresenterImpl<FeeDetailContract.View> implements FeeDetailContract.Presenter {
    private ArrayList<MaterialListItemBean> materialList;
    private ArrayList<ChargeTypeListItemBean> chargeTypeList;

    @Override
    public ArrayList<MaterialListItemBean> getMaterialList() {
        return materialList;
    }

    @Override
    public ArrayList<ChargeTypeListItemBean> getChargeTypeList() {
        return chargeTypeList;
    }

    /**
     * 获取材料列表
     */
    @Override
    public void fetchFeeList() {
        materialList = new ArrayList<>();
        chargeTypeList = new ArrayList<>();
        Observable<ArrayList<MaterialListItemBean>> materialListObservable = MyOrderModel.INSTANCE.materialList().compose(RxHelper.handleResult());
        Observable<ArrayList<ChargeTypeListItemBean>> chargeTypeListObservable = MyOrderModel.INSTANCE.chargeTypeList().compose(RxHelper.handleResult());
        getChargeTypeAndMaterialList(materialListObservable, chargeTypeListObservable)
                .retry(2)
                .subscribe(new RxSubscribe<Object>() {
                    @Override
                    public void onComplete() {
                        getMView().showSucceedPage();
                    }

                    @Override
                    public void _onNext(Object o) {
                        if (o instanceof ArrayList) {
                            ArrayList list = (ArrayList) o;
                            if (list.size() > 0) {
                                if (list.get(0) instanceof MaterialListItemBean) {//材料列表
                                    materialList.clear();
                                    materialList.addAll(((ArrayList<MaterialListItemBean>) o));
                                } else if (list.get(0) instanceof ChargeTypeListItemBean) {//费用列表
                                    chargeTypeList.clear();
                                    chargeTypeList.addAll(((ArrayList<ChargeTypeListItemBean>) o));
                                }
                            }
                        }
                    }

                    @Override
                    public void _onError(String message) {
                        getMView().showToast(message);
                        getMView().showErrorPage();
                    }
                });
    }

    /**
     * 提交费用
     *
     * @param repairId
     * @param checked
     * @param commonFeeBeenList
     */
    @Override
    public void requestSubmit(int repairId, boolean checked, ArrayList<CommonFeeBean> commonFeeBeenList) {
        MyOrderModel.INSTANCE.submitFeeList(repairId, checked ? 1 : 0, StringUtils.toJson(commonFeeBeenList, 2))
                .compose(RxHelper.handleResult())
                .compose(getMView().bindToLifecycle())
                .doOnSubscribe(o-> getMView().showProgress())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {
                        getMView().showToast("提交成功");
                        getMView().dismissProgress();
                        getMView().removeFragment();
                        EventBus.getDefault().post(new FeeSubmitSuccessEvent());
                    }

                    @Override
                    public void _onError(String message) {
                        getMView().dismissProgress();
                        getMView().showToast(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private Observable<Object> getChargeTypeAndMaterialList(Observable<ArrayList<MaterialListItemBean>> materialListObservable, Observable<ArrayList<ChargeTypeListItemBean>> chargeTypeListObservable) {
        return Observable.merge(materialListObservable, chargeTypeListObservable)
                .compose(getMView().bindToLifecycle());
    }
}