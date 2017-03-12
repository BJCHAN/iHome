package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Abyss on 2017/3/9.
 * description:加载更多的数据基类
 */

public class BaseListLoadBean<T extends BaseItemLoadBean> implements Serializable {
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
