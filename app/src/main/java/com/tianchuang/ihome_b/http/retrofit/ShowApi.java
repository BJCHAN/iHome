package com.tianchuang.ihome_b.http.retrofit;


import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean;
import com.tianchuang.ihome_b.bean.ComplainDetailBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestProcessedBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestUntratedBean;
import com.tianchuang.ihome_b.bean.DataBuildingSearchBean;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;
import com.tianchuang.ihome_b.bean.FormTypeListBean;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.bean.MenuInnerListBean;
import com.tianchuang.ihome_b.bean.MyFormListBean;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.bean.MyOrderListBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayListBean;
import com.tianchuang.ihome_b.bean.NotificationItemBean;
import com.tianchuang.ihome_b.bean.NotificationListBean;
import com.tianchuang.ihome_b.bean.PropertyListItemBean;
import com.tianchuang.ihome_b.bean.RobHallListBean;
import com.tianchuang.ihome_b.bean.RobHallRepairDetailListBean;
import com.tianchuang.ihome_b.bean.VisitorBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ShowApi {
    /***
     * 发送验证码
     */
    @POST(BizInterface.SEND_AUTH_CODE_URL)
    Observable<HttpModle<String>> sendAuthCode(@Query("mobile") String phone);

    /***
     * 注册账号
     * params
     * mobile:138********  （手机号11位）
     * passwd:****         （密码6-16位）
     * smsCode:****        （4位数字)
     *  name:****           （真实姓名0-4）
     */
    @POST(BizInterface.REGISTER_URL)
    Observable<HttpModle<String>> registerAccount(@Query("mobile") String phone,
                                                  @Query("passwd") String passwd,
                                                  @Query("smsCode") String smsCode,
                                                  @Query("name") String name);

    /***
     * 登录
     * params
     * mobile:138********  （手机号11位）
     * passwd:******       （密码6-16位）
     */
    @POST(BizInterface.LOGIN_URL)
    Observable<HttpModle<LoginBean>> login(@Query("mobile") String phone,
                                           @Query("passwd") String passwd);

    /***
     * 找回密码发送短信验证
     * params
     * mobile:138********  （手机号11位）
     */
    @POST(BizInterface.RETRIEVE_URL)
    Observable<HttpModle<String>> retrievePassword(@Query("mobile") String phone);


    /***
     * 重置密码
     * params
     * mobile:138********  （手机号11位）
     * passwd:******       （密码6-16位）
     * smsCode:****        （4位数字）
     */
    @POST(BizInterface.RESET_PWD_URL)
    Observable<HttpModle<String>> resetPassword(@Query("mobile") String phone,
                                                @Query("passwd") String passwd,
                                                @Query("smsCode") String smsCode);

    /***
     * 修改密码
     * params
     * oldPasswd:****     （原始密码6-16位）
     * passwd:****        （新密码6-16位
     */
    @POST(BizInterface.MODIFY_PWD_URL)
    Observable<HttpModle<String>> modifyPassword(@Query("oldPasswd") String oldPwd,
                                                 @Query("passwd") String pwd);

    /***
     * 物业列表
     * params 无
     */
    @POST(BizInterface.PROPERTY_LIST_URL)
    Observable<HttpModle<ArrayList<PropertyListItemBean>>> getPropertyList();

    /***
     * 设置常用
     * params   roleId:xx
     */
    @POST(BizInterface.PROPERTY_SET_OFTEN_URL)
    Observable<HttpModle<String>> setOften(@Query("roleId") int id);

    /***
     * 常用全部取消
     * params 无
     */
    @POST(BizInterface.PROPERTY_CANCEL_OFTEN_URL)
    Observable<HttpModle<String>> allCancel();

    /***
     * 内部报事列表
     * params
     * propertyCompanyId:xxx                   应物业公司ID-对应物业列表物业公司ID
     */
    @POST(BizInterface.INNER_REPORTS_LIST_URL)
    Observable<HttpModle<MenuInnerListBean>> reportsList(@Query("propertyCompanyId") int propertyCompanyId,
                                                         @Query("maxId") int maxId);

    /***
     * 内部报事提交
     * params
     * propertyCompanyId:xxx                   应物业公司ID-对应物业列表物业公司ID
     * content:xxx                             报告内容（文本内容）
     * 图片文件
     */
    @Multipart
    @POST(BizInterface.INNER_REPORTS_SUBMIT_URL)
    Observable<HttpModle<String>> reportsSubmit(@Query("propertyCompanyId") int propertyCompanyId,
                                                @Query("content") String content,
                                                @Part() List<MultipartBody.Part> parts);

    /***
     * 抢单大厅列表
     * params
     * propertyCompanyId:xxx       应物业公司ID-对应物业列表物业公司ID
     * maxId:xxx
     */
    @POST(BizInterface.ROB_HALL_LIST_URL)
    Observable<HttpModle<RobHallListBean>> robHallList(@Query("propertyCompanyId") int propertyCompanyId,
                                                       @Query("maxId") int maxId);

    /***
     * 报修故障详情
     * params
     * repairsId:xxx               报修ID-对应报修抢单列表ID
     */
    @POST(BizInterface.ROB_HALL_DETAIL_URL)
    Observable<HttpModle<RobHallRepairDetailListBean>> robHallRepairDetail(@Query("repairsId") int repairsId);

    /***
     * 报修故障抢单
     * params
     * repairsId:xxx               报修ID-对应报修抢单列表ID
     */
    @POST(BizInterface.ROB_REPAIR_URL)
    Observable<HttpModle<String>> robRepair(@Query("repairsId") int repairsId);

    /***
     * 楼宇查询
     * params
     * propertyCompanyId:xxx       应物业公司ID-对应物业列表物业公司ID
     */
    @POST(BizInterface.DATA_BUILDING_SEARCH_URL)
    Observable<HttpModle<ArrayList<DataBuildingSearchBean>>> getBuildingSearch(@Query("propertyCompanyId") int propertyCompanyId);

    /***
     * 设备查询
     * params
     * propertyCompanyId:xxx       应物业公司ID-对应物业列表物业公司ID
     */
    @POST(BizInterface.DATA_EQUIPMENT_TYPE_SEARCH_URL)
    Observable<HttpModle<ArrayList<EquipmentTypeSearchBean>>> equipmentTypeSearch(@Query("propertyCompanyId") int propertyCompanyId);


    /**
     * 未处理投诉列表
     *
     * @param propertyCompanyId 应物业公司ID-对应物业列表物业公司ID
     * @param maxId
     * @return
     */
    @POST(BizInterface.COMPLAIN_SUGGEST_UNTRATED_URL)
    Observable<HttpModle<ComplainSuggestUntratedBean>> complainSuggestUntrated(@Query("propertyCompanyId") int propertyCompanyId,
                                                                               @Query("maxId") int maxId);

    /**
     * 已处理投诉列表
     *
     * @param propertyCompanyId 应物业公司ID-对应物业列表物业公司ID
     * @param maxId
     * @return
     */
    @POST(BizInterface.COMPLAIN_SUGGEST_PROCESSED_URL)
    Observable<HttpModle<ComplainSuggestProcessedBean>> complainSuggestProcessed(@Query("propertyCompanyId") int propertyCompanyId,
                                                                                 @Query("maxId") int maxId);

    /**
     * 投诉详细
     * complaintsId:xxx                    投诉ID-对应投诉列表ID
     */
    @POST(BizInterface.COMPLAIN_DETAIL_URL)
    Observable<HttpModle<ComplainDetailBean>> complainDetail(@Query("complaintsId") int complaintsId);

    /**
     * 投诉回复
     * complaintsId:xxx                    投诉ID-对应投诉列表ID
     */
    @POST(BizInterface.COMPLAIN_REPLY_URL)
    Observable<HttpModle<String>> complainReply(@Query("complaintsId") int complaintsId,
                                                @Query("content") String content);

    /**
     * 我的订单未完成
     *
     * @param propertyCompanyId 应物业公司ID-对应物业列表物业公司ID
     * @param maxId
     * @return
     */
    @POST(BizInterface.MY_ORDER_UNFINISHED_URL)
    Observable<HttpModle<MyOrderListBean>> myOrderUnfinished(@Query("propertyCompanyId") int propertyCompanyId,
                                                             @Query("maxId") int maxId);

    /**
     * 我的订单已完成
     *
     * @param propertyCompanyId 应物业公司ID-对应物业列表物业公司ID
     * @param maxId
     * @return
     */
    @POST(BizInterface.MY_ORDER_FINISHED_URL)
    Observable<HttpModle<MyOrderListBean>> myOrderfinished(@Query("propertyCompanyId") int propertyCompanyId,
                                                           @Query("maxId") int maxId);

    /**
     * 我的订单详情
     * params
     * repairsId:xxx               报修ID-对应报修抢单列表ID
     */
    @POST(BizInterface.MY_ORDER_DETAIL_URL)
    Observable<HttpModle<MyOrderDetailBean>> myOrderDetail(@Query("repairsId") int repairsId);

    /**
     * 我的订单详情
     * params
     * repairsId:xxxxxx      报修ID-对应我的订单列表-id
     * beforePhotos:         维修前图片（至多三张）
     * afterPhotos:          维修后图片（至多三张）
     * content:xxxx          维修文字描述（<=200）
     */
    @Multipart
    @POST(BizInterface.REPAIR_CONFIRM_URL)
    Observable<HttpModle<String>> confirmOrder(@Query("repairsId") int repairsId,
                                               @Part() List<MultipartBody.Part> parts,
                                               @Query("content") String content);

    /**
     * 填写维修费用时,添加材料列表
     */
    @POST(BizInterface.MATERIAL_LIST_URL)
    Observable<HttpModle<ArrayList<MaterialListItemBean>>> materialList(@Query("propertyCompanyId") int propertyCompanyId);

    /**
     * 填写维修费用时,添加人工费用列表
     */
    @POST(BizInterface.CHARGE_TYPE_LIST_URL)
    Observable<HttpModle<ArrayList<ChargeTypeListItemBean>>> chargeTypeList(@Query("propertyCompanyId") int propertyCompanyId);

    /**
     * params
     * repairsId:xxx               报修ID-对应报修抢单列表-id
     * payOffline:xxx              是否离线支付:1-选择离线支付;0-未选择离线支付;
     * feeItems:xxx                费用明细-格式:[{'title':'人工费','type':1,'refId':
     * 1,'counts':1,'fee':'20.00'}, {'title':'水管','type':2,'refId': 2,'counts':3,'fee':'30.00'}]";
     */
    @POST(BizInterface.SUBMIT_FEE_URL)
    Observable<HttpModle<String>> submitFeeList(@Query("repairsId") int repairsId,
                                                @Query("payOffline") int payOffline,
                                                @Query("feeItems") String content);

    /**
     * 我的访客
     * params
     * propertyCompanyId:xxxxxx                //物业ID
     * mobile:138xxxxxx                        //手机号(可以为空)
     * maxId
     */
    @POST(BizInterface.VISITOR_LIST_URL)
    Observable<HttpModle<VisitorBean>> visitorList(@Query("propertyCompanyId") int propertyCompanyId,
                                                   @Query("mobile") String repairsId,
                                                   @Query("maxId") int maxId);

    /**
     * 表单类型列表
     * params
     * propertyCompanyId:xxxxxx                //物业ID
     * maxId
     */
    @POST(BizInterface.Form_Type_LIST_URL)
    Observable<HttpModle<FormTypeListBean>> formTypeList(@Query("propertyCompanyId") int propertyCompanyId,
                                                         @Query("maxId") int maxId);

    /**
     * 表单提交
     * params
     * propertyCompanyId:xxxxxx                //物业ID
     * formTypeId:xxx                          表单类型ID（对应在投诉类型列表接口中id）
     * fieldKey1:xxx                           对应字段属性列表中的fields
     * fieldKey2:xxx
     */
    @Multipart
    @POST(BizInterface.FORM_SUBMIT_URL)
    Observable<HttpModle<String>> formSubmit(@QueryMap Map<String, String> map,
                                             @Part() List<MultipartBody.Part> parts);

    /**
     * 我的表单列表
     * params
     * propertyCompanyId:xxxxxx                //物业ID
     * maxId
     */
    @POST(BizInterface.MY_FORM_LIST_URL)
    Observable<HttpModle<MyFormListBean>> myFormList(@Query("propertyCompanyId") int propertyCompanyId,
                                                     @Query("maxId") int maxId);

    /**
     * 我的任务列表(进行中)
     * params
     * propertyCompanyId:xxxxxx                //物业ID
     * maxId
     */
    @POST(BizInterface.MY_TASK_LIST_URL)
    Observable<HttpModle<MyTaskUnderWayListBean>> myTaskUnderWayList(@Query("propertyCompanyId") int propertyCompanyId,
                                                                     @Query("maxId") int maxId);

    /**
     * 通知列表
     * params
     * propertyCompanyId:xxxxxx                //物业ID
     * maxId
     */
    @POST(BizInterface.NOTIFICATION_LIST_URL)
    Observable<HttpModle<NotificationListBean>> notificationList(@Query("propertyCompanyId") int propertyCompanyId,
                                                                 @Query("maxId") int maxId);

    /**
     * 通知详情
     * params
     * noticeId:xx                 （通知ID）
     */
    @POST(BizInterface.NOTIFICATION_DETAIL_URL)
    Observable<HttpModle<NotificationItemBean>> notificationDetail(@Query("noticeId") int noticeId);
}
