package com.tianchuang.ihome_b.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.MyFormItemBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/13.
 * description:我的表单列表的适配器
 */

public class MyFormListAdapter extends BaseQuickAdapter<MyFormItemBean, BaseViewHolder> {


    public MyFormListAdapter(int layoutResId, List<MyFormItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFormItemBean item) {


        TextView textView = (TextView) helper.getView(R.id.tv_year);
        if ("".equals(item.getYear())) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(item.getYear());
        }

        helper.setText(R.id.tv_form_type, StringUtils.getNotNull(item.getTypeName()))
                .setText(R.id.tv_form_date, DateUtils.formatDate(item.getCreatedDate(), DateUtils.TYPE_02));
    }
}
