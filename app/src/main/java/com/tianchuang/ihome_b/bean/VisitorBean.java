package com.tianchuang.ihome_b.bean;

import java.util.List;

/**
 * Created by Abyss on 2017/3/7.
 * description:
 */

public class VisitorBean extends BaseListLoadBean<VisitorBean.VisitorItemBean> {


    /**
     * pageSize : 5
     * listVo : [{"propertyCompanyId":1,"ownersId":5,"roomId":1,"name":"lisi8","mobile":"18158171066","visitDate":1488883759,"num":5,"plateNumber":"沪C8GX01","year":"2017","ownersRoomVo":{"ownersId":5,"ownersName":"张三丰","ownersMobile":"18803040506","roomId":1,"num":"101","propertyCompanyName":"海创园","buildingName":"海创苑","buildingCellName":"1幢","buildingUnitName":"1单元"},"id":8,"createdDate":1488883858,"lastUpdatedDate":1488883858},{"propertyCompanyId":1,"ownersId":5,"roomId":1,"name":"lisi7","mobile":"18158171066","visitDate":1488883759,"num":5,"plateNumber":"沪C8GX01","year":"2017","ownersRoomVo":{"ownersId":5,"ownersName":"张三丰","ownersMobile":"18803040506","roomId":1,"num":"101","propertyCompanyName":"海创园","buildingName":"海创苑","buildingCellName":"1幢","buildingUnitName":"1单元"},"id":7,"createdDate":1488883857,"lastUpdatedDate":1488883857}]
     */
//
//    private int pageSize;
//    private List<VisitorItemBean> listVo;

//
//    public int getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public List<VisitorItemBean> getListVo() {
//        return listVo;
//    }
//
//    public void setListVo(List<VisitorItemBean> listVo) {
//        this.listVo = listVo;
//    }

    public static class VisitorItemBean extends BaseItemLoadBean{
        /**
         * propertyCompanyId : 1
         * ownersId : 5
         * roomId : 1
         * name : lisi8
         * mobile : 18158171066
         * visitDate : 1488883759
         * num : 5
         * plateNumber : 沪C8GX01
         * year : 2017
         * ownersRoomVo : {"ownersId":5,"ownersName":"张三丰","ownersMobile":"18803040506","roomId":1,"num":"101","propertyCompanyName":"海创园","buildingName":"海创苑","buildingCellName":"1幢","buildingUnitName":"1单元"}
         * id : 8
         * createdDate : 1488883858
         * lastUpdatedDate : 1488883858
         */

        private int propertyCompanyId;
        private int ownersId;
        private int roomId;
        private String name;
        private String mobile;
        private int visitDate;
        private int num;
        private String plateNumber;
        private String year;
        private OwnersRoomVoBean ownersRoomVo;
//        private int id;
        private int createdDate;
        private int lastUpdatedDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VisitorItemBean model = (VisitorItemBean) o;

            if (id != model.id) return false;
            return mobile != null ? mobile.equals(model.mobile) : model.mobile == null;

        }

        @Override
        public int hashCode() {
            int result = (int) (id ^ (id >>> 32));
            result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
            return result;
        }

        public int getPropertyCompanyId() {
            return propertyCompanyId;
        }

        public void setPropertyCompanyId(int propertyCompanyId) {
            this.propertyCompanyId = propertyCompanyId;
        }

        public int getOwnersId() {
            return ownersId;
        }

        public void setOwnersId(int ownersId) {
            this.ownersId = ownersId;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getVisitDate() {
            return visitDate;
        }

        public void setVisitDate(int visitDate) {
            this.visitDate = visitDate;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public OwnersRoomVoBean getOwnersRoomVo() {
            return ownersRoomVo;
        }

        public void setOwnersRoomVo(OwnersRoomVoBean ownersRoomVo) {
            this.ownersRoomVo = ownersRoomVo;
        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }

        public int getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(int createdDate) {
            this.createdDate = createdDate;
        }

        public int getLastUpdatedDate() {
            return lastUpdatedDate;
        }

        public void setLastUpdatedDate(int lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
        }

        public static class OwnersRoomVoBean {
            /**
             * ownersId : 5
             * ownersName : 张三丰
             * ownersMobile : 18803040506
             * roomId : 1
             * num : 101
             * propertyCompanyName : 海创园
             * buildingName : 海创苑
             * buildingCellName : 1幢
             * buildingUnitName : 1单元
             */

            private int ownersId;
            private String ownersName;
            private String ownersMobile;
            private int roomId;
            private String num;
            private String propertyCompanyName;
            private String buildingName;
            private String buildingCellName;
            private String buildingUnitName;

            public int getOwnersId() {
                return ownersId;
            }

            public void setOwnersId(int ownersId) {
                this.ownersId = ownersId;
            }

            public String getOwnersName() {
                return ownersName;
            }

            public void setOwnersName(String ownersName) {
                this.ownersName = ownersName;
            }

            public String getOwnersMobile() {
                return ownersMobile;
            }

            public void setOwnersMobile(String ownersMobile) {
                this.ownersMobile = ownersMobile;
            }

            public int getRoomId() {
                return roomId;
            }

            public void setRoomId(int roomId) {
                this.roomId = roomId;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPropertyCompanyName() {
                return propertyCompanyName;
            }

            public void setPropertyCompanyName(String propertyCompanyName) {
                this.propertyCompanyName = propertyCompanyName;
            }

            public String getBuildingName() {
                return buildingName;
            }

            public void setBuildingName(String buildingName) {
                this.buildingName = buildingName;
            }

            public String getBuildingCellName() {
                return buildingCellName;
            }

            public void setBuildingCellName(String buildingCellName) {
                this.buildingCellName = buildingCellName;
            }

            public String getBuildingUnitName() {
                return buildingUnitName;
            }

            public void setBuildingUnitName(String buildingUnitName) {
                this.buildingUnitName = buildingUnitName;
            }
        }
    }
}
