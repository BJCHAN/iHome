package com.tianchuang.ihome_b.adapter;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.PropertyListItem;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:物业列表的适配器
 */
public class PropertyListAdapter extends BaseQuickAdapter<PropertyListItem> {
	private int selsctedPostion = -1;

	public int getSelsctedPostion() {
		return selsctedPostion;
	}

	public void setSelsctedPostion(int selsctedPostion) {
		this.selsctedPostion = selsctedPostion;
	}
	public PropertyListAdapter(int layoutResId, List<PropertyListItem> data) {
		super(layoutResId, data);
	}


	@Override
	protected void convert(BaseViewHolder helper, PropertyListItem item) {
		helper.setOnClickListener(R.id.fl_often_btn, new OnItemChildClickListener());
		helper.setText(R.id.tv_company_name, item.getPropertyCompanyName())
				.setText(R.id.tv_department_name, item.getDepartmentName())
				.setText(R.id.tv_position_name, item.getPositionName());
		ImageView oftenImage = helper.getView(R.id.iv_often);
		TextView oftenText = helper.getView(R.id.tv_often);
		Boolean oftenUse = item.getOftenUse();
		setOftenUseStatus(oftenImage, oftenText, oftenUse);
	}

	/**
	 * 设置设为常用按钮的状态
	 */
	private void setOftenUseStatus(ImageView oftenImage, TextView oftenText, Boolean oftenUse) {
		oftenImage.setVisibility(oftenUse ? View.VISIBLE : View.INVISIBLE);
		oftenText.setVisibility(oftenUse ? View.INVISIBLE : View.VISIBLE);
	}

}
