package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyOrderActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.MyOrderModel;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.view.ItemRemoveRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/3/5.
 * description:费用明细
 */

public class MyOrderFeeDetailFragment extends BaseFragment {
	@BindView(R.id.id_item_remove_recyclerview)
	ItemRemoveRecyclerView idItemRemoveRecyclerview;
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
	private MyOrderActivity holdingActivity;
	private ArrayList<MaterialListItemBean> materialList;
	private ArrayList<ChargeTypeListItemBean> chargeTypeList;
	private Observable<ArrayList<MaterialListItemBean>> materialListObservable;
	private Observable<ArrayList<ChargeTypeListItemBean>> chargeTypeListObservable;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_order_fee_detail;
	}

	public static MyOrderFeeDetailFragment newInstance() {
		return new MyOrderFeeDetailFragment();
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("费用明细");
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((MyOrderActivity) getHoldingActivity());
		requestNet();
	}

	@OnClick({R.id.tv_add_material, R.id.tv_add_charge, R.id.bt_sure})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_add_material:
				break;
			case R.id.tv_add_charge:
				break;
			case R.id.bt_sure:
				break;
		}
	}

	/**
	 * 请求材料和费用列表
	 */
	private void requestNet() {
		materialList = new ArrayList<>();
		chargeTypeList = new ArrayList<>();
		materialListObservable = MyOrderModel.materialList().compose(RxHelper.<ArrayList<MaterialListItemBean>>handleResult());
		chargeTypeListObservable = MyOrderModel.chargeTypeList().compose(RxHelper.<ArrayList<ChargeTypeListItemBean>>handleResult());
		getChargeTypeAndMaterialList(materialListObservable, chargeTypeListObservable)
				.subscribe(new RxSubscribe<Object>() {
					@Override
					public void onCompleted() {
						dismissProgress();
					}

					@Override
					protected void _onNext(Object o) {
						if (o instanceof ArrayList) {
							ArrayList list = (ArrayList) o;
							if (list.size() > 0) {
								if (list.get(0) instanceof MaterialListItemBean) {//材料列表
									materialList.addAll(((ArrayList<MaterialListItemBean>) o));
								} else if (list.get(0) instanceof ChargeTypeListItemBean) {//费用列表
									chargeTypeList.addAll(((ArrayList<ChargeTypeListItemBean>) o));
								}
							}
						}
					}

					@Override
					protected void _onError(String message) {
						ToastUtil.showToast(getContext(), message);
						dismissProgress();
					}
				});
	}

	private Observable<Object> getChargeTypeAndMaterialList(Observable<ArrayList<MaterialListItemBean>> materialListObservable, Observable<ArrayList<ChargeTypeListItemBean>> chargeTypeListObservable) {
		return Observable.merge(materialListObservable, chargeTypeListObservable)
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						showProgress();
					}
				})
				.compose(this.bindToLifecycle());
	}


}
