package com.tianchuang.ihome_b.http.retrofit;


import com.tianchuang.ihome_b.Constants;

/**
 * Created by Abyss on 2016/12/29.
 * description:网络请求url
 */

public interface BizInterface {
    /**
     * API接口
     */
    String API = Constants.Http.getUrl();
    //登录接口
    String LOGIN_URL = "login.html";
    //发送验证码
    String SEND_AUTH_CODE_URL = "sendSms.html";
    //注册
    String REGISTER_URL = "register.html";
    //找回密码发送短信验证
    String RETRIEVE_URL = "password/reset/sendSms.html";
    //注册
    String RESET_PWD_URL = "password/reset.html";
    //修改密码
    String MODIFY_PWD_URL = "password/modify.html";
    //物业列表
    String PROPERTY_LIST_URL = "property/role/join/list.html";
    //设为常用
    String PROPERTY_SET_OFTEN_URL = "property/role/often/set.html";
    //全部取消
    String PROPERTY_CANCEL_OFTEN_URL = "property/role/often/cancel.html";
    //内部报事列表(菜单)
    String INNER_REPORTS_LIST_URL = "internalreport/list.html";
    //内部报事提交(主页)
    String INNER_REPORTS_SUBMIT_URL = "internalreport/add.html";
    //抢单大厅
    String ROB_HALL_LIST_URL = "repairs/hall.html";
    //报修故障详情
    String ROB_HALL_DETAIL_URL = "repairs/detail.html";
    //报修抢单
    String ROB_REPAIR_URL = "repairs/accept.html";
    //楼宇查询
    String DATA_BUILDING_SEARCH_URL = "dataSearch/building.html";
    //设备查询
    String DATA_EQUIPMENT_TYPE_SEARCH_URL = "dataSearch/equipmentType.html";
    //投诉建议，未处理投诉列表
    String COMPLAIN_SUGGEST_UNTRATED_URL = "complaints/list/new.html";
    //投诉建议，已处理投诉列表
    String COMPLAIN_SUGGEST_PROCESSED_URL = "complaints/list/done.html";
    //投诉详情
    String COMPLAIN_DETAIL_URL = "complaints/detail.html";
    //回复投诉
    String COMPLAIN_REPLY_URL = "complaints/reply.html";
    //我的订单（未完成）
    String MY_ORDER_UNFINISHED_URL = "repairs/order/list/new.html";
    //我的订单（已经完成）
    String MY_ORDER_FINISHED_URL = "repairs/order/list/done.html";
    //我的订单详情
    String MY_ORDER_DETAIL_URL = "repairs/order/detail.html";
    //维修确认
    String REPAIR_CONFIRM_URL = "repairs/confirm.html";
    //材料列表
    String MATERIAL_LIST_URL = "repairs/material/list.html";
    //维修费用
    String CHARGE_TYPE_LIST_URL = "repairs/chargetype/list.html";
    //提交费用明细
    String SUBMIT_FEE_URL = "repairs/confirm/fee.html";
    //访客列表
    String VISITOR_LIST_URL = "visitor/list.html";
    //表单类型列表
    String Form_Type_LIST_URL = "form/type/list.html";
    //表单提交
    String FORM_SUBMIT_URL = "form/add.html";
    //我的表单
    String MY_FORM_LIST_URL = "form/list/my.html";
    //我的表单详情
    String MY_FORM_DETAIL_URL = "form/detail.html";
    //我的任务
    String MY_TASK_LIST_URL = "task/list/new.html";
    //管理通知列表
    String NOTIFICATION_LIST_URL = "notification/list.html";
    //公告详情
    String NOTIFICATION_DETAIL_URL = "notification/detail.html";
    //我的任务录入型详情
    String TASK_INPUT_DETAIL_URL = "task/detail.html";
    //我的任务住户录入提交
    String TASK_INPUT_BUILDING_SUBMIT_URL = "task/room/data/info.html";
    //读数提交
    String TASK_INPUT_CURRENT_DATA_SUBMIT_URL = "task/room/data/enter.html";
    //读数提交
    String TASK_INPUT_SUCCESS_FINISH_URL = "task/room/data/enter/done.html";
    //执行任务-设备点控制点型任务
    String TASK_CONTROL_POINT_URL = "task/detail.html";
    //首页列表
    String HOME_PAGE_LIST_URL = "index.html";
    //我的任务-执行任务-设备点控制点型任务-提交表单
    String TASK_CONTROL_POINT_SUBMIT_URL = "task/form/data/enter.html";
    //我的任务-执行任务-设备点控制点型任务-提交表单
    String QR_CODE_URL = "task/scan/qrcode.html";

}
