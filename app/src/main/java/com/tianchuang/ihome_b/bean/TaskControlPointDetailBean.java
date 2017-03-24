package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/20.
 * description:控制点详情的bean
 */

public class TaskControlPointDetailBean implements Serializable {

    /**
     * id : 5
     * taskId : 9
     * taskKind : 4
     * taskName
     * taskExplains
     * finishTime : 0
     * type : 2
     * status : 0
     * createdDate : 1489976686
     * enterType : 0
     * equipmentControlVoList : [{"id":4,"name":"控制点二","place":"测试地区","finishTime":1489982400,"type":0,"day":1,"time":"12:00","formTypeVo":{"id":2,"propertyCompanyId":1,"name":"提交税费","description":"提交税费提交税费提交税费","fields":[{"id":21,"formTypeId":2,"fieldKey":"fieldKey21","name":"描述","sizeLimit":100,"type":1,"mustInput":true},{"id":22,"formTypeId":2,"fieldKey":"fieldKey22","name":"状态","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":13,"formTypeFieldId":22,"value":"可用"},{"id":14,"formTypeFieldId":22,"value":"不可"},{"id":15,"formTypeFieldId":22,"value":"未知"}]},{"id":23,"formTypeId":2,"fieldKey":"fieldKey23","name":"现场照片","sizeLimit":0,"type":3,"mustInput":true}]}}]
     */

    private int id;
    private int taskId;
    private int taskKind;
    private int finishTime;
    private int type;
    private int status;
    private int createdDate;
    private int enterType;
    private String taskName;
    private String taskExplains;
    public String getTaskExplains() {
        return taskExplains;
    }

    public void setTaskExplains(String taskExplains) {
        this.taskExplains = taskExplains;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    private List<ControlPointItemBean> equipmentControlVoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskKind() {
        return taskKind;
    }

    public void setTaskKind(int taskKind) {
        this.taskKind = taskKind;
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

    public List<ControlPointItemBean> getEquipmentControlVoList() {
        return equipmentControlVoList;
    }

    public void setEquipmentControlVoList(List<ControlPointItemBean> equipmentControlVoList) {
        this.equipmentControlVoList = equipmentControlVoList;
    }
}
