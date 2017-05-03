package com.tianchuang.ihome_b.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.PropertyListItemBean;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Abyss on 2017/2/9.
 * description:物业列表的适配器
 */
public class PropertyListAdapter extends BaseQuickAdapter<PropertyListItemBean,BaseViewHolder> {
	private int selsctedPostion = -1;

	public int getSelsctedPostion() {
		return selsctedPostion;
	}

	public void setSelsctedPostion(int selsctedPostion) {
		this.selsctedPostion = selsctedPostion;
	}
	public PropertyListAdapter( List<PropertyListItemBean> data) {
		super(R.layout.property_list_item_holder, data);
	}


	@Override
	protected void convert(BaseViewHolder helper, PropertyListItemBean item) {
		helper.addOnClickListener(R.id.fl_often_btn);
		TextView littleRed = helper.getView(R.id.tv_little_red);//小红点
		CardView cardView = helper.getView(R.id.card_view);
		View line = helper.getView(R.id.tv_line);
		if (item.isCurrentProperty()) {
			cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
			line.setBackgroundColor(Color.parseColor("#E0E0E0"));
		} else {
			cardView.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
			line.setBackgroundColor(Color.parseColor("#ffffff"));

		}
		helper.setText(R.id.tv_company_name, item.getPropertyCompanyName())
				.setText(R.id.tv_department_name, item.getDepartmentName())
				.setText(R.id.tv_position_name, item.getPositionName());
		ImageView oftenImage = helper.getView(R.id.iv_often);
		TextView oftenText = helper.getView(R.id.tv_often);
		Boolean oftenUse = item.getOftenUse();
		int noticeCount = item.getNoticeCount();
		Badge badge = new QBadgeView(littleRed.getContext()).bindTarget(littleRed).setBadgeNumber(noticeCount);
		badge.setBadgeBackground(ContextCompat.getDrawable(littleRed.getContext(),R.drawable.ic_super_red_point));
		badge.setBadgeGravity(Gravity.CENTER);
		badge.setBadgeTextSize(12, true);
		badge.setBadgePadding(6, true);
		badge.setShowShadow(false);
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
