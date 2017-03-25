package com.tianchuang.ihome_b.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Abyss on 2017/3/25.
 * description:
 */

public class CarSelectItem implements IPickerViewData {
    private String name;

    public String getName() {
        return name;
    }

    public CarSelectItem setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
