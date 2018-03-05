package com.tianchuang.ihome_b;

import android.app.Activity;
import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.tianchuang.ihome_b.database.UserInfo;
import com.tianchuang.ihome_b.utils.Utils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Abyss on 2017/2/9.
 * description:
 */

public class TianChuangApplication extends Application {
    public static TianChuangApplication application;
    private List<Activity> activityList = new LinkedList<>();
    private UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Constants.DEBUG_MODE) {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);//检测内存泄露的工具
//            //todo 调试工具,正式发布时去掉
//            Stetho.initializeWithDefaults(this);//调试工具
        }
        if (application == null)
            application = this;
        Utils.init(application);//初始化工具类
        LitePal.initialize(this);
        ZXingLibrary.initDisplayOpinion(this);//初始化zxing扫码
        LitePal.getDatabase();//初始化表单
    }

    // 添加Activity 到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public UserInfo getUserInfo() {
        if (null == userInfo) {
            userInfo = DataSupport.findFirst(UserInfo.class);
        }
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


}
