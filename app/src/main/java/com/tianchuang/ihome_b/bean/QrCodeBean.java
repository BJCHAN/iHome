package com.tianchuang.ihome_b.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by abyss on 2017/3/23.
 *  //设备点/控制点的二维码返回任务列表
 */

public class QrCodeBean implements  Serializable,Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taskId);
        dest.writeInt(this.taskRecordId);
        dest.writeString(this.taskName);
        dest.writeInt(this.controlPointId);
        dest.writeInt(this.equipmentId);
    }

    public QrCodeBean() {
    }

    protected QrCodeBean(Parcel in) {
        this.taskId = in.readInt();
        this.taskRecordId = in.readInt();
        this.taskName = in.readString();
        this.controlPointId = in.readInt();
        this.equipmentId = in.readInt();
    }

    public static final Parcelable.Creator<QrCodeBean> CREATOR = new Parcelable.Creator<QrCodeBean>() {
        @Override
        public QrCodeBean createFromParcel(Parcel source) {
            return new QrCodeBean(source);
        }

        @Override
        public QrCodeBean[] newArray(int size) {
            return new QrCodeBean[size];
        }
    };
}
