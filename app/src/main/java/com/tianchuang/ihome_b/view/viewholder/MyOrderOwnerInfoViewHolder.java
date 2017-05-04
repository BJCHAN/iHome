package com.tianchuang.ihome_b.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseHolder;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.bean.event.PlayPhoneEvent;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abyss on 2017/2/20.
 * description:报修人员信息的底部
 */

public class MyOrderOwnerInfoViewHolder extends BaseHolder<MyOrderDetailBean.OwnersInfoVoBean> {


	@BindView(R.id.tv_name)
	TextView tvName;
	@BindView(R.id.tv_address)
	TextView tvAddress;
	@BindView(R.id.tv_phone_num)
	TextView tvPhoneNum;
	@BindView(R.id.iv_play_phone)
	ImageView ivPlayPhone;

	@Override
	public View initHolderView() {
		View view = LayoutUtil.inflate(R.layout.myorder_owner_footer_holder);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void bindData(final MyOrderDetailBean.OwnersInfoVoBean bean) {
		if (bean == null) {
			return;
		}
		tvName.setText(StringUtils.getNotNull(bean.getOwnersName()));
		tvAddress.setText(StringUtils.getNotNull("/" + bean.getBuildingName() + bean.getBuildingCellName() + bean.getBuildingUnitName() + bean.getNum()));
		tvPhoneNum.setText(StringUtils.getNotNull(bean.getOwnersMobile()));
		ivPlayPhone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new PlayPhoneEvent(bean.getOwnersMobile()));
			}
		});

	}

}
