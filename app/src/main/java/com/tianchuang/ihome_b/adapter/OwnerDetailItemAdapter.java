package com.tianchuang.ihome_b.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.OwnerDetailBean;
import com.tianchuang.ihome_b.bean.OwnerDetailListBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/25.
 * description:
 */

public class OwnerDetailItemAdapter extends BaseQuickAdapter<OwnerDetailBean.OwnersInfoBean,BaseViewHolder> {
    public OwnerDetailItemAdapter(List<OwnerDetailBean.OwnersInfoBean> data) {
        super(R.layout.fragment_owner_detail_small_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OwnerDetailBean.OwnersInfoBean item) {
        if (item != null) {
            helper.setText(R.id.tv_name, StringUtils.getNotNull(item.getName()))
                    .setText(R.id.tv_value, StringUtils.getNotNull(item.getValue()));
        }


    }
}
