package com.tianchuang.ihome_b.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.FaultDetailAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItem;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.view.OneButtonDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.data;

/**
 * Created by Abyss on 2017/2/20.
 * description:故障详情fragment
 */

public class FaultDetailFragment extends BaseFragment {

	@BindView(R.id.tv_type)
	TextView tvType;
	@BindView(R.id.tv_date)
	TextView tvDate;
	@BindView(R.id.tv_content)
	TextView tvContent;
	@BindView(R.id.rv_list)
	RecyclerView rvList;
	private List<String> imageStrList;
	private TransferImage transferLayout;

	{
		imageStrList = new ArrayList<>();
		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486263697527.png");
		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486263782969.png");
		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486263820142.png");
		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1485136117467.jpg");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1485055822651.png");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1485053874297.png");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486194909983.png");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486194996586.png");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486195059137.png");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486173497249.png");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486173526402.png");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486173639603.png");
//		imageStrList.add("http://static.fdc.com.cn/avatar/sns/1486172566083.png");
	}
	@Override
	protected int getLayoutId() {
		return R.layout.fragment_fault_detail;
	}

	public static FaultDetailFragment newInstance(RobHallItem item) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("item", item);
		FaultDetailFragment faultDetailFragment = new FaultDetailFragment();
		faultDetailFragment.setArguments(bundle);
		return faultDetailFragment;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		RobHallItem item = (RobHallItem) getArguments().getSerializable("item");
		if (item != null) {
			tvType.setText(StringUtils.getNotNull(item.getType()));
			tvDate.setText(StringUtils.getNotNull(item.getDate()));
			tvContent.setText(StringUtils.getNotNull(item.getContent()));
		}
		rvList.setLayoutManager(new GridLayoutManager(getHoldingActivity(),3));
		rvList.addItemDecoration(new RobHallItemDecoration(10));
		FaultDetailAdapter adapter = new FaultDetailAdapter(R.layout.fault_image_item_holder, imageStrList);
		rvList.setAdapter(adapter);
		adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
			@Override
			public void onItemClick(View view, int i) {
				transferLayout = new TransferImage.Builder(getHoldingActivity())
						.setBackgroundColor(ContextCompat.getColor(getHoldingActivity(), R.color.black))
						.setOriginImageList(wrapOriginImageViewList())
						.setImageUrlList(imageStrList)
						.setOriginIndex(i)
						.create();
				transferLayout.show();
				EventBus.getDefault().post(new TransferLayoutEvent(transferLayout));
			}
		});
	}

	/**
	 * 包装缩略图 ImageView 集合
	 * @return
	 */
	@NonNull
	private List<ImageView> wrapOriginImageViewList() {
		List<ImageView> originImgList = new ArrayList<>();
		for (int i = 0; i < imageStrList.size(); i++) {
			ImageView thumImg = (ImageView) rvList.getChildAt(i);
			originImgList.add(thumImg);
		}
		return originImgList;
	}
	@OnClick(R.id.tv_rob_order)
	public void onClick() {
		OneButtonDialogFragment.newInstance("当前报修单已被其他用户抢单，\n" +
				"去看看其他报修单吧")
				.show(getHoldingActivity().getFragmentManager(), OneButtonDialogFragment.class.getSimpleName());
	}


}
