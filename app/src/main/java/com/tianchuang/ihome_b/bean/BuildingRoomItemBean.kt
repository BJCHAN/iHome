package com.tianchuang.ihome_b.bean

import com.bigkoo.pickerview.model.IPickerViewData

import java.io.Serializable

/**
 * Created by Abyss on 2017/3/25.
 * description:
 */

class BuildingRoomItemBean : Serializable, IPickerViewData {


    /**
     * propertyCompanyId : 1
     * buildingId : 1
     * buildingCellId : 1
     * buildingUnitId : 1
     * num : 101
     * area : 89
     * id : 1
     * createdDate : 1487067514
     * lastUpdatedDate : 1487067514
     */

    var propertyCompanyId: Int = 0
    var buildingId: Int = 0
    var buildingCellId: Int = 0
    var buildingUnitId: Int = 0
    var num: String? = null
    var area: Int = 0
    var id: Int = 0
    var createdDate: Int = 0
    var lastUpdatedDate: Int = 0

    override fun getPickerViewText(): String? {
        return num
    }
}
