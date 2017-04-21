package com.tianchuang.ihome_b.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.ImagesSelectorAdapter;
import com.tianchuang.ihome_b.adapter.SubmitMultiAdapter;
import com.tianchuang.ihome_b.base.BaseActivity;
import com.tianchuang.ihome_b.bean.CheakBean;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.bean.event.MyFormSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.event.TaskFormSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.model.FormModel;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.CustomLinearLayoutManager;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.MultipartBuilder;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Abyss on 2017/3/10.
 * description:表单申报页面
 */

public class FormSubmitActivity extends BaseActivity implements View.OnClickListener, SubmitMultiAdapter.SaveEditListener {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.ac_toolbar_toolbar)
    Toolbar acToolbarToolbar;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_submit_form)
    LinearLayout llSubmitForm;
    private FormTypeItemBean formTypeItemBean;
    private List<FormTypeItemBean.FieldsBean> fields;
    private SparseArray<String> editTexts;
    private SubmitMultiAdapter submitMultiAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_form;
    }

    @Override
    protected int getFragmentContainerId() {
        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initNormalToolbar(acToolbarToolbar);
        toolbarTitle.setText("表单申报");
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Serializable item = intent.getSerializableExtra("item");
            if (item != null) {
                formTypeItemBean = (FormTypeItemBean) item;
                initView(formTypeItemBean);
            }

        }
    }

    private void initView(FormTypeItemBean formTypeItemBean) {
        mRecyclerView.addItemDecoration(new CommonItemDecoration(DensityUtil.dip2px(this, 20)));
        fields = formTypeItemBean.getFields();
        if (fields.size() > 0) {
            editTexts = new SparseArray<>();
            submitMultiAdapter = new SubmitMultiAdapter(this, fields);
            submitMultiAdapter.setSaveEditListener(this);
            //添加头部
            submitMultiAdapter.addHeaderView(ViewHelper.getFormSubmitHeaderView(getString(R.string.form_type_string) + formTypeItemBean.getName()));
            //添加腿部
            View view = LayoutUtil.inflate(R.layout.form_submit_footer_holder);
            view.findViewById(R.id.tv_submit).setOnClickListener(this);
            submitMultiAdapter.addFooterView(view);
            mRecyclerView.setAdapter(submitMultiAdapter);
            CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(FormSubmitActivity.this);
            customLinearLayoutManager.setScrollEnabled(false);
            mRecyclerView.setLayoutManager(customLinearLayoutManager);
        }
    }

    public void startImageSelector(ImgSelConfig config, int type) {
        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调,三张图片
        if (resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (getImageByCodeListener != null) {
                getImageByCodeListener.onImages(pathList, requestCode);
            }
        }
    }


    public void setGetImageByCodeListener(GetImageByCodeListener getImageByCodeListener) {
        this.getImageByCodeListener = getImageByCodeListener;
    }

    public interface GetImageByCodeListener {
        void onImages(List<String> paths, int type);
    }

    private GetImageByCodeListener getImageByCodeListener;

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
                new Func2<HashMap<String, String>, HashMap<String, ArrayList<File>>, CheakBean>() {//检查是否可以提价
                    @Override
                    public CheakBean call(HashMap<String, String> submitTextMap, HashMap<String, ArrayList<File>> map) {//判断可否提交
                        boolean textIsPut = cheackTextIsPut(submitTextMap);
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
                    }
                })
                .compose(this.<CheakBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CheakBean, Boolean>() {
                    @Override
                    public Boolean call(CheakBean bean) {//弹出错误提示
                        boolean can = bean.isCan();
                        if (!can) {
                            dismissProgress();
                            ToastUtil.showToast(FormSubmitActivity.this, bean.getTip());
                        }
                        return can;
                    }
                })
                .observeOn(Schedulers.io())//图片压缩转换
                .map(new Func1<CheakBean, List<MultipartBody.Part>>() {
                    @Override
                    public List<MultipartBody.Part> call(CheakBean bean) {
                        List<MultipartBody.Part> parts = MultipartBuilder.filesToMultipartBodyParts(new ArrayList<File>(), "");
                        for (Map.Entry<String, ArrayList<File>> stringArrayListEntry : submitImagesFiles.entrySet()) {
                            ArrayList<File> values = stringArrayListEntry.getValue();
                            if (values.size() > 0) {
                                parts.addAll(MultipartBuilder.filesToMultipartBodyParts(values, stringArrayListEntry.getKey()));
                            }
                        }
                        return parts;
                    }
                })//请求网络
                .flatMap(new Func1<List<MultipartBody.Part>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<MultipartBody.Part> parts) {
                        return FormModel.formSubmit(formTypeItemBean.getId(), submitTextMap, parts).compose(RxHelper.<String>handleResult());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    protected void _onNext(String s) {
                        dismissProgress();
                        EventBus.getDefault().post(new MyFormSubmitSuccessEvent());//通知列表刷新
                        EventBus.getDefault().post(new TaskFormSubmitSuccessEvent());//通知任务详情刷新
                        ToastUtil.showToast(FormSubmitActivity.this, "申报成功");
                        finishWithAnim();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(FormSubmitActivity.this, message);
                        dismissProgress();
                    }

                    @Override
                    public void onCompleted() {

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    public void initNormalToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                finishWithAnim();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
