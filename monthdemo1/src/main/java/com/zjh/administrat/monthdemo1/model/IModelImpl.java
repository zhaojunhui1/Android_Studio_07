package com.zjh.administrat.monthdemo1.model;
import com.google.gson.Gson;
import com.zjh.administrat.monthdemo1.api.RetrofitManager;
import com.zjh.administrat.monthdemo1.callback.MyCallBack;

import java.util.Map;

import okhttp3.RequestBody;

public class IModelImpl<T> implements IModel {


    @Override
    public void mRequestData(String url, Map<String, Integer> params, final Class clazz, final MyCallBack callBack) {
        //Map<String, RequestBody> map = RetrofitManager.getInstance().generatrRequestBody(params);
        RetrofitManager.getInstance().post(url, params).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object obj = new Gson().fromJson(data, clazz);
                    if (callBack != null) {
                        callBack.OnSuccess(obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.OnFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if (callBack != null) {
                    callBack.OnFail(error);
                }
            }
        });

    }
}
