package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.BuildingSearchAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.DataBuildingSearchBean;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.bean.model.DataSearchModel;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/1.
 * description:楼宇查询
 */

public class BuildingSearchFragment extends BaseLoadingFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private ArrayList<DataBuildingSearchBean> mData =new ArrayList<>();
    private BuildingSearchAdapter searchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_building_search;
    }

    public static BuildingSearchFragment newInstance() {
        return new BuildingSearchFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("楼宇查询");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvList.addItemDecoration(new CommonItemDecoration(20));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new BuildingSearchAdapter(R.layout.building_search_item_holder, mData);
        rvList.setAdapter(searchAdapter);
    }



    @Override
    protected void initData() {
        DataSearchModel.INSTANCE.requestBuildingSearch()
                .compose(RxHelper.<ArrayList<DataBuildingSearchBean>>handleResult())
                .compose(this.<ArrayList<DataBuildingSearchBean>>bindToLifecycle())
                .subscribe(new RxSubscribe<ArrayList<DataBuildingSearchBean>>() {


                    @Override
                    public void _onNext(ArrayList<DataBuildingSearchBean> arrayList) {
                        mData.clear();
                        mData.addAll(arrayList);
                        checkData(arrayList);
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        showErrorPage();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
