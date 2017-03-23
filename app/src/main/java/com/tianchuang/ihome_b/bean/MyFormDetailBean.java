package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by abyss on 2017/3/23.
 */

public class MyFormDetailBean implements Serializable {


    /**
     * id : 18
     * propertyCompanyId : 1
     * typeName : 设备检修单
     * formDataVos : [{"id":21,"formId":18,"typeId":12,"fieldId":47,"fieldType":1,"fieldName":"检修内容","fieldKey":"fieldKey47","fieldValue":"老婆婆婆婆所以我破7曲婆婆上晚自习送sponsors早睡早起YY"},{"id":22,"formId":18,"typeId":12,"fieldId":48,"fieldType":2,"fieldName":"设备状况","fieldKey":"fieldKey48","fieldValue":"正常"},{"id":27,"formId":18,"typeId":12,"fieldId":49,"fieldType":3,"fieldName":"设备检修图","fieldKey":"fieldKey49","fieldValue":"","fieldValues":["http://api-staff-test.hecaifu.com/filesupload/complaints/fadd4ae8-1797-40f0-9fa3-7f2d7727527e.jpg"]}]
     * status : 0
     * createdDate : 1490090032
     * employeeId : 2
     */

    private int id;
    private int propertyCompanyId;
    private String typeName;
    private int status;
    private int createdDate;
    private int employeeId;
    private List<DetailMultiItem> formDataVos;

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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public List<DetailMultiItem> getFormDataVos() {
        return formDataVos;
    }

    public void setFormDataVos(List<DetailMultiItem> formDataVos) {
        this.formDataVos = formDataVos;
    }


}
