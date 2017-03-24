package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Abyss on 2017/3/6.
 * description:
 */

public class ListBean implements Serializable {
    private ArrayList<MaterialListItemBean> materialTypeList;
    private ArrayList<ChargeTypeListItemBean> chargeTypeList;
    private ArrayList<QrCodeBean> qrCodeBeanArrayList;

    public ArrayList<QrCodeBean> getQrCodeBeanArrayList() {
        return qrCodeBeanArrayList;
    }

    public void setQrCodeBeanArrayList(ArrayList<QrCodeBean> qrCodeBeanArrayList) {

        this.qrCodeBeanArrayList = qrCodeBeanArrayList;
    }

    public ArrayList<ChargeTypeListItemBean> getChargeTypeList() {
        return chargeTypeList;
    }

    public ListBean setChargeTypeList(ArrayList<ChargeTypeListItemBean> chargeTypeList) {
        this.chargeTypeList = chargeTypeList;
        return this;
    }

    public ListBean setMaterialTypeList(ArrayList<MaterialListItemBean> materialTypeList) {
        this.materialTypeList = materialTypeList;
        return this;
    }

    public ArrayList<MaterialListItemBean> getMaterialTypeList() {
        return materialTypeList;
    }
}
