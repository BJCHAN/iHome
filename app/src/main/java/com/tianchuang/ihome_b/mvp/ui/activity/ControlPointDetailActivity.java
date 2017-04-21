package com.tianchuang.ihome_b.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.bean.event.TaskOpenScanEvent;
import com.tianchuang.ihome_b.mvp.ui.fragment.MyTaskControlPointDetailFragment;
import com.tianchuang.ihome_b.permission.MPermission;
import com.tianchuang.ihome_b.permission.OnMPermissionDenied;
import com.tianchuang.ihome_b.permission.OnMPermissionGranted;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 任务详情activity
 * */
public class ControlPointDetailActivity extends TaskSelectActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        TaskPointDetailBean detailBean = (TaskPointDetailBean) getIntent().getSerializableExtra("detailBean");
            return MyTaskControlPointDetailFragment.newInstance(detailBean);

    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
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
    @Subscribe(threadMode = ThreadMode.MAIN)//接收打开扫一扫的事件
    public void onMessageEvent(TaskOpenScanEvent event) {//打开扫一扫的事件
        requestCameraPermission();
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
        Intent intent = new Intent(ControlPointDetailActivity.this, ScanCodeActivity.class);
        startActivityForResult(intent, Constants.QrCode.TASK_OPEN_CODE);//打开扫码界面
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnMPermissionDenied(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        showPermissionInfo(getString(R.string.perssion_camera_tip), false);
    }
}
