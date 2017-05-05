package com.tianchuang.ihome_b.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.mvp.ui.fragment.MyTaskFragment;
import com.tianchuang.ihome_b.mvp.ui.fragment.MyTaskInputDetailFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

/**
 * Created by Abyss on 2017/3/9.
 * description:我的任务模块
 */
public class MyTaskActivity extends ToolBarActivity {
    private TransferImage mTransferImage;
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
                if (type == 13) {//查看录入任务详情
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
//
//    @Subscribe(threadMode = ThreadMode.MAIN)//接收打开扫一扫的事件
//    public void onMessageEvent(TaskOpenScanEvent event) {//打开扫一扫的事件
//        if (event.getType()==1) {
//            requestCameraPermission();
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TransferLayoutEvent event) {//接收浏览图片layout的引用
        this.mTransferImage = event.transferImage;
    }
//    /**
//     * 接收选择图片的结果和请求扫码的结果
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == Constants.QrCode.TASK_OPEN_CODE) {//任务扫码
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    if (qrMainResultListener != null) {
//                        qrMainResultListener.qrMainResult(result);
//                    }
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(MyTaskActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }

//    /**
//     * 二维码的结果回调
//     */
//    public QrMainResultListener qrMainResultListener;
//
//    public void setMainQrResultListener(QrMainResultListener qrResultListener) {
//        this.qrMainResultListener = qrResultListener;
//    }
//
//    public interface QrMainResultListener {
//        void qrMainResult(String code);
//    }
//
//    //基本权限 相机权限请求
//    private void requestCameraPermission() {
//        MPermission.with(this)
//                .addRequestCode(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
//                .permissions(Manifest.permission.CAMERA)
//                .request();
//    }
//
//
//    /**
//     * MPermission接管权限处理逻辑
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//    }
//
//
//    @OnMPermissionGranted(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
//    public void onBasicPermissionSucces() {
//        Intent intent = new Intent(MyTaskActivity.this, ScanCodeActivity.class);
//        startActivityForResult(intent, Constants.QrCode.TASK_OPEN_CODE);//打开扫码界面
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @OnMPermissionDenied(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
//    public void onBasicPermissionFailed() {
//        showPermissionInfo(getString(R.string.perssion_camera_tip), false);
//    }

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
