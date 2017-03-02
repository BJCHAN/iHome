package com.tianchuang.ihome_b.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wujian on 2017/3/1.
 * 投诉建议未处理数据模型
 */

public class ComplainSuggestUntratedBean implements Serializable {


    /**
     * pageSize : 2
     * listVo : [{"id":22,"typeName":"进水","content":"我是你","createdDate":1488249527,"ownersInfoVo":{"ownersId":1,"ownersName":"","roomId":1,"num":"101","propertyCompanyId":1,"propertyCompanyName":"海创园","buildingId":1,"buildingName":"海创苑","buildingCellId":1,"buildingCellName":"1幢","buildingUnitId":1,"buildingUnitName":"1单元","oftenUse":true}},{"id":21,"typeName":"进水","content":"一起特大非法营运商一","createdDate":1488192232,"ownersInfoVo":{"ownersId":1,"ownersName":"","roomId":1,"num":"101","propertyCompanyId":1,"propertyCompanyName":"海创园","buildingId":1,"buildingName":"海创苑","buildingCellId":1,"buildingCellName":"1幢","buildingUnitId":1,"buildingUnitName":"1单元","oftenUse":true}}]
     */

    private int pageSize;
    private List<ListVoBean> listVo;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ListVoBean> getListVo() {
        return listVo;
    }

    public void setListVo(List<ListVoBean> listVo) {
        this.listVo = listVo;
    }

    public static class ListVoBean {
        /**
         * id : 22
         * typeName : 进水
         * content : 我是你
         * createdDate : 1488249527
         * ownersInfoVo : {"ownersId":1,"ownersName":"","roomId":1,"num":"101","propertyCompanyId":1,"propertyCompanyName":"海创园","buildingId":1,"buildingName":"海创苑","buildingCellId":1,"buildingCellName":"1幢","buildingUnitId":1,"buildingUnitName":"1单元","oftenUse":true}
         */

        private int id;
        private String typeName;
        private String content;
        private int createdDate;
        private OwnersInfoVoBean ownersInfoVo;

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
}
