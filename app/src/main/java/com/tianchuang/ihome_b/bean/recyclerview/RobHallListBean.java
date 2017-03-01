package com.tianchuang.ihome_b.bean.recyclerview;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Abyss on 2017/2/28.
 * description:
 */

public class RobHallListBean implements Serializable {
	private int pageSize;
	private ArrayList<RobHallListItem> listVo;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public ArrayList<RobHallListItem> getListVo() {
		return listVo;
	}

	public void setListVo(ArrayList<RobHallListItem> listVo) {
		this.listVo = listVo;
	}
}
