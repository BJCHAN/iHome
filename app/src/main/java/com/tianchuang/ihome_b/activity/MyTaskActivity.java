package com.tianchuang.ihome_b.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.fragment.MyTaskControlPointDetailFragment;
import com.tianchuang.ihome_b.fragment.MyTaskFragment;
import com.tianchuang.ihome_b.fragment.MyTaskInputDetailFragment;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abyss on 2017/3/9.
 * description:我的任务模块
 */
public class MyTaskActivity extends ToolBarActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        if (getIntent() != null) {//从主页过来
            Serializable item = getIntent().getSerializableExtra("item");
            if (item != null && item instanceof HomePageMultiItem) {
                MyTaskUnderWayItemBean itemBean = ((HomePageMultiItem) item).getMyTaskUnderWayItemBean();
                int type = itemBean.getTaskKind();
                if (type == 5) {//查看录入任务详情
                    return MyTaskInputDetailFragment.newInstance(itemBean);
                } else {//控制点
                    return MyTaskControlPointDetailFragment.newInstance(itemBean);
                }
            }
        }
        return MyTaskFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        initNormalToolbar(toolbar, true);
    }

    /**
     * 关闭所有fragment
     */
    public void closeAllFragment() {
        while (getSupportFragmentManager().getBackStackEntryCount() != 1) {
            removeFragment();
        }
    }

    public void startImageSelector(ImgSelConfig config, int type) {
        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调,三张图片
        if (resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (getImageByCodeListener != null) {
                getImageByCodeListener.onImages(pathList, requestCode);
            }
        }
    }

    public interface GetImageByCodeListener {
        void onImages(List<String> paths, int type);
    }

    public void setGetImageByCodeListener(GetImageByCodeListener getImageByCodeListener) {
        this.getImageByCodeListener = getImageByCodeListener;
    }

    private GetImageByCodeListener getImageByCodeListener;
}
