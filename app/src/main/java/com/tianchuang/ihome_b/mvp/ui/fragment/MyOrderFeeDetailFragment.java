package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.ItemRemoveAdapter;
import com.tianchuang.ihome_b.bean.CommonFeeBean;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.event.MaterialFeeEvent;
import com.tianchuang.ihome_b.mvp.MVPBaseFragment;
import com.tianchuang.ihome_b.mvp.contract.FeeDetailContract;
import com.tianchuang.ihome_b.mvp.presenter.FeeDetailPresenter;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.view.ChargeTypeDialogFragment;
import com.tianchuang.ihome_b.view.ItemRemoveRecyclerView;
import com.tianchuang.ihome_b.view.MaterialTypeDialogFragment;
import com.tianchuang.ihome_b.view.OnItemRemoveClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/3/5.
 * description:费用明细
 */

public class MyOrderFeeDetailFragment extends MVPBaseFragment<FeeDetailContract.View, FeeDetailPresenter> implements FeeDetailContract.View, ChargeTypeDialogFragment.OnChargeTypeSelectedListener {
    @BindView(R.id.rv_item_remove_recyclerview)
    ItemRemoveRecyclerView rvItemRemoveRecyclerview;
    @BindView(R.id.tv_sum_price)
    TextView tvSumPrice;
    @BindView(R.id.cb_isunder_line)
    CheckBox cbIsunderLine;
    private ArrayList<CommonFeeBean> commonFeeBeenList;
    private ItemRemoveAdapter adapter;
    private int repairId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_fee_detail;
    }

    public static MyOrderFeeDetailFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        MyOrderFeeDetailFragment fragment = new MyOrderFeeDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("费用明细");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        repairId = getArguments().getInt("id");
        commonFeeBeenList = new ArrayList<>();
        rvItemRemoveRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemRemoveAdapter(getContext(), commonFeeBeenList);
        rvItemRemoveRecyclerview.setAdapter(adapter);
        rvItemRemoveRecyclerview.setOnItemClickListener(new OnItemRemoveClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                adapter.removeItem(position);
                tvSumPrice.setText("￥" + StringUtils.formatNum(getSum()));
            }
        });
    }

    /**
     * 请求材料和费用列表
     */
    @Override
    protected void initData() {
        mPresenter.fetchFeeList();
    }

    @OnClick({R.id.tv_add_material, R.id.tv_add_charge, R.id.bt_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_material://材料弹窗
                MaterialTypeDialogFragment.newInstance(new ListBean().setMaterialTypeList(mPresenter.getMaterialList()))
                        .show(getFragmentManager(), "");
                break;
            case R.id.tv_add_charge://费用弹窗
                ChargeTypeDialogFragment.newInstance(new ListBean().setChargeTypeList(mPresenter.getChargeTypeList()))
                        .setOnChargeTypeSelectedListener(this)
                        .show(getFragmentManager(), "");
                break;
            case R.id.bt_sure:
                boolean checked = cbIsunderLine.isChecked();
                mPresenter.requestSubmit(repairId, checked, commonFeeBeenList);//请求网络提交费用信息
                break;
        }
    }


    //获取选择的人工费用的数据
    @Override
    public void onChargeFee(CommonFeeBean commonFeeBean) {
        commonFeeBeenList.add(commonFeeBean);
        adapter.notifyItemInserted(adapter.getItemCount());
        tvSumPrice.setText("￥" + StringUtils.formatNum(getSum()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//获取选择的材料费用数据
    public void onMessageEvent(MaterialFeeEvent event) {
        CommonFeeBean commonFeeBean = event.getCommonFeeBean();
        commonFeeBeenList.add(commonFeeBean);
        adapter.notifyItemInserted(adapter.getItemCount());
        tvSumPrice.setText("￥" + StringUtils.formatNum(getSum()));
    }

    private float getSum() {
        float sum = 0f;
        for (CommonFeeBean feeBean : commonFeeBeenList) {
            float price = Float.valueOf(feeBean.getFee());
            sum = sum + price;
        }
        return sum;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
