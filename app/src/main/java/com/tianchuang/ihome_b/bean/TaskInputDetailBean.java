package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/16.
 * description:我的任务录入详情
 */

public class TaskInputDetailBean implements Serializable{


    /**
     * taskRecordId
     * taskName
     * id : 1
     * taskId : 4
     * finishTime : 1489579200
     * type : 2
     * status : 0
     * createdDate : 1489577986
     * enterType : 2
     * enterTypeMsg : 电表
     * buildingDetail : 海创苑(1幢,2幢,3幢,4幢)
     * taskRoomDataList : [{"id":1,"roomId":1,"taskId":4,"taskRecordId":1,"enterType":2,"lastData":0,"currentData":0,"roomInfo":"海创苑1幢1单元101"},{"id":2,"roomId":2,"taskId":4,"taskRecordId":1,"enterType":2,"lastData":0,"currentData":0,"roomInfo":"海创苑1幢1单元102"},{"id":3,"roomId":6,"taskId":4,"taskRecordId":1,"enterType":2,"lastData":0,"currentData":0,"roomInfo":"海创苑1幢1单元105"}]
     */

    private int id;
    private int taskId;
    private int finishTime;
    private String taskName;
    private int type;
    private int status;
    private int createdDate;
    private int enterType;
    private String enterTypeMsg;
    private String buildingDetail;
    private List<TaskRoomDataListBean> taskRoomDataList;
    private List<TaskBuildingListBean> buildingList;
    private int taskRecordId;

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

    public List<TaskBuildingListBean> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<TaskBuildingListBean> buildingList) {
        this.buildingList = buildingList;
    }

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

    public String getEnterTypeMsg() {
        return enterTypeMsg;
    }

    public void setEnterTypeMsg(String enterTypeMsg) {
        this.enterTypeMsg = enterTypeMsg;
    }

    public String getBuildingDetail() {
        return buildingDetail;
    }

    public void setBuildingDetail(String buildingDetail) {
        this.buildingDetail = buildingDetail;
    }

    public List<TaskRoomDataListBean> getTaskRoomDataList() {
        return taskRoomDataList;
    }

    public void setTaskRoomDataList(List<TaskRoomDataListBean> taskRoomDataList) {
        this.taskRoomDataList = taskRoomDataList;
    }
}
