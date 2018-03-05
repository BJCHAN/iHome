package com.tianchuang.ihome_b.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.database.UserInfo;
import com.tianchuang.ihome_b.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Abyss on 2017/2/15.
 * description:一个按钮的弹窗
 */

public class OneButtonDialogFragment extends DialogFragment {


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}

	@BindView(R.id.tv_tip)
	TextView tvTip;
	private Unbinder bind;


	public static OneButtonDialogFragment newInstance(String tip) {
		Bundle args = new Bundle();
		args.putString("tip", tip);
		OneButtonDialogFragment fragment = new OneButtonDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		MaterialDialog.Builder customViewBuilder = new MaterialDialog.Builder(getActivity()).customView(R.layout.custom_dialog_view2, false);
		MaterialDialog dialog = customViewBuilder.build();
		View view = dialog.getCustomView();
		bind = ButterKnife.bind(this, view);
		initData();
		return dialog;
	}

	@Override
	public void onStart() {
		super.onStart();
		Window window = getDialog().getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;
		params.width = DensityUtil.dip2px(getActivity(),270);
		window.setAttributes(params);
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	private void initData() {
		String tip = getArguments().getString("tip");
		if (!TextUtils.isEmpty(tip)) tvTip.setText(tip);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		bind.unbind();
	}

	private OnClickButtonListener onClickButtonListener;

	public OneButtonDialogFragment setOnClickButtonListener(OnClickButtonListener onClickButtonListener) {
		this.onClickButtonListener = onClickButtonListener;
		return this;
	}

	@OnClick( R.id.tv_sure)
	public void onClick(View view) {
				if (onClickButtonListener != null) {
					onClickButtonListener.onClickSure();
				}
		this.dismiss();
	}


	public interface OnClickButtonListener {
		void onClickSure();
	}
}
