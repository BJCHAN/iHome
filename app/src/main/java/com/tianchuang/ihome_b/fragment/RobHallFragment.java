package com.tianchuang.ihome_b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.EmptyViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.FaultDetailActivity;
import com.tianchuang.ihome_b.activity.RobHallActivity;
import com.tianchuang.ihome_b.adapter.RobHallAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItem;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/2/20.
 * description:抢单大厅fragment
 */

public class RobHallFragment extends BaseFragment {

	@BindView(R.id.rv_list)
	RecyclerView mRecyclerView;
	private RobHallActivity holdingActivity;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_rob_hall;
	}

	public static RobHallFragment newInstance() {
		return new RobHallFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((RobHallActivity) getHoldingActivity());
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
		mRecyclerView.addItemDecoration(new RobHallItemDecoration(20));
		ArrayList<RobHallItem> robHallItems = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			RobHallItem robHallItem = new RobHallItem();
			robHallItem.setContent("电梯怎么又坏了？");
			robHallItem.setDate("1111");
			robHallItem.setPictureNum("3");
			robHallItem.setType("西瓜" + i);
			robHallItems.add(robHallItem);
		}


		final RobHallAdapter robHallAdapter = new RobHallAdapter(R.layout.rob_hall_item_holder, robHallItems);
		//设置空页面
		EmptyViewHolder emptyViewHolder = new EmptyViewHolder();
		emptyViewHolder.bindData(getString(R.string.rob_hall_empty_tip));
		robHallAdapter.setEmptyView(emptyViewHolder.getholderView());
		//条目的点击事件
		robHallAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int i) {
				Intent intent = new Intent(getHoldingActivity(), FaultDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("item", robHallAdapter.getItem(i));
				intent.putExtras(bundle);
				getHoldingActivity().startActivityWithAnim(intent);
			}
		});
		mRecyclerView.setAdapter(robHallAdapter);

	}


	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("抢单大厅");
	}

}
