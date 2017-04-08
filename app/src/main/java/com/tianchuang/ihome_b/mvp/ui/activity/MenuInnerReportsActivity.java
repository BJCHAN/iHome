package com.tianchuang.ihome_b.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.HomePageMultiItem;
import com.tianchuang.ihome_b.bean.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.mvp.ui.fragment.MenuInnerReportsDetailFragment;
import com.tianchuang.ihome_b.mvp.ui.fragment.MenuInnerReportsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

public class MenuInnerReportsActivity extends ToolBarActivity {


    private TransferImage mTransferImage;

    @Override
    protected BaseFragment getFirstFragment() {
        if (getIntent() != null) {//从主页过来
            Serializable item = getIntent().getSerializableExtra("item");
            if (item != null && item instanceof HomePageMultiItem) {
                MenuInnerReportsItemBean menuInnerReportsItemBean = ((HomePageMultiItem) item).getMenuInnerReportsItemBean();
                return MenuInnerReportsDetailFragment.newInstance(menuInnerReportsItemBean);
            }
        }
        return MenuInnerReportsFragment.newInstance();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        initNormalToolbar(toolbar, true);
        setToolbarTitle("内部报事");
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
}
