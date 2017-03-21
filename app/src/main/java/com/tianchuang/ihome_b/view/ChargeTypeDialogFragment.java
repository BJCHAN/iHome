package com.tianchuang.ihome_b.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.ChargeTypeAdapter;
import com.tianchuang.ihome_b.bean.ChargeTypeListItemBean;
import com.tianchuang.ihome_b.bean.CommonFeeBean;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.recyclerview.MaterialTypeItemDecoration;
import com.tianchuang.ihome_b.utils.DensityUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Abyss on 2016/12/26.
 * description:材料选择的弹窗
 */

public class ChargeTypeDialogFragment extends DialogFragment {


    private static ChargeTypeDialogFragment fragment;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private Unbinder bind;


    public static ChargeTypeDialogFragment newInstance(ListBean bean) {
        Bundle args = new Bundle();
        args.putSerializable("bean", bean);
        fragment = new ChargeTypeDialogFragment();
        fragment.setArguments(args);
        return fragment;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fee_type_dialog_view, container, false);
        bind = ButterKnife.bind(this, view);
        ListBean bean = (ListBean) getArguments().getSerializable("bean");
        final ArrayList<ChargeTypeListItemBean> mData = bean.getChargeTypeList();
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.addItemDecoration(new MaterialTypeItemDecoration(DensityUtil.dip2px(getContext(), 1)));
        rvList.setAdapter(new ChargeTypeAdapter(R.layout.charge_type_item_holder, mData));
        rvList.addOnItemTouchListener(new OnItemClickListener() {

            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChargeTypeListItemBean chargeTypeListItemBean = mData.get(position);
                if (onChargeTypeSelectedListener != null && chargeTypeListItemBean != null) {
                    CommonFeeBean commonFeeBean = new CommonFeeBean();
                    commonFeeBean.setCounts(1);
                    commonFeeBean.setFee(String.valueOf(chargeTypeListItemBean.getFees()));
                    commonFeeBean.setRefId(chargeTypeListItemBean.getId());
                    commonFeeBean.setTitle(chargeTypeListItemBean.getName());
                    commonFeeBean.setType(1);
                    onChargeTypeSelectedListener.onChargeFee(commonFeeBean);
                }
                ChargeTypeDialogFragment.this.dismiss();
            }
        });
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        onChargeTypeSelectedListener = null;
    }

    private OnChargeTypeSelectedListener onChargeTypeSelectedListener;

    public ChargeTypeDialogFragment setOnChargeTypeSelectedListener(OnChargeTypeSelectedListener onChargeTypeSelectedListener) {
        this.onChargeTypeSelectedListener = onChargeTypeSelectedListener;
        return this;
    }

    public interface OnChargeTypeSelectedListener {
        void onChargeFee(CommonFeeBean commonFeeBean);
    }

}
