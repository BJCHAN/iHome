package com.tianchuang.ihome_b.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.CommonFeeBean;
import com.tianchuang.ihome_b.bean.MaterialListItemBean;
import com.tianchuang.ihome_b.bean.event.MaterialFeeEvent;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by Abyss on 2016/12/26.
 * description:添加费用的弹窗
 */

public class FeeAddDialogFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.tv_unit_price)
    TextView tvUnitPrice;
    @BindView(R.id.tv_sum_price)
    TextView tvSumPrice;
    private float buyPrice;
    private MaterialListItemBean bean;

    public static FeeAddDialogFragment newInstance(MaterialListItemBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        FeeAddDialogFragment fragment = new FeeAddDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bean = (MaterialListItemBean) getArguments().getSerializable("bean");
        tvName.setText(bean.getTypeName());
        buyPrice = bean.getSalePrice();
        tvUnitPrice.setText("单价：" + StringUtils.formatNum(buyPrice) + "元/1" + bean.getTypeUnits());
        RxTextView.textChanges(etNum)
                .compose(bindToLifecycle())
                .subscribe(text -> {
                    String str = String.valueOf(text);
                    if (StringUtils.isNumber(str)) {
                        float num = Float.parseFloat(str);
                        float sumPrice = buyPrice * num;
                        tvSumPrice.setText(String.valueOf("总计：￥" + StringUtils.formatNum(sumPrice) + ""));
                    }

                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fee_add_fragment;
    }

    @OnClick(R.id.tv_sure)
    protected void onClick() {
        String numText = etNum.getText().toString().trim();
        if (StringUtils.isNumber(numText)) {
            Float counts = Float.valueOf(numText);
            if (!(counts == 0)) {
                CommonFeeBean commonFeeBean = new CommonFeeBean();
                commonFeeBean.setType(2);
                commonFeeBean.setCounts(counts);
                commonFeeBean.setTitle(bean.getTypeName());
                commonFeeBean.setRefId(bean.getId());
                commonFeeBean.setFee(bean.getSalePrice() * counts + "");
                EventBus.getDefault().post(new MaterialFeeEvent(commonFeeBean));
            } else {
                ToastUtil.showToast(getContext(), "请输入有效数量");
            }
        } else {
            ToastUtil.showToast(getContext(), "请输入有效数量");
        }
    }


    @OnClick({R.id.iv_add, R.id.iv_sub})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add://添加数量
                String numText = etNum.getText().toString().trim();
                if (StringUtils.isNumber(numText)) {
                    Float num = Float.valueOf(numText);
                    num += 1L;
                    etNum.setText(num + "");
                }

                break;
            case R.id.iv_sub://减少数量
                String numText2 = etNum.getText().toString().trim();
                if (StringUtils.isNumber(numText2)) {
                    Float num2 = Float.valueOf(numText2);
                    if (num2 > 1) {
                        num2 -= 1L;
                        etNum.setText(num2 + "");
                    }
                }
                break;
        }
    }
}

