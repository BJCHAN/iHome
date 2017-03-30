package com.tianchuang.ihome_b.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.ImagesMultipleItem;
import com.tianchuang.ihome_b.utils.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

/**
 * Created by Abyss on 2017/2/25.
 * description:图片选择的adapter
 */

public class ImagesSelectorAdapter extends BaseMultiItemQuickAdapter<ImagesMultipleItem, BaseViewHolder> {
    private ImgSelConfig config;//选择图片需要的属性
    private int id;
    private boolean isMustPut;

    public boolean isMustPut() {
        return isMustPut;
    }

    public void setMustPut(boolean mustPut) {
        isMustPut = mustPut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImgSelConfig getConfig() {
        return config;
    }

    public void setConfig(ImgSelConfig config) {
        this.config = config;
    }

    public String keyField;

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public ImagesSelectorAdapter(List<ImagesMultipleItem> data) {
        super(data);

        addItemType(ImagesMultipleItem.ADD_IMG, R.layout.images_add_item_holder);
        addItemType(ImagesMultipleItem.IMG, R.layout.images_normal_item_holder);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImagesMultipleItem item) {
        switch (helper.getItemViewType()) {
            case ImagesMultipleItem.ADD_IMG:
                helper.setImageResource(R.id.iv_add, R.drawable.add_photo_icon);
                break;
            case ImagesMultipleItem.IMG:
                ImageView imageView = helper.getView(R.id.iv_img);
                ImageLoader.load(item.getUrl(), imageView);
                break;
        }

    }
}
