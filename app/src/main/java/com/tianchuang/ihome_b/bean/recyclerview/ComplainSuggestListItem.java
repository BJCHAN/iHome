package com.tianchuang.ihome_b.bean.recyclerview;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tianchuang.ihome_b.bean.ComplainSuggestProcessedBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestUntratedBean;

import java.io.Serializable;

/**
 * Created by wujian on 2017/3/1.
 * 投诉建议item数据
 */

public class ComplainSuggestListItem implements MultiItemEntity, Serializable {
    //未处理投诉建议
    public static final int TYPE_UNTRATED = 0;
    //已处理投诉建议
    public static final int TYPE_PROCESSED = 1;
    //数据类型
    private int type;
    //已处理投诉建议
    private ComplainSuggestProcessedBean.ListVoBean processedListVoBean;
    //未处理投诉建议
    private ComplainSuggestUntratedBean.ListVoBean untratedListVoBean;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public ComplainSuggestUntratedBean.ListVoBean getUntratedListVoBean() {
        return untratedListVoBean;
    }

    public void setUntratedListVoBean(ComplainSuggestUntratedBean.ListVoBean untratedListVoBean) {
        this.untratedListVoBean = untratedListVoBean;
    }

    public ComplainSuggestProcessedBean.ListVoBean getProcessedListVoBean() {
        return processedListVoBean;
    }

    public void setProcessedListVoBean(ComplainSuggestProcessedBean.ListVoBean processedListVoBean) {
        this.processedListVoBean = processedListVoBean;
    }

    @Override
    public int getItemType() {
        return getType();
    }
}
