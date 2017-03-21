package com.tianchuang.ihome_b.bean;

/**
 * Created by Abyss on 2017/3/21.
 * description:
 */

public class ComplainItemBean {
        /**
         * id : 18
         * typeName : 进水
         * content : 一起来看流星雨呢
         * createdDate : 1488191882
         * ownersInfoVo : {"ownersId":1,"ownersName":"","roomId":1,"num":"101","propertyCompanyId":1,"propertyCompanyName":"海创园","buildingId":1,"buildingName":"海创苑","buildingCellId":1,"buildingCellName":"1幢","buildingUnitId":1,"buildingUnitName":"1单元","oftenUse":true}
         * replayEmployeeId : 2
         * replayContent : 尊敬的业主，您提交的问题已经修复
         */

        private int id;
        private String typeName;
        private String content;
        private int createdDate;
        private OwnersInfoVoBean ownersInfoVo;
        private int replayEmployeeId;
        private String replayContent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(int createdDate) {
            this.createdDate = createdDate;
        }

        public OwnersInfoVoBean getOwnersInfoVo() {
            return ownersInfoVo;
        }

        public void setOwnersInfoVo(OwnersInfoVoBean ownersInfoVo) {
            this.ownersInfoVo = ownersInfoVo;
        }

        public int getReplayEmployeeId() {
            return replayEmployeeId;
        }

        public void setReplayEmployeeId(int replayEmployeeId) {
            this.replayEmployeeId = replayEmployeeId;
        }

        public String getReplayContent() {
            return replayContent;
        }

        public void setReplayContent(String replayContent) {
            this.replayContent = replayContent;
        }

        public static class OwnersInfoVoBean {
            /**
             * ownersId : 1
             * ownersName :
             * roomId : 1
             * num : 101
             * propertyCompanyId : 1
             * propertyCompanyName : 海创园
             * buildingId : 1
             * buildingName : 海创苑
             * buildingCellId : 1
             * buildingCellName : 1幢
             * buildingUnitId : 1
             * buildingUnitName : 1单元
             * oftenUse : true
             */

            private int ownersId;
            private String ownersName;
            private int roomId;
            private String num;
            private int propertyCompanyId;
            private String propertyCompanyName;
            private int buildingId;
            private String buildingName;
            private int buildingCellId;
            private String buildingCellName;
            private int buildingUnitId;
            private String buildingUnitName;
            private boolean oftenUse;

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

            public int getPropertyCompanyId() {
                return propertyCompanyId;
            }

            public void setPropertyCompanyId(int propertyCompanyId) {
                this.propertyCompanyId = propertyCompanyId;
            }

            public String getPropertyCompanyName() {
                return propertyCompanyName;
            }

            public void setPropertyCompanyName(String propertyCompanyName) {
                this.propertyCompanyName = propertyCompanyName;
            }

            public int getBuildingId() {
                return buildingId;
            }

            public void setBuildingId(int buildingId) {
                this.buildingId = buildingId;
            }

            public String getBuildingName() {
                return buildingName;
            }

            public void setBuildingName(String buildingName) {
                this.buildingName = buildingName;
            }

            public int getBuildingCellId() {
                return buildingCellId;
            }

            public void setBuildingCellId(int buildingCellId) {
                this.buildingCellId = buildingCellId;
            }

            public String getBuildingCellName() {
                return buildingCellName;
            }

            public void setBuildingCellName(String buildingCellName) {
                this.buildingCellName = buildingCellName;
            }

            public int getBuildingUnitId() {
                return buildingUnitId;
            }

            public void setBuildingUnitId(int buildingUnitId) {
                this.buildingUnitId = buildingUnitId;
            }

            public String getBuildingUnitName() {
                return buildingUnitName;
            }

            public void setBuildingUnitName(String buildingUnitName) {
                this.buildingUnitName = buildingUnitName;
            }

            public boolean isOftenUse() {
                return oftenUse;
            }

            public void setOftenUse(boolean oftenUse) {
                this.oftenUse = oftenUse;
            }
        }

}
