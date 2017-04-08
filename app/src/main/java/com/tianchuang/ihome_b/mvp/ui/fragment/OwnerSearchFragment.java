package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.TaskBuildingAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.BuildingRoomListBean;
import com.tianchuang.ihome_b.bean.OwnerDetailBean;
import com.tianchuang.ihome_b.bean.TaskAreaListBean;
import com.tianchuang.ihome_b.bean.model.DataSearchModel;
import com.tianchuang.ihome_b.bean.recyclerview.TaskInputSelectDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/3/25.
 * description:业主查询
 */

public class OwnerSearchFragment extends BaseLoadingFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_building)
    TextView tvBuilding;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_room)
    TextView tvRoom;
    @BindView(R.id.bt_sure)
    Button btSure;
    private TaskBuildingAdapter areaAdapter;
    private List<TaskAreaListBean> mData = new ArrayList<>();
    private TaskAreaListBean selestedAreaBean;//被选中的小区
    private TaskAreaListBean.CellListBean selectedBuildingBean;//被选中的楼宇
    private TaskAreaListBean.CellListBean.UnitListBean selectedUnitBean;//被选中的单元
    private BuildingRoomListBean selectedRoomBean;//被选中的房间
    private OptionsPickerView pvBuildingOptions;
    private OptionsPickerView pvUnitOptions;
    private OptionsPickerView pvRoomOptions;
    private ArrayList<TaskAreaListBean.CellListBean> cellItems = new ArrayList<>();
    private ArrayList<TaskAreaListBean.CellListBean.UnitListBean> unitItems = new ArrayList<>();
    private ArrayList<BuildingRoomListBean> roomItems = new ArrayList<>();

    public static OwnerSearchFragment newInstance() {
        return new OwnerSearchFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvList.addItemDecoration(new TaskInputSelectDecoration(DensityUtil.dip2px(getContext(), 5)));
        areaAdapter = new TaskBuildingAdapter(mData);
        rvList.setAdapter(areaAdapter);
        DataSearchModel.requestAreaList()//请求小区列表
                .compose(RxHelper.<ArrayList<TaskAreaListBean>>handleResult())
                .compose(this.<ArrayList<TaskAreaListBean>>bindToLifecycle())
                .subscribe(new RxSubscribe<ArrayList<TaskAreaListBean>>() {
                    @Override
                    protected void _onNext(ArrayList<TaskAreaListBean> list) {
                        mData.clear();
                        mData.addAll(list);
                        checkData(list);
                        rvList.addOnItemTouchListener(new OnItemClickListener() {
                            @Override
                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                for (TaskAreaListBean data : mData) {
                                    data.setSelected(false);
                                }
                                selestedAreaBean = mData.get(position);
                                selestedAreaBean.setSelected(true);
                                selectedBuildingClear();
                                selectedUnitClear();
                                selectedRoomClear();
                                requestBuildingList();
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        showErrorPage();
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
    }
    @Override
    protected void initData() {

    }
    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("业主查询");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_owner_search_select;
    }


    @OnClick({R.id.tv_building, R.id.tv_unit, R.id.tv_room, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_building://选择楼宇
                if (cellItems.size() > 0) {
                    pvBuildingOptions.show();
                } else {
                    ToastUtil.showToast(getContext(), "请先选择小区");
                }
                break;
            case R.id.tv_unit://选择单元
                if (unitItems.size() > 0) {
                    pvUnitOptions.show();
                } else {
                    ToastUtil.showToast(getContext(), "请先选择楼宇");
                }
                break;
            case R.id.tv_room://选择单元
                if (roomItems.size() > 0) {
                    pvRoomOptions.show();
                } else {
                    ToastUtil.showToast(getContext(), "请先选择单元");
                }
                break;
            case R.id.bt_sure://查询
                if (selectedRoomBean != null) {
                    requestOwnerDetail(selectedRoomBean.getValue());
                } else {
                    ToastUtil.showToast(getContext(), "请完善数据");
                }
                break;
        }
    }

    private void requestOwnerDetail(int value) {
        DataSearchModel.requestOwnerDetail(value)
                .compose(RxHelper.<OwnerDetailBean>handleResult())
                .compose(this.<OwnerDetailBean>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<OwnerDetailBean>() {
                    @Override
                    protected void _onNext(OwnerDetailBean ownerDetailBean) {
                        if (ownerDetailBean != null) {
                            ownerDetailBean.setAddress(getNotNull(selestedAreaBean.getName() +
                                    selectedBuildingBean.getName() +
                                    selectedUnitBean.getName() +
                                    selectedRoomBean.getName()));
                            addFragment(OwnerDetailFragment.newInstance(ownerDetailBean));
                        } else {
                            ToastUtil.showToast(getContext(), "业主信息为空");
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onCompleted() {
                        dismissProgress();

                    }
                });

    }


    /**
     * 请求楼宇列表
     */
    private void requestBuildingList() {
        DataSearchModel.requestBuildingList(selestedAreaBean.getId())
                .compose(RxHelper.<ArrayList<TaskAreaListBean.CellListBean>>handleResult())
                .compose(this.<ArrayList<TaskAreaListBean.CellListBean>>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<ArrayList<TaskAreaListBean.CellListBean>>() {
                    @Override
                    protected void _onNext(ArrayList<TaskAreaListBean.CellListBean> cellListBeen) {
                        selestedAreaBean.setCellList(cellListBeen);
                        if (cellListBeen != null && cellListBeen.size() > 0) {
                            initBuildingOptionPicker(selestedAreaBean);//初始化楼宇选择器

                        }

                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onCompleted() {
                        dismissProgress();
                    }
                });
    }

    /**
     * 请求单元列表
     */
    private void requestUnitList(int buildingId) {
        DataSearchModel.requestUnitList(buildingId)
                .compose(RxHelper.<ArrayList<TaskAreaListBean.CellListBean.UnitListBean>>handleResult())
                .compose(this.<ArrayList<TaskAreaListBean.CellListBean.UnitListBean>>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<ArrayList<TaskAreaListBean.CellListBean.UnitListBean>>() {
                    @Override
                    protected void _onNext(ArrayList<TaskAreaListBean.CellListBean.UnitListBean> unitListBeen) {
                        selectedBuildingBean.setUnitList(unitListBeen);
                        initUnitOptionPicker(selectedBuildingBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onCompleted() {
                        dismissProgress();
                    }
                });

    }

    /**
     * 请求房间列表
     */
    private void requestRoomList(int value) {
        DataSearchModel.requestRoomList(value)
                .compose(RxHelper.<ArrayList<BuildingRoomListBean>>handleResult())
                .compose(this.<ArrayList<BuildingRoomListBean>>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<ArrayList<BuildingRoomListBean>>() {
                    @Override
                    protected void _onNext(ArrayList<BuildingRoomListBean> buildingRoomListBeen) {
                        selectedUnitBean.setRoomList(buildingRoomListBeen);
                        initRoomOptionPicker(selectedUnitBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);

                    }

                    @Override
                    public void onCompleted() {
                        dismissProgress();
                    }
                });

    }

    /**
     * 选中房间清空
     */
    private void selectedRoomClear() {
        selectedRoomBean = null;
        roomItems.clear();
        tvRoom.setText("");
    }

    /**
     * 选中单元清空
     */
    private void selectedUnitClear() {
        selectedUnitBean = null;
        unitItems.clear();
        tvUnit.setText("");
    }

    /**
     * 选中楼宇清空
     */
    private void selectedBuildingClear() {
        cellItems.clear();
        selectedBuildingBean = null;
        tvBuilding.setText("");
    }

    private void initBuildingOptionPicker(TaskAreaListBean selestedAreaBean) {//条件选择器初始化，自定义布局
        cellItems.addAll(selestedAreaBean.getCellList());
        // 注意，自定义布局中，optionspicker 或者 timepicker 的布局必须要有（即WheelView内容部分），否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        pvBuildingOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectedBuildingBean = cellItems.get(options1);
                String tx = selectedBuildingBean.getPickerViewText();
                tvBuilding.setText(tx);
                requestUnitList(selectedBuildingBean.getValue());//请求单元列表
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
                                pvBuildingOptions.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvBuildingOptions.dismiss();
                            }
                        });
                    }
                })
                .build();
        pvBuildingOptions.setPicker(cellItems);//添加数据
    }


    private void initUnitOptionPicker(TaskAreaListBean.CellListBean cellListBean) {
        //还原初始状态
        selectedUnitClear();
        selectedRoomClear();
        List<TaskAreaListBean.CellListBean.UnitListBean> unitList = cellListBean.getUnitList();
        if (unitList.size() <= 0) {
            ToastUtil.showToast(getContext(), "数据为空");
            return;
        }
        unitItems.addAll(unitList);
        pvUnitOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectedUnitBean = unitItems.get(options1);
                String tx = selectedUnitBean.getPickerViewText();
                tvUnit.setText(tx);
                requestRoomList(selectedUnitBean.getValue());
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
                                pvUnitOptions.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvUnitOptions.dismiss();
                            }
                        });
                    }
                })
                .build();
        pvUnitOptions.setPicker(unitItems);//添加数据
    }


    private void initRoomOptionPicker(TaskAreaListBean.CellListBean.UnitListBean unitListBean) {
        //还原初始状态
        selectedRoomClear();
        List<BuildingRoomListBean> roomList = unitListBean.getRoomList();
        if (roomList.size() <= 0) {
            ToastUtil.showToast(getContext(), "数据为空");
            return;
        }
        roomItems.addAll(roomList);
        pvRoomOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectedRoomBean = roomItems.get(options1);
                String tx = selectedRoomBean.getPickerViewText();
                tvRoom.setText(tx);
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
                                pvRoomOptions.returnData(tvSubmit);
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvRoomOptions.dismiss();
                            }
                        });
                    }
                })
                .build();
        pvRoomOptions.setPicker(roomItems);//添加数据
    }


}
