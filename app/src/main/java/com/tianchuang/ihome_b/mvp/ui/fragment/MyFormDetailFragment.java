package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.FormDetailMultiAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.MyFormDetailBean;
import com.tianchuang.ihome_b.bean.MyFormItemBean;
import com.tianchuang.ihome_b.bean.model.FormModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/23.
 * description:我的表单详情
 */

public class MyFormDetailFragment extends BaseLoadingFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    public static MyFormDetailFragment newInstance(MyFormItemBean itemBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", itemBean);
        MyFormDetailFragment fragment = new MyFormDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myform_detail;
    }

    @Override
    protected void initData() {
        MyFormItemBean bean = (MyFormItemBean) getArguments().getSerializable("bean");
        if (bean != null) {
            tvName.setText(StringUtils.getNotNull(bean.getTypeName()));
            tvDate.setText(StringUtils.getNotNull(DateUtils.formatDate(bean.getCreatedDate(), DateUtils.TYPE_05)));
        }
        FormModel.INSTANCE.myFormDetail(bean.getId())
                .compose(RxHelper.<MyFormDetailBean>handleResult())
                .compose(this.<MyFormDetailBean>bindToLifecycle())
                .subscribe(new RxSubscribe<MyFormDetailBean>() {
                    @Override
                    public void _onNext(MyFormDetailBean myFormDetailBean) {
                        checkData(myFormDetailBean);
                        FormDetailMultiAdapter adapter = new FormDetailMultiAdapter(myFormDetailBean.getFormDataVos());
                        rvList.setAdapter(adapter);
                    }

                    @Override
                    public void _onError(String message) {
                        showErrorPage();
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
