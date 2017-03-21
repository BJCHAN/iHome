package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/15.
 * description:
 */

public class MyTaskUnderWayItemBean extends BaseItemLoadBean {

    /**
     * id : 2
     * taskId : 10
     * taskKind: 5 任务种类taskKind=5时显示录入数据相关
     * taskName : 卫生检查
     * taskExplains : 卫生检查
     * finishTime : 0
     * type : 1
     * status : 0
     * createdDate : 1489393471
     * enterType : 1
     */

    private int taskId;
    private String taskName;
    private String taskExplains;
    private int finishTime;
    private int taskKind;
    private int type;
    private int status;
    private int createdDate;
    private int enterType;

    public int getEnterType() {
        return enterType;
    }

    public void setEnterType(int enterType) {
        this.enterType = enterType;
    }

    public int getTaskKind() {
        return taskKind;
    }

    public void setTaskKind(int taskKind) {
        this.taskKind = taskKind;
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
}
