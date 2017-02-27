package com.tianchuang.ihome_b.http;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
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
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Abyss on 2017/2/11.
 * description:Okhttp的封装与配置
 */
public class OkHttpClientManager {
	private static final String TAG = "OkHttpClientManager";
	private volatile static OkHttpClientManager mInstance;
	private OkHttpClient mOkHttpClient;
	private Handler mDelivery;
	private Gson mGson;

	private HttpsDelegate mHttpsDelegate = new HttpsDelegate();
	//    private DownloadDelegate mDownloadDelegate = new DownloadDelegate();
	private GetDelegate mGetDelegate = new GetDelegate();
	//    private UploadDelegate mUploadDelegate = new UploadDelegate();
	private PostDelegate mPostDelegate = new PostDelegate();

	private OkHttpClientManager() {
		//            okhttp 3
		mOkHttpClient = initOkHttpClient();
		mDelivery = new Handler(Looper.getMainLooper());
		mGson = new Gson();
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
				}
				Response originalResponse = chain.proceed(request);
				if (NetworkUtil.isConnected(Utils.getContext())) {
					//有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//                        String cacheControl = request.cacheControl().toString();
					return originalResponse.newBuilder()
							.header("Cache-Control", CACHE_CONTROL_NETWORK)
							.removeHeader("Pragma").build();
				} else {
					return originalResponse.newBuilder().header("Cache-Control",
							"public, only-if-cached," + CACHE_STALE_SEC)
							.removeHeader("Pragma").build();
				}

			}
		};

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (Constants.DEBUG_MODE) {
			HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
			logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
			builder.addNetworkInterceptor(logging);
		}
		OkHttpClient Client = builder.cache(cache)
				.addNetworkInterceptor(rewriteCacheControlInterceptor)
				.addNetworkInterceptor(new BaseInterceptor())
				.addInterceptor(rewriteCacheControlInterceptor)
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

	public GetDelegate getGetDelegate() {
		return mGetDelegate;
	}

	public PostDelegate getPostDelegate() {
		return mPostDelegate;
	}

	private HttpsDelegate _getHttpsDelegate() {
		return mHttpsDelegate;
	}


	public static HttpsDelegate getHttpsDelegate() {
		return getInstance()._getHttpsDelegate();
	}

	/**
	 * ============Get方便的访问方式============
	 */

	public static void getAsyn(String url, Class<?> clazz, ResultCallback callback) {
		getInstance().getGetDelegate().getAsyn(url, clazz, callback, null);
	}

	public static void getAsyn(String url, Class<?> clazz, ResultCallback callback, Object tag) {
		getInstance().getGetDelegate().getAsyn(url, clazz, callback, tag);
	}

	/**
	 * ============POST方便的访问方式===============
	 */
	public static void postAsyn(String url, Param[] params, Class<?> clazz, final ResultCallback callback) {
		getInstance().getPostDelegate().postAsyn(url, params, clazz, callback, null);
	}

	public static void postAsyn(String url, Map<String, Object> params, Class<?> clazz, final ResultCallback callback) {
		getInstance().getPostDelegate().postAsyn(url, params, clazz, callback, null);
	}

	public static String postAsyn(String url, Map<String, Object> params, Class<?> clazz) throws Exception {
		// 放置参数
		if (null != params) {
			StringBuffer logBuffer = new StringBuffer();
			StringBuilder paras = new StringBuilder("");// 打印字符请求
			Set<Map.Entry<String, Object>> set = params.entrySet();
			Iterator<Map.Entry<String, Object>> it = set.iterator();
			while (it.hasNext()) {
				Map.Entry<java.lang.String, java.lang.Object> entry = it.next();
				paras.append(entry.getKey()).append("=").append(entry.getValue());// 未编码前的请求参数
				logBuffer.append(entry.getKey() + ":" + entry.getValue() + " ");
				if (it.hasNext()) {
					paras.append("&");
				}
			}
			if (Constants.DEBUG_MODE) {
				Logger.d("OKhttp***" + url + "", logBuffer + "");
				Logger.d("OKhttp***" + url, url + "?" + paras.toString());
			}

		}

		return getInstance().getPostDelegate().postAsyn(url, params, clazz);
	}


	public static void postAsyn(String url, String bodyStr, Class<?> clazz, final ResultCallback callback) {
		getInstance().getPostDelegate().postAsyn(url, bodyStr, clazz, callback, null);
	}

	public static void postAsyn(String url, Param[] params, Class<?> clazz, final ResultCallback callback, Object tag) {
		getInstance().getPostDelegate().postAsyn(url, params, clazz, callback, tag);
	}

	public static void postAsyn(String url, Map<String, Object> params, Class<?> clazz, final ResultCallback callback, Object tag) {
		getInstance().getPostDelegate().postAsyn(url, params, clazz, callback, tag);
	}

	public static void postAsyn(String url, String bodyStr, Class<?> clazz, final ResultCallback callback, Object tag) {
		getInstance().getPostDelegate().postAsyn(url, bodyStr, clazz, callback, tag);
	}

	//=============便利的访问方式结束===============

	private String guessMimeType(String path) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String contentTypeFor = fileNameMap.getContentTypeFor(path);
		if (contentTypeFor == null) {
			contentTypeFor = "application/octet-stream";
		}
		return contentTypeFor;
	}

	private Param[] validateParam(Param[] params) {
		if (params == null)
			return new Param[0];
		else
			return params;
	}

	private static Param[] map2Params(Map<String, Object> params) {
		if (params == null)
			return new Param[0];
		List<Param> paramList = new ArrayList<>();
		Set<Map.Entry<String, Object>> entries = params.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			if (entry.getValue() instanceof String[]) {
				for (String itemValue : (String[]) entry.getValue()) {
					paramList.add(new Param(entry.getKey(), itemValue));
				}
			} else if (entry.getValue() instanceof int[]) {
				for (int itemValue : (int[]) entry.getValue()) {
					paramList.add(new Param(entry.getKey(), String.valueOf(itemValue)));
				}
			} else {
				paramList.add(new Param(entry.getKey(), String.valueOf(entry.getValue())));
			}
		}
		Param[] res = new Param[paramList.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = paramList.get(i);
		}
		paramList.clear();
		paramList = null;
		return res;
	}

	private void deliveryResult(ResultCallback callback, final Request request, final Class<?> clazz) {
		if (callback == null)
			callback = DEFAULT_RESULT_CALLBACK;
		final ResultCallback resCallBack = callback;
		//UI thread
		callback.onBefore(request);
		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				sendFailedStringCallback(request, e, resCallBack);
				if (Constants.DEBUG_MODE) {
					Logger.e(request.url() + "——response: 失败");
					e.printStackTrace();
				}
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					final String string = response.body().string();
					if (Constants.DEBUG_MODE) {
						Logger.e(request.url() + "——response: " + string + "");
					}
//                    Log.d("okhttp***" + request.urlString() + "——response", string + "");
					Object o = mGson.fromJson(string, clazz);
					sendSuccessResultCallback(o, resCallBack);

				} catch (IOException e) {
					sendFailedStringCallback(response.request(), e, resCallBack);
				} catch (com.google.gson.JsonParseException e)//Json解析的错误
				{
					sendFailedStringCallback(response.request(), e, resCallBack);
				}

			}
		});
	}

	private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
		mDelivery.post(new Runnable() {
			@Override
			public void run() {
				callback.onError(request, e);
				callback.onAfter();
			}
		});
	}

	private void sendSuccessResultCallback(final Object object, final ResultCallback callback) {
		mDelivery.post(new Runnable() {
			@Override
			public void run() {
				callback.onResponse(object);
				callback.onAfter();
			}
		});
	}

	private String getFileName(String path) {
		int separatorIndex = path.lastIndexOf("/");
		return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
	}

	private Request buildPostFormRequest(String url, Param[] params, Object tag) {
		if (params == null) {
			params = new Param[0];
		}

		FormBody.Builder builder = new FormBody.Builder();
		for (Param param : params) {
			builder.add(param.key, param.value);
		}
		FormBody body = builder.build();
		Request.Builder reqBuilder = new Request.Builder();
		reqBuilder.url(url).post(body);

		if (tag != null) {
			reqBuilder.tag(tag);
		}
		return reqBuilder.build();
	}

	public static void cancelTag(Object tag) {
		getInstance()._cancelTag(tag);
	}

	private void _cancelTag(Object tag) {
		if (tag == null) {
			return;
		}

		synchronized (mOkHttpClient.dispatcher().getClass()) {
			for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
				if (tag.equals(call.request().tag())) call.cancel();
			}

			for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
				if (tag.equals(call.request().tag())) call.cancel();
			}
		}
	}

	public static OkHttpClient getClinet() {
		return getInstance().client();
	}

	public OkHttpClient client() {
		return mOkHttpClient;
	}

	public static abstract class ResultCallback<T> {
		Type mType;

		public ResultCallback() {
//			mType = getSuperclassTypeParameter(getClass());
		}

		static Type getSuperclassTypeParameter(Class<?> subclass) {
			Type superclass = subclass.getGenericSuperclass();
			if (superclass instanceof Class) {
				throw new RuntimeException("Missing type parameter.");
			}
			ParameterizedType parameterized = (ParameterizedType) superclass;
			return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
		}

		public void onBefore(Request request) {

		}

		public void onAfter() {
		}

		public abstract void onError(Request request, Exception e);

		public abstract void onResponse(T response);
	}

	private final ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {
		@Override
		public void onError(Request request, Exception e) {

		}

		@Override
		public void onResponse(String response) {

		}
	};

	public static class Param {
		public Param() {
		}

		public Param(String key, String value) {
			this.key = key;
			this.value = value;
		}

		String key;
		String value;
	}

	//====================PostDelegate=======================
	public class PostDelegate {
		private final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");
		private final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain;charset=utf-8");

		public Response post(String url, Param[] params) throws IOException {
			return post(url, params, null);
		}

		/**
		 * 同步的Post请求
		 */
		public Response post(String url, Param[] params, Object tag) throws IOException {
			Request request = buildPostFormRequest(url, params, tag);
			Response response = mOkHttpClient.newCall(request).execute();
			return response;
		}

		public String postAsString(String url, Param[] params) throws IOException {
			return postAsString(url, params, null);
		}

		/**
		 * 同步的Post请求
		 */
		public String postAsString(String url, Param[] params, Object tag) throws IOException {
			Response response = post(url, params, tag);
			return response.body().string();
		}

		public void postAsyn(String url, Map<String, Object> params, Class<?> clazz, final ResultCallback callback) {
			postAsyn(url, params, clazz, callback, null);
		}

		public String postAsyn(String url, Map<String, Object> params, Class<?> clazz) throws IOException {
			Param[] paramsArr = map2Params(params);
			Request request = buildPostFormRequest(url, paramsArr, null);
			Response response = mOkHttpClient.newCall(request).execute();
			return response.body().string();
		}

		public void postAsyn(String url, Map<String, Object> params, Class<?> clazz, final ResultCallback callback, Object tag) {
			// 放置参数
			if (null != params) {
				StringBuffer logBuffer = new StringBuffer();
				StringBuilder paras = new StringBuilder("");// 打印字符请求
				Set<Map.Entry<String, Object>> set = params.entrySet();
				Iterator<Map.Entry<String, Object>> it = set.iterator();
				while (it.hasNext()) {
					Map.Entry<java.lang.String, java.lang.Object> entry = it.next();
					paras.append(entry.getKey()).append("=").append(entry.getValue());// 未编码前的请求参数
					logBuffer.append(entry.getKey() + ":" + entry.getValue() + " ");
					if (it.hasNext()) {
						paras.append("&");
					}
				}
				if (Constants.DEBUG_MODE) {
					Logger.w(url + "?" + paras);
				}
			}
			Param[] paramsArr = map2Params(params);
			postAsyn(url, paramsArr, clazz, callback, tag);
		}

		public void postAsyn(String url, Param[] params, Class<?> clazz, final ResultCallback callback) {
			postAsyn(url, params, clazz, callback, null);
		}

		/**
		 * 异步的post请求
		 */
		public void postAsyn(String url, Param[] params, Class<?> clazz, final ResultCallback callback, Object tag) {
			Request request = buildPostFormRequest(url, params, tag);
			deliveryResult(callback, request, clazz);
		}

		/**
		 * 同步的Post请求:直接将bodyStr以写入请求体
		 */
		public Response post(String url, String bodyStr) throws IOException {
			return post(url, bodyStr);
		}

		public Response post(String url, String bodyStr, Object tag) throws IOException {
			RequestBody body = RequestBody.create(MEDIA_TYPE_STRING, bodyStr);
			Request request = buildPostRequest(url, body, tag);
			Response response = mOkHttpClient.newCall(request).execute();
			return response;
		}

		/**
		 * 同步的Post请求:直接将bodyFile以写入请求体
		 */
		public Response post(String url, File bodyFile) throws IOException {
			return post(url, bodyFile);
		}

		public Response post(String url, File bodyFile, Object tag) throws IOException {
			RequestBody body = RequestBody.create(MEDIA_TYPE_STREAM, bodyFile);
			Request request = buildPostRequest(url, body, tag);
			Response response = mOkHttpClient.newCall(request).execute();
			return response;
		}

		/**
		 * 同步的Post请求
		 */
		public Response post(String url, byte[] bodyBytes) throws IOException {
			return post(url, bodyBytes);
		}

		public Response post(String url, byte[] bodyBytes, Object tag) throws IOException {
			RequestBody body = RequestBody.create(MEDIA_TYPE_STREAM, bodyBytes);
			Request request = buildPostRequest(url, body, tag);
			Response response = mOkHttpClient.newCall(request).execute();
			return response;
		}

		/**
		 * 直接将bodyStr以写入请求体
		 */
		public void postAsyn(String url, String bodyStr, final ResultCallback callback) {
			postAsyn(url, bodyStr, callback);
		}

		public void postAsyn(String url, String bodyStr, Class<?> clazz, final ResultCallback callback, Object tag) {
			postAsynWithMediaType(url, bodyStr, MediaType.parse("text/plain;charset=utf-8"), clazz, callback, tag);
		}

		/**
		 * 直接将bodyBytes以写入请求体
		 */
		public void postAsyn(String url, byte[] bodyBytes, Class<?> clazz, final ResultCallback callback) {
			postAsyn(url, bodyBytes, clazz, callback, null);
		}

		public void postAsyn(String url, byte[] bodyBytes, Class<?> clazz, final ResultCallback callback, Object tag) {
			postAsynWithMediaType(url, bodyBytes, MediaType.parse("application/octet-stream;charset=utf-8"), clazz, callback, tag);
		}

		/**
		 * 直接将bodyFile以写入请求体
		 */
		public void postAsyn(String url, File bodyFile, Class<?> clazz, final ResultCallback callback) {
			postAsyn(url, bodyFile, clazz, callback, null);
		}

		public void postAsyn(String url, File bodyFile, Class<?> clazz, final ResultCallback callback, Object tag) {
			postAsynWithMediaType(url, bodyFile, MediaType.parse("application/octet-stream;charset=utf-8"), clazz, callback, tag);
		}

		/**
		 * 直接将bodyStr以写入请求体
		 */
		public void postAsynWithMediaType(String url, String bodyStr, MediaType type, Class<?> clazz, final ResultCallback callback, Object tag) {
			RequestBody body = RequestBody.create(type, bodyStr);
			Request request = buildPostRequest(url, body, tag);
			deliveryResult(callback, request, clazz);
		}

		/**
		 * 直接将bodyStr以写入请求体
		 */
		public String postAsynWithMediaType(String url, String bodyStr, MediaType type, Object tag) throws Exception {
			RequestBody body = RequestBody.create(type, bodyStr);
			Request request = buildPostRequest(url, body, tag);
			Response response = mOkHttpClient.newCall(request).execute();
			return response.body().string();
		}

		/**
		 * 直接将bodyBytes以写入请求体
		 */
		public void postAsynWithMediaType(String url, byte[] bodyBytes, MediaType type, Class<?> clazz, final ResultCallback callback,
										  Object tag) {
			RequestBody body = RequestBody.create(type, bodyBytes);
			Request request = buildPostRequest(url, body, tag);
			deliveryResult(callback, request, clazz);
		}

		/**
		 * 直接将bodyFile以写入请求体
		 */
		public void postAsynWithMediaType(String url, File bodyFile, MediaType type, Class<?> clazz, final ResultCallback callback, Object tag) {
			RequestBody body = RequestBody.create(type, bodyFile);
			Request request = buildPostRequest(url, body, tag);
			deliveryResult(callback, request, clazz);
		}

		/**
		 * post构造Request的方法
		 *
		 * @param url
		 * @param body
		 * @return
		 */
		private Request buildPostRequest(String url, RequestBody body, Object tag) {
			Request.Builder builder = new Request.Builder().url(url).post(body);
			if (tag != null) {
				builder.tag(tag);
			}
			Request request = builder.build();
			return request;
		}

	}

	//====================GetDelegate=======================
	public class GetDelegate {

		private Request buildGetRequest(String url, Object tag) {
			Request.Builder builder = new Request.Builder().url(url);

			if (tag != null) {
				builder.tag(tag);
			}

			return builder.build();
		}

		/**
		 * 通用的方法
		 */
		public Response get(Request request) throws IOException {
			Call call = mOkHttpClient.newCall(request);
			Response execute = call.execute();
			return execute;
		}

		/**
		 * 同步的Get请求
		 */
		public Response get(String url) throws IOException {
			return get(url);
		}

		public Response get(String url, Object tag) throws IOException {
			final Request request = buildGetRequest(url, tag);
			return get(request);
		}

		/**
		 * 同步的Get请求
		 */
		public String getAsString(String url) throws IOException {
			return getAsString(url);
		}

		public String getAsString(String url, Object tag) throws IOException {
			Response execute = get(url, tag);
			return execute.body().string();
		}

		/**
		 * 通用的方法
		 */
		public void getAsyn(Request request, Class<?> clazz, ResultCallback callback) {
			deliveryResult(callback, request, clazz);
		}

		/**
		 * 异步的get请求
		 */
		public void getAsyn(String url, final ResultCallback callback) {
			getAsyn(url, callback);
		}

		public void getAsyn(String url, Class<?> clazz, final ResultCallback callback, Object tag) {
			final Request request = buildGetRequest(url, tag);
			getAsyn(request, clazz, callback);
		}
	}


	//====================HttpsDelegate=======================

	/**
	 * Https相关模块
	 */
	public class HttpsDelegate {

		public void setCertificates(InputStream... certificates) {
			setCertificates(certificates, null, null);
		}

		public TrustManager[] prepareTrustManager(InputStream... certificates) {
			if (certificates == null || certificates.length <= 0)
				return null;
			try {

				CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
				KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
				keyStore.load(null);
				int index = 0;
				for (InputStream certificate : certificates) {
					String certificateAlias = Integer.toString(index++);
					keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
					try {
						if (certificate != null)
							certificate.close();
					} catch (IOException e)

					{
					}
				}
				TrustManagerFactory trustManagerFactory = null;

				trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				trustManagerFactory.init(keyStore);

				TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

				return trustManagers;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		public KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
			try {
				if (bksFile == null || password == null)
					return null;

				KeyStore clientKeyStore = KeyStore.getInstance("BKS");
				clientKeyStore.load(bksFile, password.toCharArray());
				KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				keyManagerFactory.init(clientKeyStore, password.toCharArray());
				return keyManagerFactory.getKeyManagers();

			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public void setCertificates(InputStream[] certificates, InputStream bksFile, String password) {
			try {
				TrustManager[] trustManagers = prepareTrustManager(certificates);
				KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, new TrustManager[]{new MyTrustManager(chooseTrustManager(trustManagers))},
						new SecureRandom());
				mOkHttpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory()).hostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				}).build();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}
		}

		private X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
			for (TrustManager trustManager : trustManagers) {
				if (trustManager instanceof X509TrustManager) {
					return (X509TrustManager) trustManager;
				}
			}
			return null;
		}

		public class MyTrustManager implements X509TrustManager {
			private X509TrustManager defaultTrustManager;
			private X509TrustManager localTrustManager;

			public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
				TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				var4.init((KeyStore) null);
				defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
				this.localTrustManager = localTrustManager;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				try {
					defaultTrustManager.checkServerTrusted(chain, authType);
				} catch (CertificateException ce) {
					localTrustManager.checkServerTrusted(chain, authType);
				}
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		}

	}


}
