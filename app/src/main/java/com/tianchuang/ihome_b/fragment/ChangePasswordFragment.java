package com.tianchuang.ihome_b.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.ChangePasswordActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/16.
 * description:修改密码界面
 */

public class ChangePasswordFragment extends BaseFragment {
	@BindView(R.id.et_old_passwrod)
	EditText etOldPasswrod;
	@BindView(R.id.et_new_passwrod)
	EditText etNewPasswrod;
	@BindView(R.id.et_sure_passwrod)
	EditText etSurePasswrod;
	@BindView(R.id.tv_red_tip)
	TextView tvRedTip;
	@BindView(R.id.bt_submit)
	Button btSubmit;
	private ChangePasswordActivity holdingActivity;

	@Override
	protected void initView(View view, Bundle savedInstanceState) {

	}
	@Override
	public void onAttach(Context context) {//Modified 2016-06-01</span>
		super.onAttach(context);
		holdingActivity = ((ChangePasswordActivity) getHoldingActivity());
	}
	public static ChangePasswordFragment newInstance() {
		return new ChangePasswordFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_change_password;
	}


	@OnClick(R.id.bt_submit)
	public void onClick() {
		FragmentUtils.popAddFragment(getFragmentManager(),holdingActivity.getContainer(),ChangePwdSuccessFragment.newInstance(),false);
	}
}
