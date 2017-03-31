package com.tianchuang.ihome_b.fragment;

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
 * description:查看协议界面
 */

public class ProtocolNoteFragment extends BaseFragment {


    @BindView(R.id.webView)
    WebView webView;

    public static ProtocolNoteFragment newInstance() {
        return new ProtocolNoteFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("用户注册协议");
        webView.loadUrl("http://boss-client-test.hecaifu.com/assets/html/agreement.htm");
        webView.setWebViewClient(new WebViewClient() {
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
