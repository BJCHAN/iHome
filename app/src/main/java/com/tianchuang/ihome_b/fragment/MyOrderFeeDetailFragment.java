package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.ItemRemoveAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean;
import com.tianchuang.ihome_b.bean.CommonFeeBean;
import com.tianchuang.ihome_b.bean.ControlPointItemBean;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.bean.event.FeeSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.event.MaterialFeeEvent;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.bean.model.MyOrderModel;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.view.ChargeTypeDialogFragment;
import com.tianchuang.ihome_b.view.ItemRemoveRecyclerView;
import com.tianchuang.ihome_b.view.MaterialTypeDialogFragment;
import com.tianchuang.ihome_b.view.OnItemRemoveClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/3/5.
 * description:费用明细
 */

public class MyOrderFeeDetailFragment extends BaseLoadingFragment implements ChargeTypeDialogFragment.OnChargeTypeSelectedListener {
	@BindView(R.id.rv_item_remove_recyclerview)
	ItemRemoveRecyclerView rvItemRemoveRecyclerview;
	@BindView(R.id.tv_sum_price)
	TextView tvSumPrice;
	@BindView(R.id.tv_add_material)
	TextView tvAddMaterial;
	@BindView(R.id.tv_add_charge)
	TextView tvAddCharge;
	@BindView(R.id.cb_isunder_line)
	CheckBox cbIsunderLine;
	@BindView(R.id.bt_sure)
	Button btSure;
	private ArrayList<MaterialListItemBean> materialList;
	private ArrayList<ChargeTypeListItemBean> chargeTypeList;
	private Observable<ArrayList<MaterialListItemBean>> materialListObservable;
	private Observable<ArrayList<ChargeTypeListItemBean>> chargeTypeListObservable;
	private ArrayList<CommonFeeBean> commonFeeBeenList;
	private ItemRemoveAdapter adapter;
	private int repairId;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_order_fee_detail;
	}

	public static MyOrderFeeDetailFragment newInstance(int id) {
		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		MyOrderFeeDetailFragment fragment = new MyOrderFeeDetailFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		setToolbarTitle("费用明细");
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		repairId = getArguments().getInt("id");
		commonFeeBeenList = new ArrayList<>();
		rvItemRemoveRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
		adapter = new ItemRemoveAdapter(getContext(), commonFeeBeenList);
		rvItemRemoveRecyclerview.setAdapter(adapter);
		rvItemRemoveRecyclerview.setOnItemClickListener(new OnItemRemoveClickListener() {
			@Override
			public void onItemClick(View view, int position) {

			}

			@Override
			public void onDeleteClick(int position) {
				adapter.removeItem(position);
				tvSumPrice.setText("￥" + StringUtils.formatNum(getSum()));
			}
		});
//		requestNet();
	}

	@OnClick({R.id.tv_add_material, R.id.tv_add_charge, R.id.bt_sure})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_add_material://材料弹窗
				MaterialTypeDialogFragment.newInstance(new ListBean().setMaterialTypeList(materialList))
						.show(getFragmentManager(), "");
				break;
			case R.id.tv_add_charge://费用弹窗
				ChargeTypeDialogFragment.newInstance(new ListBean().setChargeTypeList(chargeTypeList))
						.setOnChargeTypeSelectedListener(this)
						.show(getFragmentManager(), "");
				break;
			case R.id.bt_sure:
				requestSubmit();//请求网络提交费用信息
				break;
		}
	}

	/**
	 * 请求网络提交费用信息
	 */
	private void requestSubmit() {
		boolean checked = cbIsunderLine.isChecked();
		MyOrderModel.submitFeeList(repairId, checked ? 1 : 0, StringUtils.toJson(commonFeeBeenList, 2))
				.compose(RxHelper.<String>handleResult())
				.compose(this.<String>bindToLifecycle())
				.subscribe(new RxSubscribe<String>() {
					@Override
					protected void _onNext(String s) {
						ToastUtil.showToast(getContext(), "提交成功");
						removeFragment();
						EventBus.getDefault().post(new FeeSubmitSuccessEvent());
					}

					@Override
					protected void _onError(String message) {
						ToastUtil.showToast(getContext(), message);
					}

					@Override
					public void onCompleted() {

					}
				});
	}

	/**
	 * 请求材料和费用列表
	 */
	@Override
	protected void initData() {
		materialList = new ArrayList<>();
		chargeTypeList = new ArrayList<>();
		materialListObservable = MyOrderModel.materialList().compose(RxHelper.<ArrayList<MaterialListItemBean>>handleResult());
		chargeTypeListObservable = MyOrderModel.chargeTypeList().compose(RxHelper.<ArrayList<ChargeTypeListItemBean>>handleResult());
		getChargeTypeAndMaterialList(materialListObservable, chargeTypeListObservable)
//				.doOnSubscribe(new Action0() {
//					@Override
//					public void call() {
//						showProgress();
//					}
//				})
				.retry(2)
				.subscribe(new RxSubscribe<Object>() {
					@Override
					public void onCompleted() {
						showSucceedPage();
//						dismissProgress();
					}

					@Override
					protected void _onNext(Object o) {
						if (o instanceof ArrayList) {
							ArrayList list = (ArrayList) o;
							if (list.size() > 0) {
								if (list.get(0) instanceof MaterialListItemBean) {//材料列表
									materialList.clear();
									materialList.addAll(((ArrayList<MaterialListItemBean>) o));
								} else if (list.get(0) instanceof ChargeTypeListItemBean) {//费用列表
									chargeTypeList.clear();
									chargeTypeList.addAll(((ArrayList<ChargeTypeListItemBean>) o));
								}
							}
						}
					}

					@Override
					protected void _onError(String message) {
						ToastUtil.showToast(getContext(), message);
						showErrorPage();
//						dismissProgress();
					}
				});
	}


	private Observable<Object> getChargeTypeAndMaterialList(Observable<ArrayList<MaterialListItemBean>> materialListObservable, Observable<ArrayList<ChargeTypeListItemBean>> chargeTypeListObservable) {
		return Observable.merge(materialListObservable, chargeTypeListObservable)
				.compose(this.bindToLifecycle());
	}

	//获取选择的人工费用的数据
	@Override
	public void onChargeFee(CommonFeeBean commonFeeBean) {
		commonFeeBeenList.add(commonFeeBean);
		adapter.notifyItemInserted(adapter.getItemCount());
		tvSumPrice.setText("￥" + StringUtils.formatNum(getSum()));
	}

	@Subscribe(threadMode = ThreadMode.MAIN)//获取选择的材料费用数据
	public void onMessageEvent(MaterialFeeEvent event) {
		CommonFeeBean commonFeeBean = event.getCommonFeeBean();
		commonFeeBeenList.add(commonFeeBean);
		adapter.notifyItemInserted(adapter.getItemCount());
		tvSumPrice.setText("￥" + StringUtils.formatNum(getSum()));
	}

	private float getSum() {
		float sum = 0f;
		for (CommonFeeBean feeBean : commonFeeBeenList) {
			float price = Float.valueOf(feeBean.getFee());
			sum = sum + price;
		}
		return sum;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}


}
