package com.tianchuang.ihome_b.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.DetailMultiItem;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesSelectorItemDecoration;
import com.tianchuang.ihome_b.utils.DensityUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:表单详情
 */
public class FormDetailMultiAdapter extends BaseMultiItemQuickAdapter<DetailMultiItem, BaseViewHolder> {
    public static final int TYPE_TEXT = 1;//文本
    public static final int TYPE_RADIO = 2;//单选
    public static final int TYPE_IMG = 3;//图片
    protected TransferImage transferLayout;

    public FormDetailMultiAdapter(List<DetailMultiItem> data) {
        super(data);
        addItemType(TYPE_TEXT, R.layout.form_multi_text_item_holder);
        addItemType(TYPE_RADIO, R.layout.form_multi_radio_item_holder);
        addItemType(TYPE_IMG, R.layout.form_multi_images_item_holder);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailMultiItem item) {
        switch (helper.getItemViewType()) {
            case TYPE_TEXT:
                helper.setText(R.id.tv_fieldKey, item.getFieldName() + "：")
                        .setText(R.id.tv_fieldValue, item.getFieldValue());
                break;
            case TYPE_RADIO:
                helper.setText(R.id.tv_fieldKey2, item.getFieldName() + "：")
                        .setText(R.id.tv_fieldValue2, item.getFieldValue());
                break;
            case TYPE_IMG:
                helper.setText(R.id.tv_images_title, item.getFieldName() + "：");
                final RecyclerView rvList = (RecyclerView) helper.getView(R.id.rv_list);
                final Context context = rvList.getContext();
                final List<String> imageStrList = item.getFieldValues();
                rvList.setLayoutManager(new GridLayoutManager(context, 2));
                rvList.addItemDecoration(new ImagesSelectorItemDecoration(DensityUtil.dip2px(context,5)));
                FaultDetailAdapter adapter = new FaultDetailAdapter(R.layout.fault_image_item_holder, imageStrList);
                rvList.setAdapter(adapter);
                rvList.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                        transferLayout = new TransferImage.Builder(context)
                                .setBackgroundColor(ContextCompat.getColor(context, R.color.black))
                                .setOriginImageList(wrapOriginImageViewList(imageStrList, rvList))
                                .setImageUrlList(imageStrList)
                                .setOriginIndex(position)
                                .create();
                        transferLayout.show();
                        EventBus.getDefault().post(new TransferLayoutEvent(transferLayout));
                    }
                });

                break;
        }

    }

    /**
     * 包装缩略图 ImageView 集合
     *
     * @param imageStrList
     * @param rvList
     * @return
     */

    protected List<ImageView> wrapOriginImageViewList(List<String> imageStrList, RecyclerView rvList) {
        List<ImageView> originImgList = new ArrayList<>();
        for (int i = 0; i < imageStrList.size(); i++) {
            ImageView thumImg = (ImageView) rvList.getChildAt(i);
            originImgList.add(thumImg);
        }
        return originImgList;
    }
}
