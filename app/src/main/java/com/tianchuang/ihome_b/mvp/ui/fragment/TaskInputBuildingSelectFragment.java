package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.TaskBuildingAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.BuildingRoomItemBean;
import com.tianchuang.ihome_b.bean.SelectedBean;
import com.tianchuang.ihome_b.bean.TaskAreaListBean;
import com.tianchuang.ihome_b.bean.TaskInputDetailBean;
import com.tianchuang.ihome_b.bean.TaskInputResponseBean;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.bean.recyclerview.TaskInputSelectDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.ui.activity.MyTaskActivity;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Abyss on 2017/3/16.
 * description:录入住户位置信息
 */

public class TaskInputBuildingSelectFragment extends BaseFragment {
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_name)
    TextView tvName;
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
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.ll_building)
    LinearLayout llBuilding;
    @BindView(R.id.ll_unit)
    LinearLayout llUnit;
    @BindView(R.id.ll_room)
    LinearLayout llRoom;
    private List<TaskAreaListBean> mData = new ArrayList<>();
    private TaskAreaListBean selestedBean;//被选中的小区
    private TaskAreaListBean.CellListBean selectedBuildingBean;//被选中的楼宇
    private TaskAreaListBean.CellListBean.UnitListBean selectedUnitBean;//被选中的单元
    private BuildingRoomItemBean selectedRoomBean;//被选中的房间
    private OptionsPickerView pvBuildingOptions;
    private OptionsPickerView pvUnitOptions;
    private OptionsPickerView pvRoomOptions;
    private ArrayList<TaskAreaListBean.CellListBean> cellItems = new ArrayList<>();
    private ArrayList<TaskAreaListBean.CellListBean.UnitListBean> unitItems = new ArrayList<>();
    private ArrayList<BuildingRoomItemBean> roomItems = new ArrayList<>();
    private TaskBuildingAdapter adapter;
    private TaskInputDetailBean taskBean;
    private MyTaskActivity holdingActivity;
    private boolean isAreaHide = false;
    private boolean isBuildingHide = false;
    private boolean isUnitHide = false;
    private boolean isRoomHide = false;//必有房间号

    public static TaskInputBuildingSelectFragment newInstance(TaskInputDetailBean taskBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("taskBean", taskBean);
        TaskInputBuildingSelectFragment taskInputSelectFragment = new TaskInputBuildingSelectFragment();
        taskInputSelectFragment.setArguments(bundle);
        return taskInputSelectFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_input_select;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("数据录入");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        holdingActivity = (MyTaskActivity) getHoldingActivity();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        taskBean = (TaskInputDetailBean) getArguments().getSerializable("taskBean");
        tvName.setText(StringUtils.getNotNull(taskBean.getTaskName()));
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvList.addItemDecoration(new TaskInputSelectDecoration(DensityUtil.dip2px(getContext(), 5)));
        mData.clear();
        llBuilding.setVisibility(View.GONE);
        llUnit.setVisibility(View.GONE);
        llRoom.setVisibility(View.GONE);
        isAreaHide = taskBean.isHide();
        setMyVisibility(llArea,isAreaHide);
        Observable.fromIterable(taskBean.getBuildingList())
                .filter(taskBuildingListBean -> taskBuildingListBean.isUsed()
                )
                .compose(this.<TaskAreaListBean>bindToLifecycle())
                .subscribe(new Observer<TaskAreaListBean>() {
                    @Override
                    public void onComplete() {
                        selestedBean = null;
                        adapter = new TaskBuildingAdapter(mData);
                        rvList.setAdapter(adapter);
                        initBuildingOptionPicker();//初始化楼宇选择器
                        if (isAreaHide) {
                            selectArea(0);
                        } else {
                            rvList.addOnItemTouchListener(new OnItemClickListener() {
                                @Override
                                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    selectArea(position);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }


                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(TaskAreaListBean taskAreaListBean) {
                        mData.add(taskAreaListBean);
                    }
                });

    }

    private void setMyVisibility(LinearLayout ll, boolean hide) {
        ll.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    private void roomsClear() {
        selectedRoomBean = null;
        roomItems.clear();
        tvRoom.setText("");
    }

    private void unitsClear() {
        selectedUnitBean = null;
        unitItems.clear();
        tvUnit.setText("");
    }

    private void cellsClear() {
        tvBuilding.setText("");
        selectedBuildingBean = null;
        cellItems.clear();
    }


    @OnClick({R.id.tv_building, R.id.tv_unit, R.id.tv_room, R.id.bt_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_building:
                if (selestedBean != null) {
                    cellItems.clear();
                    List<TaskAreaListBean.CellListBean> cellList = selestedBean.getCellList();
                    if (cellList.size() <= 0) {
                        ToastUtil.showToast(getContext(), "数据为空");
                        return;
                    }
                    Observable.fromIterable(cellList)
                            .filter(cellListBean -> {
                                        return cellListBean.isUsed();
                                    }
                            )
                            .compose(this.<TaskAreaListBean.CellListBean>bindToLifecycle())
                            .subscribe(new Observer<TaskAreaListBean.CellListBean>() {
                                @Override
                                public void onComplete() {
                                    pvBuildingOptions.setPicker(cellItems);
                                    pvBuildingOptions.show();
                                }


                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(TaskAreaListBean.CellListBean cellListBean) {
                                    cellItems.add(cellListBean);

                                }
                            });


                } else {
                    ToastUtil.showToast(getContext(), "楼宇数据为空");
                }

                break;
            case R.id.tv_unit:
                if (unitItems.size() > 0) {
                    pvUnitOptions.show();
                } else {
                    ToastUtil.showToast(getContext(), "单元数据为空");
                }
                break;
            case R.id.tv_room:
                if (roomItems.size() > 0) {
                    pvRoomOptions.show();
                } else {
                    ToastUtil.showToast(getContext(), "房间数据为空");
                }
                break;
            case R.id.bt_sure://确认
                String roomText = tvRoom.getText().toString().trim();
                if (selectedUnitBean != null && taskBean != null && !TextUtils.isEmpty(roomText)) {
                    Integer roomId = Integer.valueOf(roomText);
                    getTaskInputResponseBeanObservable(roomId)
                            .subscribe(new RxSubscribe<TaskInputResponseBean>() {
                                @Override
                                public void _onNext(TaskInputResponseBean taskInputResponseBean) {
                                    if (taskInputResponseBean != null) {
                                        FragmentUtils.popAddFragment(getFragmentManager(),
                                                holdingActivity.getFragmentContainerId(),
                                                TaskInputEditDataFragment.newInstance(taskInputResponseBean), true);
                                    } else {
                                        ToastUtil.showToast(getContext(), "该房间不存在");
                                    }
                                }

                                @Override
                                public void _onError(String message) {
                                    ToastUtil.showToast(getContext(), message);
                                    dismissProgress();
                                }

                                @Override
                                public void onComplete() {
                                    dismissProgress();
                                }
                            });
                } else {
                    ToastUtil.showToast(getContext(), "请完善数据");
                }

                break;
        }
    }

    @NonNull
    private Observable<TaskInputResponseBean> getTaskInputResponseBeanObservable(Integer roomId) {
        return MyTaskModel.INSTANCE.taskInputSubmit(taskBean.getTaskRecordId(), selectedUnitBean.getBuildingId(), selectedUnitBean.getBuildingCellId(), selectedUnitBean.getId(), roomId)
                .compose(RxHelper.<TaskInputResponseBean>handleResult())
                .compose(this.<TaskInputResponseBean>bindToLifecycle())
                .doOnSubscribe(o -> {
                            showProgress();
                        }
                );
    }


    private void initBuildingOptionPicker() {//条件选择器初始化，自定义布局

        // 注意，自定义布局中，optionspicker 或者 timepicker 的布局必须要有（即WheelView内容部分），否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        pvBuildingOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectBuilding(options1);
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

    private void selectArea(int position) {
        for (TaskAreaListBean data : mData) {
            data.setSelected(false);
        }
        selestedBean = mData.get(position);
        selestedBean.setSelected(true);
        cellsClear();
        unitsClear();
        roomsClear();
        isBuildingHide = selestedBean.isHide();
        setMyVisibility(llBuilding,isBuildingHide);
        setMyVisibility(llUnit,true);
        setMyVisibility(llRoom,true);
        if (isBuildingHide)selectBuilding(0);
    }

    private void selectBuilding(int position) {
        //还原初始状态
        unitsClear();
        roomsClear();
        selectedBuildingBean = selestedBean.getCellList().get(position);
        initUnitOptionPicker(selectedBuildingBean);
        String tx = selectedBuildingBean.getPickerViewText();
        tvBuilding.setText(tx);
        isUnitHide = selectedBuildingBean.isHide();
        setMyVisibility(llUnit,isUnitHide);
        setMyVisibility(llRoom,true);
        if (isUnitHide)selectUnit(0);

    }
    private void selectUnit(int position) {
        selectedUnitBean = selectedBuildingBean.getUnitList().get(position);
        String tx = selectedUnitBean.getPickerViewText();
        tvUnit.setText(tx);
        //还原初始状态
        roomsClear();
        requestRooms(selectedUnitBean);
    }

    private void initUnitOptionPicker(TaskAreaListBean.CellListBean cellListBean) {


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
                selectUnit(options1);
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



    private void initRoomsOptionPicker(ArrayList<BuildingRoomItemBean> roomlist) {
        roomItems.addAll(roomlist);
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

    /**
     * 请求房间列表
     */
    private void requestRooms(TaskAreaListBean.CellListBean.UnitListBean selectedUnitBean) {
        MyTaskModel.INSTANCE.requestRoomNumList(selectedUnitBean.getBuildingId(), selectedUnitBean.getBuildingCellId(), selectedUnitBean.getId())
                .compose(RxHelper.handleResult())
                .compose(bindToLifecycle())
                .subscribe(new RxSubscribe<ArrayList<BuildingRoomItemBean>>() {
                    @Override
                    public void _onNext(ArrayList<BuildingRoomItemBean> roomslist) {
                        setMyVisibility(llRoom,isRoomHide);
                        if (roomslist.size() == 0) {
                            showToast("房间列表为空");
                            return;
                        }
                        initRoomsOptionPicker(roomslist);

                    }

                    @Override
                    public void _onError(String message) {
                        showToast(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        for (TaskAreaListBean data : mData) {//还原ui状态
            data.setSelected(false);
        }
        adapter.notifyDataSetChanged();
        super.onDestroy();
    }

}
