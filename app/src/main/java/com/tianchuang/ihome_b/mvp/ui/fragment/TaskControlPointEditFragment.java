package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.ImagesSelectorAdapter;
import com.tianchuang.ihome_b.adapter.TaskSubmitMultiAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.CheakBean;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.bean.event.NotifyHomePageRefreshEvent;
import com.tianchuang.ihome_b.bean.event.TaskFormSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.ui.activity.TaskSelectActivity;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.MultipartBuilder;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * Created by Abyss on 2017/3/20.
 * description:控制点的编辑提交
 */

public class TaskControlPointEditFragment extends BaseFragment implements TaskSubmitMultiAdapter.SaveEditListener, View.OnClickListener {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    //
    private TaskSubmitMultiAdapter submitMultiAdapter;
    private List<FormTypeItemBean.FieldsBean> fields;
    private SparseArray<String> editTexts;
    private TaskSelectActivity holdingActivity;
    private FormTypeItemBean formTypeItemBean;
    private int taskRecordId;

    public static TaskControlPointEditFragment newInstance(int taskRecordId, FormTypeItemBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putInt("taskRecordId", taskRecordId);
        TaskControlPointEditFragment fragment = new TaskControlPointEditFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("填写表单");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_control_point_edit;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        holdingActivity = ((TaskSelectActivity) getHoldingActivity());
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //添加头部
//        View header = LayoutUtil.inflate(R.layout.control_point_header);

//        TextView tvPointAddress = (TextView) header.findViewById(R.id.tv_point_address);
//        TextView tvPointDate = (TextView) header.findViewById(R.id.tv_point_date);
//        TextView tvPointName = (TextView) header.findViewById(R.id.tv_point_name);
        FormTypeItemBean formTypeItemBean = (FormTypeItemBean) getArguments().getSerializable("bean");
        taskRecordId = getArguments().getInt("taskRecordId");
        if (formTypeItemBean != null) {
//            tvPointAddress.setText(StringUtils.getNotNull(formTypeItemBean.getPlace()));
//            tvPointDate.setText(StringUtils.getNotNull(formTypeItemBean.getTime()));
            this.formTypeItemBean = formTypeItemBean;
        }

        mRecyclerView.addItemDecoration(new CommonItemDecoration(DensityUtil.dip2px(getContext(), 20)));
        if (this.formTypeItemBean != null) {
            fields = this.formTypeItemBean.getFields();
            if (fields.size() > 0) {
                editTexts = new SparseArray<>();
                submitMultiAdapter = new TaskSubmitMultiAdapter(holdingActivity, fields);
                submitMultiAdapter.setSaveEditListener(this);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                //添加腿部
                View footer = LayoutUtil.inflate(R.layout.form_submit_footer_holder);
                TextView submitBtn = (TextView) footer.findViewById(R.id.tv_submit);
                submitBtn.setText("完成");
                submitBtn.setOnClickListener(this);
                submitMultiAdapter.addFooterView(footer);
                submitMultiAdapter.addHeaderView(ViewHelper.getFormSubmitHeaderView(formTypeItemBean.getName()));
                mRecyclerView.setAdapter(submitMultiAdapter);

            }
        }

    }


    /**
     * 提交按钮
     */
    @Override
    public void onClick(View v) {
        final HashMap<String, String> submitTextMap = initSubmitTexts();
        addRadioTexts(submitTextMap);
        addEditTexts(submitTextMap);
        final HashMap<String, ArrayList<File>> submitImagesFiles = getSubmitImagesFiles();
        Observable.zip(Observable.just(submitTextMap), Observable.just(submitImagesFiles),
                (submitTextMap1, map) -> {//判断可否提交
                    boolean textIsPut = cheackTextIsPut(submitTextMap1);
                    boolean imagesIsPut = cheackImagesIsPut(map);
                    CheakBean cheakBean = new CheakBean();
                    if (textIsPut && imagesIsPut) {
                        cheakBean.setCan(true);
                    } else if (!textIsPut) {
                        cheakBean.setCan(false);
                        cheakBean.setTip("文本或选项不能为空");
                    } else if (!imagesIsPut) {
                        cheakBean.setCan(false);
                        cheakBean.setTip("图片不能为空");
                    }
                    return cheakBean;
                })
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(bean -> {//弹出错误提示
                    boolean can = bean.isCan();
                    if (!can) {
                        dismissProgress();
                        ToastUtil.showToast(getContext(), bean.getTip());
                    }
                    return can;
                })
                .observeOn(Schedulers.io())//图片压缩转换
                .map(bean -> {
                    List<MultipartBody.Part> parts = MultipartBuilder.filesToMultipartBodyParts(new ArrayList<File>(), "");
                    for (Map.Entry<String, ArrayList<File>> stringArrayListEntry : submitImagesFiles.entrySet()) {
                        ArrayList<File> values = stringArrayListEntry.getValue();
                        if (values.size() > 0) {
                            parts.addAll(MultipartBuilder.filesToMultipartBodyParts(values, stringArrayListEntry.getKey()));
                        }
                    }
                    return parts;
                })//请求网络
                .flatMap(parts -> MyTaskModel.taskFormSubmit(taskRecordId, formTypeItemBean.getId(), submitTextMap, parts)
                        .compose(RxHelper.handleResult())
                )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(o -> showProgress())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {
                        dismissProgress();
                        ToastUtil.showToast(getContext(), "任务提交成功");
                        removeFragment();
                        EventBus.getDefault().post(new TaskFormSubmitSuccessEvent());
                        EventBus.getDefault().post(new NotifyHomePageRefreshEvent());//通知主页刷新
                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private HashMap<String, String> initSubmitTexts() {
        HashMap<String, String> map = new HashMap<>();
        for (FormTypeItemBean.FieldsBean field : fields) {
            if (field.getType() == 1 || field.getType() == 2) {
                map.put(field.getFieldKey(), "");
            }
        }
        return map;
    }

    /**
     * 检查图片是否可以提交
     *
     * @param map
     */
    private boolean cheackImagesIsPut(HashMap<String, ArrayList<File>> map) {
        boolean canSubmit = true;
        for (Map.Entry<String, ArrayList<File>> entry : map.entrySet()) {
            FormTypeItemBean.FieldsBean field = getItemBeanByKeyField(entry.getKey());
            if (field != null && field.isMustInput()) {
                if (entry.getValue().size() == 0) {
                    canSubmit = false;
                }
            }
        }
        return canSubmit;
    }

    /**
     * 检查文本是否可以提交
     */
    private boolean cheackTextIsPut(HashMap<String, String> submitTextMap) {
        boolean canSubmit = true;
        for (Map.Entry<String, String> stringStringEntry : submitTextMap.entrySet()) {
            FormTypeItemBean.FieldsBean field = getItemBeanByKeyField(stringStringEntry.getKey());
            if (field != null && field.isMustInput()) {
                if (TextUtils.isEmpty(stringStringEntry.getValue())) {
                    canSubmit = false;
                }
            }
        }
        return canSubmit;
    }

    /**
     * 获取提交的图片资源文件
     */
    private HashMap<String, ArrayList<File>> getSubmitImagesFiles() {
        HashMap<String, ArrayList<File>> fileHashMap = new HashMap<>();
        for (ImagesSelectorAdapter imagesSelectorAdapter : submitMultiAdapter.getImagesSelectorAdapters()) {
            fileHashMap.put(imagesSelectorAdapter.getKeyField(), submitMultiAdapter.getImageFiles(imagesSelectorAdapter));
        }
        return fileHashMap;
    }


    //    获取单选的提交文本
    public void addRadioTexts(HashMap<String, String> map) {
        for (FormTypeItemBean.FieldsBean field : fields) {
            if (field.getType() == 2) {//单选
                map.put(field.getFieldKey(), field.getRadioText());
            }
        }
    }

    //获取edittext的提交文本
    public void addEditTexts(HashMap<String, String> map) {
        for (int i = 0; i < editTexts.size(); i++) {
            int key = editTexts.keyAt(i);
            FormTypeItemBean.FieldsBean fieldsBean = fields.get(key);
            String s = editTexts.get(key);
            map.put(fieldsBean.getFieldKey(), s);
        }
    }

    //动态保存列表中edittext的文本
    @Override
    public void saveEdit(int position, String string) {
        editTexts.put(position, string);
    }

    /**
     * 根据keyfield找到对应的itembean
     */
    public FormTypeItemBean.FieldsBean getItemBeanByKeyField(String key) {
        for (FormTypeItemBean.FieldsBean field : fields) {
            if (key.equals(field.getFieldKey())) {
                return field;
            }
        }
        return null;
    }
}
