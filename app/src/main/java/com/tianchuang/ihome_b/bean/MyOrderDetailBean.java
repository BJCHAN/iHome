package com.tianchuang.ihome_b.bean;

import com.tianchuang.ihome_b.bean.recyclerview.DetailMultiItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/2.
 * description:
 */

public class MyOrderDetailBean implements Serializable {


	/**
	 * id : 4
	 * typeName : 进水
	 * repairsDataVos : [{"id":12,"repairsId":4,"typeId":1,"fieldId":1,"fieldType":1,"fieldName":"文字描述","fieldKey":"fieldKey1","fieldValue":"有点痛到睡不着了，我的人都有一个人"},{"id":13,"repairsId":4,"typeId":1,"fieldId":2,"fieldType":2,"fieldName":"状态描述","fieldKey":"fieldKey2","fieldValue":"正常"},{"id":14,"repairsId":4,"typeId":1,"fieldId":3,"fieldType":3,"fieldName":"图片","fieldKey":"fieldKey3","fieldValue":"","fieldValues":["http://api-staff-test.hecaifu.com/filesupload/repairs/a1840346-595e-4f64-8dea-b0f009d3d769.png","http://api-staff-test.hecaifu.com/filesupload/repairs/f95ad057-a681-4554-92c1-be788ee90288.png"]}]
	 * status : 3
	 * createdDate : 1488252083
	 * ownersInfoVo : {"ownersId":1,"ownersName":"","ownersMobile":"18303052208","roomId":1,"num":"101","propertyCompanyId":1,"propertyCompanyName":"海创园","buildingId":1,"buildingName":"海创苑","buildingCellId":1,"buildingCellName":"1幢","buildingUnitId":1,"buildingUnitName":"1单元","oftenUse":false,"propertyEnable":true}
	 * repairsFeeVos : [{"id":1,"repairsId":4,"title":"人工费","counts":1,"fee":20},{"id":2,"repairsId":4,"title":"水管","counts":3,"fee":30}]
	 * totalFee : 50
	 * evaluateStar : 0
	 * evaluate : 服务好,服务好服务好服务好
	 * repairsOrderLogVos : []
	 */

	private int id;
	private String typeName;
	private int status;
	private int createdDate;
	private OwnersInfoVoBean ownersInfoVo;
	private int totalFee;
	private int evaluateStar;
	private String evaluate;
	private List<DetailMultiItem> repairsDataVos;
	private List<RepairsFeeVosBean> repairsFeeVos;
	private List<?> repairsOrderLogVos;

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

	public int getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(int createdDate) {
		this.createdDate = createdDate;
	}

	public OwnersInfoVoBean getOwnersInfoVo() {
		return ownersInfoVo;
	}

	public void setOwnersInfoVo(OwnersInfoVoBean ownersInfoVo) {
		this.ownersInfoVo = ownersInfoVo;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getEvaluateStar() {
		return evaluateStar;
	}

	public void setEvaluateStar(int evaluateStar) {
		this.evaluateStar = evaluateStar;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public List<DetailMultiItem> getRepairsDataVos() {
		return repairsDataVos;
	}

	public void setRepairsDataVos(List<DetailMultiItem> repairsDataVos) {
		this.repairsDataVos = repairsDataVos;
	}

	public List<RepairsFeeVosBean> getRepairsFeeVos() {
		return repairsFeeVos;
	}

	public void setRepairsFeeVos(List<RepairsFeeVosBean> repairsFeeVos) {
		this.repairsFeeVos = repairsFeeVos;
	}

	public List<?> getRepairsOrderLogVos() {
		return repairsOrderLogVos;
	}

	public void setRepairsOrderLogVos(List<?> repairsOrderLogVos) {
		this.repairsOrderLogVos = repairsOrderLogVos;
	}

	public static class OwnersInfoVoBean {
		/**
		 * ownersId : 1
		 * ownersName :
		 * ownersMobile : 18303052208
		 * roomId : 1
		 * num : 101
		 * propertyCompanyId : 1
		 * propertyCompanyName : 海创园
		 * buildingId : 1
		 * buildingName : 海创苑
		 * buildingCellId : 1
		 * buildingCellName : 1幢
		 * buildingUnitId : 1
		 * buildingUnitName : 1单元
		 * oftenUse : false
		 * propertyEnable : true
		 */

		private int ownersId;
		private String ownersName;
		private String ownersMobile;
		private int roomId;
		private String num;
		private int propertyCompanyId;
		private String propertyCompanyName;
		private int buildingId;
		private String buildingName;
		private int buildingCellId;
		private String buildingCellName;
		private int buildingUnitId;
		private String buildingUnitName;
		private boolean oftenUse;
		private boolean propertyEnable;

		public int getOwnersId() {
			return ownersId;
		}

		public void setOwnersId(int ownersId) {
			this.ownersId = ownersId;
		}

		public String getOwnersName() {
			return ownersName;
		}

		public void setOwnersName(String ownersName) {
			this.ownersName = ownersName;
		}

		public String getOwnersMobile() {
			return ownersMobile;
		}

		public void setOwnersMobile(String ownersMobile) {
			this.ownersMobile = ownersMobile;
		}

		public int getRoomId() {
			return roomId;
		}

		public void setRoomId(int roomId) {
			this.roomId = roomId;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public int getPropertyCompanyId() {
			return propertyCompanyId;
		}

		public void setPropertyCompanyId(int propertyCompanyId) {
			this.propertyCompanyId = propertyCompanyId;
		}

		public String getPropertyCompanyName() {
			return propertyCompanyName;
		}

		public void setPropertyCompanyName(String propertyCompanyName) {
			this.propertyCompanyName = propertyCompanyName;
		}

		public int getBuildingId() {
			return buildingId;
		}

		public void setBuildingId(int buildingId) {
			this.buildingId = buildingId;
		}

		public String getBuildingName() {
			return buildingName;
		}

		public void setBuildingName(String buildingName) {
			this.buildingName = buildingName;
		}

		public int getBuildingCellId() {
			return buildingCellId;
		}

		public void setBuildingCellId(int buildingCellId) {
			this.buildingCellId = buildingCellId;
		}

		public String getBuildingCellName() {
			return buildingCellName;
		}

		public void setBuildingCellName(String buildingCellName) {
			this.buildingCellName = buildingCellName;
		}

		public int getBuildingUnitId() {
			return buildingUnitId;
		}

		public void setBuildingUnitId(int buildingUnitId) {
			this.buildingUnitId = buildingUnitId;
		}

		public String getBuildingUnitName() {
			return buildingUnitName;
		}

		public void setBuildingUnitName(String buildingUnitName) {
			this.buildingUnitName = buildingUnitName;
		}

		public boolean isOftenUse() {
			return oftenUse;
		}

		public void setOftenUse(boolean oftenUse) {
			this.oftenUse = oftenUse;
		}

		public boolean isPropertyEnable() {
			return propertyEnable;
		}

		public void setPropertyEnable(boolean propertyEnable) {
			this.propertyEnable = propertyEnable;
		}
	}


	public static class RepairsFeeVosBean {
		/**
		 * id : 1
		 * repairsId : 4
		 * title : 人工费
		 * counts : 1
		 * fee : 20
		 */

		private int id;
		private int repairsId;
		private String title;
		private int counts;
		private int fee;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getRepairsId() {
			return repairsId;
		}

		public void setRepairsId(int repairsId) {
			this.repairsId = repairsId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getCounts() {
			return counts;
		}

		public void setCounts(int counts) {
			this.counts = counts;
		}

		public int getFee() {
			return fee;
		}

		public void setFee(int fee) {
			this.fee = fee;
		}
	}
}
