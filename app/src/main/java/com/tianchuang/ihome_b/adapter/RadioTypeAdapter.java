package com.tianchuang.ihome_b.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;
import com.tianchuang.ihome_b.bean.FormTypeItemBean;

import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:抢单大厅的适配器
 */
public class RadioTypeAdapter extends BaseQuickAdapter<FormTypeItemBean.FieldsBean.FieldExtrasBean, BaseViewHolder> {


    public RadioTypeAdapter(int layoutResId, List<FormTypeItemBean.FieldsBean.FieldExtrasBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, FormTypeItemBean.FieldsBean.FieldExtrasBean item) {
        helper.setText(R.id.tv_equipment_name, item.getValue());
        View view = helper.getView(R.id.tv_equipment_name);
        if (item.isSelected()) {
            view.setSelected(true);
        } else {
            view.setSelected(false);
        }
//		mView.setSelected(item.isSelected());
    }

}
