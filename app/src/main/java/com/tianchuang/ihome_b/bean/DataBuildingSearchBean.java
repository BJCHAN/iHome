package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/1.
 * description:楼宇查询
 */

public class DataBuildingSearchBean implements Serializable {

	private String name;
	private List<ItemListBean> itemList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ItemListBean> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemListBean> itemList) {
		this.itemList = itemList;
	}

	public static class ItemListBean {
		/**
		 * buildingId : 1
		 * number : 1幢
		 * unit : 4
		 * floor : 10
		 * id : 1
		 * createdDate : 1487324326
		 * lastUpdatedDate : 1487324326
		 */

		private int buildingId;
		private String number;
		private int unit;
		private int floor;
		private int id;
		private int createdDate;
		private int lastUpdatedDate;

		public int getBuildingId() {
			return buildingId;
		}

		public void setBuildingId(int buildingId) {
			this.buildingId = buildingId;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public int getUnit() {
			return unit;
		}

		public void setUnit(int unit) {
			this.unit = unit;
		}

		public int getFloor() {
			return floor;
		}

		public void setFloor(int floor) {
			this.floor = floor;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(int createdDate) {
			this.createdDate = createdDate;
		}

		public int getLastUpdatedDate() {
			return lastUpdatedDate;
		}

		public void setLastUpdatedDate(int lastUpdatedDate) {
			this.lastUpdatedDate = lastUpdatedDate;
		}
	}
}
