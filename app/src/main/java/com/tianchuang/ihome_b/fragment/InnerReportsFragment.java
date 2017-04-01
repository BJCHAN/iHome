package com.tianchuang.ihome_b.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jakewharton.rxbinding.view.RxView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.InnerReportsActivity;
import com.tianchuang.ihome_b.adapter.ImagesSelectorAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.ImagesMultipleItem;
import com.tianchuang.ihome_b.bean.event.NotifyHomePageRefreshEvent;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesSelectorItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.bean.model.InnerReportsModel;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.ImagesSelectorUtils;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事fragment(主页)
 */

public class InnerReportsFragment extends BaseFragment implements InnerReportsActivity.GetImageByCodeListener {

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.loginBt)
    Button loginBt;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_department_name)
    TextView tvDepartmentName;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private InnerReportsActivity holdingActivity;
    private ImgSelConfig config;
    private ArrayList<ImagesMultipleItem> mData;
    private ImagesSelectorAdapter imagesSelectorAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inner_reports;
    }

    public static InnerReportsFragment newInstance() {
        return new InnerReportsFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        LoginBean loginBean = UserUtil.getLoginBean();
        tvName.setText(StringUtils.getNotNull(loginBean.getName()));
        tvDepartmentName.setText(StringUtils.getNotNull(loginBean.getDepartmentName()));
        initImageSelector();
        rvList.addItemDecoration(new ImagesSelectorItemDecoration(5));
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        initAdapter();

    }

    private void initAdapter() {
        mData = new ArrayList<>();
        mData.add(new ImagesMultipleItem(ImagesMultipleItem.ADD_IMG));//添加图片按钮
        imagesSelectorAdapter = new ImagesSelectorAdapter(mData);
        rvList.setAdapter(imagesSelectorAdapter);
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (((ImagesMultipleItem) adapter.getData().get(position)).getItemType()) {
                    case ImagesMultipleItem.ADD_IMG:
                        if (config.maxNum > 0) {
                            holdingActivity.startImageSelector(config);//去选择图片
                        } else {
                            ToastUtil.showToast(getContext(), "选择图片数量到达上限");
                        }
                        break;
                    case ImagesMultipleItem.IMG:
                        config.maxNum += 1;//图片上限加上删除数量
                        mData.remove(position);
                        adapter.notifyItemRemoved(position);
                        break;
                }
            }
        });
    }

    /**
     * 接收选择的图片的回调
     *
     * @param paths
     */
    @Override
    public void onImage(List<String> paths) {
        for (String s : paths) {
            mData.add(mData.size() - 1, new ImagesMultipleItem(ImagesMultipleItem.IMG).setUrl(s));
        }
        imagesSelectorAdapter.notifyItemRangeInserted(mData.size() - 1, paths.size());
        config.maxNum -= paths.size();//图片上限减去选中数量
    }

    /**
     * 初始化图片选择器
     */
    private void initImageSelector() {
        config = ImagesSelectorUtils.getImgSelConfig();//获取本项目图片选择器的配置
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        holdingActivity = ((InnerReportsActivity) activity);

    }

    @Override
    protected void initListener() {
        RxView.clicks(loginBt)
                .throttleFirst(3, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        requestNet();
                    }
                });
        holdingActivity.setGetImageByCodeListener(this);//选择图片的监听
    }

    /**
     * 请求网络上传数据
     */
    private void requestNet() {
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(getContext(), "内容不能为空");
            return;
        }
        ArrayList<File> files = new ArrayList<>();
        for (ImagesMultipleItem imagesMultipleItem : mData) {
            if (imagesMultipleItem.getItemType() == imagesMultipleItem.IMG) {
                files.add(new File(imagesMultipleItem.getUrl()));
            }
        }
        InnerReportsModel.requestReportsSubmit(UserUtil.getLoginBean().getPropertyCompanyId(), content, files)
                .compose(RxHelper.<String>handleResult())
                .compose(this.<String>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    protected void _onNext(String s) {
                        FragmentUtils.popAddFragment(getFragmentManager(), holdingActivity.getFragmentContainerId(), InnerReportsSuccessFragment.newInstance(), true);
                        EventBus.getDefault().post(new NotifyHomePageRefreshEvent());
                        dismissProgress();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
