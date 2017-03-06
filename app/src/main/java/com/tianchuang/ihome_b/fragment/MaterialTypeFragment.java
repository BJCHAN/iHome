package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MaterialTypeAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.bean.recyclerview.MaterialTypeItemDecoration;
import com.tianchuang.ihome_b.utils.DensityUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/2/13.
 * description:材料类型界面
 */

public class MaterialTypeFragment extends BaseFragment {

	@BindView(R.id.rv_list)
	RecyclerView rvList;
	private ArrayList<MaterialListItemBean> mData;

	public static MaterialTypeFragment newInstance(ListBean bean) {
		Bundle args = new Bundle();
		args.putSerializable("bean", bean);
		MaterialTypeFragment fragment = new MaterialTypeFragment();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		initMyData();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.material_type_fragment;
	}

	private void initMyData() {
		ListBean bean = (ListBean) getArguments().getSerializable("bean");
		mData = bean.getMaterialTypeList();
		rvList.setLayoutManager(new LinearLayoutManager(getContext()));
		rvList.addItemDecoration(new MaterialTypeItemDecoration(DensityUtil.dip2px(getContext(), 1)));
		rvList.setAdapter(new MaterialTypeAdapter(R.layout.material_type_item_holder, mData));
		rvList.addOnItemTouchListener(new OnItemClickListener() {

			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
				MaterialListItemBean bean = mData.get(position);
				if (onMaterialTypeDismiss != null) {
					onMaterialTypeDismiss.onSelectedType(bean);
				}
			}
		});
	}

	private OnMaterialTypeDismiss onMaterialTypeDismiss;

	public MaterialTypeFragment setOnMaterialTypeDismiss(OnMaterialTypeDismiss onMaterialTypeDismiss) {
		this.onMaterialTypeDismiss = onMaterialTypeDismiss;
		return this;
	}

	public interface OnMaterialTypeDismiss {
		void onSelectedType(MaterialListItemBean bean);
	}
}
