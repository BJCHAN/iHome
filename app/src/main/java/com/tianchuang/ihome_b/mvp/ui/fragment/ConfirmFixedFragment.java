package com.tianchuang.ihome_b.mvp.ui.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jakewharton.rxbinding.view.RxView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.mvp.ui.activity.MyOrderActivity;
import com.tianchuang.ihome_b.adapter.ImagesSelectorAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ImagesMultipleItem;
import com.tianchuang.ihome_b.bean.event.FeeSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesSelectorItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.bean.model.MyOrderModel;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.ImagesSelectorUtils;
import com.tianchuang.ihome_b.utils.MultipartBuilder;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Abyss on 2017/3/4.
 * description:确认修复
 */
public class ConfirmFixedFragment extends BaseFragment implements MyOrderActivity.GetImageByCodeListener {
    public static final int TYPE_BEFORE = 666;
    public static final int TYPE_AFTER = 777;
    @BindView(R.id.rv_list_fix_before)
    RecyclerView rvListFixBefore;
    @BindView(R.id.rv_list_fix_after)
    RecyclerView rvListFixAfter;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.loginBt)
    Button loginBt;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private MyOrderActivity holdingActivity;
    private ImgSelConfig config_before;
    private ImgSelConfig config_after;
    private ArrayList<ImagesMultipleItem> beforeList;
    private ArrayList<ImagesMultipleItem> afterList;
    private ImagesSelectorAdapter beforeAdapter;
    private ImagesSelectorAdapter afterAdapter;
    private int repairId;

    public static ConfirmFixedFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        ConfirmFixedFragment fragment = new ConfirmFixedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_fixed;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        repairId = getArguments().getInt("id");
        holdingActivity = (MyOrderActivity) getHoldingActivity();
        initImageSelector();
        initRecyclerView(rvListFixBefore);
        initRecyclerView(rvListFixAfter);
        initAdapter();
        controlKeyboardLayout(scrollView.getRootView(), scrollView);
    }

    private void initAdapter() {
        this.beforeList = getListData();
        this.afterList = getListData();
        beforeAdapter = new ImagesSelectorAdapter(beforeList);
        afterAdapter = new ImagesSelectorAdapter(afterList);
        rvListFixBefore.setAdapter(beforeAdapter);
        rvListFixAfter.setAdapter(afterAdapter);

        rvListFixBefore.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (((ImagesMultipleItem) adapter.getData().get(position)).getItemType()) {
                    case ImagesMultipleItem.ADD_IMG:
                        if (config_before.maxNum > 0) {
                            holdingActivity.startImageSelector(config_before, TYPE_BEFORE);//去选择图片
                        } else {
                            ToastUtil.showToast(getContext(), "选择图片数量到达上限");
                        }
                        break;
                    case ImagesMultipleItem.IMG:
                        removeImage(adapter, position, config_before);
                        break;
                }
            }
        });
        rvListFixAfter.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (((ImagesMultipleItem) adapter.getData().get(position)).getItemType()) {
                    case ImagesMultipleItem.ADD_IMG:
                        if (config_after.maxNum > 0) {
                            holdingActivity.startImageSelector(config_after, TYPE_AFTER);//去选择图片
                        } else {
                            ToastUtil.showToast(getContext(), "选择图片数量到达上限");
                        }
                        break;
                    case ImagesMultipleItem.IMG:
                        removeImage(adapter, position, config_after);
                        break;
                }
            }
        });
    }

    //接收之前的图片
    @Override
    public void onImageBefore(List<String> paths) {
        receiveImages(paths, beforeList, beforeAdapter, config_before);
    }

    //接收之后的图片
    @Override
    public void onImageAfter(List<String> paths) {
        receiveImages(paths, afterList, afterAdapter, config_after);
    }

    private void receiveImages(List<String> paths, ArrayList<ImagesMultipleItem> mData, ImagesSelectorAdapter imagesSelectorAdapter, ImgSelConfig config) {
        for (String s : paths) {
            mData.add(mData.size() - 1, new ImagesMultipleItem(ImagesMultipleItem.IMG).setUrl(s));
        }
        imagesSelectorAdapter.notifyItemRangeInserted(mData.size() - 1, paths.size());
        config.maxNum -= paths.size();//图片上限减去选中数量
        if (config.maxNum <= 0) {//达到图片选择上限,去掉添加按钮
            imagesSelectorAdapter.remove(mData.size() - 1);
        }
    }

    private void removeImage(BaseQuickAdapter adapter, int position, ImgSelConfig config) {
        config.maxNum += 1;//图片上限加上删除数量
        List data = adapter.getData();
        data.remove(position);
        adapter.notifyItemRemoved(position);
        //判断添加图片按钮是否存在
        if (data.size() > 0 && (ImagesMultipleItem.IMG == adapter.getItemViewType(data.size() - 1))) {
            //不存在，添加按钮
            data.add(data.size(), new ImagesMultipleItem(ImagesMultipleItem.ADD_IMG));
            adapter.notifyItemInserted(data.size());
        }
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
//						addFragment(MyOrderFeeDetailFragment.newInstance(repairId));
                    }
                });
        holdingActivity.setGetImageByCodeListener(this);//选择图片的监听
    }

    /**
     * 访问网络提交数据
     */
    private void requestNet() {
        final String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showToast(getContext(), "描述不能为空");
            return;
        }
        final ArrayList<File> beforeFiles = getImageFiles(beforeList);
        final ArrayList<File> afterFiles = getImageFiles(afterList);
        if (beforeFiles.size() == 0 || afterFiles.size() == 0) {
            ToastUtil.showToast(getContext(), "图片不能为空");
            return;
        }
        Observable.zip(Observable.just(beforeFiles), Observable.just(afterFiles), (file1, files2) -> {
            List<MultipartBody.Part> beforePhotos = MultipartBuilder.filesToMultipartBodyParts(file1, "beforePhotos");
            List<MultipartBody.Part> afterPhotos = MultipartBuilder.filesToMultipartBodyParts(files2, "afterPhotos");
            beforePhotos.addAll(afterPhotos);
            return beforePhotos;
        })
                .observeOn(Schedulers.io())
                .switchMap(new Func1<List<MultipartBody.Part>, Observable<HttpModle<String>>>() {
                    @Override
                    public Observable<HttpModle<String>> call(List<MultipartBody.Part> parts) {
                        return MyOrderModel.confirmOrder(repairId, content, parts);
                    }
                })
                .compose(this.<HttpModle<String>>bindToLifecycle())
                .compose(RxHelper.<String>handleResult())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    protected void _onNext(String s) {
//						FileUtils.deleteImageFile();
                        //跳转到费用明细
                        EventBus.getDefault().post(new FeeSubmitSuccessEvent());//通知前一个页面刷新
                        FragmentUtils.popAddFragment(getFragmentManager(), holdingActivity.getFragmentContainerId(), MyOrderFeeDetailFragment.newInstance(repairId), true);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
//						FileUtils.deleteImageFile();//删除压缩的图片
                    }

                    @Override
                    public void onCompleted() {
                    }
                });


    }

    private ArrayList<File> getImageFiles(ArrayList<ImagesMultipleItem> mData) {
        ArrayList<File> files = new ArrayList<>();
        for (ImagesMultipleItem imagesMultipleItem : mData) {
            if (imagesMultipleItem.getItemType() == imagesMultipleItem.IMG) {
                files.add(new File(imagesMultipleItem.getUrl()));
            }
        }
        return files;
    }

    public ArrayList<ImagesMultipleItem> getListData() {
        ArrayList<ImagesMultipleItem> list = new ArrayList<>();
        list.add(new ImagesMultipleItem(ImagesMultipleItem.ADD_IMG));
        return list;
    }

    /**
     * 初始化列表
     *
     * @param rvList
     */
    private void initRecyclerView(RecyclerView rvList) {
        rvList.addItemDecoration(new ImagesSelectorItemDecoration(5));
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    /**
     * 初始化图片选择器
     */
    private void initImageSelector() {
        config_before = ImagesSelectorUtils.getImgSelConfig();
        config_after = ImagesSelectorUtils.getImgSelConfig();
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("确认修复");
    }

    /**
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private void controlKeyboardLayout(final View root, final ScrollView needToScrollView) {
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
            }
        });
    }


}
