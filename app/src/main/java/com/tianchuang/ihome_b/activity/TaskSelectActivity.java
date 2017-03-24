package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.fragment.TaskSelectFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制点型任务选择页面
 */
public class TaskSelectActivity extends ToolBarActivity {

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
