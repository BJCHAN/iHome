package com.tianchuang.ihome_b.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseCustomActivity;
import com.tianchuang.ihome_b.fragment.VisitorListFragment2;

import butterknife.BindView;
import butterknife.OnClick;

public class VisitorListActivity extends BaseCustomActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    @BindView(R.id.et_search_text)
    EditText etSearchText;
    @BindView(R.id.iv_search_request)
    ImageView ivSearchRequest;
    @BindView(R.id.ll_search_view)
    LinearLayout llSearchView;
    private RequestSearchListener requestSearchListener;

    public void setRequestSearchListener(RequestSearchListener requestSearchListener) {
        this.requestSearchListener = requestSearchListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_visitor_list;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fl_fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishWithAnim(true);
        addFragment(VisitorListFragment2.newInstance());
    }

    @OnClick({R.id.iv_back, R.id.search_icon, R.id.iv_search_request})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                removeFragment();
                break;
            case R.id.search_icon:
                llSearchView.setVisibility(View.VISIBLE);
                searchIcon.setVisibility(View.INVISIBLE);
                tvTitle.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_search_request:
                String searchText = etSearchText.getText().toString().trim();
                if (requestSearchListener != null) {
                    requestSearchListener.searchByPhoneNum(searchText);
                }
                break;
        }
    }


    public interface RequestSearchListener {
        void searchByPhoneNum(String phone);
    }


}
