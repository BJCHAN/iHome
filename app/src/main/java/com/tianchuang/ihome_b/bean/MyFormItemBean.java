package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/13.
 * description:
 */

public class MyFormItemBean extends BaseItemLoadBean {
    /**
     * id : 8
     * propertyCompanyId : 1
     * formTypeId : 10
     * employeeId : 2
     * typeName : 表单4
     * createdDate : 1489370190
     * year : 2017
     */

    private int propertyCompanyId;
    private int formTypeId;
    private int employeeId;
    private String typeName;
    private int createdDate;
    private String year;

    public int getPropertyCompanyId() {
        return propertyCompanyId;
    }

    public void setPropertyCompanyId(int propertyCompanyId) {
        this.propertyCompanyId = propertyCompanyId;
    }

    public int getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(int formTypeId) {
        this.formTypeId = formTypeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
