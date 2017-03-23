package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/2.
 * description:
 */

public class ComplainDetailBean implements Serializable {

	/**
	 * id : 18
	 * typeName : 进水
	 * content:dkd
	 * complaintsDataVos : [{"id":36,"complaintsId":18,"typeId":1,"fieldId":1,"fieldType":1,"fieldKey":"fieldKey1","fieldValue":"一起来看流星雨呢"},{"id":37,"complaintsId":18,"typeId":1,"fieldId":2,"fieldType":2,"fieldKey":"fieldKey2","fieldValue":"正常"},{"id":38,"complaintsId":18,"typeId":1,"fieldId":3,"fieldType":3,"fieldKey":"fieldKey3","fieldValue":"","fieldValues":["http://api-staff-test.hecaifu.com/filesupload/complaints/a8325026-646f-437f-b489-bdc551d3cb9a.png","http://api-staff-test.hecaifu.com/filesupload/complaints/80d8b2c6-a66f-44ef-8aed-23f7bcd29a1b.png"]}]
	 * status : 1
	 * createdDate : 1488191882
	 * ownersInfoVo : {"ownersId":1,"ownersName":"","ownersMobile":"18303052208","roomId":1,"num":"101","propertyCompanyId":1,"propertyCompanyName":"海创园","buildingId":1,"buildingName":"海创苑","buildingCellId":1,"buildingCellName":"1幢","buildingUnitId":1,"buildingUnitName":"1单元","oftenUse":true}
	 * replayEmployeeId : 2
	 * replayContent : 尊敬的业主，您提交的问题已经修复
	 */

	private int id;
	private String typeName;
	private int status;
	private int createdDate;
	private OwnersInfoVoBean ownersInfoVo;
	private int replayEmployeeId;
	private String replyContent;

	private List<DetailMultiItem> complaintsDataVos;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<DetailMultiItem> getComplaintsDataVos() {
		return complaintsDataVos;
	}

	public void setComplaintsDataVos(List<DetailMultiItem> complaintsDataVos) {
		this.complaintsDataVos = complaintsDataVos;
	}

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

	public int getReplayEmployeeId() {
		return replayEmployeeId;
	}

	public void setReplayEmployeeId(int replayEmployeeId) {
		this.replayEmployeeId = replayEmployeeId;
	}

	public String getReplayContent() {
		return replyContent;
	}

	public void setReplayContent(String replayContent) {
		this.replyContent = replayContent;
	}

	public static class OwnersInfoVoBean implements Serializable{
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
		 * oftenUse : true
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
	}

}
