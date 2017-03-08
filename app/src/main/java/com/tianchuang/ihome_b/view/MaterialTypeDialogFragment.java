package com.tianchuang.ihome_b.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.bean.event.MaterialFeeEvent;
import com.tianchuang.ihome_b.fragment.MaterialTypeFragment;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.FragmentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Abyss on 2016/12/26.
 * description:材料选择的弹窗
 */

public class MaterialTypeDialogFragment extends DialogFragment implements MaterialTypeFragment.OnMaterialTypeDismiss {


	private Unbinder bind;


	public static MaterialTypeDialogFragment newInstance(ListBean bean) {
		Bundle args = new Bundle();
		args.putSerializable("bean", bean);
		MaterialTypeDialogFragment fragment = new MaterialTypeDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		Window window = getDialog().getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		params.width = DensityUtil.dip2px(getActivity(), 300);
		window.setAttributes(params);
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.material_type_dialog_view, container, false);
		bind = ButterKnife.bind(this, view);
		ListBean bean = (ListBean) getArguments().getSerializable("bean");
		MaterialTypeFragment fragment = MaterialTypeFragment.newInstance(bean);
		FragmentUtils.addFragment(getChildFragmentManager(), fragment, R.id.dialog_container);
		fragment.setOnMaterialTypeDismiss(this);
		return view;
	}


	@Override
	public void onSelectedType(MaterialListItemBean bean) {
		FeeAddDialogFragment feeAddFragment = FeeAddDialogFragment.newInstance(bean);
		FragmentUtils.replaceFragment(getChildFragmentManager(), R.id.dialog_container, feeAddFragment, false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		bind.unbind();
		EventBus.getDefault().unregister(this);
	}
	@Subscribe(threadMode = ThreadMode.MAIN)//获取选择的材料费用数据
	public void onMessageEvent(MaterialFeeEvent event) {
		this.dismiss();
	}
}
