package com.tianchuang.ihome_b.bean;

import java.io.Serializable;

/**
 * Created by abyss on 2017/3/23.
 *  //设备点/控制点的二维码返回任务列表
 */

public class QrCodeBean implements Serializable{

    /**
     * taskId : 8
     * taskRecordId : 4
     * taskName : 2017年03月20日巡更
     * controlPointId : 3
     * equipmentId : 0
     */

    private int taskId;
    private int taskRecordId;
    private String taskName;
    private int controlPointId;
    private int equipmentId;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskRecordId() {
        return taskRecordId;
    }

    public void setTaskRecordId(int taskRecordId) {
        this.taskRecordId = taskRecordId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getControlPointId() {
        return controlPointId;
    }

    public void setControlPointId(int controlPointId) {
        this.controlPointId = controlPointId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
}
