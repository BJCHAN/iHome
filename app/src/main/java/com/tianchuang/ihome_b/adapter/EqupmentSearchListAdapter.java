package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.EquipmentSearchListItemBean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/24.
 * description:
 */

public class EqupmentSearchListAdapter extends BaseQuickAdapter<EquipmentSearchListItemBean,BaseViewHolder> {
    public EqupmentSearchListAdapter(List<EquipmentSearchListItemBean> data) {
        super(R.layout.equipment_search_list_item_holder,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EquipmentSearchListItemBean item) {
        helper.setText(R.id.tv_item_name,item.getName())
                .setText(R.id.tv_item_code,item.getCode()+"");
    }
}
