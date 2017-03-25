package com.tianchuang.ihome_b.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/25.
 * description:
 */

public class BuildingRoomListBean implements Serializable,IPickerViewData {

    /**
     * name : 101
     * value : 1
     */

    private String name;
    private int value;

    @Override
    public String getPickerViewText() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
