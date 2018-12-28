package com.zjh.administrat.monthdemo1.api;

import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager<T> {
    //private final String urlStr = "http://www.zhaoapi.cn/product/searchProducts?keywords=%E6%89%8B%E6%9C%BA&page=1&sort=1";
    private final String BASR_URL = "http://www.zhaoapi.cn/product/";


    private static volatile RetrofitManager retrofitManager;

    public static RetrofitManager getInstance(){
        if (retrofitManager == null){
            synchronized (RetrofitManager.class){
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }

    private BaseApis baseApis;

    public RetrofitManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASR_URL)
                .client(client)
                .build();
        baseApis = retrofit.create(BaseApis.class);

    }

    public Map<String, RequestBody> generatrRequestBody(Map<String, String> requestDataMap){
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }


    /**
     * get请求
     */
    public RetrofitManager get(String url) {

        baseApis.get(url)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(observer);

        return retrofitManager;
    }

    /**
     * 表单post请求
     */
    public RetrofitManager postFormBody(String url, Map<String, RequestBody> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseApis.postFormBody(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitManager;
    }

    /**
     * 普通post请求
     */
    public RetrofitManager post(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitManager;
    }

    private rx.Observer observer = new rx.Observer<ResponseBody>() {
        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String string = responseBody.string();
                if (mListener != null){
                    mListener.onSuccess(string);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (mListener != null){
                    mListener.onFail(e.getMessage());
                }
            }
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (mListener != null) {
                mListener.onFail(e.getMessage());
            }
        }

    };


    private HttpListener mListener;

    public void result(HttpListener listener) {
        this.mListener = listener;
    }
    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }

}
