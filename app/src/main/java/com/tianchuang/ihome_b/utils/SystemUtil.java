package com.tianchuang.ihome_b.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by wangning on 2016/3/31.
 */
public class SystemUtil {
    /**
     * 改变状态栏颜色 针对Android5.0+
     */
    public static void changeStatusBarColor(Context context, int colorRes) {
        if (null != context && context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((Activity) context).getWindow().setStatusBarColor(ContextCompat.getColor(context, colorRes));
//            getWindow().setNavigationBarColor(ContextCompat.getColor(this, colorRes));//修改底部操作栏（返回键）
            } else {
                changeStatusBarColorBelowAndroidL(context, colorRes);
            }
        }
    }

    /**
     * 改变状态栏颜色 针对Android5.0以下
     */
    private static void changeStatusBarColorBelowAndroidL(Context context, int colorRes) {
        if (null == context)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
            setTranslucentStatus(context, true);
            SystemBarTintManager tintManager = new SystemBarTintManager((Activity) context);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorRes);
        }
    }

    private static void setTranslucentStatus(Context context, boolean on) {
        if (null != context && context instanceof Activity) {
            Window win = ((Activity) context).getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 根据context获取activity
     */
    public static Activity getActivity(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }
}
