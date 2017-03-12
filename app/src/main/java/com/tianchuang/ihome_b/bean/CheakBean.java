package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/12.
 * description:用于检查的类
 */

public class CheakBean {
    private boolean isCan = true;
    private String tip = "";

    public boolean isCan() {
        return isCan;
    }

    public void setCan(boolean can) {
        isCan = can;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
