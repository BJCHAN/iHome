package com.tianchuang.ihome_b.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/15.
 * description:通知列表的adapter
 */

public class NotificationListAdapter extends BaseQuickAdapter<NotificationItemBean, BaseViewHolder> {
    public NotificationListAdapter(List<NotificationItemBean> data) {
        super(R.layout.notification_list_item_holder, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NotificationItemBean item) {
        helper.setText(R.id.tv_notification_type, StringUtils.getNotNull(item.getTitle()))
                .setText(R.id.tv_content, StringUtils.getNotNull(item.getContent()))
                .setText(R.id.tv_notification_date, StringUtils.getNotNull(
                        DateUtils.formatDate(item.getCreatedDate(), DateUtils.TYPE_02)));
        TextView yearView = (TextView) helper.getView(R.id.tv_year);
        if ("".equals(item.getYear())) {
            yearView.setVisibility(View.GONE);
        } else {
            yearView.setVisibility(View.VISIBLE);
            yearView.setText(item.getYear());
        }
    }
}
