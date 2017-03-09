package com.tianchuang.ihome_b.bean;

import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerReportsItemBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Abyss on 2017/3/9.
 * description:
 */

public class BaseListLoadBean<T> implements Serializable {
    private int pageSize;
    private ArrayList<T> listVo;

    public int getPageSize() {
        return pageSize;
    }

    public void setListVo(ArrayList<T> listVo) {
        this.listVo = listVo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ArrayList<T> getListVo() {
        return listVo;
    }
}
