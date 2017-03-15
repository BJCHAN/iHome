package com.tianchuang.ihome_b.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.TianChuangApplication;
import com.tianchuang.ihome_b.adapter.DrawMenuAdapter;
import com.tianchuang.ihome_b.base.BaseActivity;
import com.tianchuang.ihome_b.bean.DrawMenuItem;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.event.LogoutEvent;
import com.tianchuang.ihome_b.bean.event.OpenScanEvent;
import com.tianchuang.ihome_b.bean.event.SwitchSuccess;
import com.tianchuang.ihome_b.bean.recyclerview.DrawMenuItemDecoration;
import com.tianchuang.ihome_b.fragment.MainFragment;
import com.tianchuang.ihome_b.fragment.PropertyListFragment;
import com.tianchuang.ihome_b.permission.MPermission;
import com.tianchuang.ihome_b.permission.OnMPermissionDenied;
import com.tianchuang.ihome_b.permission.OnMPermissionGranted;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.FileUtils;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/9.
 * description:主页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.id_draw_menu_item_list_select)
    RecyclerView mRecyclerView;
    @BindView(R.id.id_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindArray(R.array.draw_menu_items)
    String[] items;
    @BindView(R.id.spinner)
    TextView spinner;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_draw_name)
    TextView tvDrawName;
    @BindView(R.id.tv_draw_phone)
    TextView tvDrawPhone;

    private DrawMenuAdapter menuAdapter;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.main_fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //添加主页fragment
        addFragment(MainFragment.newInstance());
        initView();
        //设置监听
        initListener();
    }

    /**
     * 设置头部的title
     */
    public void setSpinnerText(String text) {
        if (FragmentUtils.findFragment(getSupportFragmentManager(), PropertyListFragment.class) == null) {//非二级fragment，为主页
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.triangle_icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            spinner.setCompoundDrawables(null, null, drawable, null);
            spinner.setCompoundDrawablePadding(DensityUtil.dip2px(this, 5));
            spinner.setText(text);
            ivRight.setVisibility(View.VISIBLE);
        } else {
            spinner.setCompoundDrawables(null, null, null, null);
            spinner.setText(text);
            ivRight.setVisibility(View.INVISIBLE);
        }
    }

    public void setIvRightEnable(boolean enable) {
        ivRight.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    private void initView() {
        LoginBean loginBean = UserUtil.getLoginBean();
        if (loginBean != null) {//初始化菜单布局
            tvDrawName.setText(loginBean.getName());
            tvDrawPhone.setText(loginBean.getMobile());
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DrawMenuItemDecoration(20));
        ArrayList<DrawMenuItem> drawMenuItems = new ArrayList<>();
        for (String item : items) {
            DrawMenuItem drawMenuItem = new DrawMenuItem();
            drawMenuItem.setName(item);
            drawMenuItems.add(drawMenuItem);
        }
        menuAdapter = new DrawMenuAdapter(R.layout.draw_menu_item_holder, drawMenuItems);
        mRecyclerView.setAdapter(menuAdapter);
    }

    @OnClick({R.id.iv_navigation_icon, R.id.iv_right, R.id.spinner})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_navigation_icon://侧滑菜单的入口
                toggle();
                break;
            case R.id.iv_right://抢单大厅的入口
                startActivityWithAnim(new Intent(this, RobHallActivity.class));
                break;
            case R.id.spinner://物业列表入口
                if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                    addFragment(PropertyListFragment.newInstance());//避免重复添加
                }
                break;
        }
    }


    private void initListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 1:
                        startActivityWithAnim(new Intent(MainActivity.this, MyFormActivity.class));
                        break;
                    case 3:
                        startActivityWithAnim(new Intent(MainActivity.this, MyOrderActivity.class));
                        break;
                    case 4:
                        startActivityWithAnim(new Intent(MainActivity.this, VisitorListActivity.class));
                        break;
                    case 6://投诉建议
                        startActivityWithAnim(new Intent(MainActivity.this, ComplainSuggestActivity.class));
                        break;
                    case 7://内部报事
                        startActivityWithAnim(new Intent(MainActivity.this, MenuInnerReportsActivity.class));
                        break;
                    case 8://设置
                        startActivityWithAnim(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            /**
             * @param drawerView
             * @param slideOffset   偏移(0-1)
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // 判断是否左菜单并设置移动(如果不这样设置,则主页面的内容不会向右移动)
                if (drawerView.getTag().equals("left")) {
                    View content = mDrawerLayout.getChildAt(0);
                    int offset = (int) (drawerView.getWidth() * slideOffset);
                    content.setTranslationX(offset);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    /**
     * 自定义NavigationIcon设置关联DrawerLayout
     */
    private void toggle() {
        int drawerLockMode = mDrawerLayout.getDrawerLockMode(GravityCompat.START);
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START) && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                int drawerLockMode = mDrawerLayout.getDrawerLockMode(GravityCompat.START);
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)
                        && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {//菜单未关闭，先关闭菜单
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {//关闭页面
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LogoutEvent event) {//退出登录的事件
        UserUtil.logout();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        TianChuangApplication.application.exit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SwitchSuccess event) {//切换用户的事件

    }


    @Subscribe(threadMode = ThreadMode.MAIN)//接收打开扫一扫的事件
    public void onMessageEvent(OpenScanEvent event) {//打开扫一扫的事件
        requestCameraPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
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
        Intent intent = new Intent(MainActivity.this, ScanCodeActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnMPermissionDenied(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        showPermissionInfo(getString(R.string.perssion_camera_tip), false);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        FileUtils.deleteImageFile();//删除上传图片缓存
    }
}
