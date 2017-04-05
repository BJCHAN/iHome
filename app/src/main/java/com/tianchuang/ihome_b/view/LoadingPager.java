package com.tianchuang.ihome_b.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.tianchuang.ihome_b.utils.LayoutUtil;


/**
 * Created by Abyss on 2017/1/16.
 * description:加载的常用状态页
 */

public abstract class LoadingPager extends FrameLayout {

    // 加载默认的状态
    public static final int STATE_UNLOADED = 1;
    // 加载的状态
    public static final int STATE_LOADING = 2;
    // 加载失败的状态
    public static final int STATE_ERROR = 3;
    // 加载空的状态
    public static final int STATE_EMPTY = 4;
    // 加载成功的状态
    public static final int STATE_SUCCEED = 5;

    private View mLoadingView;// 转圈的view
    private View mErrorView;// 错误的view
    private View mEmptyView;// 空的view
    private View mSucceedView;// 成功的view

    public View getSucceedView() {
        return mSucceedView;
    }

    private int mState;// 默认的状态

    private int loadpage_empty;
    private int loadpage_error;
    private int loadpage_loading;

    public LoadingPager(Context context, @LayoutRes int  loading, @LayoutRes int error, @LayoutRes int empty) {
        super(context);
        loadpage_empty = empty;
        loadpage_error = error;
        loadpage_loading = loading;
        init();
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyle,
                        int loading, int error, int empty) {
        super(context, attrs, defStyle);
        loadpage_empty = empty;
        loadpage_error = error;
        loadpage_loading = loading;
        init();
    }

    public LoadingPager(Context context, AttributeSet attrs, int loading,
                        int error, int empty) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 初始化状态
        mState = STATE_UNLOADED;
        // 初始化三个 状态的view 这个时候 三个状态的view叠加在一起了
        mLoadingView = createLoadingView();
        if (null != mLoadingView) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        mErrorView = createErrorView();
        if (null != mErrorView) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        mEmptyView = createEmptyView();
        if (null != mEmptyView) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        showPagerView(mState);
    }

    public void showPagerView(int mState) {
        //外部调用也得更新状态
        this.mState = mState;
        // 这个时候都不为空 mState默认是STATE_UNLOADED状态所以只显示 lodaing 下面的 error
        // 和empty暂时不显示
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mState == STATE_UNLOADED
                    || mState == STATE_LOADING ? View.VISIBLE :

                    View.INVISIBLE);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }

        if (mState == STATE_SUCCEED && mSucceedView == null) {
            mSucceedView = createSuccessView();
            addView(mSucceedView, new LayoutParams

                    (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        if (null != mSucceedView) {
            mSucceedView.setVisibility(mState == STATE_SUCCEED ?
                    View.VISIBLE : View.INVISIBLE);
        }
    }

    public void show() {
        // 第一次进来肯定要 转圈的 所以就算是 error和empty 也要让状态是 unload
        if (mState == STATE_ERROR || mState == STATE_EMPTY) {
            mState = STATE_UNLOADED;
        }
        // 如果是unload 就把状态 变为 loading了 这时候从服务器拿数据
        if (mState == STATE_UNLOADED) {
            mState = STATE_LOADING;
            load();
        }
        showPagerView(mState);
    }

    /**
     * 制作界面
     */

    protected abstract  View createSuccessView();

    /**
     * 处理下载 耗时操作
     */
    protected abstract void load();

    /**
     * 空界面
     */
    public View createEmptyView() {
        if (loadpage_empty != 0) {
            return LayoutUtil.inflate(loadpage_empty);
        }
        return null;

    }

    /**
     * 失败的页面
     */
    public View createErrorView() {
        if (loadpage_error != 0) {
            View emptyView = LayoutUtil.inflate(loadpage_error);
            emptyView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    show();
                }
            });
            return emptyView;
        }
        return null;
    }

    /**
     * 正在旋转的页面
     */
    public View createLoadingView() {
        if (loadpage_loading != 0) {
            View loadingView = LayoutUtil.inflate(loadpage_loading);
            return loadingView;
        }
        return null;
    }

}