package com.tianchuang.ihome_b.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abyss on 2017/3/3.
 * description:进行中与未完成订单的bean
 */

public class MyOrderListBean {

	/**
	 * pageSize : 2
	 * listVo : [{"id":5,"typeName":"进水","status":1,"statusMsg":"待服务","content":"lolo","imgCount":0,"createdDate":1488253206},{"id":2,"typeName":"进水","status":1,"statusMsg":"待服务","content":"2报修文字描述内容报修文字描述内容报修文字描述内容","imgCount":2,"createdDate":1487324326}]
	 */

	private int pageSize;
	private List<MyOrderCommonBean> listVo;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<MyOrderCommonBean> getListVo() {
		return listVo;
	}

	public void setListVo(List<MyOrderCommonBean> listVo) {
		this.listVo = listVo;
	}

	public static class ListVoBean {

	}
}
