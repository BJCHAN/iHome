package com.tianchuang.ihome_b.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/25.
 * description:
 */

public class BuildingRoomItemBean implements Serializable,IPickerViewData {


    /**
     * propertyCompanyId : 1
     * buildingId : 1
     * buildingCellId : 1
     * buildingUnitId : 1
     * num : 101
     * area : 89
     * id : 1
     * createdDate : 1487067514
     * lastUpdatedDate : 1487067514
     */

    private int propertyCompanyId;
    private int buildingId;
    private int buildingCellId;
    private int buildingUnitId;
    private String num;
    private int area;
    private int id;
    private int createdDate;
    private int lastUpdatedDate;

    @Override
    public String getPickerViewText() {
        return num;
    }

    public int getPropertyCompanyId() {
        return propertyCompanyId;
    }

    public void setPropertyCompanyId(int propertyCompanyId) {
        this.propertyCompanyId = propertyCompanyId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getBuildingCellId() {
        return buildingCellId;
    }

    public void setBuildingCellId(int buildingCellId) {
        this.buildingCellId = buildingCellId;
    }

    public int getBuildingUnitId() {
        return buildingUnitId;
    }

    public void setBuildingUnitId(int buildingUnitId) {
        this.buildingUnitId = buildingUnitId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
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
