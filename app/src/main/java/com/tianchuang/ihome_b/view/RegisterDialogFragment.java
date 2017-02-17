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
import com.tianchuang.ihome_b.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Abyss on 2017/2/15.
 * description:注册的弹窗
 */

public class RegisterDialogFragment extends DialogFragment {


	@BindView(R.id.tv_tip)
	TextView tvTip;
	@BindView(R.id.tv_cancel)
	TextView tvCancel;
	@BindView(R.id.tv_sure)
	TextView tvSure;
	private Unbinder bind;


	public static RegisterDialogFragment newInstance(String tip) {
		Bundle args = new Bundle();
		args.putString("tip", tip);
		RegisterDialogFragment fragment = new RegisterDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		MaterialDialog.Builder customViewBuilder = new MaterialDialog.Builder(getActivity()).customView(R.layout.custom_dialog_view, false);
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

	public RegisterDialogFragment setOnClickButtonListener(OnClickButtonListener onClickButtonListener) {
		this.onClickButtonListener = onClickButtonListener;
		return this;
	}

	@OnClick({R.id.tv_cancel, R.id.tv_sure})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_cancel:
				if (onClickButtonListener != null) {
					onClickButtonListener.onClickCancel();
					this.dismiss();
				}
				break;
			case R.id.tv_sure:
				if (onClickButtonListener != null) {
					onClickButtonListener.onClickSure();
					this.dismiss();
				}
				break;
		}
	}


	public interface OnClickButtonListener {
		void onClickCancel();

		void onClickSure();
	}
}
