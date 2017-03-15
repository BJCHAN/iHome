package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/15.
 * description:
 */

public class NotificationItemBean extends BaseItemLoadBean {

    /**
     * id:6
     * year : 2017
     * propertyCompanyId : 1
     * title : xx小区的另一个管理通告
     * type : 0
     * content : 管理通告！！！xx小区xxxxxxxxxxxxxxxxx
     * createdDate : 1489394123
     * lastUpdatedDate : 1489394123
     */

    private String year;
    private int propertyCompanyId;
    private String title;
    private int type;
    private String content;
    private int createdDate;
    private int lastUpdatedDate;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPropertyCompanyId() {
        return propertyCompanyId;
    }

    public void setPropertyCompanyId(int propertyCompanyId) {
        this.propertyCompanyId = propertyCompanyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
