package com.tianchuang.ihome_b.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MenuInnerReportsAdapter;
import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerListBean;
import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.model.InnerReportsModel;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事（菜单）
 */

public class MenuInnerReportsFragment extends BaseRefreshAndLoadMoreFragment<MenuInnerReportsItemBean, MenuInnerListBean> {

    public static MenuInnerReportsFragment newInstance() {
        return new MenuInnerReportsFragment();
    }

    /**
     * 首次访问网络成功,初始化adapter
     */
    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<MenuInnerReportsItemBean> mData, MenuInnerListBean listBean) {
        return new MenuInnerReportsAdapter(R.layout.inner_reports_item_holder
                , mData);
    }

    /**
     * item的点击事件
     */
    @Override
    protected void onListitemClick(MenuInnerReportsItemBean menuInnerReportsItemBean) {
        addFragment(MenuInnerReportsDetailFragment.newInstance(menuInnerReportsItemBean));
    }

    /**
     * 请求网络的接口
     */
    @Override
    protected Observable<MenuInnerListBean> getNetObservable(int maxId) {
        return InnerReportsModel.requestReportsList(UserUtil.getLoginBean().getPropertyCompanyId(), maxId)
                .compose(RxHelper.<MenuInnerListBean>handleResult());
    }

    /**
     * 获取数据为空时候的显示
     */
    @Override
    protected String getEmptyString() {
        return getString(R.string.menu_inner_list_empty);
    }
}
