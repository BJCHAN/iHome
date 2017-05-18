package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/3.
 * description:进行中与未完成工单的bean
 */

public class MyOrderCommonBean extends BaseItemLoadBean{

    /**
     * id : 5
     * typeName : 进水
     * status : 1
     * statusMsg : 待服务
     * content : lolo
     * imgCount : 0
     * createdDate : 1488253206
     */


    private String typeName;
    private int status;
    private String statusMsg;
    private String content;
    private int imgCount;
    private int createdDate;
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImgCount() {
        return imgCount;
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }

    public int getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

}
