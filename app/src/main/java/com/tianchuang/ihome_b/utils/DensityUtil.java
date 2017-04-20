package com.tianchuang.ihome_b.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        if (null != context && null != context.getResources() && null != context.getResources().getDisplayMetrics()) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
        return 0;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        if (null != context && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        return (int) pxValue;
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(Context context, float spValue) {
        if (null != context && null != context.getResources() && null != context.getResources().getDisplayMetrics()) {
            final float scale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * scale + 0.5f);
        }
        return 0;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机密度
     *
     * @return
     */
    public static float getDensity(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
//        double width = metric.widthPixels; // 屏幕宽度（像素）
//        double height = metric.heightPixels; // 屏幕高度（像素）
        float density = metric.density; // 屏幕密度
        return density;
    }

    /**
     * 获取手机宽度
     *
     * @return
     */
    public static double getMetricsWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        double width = metric.widthPixels; // 屏幕宽度（像素）
//        double height = metric.heightPixels; // 屏幕高度（像素）
//        float density = metric.density; // 屏幕密度
        return width;
    }

    /**
     * 获取手机高度
     *
     * @return
     */
    public static double getMetricsHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        double heightPixels = metric.heightPixels; // 屏幕宽度（像素）
//        double height = metric.heightPixels; // 屏幕高度（像素）
//        float density = metric.density; // 屏幕密度
        return heightPixels;
    }

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param isDp   需要设置的数值是否为DP
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    public static ViewGroup.LayoutParams setViewMargin(Context context, View view, boolean isDp, int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }

        //根据DP与PX转换计算值
        if (isDp) {
            leftPx = dip2px(context,left);
            rightPx = dip2px(context,right);
            topPx = dip2px(context,top);
            bottomPx = dip2px(context,bottom);
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        return marginParams;
    }
}
