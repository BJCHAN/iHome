package com.tianchuang.ihome_b.bean.event;

/**
 * Created by Abyss on 2017/3/8.
 * description:切换角色成功
 */

public class SwitchSuccessEvent {
    public int PropertyListSize = 0;

    public SwitchSuccessEvent(int propertyListSize) {
        PropertyListSize = propertyListSize;
    }
}
