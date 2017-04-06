package com.tianchuang.ihome_b.mvp.contract;

import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean;
import com.tianchuang.ihome_b.bean.CommonFeeBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.mvp.BasePresenter;
import com.tianchuang.ihome_b.mvp.BaseRequestView;

import java.util.ArrayList;

/**
 * Created by Abyss on 2017/4/6.
 * description:费用明细的契约类
 */

public interface FeeDetailContract {
    interface View extends BaseRequestView {
        void removeFragment();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchFeeList();
        ArrayList<ChargeTypeListItemBean> getChargeTypeList();
        ArrayList<MaterialListItemBean> getMaterialList();
        void requestSubmit(int repairId, boolean checked, ArrayList<CommonFeeBean> commonFeeBeenList);

    }

}