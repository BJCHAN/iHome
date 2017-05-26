package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.EquipmentTypeAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;
import com.tianchuang.ihome_b.bean.model.DataSearchModel;
import com.tianchuang.ihome_b.bean.recyclerview.EquipmentTypeItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/1.
 * description:设备类型查询
 */

public class EquipmentTypeFragment extends BaseLoadingFragment {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private EquipmentTypeAdapter typeAdapter;
    private ArrayList<EquipmentTypeSearchBean> mData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_equipment_type_search;
    }

    public static EquipmentTypeFragment newInstance() {
        return new EquipmentTypeFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("设备查询");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvList.addItemDecoration(new EquipmentTypeItemDecoration(DensityUtil.dip2px(getContext(), 10)));
        mData = new ArrayList<>();
        typeAdapter = new EquipmentTypeAdapter(mData);
        rvList.setAdapter(typeAdapter);
    }


    @Override
    protected void initData() {
        DataSearchModel.INSTANCE.requestEquipmentTypeSearch()
                .compose(RxHelper.<ArrayList<EquipmentTypeSearchBean>>handleResult())
                .compose(this.<ArrayList<EquipmentTypeSearchBean>>bindToLifecycle())
                .subscribe(new RxSubscribe<ArrayList<EquipmentTypeSearchBean>>() {
                    @Override
                    public void _onNext(ArrayList<EquipmentTypeSearchBean> list) {
                        mData.clear();
                        mData.addAll(list);
                        checkData(list);
                        rvList.addOnItemTouchListener(new OnItemClickListener() {
                            @Override
                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                for (EquipmentTypeSearchBean data : mData) {
                                    data.setSelected(false);
                                }
                                EquipmentTypeSearchBean equipmentTypeSearchBean = mData.get(position);
                                equipmentTypeSearchBean.setSelected(true);
                                adapter.notifyDataSetChanged();
                                addFragment(EquipmentTypeFormFragment.newInstance(equipmentTypeSearchBean));
                            }
                        });
                        typeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void _onError(String message) {
                        showErrorPage();
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
