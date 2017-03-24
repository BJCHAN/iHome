package com.tianchuang.ihome_b.bean;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/20.
 * description:
 */

public class ControlPointItemBean implements Serializable {
    /**
     * id : 4
     * name : 控制点二
     * place : 测试地区
     * finishTime : 1489982400
     * type : 0
     * day : 1
     * time : 12:00
     * formTypeVo : {"id":2,"propertyCompanyId":1,"name":"提交税费","description":"提交税费提交税费提交税费","fields":[{"id":21,"formTypeId":2,"fieldKey":"fieldKey21","name":"描述","sizeLimit":100,"type":1,"mustInput":true},{"id":22,"formTypeId":2,"fieldKey":"fieldKey22","name":"状态","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":13,"formTypeFieldId":22,"value":"可用"},{"id":14,"formTypeFieldId":22,"value":"不可"},{"id":15,"formTypeFieldId":22,"value":"未知"}]},{"id":23,"formTypeId":2,"fieldKey":"fieldKey23","name":"现场照片","sizeLimit":0,"type":3,"mustInput":true}]}
     * isDone 是否已经完成
     * formDetailVo
     */
    private int id;
    private String name;
    private String place;
    private int finishTime;
    private int type;
    private int day;
    private String time;
    private FormTypeItemBean formTypeVo;
    private MyFormDetailBean formDetailVo;
    private boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public MyFormDetailBean getFormDetailVo() {
        return formDetailVo;
    }

    public void setFormDetailVo(MyFormDetailBean formDetailVo) {
        this.formDetailVo = formDetailVo;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public FormTypeItemBean getFormTypeVo() {
        return formTypeVo;
    }

    public void setFormTypeVo(FormTypeItemBean formTypeVo) {
        this.formTypeVo = formTypeVo;
    }


}
