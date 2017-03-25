package com.tianchuang.ihome_b.bean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/25.
 * description:
 */

public class OwnerDetailListBean  {
    private String title;
    private List<OwnerDetailBean.OwnersInfoBean> ownerInfo;

    public String getTitle() {
        return title;
    }

    public OwnerDetailListBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<OwnerDetailBean.OwnersInfoBean> getOwnerInfo() {
        return ownerInfo;
    }

    public OwnerDetailListBean setOwnerInfo(List<OwnerDetailBean.OwnersInfoBean> ownerInfo) {
        this.ownerInfo = ownerInfo;
        return this;
    }
}
