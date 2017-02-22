package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.EmptyViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MainActivity;
import com.tianchuang.ihome_b.adapter.PropertyListAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.PropertyListItem;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.PropertyModel;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Abyss on 2017/2/21.
 * description:物业列表页面
 */

public class PropertyListFragment extends BaseFragment {
	@BindView(R.id.rv_list)
	RecyclerView rvList;
	private MainActivity holdingActivity;
	private PropertyListAdapter listAdapter;
	private ArrayList<PropertyListItem> data;

	public static PropertyListFragment newInstance() {
		return new PropertyListFragment();
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity = ((MainActivity) getHoldingActivity());
		holdingActivity.setSpinnerText("我的物业");
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		rvList.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
		rvList.addItemDecoration(new RobHallItemDecoration(10));
	}

	@Override
	protected void initData() {
		//请求物业列表的数据
		PropertyModel.requestPropertyList()
				.compose(RxHelper.<ArrayList<PropertyListItem>>handleResult())
				.compose(this.<ArrayList<PropertyListItem>>bindToLifecycle())
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						showProgress();
					}
				})
				.subscribe(new RxSubscribe<ArrayList<PropertyListItem>>() {
					@Override
					protected void _onNext(ArrayList<PropertyListItem> propertyList) {
						data = propertyList;
						listAdapter = new PropertyListAdapter(R.layout.property_list_item_holder, data);
						EmptyViewHolder emptyViewHolder = new EmptyViewHolder();
						emptyViewHolder.bindData(getString(R.string.property_no_join));
						listAdapter.setEmptyView(emptyViewHolder.getholderView());
						rvList.setAdapter(listAdapter);
						dismissProgress();
					}

					@Override
					protected void _onError(String message) {
						ToastUtil.showToast(getHoldingActivity(), message);
						dismissProgress();
					}

					@Override
					public void onCompleted() {
						initmListener();
					}
				});
	}

	private void initmListener() {
		listAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int i) {
				ToastUtil.showToast(getHoldingActivity(), "按a");
			}
		});
		listAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
				switch (view.getId()) {
					case R.id.fl_often_btn:
						PropertyListItem propertyListItem = data.get(i);
						if (!propertyListItem.getOftenUse()) {//已经是常用的不用再请求
							requestSetOften(propertyListItem, i);
						}
						break;
					default:
						ToastUtil.showToast(getHoldingActivity(), "没按到");
				}

			}
		});
	}

	/**
	 * 请求网络设为常用
	 */
	private void requestSetOften(PropertyListItem propertyListItem, final int i) {
		Observable<HttpModle<String>> setOften = PropertyModel.requestSetOften(propertyListItem.getId());
		Observable<HttpModle<String>> allCancel = PropertyModel.requestAllCancel();
		Observable.zip(allCancel, setOften, new Func2<HttpModle<String>, HttpModle<String>, Boolean>() {
			@Override
			public Boolean call(HttpModle<String> stringHttpModle, HttpModle<String> stringHttpModle2) {
				return stringHttpModle.success() && stringHttpModle2.success();
			}
		})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						showProgress();
					}
				})
				.subscribe(new Subscriber<Boolean>() {
					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {
						dismissProgress();
					}

					@Override
					public void onNext(Boolean aBoolean) {
						if (aBoolean) {//设为常用成功
							for (PropertyListItem propertyListItem : data) {
								propertyListItem.setOftenUse(false);
							}
							data.get(i).setOftenUse(!data.get(i).getOftenUse());
							listAdapter.setSelsctedPostion(i);
							listAdapter.notifyDataSetChanged();
						} else {
							ToastUtil.showToast(getHoldingActivity(), "设为常用失败");
						}
						dismissProgress();

					}
				});
	}


	@Override
	protected int getLayoutId() {
		return R.layout.fragment_property_list;
	}


}
