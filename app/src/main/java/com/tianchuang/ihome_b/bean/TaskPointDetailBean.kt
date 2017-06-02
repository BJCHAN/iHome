package com.tianchuang.ihome_b.bean

import android.text.TextUtils

import java.io.Serializable

/**
 * Created by Abyss on 2017/4/20.
 * description:任务点详情bean
 */

class TaskPointDetailBean : Serializable {

    /**
     * id : 180
     * propertyCompanyId : 1
     * propertyEmployeeId : 1
     * taskId : 117
     * taskName : 2017年04月19日好饿啊
     * taskKind : 4
     * taskExplains : uuuuuuuu
     * finishTime : 0
     * type : 2
     * status : 0
     * createdDate : 1492597627
     * enterType : 0
     * equipmentControlVoList : [{"id":8,"name":"控制点8","place":"控制点8地址","finishTime":1492597800,"executeTime":0,"type":0,"day":1,"time":"18:30","hasWarn":true}]
     * kindFormat : 绿化服务
     * alreadyEnterCount : 0
     * EnterCount : 0
     * executeTime : 1492597800
     * departmentName : 后勤部
     * employeeName : 龚世晟
     * publicity : 1
     * scanEquipmentName
     * scanControlPointName//当前扫描控制点的名称
     * formTypeVoList : [{"formTypeName":"消防巡查记录表","formTypeId":25,"id":8,"formTypeVo":{"id":25,"propertyCompanyId":1,"name":"消防巡查记录表","description":"消防日常检查用表","count":0,"used":false,"fields":[{"id":108,"formTypeId":25,"fieldKey":"fieldKey108","name":"消火栓箱检查","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":69,"formTypeFieldId":108,"value":"完好"},{"id":70,"formTypeFieldId":108,"value":"损坏"}]},{"id":109,"formTypeId":25,"fieldKey":"fieldKey109","name":"烟感报警","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":71,"formTypeFieldId":109,"value":"正常"},{"id":72,"formTypeFieldId":109,"value":"不工作"}]},{"id":110,"formTypeId":25,"fieldKey":"fieldKey110","name":"防火门","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":73,"formTypeFieldId":110,"value":"完好"},{"id":74,"formTypeFieldId":110,"value":"不能关闭"}]}]},"done":false},{"formTypeName":"AAAAAA","formTypeId":22,"id":9,"formTypeVo":{"id":22,"propertyCompanyId":1,"name":"AAAAAA","description":"AAAA","count":0,"used":false,"fields":[{"id":99,"formTypeId":22,"fieldKey":"fieldKey99","name":"输入框","sizeLimit":30,"type":1,"mustInput":true},{"id":100,"formTypeId":22,"fieldKey":"fieldKey100","name":"输入框","sizeLimit":20,"type":1,"mustInput":true}]},"done":false}]
     */

    var id: Int = 0
    var propertyCompanyId: Int = 0
    var propertyEmployeeId: Int = 0
    var taskId: Int = 0
    var taskName: String? = null
    var taskKind: Int = 0
    var taskExplains: String? = null
    var finishTime: Int = 0
    var type: Int = 0
    var status: Int = 0
    var createdDate: Int = 0
    var enterType: Int = 0
    var kindFormat: String? = null
    var alreadyEnterCount: Int = 0
    var enterCount: Int = 0
    var executeTime: Int = 0
    private val scanControlPointName: String? = null
    private val scanEquipmentName: String? = null
    var departmentName: String? = null
    var employeeName: String? = null
    var publicity: Int = 0
    var equipmentControlVoList: List<EquipmentControlVoListBean>? = null
    var formTypeVoList: List<FormTypeVoListBean>? = null

    fun getScanControlPointName(): String {
        if (TextUtils.isEmpty(scanEquipmentName)) {
            return scanControlPointName!! + "控制点"
        }
        return scanEquipmentName!! + ""
    }

    class EquipmentControlVoListBean : Serializable {
        /**
         * id : 8
         * name : 控制点8
         * place : 控制点8地址
         * finishTime : 1492597800
         * executeTime : 0
         * type : 0
         * day : 1
         * time : 18:30
         * hasWarn : true
         */

        var id: Int = 0
        var name: String? = null
        var place: String? = null
        var finishTime: Int = 0
        var executeTime: Int = 0
        var type: Int = 0
        var day: Int = 0
        var time: String? = null
        var isHasWarn: Boolean = false
    }

    class FormTypeVoListBean : Serializable {
        /**
         * formTypeName : 消防巡查记录表
         * formTypeId : 25
         * id : 8
         * formTypeVo : {"id":25,"propertyCompanyId":1,"name":"消防巡查记录表","description":"消防日常检查用表","count":0,"used":false,"fields":[{"id":108,"formTypeId":25,"fieldKey":"fieldKey108","name":"消火栓箱检查","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":69,"formTypeFieldId":108,"value":"完好"},{"id":70,"formTypeFieldId":108,"value":"损坏"}]},{"id":109,"formTypeId":25,"fieldKey":"fieldKey109","name":"烟感报警","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":71,"formTypeFieldId":109,"value":"正常"},{"id":72,"formTypeFieldId":109,"value":"不工作"}]},{"id":110,"formTypeId":25,"fieldKey":"fieldKey110","name":"防火门","sizeLimit":0,"type":2,"mustInput":true,"fieldExtras":[{"id":73,"formTypeFieldId":110,"value":"完好"},{"id":74,"formTypeFieldId":110,"value":"不能关闭"}]}]}
         * done : false
         */

        var formTypeName: String? = null
        var formTypeId: Int = 0
        var id: Int = 0
        var formTypeVo: FormTypeItemBean? = null
        var isDone: Boolean = false
        var formDetailVo: MyFormDetailBean? = null


    }
}
