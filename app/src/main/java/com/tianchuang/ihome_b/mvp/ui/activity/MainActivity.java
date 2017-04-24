package com.tianchuang.ihome_b.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.TianChuangApplication;
import com.tianchuang.ihome_b.adapter.DrawMenuAdapter;
import com.tianchuang.ihome_b.base.BaseActivity;
import com.tianchuang.ihome_b.bean.DrawMenuItem;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.bean.event.LogoutEvent;
import com.tianchuang.ihome_b.bean.event.OpenScanEvent;
import com.tianchuang.ihome_b.bean.event.SwitchSuccessEvent;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.bean.recyclerview.DrawMenuItemDecoration;
import com.tianchuang.ihome_b.mvp.ui.fragment.MainFragment;
import com.tianchuang.ihome_b.mvp.ui.fragment.PropertyListFragment;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.permission.MPermission;
import com.tianchuang.ihome_b.permission.OnMPermissionDenied;
import com.tianchuang.ihome_b.permission.OnMPermissionGranted;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.FileUtils;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.view.OneButtonDialogFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

import static com.tencent.android.tpush.XGPush4Msdk.registerPush;

/**
 * Created by Abyss on 2017/2/9.
 * description:主页
 */
public class MainActivity extends BaseActivity implements MainFragment.LittleRedListener {

    @BindView(R.id.id_draw_menu_item_list_select)
    RecyclerView mRecyclerView;
    @BindView(R.id.id_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindArray(R.array.draw_menu_items)
    String[] itemsNameArray;
    @BindView(R.id.spinner)
    TextView spinner;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_draw_name)
    TextView tvDrawName;
    @BindView(R.id.tv_draw_phone)
    TextView tvDrawPhone;
    @BindView(R.id.iv_little_red)
    ImageView ivLittleRed;
    private ArrayList<DrawMenuItem> drawMenuItems = new ArrayList<>();
    private DrawMenuAdapter menuAdapter;
    private int noticeCount = 0;
    private MainFragment mainFragment;

    /**
     * 扫描跳转Activity RequestCode
     */

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
        initXGPush();
        //添加主页fragment
        mainFragment = MainFragment.newInstance().setLittleRedListener(this);
        addFragment(mainFragment);
        initView();
        //设置监听
        initListener();
    }

    /**
     * 初始化信鸽推送
     */
    private void initXGPush() {
        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, Constants.DEBUG_MODE);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        registerPush(getApplicationContext(), "staff_" + UserUtil.getUserid(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
            }

            @Override
            public void onFail(Object o, int i, String s) {
            }
        });
        XGPushManager.registerPush(getApplicationContext());
    }


    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DrawMenuItemDecoration(20));
        menuAdapter = new DrawMenuAdapter(R.layout.draw_menu_item_holder, drawMenuItems);
        mRecyclerView.setAdapter(menuAdapter);
        LoginBean loginBean = UserUtil.getLoginBean();
        if (loginBean != null) {//初始化菜单布局
            tvDrawName.setText(loginBean.getName());
            tvDrawPhone.setText(loginBean.getMobile());
        }

        initDrawMenu(loginBean);

    }

    private void initDrawMenu(LoginBean loginBean) {
        drawMenuItems.clear();
        setIvRightEnable(false);
        List<Integer> menuList = loginBean.getMenuList();
        if (menuList != null && menuList.size() > 0) {
            Observable.from(menuList)
                    .distinct()//去重
                    .compose(bindToLifecycle())
                    .subscribe(integer -> {
                        switch (integer) {
                            case 1://我的任务
                                drawMenuItems.add(new DrawMenuItem().setId(0).setName(itemsNameArray[0]));
                                break;
                            case 2://我的表单
                                drawMenuItems.add(new DrawMenuItem().setId(1).setName(itemsNameArray[1]));
                                break;
                            case 8://报修抢单
                                drawMenuItems.add(new DrawMenuItem().setId(2).setName(itemsNameArray[2]));
                                drawMenuItems.add(new DrawMenuItem().setId(3).setName(itemsNameArray[3]));
                                setIvRightEnable(true);//抢单大厅显示
                                break;
                            case 7://访客列表
                                drawMenuItems.add(new DrawMenuItem().setId(4).setName(itemsNameArray[4]));
                                break;
                            case 3://管理通知
                                drawMenuItems.add(new DrawMenuItem().setId(5).setName(itemsNameArray[5]));
                                break;
                            case 5://内部报事接收
                                drawMenuItems.add(new DrawMenuItem().setId(7).setName(itemsNameArray[7]));
                                break;
                            case 10://投诉建议
                                drawMenuItems.add(new DrawMenuItem().setId(6).setName(itemsNameArray[6]));
                                break;

                        }
                    });

        }
        drawMenuItems.add(new DrawMenuItem().setId(8).setName(itemsNameArray[8]));//默认有设置
        menuAdapter.notifyDataSetChanged();
    }


    private void initListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                DrawMenuItem drawMenuItem = drawMenuItems.get(position);
                mainFragment.setCurrentPostion(-1);
                switch (drawMenuItem.getId()) {
                    case 0://我的任务
                        startActivityWithAnim(new Intent(MainActivity.this, MyTaskActivity.class));
                        break;
                    case 1://我的表单
                        startActivityWithAnim(new Intent(MainActivity.this, MyFormActivity.class));
                        break;
                    case 2://抢单大厅
                        startActivityWithAnim(new Intent(MainActivity.this, RobHallActivity.class));
                        break;
                    case 3://我的订单
                        startActivityWithAnim(new Intent(MainActivity.this, MyOrderActivity.class));
                        break;
                    case 4://访客列表
                        startActivityWithAnim(new Intent(MainActivity.this, VisitorListActivity.class));
                        break;
                    case 5: //管理通知
                        startActivityWithAnim(new Intent(MainActivity.this, ManageNotificationActivity.class));
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

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

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

    @OnClick({R.id.iv_navigation_icon, R.id.iv_right, R.id.spinner, R.id.rl_user_info})
    public void onClick(View view) {
        mainFragment.setCurrentPostion(-1);
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
            case R.id.rl_user_info://菜单上访问个人信息
                startActivityWithAnim(new Intent(this, PersonalInfoActivity.class));
                break;
        }
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
            ivLittleRed.setVisibility(this.noticeCount > 0 ? View.VISIBLE : View.INVISIBLE);
        } else {
            spinner.setCompoundDrawables(null, null, null, null);
            spinner.setText(text);
            ivRight.setVisibility(View.INVISIBLE);
            ivLittleRed.setVisibility(View.INVISIBLE);
        }
    }

    public void setIvRightEnable(boolean enable) {
        ivRight.setVisibility(enable ? View.VISIBLE : View.GONE);
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

    private boolean twoBack;

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
                    if (twoBack) {
                        finish();
                    } else {
                        ToastUtil.showToast(this, "再按一次退出");
                        twoBack = true;
                        new Handler().postDelayed(() -> twoBack = false, 2000);
                    }
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
    public void onMessageEvent(SwitchSuccessEvent event) {//切换用户的事件
        initDrawMenu(UserUtil.getLoginBean());
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
        //处理扫描结果（在界面上显示）
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (requestCode == Constants.QrCode.HOME_OPEN_CODE) {//主页进行扫码
                requestQrCode(bundle, currentTaskId);
            }

        }


    }

    private int currentTaskId = -1;//设置当前任务的id

    public void setCurrentTaskId(int currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    private void requestQrCode(Bundle bundle, int taskId) {
        if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
            String result = bundle.getString(CodeUtils.RESULT_STRING);
            HashMap<String, String> map = new HashMap<>();
            map.put("propertyCompanyId", String.valueOf(UserUtil.getLoginBean().getPropertyCompanyId()));
            map.put("code", result);
            if (taskId != -1) {
                map.put("taskRecordId", String.valueOf(taskId));
                requestTaskQrCode(map);
            } else {
                requestQrCode(map);
            }
        } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
            Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
        }
        setCurrentTaskId(-1);//初始化当前任务id
    }
    private void requestTaskQrCode(HashMap<String, String> map) {
        HomePageModel.requestTaskQrCode(map)
                .compose(RxHelper.handleResult())
                .compose(bindToLifecycle())
                .subscribe(new RxSubscribe<TaskPointDetailBean>() {
                    @Override
                    protected void _onNext(TaskPointDetailBean detailBean) {
                        if (detailBean != null) {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, ControlPointDetailActivity.class);
                            intent.putExtra("detailBean", detailBean);
                            startActivityWithAnim(intent);
                        } else {
                            ToastUtil.showToast(getApplicationContext(),"任务为空");
                        }

                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(MainActivity.this,message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });

    }
    private void requestQrCode(HashMap<String, String> map) {
        HomePageModel.requestQrCode(map)
                .compose(RxHelper.handleResult())
                .compose(this.bindToLifecycle())
                .subscribe(new RxSubscribe<ArrayList<QrCodeBean>>() {
                    @Override
                    protected void _onNext(ArrayList<QrCodeBean> qrCodeBeanlist) {
//                        ToastUtil.showToast(MainActivity.this, "请求成功！");
                        if (qrCodeBeanlist != null && qrCodeBeanlist.size() > 0) {
                            Intent intent = new Intent(getApplicationContext(), TaskSelectActivity.class);
                            ListBean listBean = new ListBean();
                            listBean.setQrCodeBeanArrayList(qrCodeBeanlist);
                            intent.putExtra("listBean", listBean);
                            startActivityWithAnim(intent);
                        } else {
                            ToastUtil.showToast(getApplicationContext(), "任务为空");
                        }

                    }

                    @Override
                    protected void _onError(String message) {
                        if ("考勤成功".equals(message)) {
                            OneButtonDialogFragment.newInstance(message)
                                    .show(getFragmentManager(), "");
                        } else {
                            ToastUtil.showToast(MainActivity.this, message);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
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
        startActivityForResult(intent, Constants.QrCode.HOME_OPEN_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnMPermissionDenied(Constants.PERMISSION_REQUEST_CODE.BASIC_PERMISSION_CAMERA_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        showPermissionInfo(getString(R.string.perssion_camera_tip), false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FileUtils.deleteImageFile();//删除上传图片缓存
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 小红点发生变化
     */
    @Override
    public void onRedPointChanged(int noticeCount) {
        this.noticeCount = noticeCount;
        ivLittleRed.setVisibility(this.noticeCount > 0 ? View.VISIBLE : View.INVISIBLE);
    }


}
