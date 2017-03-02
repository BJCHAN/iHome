package com.tianchuang.ihome_b.bean.recyclerview;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:故障报修详情
 */

public class DetailMultiItem implements Serializable, MultiItemEntity {


	/**
	 * id : 12
	 * repairsId : 4
	 * typeId : 1
	 * fieldId : 1
	 * fieldType : 1
	 * fieldKey : fieldKey1
	 * fieldValue : 有点痛到睡不着了，我的人都有一个人
	 * fieldValues : ["http://api-staff-test.hecaifu.com/filesupload/repairs/a1840346-595e-4f64-8dea-b0f009d3d769.png","http://api-staff-test.hecaifu.com/filesupload/repairs/f95ad057-a681-4554-92c1-be788ee90288.png"]
	 */

	private int id;
	private int repairsId;
	private int typeId;
	private int fieldId;
	private int fieldType;
	private String fieldName;
	private String fieldKey;
	private String fieldValue;
	private List<String> fieldValues;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getRepairsId() {
		return repairsId;
	}

	public void setRepairsId(int repairsId) {
		this.repairsId = repairsId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	@Override
	public int getItemType() {
		return fieldType;
	}
}
