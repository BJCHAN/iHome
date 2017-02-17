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
 * description:退出登录的弹窗
 */

public class LogoutDialogFragment extends DialogFragment {


	@BindView(R.id.tv_tip)
	TextView tvTip;
	@BindView(R.id.tv_cancel)
	TextView tvCancel;
	@BindView(R.id.tv_sure)
	TextView tvSure;
	private Unbinder bind;

	//属性
	private String sureText;
	private String tip;

	public LogoutDialogFragment(Builder builder) {
		this.sureText = builder.sureText;
		this.tip = builder.tip;
	}

	public LogoutDialogFragment() {
		this(new Builder());
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		MaterialDialog.Builder customViewBuilder = new MaterialDialog.Builder(getActivity()).customView(R.layout.logout_dialog_view, false);
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
		params.width = DensityUtil.dip2px(getActivity(), 270);
		window.setAttributes(params);
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	private void initData() {
		if (!TextUtils.isEmpty(tip)) tvTip.setText(tip);
		if (!TextUtils.isEmpty(sureText)) tvSure.setText(sureText);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		bind.unbind();
	}

	public LogoutDialogFragment setSureText(String text) {

		tvSure.setText(text);
		return this;
	}

	private OnClickButtonListener onClickButtonListener;

	public LogoutDialogFragment setOnClickButtonListener(OnClickButtonListener onClickButtonListener) {
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

	public static final class Builder {
		private String sureText;
		private String tip;

		public Builder() {
		}

		public Builder setSureText(String sureText) {
			this.sureText = sureText;
			return this;
		}

		public Builder setTipText(String tip) {
			this.tip = tip;
			return this;
		}

		public LogoutDialogFragment build() {
			return new LogoutDialogFragment(this);
		}
	}
}
