package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/25.
 * description:业主详情Bean
 */

public class OwnerDetailBean implements Serializable {

    /**
     * address
     * ownersInfo : {"name":"李斯","value":"18158171066"}
     * ordinaryUserInfo : [{"name":"李斯","value":"18158171066"},{"name":"大熊","value":"18303052208"},{"name":"张三丰","value":"18803040506"},{"name":"star","value":"00000000000"},{"name":"五步","value":"18767103457"},{"name":"lmf","value":"15757100843"},{"name":"王文生","value":"13867153924"}]
     * ownerDesignateInfo : {"name":"李斯","value":"18158171066"}
     */
    private OwnersInfoBean ownersInfo;
    private OwnersInfoBean ownerDesignateInfo;
    private List<OwnersInfoBean> ordinaryUserInfo;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OwnersInfoBean getOwnersInfo() {
        return ownersInfo;
    }

    public void setOwnersInfo(OwnersInfoBean ownersInfo) {
        this.ownersInfo = ownersInfo;
    }

    public OwnersInfoBean getOwnerDesignateInfo() {
        return ownerDesignateInfo;
    }

    public void setOwnerDesignateInfo(OwnersInfoBean ownerDesignateInfo) {
        this.ownerDesignateInfo = ownerDesignateInfo;
    }

    public List<OwnersInfoBean> getOrdinaryUserInfo() {
        return ordinaryUserInfo;
    }

    public void setOrdinaryUserInfo(List<OwnersInfoBean> ordinaryUserInfo) {
        this.ordinaryUserInfo = ordinaryUserInfo;
    }

    public static class OwnersInfoBean {
        /**
         * name : 李斯
         * value : 18158171066
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
