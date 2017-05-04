package com.tianchuang.ihome_b.http.retrofit;

import android.support.annotation.NonNull;


import com.tianchuang.ihome_b.http.OkHttpClientManager;
import com.tianchuang.ihome_b.utils.NetworkUtil;
import com.tianchuang.ihome_b.utils.Utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abyss on 2016/12/29.
 * description:Retrofit2+okhttp3
 */
public class RetrofitService {
    private static OkHttpClient mOkHttpClient;

    private volatile static ShowApi showApi = null;

    private RetrofitService() {

    }

    public static ShowApi createShowApi() {
        if (showApi == null) {
            synchronized (RetrofitService.class) {
                if (showApi == null) {
                    initOkHttpClient();
                    showApi = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BizInterface.API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(ShowApi.class);
                }
            }
        }
        return showApi;
    }

    // 配置OkHttpClient
    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
//            okhttp 3
            mOkHttpClient = OkHttpClientManager.getClinet();
        }
    }

    /**
     * 根据网络状况获取缓存的策略
     *
     * @return
     */
    @NonNull
    public static String getCacheControl() {
        return NetworkUtil.isConnected(Utils.getContext()) ? OkHttpClientManager.CACHE_CONTROL_NETWORK : OkHttpClientManager.CACHE_CONTROL_CACHE;
    }
}
