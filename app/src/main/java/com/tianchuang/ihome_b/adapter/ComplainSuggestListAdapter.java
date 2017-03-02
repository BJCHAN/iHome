package com.tianchuang.ihome_b.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.ComplainSuggestProcessedBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestUntratedBean;
import com.tianchuang.ihome_b.bean.recyclerview.ComplainSuggestListItem;
import com.tianchuang.ihome_b.utils.DateUtils;

import java.util.List;

/**
 * Created by wujian on 2017/3/1.
 * description:投诉建议列表适配器
 */
public class ComplainSuggestListAdapter extends BaseMultiItemQuickAdapter<ComplainSuggestListItem, BaseViewHolder> {

    public ComplainSuggestListAdapter(List<ComplainSuggestListItem> data) {
        super(data);
        //已处理投诉建议
        addItemType(ComplainSuggestListItem.TYPE_PROCESSED, R.layout.complain_suggest_item);
        //未处理投诉建议
        addItemType(ComplainSuggestListItem.TYPE_UNTRATED, R.layout.complain_suggest_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComplainSuggestListItem item) {
        int itemViewType = helper.getItemViewType();
        switch (itemViewType) {
            //已处理投诉建议
            case ComplainSuggestListItem.TYPE_PROCESSED:
                ComplainSuggestProcessedBean.ListVoBean processedListVoBean = item.getProcessedListVoBean();
                ComplainSuggestProcessedBean.ListVoBean.OwnersInfoVoBean ownersInfoVo = processedListVoBean.getOwnersInfoVo();
                helper.getView(R.id.bl_complain_suggest_content_apply).setVisibility(View.VISIBLE);
                if (null != processedListVoBean && null != ownersInfoVo) {
                    String name = ownersInfoVo.getOwnersName() + "/" + ownersInfoVo.getBuildingName() + "-" + ownersInfoVo.getBuildingCellName()
                            + "-" + ownersInfoVo.getBuildingUnitName();
                    String time = DateUtils.formatDate(processedListVoBean.getCreatedDate(), DateUtils.TYPE_01);
                    String content = processedListVoBean.getContent();
                    String reply = processedListVoBean.getReplayContent();
                    helper.setText(R.id.tv_complain_suggest_name, name)
                            .setText(R.id.tv_complain_suggest_time, time)
                            .setText(R.id.tv_complain_suggest_content, content)
                            .setText(R.id.tv_complain_suggest_content_apply, reply);
                }
                break;
            //未处理投诉建议
            case ComplainSuggestListItem.TYPE_UNTRATED:
                ComplainSuggestUntratedBean.ListVoBean untratedListVoBean = item.getUntratedListVoBean();
                ComplainSuggestUntratedBean.ListVoBean.OwnersInfoVoBean ownersInfoVo1 = untratedListVoBean.getOwnersInfoVo();
                helper.getView(R.id.bl_complain_suggest_content_apply).setVisibility(View.GONE);
                if (null != untratedListVoBean && null != ownersInfoVo1) {
                    String name = ownersInfoVo1.getOwnersName() + "/" + ownersInfoVo1.getBuildingName() + "-" + ownersInfoVo1.getBuildingCellName()
                            + "-" + ownersInfoVo1.getBuildingUnitName();
                    String time = DateUtils.formatDate(untratedListVoBean.getCreatedDate(), DateUtils.TYPE_01);
                    String content = untratedListVoBean.getContent();
                    helper.setText(R.id.tv_complain_suggest_name, name)
                            .setText(R.id.tv_complain_suggest_time, time)
                            .setText(R.id.tv_complain_suggest_content, content);
                }
                break;
            default:
        }
    }
}
