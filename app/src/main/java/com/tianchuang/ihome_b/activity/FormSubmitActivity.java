package com.tianchuang.ihome_b.activity;

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
 * description:
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
    private HashMap<String, String> radioTexts;
    private HashMap<String, String> textTexts;
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
//        EventBus.getDefault().register(this);
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
//            controlKeyboardLayout(llSubmitForm, mRecyclerView);
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

    private GetImageByCodeListener getImageByCodeListener;

    /**
     * 提交按钮
     */
    @Override
    public void onClick(View v) {
        final HashMap<String, String> submitTextMap = initSubmitTexts();
        addRadioTexts(submitTextMap);
        addEditTexts(submitTextMap);
        final ArrayList<File> submitImagesFiles = getSubmitImagesFiles();
        Observable.zip(Observable.just(submitTextMap), Observable.just(submitImagesFiles),
                new Func2<HashMap<String, String>, ArrayList<File>, CheakBean>() {//检查是否可以提价
                    @Override
                    public CheakBean call(HashMap<String, String> submitTextMap, ArrayList<File> files) {//判断可否提交
                        boolean textIsPut = cheackTextIsPut(submitTextMap);
                        boolean imagesIsPut = cheackImagesIsPut(files);
                        CheakBean cheakBean = new CheakBean();
                        if (textIsPut && imagesIsPut) {
                            cheakBean.setCan(true);
                        } else if (!textIsPut) {
                            cheakBean.setCan(false);
                            cheakBean.setTip("文本不能为空");
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
                        List<MultipartBody.Part> parts = MultipartBuilder.filesToMultipartBodyParts(submitImagesFiles, "photos");
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
                        ToastUtil.showToast(FormSubmitActivity.this, "成功");
                        dismissProgress();
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
     */
    private boolean cheackImagesIsPut(ArrayList<File> files) {
        boolean canSubmit = true;
        if (files.size() == 0) {
            canSubmit = false;
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
    private ArrayList<File> getSubmitImagesFiles() {
        ArrayList<File> imageFiles = new ArrayList<>();
        for (ImagesSelectorAdapter imagesSelectorAdapter : submitMultiAdapter.getImagesSelectorAdapters()) {
            imageFiles.addAll(submitMultiAdapter.getImageFiles(imagesSelectorAdapter));
        }
        return imageFiles;
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
    public void SaveEdit(int position, String string) {
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

    public interface GetImageByCodeListener {
        void onImages(List<String> paths, int type);
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