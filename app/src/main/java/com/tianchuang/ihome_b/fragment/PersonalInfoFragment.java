package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.PersonalInfoAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.PersonalInfoBean;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;

import butterknife.BindView;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/3/29.
 * description:
 */

public class PersonalInfoFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private ArrayList<PersonalInfoBean> mData;
    private PersonalInfoAdapter adapter;

    public static PersonalInfoFragment newInstance() {
        return new PersonalInfoFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvName.setText(StringUtils.getNotNull(UserUtil.getLoginBean().getName()));
        tvPhone.setText(StringUtils.getNotNull(UserUtil.getLoginBean().getMobile()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initData() {
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mData = new ArrayList<>();
        adapter = new PersonalInfoAdapter(mData);
        rvList.setAdapter(adapter);
        HomePageModel.requestPersonInfo()
                .compose(RxHelper.<ArrayList<PersonalInfoBean>>handleResult())
                .compose(this.<ArrayList<PersonalInfoBean>>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<ArrayList<PersonalInfoBean>>() {
                    @Override
                    protected void _onNext(ArrayList<PersonalInfoBean> list) {
                        mData.clear();
                        mData.addAll(list);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onCompleted() {
                        dismissProgress();

                    }
                });
    }
}
