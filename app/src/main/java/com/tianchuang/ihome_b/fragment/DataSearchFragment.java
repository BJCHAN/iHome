package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.DataSearchActivity;
import com.tianchuang.ihome_b.adapter.SimpleItemAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.bean.SimpleItemBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/1.
 * description:数据查询页面
 */

public class DataSearchFragment extends BaseFragment {
	@BindView(R.id.rv_list)
	RecyclerView rvList;

	public static DataSearchFragment newInstance() {
		return new DataSearchFragment();
	}

	@Override
	public void onStart() {
		super.onStart();
		setToolbarTitle("资料查询");
	}


	@Override
	protected int getLayoutId() {
		return R.layout.fragment_data_search;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		rvList.addItemDecoration(new CommonItemDecoration(20));
		rvList.setLayoutManager(new LinearLayoutManager(getContext()));
		final ArrayList<SimpleItemBean> mData = new ArrayList<>();
		mData.add(new SimpleItemBean().setId(1).setText("楼宇查询"));
		mData.add(new SimpleItemBean().setId(2).setText("设备查询"));
		mData.add(new SimpleItemBean().setId(3).setText("业主查询"));
		mData.add(new SimpleItemBean().setId(4).setText("车辆查询"));
		rvList.setAdapter(new SimpleItemAdapter(R.layout.data_search_type_item_holder, mData));
		rvList.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
				switch (mData.get(position).getId()) {
					case 1:
						addFragment(BuildingSearchFragment.newInstance());
						break;
					case 2:
						addFragment(EquipmentTypeFragment.newInstance());
						break;
					case 3:

						break;
					case 4:

						break;
				}
			}
		});
	}

}
