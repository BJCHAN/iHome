package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/15.
 * description:
 */

public class MyTaskUnderWayItemBean extends BaseItemLoadBean {

    /**
     * id : 2
     * taskId : 10
     * taskName : 卫生检查
     * taskExplains : 卫生检查
     * finishTime : 0
     * type : 1
     * status : 0
     * createdDate : 1489393471
     */

    private int taskId;
    private String taskName;
    private String taskExplains;
    private int finishTime;
    private int type;
    private int status;
    private int createdDate;


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
