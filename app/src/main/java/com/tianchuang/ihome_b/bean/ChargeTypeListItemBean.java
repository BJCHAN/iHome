package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/5.
 * description:
 */

public class ChargeTypeListItemBean {
	/**
	 * name : 水维修人工费
	 * fees : 20
	 * units : 1
	 * unitsDesc : 次
	 * id : 6
	 */

	private String name;
	private float fees;

	public float getFees() {
		return fees;
	}

	public void setFees(float fees) {
		this.fees = fees;
	}

	private int units;
	private String unitsDesc;
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public String getUnitsDesc() {
		return unitsDesc;
	}

	public void setUnitsDesc(String unitsDesc) {
		this.unitsDesc = unitsDesc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
