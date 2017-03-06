package com.tianchuang.ihome_b.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/5.
 * description:
 */

public class MaterialListItemBean extends SelectedBean implements Serializable {


	/**
	 * id : 1
	 * propertyCompanyId : 1
	 * materialTypeId : 1
	 * typeName : 水晶头
	 * typeUnits : 个
	 * brand : 安普
	 * model : 网络
	 * buyPrice : 1
	 * salePrice : 2
	 * amount : 100
	 */

	private int id;
	private int propertyCompanyId;
	private int materialTypeId;
	private String typeName;
	private String typeUnits;
	private String brand;
	private String model;
	private float buyPrice;

	public float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(float buyPrice) {
		this.buyPrice = buyPrice;
	}

	public float getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	private float salePrice;
	private float amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPropertyCompanyId() {
		return propertyCompanyId;
	}

	public void setPropertyCompanyId(int propertyCompanyId) {
		this.propertyCompanyId = propertyCompanyId;
	}

	public int getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(int materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeUnits() {
		return typeUnits;
	}

	public void setTypeUnits(String typeUnits) {
		this.typeUnits = typeUnits;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
