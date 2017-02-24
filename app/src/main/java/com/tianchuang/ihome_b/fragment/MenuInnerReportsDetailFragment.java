package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事fragment详情(菜单)
 */

public class MenuInnerReportsDetailFragment extends BaseFragment {

	@BindView(R.id.tv_name)
	TextView tvName;
	@BindView(R.id.tv_department_name)
	TextView tvDepartmentName;
	@BindView(R.id.tv_content)
	TextView tvContent;
	@BindView(R.id.iv_add1)
	ImageView ivAdd1;
	@BindView(R.id.iv_add2)
	ImageView ivAdd2;
	@BindView(R.id.iv_add3)
	ImageView ivAdd3;
	@BindView(R.id.statusBt)
	Button statusBt;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_inner_reports_detail;
	}

	public static MenuInnerReportsDetailFragment newInstance(MenuInnerReportsItemBean menuInnerReportsItemBean) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("info", menuInnerReportsItemBean);
		MenuInnerReportsDetailFragment fragment = new MenuInnerReportsDetailFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		MenuInnerReportsItemBean info = ((MenuInnerReportsItemBean) getArguments().getSerializable("info"));
		tvName.setText(StringUtils.getNotNull(info.getPropertyEmployeeRoleVo().getEmployeeName()));
		tvDepartmentName.setText(StringUtils.getNotNull(info.getPropertyEmployeeRoleVo().getDepartmentName()));
		tvContent.setText(StringUtils.getNotNull(info.getContent()));
		statusBt.setText(StringUtils.getNotNull(info.getStatusMsg()));
		String photo1Url = info.getPhoto1Url();
		String photo2Url = info.getPhoto2Url();
		String photo3Url = info.getPhoto3Url();
		loadPhoto(photo1Url, ivAdd1);
		loadPhoto(photo2Url, ivAdd2);
		loadPhoto(photo3Url, ivAdd3);

	}
	//加载图片
	private void loadPhoto(String photoUrl, ImageView ivAdd1) {
		if (!TextUtils.isEmpty(photoUrl)) {
			ivAdd1.setVisibility(View.VISIBLE);
			Glide.with(getContext())
					.load(photoUrl)
					.placeholder(R.mipmap.default_logo)
					.into(ivAdd1);
		}
	}


}
