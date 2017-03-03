package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyOrderActivity;
import com.tianchuang.ihome_b.adapter.DetailMultiAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.MyOrderModel;
import com.tianchuang.ihome_b.utils.ViewHelper;
import com.tianchuang.ihome_b.view.OneButtonDialogFragment;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/1.
 * description:我的订单详情
 */

public class MyOrderDetailFragment extends BaseFragment {
	@BindView(R.id.rv_list)
	RecyclerView rvList;
	@BindView(R.id.fl_complain)
	FrameLayout flComplain;
	private MyOrderActivity holdingActivity;
	private TextView tv_sure;
	private EditText tv_content;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_complain_detail;
	}

	public static MyOrderDetailFragment newInstance(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		MyOrderDetailFragment detailFragment = new MyOrderDetailFragment();
		detailFragment.setArguments(bundle);
		return detailFragment;
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("订单详情");
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		int id = getArguments().getInt("id");
		holdingActivity = ((MyOrderActivity) getHoldingActivity());
		rvList.addItemDecoration(new CommonItemDecoration(20));
		rvList.setLayoutManager(new LinearLayoutManager(getContext()));
		requestNet(id);
	}

	/**
	 * 请求网络
	 *
	 * @param id
	 */
	private void requestNet(final int id) {
		MyOrderModel.myOrderDetail(id)
				.compose(this.<HttpModle<MyOrderDetailBean>>bindToLifecycle())
				.compose(RxHelper.<MyOrderDetailBean>handleResult())
				.subscribe(new RxSubscribe<MyOrderDetailBean>() {


					@Override
					protected void _onNext(MyOrderDetailBean bean) {
						DetailMultiAdapter detailMultiAdapter = new DetailMultiAdapter(bean.getRepairsDataVos());
						rvList.setAdapter(detailMultiAdapter);
//						int status = bean.getStatus();
						//添加头部
						detailMultiAdapter.addHeaderView(ViewHelper.getDetailHeaderView(bean.getTypeName(), bean.getCreatedDate()));
						//添加底部
//						当status == 1时显示回复内容
//						当status == 0时才可以进行回复
//						if (status == 1) {
//							detailMultiAdapter.addFooterView(ViewHelper.getDetailFooterView(bean.getReplayContent()));
//						} else {
//							View view = LayoutUtil.inflate(R.layout.multi_detail_footer_holder2);
//							detailMultiAdapter.addFooterView(view);
//							tv_content = ((EditText) view.findViewById(R.id.tv_content));
//							tv_sure = ((TextView) view.findViewById(R.id.tv_sure));
//							tv_sure.setOnClickListener(new View.OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									String content = tv_content.getText().toString().trim();
//									if (content.length() == 0) {
//										ToastUtil.showToast(getContext(), "内容不能为空");
//									} else {
//										requestNetToReplay(id, content);
//									}
//								}
//							});
//						}

					}

					@Override
					protected void _onError(String message) {

					}

					@Override
					public void onCompleted() {

					}
				});
	}


	private void showDialog(String tip) {
		OneButtonDialogFragment.newInstance(tip)
				.show(getHoldingActivity().getFragmentManager(), OneButtonDialogFragment.class.getSimpleName());
	}

	/**
	 * 将实体类转换成json字符串对象            注意此方法需要第三方gson  jar包
	 * @param obj  对象
	 * @return  map
	 */
	private String toJson(Object obj,int method) {
		if (method==1) {

			Gson gson = new Gson();
			String obj2 = gson.toJson(obj);
			return obj2;
		}else if(method==2){

			Gson gson2=new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
			String obj2=gson2.toJson(obj);
			return obj2;
		}
		return "";
	}
}
