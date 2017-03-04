package com.tianchuang.ihome_b.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jakewharton.rxbinding.view.RxView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyOrderActivity;
import com.tianchuang.ihome_b.adapter.ImagesSelectorAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesMultipleItem;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesSelectorItemDecoration;
import com.tianchuang.ihome_b.utils.ImagesSelectorUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by Abyss on 2017/3/4.
 * description:确认修复
 */
public class ConfirmFixedFragment extends BaseFragment implements MyOrderActivity.GetImageByCodeListener {
	public static final int TYPE_BEFORE = 666;
	public static final int TYPE_AFTER = 777;
	@BindView(R.id.rv_list_fix_before)
	RecyclerView rvListFixBefore;
	@BindView(R.id.rv_list_fix_after)
	RecyclerView rvListFixAfter;
	@BindView(R.id.et_content)
	EditText etContent;
	@BindView(R.id.loginBt)
	Button loginBt;
	private MyOrderActivity holdingActivity;
	private ImgSelConfig config_before;
	private ImgSelConfig config_after;
	private ArrayList<ImagesMultipleItem> beforeList;
	private ArrayList<ImagesMultipleItem> afterList;
	private ImagesSelectorAdapter beforeAdapter;
	private ImagesSelectorAdapter afterAdapter;

	public static ConfirmFixedFragment newInstance() {
		return new ConfirmFixedFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_confirm_fixed;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = (MyOrderActivity) getHoldingActivity();
		initImageSelector();
		initRecyclerView(rvListFixBefore);
		initAdapter();
	}

	private void initAdapter() {
		this.beforeList = getListData();
		this.afterList = getListData();
		beforeAdapter = new ImagesSelectorAdapter(beforeList);
		afterAdapter = new ImagesSelectorAdapter(afterList);
		rvListFixBefore.setAdapter(beforeAdapter);
		rvListFixAfter.setAdapter(afterAdapter);
		rvListFixBefore.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
				switch (((ImagesMultipleItem) adapter.getData().get(position)).getItemType()) {
					case ImagesMultipleItem.ADD_IMG:
						if (config_before.maxNum > 0) {
							holdingActivity.startImageSelector(config_before, TYPE_BEFORE);//去选择图片
						} else {
							ToastUtil.showToast(getContext(), "选择图片数量到达上限");
						}
						break;
					case ImagesMultipleItem.IMG:
						config_before.maxNum += 1;//图片上限加上删除数量
						beforeList.remove(position);
						adapter.notifyItemRemoved(position);
						break;
				}
			}
		});
		rvListFixAfter.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
				switch (((ImagesMultipleItem) adapter.getData().get(position)).getItemType()) {
					case ImagesMultipleItem.ADD_IMG:
						if (config_after.maxNum > 0) {
							holdingActivity.startImageSelector(config_after, TYPE_AFTER);//去选择图片
						} else {
							ToastUtil.showToast(getContext(), "选择图片数量到达上限");
						}
						break;
					case ImagesMultipleItem.IMG:
						config_after.maxNum += 1;//图片上限加上删除数量
						afterList.remove(position);
						adapter.notifyItemRemoved(position);
						break;
				}
			}
		});
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
		holdingActivity.setGetImageByCodeListener(this);//选择图片的监听
	}

	private void requestNet() {

	}

	public ArrayList<ImagesMultipleItem> getListData() {
		ArrayList<ImagesMultipleItem> list = new ArrayList<>();
		list.add(new ImagesMultipleItem(ImagesMultipleItem.ADD_IMG));
		return list;
	}

	/**
	 * 初始化列表
	 *
	 * @param rvList
	 */
	private void initRecyclerView(RecyclerView rvList) {
		rvList.addItemDecoration(new ImagesSelectorItemDecoration(5));
		rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
	}

	/**
	 * 初始化图片选择器
	 */
	private void initImageSelector() {
		config_before = ImagesSelectorUtils.getImgSelConfig();
		config_after = ImagesSelectorUtils.getImgSelConfig();
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("确认修复");
	}

	@Override
	protected void initData() {
		super.initData();

	}

	//接收之前的图片
	@Override
	public void onImageBefore(List<String> paths) {
		receiveImages(paths, beforeList, beforeAdapter, config_before);
	}

	//接收之后的图片
	@Override
	public void onImageAfter(List<String> paths) {
		receiveImages(paths, afterList, afterAdapter, config_after);
	}

	private void receiveImages(List<String> paths, ArrayList<ImagesMultipleItem> mData, ImagesSelectorAdapter imagesSelectorAdapter, ImgSelConfig config) {
		for (String s : paths) {
			mData.add(mData.size() - 1, new ImagesMultipleItem(ImagesMultipleItem.IMG).setUrl(s));
		}
		imagesSelectorAdapter.notifyItemRangeInserted(mData.size() - 1, paths.size());
		config.maxNum -= paths.size();//图片上限减去选中数量
	}


}
