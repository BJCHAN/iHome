package com.tianchuang.ihome_b.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.ComplainDetailBean;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.bean.PropertyListItemBean;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/22.
 * description:主页多类型item的adapter
 */

public class HomeMultiAdapter extends BaseMultiItemQuickAdapter<HomePageMultiItem, BaseViewHolder> {

    public HomeMultiAdapter(List<HomePageMultiItem> data) {
        super(data);
        addItemType(HomePageMultiItem.TYPE_TASK, R.layout.mytask_under_way_item_holder);
        addItemType(HomePageMultiItem.TYPE_NOTICE, R.layout.home_notice_multi_item_holder);
        addItemType(HomePageMultiItem.TYPE_INNER_REPORT, R.layout.home_inner_reports_multi_item_holder);
        addItemType(HomePageMultiItem.TYPE_COMPLAIN, R.layout.home_complain_multi_item_holder);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageMultiItem item) {
        switch (helper.getItemViewType()) {
            case HomePageMultiItem.TYPE_TASK://任务
                MyTaskUnderWayItemBean taskItemBean = item.getMyTaskUnderWayItemBean();
                helper.setText(R.id.tv_type, StringUtils.getNotNull(taskItemBean.getTaskName()))
                        .setText(R.id.tv_content, StringUtils.getNotNull(DateUtils
                                .formatDate(taskItemBean.getCreatedDate(), DateUtils.TYPE_04) + taskItemBean.getTaskExplains()));
                break;
            case HomePageMultiItem.TYPE_COMPLAIN://投诉
                ComplainDetailBean complainItemBean = item.getComplainItemBean();
                String content = complainItemBean.getContent();
                helper.setText(R.id.tv_content, StringUtils.getNotNull(content));
                break;

            case HomePageMultiItem.TYPE_INNER_REPORT://内部报事
                MenuInnerReportsItemBean menuInnerReportsItemBean = item.getMenuInnerReportsItemBean();
                PropertyListItemBean propertyEmployeeRoleVo = menuInnerReportsItemBean.getPropertyEmployeeRoleVo();
                helper.setText(R.id.tv_content, menuInnerReportsItemBean.getContent())
                        .setText(R.id.tv_info, propertyEmployeeRoleVo.getDepartmentName()
                                + "/" + propertyEmployeeRoleVo.getEmployeeName());
                break;
            case HomePageMultiItem.TYPE_NOTICE://通知
                NotificationItemBean notificationItemBean = item.getNotificationItemBean();
                helper.setText(R.id.tv_type, StringUtils.getNotNull(notificationItemBean.getTitle()))
                        .setText(R.id.tv_content, StringUtils.getNotNull(notificationItemBean.getContent()));
                break;
        }
    }
}
