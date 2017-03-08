package com.tianchuang.ihome_b.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.VisitorBean;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.List;

/**
 * Created by Abyss on 2017/3/8.
 * description:
 */

public class VistorListAdapter extends BaseQuickAdapter<VisitorBean.VisitorItemBean, BaseViewHolder> {


    public VistorListAdapter(int layoutResId, List<VisitorBean.VisitorItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VisitorBean.VisitorItemBean item) {
        VisitorBean.VisitorItemBean.OwnersRoomVoBean ownersRoomVo = item.getOwnersRoomVo();
        helper.setText(R.id.tv_mobile, StringUtils.getNotNull(item.getMobile()))
                .setText(R.id.tv_name_num, String.format("%s(%s人)", item.getName(), item.getNum()))
                .setText(R.id.tv_plateNumber, "车牌号：" + item.getPlateNumber())
                .setText(R.id.tv_owner, ownersRoomVo.getOwnersName())
                .setText(R.id.tv_address, String.format("%s-%s-%s", ownersRoomVo.getBuildingName(), ownersRoomVo.getBuildingCellName(), ownersRoomVo.getBuildingUnitName()));

    }

}
