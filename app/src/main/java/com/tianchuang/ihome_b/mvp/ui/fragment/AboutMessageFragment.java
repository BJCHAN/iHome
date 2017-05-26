package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/2/13.
 * description:关于i家帮界面
 */

public class AboutMessageFragment extends BaseFragment {

    @BindView(R.id.webView)
    WebView webView;

    public static AboutMessageFragment newInstance() {
        return new AboutMessageFragment();
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        webView.loadUrl("http://boss.wuguan365.com/assets/html/aboutus.htm");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_message;
    }
}
