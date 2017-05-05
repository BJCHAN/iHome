package com.tianchuang.ihome_b.http;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.orhanobut.logger.Logger;
import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.utils.AppUtils;
import com.tianchuang.ihome_b.utils.NetworkUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.Utils;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Abyss on 2017/2/11.
 * description:Okhttp的封装与配置
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private volatile static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private OkHttpClientManager() {      //            okhttp 3
        mOkHttpClient = initOkHttpClient();
    }

    //设缓存有效期为两天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    /**
     * 配置okhttp3
     */
    @NonNull
    private OkHttpClient initOkHttpClient() {
        File cacheFile = new File(Utils.getContext().getCacheDir(),
                "HttpCache"); // 指定缓存路径
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); // 指定缓存大小50Mb
        // 云端响应头拦截器，用来配置缓存策略
        Interceptor rewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isConnected(Utils.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE).build();
                    Logger.e("no network");
//                    throw new ConnectException();
                }
                Response originalResponse = chain.proceed(request);
                if (NetworkUtil.isConnected(Utils.getContext())) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//                        String cacheControl = request.cacheControl().toString();
                     originalResponse.newBuilder()
                            .header("Cache-Control", CACHE_CONTROL_NETWORK)
                            .removeHeader("Pragma").build();
                } else {
                    originalResponse.newBuilder().header("Cache-Control",
                            "public, only-if-cached," + CACHE_STALE_SEC)
                            .removeHeader("Pragma").build();
                }
                return originalResponse;

            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        if (Constants.DEBUG_MODE) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addNetworkInterceptor(logging);
//        }
        OkHttpClient Client = builder.cache(cache)
                .addInterceptor(new LoggingInterceptor.Builder()//添加日志
                        .loggable(Constants.DEBUG_MODE)
                        .setLevel(Level.BODY)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .build())
//                .addNetworkInterceptor(rewriteCacheControlInterceptor)
                .addNetworkInterceptor(new BaseInterceptor())
                .addInterceptor(rewriteCacheControlInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())//stetho调试工具
                // todo 调试工具,正式发布时去掉
                .cookieJar(new CookieJar() {        //cookie enabled
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(Constants.ConfigureTimeouts.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.ConfigureTimeouts.GET_REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.ConfigureTimeouts.REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .build();
        return Client;
    }

    public static OkHttpClient getClinet() {
        return getInstance().mOkHttpClient;
    }


    private class BaseInterceptor implements Interceptor {

        private OAuthAccessor oAuthAccessor;

        public BaseInterceptor() {
            oAuthAccessor = new OAuthAccessor(new OAuthConsumer(null, Constants.Http.KEY, Constants.Http.SECRET, new OAuthServiceProvider(null, null, null)));
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("User-Agent", "android");
            HttpUrl.Builder httpUrlBuilder = original.url().newBuilder()
                    .addQueryParameter("deviceInfo", "tempdevice")
                    .addQueryParameter("appVersion", AppUtils.getAppVersionName(Utils.getContext()));
            if (UserUtil.isLogin()) {
                httpUrlBuilder.addQueryParameter("token", UserUtil.getToken());
            }

            String url = httpUrlBuilder.build().url().toString();
            List<Map.Entry<String, String>> mOAuthParamList = null;
            try {
                OAuthMessage message = oAuthAccessor.newRequestMessage(OAuthMessage.POST, url, null);
                mOAuthParamList = message.getParameters();
            } catch (Exception e) {
                e.printStackTrace();
            }
            builder.url(OAuth.addParameters(url, mOAuthParamList));
            return chain.proceed(builder.build());
        }
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }








}
