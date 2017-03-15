package com.tianchuang.ihome_b.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tianchuang.ihome_b.R;

/**
 * Created by wangning on 2015/8/18.
 */
public class MaterialDialogsUtil {
    private Context mContext;

    public MaterialDialogsUtil(Context context) {
        this.mContext = context;
    }
    MaterialDialog materialDialog;
    MaterialDialog.Builder builder;


    /**
     * “使用帮助类”弹出框,一般只有一个按钮
     *
     * @param titleTextRes    标题内容
     * @param contentTextRes  正文内容
     * @param positiveTextRes 确定按钮文本
     */
    public MaterialDialog.Builder getBuilder(String titleTextRes, String contentTextRes, String positiveTextRes, String negativeTextRes) {
        builder = new MaterialDialog.Builder(mContext).title(titleTextRes).content(contentTextRes).positiveText(positiveTextRes).negativeText(negativeTextRes);
        return builder;
    }
    /**
     * 返回加载的dialog
     * */
    public MaterialDialog getProgressDialog() {
        return new MaterialDialog.Builder(mContext).customView(R.layout.base_progress_view, false).canceledOnTouchOutside(false).build();
    }

    public void show() {
        if (null != materialDialog && materialDialog.isShowing())
            materialDialog.dismiss();
        if (null != builder) {
            materialDialog = builder.build();//避免activity被销毁后，materialDialog无法show报错
            boolean destroyed = false;
            boolean finishing = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Activity activity = SystemUtil.getActivity(mContext);
                if (activity == null) return;
                destroyed = activity.isDestroyed();
                finishing = activity.isFinishing();
            }
            if (!(destroyed || finishing))
                materialDialog.show();
        }
    }

    public void dismiss() {
        if (null != materialDialog)
            materialDialog.dismiss();
    }
}
