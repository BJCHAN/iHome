package com.tianchuang.ihome_b.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.activity.TaskSelectActivity;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;
import com.tianchuang.ihome_b.bean.ImagesMultipleItem;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesSelectorItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.MyGridLayoutManager;
import com.tianchuang.ihome_b.bean.recyclerview.SubmitRadioDecoration;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.ImagesSelectorUtils;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:任务提交的adapter
 */
public class TaskSubmitMultiAdapter extends BaseMultiItemQuickAdapter<FormTypeItemBean.FieldsBean, BaseViewHolder> {
    public static final int TYPE_TEXT = 1;//文本
    public static final int TYPE_RADIO = 2;//单选
    public static final int TYPE_IMG = 3;//图片
    private final TaskSelectActivity formSubmitActivity;
    private ArrayList<ImagesSelectorAdapter> imagesSelectorAdapters;//图片选择的adapter

    public ArrayList<ImagesSelectorAdapter> getImagesSelectorAdapters() {
        return imagesSelectorAdapters;
    }

    public TaskSubmitMultiAdapter(TaskSelectActivity formSubmitActivity, List<FormTypeItemBean.FieldsBean> data) {
        super(data);
        this.formSubmitActivity = formSubmitActivity;
        addItemType(TYPE_TEXT, R.layout.item_submit_task_multi_text_holder);
        addItemType(TYPE_RADIO, R.layout.list_multi_radio_holder);
        addItemType(TYPE_IMG, R.layout.list_multi_image_holder);
        imagesSelectorAdapters = new ArrayList<>();
        formSubmitActivity.setGetImageByCodeListener(new TaskSelectActivity.GetImageByCodeListener() {
            @Override
            public void onImages(List<String> paths, int type) {
                receiveImages(paths, getImageSelectorAdapterById(type));
            }
        });
    }


    protected void convert(BaseViewHolder helper, final FormTypeItemBean.FieldsBean item) {
        switch (helper.getItemViewType()) {
            case TYPE_TEXT://提交文本
                helper.setText(R.id.tv_title, getNotNull(item.getName()));
                EditText editText = (EditText) helper.getView(R.id.et_content);
                editText.setTag(helper.getLayoutPosition() - getHeaderLayoutCount());//设置数据的位置
                editText.addTextChangedListener(new TextSwitcher(helper));
                int sizeLimit = item.getSizeLimit();
                if (sizeLimit > 0) {//限制字数
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(sizeLimit)});
                }
                break;
            case TYPE_RADIO://单选列表
                helper.setText(R.id.tv_radio_name, getNotNull(item.getName()));
                RecyclerView radioList = (RecyclerView) helper.getView(R.id.rv_radio_list);
                radioList.setLayoutManager(new MyGridLayoutManager(radioList.getContext(), 5,radioList.getMeasuredWidth()));
                List<FormTypeItemBean.FieldsBean.FieldExtrasBean> fieldExtras = item.getFieldExtras();
                if (fieldExtras.size() > 0) {
                    RadioTypeAdapter radioTypeAdapter = new RadioTypeAdapter(R.layout.form_type_radio_item_holder, fieldExtras);
                    radioList.setAdapter(radioTypeAdapter);
                    radioList.addItemDecoration(new SubmitRadioDecoration(DensityUtil.dip2px(radioList.getContext(), 20)));
                    radioList.addOnItemTouchListener(new OnItemClickListener() {
                        @Override
                        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                            RadioTypeAdapter radioTypeAdapter = (RadioTypeAdapter) adapter;
                            List<FormTypeItemBean.FieldsBean.FieldExtrasBean> mData = radioTypeAdapter.getData();
                            for (FormTypeItemBean.FieldsBean.FieldExtrasBean data : mData) {//实现单选
                                data.setSelected(false);
                            }
                            FormTypeItemBean.FieldsBean.FieldExtrasBean fieldExtrasBean = mData.get(position);
                            fieldExtrasBean.setSelected(true);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                break;
            case TYPE_IMG:
                helper.setText(R.id.tv_images_name, getNotNull(item.getName()));
                RecyclerView imageRecyclerView = (RecyclerView) helper.getView(R.id.rv_image_list);
                final Context imContext = imageRecyclerView.getContext();
                imageRecyclerView.addItemDecoration(new ImagesSelectorItemDecoration(5));
                imageRecyclerView.setLayoutManager(new GridLayoutManager(imContext, 3));
                ImgSelConfig config = ImagesSelectorUtils.getImgSelConfig();
                //初始化图片选择器
                ImagesSelectorAdapter selectorAdapter = new ImagesSelectorAdapter(getListData());
                selectorAdapter.setConfig(config);
                selectorAdapter.setId(item.getId());
                selectorAdapter.setKeyField(item.getFieldKey());
                selectorAdapter.setMustPut(item.isMustInput());
                imagesSelectorAdapters.add(selectorAdapter);//存储数据
                imageRecyclerView.setAdapter(selectorAdapter);
                imageRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ImagesSelectorAdapter imagesSelectorAdapter = (ImagesSelectorAdapter) adapter;
                        ImagesMultipleItem item = imagesSelectorAdapter.getData().get(position);
                        switch (item.getItemType()) {
                            case ImagesMultipleItem.ADD_IMG:
                                ImgSelConfig itemConfig = imagesSelectorAdapter.getConfig();
                                if (itemConfig.maxNum > 0) {
                                    formSubmitActivity.startImageSelector(itemConfig, imagesSelectorAdapter.getId());//去选择图片
                                } else {
                                    ToastUtil.showToast(formSubmitActivity, "选择图片数量到达上限");
                                }
                                break;
                            case ImagesMultipleItem.IMG:
                                removeImage(imagesSelectorAdapter, position);
                                break;
                        }
                    }
                });
                break;
        }
    }

    //自定义EditText的监听类
    class TextSwitcher implements TextWatcher {

        private BaseViewHolder helper;

        public TextSwitcher(BaseViewHolder mHolder) {
            this.helper = mHolder;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //用户输入完毕后，处理输入数据，回调给主界面处理

        }

        @Override
        public void afterTextChanged(Editable s) {
            //用户输入完毕后，处理输入数据，回调给主界面处理
            if (s != null) {
                EditText editText = (EditText) helper.getView(R.id.et_content);
                saveEditListener.SaveEdit(Integer.parseInt(editText.getTag().toString()), s.toString());
            }

        }
    }

    private SaveEditListener saveEditListener;

    public void setSaveEditListener(SaveEditListener saveEditListener) {
        this.saveEditListener = saveEditListener;
    }

    public interface SaveEditListener {

        void SaveEdit(int position, String string);
    }

    private String getNotNull(String text) {
        return StringUtils.getNotNull(text);
    }

    //初始化图片选择的数据
    public ArrayList<ImagesMultipleItem> getListData() {
        ArrayList<ImagesMultipleItem> list = new ArrayList<>();
        list.add(new ImagesMultipleItem(ImagesMultipleItem.ADD_IMG));
        return list;
    }

    /**
     * 根据id获取指定id
     */
    public ImagesSelectorAdapter getImageSelectorAdapterById(int id) {
        for (ImagesSelectorAdapter imagesSelectorAdapter : imagesSelectorAdapters) {
            if (imagesSelectorAdapter.getId() == id) {
                return imagesSelectorAdapter;
            }
        }
        return null;
    }


    private void receiveImages(List<String> paths, ImagesSelectorAdapter imagesSelectorAdapter) {
        List<ImagesMultipleItem> mData = imagesSelectorAdapter.getData();
        ImgSelConfig config = imagesSelectorAdapter.getConfig();
        for (String s : paths) {
            mData.add(mData.size() - 1, new ImagesMultipleItem(ImagesMultipleItem.IMG).setUrl(s));
        }
        imagesSelectorAdapter.notifyItemRangeInserted(mData.size() - 1, paths.size());
        config.maxNum -= paths.size();//图片上限减去选中数量
        if (config.maxNum <= 0) {//达到图片选择上限,去掉添加按钮
            imagesSelectorAdapter.remove(mData.size() - 1);
        }
    }

    /**
     * 删除图片
     */
    private void removeImage(ImagesSelectorAdapter adapter, int position) {
        ImgSelConfig config = adapter.getConfig();
        config.maxNum += 1;//图片上限加上删除数量
        List data = adapter.getData();
        data.remove(position);
        adapter.notifyItemRemoved(position);
        //判断添加图片按钮是否存在
        if (data.size() > 0 && (ImagesMultipleItem.IMG == adapter.getItemViewType(data.size() - 1))) {
            //不存在，添加按钮
            data.add(data.size(), new ImagesMultipleItem(ImagesMultipleItem.ADD_IMG));
            adapter.notifyItemInserted(data.size());
        }
    }

    /**
     * 拿到adpter中的图片文件
     */
    public ArrayList<File> getImageFiles(ImagesSelectorAdapter adapter) {
        List<ImagesMultipleItem> mData = adapter.getData();
        ArrayList<File> files = new ArrayList<>();
        for (ImagesMultipleItem imagesMultipleItem : mData) {
            if (imagesMultipleItem.getItemType() == imagesMultipleItem.IMG) {
                files.add(new File(imagesMultipleItem.getUrl()));
            }
        }
        return files;
    }
}
