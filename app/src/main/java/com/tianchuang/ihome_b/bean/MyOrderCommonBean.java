package com.tianchuang.ihome_b.bean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/3.
 * description:进行中与未完成订单的bean
 */

public class MyOrderCommonBean {

		/**
		 * id : 5
		 * typeName : 进水
		 * status : 1
		 * statusMsg : 待服务
		 * content : lolo
		 * imgCount : 0
		 * createdDate : 1488253206
		 */

		private int id;
		private String typeName;
		private int status;
		private String statusMsg;
		private String content;
		private int imgCount;
		private int createdDate;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getStatusMsg() {
			return statusMsg;
		}

		public void setStatusMsg(String statusMsg) {
			this.statusMsg = statusMsg;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getImgCount() {
			return imgCount;
		}

		public void setImgCount(int imgCount) {
			this.imgCount = imgCount;
		}

		public int getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(int createdDate) {
			this.createdDate = createdDate;
		}

}
