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
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesSelectorItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallRepairDetailMultiItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:故障的适配器
 */
public class FaultDetailMultiAdapter extends BaseDetailMultiAdapter<RobHallRepairDetailMultiItem,BaseViewHolder> {

	public FaultDetailMultiAdapter(List<RobHallRepairDetailMultiItem> data) {
		super(data);
	}

	@Override
	protected void convert(BaseViewHolder helper, RobHallRepairDetailMultiItem item) {
		switch (helper.getItemViewType()) {
			case TYPE_TEXT:
				helper.setText(R.id.tv_fieldKey, item.getFieldKey()+":")
						.setText(R.id.tv_fieldValue, item.getFieldValue());
				break;
			case TYPE_RADIO:
				helper.setText(R.id.tv_fieldKey2, item.getFieldKey()+":")
						.setText(R.id.tv_fieldValue2, item.getFieldValue());
				break;
			case TYPE_IMG:
				helper.setText(R.id.tv_images_title, item.getFieldKey()+":");
				final RecyclerView rvList = (RecyclerView) helper.getView(R.id.rv_list);
				final Context context = rvList.getContext();
				final List<String> imageStrList = item.getFieldValues();
				rvList.setLayoutManager(new GridLayoutManager(context, 3));
				rvList.addItemDecoration(new ImagesSelectorItemDecoration(10));
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

}
