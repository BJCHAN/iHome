package com.tianchuang.ihome_b.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.CommonFeeBean;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.view.ItemRemoveViewHolder;

import java.util.ArrayList;

public class ItemRemoveAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<CommonFeeBean> mList;

    public ItemRemoveAdapter(Context context, ArrayList<CommonFeeBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemRemoveViewHolder(mInflater.inflate(R.layout.recyclerview_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemRemoveViewHolder viewHolder = (ItemRemoveViewHolder) holder;
        CommonFeeBean commonFeeBean = mList.get(position);
        viewHolder.content.setText(commonFeeBean.getTitle());
        viewHolder.price.setText(StringUtils.formatNum(Float.valueOf(commonFeeBean.getFee())));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }
}
