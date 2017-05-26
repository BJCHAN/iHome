package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.CarDetailAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.CarDetailBean;
import com.tianchuang.ihome_b.bean.CarSelectItem;
import com.tianchuang.ihome_b.bean.EquipmentDetailBean;
import com.tianchuang.ihome_b.bean.model.DataSearchModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.KeyboardUtils;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/3/25.
 * description:车辆信息查询
 */

public class CarDetailFragment extends BaseFragment {
    @BindView(R.id.tv_select_car)
    TextView tvSelectCar;
    @BindView(R.id.et_plate_num)
    EditText etPlateNum;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private OptionsPickerView pvCarOptions;
    private ArrayList<CarSelectItem> carSelectItems;
    private CarSelectItem carSelectedItem;
    private ArrayList<EquipmentDetailBean.FieldDataListBean> mData;
    private CarDetailAdapter adapter;

    public static CarDetailFragment newInstance() {
        return new CarDetailFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        carSelectItems = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.province_name);
        for (int i = 0; i < stringArray.length; i++) {
            carSelectItems.add(new CarSelectItem().setName(stringArray[i]));
        }
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mData = new ArrayList<>();
        adapter = new CarDetailAdapter(mData);
        adapter.setEmptyView(LayoutUtil.inflate(R.layout.car_empty_view));
        rvList.setAdapter(adapter);
        initBuildingOptionPicker();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("车辆查询");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car_detail;
    }

    private void initBuildingOptionPicker() {//条件选择器初始化，自定义布局

        // 注意，自定义布局中，optionspicker 或者 timepicker 的布局必须要有（即WheelView内容部分），否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        pvCarOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                carSelectedItem = carSelectItems.get(options1);
                String tx = carSelectedItem.getPickerViewText();
                tvSelectCar.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCarOptions.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCarOptions.dismiss();
                            }
                        });
                    }
                })
                .build();
        pvCarOptions.setPicker(carSelectItems);//添加数据
    }


    @OnClick({R.id.tv_select_car, R.id.tv_sure_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_car:
                pvCarOptions.show();
                break;
            case R.id.tv_sure_button:
                if (carSelectedItem == null) {
                    ToastUtil.showToast(getContext(), "请选择车辆所在区域");
                    KeyboardUtils.hideSoftInput(getHoldingActivity());
                    return;
                }
                String plateNum = etPlateNum.getText().toString().trim();
                if (TextUtils.isEmpty(plateNum)) {
                    ToastUtil.showToast(getContext(), "车牌号不能为空");
                    return;
                }
                DataSearchModel.INSTANCE.carDetail(carSelectedItem.getName() + plateNum)
                        .compose(RxHelper.<CarDetailBean>handleResult())
                        .compose(this.<CarDetailBean>bindToLifecycle())
                        .doOnSubscribe(o ->showProgress())
                        .subscribe(new RxSubscribe<CarDetailBean>() {
                            @Override
                            public void _onNext(CarDetailBean carDetailBean) {
                                mData.clear();
                                if (carDetailBean != null) {
                                    if (carDetailBean.getOwnersName() != null) {
                                        mData.add(getListBean("车主：", carDetailBean.getOwnersName()));
                                    }
                                    if (carDetailBean.getOwnersMobile() != null) {
                                        mData.add(getListBean("电话号码：", carDetailBean.getOwnersMobile()));
                                    }
                                    if (carDetailBean.getBuildingName() != null) {
                                        String address = StringUtils.getNotNull(carDetailBean.getBuildingName())
                                                + StringUtils.getNotNull(carDetailBean.getBuildingCell())
                                                + StringUtils.getNotNull(carDetailBean.getBuildingUnit())
                                                + StringUtils.getNotNull(carDetailBean.getBuildingRoom());
                                        mData.add(getListBean("住址：", address));
                                    }
                                    KeyboardUtils.hideSoftInput(getHoldingActivity());
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void _onError(String message) {
                                dismissProgress();
                                ToastUtil.showToast(getContext(), message);
                            }

                            @Override
                            public void onComplete() {
                                dismissProgress();
                            }
                        });

                break;
        }
    }

    private EquipmentDetailBean.FieldDataListBean getListBean(String name, String value) {
        EquipmentDetailBean.FieldDataListBean listBean = new EquipmentDetailBean.FieldDataListBean();
        listBean.setFieldName(name);
        listBean.setFieldValue(value);
        return listBean;
    }
}
