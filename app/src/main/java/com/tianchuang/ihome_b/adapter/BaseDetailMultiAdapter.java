package com.tianchuang.ihome_b.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abyss on 2017/3/2.
 * description:详情页列表适配器的基类
 */
public abstract class BaseDetailMultiAdapter<T extends MultiItemEntity, K extends BaseViewHolder>  extends BaseMultiItemQuickAdapter<T ,K>{
	public static final int TYPE_TEXT = 1;//文本
	public static final int TYPE_RADIO = 2;//单选
	public static final int TYPE_IMG = 3;//图片
	protected TransferImage transferLayout;

	public BaseDetailMultiAdapter(List<T> data) {
		super(data);
		addItemType(TYPE_TEXT, R.layout.multi_text_item_holder);
		addItemType(TYPE_RADIO, R.layout.multi_radio_item_holder);
		addItemType(TYPE_IMG, R.layout.multi_images_item_holder);
	}

	/**
	 * 包装缩略图 ImageView 集合
	 *
	 * @param imageStrList
	 * @param rvList
	 * @return
	 */
	@NonNull
	protected List<ImageView> wrapOriginImageViewList(List<String> imageStrList, RecyclerView rvList) {
		List<ImageView> originImgList = new ArrayList<>();
		for (int i = 0; i < imageStrList.size(); i++) {
			ImageView thumImg = (ImageView) rvList.getChildAt(i);
			originImgList.add(thumImg);
		}
		return originImgList;
	}
}
