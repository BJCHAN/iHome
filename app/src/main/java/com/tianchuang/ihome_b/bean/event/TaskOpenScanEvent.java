package com.tianchuang.ihome_b.bean.event;

/**
 * Created by Abyss on 2017/2/16.
 * description:打开任务扫一扫的事件
 */

public class TaskOpenScanEvent {
    private int type =0;//当type ==1时,表示我的任务--任务进行中的扫码,避免与控制点扫码的冲突,导致重复开启扫码

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
