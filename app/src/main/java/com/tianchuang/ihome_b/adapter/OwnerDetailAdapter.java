package com.tianchuang.ihome_b.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.OwnerDetailListBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/25.
 * description:
 */

public class OwnerDetailAdapter extends BaseQuickAdapter<OwnerDetailListBean,BaseViewHolder> {
    public OwnerDetailAdapter(List<OwnerDetailListBean> data) {
        super(R.layout.fragment_owner_detail_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OwnerDetailListBean item) {
        RecyclerView listView = (RecyclerView) helper.getView(R.id.rv_list);
        listView.setLayoutManager(new LinearLayoutManager(listView.getContext()));
        listView.setAdapter(new OwnerDetailItemAdapter(item.getOwnerInfo()));
        helper.setText(R.id.tv_title, StringUtils.getNotNull(item.getTitle()));
    }
}
