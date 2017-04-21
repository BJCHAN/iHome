package com.tianchuang.ihome_b.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/10.
 * description:表单类型的itemBean
 */

public class FormTypeItemBean extends BaseItemLoadBean  {
    /**
     * id : 6
     * propertyCompanyId : 1
     * name : 现场表单
     * description : 现场表单现场表单现场表单
     * count : 0
     * used : false
     * fields : [{"id":30,"formTypeId":6,"fieldKey":"fieldKey30","name":"状态","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":19,"formTypeFieldId":30,"value":"A选项"},{"id":20,"formTypeFieldId":30,"value":"B选项"},{"id":21,"formTypeFieldId":30,"value":"C选项"}]},{"id":31,"formTypeId":6,"fieldKey":"fieldKey31","name":"图片","sizeLimit":0,"type":3,"mustInput":true},{"id":32,"formTypeId":6,"fieldKey":"fieldKey32","name":"描述","sizeLimit":100,"type":1,"mustInput":true}]
     *     private String place;
     private String time;
     */

    private int propertyCompanyId;
    private String name;
    private String description;
    private int count;
    private boolean used;
    private List<FieldsBean> fields;
    private String place;
    private String time;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPropertyCompanyId() {
        return propertyCompanyId;
    }

    public void setPropertyCompanyId(int propertyCompanyId) {
        this.propertyCompanyId = propertyCompanyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public List<FieldsBean> getFields() {
        return fields;
    }

    public void setFields(List<FieldsBean> fields) {
        this.fields = fields;
    }




    public static class FieldsBean implements MultiItemEntity, Serializable {
        /**
         * id : 30
         * formTypeId : 6
         * fieldKey : fieldKey30
         * name : 状态
         * sizeLimit : 0
         * type : 2
         * mustInput : true
         * fieldExtras : [{"id":19,"formTypeFieldId":30,"value":"A选项"},{"id":20,"formTypeFieldId":30,"value":"B选项"},{"id":21,"formTypeFieldId":30,"value":"C选项"}]
         */
        @Override
        public int getItemType() {
            return type;
        }

        public String getRadioText() {//获取需要单选的文本
            if (type == 2) {//单选
                for (FieldExtrasBean fieldExtrasBean : getFieldExtras()) {
                    if (fieldExtrasBean.isSelected()) {
                        return fieldExtrasBean.getValue();
                    }
                }
            }
            return "";
        }


        private int id;
        private int formTypeId;
        private String fieldKey;
        private String name;
        private int sizeLimit;
        private int type;
        private boolean mustInput;
        private List<FieldExtrasBean> fieldExtras;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFormTypeId() {
            return formTypeId;
        }

        public void setFormTypeId(int formTypeId) {
            this.formTypeId = formTypeId;
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSizeLimit() {
            return sizeLimit;
        }

        public void setSizeLimit(int sizeLimit) {
            this.sizeLimit = sizeLimit;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isMustInput() {
            return mustInput;
        }

        public void setMustInput(boolean mustInput) {
            this.mustInput = mustInput;
        }

        public List<FieldExtrasBean> getFieldExtras() {
            return fieldExtras;
        }

        public void setFieldExtras(List<FieldExtrasBean> fieldExtras) {
            this.fieldExtras = fieldExtras;
        }

        public static class FieldExtrasBean extends SelectedBean implements Serializable {
            /**
             * id : 19
             * formTypeFieldId : 30
             * value : A选项
             */

            private int id;
            private int formTypeFieldId;
            private String value;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getFormTypeFieldId() {
                return formTypeFieldId;
            }

            public void setFormTypeFieldId(int formTypeFieldId) {
                this.formTypeFieldId = formTypeFieldId;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
