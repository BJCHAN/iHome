package com.tianchuang.ihome_b.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abyss on 2017/3/4.
 * description:时间轴的适配器
 */

public class TraceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private LayoutInflater inflater;
	private List<MyOrderDetailBean.RepairsOrderLogVo> traceList = new ArrayList<>(1);
	private static final int TYPE_TOP = 0x0000;
	private static final int TYPE_NORMAL = 0x0001;
	private static final int TYPE_Bottom = 0x0002;
	private Context context;

	public TraceListAdapter(Context context, List<MyOrderDetailBean.RepairsOrderLogVo> traceList) {
		inflater = LayoutInflater.from(context);
		this.traceList = traceList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.time_line_item_holder, parent, false);
		context = view.getContext();
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		ViewHolder itemHolder = (ViewHolder) holder;
		int color = ContextCompat.getColor(context, R.color.app_primary_color);
		int gray = ContextCompat.getColor(context, R.color.TC_2);
		if (getItemViewType(position) == TYPE_TOP) {
			// 第一行头的竖线不显示
			if (getItemCount() == 1) {//只有一条是特殊处理
				itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
				itemHolder.tvBottomLine.setVisibility(View.INVISIBLE);
				itemHolder.tvAcceptTime.setTextColor(color);
				itemHolder.tvAcceptStation.setTextColor(color);
				itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_last);
			} else {
				itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
				itemHolder.tvBottomLine.setVisibility(View.VISIBLE);
				itemHolder.tvAcceptTime.setTextColor(gray);
				itemHolder.tvAcceptStation.setTextColor(gray);
				itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_normal);
			}
		} else if (getItemViewType(position) == TYPE_NORMAL) {
			itemHolder.tvBottomLine.setVisibility(View.VISIBLE);
			itemHolder.tvTopLine.setVisibility(View.VISIBLE);
			itemHolder.tvAcceptTime.setTextColor(gray);
			itemHolder.tvAcceptStation.setTextColor(gray);
			itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_normal);
		} else {
			itemHolder.tvTopLine.setVisibility(View.VISIBLE);
			itemHolder.tvBottomLine.setVisibility(View.INVISIBLE);
			// 字体颜色蓝色
			itemHolder.tvAcceptTime.setTextColor(color);
			itemHolder.tvAcceptStation.setTextColor(color);
			itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot_last);
		}

		itemHolder.bindHolder(traceList.get(position));
	}

	@Override
	public int getItemCount() {
		return traceList.size();
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return TYPE_TOP;
		}
		if (position == getItemCount() - 1) {
			return TYPE_Bottom;
		}
		return TYPE_NORMAL;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		private TextView tvAcceptTime, tvAcceptStation;
		private TextView tvTopLine, tvDot, tvBottomLine;

		public ViewHolder(View itemView) {
			super(itemView);
			tvAcceptTime = (TextView) itemView.findViewById(R.id.tvAcceptTime);
			tvAcceptStation = (TextView) itemView.findViewById(R.id.tvAcceptStation);
			tvTopLine = (TextView) itemView.findViewById(R.id.tvTopLine);
			tvBottomLine = ((TextView) itemView.findViewById(R.id.tvBottomLine));
			tvDot = (TextView) itemView.findViewById(R.id.tvDot);
		}

		public void bindHolder(MyOrderDetailBean.RepairsOrderLogVo trace) {
			tvAcceptTime.setText(DateUtils.formatDate(trace.getCreatedDate(), DateUtils.TYPE_04));
			tvAcceptStation.setText(trace.getContent());
		}
	}
}