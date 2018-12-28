package com.zjh.administrat.monthdemo1.api;

import java.util.Map;
import java.util.Observable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface BaseApis<T> {
    @GET
    rx.Observable<ResponseBody> get(@Url String urlStr);

    @POST
    rx.Observable<ResponseBody> post(@Url String urlStr, @QueryMap Map<String, String> map);

    @Multipart
    @POST
    rx.Observable<ResponseBody> postFormBody(@Url String urlStr, @PartMap Map<String, RequestBody> requestBodyMap);

}
