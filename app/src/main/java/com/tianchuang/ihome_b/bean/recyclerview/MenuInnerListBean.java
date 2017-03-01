package com.tianchuang.ihome_b.bean.recyclerview;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Abyss on 2017/2/28.
 * description:菜单内部报事列表数据bean
 */

public class MenuInnerListBean implements Serializable {
	private int pageSize;
	private ArrayList<MenuInnerReportsItemBean> listVo;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public ArrayList<MenuInnerReportsItemBean> getListVo() {
		return listVo;
	}

	public void setListVo(ArrayList<MenuInnerReportsItemBean> listVo) {
		this.listVo = listVo;
	}
}
