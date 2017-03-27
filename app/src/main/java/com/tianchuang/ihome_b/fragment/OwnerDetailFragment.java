package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.OwnerDetailAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.OwnerDetailBean;
import com.tianchuang.ihome_b.bean.OwnerDetailListBean;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Abyss on 2017/3/25.
 * description:业主详情
 */

public class OwnerDetailFragment extends BaseFragment {
    @BindView(R.id.rv_owner_list)
    RecyclerView rvOwnerList;
    private List<OwnerDetailListBean> mData = new ArrayList<>();

    public static OwnerDetailFragment newInstance(OwnerDetailBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        OwnerDetailFragment fragment = new OwnerDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        OwnerDetailBean detailBean = (OwnerDetailBean) getArguments().getSerializable("bean");
        View header = LayoutUtil.inflate(R.layout.owner_detail_header_holder);
        TextView tvAddress = (TextView) header.findViewById(R.id.tv_address);
        tvAddress.setText(StringUtils.getNotNull(detailBean.getAddress()));
        rvOwnerList.setLayoutManager(new LinearLayoutManager(getContext()));
        if (detailBean.getOwnersInfo() != null) {
            ArrayList<OwnerDetailBean.OwnersInfoBean> list = new ArrayList<>();
            list.add(detailBean.getOwnersInfo());
            mData.add(new OwnerDetailListBean().setTitle("业主").setOwnerInfo(list));
        }
        if (detailBean.getOwnerDesignateInfo() != null) {
            ArrayList<OwnerDetailBean.OwnersInfoBean> list = new ArrayList<>();
            list.add(detailBean.getOwnerDesignateInfo());
            mData.add(new OwnerDetailListBean().setTitle("业主指定人").setOwnerInfo(list));
        }

        if (detailBean.getOrdinaryUserInfo() != null) {
            ArrayList<OwnerDetailBean.OwnersInfoBean> list = new ArrayList<>();
            list.addAll(detailBean.getOrdinaryUserInfo());
            mData.add(new OwnerDetailListBean().setTitle("普通用户").setOwnerInfo(list));
        }
        OwnerDetailAdapter ownerDetailAdapter = new OwnerDetailAdapter(mData);
        ownerDetailAdapter.addHeaderView(header);
        rvOwnerList.setAdapter(ownerDetailAdapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_owner_detail;
    }

}
