package com.tianchuang.ihome_b.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.DetailMultiAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.ComplainDetailBean;
import com.tianchuang.ihome_b.bean.event.NotifyHomePageRefreshEvent;
import com.tianchuang.ihome_b.bean.model.ComplainSuggestModel;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.LayoutUtil;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;
import com.tianchuang.ihome_b.view.OneButtonDialogFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import rx.Subscriber;

/**
 * Created by Abyss on 2017/3/1.
 * description:投诉详情
 */

public class ComplainDetailFragment extends BaseLoadingFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.fl_complain)
    FrameLayout flComplain;
    private TextView tv_sure;
    private EditText tv_content;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_complain_detail;
    }

    public static ComplainDetailFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        ComplainDetailFragment detailFragment = new ComplainDetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("投诉详情");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        rvList.addItemDecoration(new CommonItemDecoration(20));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * 请求网络
     */
    @Override
    protected void initData() {
        ComplainSuggestModel.complainDetail(id)
                .compose(this.<HttpModle<ComplainDetailBean>>bindToLifecycle())
                .compose(RxHelper.<ComplainDetailBean>handleResult())
                .subscribe(new RxSubscribe<ComplainDetailBean>() {
                    @Override
                    protected void _onNext(ComplainDetailBean bean) {
                        DetailMultiAdapter detailMultiAdapter = new DetailMultiAdapter(bean.getComplaintsDataVos());
                        rvList.setAdapter(detailMultiAdapter);
                        int status = bean.getStatus();
                        //添加头部
                        detailMultiAdapter.addHeaderView(ViewHelper.getDetailHeaderView(bean.getTypeName(), bean.getCreatedDate()));
                        //添加底部
//						当status == 1时显示回复内容
//						当status == 0时才可以进行回复
                        if (status == 1) {
                            detailMultiAdapter.addFooterView(ViewHelper.getDetailFooterView(bean.getReplyContent()));
                        } else {
                            View view = LayoutUtil.inflate(R.layout.multi_detail_footer_holder2);
                            detailMultiAdapter.addFooterView(view);
                            tv_content = ((EditText) view.findViewById(R.id.tv_content));
                            tv_sure = ((TextView) view.findViewById(R.id.tv_sure));
                            tv_sure.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String content = tv_content.getText().toString().trim();
                                    if (content.length() == 0) {
                                        ToastUtil.showToast(getContext(), "内容不能为空");
                                    } else {
                                        requestNetToReplay(id, content);
                                    }
                                }
                            });
                        }
                        controlKeyboardLayout(flComplain, rvList);

                    }

                    @Override
                    protected void _onError(String message) {
                        showErrorPage();
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onCompleted() {
                        showSucceedPage();
                    }
                });
    }


    /**
     * 请求网络进行回复
     *
     * @param id
     * @param content
     */
    private void requestNetToReplay(int id, String content) {
        ComplainSuggestModel.complainReply(id, content)
                .compose(this.<HttpModle<String>>bindToLifecycle())
                .doOnSubscribe(()->showProgress())
                .subscribe(new Subscriber<HttpModle<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgress();
                        ToastUtil.showToast(getContext(), "连接失败");
                    }

                    @Override
                    public void onNext(HttpModle<String> modle) {
                        dismissProgress();
                        if (modle.success()) {
                            ToastUtil.showToast(getContext(), "回复成功");
                            removeFragment();
                            EventBus.getDefault().post(new NotifyHomePageRefreshEvent());//通知主页刷新
                        } else {
                            if (modle.msg != null)
                                showDialog(modle.msg);
                        }
                    }
                });

    }

    private void showDialog(String tip) {
        OneButtonDialogFragment.newInstance(tip)
                .show(getHoldingActivity().getFragmentManager(), OneButtonDialogFragment.class.getSimpleName());
    }

    /**
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private void controlKeyboardLayout(final View root, final RecyclerView needToScrollView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                getHoldingActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = getHoldingActivity().getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                needToScrollView.scrollBy(0, heightDifference);
                if (heightDifference > 0 && tv_content != null) {
                    tv_content.setFocusable(true);
                    tv_content.setFocusableInTouchMode(true);
                    tv_content.requestFocus();
                    tv_content.findFocus();
                }
            }
        });
    }


}
