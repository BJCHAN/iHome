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
        addItemType(HomePageMultiItem.TYPE_NOTICE, R.layout.notification_list_item_holder);
        addItemType(HomePageMultiItem.TYPE_INNER_REPORT, R.layout.inner_reports_item_holder);
        addItemType(HomePageMultiItem.TYPE_COMPLAIN, R.layout.complain_suggest_item);
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
                String time = DateUtils.formatDate(complainItemBean.getCreatedDate(), DateUtils.TYPE_01);
                String content = complainItemBean.getContent();
                String reply = complainItemBean.getReplayContent();
                ComplainDetailBean.OwnersInfoVoBean ownersInfoVo = complainItemBean.getOwnersInfoVo();
                if (null != ownersInfoVo) {
                    String name = ownersInfoVo.getOwnersName() + "/" + ownersInfoVo.getBuildingName() + "-" + ownersInfoVo.getBuildingCellName()
                            + "-" + ownersInfoVo.getBuildingUnitName();
                    helper.setText(R.id.tv_complain_suggest_name, name);
                }
                helper.getView(R.id.bl_complain_suggest_content_apply).setVisibility(TextUtils.isEmpty(reply) ? View.GONE : View.VISIBLE);
                helper.setText(R.id.tv_complain_suggest_time, StringUtils.getNotNull(time))
                        .setText(R.id.tv_complain_suggest_content, StringUtils.getNotNull(content))
                        .setText(R.id.tv_complain_suggest_content_apply, StringUtils.getNotNull(reply));
                break;

            case HomePageMultiItem.TYPE_INNER_REPORT://内部报事
                MenuInnerReportsItemBean menuInnerReportsItemBean = item.getMenuInnerReportsItemBean();
                PropertyListItemBean propertyEmployeeRoleVo = menuInnerReportsItemBean.getPropertyEmployeeRoleVo();
                helper.setText(R.id.tv_content, menuInnerReportsItemBean.getContent())
                        .setText(R.id.tv_info, propertyEmployeeRoleVo.getEmployeeName()
                                + "/" + propertyEmployeeRoleVo.getDepartmentName()
                                + "-" + propertyEmployeeRoleVo.getPositionName())
                        .setText(R.id.tv_date, DateUtils.formatDate(menuInnerReportsItemBean.getLastUpdatedDae(), DateUtils.TYPE_01))
                        .setText(R.id.tv_status, menuInnerReportsItemBean.getStatusMsg());
                break;
            case HomePageMultiItem.TYPE_NOTICE://通知
                NotificationItemBean notificationItemBean = item.getNotificationItemBean();
                helper.setText(R.id.tv_notification_type, StringUtils.getNotNull(notificationItemBean.getTitle()))
                        .setText(R.id.tv_content, StringUtils.getNotNull(notificationItemBean.getContent()))
                        .setText(R.id.tv_notification_date, StringUtils.getNotNull(
                                DateUtils.formatDate(notificationItemBean.getCreatedDate(), DateUtils.TYPE_02)));
                TextView yearView = helper.getView(R.id.tv_year);
                if ("".equals(notificationItemBean.getYear())) {
                    yearView.setVisibility(View.GONE);
                } else {
                    yearView.setVisibility(View.VISIBLE);
                    yearView.setText(notificationItemBean.getYear());
                }
                break;
        }
    }
}
