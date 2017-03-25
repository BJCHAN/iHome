package com.tianchuang.ihome_b.bean;

import android.text.TextUtils;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/16.
 * description:
 */

public class TaskAreaListBean extends SelectedBean implements Serializable{
    /**
     * id : 1
     * name : 海创苑
     * used : true
     * cellList : [{"id":1,"buildingId":1,"number":"1幢","used":true,"unitList":[{"id":1,"propertyCompanyId":1,"buildingId":1,"buildingCellId":1,"name":"1单元"},{"id":2,"propertyCompanyId":1,"buildingId":1,"buildingCellId":1,"name":"2单元"}]},{"id":2,"buildingId":1,"number":"2幢","used":true,"unitList":[]}]
     */

    private int id;
    private String name;
    private boolean used;
    private List<CellListBean> cellList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public List<CellListBean> getCellList() {
        return cellList;
    }

    public void setCellList(List<CellListBean> cellList) {
        this.cellList = cellList;
    }

    public static class CellListBean implements Serializable,IPickerViewData {
        /**
         * id : 1
         * buildingId : 1
         * number : 1幢
         * used : true
         * name
         * unitList : [{"id":1,"propertyCompanyId":1,"buildingId":1,"buildingCellId":1,"name":"1单元"},{"id":2,"propertyCompanyId":1,"buildingId":1,"buildingCellId":1,"name":"2单元"}]
         * value //同ID
         */
        private int value;
        private int id;
        private int buildingId;
        private String number;
        private String name;
        private boolean used;
        private List<UnitListBean> unitList;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(int buildingId) {
            this.buildingId = buildingId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public List<UnitListBean> getUnitList() {
            return unitList;
        }

        public void setUnitList(List<UnitListBean> unitList) {
            this.unitList = unitList;
        }

        @Override
        public String getPickerViewText() {
            if (TextUtils.isEmpty(number))
                return name;
            return number;
        }

        public static class UnitListBean implements Serializable,IPickerViewData{
            /**
             * id : 1
             * propertyCompanyId : 1
             * buildingId : 1
             * buildingCellId : 1
             * name : 1单元
             * value 同id
             */

            private int id;
            private int propertyCompanyId;
            private int buildingId;
            private int buildingCellId;
            private int value;
            private String name;
            private List<BuildingRoomListBean> roomList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPropertyCompanyId() {
                return propertyCompanyId;
            }

            public void setPropertyCompanyId(int propertyCompanyId) {
                this.propertyCompanyId = propertyCompanyId;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public List<BuildingRoomListBean> getRoomList() {
                return roomList;
            }

            public void setRoomList(List<BuildingRoomListBean> roomList) {
                this.roomList = roomList;
            }

            public int getBuildingId() {
                return buildingId;
            }

            public void setBuildingId(int buildingId) {
                this.buildingId = buildingId;
            }

            public int getBuildingCellId() {
                return buildingCellId;
            }

            public void setBuildingCellId(int buildingCellId) {
                this.buildingCellId = buildingCellId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String getPickerViewText() {
                return name;
            }
        }
    }
}
