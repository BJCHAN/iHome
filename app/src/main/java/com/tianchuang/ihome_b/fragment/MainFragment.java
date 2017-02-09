package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;


/**
 * Created by Abyss on 2017/2/9.
 * description:
 */

public class MainFragment extends BaseFragment {

	@BindView(R.id.button)
	Button button;


	public static MainFragment newInstance() {
		return new MainFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
	}

	@Override
	protected void initListener() {
		RxView.clicks(button).compose(this.<Void>bindToLifecycle()).subscribe(new Action1<Void>() {
			@Override
			public void call(Void aVoid) {
				Toast.makeText(getHoldingActivity(), "你好啊", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_main;
	}


}
