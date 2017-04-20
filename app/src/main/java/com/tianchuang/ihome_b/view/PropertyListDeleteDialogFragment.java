package com.tianchuang.ihome_b.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.LayoutUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Abyss on 2016/12/26.
 * description:物业列表删除的弹窗
 */

public class PropertyListDeleteDialogFragment extends DialogFragment {


    Unbinder bind;
    private MaterialDialog dialog;
    private View customView;

    public static PropertyListDeleteDialogFragment newInstance() {
        return new PropertyListDeleteDialogFragment();
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = DensityUtil.dip2px(getActivity(), 300);
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new MaterialDialog.Builder(getContext()).customView(R.layout.property_list_item_delete_dialog, false).build();
        customView = dialog.getCustomView();
        bind = ButterKnife.bind(this, customView);
        return dialog;
    }

    @OnClick(R.id.tv_delete)
    public void onViewClicked() {
        LinearLayout container = (LinearLayout) customView.findViewById(R.id.ll_container);
        container.removeAllViews();
        View newView = LayoutUtil.inflate(R.layout.delete_property_list_dialog_view);
        container.setGravity(Gravity.CENTER);//设置内容居中
        container.setBackground(new ColorDrawable(Color.TRANSPARENT));//设置背景透明
        newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initNewView(newView);
        container.addView(newView);
    }

    private void initNewView(View newView) {
        //确认删除
        newView.findViewById(R.id.tv_sure).setOnClickListener(v -> {
            if (confirmDeleteListener != null) {
                this.dismiss();
                confirmDeleteListener.confirmedDelete();
            }
        });
        //取消
        newView.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            this.dismiss();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
    /**
     * 确认删除的接口
     * */
    public interface ConfirmDeleteListener {
     void confirmedDelete();
    }
    private ConfirmDeleteListener confirmDeleteListener;

    public PropertyListDeleteDialogFragment setConfirmDeleteListener(ConfirmDeleteListener confirmDeleteListener) {
        this.confirmDeleteListener = confirmDeleteListener;
        return this;
    }
}
