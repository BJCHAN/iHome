package com.tianchuang.ihome_b.mvp.contract;

import com.tianchuang.ihome_b.adapter.PropertyListAdapter;
import com.tianchuang.ihome_b.bean.PropertyListItemBean;
import com.tianchuang.ihome_b.mvp.BasePresenter;
import com.tianchuang.ihome_b.mvp.BaseRequestView;

import java.util.ArrayList;

/**
 * Created by Abyss on 2017/4/17.
 * description:物业列表
 */
public interface PropertyListContract {
    interface View extends BaseRequestView {

        void initAdapter(ArrayList<PropertyListItemBean> propertyList);

        void notifyUISetOften(int position);

        void deleteItem(int position);
    }

    interface Presenter extends BasePresenter<View> {
        void requestPropertyListData();

        void requestSetOften(PropertyListItemBean propertyListItemBean, int position);

        void requestDelete(int position);
    }
}