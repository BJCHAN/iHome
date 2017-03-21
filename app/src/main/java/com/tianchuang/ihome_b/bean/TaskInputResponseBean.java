package com.tianchuang.ihome_b.bean;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/16.
 * description:我的任务录入提交返回的结果
 */

public class TaskInputResponseBean implements Serializable {

    /**
     * taskRecordId 任务id
     * taskName : 电费录入
     * dataInfo : {"id":1,"lastData":0,"currentData":0,"roomInfo":"海创苑1幢1单元101"}
     */
    private int taskRecordId;
    private String taskName;
    private DataInfoBean dataInfo;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public DataInfoBean getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(DataInfoBean dataInfo) {
        this.dataInfo = dataInfo;
    }

    public int getTaskRecordId() {
        return taskRecordId;
    }

    public void setTaskRecordId(int taskRecordId) {
        this.taskRecordId = taskRecordId;
    }

    public static class DataInfoBean {
        /**
         * id : 1
         * lastData : 0
         * currentData : 0
         * roomInfo : 海创苑1幢1单元101
         */

        private int id;
        private String lastData;
        private String currentData;
        private String roomInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
}
