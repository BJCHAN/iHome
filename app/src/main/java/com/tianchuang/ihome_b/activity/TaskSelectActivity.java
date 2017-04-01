package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.fragment.TaskSelectFragment;
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

    /**
     * 接收选择图片的结果和请求扫码的结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 图片选择结果回调,三张图片
        if (resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (getImageByCodeListener != null) {
                getImageByCodeListener.onImages(pathList, requestCode);
            }
        }


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
