package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.EquipmentDetailAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.EquipmentDetailBean;
import com.tianchuang.ihome_b.bean.model.DataSearchModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/25.
 * description:设备详情页面
 */

public class EquipmentDetailFragment extends BaseLoadingFragment {
    @BindView(R.id.tv_equipment_type)
    TextView tvEquipmentType;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_equipment_detail;
    }

    public static EquipmentDetailFragment newInstance(int equipmentId) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", equipmentId);
        EquipmentDetailFragment fragment = new EquipmentDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static EquipmentDetailFragment newInstance(EquipmentDetailBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        EquipmentDetailFragment fragment = new EquipmentDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        setToolbarTitle("设备详情");
    }


    @Override
    protected void initData() {
        int id = getArguments().getInt("id");
        Serializable serializable = getArguments().getSerializable("bean");
        if (serializable != null) {
            updateUIByData(((EquipmentDetailBean) serializable));
        } else {
            DataSearchModel.INSTANCE.equipmentDetail(id)
                    .compose(RxHelper.<EquipmentDetailBean>handleResult())
                    .compose(this.<EquipmentDetailBean>bindToLifecycle())
                    .subscribe(new RxSubscribe<EquipmentDetailBean>() {
                        @Override
                        public void _onNext(EquipmentDetailBean equipmentDetailBean) {
                            updateUIByData(equipmentDetailBean);
                        }

                        @Override
                        public void _onError(String message) {
                            showToast(message);
                            showErrorPage();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }

    }

    private void updateUIByData(EquipmentDetailBean equipmentDetailBean) {
        if (equipmentDetailBean != null) {
            tvEquipmentType.setText(StringUtils.getNotNull(equipmentDetailBean.getEquipmentTypeName()));
            View header = LayoutUtil.inflate(R.layout.equipment_detail_header_holder);
            TextView tvName = (TextView) header.findViewById(R.id.tv_name);
            TextView tvAddress = (TextView) header.findViewById(R.id.tv_address);
            TextView tvContent = (TextView) header.findViewById(R.id.tv_content);
            tvName.setText(StringUtils.getNotNull(equipmentDetailBean.getName()));
            tvAddress.setText(StringUtils.getNotNull(equipmentDetailBean.getPlace()));
            tvContent.setText(StringUtils.getNotNull(equipmentDetailBean.getBrand()));
            EquipmentDetailAdapter adapter = new EquipmentDetailAdapter(equipmentDetailBean.getFieldDataList());
            adapter.addHeaderView(header);
            rvList.setAdapter(adapter);
        }
        showSucceedPage();
    }
}
