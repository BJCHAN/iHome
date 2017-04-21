package com.tianchuang.ihome_b.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.QrResultListener;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.bean.event.TaskOpenScanEvent;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.mvp.ui.fragment.MyTaskControlPointDetailFragment;
import com.tianchuang.ihome_b.mvp.ui.fragment.TaskSelectFragment;
import com.tianchuang.ihome_b.permission.MPermission;
import com.tianchuang.ihome_b.permission.OnMPermissionDenied;
import com.tianchuang.ihome_b.permission.OnMPermissionGranted;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制点型任务选择页面
 */
public class TaskSelectActivity extends ToolBarActivity {

    private TransferImage mTransferImage;

    @Override
    protected BaseFragment getFirstFragment() {
        ListBean listBean = (ListBean) getIntent().getSerializableExtra("listBean");
        ArrayList<QrCodeBean> list = listBean.getQrCodeBeanArrayList();
        if (list.size() == 1) {

            return MyTaskControlPointDetailFragment.newInstance(list.get(0).getTaskRecordId());
        }
        return TaskSelectFragment.newInstance(listBean);
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        setToolbarTitle("请选择任务");
        initNormalToolbar(toolbar, true);
    }

    @Override
    protected void handleIntent(Intent intent) {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TransferLayoutEvent event) {//接收浏览图片layout的引用
        this.mTransferImage = event.transferImage;
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
                    Toast.makeText(TaskSelectActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else if (resultCode == RESULT_OK && data != null) {
            // 图片选择结果回调,三张图片
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (getImageByCodeListener != null) {
                getImageByCodeListener.onImages(pathList, requestCode);
            }
        }
    }



//    public interface QrResultListener {
//        void qrMainResult(String code);
//    }

    //基本权限 相机权限请求
    public void requestCameraPermission() {
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
        Intent intent = new Intent(TaskSelectActivity.this, ScanCodeActivity.class);
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

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (mTransferImage != null && mTransferImage.isShown()) {
                mTransferImage.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
