package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.EquipmentDetailBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/25.
 * description:
 */

public class EquipmentDetailAdapter extends BaseQuickAdapter<EquipmentDetailBean.FieldDataListBean,BaseViewHolder> {
    public EquipmentDetailAdapter( List<EquipmentDetailBean.FieldDataListBean> data) {
        super(R.layout.equipment_detail_item_holder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EquipmentDetailBean.FieldDataListBean item) {
        helper.setText(R.id.tv_title, StringUtils.getNotNull(item.getFieldName()+"："))
                .setText(R.id.tv_name, StringUtils.getNotNull(item.getFieldValue()));
    }
}
