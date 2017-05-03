package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.BuildingRoomItemBean;
import com.tianchuang.ihome_b.mvp.ui.activity.MyTaskActivity;
import com.tianchuang.ihome_b.adapter.TaskBuildingAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.TaskAreaListBean;
import com.tianchuang.ihome_b.bean.TaskInputDetailBean;
import com.tianchuang.ihome_b.bean.TaskInputResponseBean;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.bean.recyclerview.TaskInputSelectDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;

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
    private List<TaskAreaListBean> mData = new ArrayList<>();
    private TaskAreaListBean selestedBean;
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
        Observable.from(taskBean.getBuildingList())
                .filter(new Func1<TaskAreaListBean, Boolean>() {
                    @Override
                    public Boolean call(TaskAreaListBean taskBuildingListBean) {
                        return taskBuildingListBean.isUsed();
                    }
                })
                .compose(this.<TaskAreaListBean>bindToLifecycle())
                .subscribe(new Subscriber<TaskAreaListBean>() {
                    @Override
                    public void onCompleted() {
                        selestedBean = null;
                        adapter = new TaskBuildingAdapter(mData);
                        rvList.setAdapter(adapter);
                        initBuildingOptionPicker();//初始化楼宇选择器
                        rvList.addOnItemTouchListener(new OnItemClickListener() {
                            @Override
                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                for (TaskAreaListBean data : mData) {
                                    data.setSelected(false);
                                }
                                selestedBean = mData.get(position);
                                selestedBean.setSelected(true);
                                cellsClear();
                                unitsClear();
                                roomsClear();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TaskAreaListBean taskBuildingListBean) {
                        mData.add(taskBuildingListBean);
                    }
                });

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


    @OnClick({R.id.tv_building, R.id.tv_unit, R.id.tv_room,R.id.bt_sure})
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
                    Observable.from(cellList)
                            .filter(new Func1<TaskAreaListBean.CellListBean, Boolean>() {
                                @Override
                                public Boolean call(TaskAreaListBean.CellListBean cellListBean) {
                                    return cellListBean.isUsed();
                                }
                            })
                            .compose(this.<TaskAreaListBean.CellListBean>bindToLifecycle())
                            .subscribe(new Subscriber<TaskAreaListBean.CellListBean>() {
                                @Override
                                public void onCompleted() {
                                    pvBuildingOptions.setPicker(cellItems);
                                    pvBuildingOptions.show();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(TaskAreaListBean.CellListBean cellListBean) {
                                    cellItems.add(cellListBean);

                                }
                            });


                } else {
                    ToastUtil.showToast(getContext(), "请先选择小区");
                }

                break;
            case R.id.tv_unit:
                if (unitItems.size() > 0) {
                    pvUnitOptions.show();
                } else {
                    ToastUtil.showToast(getContext(), "请先选择楼宇");
                }
                break;
            case R.id.tv_room:
                if (roomItems.size() > 0) {
                    pvRoomOptions.show();
                } else {
                    ToastUtil.showToast(getContext(), "请先选择单元");
                }
                break;
            case R.id.bt_sure://确认
                String roomText = tvRoom.getText().toString().trim();
                if (selectedUnitBean != null && taskBean != null && !TextUtils.isEmpty(roomText)) {
                    Integer roomId = Integer.valueOf(roomText);
                    getTaskInputResponseBeanObservable(roomId)
                            .subscribe(new RxSubscribe<TaskInputResponseBean>() {
                                @Override
                                protected void _onNext(TaskInputResponseBean taskInputResponseBean) {
                                    if (taskInputResponseBean != null) {
                                        FragmentUtils.popAddFragment(getFragmentManager(),
                                                holdingActivity.getFragmentContainerId(),
                                                TaskInputEditDataFragment.newInstance(taskInputResponseBean), true);
                                    } else {
                                        ToastUtil.showToast(getContext(), "该房间不存在");
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
                } else {
                    ToastUtil.showToast(getContext(), "请完善数据");
                }

                break;
        }
    }

    @NonNull
    private Observable<TaskInputResponseBean> getTaskInputResponseBeanObservable(Integer roomId) {
        return MyTaskModel.taskInputSubmit(taskBean.getTaskRecordId(), selectedUnitBean.getBuildingId(), selectedUnitBean.getBuildingCellId(), selectedUnitBean.getId(), roomId)
                .compose(RxHelper.<TaskInputResponseBean>handleResult())
                .compose(this.<TaskInputResponseBean>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                });
    }


    private void initBuildingOptionPicker() {//条件选择器初始化，自定义布局

        // 注意，自定义布局中，optionspicker 或者 timepicker 的布局必须要有（即WheelView内容部分），否则会报空指针
        // 具体可参考demo 里面的两个自定义布局
        pvBuildingOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selectedBuildingBean = cellItems.get(options1);
                String tx = selectedBuildingBean.getPickerViewText();
                tvBuilding.setText(tx);
                initUnitOptionPicker(selectedBuildingBean);
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
        unitsClear();
        roomsClear();
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
                requestRooms(selectedUnitBean);
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
        //还原初始状态
        roomsClear();
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
        MyTaskModel.requestRoomNumList(selectedUnitBean.getBuildingId(), selectedUnitBean.getBuildingCellId(), selectedUnitBean.getId())
                .compose(RxHelper.handleResult())
                .compose(bindToLifecycle())
                .subscribe(new RxSubscribe<ArrayList<BuildingRoomItemBean>>() {
                    @Override
                    protected void _onNext(ArrayList<BuildingRoomItemBean> roomslist) {
                        if (roomslist.size() == 0) {
                            showToast("房间列表为空");
                            return;
                        }
                        initRoomsOptionPicker(roomslist);

                    }

                    @Override
                    protected void _onError(String message) {
                        showToast(message);
                    }

                    @Override
                    public void onCompleted() {

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
