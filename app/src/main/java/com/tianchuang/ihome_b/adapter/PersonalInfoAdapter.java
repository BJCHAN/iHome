package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.PersonalInfoBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/29.
 * description:
 */

public class PersonalInfoAdapter extends BaseQuickAdapter<PersonalInfoBean,BaseViewHolder> {
    public PersonalInfoAdapter(List<PersonalInfoBean> data) {
        super(R.layout.personal_info_item_holder,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonalInfoBean item) {
        helper.setText(R.id.tv_address, StringUtils.getNotNull(String.format("%s-%s", item.getProjectName(), item.getPropertyName())))
                .setText(R.id.tv_postion, StringUtils.getNotNull(item.getPositionInfo()))
                .setText(R.id.tv_name,StringUtils.getNotNull("物业经理："+item.getName()))
                .setText(R.id.tv_phone, StringUtils.getNotNull(String.format("手机：%s (非工作时间)",item.getMobile())));
    }
}
