package com.tianchuang.ihome_b.bean;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/16.
 * description:
 */

public class TaskRoomDataListBean implements Serializable{
    /**
     * id : 1
     * roomId : 1
     * taskId : 4
     * taskRecordId : 1
     * enterType : 2
     * lastData : 0
     * currentData : 0
     * roomInfo : 海创苑1幢1单元101
     */

    private int id;
    private int roomId;
    private int taskId;
    private int taskRecordId;
    private int enterType;
    private String lastData;
    private String currentData;
    private String roomInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

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

    public int getEnterType() {
        return enterType;
    }

    public void setEnterType(int enterType) {
        this.enterType = enterType;
    }

    public String getLastData() {
        return lastData;
    }

    public void setLastData(String lastData) {
        this.lastData = lastData;
    }

    public String getCurrentData() {
        return currentData;
    }

    public void setCurrentData(String currentData) {
        this.currentData = currentData;
    }

    public String getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(String roomInfo) {
        this.roomInfo = roomInfo;
    }

}
