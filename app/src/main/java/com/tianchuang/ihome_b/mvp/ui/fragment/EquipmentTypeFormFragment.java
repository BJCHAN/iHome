package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.EqupmentSearchListAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.EquipmentSearchListItemBean;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;
import com.tianchuang.ihome_b.bean.model.DataSearchModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSchedulers;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.KeyboardUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by Abyss on 2017/3/1.
 * description:设备查询列表
 */

public class EquipmentTypeFormFragment extends BaseFragment {


    @BindView(R.id.tv_equipment_name)
    TextView tvEquipmentName;
    @BindView(R.id.et_address_name)
    EditText etAddressName;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private EquipmentTypeSearchBean item;
    private ArrayList<EquipmentSearchListItemBean> mData;
    private EqupmentSearchListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_equipment_type_form;
    }

    public static EquipmentTypeFormFragment newInstance(EquipmentTypeSearchBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", bean);
        EquipmentTypeFormFragment formFragment = new EquipmentTypeFormFragment();
        formFragment.setArguments(bundle);
        return formFragment;

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        item = ((EquipmentTypeSearchBean) getArguments().getSerializable("item"));
        if (item != null) {
            tvEquipmentName.setText(item.getName());
        }
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mData = new ArrayList<>();
        adapter = new EqupmentSearchListAdapter(mData);
        rvList.setAdapter(adapter);
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                EquipmentSearchListItemBean equipmentSearchListItemBean = (EquipmentSearchListItemBean) adapter.getData().get(position);
                addFragment(EquipmentDetailFragment.newInstance(equipmentSearchListItemBean.getId()));
            }
        });
    }

    @Override
    protected void initData() {
        requestNet();
    }

    @Override
    protected void initListener() {
        RxTextView.textChanges(etAddressName)
                .debounce(400,TimeUnit.MILLISECONDS)
//                .flatMap(this::getListSingle)
//                .toList()
//                .subscribe(newList -> {
//                    showToast(""+newList.size());
//                    adapter.setNewData(newList.size()>0?newList:mData);
//                });
                .compose(RxSchedulers.main())
                .subscribe(charSequence -> {
                    if (TextUtils.isEmpty(charSequence)) {
                        adapter.setNewData(mData);
                    } else {
                        requestNewData(charSequence);
                    }
                });

    }

    private void requestNewData(CharSequence address) {
        Observable.fromIterable(mData)
                .filter(equipmentSearchListItemBean -> equipmentSearchListItemBean.getPlace().toLowerCase().contains(address.toString().toLowerCase()))
                .compose(RxSchedulers.io_main())
                .toList()
                .subscribe(newList ->adapter.setNewData(newList));
    }


    /**
     * 请求网络
     *
     */
    private void requestNet() {
        DataSearchModel.INSTANCE.requestEquipmentListSearch(item.getId())
                .compose(RxHelper.<ArrayList<EquipmentSearchListItemBean>>handleResult())
                .subscribe(new RxSubscribe<ArrayList<EquipmentSearchListItemBean>>() {
                    @Override
                    public void _onNext(ArrayList<EquipmentSearchListItemBean> list) {
                        if (list != null && list.size() > 0) {
                            mData.clear();
                            mData.addAll(list);
                            adapter.notifyDataSetChanged();
                            KeyboardUtils.hideSoftInput(getHoldingActivity());
                        } else {
                            ToastUtil.showToast(getContext(), "数据为空");
                        }

                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

//    @OnClick(R.id.tv_sure_button)
//    public void onClick() {
////        final String address = etAddressName.getText().toString().trim();
////        if (TextUtils.isEmpty(address)) {
////            ToastUtil.showToast(getContext(), "输入不能为空");
////            return;
////        }
////        getListSingle(address)
//
//    }
}
