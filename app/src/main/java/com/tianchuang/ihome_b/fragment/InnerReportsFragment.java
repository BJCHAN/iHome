package com.tianchuang.ihome_b.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.InnerReportsActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.InnerReportsModel;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事fragment(主页)
 */

public class InnerReportsFragment extends BaseFragment implements InnerReportsActivity.GetImageByCodeListener {

	@BindView(R.id.et_content)
	EditText etContent;


	ImageView ivAdd3;
	@BindView(R.id.loginBt)
	Button loginBt;
	@BindView(R.id.tv_name)
	TextView tvName;
	@BindView(R.id.tv_department_name)
	TextView tvDepartmentName;
	@BindView(R.id.rv_list)
	RecyclerView rvList;
	private InnerReportsActivity holdingActivity;
	private ImgSelConfig config;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_inner_reports;
	}

	public static InnerReportsFragment newInstance() {
		return new InnerReportsFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		LoginBean loginBean = UserUtil.getLoginBean();
		tvName.setText(StringUtils.getNotNull(loginBean.getName()));
		tvDepartmentName.setText(StringUtils.getNotNull(loginBean.getDepartmentName()));
		initImageSelector();
		rvList.addItemDecoration(new RobHallItemDecoration(5));
		rvList.setLayoutManager(new GridLayoutManager(getContext(),3));
//		new

	}

	private void initImageSelector() {
		// 自定义图片加载器
		ImageLoader loader = new ImageLoader() {
			@Override
			public void displayImage(Context context, String path, ImageView imageView) {
				Glide.with(context).load(path).into(imageView);
			}
		};

		config = new ImgSelConfig.Builder(getContext(), loader)
				// 是否多选, 默认true
				.multiSelect(false)
				// 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
				.rememberSelected(true)
				// “确定”按钮背景色
				.btnBgColor(Color.BLACK)
				// “确定”按钮文字颜色
				.btnTextColor(Color.WHITE)
				// 使用沉浸式状态栏
				.statusBarColor(ContextCompat.getColor(getContext(), R.color.app_primary_color))
				// 返回图标ResId
				.backResId(R.mipmap.back)
				// 标题
				.title("图片")
				// 标题文字颜色
				.titleColor(Color.WHITE)
				// TitleBar背景色
				.titleBgColor(ContextCompat.getColor(getContext(), R.color.app_primary_color))
				// 裁剪大小。needCrop为true的时候配置
				.cropSize(1, 1, 200, 200)
				.needCrop(false)
				// 第一个是否显示相机，默认true
				.needCamera(true)
				// 最大选择图片数量，默认9
				.maxNum(3)
				.build();
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		holdingActivity = ((InnerReportsActivity) activity);

	}

	@Override
	protected void initListener() {
		RxView.clicks(loginBt)
				.throttleFirst(3, TimeUnit.SECONDS)
				.compose(this.<Void>bindToLifecycle())
				.subscribe(new Action1<Void>() {
					@Override
					public void call(Void aVoid) {
						requestNet();
					}
				});
		holdingActivity.setGetImageByCodeListener(this);
	}

	/**
	 * 请求网络
	 */
	private void requestNet() {
		String content = etContent.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			ToastUtil.showToast(getContext(), "内容不能为空");
			return;
		}
		InnerReportsModel.requestReportsSubmit(UserUtil.getLoginBean().getPropertyCompanyId(), content)
				.compose(RxHelper.<String>handleResult())
				.compose(this.<String>bindToLifecycle())
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						showProgress();
					}
				})
				.subscribe(new RxSubscribe<String>() {
					@Override
					protected void _onNext(String s) {
						FragmentUtils.popAddFragment(getFragmentManager(), holdingActivity.getFragmentContainerId(), InnerReportsSuccessFragment.newInstance(), true);
						dismissProgress();
					}

					@Override
					protected void _onError(String message) {
						dismissProgress();
					}

					@Override
					public void onCompleted() {

					}
				});
	}


	private void loadImage(String path, ImageView view) {
		Glide.with(getContext()).load(path)
				.centerCrop()
				.placeholder(ContextCompat.getDrawable(getContext(), R.drawable.add_photo_icon))
				.into(view);
	}

	@Override
	public void onImage(String path) {

	}
}
