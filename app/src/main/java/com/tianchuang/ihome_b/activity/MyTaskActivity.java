package com.tianchuang.ihome_b.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.CheakBean;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.bean.event.OpenScanEvent;
import com.tianchuang.ihome_b.bean.event.TaskOpenScanEvent;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.fragment.MyTaskControlPointDetailFragment;
import com.tianchuang.ihome_b.fragment.MyTaskFragment;
import com.tianchuang.ihome_b.fragment.MyTaskInputDetailFragment;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.permission.MPermission;
import com.tianchuang.ihome_b.permission.OnMPermissionDenied;
import com.tianchuang.ihome_b.permission.OnMPermissionGranted;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Abyss on 2017/3/9.
 * description:我的任务模块
 */
public class MyTaskActivity extends ToolBarActivity {
    public static final int TASK_OPEN_CODE = 55;//任务请求扫码

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 关闭所有fragment
     */
    public void closeAllFragment() {
        while (getSupportFragmentManager().getBackStackEntryCount() != 1) {
            removeFragment();
        }
    }

    @Override
    protected BaseFragment getFirstFragment() {
        if (getIntent() != null) {//从主页过来
            Serializable item = getIntent().getSerializableExtra("item");
            if (item != null && item instanceof HomePageMultiItem) {
                MyTaskUnderWayItemBean itemBean = ((HomePageMultiItem) item).getMyTaskUnderWayItemBean();
                int type = itemBean.getTaskKind();
                if (type == 5) {//查看录入任务详情
                    return MyTaskInputDetailFragment.newInstance(itemBean);
                }
            }
        }
        return MyTaskFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        initNormalToolbar(toolbar, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//接收打开扫一扫的事件
    public void onMessageEvent(TaskOpenScanEvent event) {//打开扫一扫的事件
        requestCameraPermission();
    }

    /**
     * 接收选择图片的结果和请求扫码的结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.QrCode.TASK_OPEN_CODE) {//任务扫码
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (qrResultListener != null) {
                        qrResultListener.qrResult(result);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MyTaskActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            // 图片选择结果回调,三张图片
            if (resultCode == RESULT_OK && data != null) {
                List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                if (getImageByCodeListener != null) {
                    getImageByCodeListener.onImages(pathList, requestCode);
                }
            }

        }
    }

    /**
     * 二维码的结果回调
     */
    public QrResultListener qrResultListener;

    public void setQrResultListener(QrResultListener qrResultListener) {
        this.qrResultListener = qrResultListener;
    }

    public interface QrResultListener {
        void qrResult(String code);
    }

    //基本权限 相机权限请求
    private void requestCameraPermission() {
        MPermission.with(this)
                .addRequestCode(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }


    /**
     * MPermission接管权限处理逻辑
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    @OnMPermissionGranted(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
    public void onBasicPermissionSucces() {
        Intent intent = new Intent(MyTaskActivity.this, ScanCodeActivity.class);
        startActivityForResult(intent, Constants.QrCode.TASK_OPEN_CODE);//打开扫码界面
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnMPermissionDenied(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        showPermissionInfo(getString(R.string.perssion_camera_tip), false);
    }


    /**
     * 获取图片的回调接口
     */
    public interface GetImageByCodeListener {
        void onImages(List<String> paths, int type);
    }

    public void setGetImageByCodeListener(GetImageByCodeListener getImageByCodeListener) {
        this.getImageByCodeListener = getImageByCodeListener;
    }

    private GetImageByCodeListener getImageByCodeListener;

    public void startImageSelector(ImgSelConfig config, int type) {
        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, type);
    }

}
