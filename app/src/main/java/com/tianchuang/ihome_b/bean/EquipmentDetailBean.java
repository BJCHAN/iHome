package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by abyss on 2017/3/25.
 */

public class EquipmentDetailBean implements Serializable {

    /**
     * code : TC2017030231486
     * name : 空调
     * equipmentTypeId : 1
     * brand : 格力
     * model : A10
     * place : 一幢一单元1层001
     * formTypeId : 5
     * status : 0
     * formTypeName : 提交材料2
     * id : 3
     * equipmentTypeName : 机器类
     * used : false
     * fieldDataList : [{"fieldName":"编号","fieldValue":"A10-0001","fieldId":1},{"fieldName":"名称","fieldValue":"123123","fieldId":2}]
     */

    private String code;
    private String name;
    private int equipmentTypeId;
    private String brand;
    private String model;
    private String place;
    private int formTypeId;
    private int status;
    private String formTypeName;
    private int id;
    private String equipmentTypeName;
    private boolean used;
    private List<FieldDataListBean> fieldDataList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(int equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(int formTypeId) {
        this.formTypeId = formTypeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFormTypeName() {
        return formTypeName;
    }

    public void setFormTypeName(String formTypeName) {
        this.formTypeName = formTypeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public List<FieldDataListBean> getFieldDataList() {
        return fieldDataList;
    }

    public void setFieldDataList(List<FieldDataListBean> fieldDataList) {
        this.fieldDataList = fieldDataList;
    }

    public static class FieldDataListBean implements Serializable{
        /**
         * fieldName : 编号
         * fieldValue : A10-0001
         * fieldId : 1
         */

        private String fieldName;
        private String fieldValue;
        private int fieldId;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        public int getFieldId() {
            return fieldId;
        }

        public void setFieldId(int fieldId) {
            this.fieldId = fieldId;
        }
    }
}
