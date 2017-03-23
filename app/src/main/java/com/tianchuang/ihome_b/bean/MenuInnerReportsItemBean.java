package com.tianchuang.ihome_b.bean;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/2/9.
 * description：菜单内部报事详情的bean
 */

public class MenuInnerReportsItemBean extends BaseItemLoadBean implements Serializable {
    /**
     * id : 1
     * propertyCompanyId : 1
     * propertyEmployeeRoleVo : {"id":1,"employeeId":2,"employeeName":"李斯","propertyCompanyId":1,"propertyCompanyName":"海创园","departmentId":1,"departmentName":"工程维修部","positionId":1,"positionName":"水电维修工","oftenUse":true}
     * content : 测试未处理
     * photo1Url : http://api-staff-test.hecaifu.com/internalreport/88f7af0d-1cb8-4a6c-8f94-8f9492138fc2.png
     * photo2Url : http://api-staff-test.hecaifu.com/internalreport/88f7af0d-1cb8-4a6c-8f94-8f9492138fc2.png
     * photo3Url : http://api-staff-test.hecaifu.com/internalreport/88f7af0d-1cb8-4a6c-8f94-8f9492138fc2.png
     * statusMsg : 未处理
     *  status : 0
     * createdDate : 1487746560
     * lastUpdatedDate : 1487746560
     */
    private PropertyListItemBean propertyEmployeeRoleVo;
    private String content;
    private String photo1Url;
    private String photo2Url;
    private String photo3Url;
    private String statusMsg;
    private int status;
    private int propertyCompanyId;
    private int createdDate;
    private int lastUpdatedDate;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPropertyCompanyId() {
        return propertyCompanyId;
    }

    public void setPropertyCompanyId(int propertyCompanyId) {
        this.propertyCompanyId = propertyCompanyId;
    }

    public PropertyListItemBean getPropertyEmployeeRoleVo() {
        return propertyEmployeeRoleVo;
    }

    public void setPropertyEmployeeRoleVo(PropertyListItemBean propertyEmployeeRoleVo) {
        this.propertyEmployeeRoleVo = propertyEmployeeRoleVo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto1Url() {
        return photo1Url;
    }

    public void setPhoto1Url(String photo1Url) {
        this.photo1Url = photo1Url;
    }

    public String getPhoto2Url() {
        return photo2Url;
    }

    public void setPhoto2Url(String photo2Url) {
        this.photo2Url = photo2Url;
    }

    public String getPhoto3Url() {
        return photo3Url;
    }

    public void setPhoto3Url(String photo3Url) {
        this.photo3Url = photo3Url;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
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
