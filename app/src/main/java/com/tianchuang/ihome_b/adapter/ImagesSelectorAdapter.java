package com.tianchuang.ihome_b.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesMultipleItem;

import java.util.List;

/**
 * Created by Abyss on 2017/2/25.
 * description:
 */

public class ImagesSelectorAdapter extends BaseMultiItemQuickAdapter<ImagesMultipleItem, BaseViewHolder> {

	public ImagesSelectorAdapter(List<ImagesMultipleItem> data) {
		super(data);
		data.add(new ImagesMultipleItem(ImagesMultipleItem.ADD_IMG));//添加图片按钮
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
				Glide.with(imageView.getContext()).load(item.getUrl()).placeholder(R.mipmap.default_logo).into(imageView);
				break;
		}

	}
}
