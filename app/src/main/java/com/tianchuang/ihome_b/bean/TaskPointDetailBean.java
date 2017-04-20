package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/4/20.
 * description:任务点详情bean
 */

public class TaskPointDetailBean implements Serializable {

    /**
     * id : 180
     * propertyCompanyId : 1
     * propertyEmployeeId : 1
     * taskId : 117
     * taskName : 2017年04月19日好饿啊
     * taskKind : 4
     * taskExplains : uuuuuuuu
     * finishTime : 0
     * type : 2
     * status : 0
     * createdDate : 1492597627
     * enterType : 0
     * equipmentControlVoList : [{"id":8,"name":"控制点8","place":"控制点8地址","finishTime":1492597800,"executeTime":0,"type":0,"day":1,"time":"18:30","hasWarn":true}]
     * kindFormat : 绿化服务
     * alreadyEnterCount : 0
     * EnterCount : 0
     * executeTime : 1492597800
     * departmentName : 后勤部
     * employeeName : 龚世晟
     * publicity : 1
     * formTypeVoList : [{"formTypeName":"消防巡查记录表","formTypeId":25,"id":8,"formTypeVo":{"id":25,"propertyCompanyId":1,"name":"消防巡查记录表","description":"消防日常检查用表","count":0,"used":false,"fields":[{"id":108,"formTypeId":25,"fieldKey":"fieldKey108","name":"消火栓箱检查","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":69,"formTypeFieldId":108,"value":"完好"},{"id":70,"formTypeFieldId":108,"value":"损坏"}]},{"id":109,"formTypeId":25,"fieldKey":"fieldKey109","name":"烟感报警","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":71,"formTypeFieldId":109,"value":"正常"},{"id":72,"formTypeFieldId":109,"value":"不工作"}]},{"id":110,"formTypeId":25,"fieldKey":"fieldKey110","name":"防火门","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":73,"formTypeFieldId":110,"value":"完好"},{"id":74,"formTypeFieldId":110,"value":"不能关闭"}]}]},"done":false},{"formTypeName":"AAAAAA","formTypeId":22,"id":9,"formTypeVo":{"id":22,"propertyCompanyId":1,"name":"AAAAAA","description":"AAAA","count":0,"used":false,"fields":[{"id":99,"formTypeId":22,"fieldKey":"fieldKey99","name":"输入框","sizeLimit":30,"type":1,"mustInput":true},{"id":100,"formTypeId":22,"fieldKey":"fieldKey100","name":"输入框","sizeLimit":20,"type":1,"mustInput":true}]},"done":false}]
     */

    private int id;
    private int propertyCompanyId;
    private int propertyEmployeeId;
    private int taskId;
    private String taskName;
    private int taskKind;
    private String taskExplains;
    private int finishTime;
    private int type;
    private int status;
    private int createdDate;
    private int enterType;
    private String kindFormat;
    private int alreadyEnterCount;
    private int EnterCount;
    private int executeTime;
    private String departmentName;
    private String employeeName;
    private int publicity;
    private List<EquipmentControlVoListBean> equipmentControlVoList;
    private List<FormTypeVoListBean> formTypeVoList;

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

    public int getPropertyEmployeeId() {
        return propertyEmployeeId;
    }

    public void setPropertyEmployeeId(int propertyEmployeeId) {
        this.propertyEmployeeId = propertyEmployeeId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskKind() {
        return taskKind;
    }

    public void setTaskKind(int taskKind) {
        this.taskKind = taskKind;
    }

    public String getTaskExplains() {
        return taskExplains;
    }

    public void setTaskExplains(String taskExplains) {
        this.taskExplains = taskExplains;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getEnterType() {
        return enterType;
    }

    public void setEnterType(int enterType) {
        this.enterType = enterType;
    }

    public String getKindFormat() {
        return kindFormat;
    }

    public void setKindFormat(String kindFormat) {
        this.kindFormat = kindFormat;
    }

    public int getAlreadyEnterCount() {
        return alreadyEnterCount;
    }

    public void setAlreadyEnterCount(int alreadyEnterCount) {
        this.alreadyEnterCount = alreadyEnterCount;
    }

    public int getEnterCount() {
        return EnterCount;
    }

    public void setEnterCount(int EnterCount) {
        this.EnterCount = EnterCount;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(int executeTime) {
        this.executeTime = executeTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getPublicity() {
        return publicity;
    }

    public void setPublicity(int publicity) {
        this.publicity = publicity;
    }

    public List<EquipmentControlVoListBean> getEquipmentControlVoList() {
        return equipmentControlVoList;
    }

    public void setEquipmentControlVoList(List<EquipmentControlVoListBean> equipmentControlVoList) {
        this.equipmentControlVoList = equipmentControlVoList;
    }

    public List<FormTypeVoListBean> getFormTypeVoList() {
        return formTypeVoList;
    }

    public void setFormTypeVoList(List<FormTypeVoListBean> formTypeVoList) {
        this.formTypeVoList = formTypeVoList;
    }

    public static class EquipmentControlVoListBean implements Serializable{
        /**
         * id : 8
         * name : 控制点8
         * place : 控制点8地址
         * finishTime : 1492597800
         * executeTime : 0
         * type : 0
         * day : 1
         * time : 18:30
         * hasWarn : true
         */

        private int id;
        private String name;
        private String place;
        private int finishTime;
        private int executeTime;
        private int type;
        private int day;
        private String time;
        private boolean hasWarn;

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

        public int getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(int finishTime) {
            this.finishTime = finishTime;
        }

        public int getExecuteTime() {
            return executeTime;
        }

        public void setExecuteTime(int executeTime) {
            this.executeTime = executeTime;
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

        public boolean isHasWarn() {
            return hasWarn;
        }

        public void setHasWarn(boolean hasWarn) {
            this.hasWarn = hasWarn;
        }
    }

    public static class FormTypeVoListBean implements Serializable{
        /**
         * formTypeName : 消防巡查记录表
         * formTypeId : 25
         * id : 8
         * formTypeVo : {"id":25,"propertyCompanyId":1,"name":"消防巡查记录表","description":"消防日常检查用表","count":0,"used":false,"fields":[{"id":108,"formTypeId":25,"fieldKey":"fieldKey108","name":"消火栓箱检查","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":69,"formTypeFieldId":108,"value":"完好"},{"id":70,"formTypeFieldId":108,"value":"损坏"}]},{"id":109,"formTypeId":25,"fieldKey":"fieldKey109","name":"烟感报警","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":71,"formTypeFieldId":109,"value":"正常"},{"id":72,"formTypeFieldId":109,"value":"不工作"}]},{"id":110,"formTypeId":25,"fieldKey":"fieldKey110","name":"防火门","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":73,"formTypeFieldId":110,"value":"完好"},{"id":74,"formTypeFieldId":110,"value":"不能关闭"}]}]}
         * done : false
         */

        private String formTypeName;
        private int formTypeId;
        private int id;
        private FormTypeItemBean formTypeVo;
        private boolean done;

        public String getFormTypeName() {
            return formTypeName;
        }

        public void setFormTypeName(String formTypeName) {
            this.formTypeName = formTypeName;
        }

        public int getFormTypeId() {
            return formTypeId;
        }

        public void setFormTypeId(int formTypeId) {
            this.formTypeId = formTypeId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public FormTypeItemBean getFormTypeVo() {
            return formTypeVo;
        }

        public void setFormTypeVo(FormTypeItemBean formTypeVo) {
            this.formTypeVo = formTypeVo;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }


    }
}
